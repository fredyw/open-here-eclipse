/*
 * Copyright (c) 2014 Fredy Wijaya
 * 
 * All rights reserved. This document and accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.fredy.openhere.ui.handlers;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.fredy.openhere.ui.Activator;
import org.fredy.openhere.ui.preferences.OpenHerePreferencePage;
import org.osgi.service.prefs.Preferences;

/**
 * This handler is responsible for opening a console.
 * 
 * @author fredy
 */
public class OpenConsoleHandler extends OpenHereHandler {
    @Override
    protected String getCommand() {
        // TODO: exception handling when auto detection fails
        Preferences preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
        return preferences.get(OpenHerePreferencePage.CONSOLE_CMD, "");
    }
}
