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

package de.walware.docmlet.wikitext.internal.commonmark.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.walware.jcommons.collections.ImCollections;
import de.walware.jcommons.collections.ImList;


public class TextSegment {
	
	
	private static String computeText(final List<Line> lines) {
		final StringBuilder text= new StringBuilder(lines.size() * 32);
		
		final Iterator<Line> iter= lines.iterator();
		if (iter.hasNext()) {
			Line line= iter.next();
			while (iter.hasNext()) {
				text.append(line.getTextContent(false));
				text.append('\n');
				line= iter.next();
			}
			text.append(line.getTextContent(true));
		}
		
		return text.toString();
	}
	
	
	private final ImList<Line> lines;
	
	private final String text;
	
	
	public TextSegment(final ImList<Line> lines) {
		this.lines= lines;
		this.text= computeText(this.lines);
	}
	
	public TextSegment(final LineSequence lines) {
		final List<Line> list= new ArrayList<>();
		while (true) {
			final Line line= lines.getCurrentLine();
			if (line == null) {
				break;
			}
			list.add(line);
			lines.advance();
		}
		this.lines= ImCollections.toList(list);
		this.text= computeText(this.lines);
	}
	
	
	public String getText() {
		return this.text;
	}
	
	public List<Line> getLines() {
		return this.lines;
	}
	
	public int offsetOf(final int textOffset) {
		checkArgument(textOffset >= 0);
		int contentOffsetOfLine= 0;
		int remainder= textOffset;
		for (final Line line : this.lines) {
			contentOffsetOfLine= line.getTextContentOffset();
			final int linePlusSeparatorLength= line.getText().length() + 1;
			if (linePlusSeparatorLength > remainder) {
				break;
			}
			remainder-= linePlusSeparatorLength;
		}
		return contentOffsetOfLine + remainder;
	}
	
	public int toTextOffset(final int documentOffset) {
		int textOffset= 0;
		for (final Line line : this.lines) {
			final int lineRelativeOffset= documentOffset - line.getTextContentOffset();
			final int linePlusSeparatorLength= line.getText().length() + 1;
			if (lineRelativeOffset >= 0 && lineRelativeOffset < linePlusSeparatorLength) {
				return textOffset + lineRelativeOffset;
			}
			textOffset+= linePlusSeparatorLength;
		}
		throw new IllegalArgumentException();
	}
	
	public Line getLineAtOffset(final int textOffset) {
		final int documentOffset= offsetOf(textOffset);
		Line previous= null;
		for (final Line line : this.lines) {
			if (line.getOffset() > documentOffset) {
				break;
			}
			previous= line;
		}
		return checkNotNull(previous);
	}
	
	
	@Override
	public String toString() {
		return com.google.common.base.Objects.toStringHelper(TextSegment.class)
				.add("text", ToStringHelper.toStringValue(this.text))
				.toString();
	}
	
}
