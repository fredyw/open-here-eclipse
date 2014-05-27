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
 * This enum provides the list of commands in each operating sytem.
 * 
 * @author fredy
 */
public enum OSCommandFactory {
    LINUX(
        new FileBrowserCommand(
            "thunar ${path}", // xfce,
            "nautilus ${path}", // gnome
            "konqueror ${path}", // kde
            "caja ${path}", // mate
            "nemo ${path}", // cinnamon
            "pcmanfm ${path}" // lxde
        ),
        new ConsoleCommand(
            "exo-open --launch TerminalEmulator --working-directory=${path}", // xfce
            "gnome-terminal --working-directory=${path}", // gnome or cinnamon
            "konsole --workdir=${path}", // kde
            "mate-terminal --working-directory=${path}", // mate
            "lxterminal --working-directory=${path}" // lxde
        )
    ),
    
    WINDOWS(
        new FileBrowserCommand("explorer.exe \"${path}\""),
        new ConsoleCommand("cmd.exe /c start /d \"${path}\"")
    ),
    
    OSX(
        new FileBrowserCommand("open ${path}"),
        new ConsoleCommand("open -a /Applications/Utilities/Terminal.app ${path}")
    );
 
    private FileBrowserCommand fbc;
    private ConsoleCommand cc;
    
    private OSCommandFactory(FileBrowserCommand fbc, ConsoleCommand cc) {
        this.fbc = fbc;
        this.cc = cc;
    }
    
    public FileBrowserCommand getFileBrowserCommand() {
        return fbc;
    }
    
    public ConsoleCommand getConsoleCommand() {
        return cc;
    }
}
