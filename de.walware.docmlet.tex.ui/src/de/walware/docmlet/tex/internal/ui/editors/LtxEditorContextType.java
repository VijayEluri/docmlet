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

import de.walware.ecommons.ltk.ui.templates.IWaTemplateContextTypeExtension1;
import de.walware.ecommons.ltk.ui.templates.SourceEditorContextType;


public class LtxEditorContextType extends SourceEditorContextType
		implements IWaTemplateContextTypeExtension1 {
	
	
/*[ Context Types ]============================================================*/
	
	public static final String LTX_DEFAULT_CONTEXT_TYPE_ID= "de.walware.docmlet.tex.templates.LtxEditorDefaultContextType"; //$NON-NLS-1$
	
	public static final String LTX_MATH_CONTEXT_TYPE_ID= "de.walware.docmlet.tex.templates.LtxEditorMathContextType"; //$NON-NLS-1$
	
	
	public LtxEditorContextType() {
		super();
	}
	
	
	@Override
	public void init() {
		addCommonVariables();
		addEditorVariables();
	}
	
	
}
