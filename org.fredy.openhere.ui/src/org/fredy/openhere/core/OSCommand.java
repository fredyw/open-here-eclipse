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
 * @author fredy
 */
public class OSCommand {
    private final String consoleCommand;
    private final String fileBrowserCommand;
    
    public OSCommand(String consoleCommand, String fileBrowserCommand) {
        this.consoleCommand = consoleCommand;
        this.fileBrowserCommand = fileBrowserCommand;
    }
    
    public String getConsoleCommand() {
        return consoleCommand;
    }
    
    public String getFileBrowserCommand() {
        return fileBrowserCommand;
    }
}
