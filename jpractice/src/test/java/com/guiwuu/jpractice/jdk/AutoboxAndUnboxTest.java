package com.guiwuu.jpractice.jdk;

import static org.junit.Assert.*;

import org.junit.Test;

public class AutoboxAndUnboxTest {
	
	@Test
	public void testAssignment() throws Exception {
		int i = 1;
		Integer a = new Integer(1);
		Integer b = new Integer(1);
		assertTrue(a == 1);
		assertTrue(b == 1);
		assertTrue(a == i);
		assertFalse(a == b);
	}
	
	@Test
	public void testArithmetic() throws Exception {
		
	}
	
	@Test
	public void testOther() throws Exception {
		
	}

}
