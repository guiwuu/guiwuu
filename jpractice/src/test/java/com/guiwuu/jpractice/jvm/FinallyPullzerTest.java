package com.guiwuu.jpractice.jvm;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class FinallyPullzerTest {

    @Test
    public void test() {
        assertEquals(1, FinallyPuzller.returnDirectly());
        assertEquals(1, FinallyPuzller.returnBeforeFinally());
    }
}
