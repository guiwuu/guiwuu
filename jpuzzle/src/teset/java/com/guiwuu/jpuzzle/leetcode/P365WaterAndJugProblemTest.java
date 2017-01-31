package com.guiwuu.jpuzzle.leetcode;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author guiwuu
 * @since 2017-01-31
 *
 */
public class P365WaterAndJugProblemTest {

    @Test
    public void test() {
        P365WaterAndJugProblem p = new P365WaterAndJugProblem();
        assertTrue(p.canMeasureWater(3,5,4));
        assertFalse(p.canMeasureWater(2,6,5));
        assertTrue(p.canMeasureWater(5,34,6));
        assertFalse(p.canMeasureWater(104693,104701,324244));
    }
}
