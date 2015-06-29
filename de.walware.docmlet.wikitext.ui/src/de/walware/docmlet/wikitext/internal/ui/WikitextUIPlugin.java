/*=============================================================================#
 # Copyright (c) 2009-2015 Stephan Wahlbrink (WalWare.de) and others.
 # All rights reserved. This program and the accompanying materials
 # are made available under the terms of the Eclipse Public License v1.0
 # which accompanies this distribution, and is available at
 # http://www.eclipse.org/legal/epl-v10.html
 # 
 # Contributors:
 #     Stephan Wahlbrink - initial API and implementation
 #=============================================================================*/

package de.walware.docmlet.wikitext.internal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.mylyn.internal.wikitext.ui.WikiTextUiPlugin;
import org.eclipse.mylyn.internal.wikitext.ui.util.WikiTextUiResources;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.walware.ecommons.ICommonStatusConstants;
import de.walware.ecommons.IDisposable;
import de.walware.ecommons.ltk.ui.sourceediting.assist.ContentAssistComputerRegistry;
import de.walware.ecommons.ltk.ui.util.CombinedPreferenceStore;
import de.walware.ecommons.preferences.PreferencesUtil;

import de.walware.docmlet.wikitext.core.WikitextCore;
import de.walware.docmlet.wikitext.ui.WikitextUI;
import de.walware.docmlet.wikitext.ui.sourceediting.WikitextEditingSettings;


public class WikitextUIPlugin extends AbstractUIPlugin {
	
	
	public static final String WIKIDOC_EDITOR_TEMPLATES_ID= "de.walware.docmlet.wikitext.templates.WikidocEditor"; //$NON-NLS-1$
	
	public static final String WIKITEXT_EDITOR_ASSIST_REGISTRY_GROUP_ID= "wikitext/wikitext.editor/assist.registry"; //$NON-NLS-1$
	
	
	/** The shared instance */
	private static WikitextUIPlugin instance;
	
	/**
	 * Returns the shared plug-in instance
	 *
	 * @return the shared instance
	 */
	public static WikitextUIPlugin getInstance() {
		return instance;
	}
	
	
	private volatile boolean started;
	
	private List<IDisposable> disposables;
	
	private IPreferenceStore editorPreferenceStore;
	
	private ContentAssistComputerRegistry wikidocEditorContentAssistRegistry;
	
	private IPropertyChangeListener mylynPrefListener;
	
	
	public WikitextUIPlugin() {
	}
	
	
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		instance= this;
		
		this.disposables= new ArrayList<>();
		
		this.started= true;
	}
	
	@Override
	public void stop(final BundleContext context) throws Exception {
		try {
			synchronized (this) {
				this.started= false;
				
				this.editorPreferenceStore= null;
			}
			
			if (this.mylynPrefListener != null) {
				final IPreferenceStore mylynPrefs= WikiTextUiPlugin.getDefault().getPreferenceStore();
				if (mylynPrefs != null) {
					mylynPrefs.removePropertyChangeListener(this.mylynPrefListener);
				}
				this.mylynPrefListener= null;
			}
			
			for (final IDisposable listener : this.disposables) {
				try {
					listener.dispose();
				}
				catch (final Throwable e) {
					getLog().log(new Status(IStatus.ERROR, WikitextUI.PLUGIN_ID, ICommonStatusConstants.INTERNAL_PLUGGED_IN,
							"Error occured while disposing a module.", e )); 
				}
			}
			this.disposables= null;
		}
		finally {
			instance= null;
			super.stop(context);
		}
	}
	
	
	public void addStoppingListener(final IDisposable listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		synchronized (this) {
			if (!this.started) {
				throw new IllegalStateException("Plug-in is not started.");
			}
			this.disposables.add(listener);
		}
	}
	
	
	@Override
	public synchronized IPreferenceStore getPreferenceStore() {
		final IPreferenceStore preferenceStore= super.getPreferenceStore();
		
		if (this.mylynPrefListener == null && this.started) {
			this.mylynPrefListener= new IPropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					if (event.getProperty().equals(WikiTextUiResources.PREFERENCE_TEXT_FONT)
							|| event.getProperty().equals(WikiTextUiResources.PREFERENCE_MONOSPACE_FONT) ) {
						final Job job= PreferencesUtil.getSettingsChangeNotifier().getNotifyJob("mylyn", //$NON-NLS-1$
								new String[] { WikitextEditingSettings.TEXTSTYLE_CONFIG_QUALIFIER } );
						job.schedule(250);
					}
				}
			};
			WikiTextUiPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this.mylynPrefListener);
		}
		
		return preferenceStore;
	}
	
	public synchronized IPreferenceStore getEditorPreferenceStore() {
		if (this.editorPreferenceStore == null) {
			if (!this.started) {
				throw new IllegalStateException("Plug-in is not started.");
			}
			this.editorPreferenceStore= CombinedPreferenceStore.createStore(
					getPreferenceStore(),
					EditorsUI.getPreferenceStore() );
		}
		return this.editorPreferenceStore;
	}
	
//	public synchronized WikidocDocumentProvider getWikidocDocumentProvider() {
//		if (this.wikidocDocumentProvider == null) {
//			if (!this.started) {
//				throw new IllegalStateException("Plug-in is not started.");
//			}
//			this.wikidocDocumentProvider= new WikidocDocumentProvider();
//			this.disposables.add(this.wikidocDocumentProvider);
//		}
//		return this.wikidocDocumentProvider;
//	}
//	
//	public synchronized ContextTypeRegistry getTexEditorTemplateContextTypeRegistry() {
//		if (this.fTexEditorTemplateContextTypeRegistry == null) {
//			if (!this.started) {
//				throw new IllegalStateException("Plug-in is not started.");
//			}
//			this.fTexEditorTemplateContextTypeRegistry= new ContributionContextTypeRegistry(WIKIDOC_EDITOR_TEMPLATES_ID);
//		}
//		return this.fTexEditorTemplateContextTypeRegistry;
//	}
//	
//	public synchronized TemplateStore getTexEditorTemplateStore() {
//		if (this.fTexEditorTemplateStore == null) {
//			if (!this.started) {
//				throw new IllegalStateException("Plug-in is not started.");
//			}
//			this.fTexEditorTemplateStore= new ContributionTemplateStore(
//					getTexEditorTemplateContextTypeRegistry(), getPreferenceStore(), WIKIDOC_EDITOR_TEMPLATES_ID);
//			try {
//				this.fTexEditorTemplateStore.load();
//			}
//			catch (final IOException e) {
//				getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, ICommonStatusConstants.IO_ERROR,
//						"An error occured when loading 'LaTeX Editor' template store.", e)); 
//			}
//		}
//		return this.fTexEditorTemplateStore;
//	}
	
	public synchronized ContentAssistComputerRegistry getWikidocEditorContentAssistRegistry() {
		if (this.wikidocEditorContentAssistRegistry == null) {
			if (!this.started) {
				throw new IllegalStateException("Plug-in is not started.");
			}
			this.wikidocEditorContentAssistRegistry= new ContentAssistComputerRegistry(
					WikitextCore.WIKIDOC_CONTENT_ID_NG,
					WikitextEditingSettings.ASSIST_WIKIDOC_PREF_QUALIFIER ); 
			this.disposables.add(this.wikidocEditorContentAssistRegistry);
		}
		return this.wikidocEditorContentAssistRegistry;
	}
	
}
