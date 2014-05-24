package org.fredy.openhere.core;

public class ProcessLauncherException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ProcessLauncherException(String message) {
        super(message);
    }
    
    public ProcessLauncherException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ProcessLauncherException(Throwable cause) {
        super(cause);
    }
}
