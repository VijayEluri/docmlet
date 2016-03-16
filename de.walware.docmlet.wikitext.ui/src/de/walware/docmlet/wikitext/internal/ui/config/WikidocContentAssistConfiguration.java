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

package de.walware.docmlet.wikitext.internal.ui.config;

import org.eclipse.core.runtime.CoreException;

import de.walware.ecommons.ltk.ui.sourceediting.assist.AdvancedContentAssistConfigurationBlock;
import de.walware.ecommons.preferences.ui.ConfigurationBlock;
import de.walware.ecommons.preferences.ui.ConfigurationBlockPreferencePage;

import de.walware.docmlet.wikitext.internal.ui.WikitextUIPlugin;


public class WikidocContentAssistConfiguration extends ConfigurationBlockPreferencePage {
	
	
	public WikidocContentAssistConfiguration() {
	}
	
	
	@Override
	protected ConfigurationBlock createConfigurationBlock() throws CoreException {
		return new AdvancedContentAssistConfigurationBlock(
				WikitextUIPlugin.getInstance().getWikidocEditorContentAssistRegistry(),
				createStatusChangedListener());
	}
	
}
