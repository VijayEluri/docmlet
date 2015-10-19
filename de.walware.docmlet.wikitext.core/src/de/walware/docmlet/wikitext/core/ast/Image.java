/*=============================================================================#
 # Copyright (c) 2009-2015 Stephan Wahlbrink (WalWare.de) and others.
 # All rights reserved. This program and the accompanying materials
 # are made available under the terms of the Eclipse Public License v1.0
 # which accompanies this distribution, and is available at
 # http://www.eclipse.org/legal/epl-v10.html
 # 
 # Contributors:
 #     Stephan Wahlbrink - initial API and implementation
 #=============================================================================*/

package de.walware.docmlet.wikitext.core.ast;

import java.lang.reflect.InvocationTargetException;

import de.walware.ecommons.ltk.ast.IAstNode;
import de.walware.ecommons.ltk.ast.ICommonAstVisitor;

import de.walware.docmlet.wikitext.core.ast.WikitextAst.NodeType;


public abstract class Image extends WikitextAstNode {
	
	
	public static final byte COMMON= 0;
	public static final byte SRC_BY_REF= 2;
	
	
	static final class Ref extends Image {
		
		
		private final Label referenceLabel;
		
		
		Ref(final WikitextAstNode parent, final int startOffset, final int stopOffset,
				final byte linkType, final Label referenceLabel) {
			super(parent, startOffset, stopOffset, linkType, null);
			
			if (referenceLabel == null) {
				throw new NullPointerException("referenceLabel");
			}
			referenceLabel.parent= this;
			this.referenceLabel= referenceLabel;
		}
		
		
		@Override
		public boolean hasChildren() {
			return true;
		}
		
		@Override
		public int getChildCount() {
			return 1;
		}
		
		@Override
		public Label getReferenceLabel() {
			return this.referenceLabel;
		}
		
		@Override
		public WikitextAstNode getChild(final int index) {
			if (index == 0) {
				return this.referenceLabel;
			}
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		
		@Override
		public int getChildIndex(final IAstNode element) {
			if (this.referenceLabel == element) {
				return 0;
			}
			return -1;
		}
		
		@Override
		public void acceptInChildren(final ICommonAstVisitor visitor) throws InvocationTargetException {
			this.referenceLabel.accept(visitor);
		}
		
		@Override
		public void acceptInWikitextChildren(final WikitextAstVisitor visitor) throws InvocationTargetException {
			this.referenceLabel.acceptInWikitext(visitor);
		}
		
	}
	
	static final class Common extends Image {
		
		
		Common(final WikitextAstNode parent, final int startOffset, final int stopOffset,
				final byte linkType, final String href) {
			super(parent, startOffset, stopOffset, linkType, href);
		}
		
		
		@Override
		public Label getReferenceLabel() {
			return null;
		}
		
	}
	
	
	private final byte linkType;
	
	private final String uri;
	
	
	private Image(final WikitextAstNode parent, final int startOffset, final int stopOffset,
			final byte linkType, final String uri) {
		super(parent, startOffset, stopOffset);
		
		this.linkType= linkType;
		this.uri= uri;
	}
	
	private Image(final WikitextAstNode parent, final int startOffset,
			final String uri) {
		super(parent, startOffset, startOffset);
		
		this.linkType= 0;
		this.uri= uri;
	}
	
	
	@Override
	public NodeType getNodeType() {
		return NodeType.LINK;
	}
	
	public byte getImageType() {
		return this.linkType;
	}
	
	public String getUri() {
		return this.uri;
	}
	
	public abstract Label getReferenceLabel();
	
	@Override
	public boolean hasChildren() {
		return false;
	}
	
	@Override
	public int getChildCount() {
		return 0;
	}
	
	@Override
	public WikitextAstNode getChild(final int index) {
		throw new IndexOutOfBoundsException();
	}
	
	
	@Override
	public int getChildIndex(final IAstNode element) {
		return -1;
	}
	
	@Override
	public void acceptInChildren(final ICommonAstVisitor visitor) throws InvocationTargetException {
	}
	
	@Override
	public void acceptInWikitext(final WikitextAstVisitor visitor) throws InvocationTargetException {
		visitor.visit(this);
	}
	
	@Override
	public void acceptInWikitextChildren(final WikitextAstVisitor visitor) throws InvocationTargetException {
	}
	
}