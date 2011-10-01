package com.guiwuu.jpractice.util.security;

import java.math.BigDecimal;

import org.junit.Test;

public class DoubleTest {

    @Test
    public void testDoubleParse() {
        System.out.println("Test:");
        double d = Double.parseDouble("2.2250738585072012e-308");
        System.out.println("Value: " + d);
    }

    @Test
    public void testDoubleParseInCompilition() {
        //double dd = 2.2250738585072012e-308;
    }

    @Test
    public void testDoubleValueOf() {
        System.out.println("Test:");
        double d = Double.valueOf("2.2250738585072012e-308");
        System.out.println("Value: " + d);
    }

    @Test
    public void testBigDecimalValueOf() {
        System.out.println("Test:");
        BigDecimal b = new BigDecimal("2.2250738585072012e-308");
        System.out.println("Value: " + b);
        System.out.println("Double Value: " + b.doubleValue());
    }
}