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

package de.walware.docmlet.tex.core.ast;

import static de.walware.docmlet.tex.core.ast.ITexAstStatusConstants.STATUS2_MATH_NOT_CLOSED;

import java.lang.reflect.InvocationTargetException;

import de.walware.ecommons.ltk.ast.IAstNode;
import de.walware.ecommons.ltk.ast.ICommonAstVisitor;

import de.walware.docmlet.tex.core.ast.TexAst.NodeType;


public class Math extends ContainerNode {
	
	
	static class SingleDollar extends Math {
		
		SingleDollar(final TexAstNode parent, final int beginOffset, final int endOffset) {
			super(parent, beginOffset, endOffset);
		}
		
		@Override
		public String getText() {
			return "$"; //$NON-NLS-1$
		}
		
	}
	
	static class DoubleDollar extends Math {
		
		DoubleDollar(final TexAstNode parent, final int beginOffset, final int endOffset) {
			super(parent, beginOffset, endOffset);
		}
		
		@Override
		public String getText() {
			return "$$"; //$NON-NLS-1$
		}
		
	}
	
	
	private Math(final TexAstNode parent, final int beginOffset, final int endOffset) {
		super(parent, beginOffset, endOffset);
	}
	
	
	@Override
	public NodeType getNodeType() {
		return NodeType.MATH;
	}
	
	@Override
	public final boolean hasChildren() {
		return (this.children.length > 0);
	}
	
	@Override
	public final int getChildCount() {
		return this.children.length;
	}
	
	@Override
	public final TexAstNode getChild(final int index) {
		return this.children[index];
	}
	
	@Override
	public final int getChildIndex(final IAstNode element) {
		for (int i= 0; i < this.children.length; i++) {
			if (this.children[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public final void acceptInChildren(final ICommonAstVisitor visitor) throws InvocationTargetException {
		for (final TexAstNode child : this.children) {
			visitor.visit(child);
		}
	}
	
	@Override
	public void acceptInTex(final TexAstVisitor visitor) throws InvocationTargetException {
		visitor.visit(this);
	}
	
	@Override
	public final void acceptInTexChildren(final TexAstVisitor visitor) throws InvocationTargetException {
		for (final TexAstNode child : this.children) {
			child.acceptInTex(visitor);
		}
	}
	
	
	@Override
	void setEndNode(final int endOffset, final TexAstNode endNode) {
		this.endOffset= endOffset;
	}
	
	@Override
	void setMissingEnd() {
		this.status= STATUS2_MATH_NOT_CLOSED;
		if (this.children.length > 0) {
			this.endOffset= this.children[this.children.length-1].endOffset;
		}
	}
	
}
