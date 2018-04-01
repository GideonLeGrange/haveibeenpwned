package me.legrange.haveibeenpwned;

/**
 * Exception thrown by the HaveIBeenPwned API. 
 * @author gideon
 */
public class HaveIBeenPwndException extends Exception {

    public HaveIBeenPwndException(String message) {
        super(message);
    }

    public HaveIBeenPwndException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
