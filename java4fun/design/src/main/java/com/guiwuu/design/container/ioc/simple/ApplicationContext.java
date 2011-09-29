package com.guiwuu.design.container.ioc.simple;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.guiwuu.design.ood.dip.Copier;
import com.guiwuu.design.ood.dip.DisplayWriter;
import com.guiwuu.design.ood.dip.FileReader;
import com.guiwuu.design.ood.dip.KeyboardReader;
import com.guiwuu.design.ood.dip.PrinterWriter;

public class ApplicationContext {

    private static Map<String, Object> context = null;

    private ApplicationContext() {
    }

    synchronized public static Object getBean(String key) throws Exception {
        if (context == null) {
            context = new HashMap<String, Object>();

            context.put("keyboardReader", KeyboardReader.class.newInstance());
            context.put("fileReader", FileReader.class.newInstance());
            context.put("displayWriter", DisplayWriter.class.newInstance());
            context.put("printerWriter", PrinterWriter.class.newInstance());
            context.put("copier", Copier.class.newInstance());

            Map<String, String> params = new HashMap<String, String>();
            params.put("reader", "keyboardReader");
            params.put("writer", "displayWriter");
            Object bean = context.get("copier");
            for (String param : params.keySet()) {
                Method[] methods = bean.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    String formattedParam = param.substring(0, 1).toUpperCase() + param.substring(1, param.length());
                    if (method.getName().equals("set" + formattedParam)) {
                        method.invoke(bean, context.get(params.get(param)));
                    }
                }
            }
        }

        return context.get(key);
    }
}
