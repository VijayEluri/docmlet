/*******************************************************************************
 * Copyright (c) 2012-2013 WalWare/StatET-Project (www.walware.de/goto/statet)
 * and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stephan Wahlbrink - initial API and implementation
 *******************************************************************************/

package de.walware.docmlet.tex.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;

import de.walware.ecommons.ltk.ui.sourceediting.ISourceEditor;
import de.walware.ecommons.ltk.ui.sourceediting.assist.AssistInvocationContext;
import de.walware.ecommons.ltk.ui.sourceediting.assist.AssistProposalCollector;
import de.walware.ecommons.ltk.ui.sourceediting.assist.IAssistCompletionProposal;
import de.walware.ecommons.ltk.ui.sourceediting.assist.IQuickAssistComputer;
import de.walware.ecommons.ltk.ui.sourceediting.assist.QuickAssistProcessor;

import de.walware.docmlet.tex.internal.ui.editors.LtxAssistInvocationContext;
import de.walware.docmlet.tex.internal.ui.editors.TexQuickRenameComputer;


public class LtxQuickAssistProcessor extends QuickAssistProcessor {
	
	
	private final IQuickAssistComputer fComputer = new TexQuickRenameComputer();
	
	
	public LtxQuickAssistProcessor(final ISourceEditor editor) {
		super(editor);
	}
	
	
	@Override
	protected AssistInvocationContext createContext(final IQuickAssistInvocationContext invocationContext,
			final IProgressMonitor monitor) {
		return new LtxAssistInvocationContext(getEditor(), invocationContext.getOffset(),
				true, monitor);
	}
	
	@Override
	protected void addModelAssistProposals(final AssistInvocationContext context,
			final AssistProposalCollector<IAssistCompletionProposal> proposals,
			final IProgressMonitor monitor) {
		fComputer.computeAssistProposals(context, proposals, monitor);
	}
	
	
}
