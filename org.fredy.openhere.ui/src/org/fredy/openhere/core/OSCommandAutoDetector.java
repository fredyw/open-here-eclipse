/*
 * Copyright (c) 2014 Fredy Wijaya
 * 
 * All rights reserved. This document and accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.fredy.openhere.core;

/**
 * This class auto-detects the OS and returns an corresponding OSCommand.
 * 
 * @author fredy
 */
public class OSCommandAutoDetector {
    private static final String OS_NAME_PROP = "os.name";
    
    private OSCommandAutoDetector() {
    }
    
    /**
     * Auto detects the OS and returns an OSCommand.
     * 
     * @return the OSCommand
     */
    public static OSCommand detect() {
        if (System.getProperty(OS_NAME_PROP).toLowerCase().startsWith("win")) {
            return detectWindows();
        } else {
            // TODO: fix it later
            throw new RuntimeException("OS is not supported");
        }
    }
    
    private static OSCommand detectWindows() {
        String cc = OSCommandFactory.WINDOWS.getConsoleCommand().getCommands()[0];
        String fbc = OSCommandFactory.WINDOWS.getFileBrowserCommand().getCommands()[0];
        
        return new OSCommand(cc, fbc);
    }
    
    private static OSCommand detectLinux() {
        // TODO: to be implemented
        return null;
    }
    
    private static OSCommand detectOSX() {
        // TODO: to be implemented
        return null;
    }
}
