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

package de.walware.docmlet.wikitext.internal.commonmark.ui;

import org.eclipse.osgi.util.NLS;


public class Messages extends NLS {
	
	
	public static String MarkupConfig_BlankBeforeHeader_Enable_label;
	public static String MarkupConfig_BlankBeforeBlockquote_Enable_label;
	public static String MarkupConfig_StrikeoutByDTilde_Enable_label;
	public static String MarkupConfig_SuperscriptBySCircumflex_Enable_label;
	public static String MarkupConfig_SubscriptBySTilde_Enable_label;
	
	
	static {
		NLS.initializeMessages(Messages.class.getName(), Messages.class);
	}
	private Messages() {}
	
}
