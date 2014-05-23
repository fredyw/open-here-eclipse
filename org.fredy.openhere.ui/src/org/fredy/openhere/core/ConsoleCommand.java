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
public class ConsoleCommand {
    private final String[] commands;
    
    public ConsoleCommand(String... commands) {
        this.commands = commands;
    }
    
    public String[] getCommands() {
        return commands;
    }
}
