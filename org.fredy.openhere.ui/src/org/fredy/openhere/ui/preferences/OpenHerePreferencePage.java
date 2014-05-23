/*
 * Copyright (c) 2014 Fredy Wijaya
 * 
 * All rights reserved. This document and accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.fredy.openhere.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.fredy.openhere.ui.Activator;

/**
 * @author fredy
 */
public class OpenHerePreferencePage extends FieldEditorPreferencePage implements
    IWorkbenchPreferencePage {
    public static final String CONSOLE_CMD = "consoleCommand";
    public static final String FILE_BROWSER_CMD = "fileBrowserCommand";
    
    public OpenHerePreferencePage() {
        super(GRID);
    }
    
    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

    @Override
    protected void createFieldEditors() {
        addField(new StringFieldEditor("consoleCommand", "Console command",
            getFieldEditorParent()));
        addField(new StringFieldEditor("fileBrowserCommand", "File browser command",
            getFieldEditorParent()));
    }
}
