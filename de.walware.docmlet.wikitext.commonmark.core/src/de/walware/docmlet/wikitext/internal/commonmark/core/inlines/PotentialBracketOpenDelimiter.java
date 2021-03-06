/*=============================================================================#
 # Copyright (c) 2015-2016 David Green and others.
 # All rights reserved. This program and the accompanying materials
 # are made available under the terms of the Eclipse Public License v1.0
 # which accompanies this distribution, and is available at
 # http://www.eclipse.org/legal/epl-v10.html
 # 
 # Contributors:
 #     David Green - initial API and implementation in Mylyn
 #     Stephan Wahlbrink (WalWare.de) - revised API and implementation
 #=============================================================================*/

package de.walware.docmlet.wikitext.internal.commonmark.core.inlines;

import org.eclipse.mylyn.wikitext.core.parser.DocumentBuilder;

import de.walware.docmlet.wikitext.internal.commonmark.core.CommonmarkLocator;
import de.walware.docmlet.wikitext.internal.commonmark.core.Line;
import de.walware.docmlet.wikitext.internal.commonmark.core.ProcessingContext;


public class PotentialBracketOpenDelimiter extends InlineWithText {
	
	
	private boolean isActive= true;
	
	
	public PotentialBracketOpenDelimiter(final Line line, final int offset, final int length,
			final String text) {
		super(line, offset, length, length, text);
	}
	
	
	boolean isImageDelimiter() {
		return this.text.charAt(0) == '!';
	}
	
	boolean isLinkDelimiter() {
		return this.text.charAt(0) == '[';
	}
	
	boolean isActive() {
		return this.isActive;
	}
	
	void setInactive() {
		this.isActive= false;
	}
	
	
	@Override
	public void emit(final ProcessingContext context,
			final CommonmarkLocator locator, final DocumentBuilder builder) {
		builder.characters(this.text);
	}
	
}
