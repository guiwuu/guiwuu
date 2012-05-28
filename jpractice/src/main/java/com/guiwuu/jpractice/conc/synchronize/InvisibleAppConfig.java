package com.guiwuu.jpractice.conc.synchronize;

/**
 * Application configuration with invisible properties
 *
 * @author diancang
 * @since 2012-05-26
 */
public class InvisibleAppConfig implements AppConfig{
    public int i = 0;
    public Integer j =new  Integer(0);
    private boolean updated = false;

    @Override
    public void update() {
        i++;
        j++;
        updated = true;
    }

    @Override
    public boolean isUpdated() {
        return updated;
    }
}
