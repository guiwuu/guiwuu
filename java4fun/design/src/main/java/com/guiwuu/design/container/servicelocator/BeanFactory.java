package com.guiwuu.design.container.servicelocator;

import java.util.Properties;

public class BeanFactory {

    private static Properties config = null;

    @SuppressWarnings("unchecked")
    synchronized public static Object createBean(String key) throws Exception {
        if (config == null) {
            config = new Properties();
            config.setProperty("keyboardReader", "com.guiwuu.ood.dip.KeyboardReader");
            config.setProperty("fileReader", "com.guiwuu.ood.dip.FileReader");
            config.setProperty("displayWriter", "com.guiwuu.ood.dip.DisplayWriter");
            config.setProperty("printerWriter", "com.guiwuu.ood.dip.PrinterWriter");
        }

        Class clazz = Class.forName(config.getProperty(key));
        return clazz.newInstance();
    }
}
