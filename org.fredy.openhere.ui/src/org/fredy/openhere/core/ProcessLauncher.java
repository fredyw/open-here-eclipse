/*
 * Copyright (c) 2014 Fredy Wijaya
 * 
 * All rights reserved. This document and accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.fredy.openhere.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for launching a process.
 * 
 * @author fredy
 */
public class ProcessLauncher {
    private ProcessLauncher() {
    }
    
    private static String[] buildCommand(String command, String path) {
        String newCommand = substitute(command, path);
        List<String> matchList = new ArrayList<>();
        Pattern regex = Pattern.compile("[^\\s\"']+|(\"[^\"]*\")|('[^']*')");
        Matcher matcher = regex.matcher(newCommand);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                matchList.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                matchList.add(matcher.group(2));
            } else {
                matchList.add(matcher.group());
            }
        }
        return matchList.toArray(new String[0]);
    }
    
    private static String substitute(String str, String replacement) {
        return str.replaceAll("\\$\\{path\\}", Matcher.quoteReplacement(replacement));
    }
    
    /**
     * Launches a process.
     * 
     * @param command the process
     */
    public static boolean launch(String command, String path) {
        String[] cmd = buildCommand(command, path);
        ProcessBuilder pb = new ProcessBuilder(cmd);
        try {
            Process p = pb.start();
            return p.waitFor() == 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
} 
