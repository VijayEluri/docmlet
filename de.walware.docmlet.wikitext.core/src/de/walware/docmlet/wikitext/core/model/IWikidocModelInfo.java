/*=============================================================================#
 # Copyright (c) 2011-2016 Stephan Wahlbrink (WalWare.de) and others.
 # All rights reserved. This program and the accompanying materials
 # are made available under the terms of the Eclipse Public License v1.0
 # which accompanies this distribution, and is available at
 # http://www.eclipse.org/legal/epl-v10.html
 # 
 # Contributors:
 #     Stephan Wahlbrink - initial API and implementation
 #=============================================================================*/

package de.walware.docmlet.wikitext.core.model;

import de.walware.ecommons.ltk.core.model.INameAccessSet;
import de.walware.ecommons.ltk.core.model.ISourceUnitModelInfo;

import de.walware.docmlet.wikitext.core.ast.WikitextAstInfo;


public interface IWikidocModelInfo extends ISourceUnitModelInfo {
	
	
	@Override
	WikitextAstInfo getAst();
	
	INameAccessSet<WikitextNameAccess> getLinkAnchorLabels();
	INameAccessSet<WikitextNameAccess> getLinkRefLabels();
	int getMinSectionLevel();
	int getMaxSectionLevel();
	
}
