package com.guiwuu.swissknife.converter.cvs;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * CvsBeanConverterTest
 *
 * @author daijun
 * @sine 2012-07-21
 */
public class CvsBeanConverterTest {

    @Test
    public void testConvert() throws Exception {
        String filename = this.getClass().getResource("/OrderTestCase.csv").getFile();
        CsvBeanConverter<OrderTestCase> converter = new CsvBeanConverter<OrderTestCase>(filename);
        List<OrderTestCase> testCases = converter.convert(OrderTestCase.class);
        assertFalse(testCases.isEmpty());
    }
}
