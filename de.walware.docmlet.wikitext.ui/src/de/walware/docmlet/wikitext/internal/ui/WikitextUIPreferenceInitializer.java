/*=============================================================================#
 # Copyright (c) 2014-2015 Stephan Wahlbrink (WalWare.de) and others.
 # All rights reserved. This program and the accompanying materials
 # are made available under the terms of the Eclipse Public License v1.0
 # which accompanies this distribution, and is available at
 # http://www.eclipse.org/legal/epl-v10.html
 # 
 # Contributors:
 #     Stephan Wahlbrink - initial API and implementation
 #=============================================================================*/

package de.walware.docmlet.wikitext.internal.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;

import de.walware.ecommons.ltk.ui.sourceediting.ISmartInsertSettings.TabAction;
import de.walware.ecommons.preferences.PreferencesUtil;
import de.walware.ecommons.text.ui.settings.AssistPreferences;

import de.walware.docmlet.wikitext.ui.editors.WikitextEditorBuild;
import de.walware.docmlet.wikitext.ui.sourceediting.WikitextEditingSettings;


public class WikitextUIPreferenceInitializer extends AbstractPreferenceInitializer {
	
	
	public WikitextUIPreferenceInitializer() {
	}
	
	
	@Override
	public void initializeDefaultPreferences() {
		final IScopeContext scope= DefaultScope.INSTANCE;
		
		final IPreferenceStore store= WikitextUIPlugin.getInstance().getPreferenceStore();
		EditorsUI.useAnnotationsPreferencePage(store);
		EditorsUI.useQuickDiffPreferencePage(store);
		
		{	final AssistPreferences assistPrefs= WikitextEditingSettings.getAssistPrefences();
			PreferencesUtil.setPrefValue(scope, assistPrefs.getAutoActivationEnabledPref(), Boolean.TRUE);
			PreferencesUtil.setPrefValue(scope, assistPrefs.getAutoInsertSinglePref(), Boolean.FALSE);
			PreferencesUtil.setPrefValue(scope, assistPrefs.getAutoInsertPrefixPref(), Boolean.FALSE);
		}
		
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.FOLDING_ENABLED_PREF, Boolean.TRUE);
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.FOLDING_RESTORE_STATE_ENABLED_PREF, Boolean.TRUE);
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.MARKOCCURRENCES_ENABLED_PREF, Boolean.TRUE);
		
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.SMARTINSERT_BYDEFAULT_ENABLED_PREF, Boolean.TRUE);
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.SMARTINSERT_TAB_ACTION_PREF, TabAction.INSERT_INDENT_LEVEL);
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.SMARTINSERT_CLOSEBRACKETS_ENABLED_PREF, Boolean.TRUE);
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.SMARTINSERT_CLOSEPARENTHESIS_ENABLED_PREF, Boolean.TRUE);
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.SMARTINSERT_CLOSEMATHDOLLAR_ENABLED_PREF, Boolean.TRUE);
		PreferencesUtil.setPrefValue(scope, WikitextEditingSettings.SMARTINSERT_HARDWRAP_TEXT_ENABLED_PREF, Boolean.TRUE);
		
		PreferencesUtil.setPrefValue(scope, WikitextEditorBuild.PROBLEMCHECKING_ENABLED_PREF, Boolean.TRUE);
		
	}
	
}
