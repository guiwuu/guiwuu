package com.guiwuu.jpractice.conc.synchronize;

/**
 * Application configuration with invisible properties
 *
 * @author diancang
 * @since 2012-05-26
 */
public class InvisibleAppConfig {
    public int i = 0;
    public Integer j = new Integer(0);

    public void update() {
        i++;
        j++;
    }
}
