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

package de.walware.docmlet.wikitext.internal.ui.editors;

import org.eclipse.osgi.util.NLS;


public class Messages extends NLS {
	
	
	public static String Hyperlinks_OpenDeclaration_label;
	public static String Hyperlinks_OpenMarkupLink_label;
	
	public static String Proposal_RenameInFile_label;
	public static String Proposal_RenameInFile_description;
	
	public static String CorrectLineWrap_task;
	
	
	static {
		NLS.initializeMessages(Messages.class.getName(), Messages.class);
	}
	private Messages() {}

}
