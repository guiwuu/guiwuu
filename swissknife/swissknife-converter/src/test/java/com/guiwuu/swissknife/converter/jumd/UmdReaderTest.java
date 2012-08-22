package com.guiwuu.swissknife.converter.jumd;

import java.util.Arrays;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

/**
 * UmdParserTest
 *
 * @author daijun
 * @since 2011-02-12
 * @version 2011-02-22
 */
public class UmdReaderTest {

    private static final Logger log = Logger.getLogger(UmdReaderTest.class.getName());
    private FileFilter filter = new FileFilter() {

        @Override
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".umd");
        }
    };

    @Test
    public void testRead() throws Exception {
        File f = new File("D:\\tmp\\jumd\\test.umd");
        UmdReader umdReader = new UmdReader(f);
        Umd umd = umdReader.read();
        assertNotNull(umd);
    }

    @Test
    public void testBatchRead() throws Exception {
        File root = new File("E:\\doc\\book");
        int travelDeep = 1, maxDeep = 10;
        int umdNum = 0, maxNum = 10;
        LinkedList<File> a = new LinkedList<File>();
        LinkedList<File> b = new LinkedList<File>();
        a.add(root);
        do {
            if (a.isEmpty()) {
                a = b;
                b = new LinkedList<File>();
                travelDeep++;
            } else {
                File file = a.poll();
                if (file.isFile()) {
                    String msg = "[{0}]{1}th file: {2}";
                    Object[] params = {travelDeep, umdNum, file.getAbsolutePath()};
                    log.log(Level.WARNING, msg, params);
                    umdNum++;
                    UmdReader umdReader = new UmdReader(file);
                    Umd umd = umdReader.read();
                    assertNotNull(umd);
                } else if (file.isDirectory()) {
                    File[] list = file.listFiles(filter);
                    b.addAll(Arrays.asList(list));
                }
            }
        } while (travelDeep <= maxDeep && umdNum <= maxNum && (!a.isEmpty() || !b.isEmpty()));
    }
}
