/*
 * Copyright (c) 2014 Fredy Wijaya
 * 
 * All rights reserved. This document and accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.fredy.openhere.ui.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.fredy.openhere.core.ProcessLauncher;

/**
 * This abstract class is responsible for launching a process in an editor
 * and a resource.
 * 
 * @author fredy
 */
public abstract class OpenHereHandler extends AbstractHandler {
    /**
     * Gets the command.
     * 
     * @return the command
     */
    protected abstract String getCommand();
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchPart workbenchPart = HandlerUtil.getActiveWorkbenchWindow(event)
            .getActivePage().getActivePart();
        if (workbenchPart instanceof ITextEditor) {
            ITextEditor textEditor = (ITextEditor) workbenchPart;
            File file = ResourceUtil.getFile(textEditor.getEditorInput())
                .getLocation().toFile();
            execCommand(getCommand(), getPath(file));
        } else {
            ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
            if (currentSelection instanceof IStructuredSelection) {
                IStructuredSelection structuredSelection = (IStructuredSelection) currentSelection;
                IResource resource = (IResource) structuredSelection.getFirstElement();
                if (resource != null) {
                    File file = resource.getLocation().toFile();
                    execCommand(getCommand(), getPath(file));
                }
            }
        }
        
        return null;
    }
    
    private String getPath(File file) {
        String path = file.getAbsolutePath();
        if (!file.isDirectory()) {
            path = file.getParentFile().getAbsolutePath();
        }
        return path;
    }
    
    private void execCommand(String command, String path) {
        // TODO: exception handling
        ProcessLauncher.launch(command, path);
    }
}
