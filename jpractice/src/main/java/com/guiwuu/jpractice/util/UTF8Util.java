package com.guiwuu.jpractice.util;

/**
 * 参考：
 * 1.isUTF8(byte[] data)函数出处：http://longrenrex.blog.sohu.com/506699.html
 * 2.数据库、文件、WEB等各Java中文问题：http://home3k.blog.51cto.com/100217/14722
 * 3.javascript中三种urlencode函数：http://www.cnblogs.com/xgw2004058/archive/2010/01/11/1644193.html
 * 
 * @author diancang
 *
 */
public class UTF8Util {

    public static boolean isUTF8(byte[] data) {
        int count_good_utf = 0;
        int count_bad_utf = 0;
        byte current_byte = 0x00;
        byte previous_byte = 0x00;
        for (int i = 1; i < data.length; i++) {
            current_byte = data[i];
            previous_byte = data[i - 1];
            if ((current_byte & 0xC0) == 0x80) {
                if ((previous_byte & 0xC0) == 0xC0) {
                    count_good_utf++;
                } else if ((previous_byte & 0x80) == 0x00) {
                    count_bad_utf++;
                }
            } else if ((previous_byte & 0xC0) == 0xC0) {
                count_bad_utf++;
            }
        }
        if (count_good_utf > count_bad_utf) {
            return true;
        } else {
            return false;
        }
    }
}