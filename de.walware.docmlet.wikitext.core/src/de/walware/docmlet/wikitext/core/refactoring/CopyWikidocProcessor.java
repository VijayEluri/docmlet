/*=============================================================================#
 # Copyright (c) 2012-2016 Stephan Wahlbrink (WalWare.de) and others.
 # All rights reserved. This program and the accompanying materials
 # are made available under the terms of the Eclipse Public License v1.0
 # which accompanies this distribution, and is available at
 # http://www.eclipse.org/legal/epl-v10.html
 # 
 # Contributors:
 #     Stephan Wahlbrink - initial API and implementation
 #=============================================================================*/

package de.walware.docmlet.wikitext.core.refactoring;

import de.walware.ecommons.ltk.core.ElementSet;
import de.walware.ecommons.ltk.core.refactoring.CommonCopyProcessor;
import de.walware.ecommons.ltk.core.refactoring.RefactoringAdapter;
import de.walware.ecommons.ltk.core.refactoring.RefactoringDestination;


public class CopyWikidocProcessor extends CommonCopyProcessor {
	
	
	public CopyWikidocProcessor(final ElementSet elementsToCopy,
			final RefactoringDestination destination, final RefactoringAdapter adapter) {
		super(elementsToCopy, destination, adapter);
	}
	
	
	@Override
	public String getIdentifier() {
		return WikitextRefactoring.COPY_WIKIDOC_ELEMENTS_PROCESSOR_ID;
	}
	
	@Override
	protected String getRefactoringIdentifier() {
		return WikitextRefactoring.COPY_WIKIDOC_ELEMENTS_REFACTORING_ID;
	}
	
}
