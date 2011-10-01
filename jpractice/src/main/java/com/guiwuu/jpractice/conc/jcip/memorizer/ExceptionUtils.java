package com.guiwuu.jpractice.conc.jcip.memorizer;

/**
 *
 * @author jcip
 */
public class ExceptionUtils {

    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
