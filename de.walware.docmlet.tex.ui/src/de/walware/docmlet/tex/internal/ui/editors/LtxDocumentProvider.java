/*=============================================================================#
 # Copyright (c) 2009-2016 Stephan Wahlbrink (WalWare.de) and others.
 # All rights reserved. This program and the accompanying materials
 # are made available under the terms of the Eclipse Public License v1.0
 # which accompanies this distribution, and is available at
 # http://www.eclipse.org/legal/epl-v10.html
 # 
 # Contributors:
 #     Stephan Wahlbrink - initial API and implementation
 #=============================================================================*/

package de.walware.docmlet.tex.internal.ui.editors;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.source.IAnnotationModel;

import de.walware.ecommons.IDisposable;
import de.walware.ecommons.ltk.IProblem;
import de.walware.ecommons.ltk.LTK;
import de.walware.ecommons.ltk.core.model.ISourceUnit;
import de.walware.ecommons.ltk.ui.sourceediting.SourceAnnotationModel;
import de.walware.ecommons.ltk.ui.sourceediting.SourceDocumentProvider;
import de.walware.ecommons.ltk.ui.sourceediting.SourceProblemAnnotation;
import de.walware.ecommons.preferences.PreferencesUtil;
import de.walware.ecommons.preferences.SettingsChangeNotifier;
import de.walware.ecommons.preferences.core.IPreferenceAccess;

import de.walware.docmlet.tex.core.model.ITexSourceUnit;
import de.walware.docmlet.tex.core.model.TexModel;
import de.walware.docmlet.tex.core.source.LtxDocumentSetupParticipant;
import de.walware.docmlet.tex.ui.editors.TexEditorBuild;


public class LtxDocumentProvider extends SourceDocumentProvider<ITexSourceUnit>
		implements IDisposable {
	
	
	private class ThisAnnotationModel extends SourceAnnotationModel {
		
		public ThisAnnotationModel(final IResource resource) {
			super(resource);
		}
		
		@Override
		protected boolean isHandlingTemporaryProblems() {
			return fHandleTemporaryProblems;
		}
		
		@Override
		protected SourceProblemAnnotation createAnnotation(final IProblem problem) {
			if (problem.getCategoryId() == TexModel.LTX_TYPE_ID) {
				switch (problem.getSeverity()) {
				case IProblem.SEVERITY_ERROR:
					return new SourceProblemAnnotation(TexEditorBuild.ERROR_ANNOTATION_TYPE, problem,
							SourceProblemAnnotation.ERROR_CONFIG );
				case IProblem.SEVERITY_WARNING:
					return new SourceProblemAnnotation(TexEditorBuild.WARNING_ANNOTATION_TYPE, problem,
							SourceProblemAnnotation.WARNING_CONFIG );
				default:
					return new SourceProblemAnnotation(TexEditorBuild.INFO_ANNOTATION_TYPE, problem,
							SourceProblemAnnotation.INFO_CONFIG );
				}
			}
			return null;
		}
		
	}
	
	
	private SettingsChangeNotifier.ChangeListener fEditorPrefListener;
	
	private boolean fHandleTemporaryProblems;
	
	
	public LtxDocumentProvider() {
		super(TexModel.LTX_TYPE_ID, new LtxDocumentSetupParticipant());
		
		fEditorPrefListener = new SettingsChangeNotifier.ChangeListener() {
			@Override
			public void settingsChanged(final Set<String> groupIds) {
				if (groupIds.contains(TexEditorBuild.GROUP_ID)) {
					updateEditorPrefs();
				}
			}
		};
		PreferencesUtil.getSettingsChangeNotifier().addChangeListener(fEditorPrefListener);
		final IPreferenceAccess access = PreferencesUtil.getInstancePrefs();
		fHandleTemporaryProblems = access.getPreferenceValue(TexEditorBuild.PROBLEMCHECKING_ENABLED_PREF);
	}
	
	
	@Override
	public void dispose() {
		if (fEditorPrefListener != null) {
			PreferencesUtil.getSettingsChangeNotifier().removeChangeListener(fEditorPrefListener);
			fEditorPrefListener = null;
		}
	}
	
	private void updateEditorPrefs() {
		final IPreferenceAccess access = PreferencesUtil.getInstancePrefs();
		final boolean newHandleTemporaryProblems = access.getPreferenceValue(TexEditorBuild.PROBLEMCHECKING_ENABLED_PREF);
		if (fHandleTemporaryProblems != newHandleTemporaryProblems) {
			fHandleTemporaryProblems = newHandleTemporaryProblems;
			if (fHandleTemporaryProblems) {
				TexModel.getLtxModelManager().refresh(LTK.EDITOR_CONTEXT);
			}
			else {
				final List<? extends ISourceUnit> sus = LTK.getSourceUnitManager().getOpenSourceUnits(
						TexModel.LTX_TYPE_ID, LTK.EDITOR_CONTEXT );
				for (final ISourceUnit su : sus) {
					final IAnnotationModel model = getAnnotationModel(su);
					if (model instanceof ThisAnnotationModel) {
						((ThisAnnotationModel) model).clearProblems(null);
					}
				}
			}
		}
	}
	
	@Override
	protected IAnnotationModel createAnnotationModel(final IFile file) {
		return new ThisAnnotationModel(file);
	}
	
}
