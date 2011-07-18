package com.guiwuu.jvm;

/**
 *
 * @author guiwuu
 */
public class Crash {

    public static void crash() {
        Object[] link = null;
        while (true) {
            link = new Object[]{link};
        }
    }
}
