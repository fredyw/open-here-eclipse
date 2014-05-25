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
import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.texteditor.ITextEditor;
import org.fredy.openhere.core.ProcessLauncher;
import org.fredy.openhere.core.ProcessLauncherException;
import org.fredy.openhere.ui.Activator;

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
    
    /**
     * Gets the type.
     * 
     * @return the type
     */
    protected abstract String getType();
    
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchPart workbenchPart = HandlerUtil.getActiveWorkbenchWindow(event)
            .getActivePage().getActivePart();
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
        if (workbenchPart instanceof ITextEditor) {
            ITextEditor textEditor = (ITextEditor) workbenchPart;
            File file = ResourceUtil.getFile(textEditor.getEditorInput())
                .getLocation().toFile();
            launchProgram(getCommand(), getPath(file), window);
        } else {
            ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
            if (currentSelection instanceof IStructuredSelection) {
                IStructuredSelection structuredSelection = (IStructuredSelection) currentSelection;
                IResource resource = null;
                Object obj = structuredSelection.getFirstElement();
                if (obj instanceof IResource) {
                    resource = (IResource) obj;
                } else {
                    // a hackish way to support many different types of projects
                    try {
                        Method m = obj.getClass().getMethod("getResource");
                        resource = (IResource) m.invoke(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                if (resource != null) {
                    File file = resource.getLocation().toFile();
                    launchProgram(getCommand(), getPath(file), window);
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
    
    private void launchProgram(final String command, final String path,
        IWorkbenchWindow window) {
        if (command.isEmpty()) {
            MessageDialog.openInformation(window.getShell(),
                getType() + " Not Configured",
                "Please configure the executable for " + getType() +
                " in the Window -> Preferences -> Open Here");
            return;
        }
        
        Job job = new Job("Launching a " + getType()) {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    ProcessLauncher.launch(command, path);
                } catch (ProcessLauncherException e) {
                    return new Status(Status.ERROR, Activator.PLUGIN_ID,
                        "Error while launching a " + getType(), e);
                }
                return Status.OK_STATUS;
            }
        };
        job.schedule();
    }
}
