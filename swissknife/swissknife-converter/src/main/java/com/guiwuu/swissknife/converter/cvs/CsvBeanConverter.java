package com.guiwuu.swissknife.converter.cvs;


import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Convert cvs data to beans
 * <p/>
 * The mapping from cvs to bean is according to the first line in a csv file.
 *
 * @author daijun
 * @sine 2012-07-21
 */
public class CsvBeanConverter<E> {

    private File file;

    public CsvBeanConverter(String filename) {
        this.file = new File(filename);
    }

    public CsvBeanConverter(File file) {
        this.file = file;
    }

    public List<E> convert(Class<E> clazz) throws Exception {
        List<E> requests = new ArrayList<E>();
        String[] titles = null;
        for (Object line : FileUtils.readLines(file)) {
            String data = line.toString();
            if (data.startsWith("id")) {
                titles = data.split(",");
                continue;
            }
            E bean = clazz.newInstance();
            String[] datas = data.split(",", titles.length);
            for (int i = 0; i < titles.length; i++) {
                for (Method method : clazz.getMethods()) {
                    if (method.getName().equals("set" + capitalize(titles[i]))) {
                        Class type = method.getParameterTypes()[0];
                        String typeName = method.getParameterTypes()[0].getName();
                        if ("boolean".equals(typeName)) {
                            method.invoke(bean, Boolean.valueOf(datas[i]));
                        } else if ("int".equals(typeName)) {
                            method.invoke(bean, Integer.valueOf(datas[i]));
                        } else if ("long".equals(typeName)) {
                            method.invoke(bean, Long.valueOf(datas[i]));
                        } else if (String.class == type) {
                            String str = datas[i];
                            if (datas[i].indexOf(',') > -1) {
                                str = datas[i].substring(1, datas[i].length() - 1);
                            }
                            method.invoke(bean, str);
                        } else {
                            throw new UnsupportedOperationException("unsupported property class type: " + type);
                        }
                    }
                }
            }
            requests.add((E) bean);
        }
        return requests;
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0)
            return str;

        String firstChar = str.substring(0, 1);
        String restChars = str.substring(1, str.length());
        return firstChar.toUpperCase() + restChars;
    }

}
