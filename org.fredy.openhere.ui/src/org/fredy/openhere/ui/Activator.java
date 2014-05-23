/*
 * Copyright (c) 2014 Fredy Wijaya
 * 
 * All rights reserved. This document and accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.fredy.openhere.ui;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.fredy.openhere.core.OSCommand;
import org.fredy.openhere.core.OSCommandAutoDetector;
import org.fredy.openhere.ui.preferences.OpenHerePreferencePage;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.Preferences;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author fredy
 */
public class Activator extends AbstractUIPlugin {
	// The plug-in ID
	public static final String PLUGIN_ID = "org.fredy.openhere.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// TODO: exception handling
		OSCommand osCmd = OSCommandAutoDetector.detect();
		Preferences preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
        String cc = preferences.get(OpenHerePreferencePage.CONSOLE_CMD, "");
        if (cc.isEmpty()) {
            cc = osCmd.getConsoleCommand().getCommand();
            preferences.put(OpenHerePreferencePage.CONSOLE_CMD, cc);
        }
        
        String fbc = preferences.get(OpenHerePreferencePage.FILE_BROWSER_CMD, "");
        if (fbc.isEmpty()) {
            fbc = osCmd.getFileBrowserCommand().getCommand();
            preferences.put(OpenHerePreferencePage.FILE_BROWSER_CMD, fbc);
        }
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
