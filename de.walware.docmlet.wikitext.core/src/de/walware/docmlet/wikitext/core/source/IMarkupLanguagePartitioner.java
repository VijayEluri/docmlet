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

package de.walware.docmlet.wikitext.core.source;

import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IDocumentPartitionerExtension3;

import de.walware.docmlet.wikitext.core.markup.IMarkupLanguage;


public interface IMarkupLanguagePartitioner extends IDocumentPartitioner, IDocumentPartitionerExtension3 {
	
	
	IMarkupLanguage getMarkupLanguage();
	
	void setMarkupLanguage(IMarkupLanguage language);
	
	void reset(); // for changed configuration
	
}