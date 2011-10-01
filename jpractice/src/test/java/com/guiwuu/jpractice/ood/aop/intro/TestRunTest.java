package com.guiwuu.jpractice.ood.aop.intro;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class TestRunTest {

    @Test
    public void testRunAop() {
        System.out.println("run aop");
        int expResult = 2;
        int result = RunAop.run(TestCase.class);
        assertEquals(expResult, result);
    }
}
