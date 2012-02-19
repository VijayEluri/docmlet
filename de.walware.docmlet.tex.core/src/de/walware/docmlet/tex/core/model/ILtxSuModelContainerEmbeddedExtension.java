/*******************************************************************************
 * Copyright (c) 2011-2012 WalWare/StatET-Project (www.walware.de/goto/statet)
 * and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stephan Wahlbrink - initial API and implementation
 *******************************************************************************/

package de.walware.docmlet.tex.core.model;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import de.walware.ecommons.ltk.IProblemRequestor;
import de.walware.ecommons.ltk.SourceContent;
import de.walware.ecommons.ltk.SourceContentLines;

import de.walware.docmlet.tex.core.ast.Embedded;


public interface ILtxSuModelContainerEmbeddedExtension {
	
	
	String getNowebType();
	
	void reconcileEmbeddedAst(SourceContent content, List<Embedded> list,
			int level, IProgressMonitor monitor);
	
	void reconcileEmbeddedModel(SourceContent content, ILtxModelInfo texModel,
			List<EmbeddedReconcileItem> list,
			int level, IProgressMonitor monitor);
	
	void reportEmbeddedProblems(SourceContentLines content, ILtxModelInfo texModel,
			IProblemRequestor problemRequestor,
			int level, IProgressMonitor monitor);
	
}