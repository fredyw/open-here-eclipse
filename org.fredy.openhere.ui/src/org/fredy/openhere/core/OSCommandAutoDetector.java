/*
 * Copyright (c) 2014 Fredy Wijaya
 * 
 * All rights reserved. This document and accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.fredy.openhere.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
     * @return the OSCommand or null if the auto detection fails
     */
    public static OSCommand detect() {
        if (System.getProperty(OS_NAME_PROP).toLowerCase().startsWith("win")) {
            return detectWindows();
        } else if (System.getProperty(OS_NAME_PROP).toLowerCase().startsWith("linux")) {
            return detectLinux();
        } else if (System.getProperty(OS_NAME_PROP).toLowerCase().startsWith("mac")) {
            return detectOSX();
        } else {
            return null;
        }
    }
    
    private static OSCommand detectWindows() {
        String cc = OSCommandFactory.WINDOWS.getConsoleCommand().getCommands()[0];
        String fbc = OSCommandFactory.WINDOWS.getFileBrowserCommand().getCommands()[0];
        
        return new OSCommand(cc, fbc);
    }
    
    private static OSCommand detectLinux() {
        String cc = "";
        for (String cmd : OSCommandFactory.LINUX.getConsoleCommand().getCommands()) {
            String output = execCommand("which", cmd.split("\\s+")[0]);
            if (!output.isEmpty()) {
                cc = cmd;
                break;
            }
        }
        
        String fbc = "";
        for (String cmd : OSCommandFactory.LINUX.getFileBrowserCommand().getCommands()) {
            String output = execCommand("which", cmd.split("\\s+")[0]);
            if (!output.isEmpty()) {
                fbc = cmd;
                break;
            }
        }
        return new OSCommand(cc, fbc);
    }
    
    private static OSCommand detectOSX() {
        String cc = OSCommandFactory.OSX.getConsoleCommand().getCommands()[0];
        String fbc = OSCommandFactory.OSX.getFileBrowserCommand().getCommands()[0];
        
        return new OSCommand(cc, fbc);
    }
    
    private static class StreamGobbler extends Thread {
        private InputStream is;
        private StringBuilder output = new StringBuilder();
        
        public StreamGobbler(InputStream is) {
            this.is = is;
        }
        
        public String getOutput() {
            return output.toString();
        }
        
        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line = br.readLine();
                output.append(line);
                while ((line = br.readLine()) != null) {
                    output.append(line);
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private static String execCommand(String... command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        Process p;
        try {
            p = pb.start();
            StreamGobbler sb = new StreamGobbler(p.getInputStream());
            sb.start();
            p.waitFor();
            sb.join();
            return sb.getOutput();
        } catch (Exception e) {
            // ignore the exception
        }
        return "";
    }
}
