package edu.gatech.ruddha.util;

/**
 * Created by Sanath on 2/8/2018.
 */

public class TooManyAttemptsException extends RuntimeException {
    public TooManyAttemptsException() {
        super("Too many attempts to log in by user");
    }
}
