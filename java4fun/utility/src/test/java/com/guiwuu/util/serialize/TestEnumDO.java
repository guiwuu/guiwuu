package com.guiwuu.util.serialize;

import java.io.Serializable;

public class TestEnumDO implements Serializable{

	private static final long serialVersionUID = -2001873358459207742L;
	
	private TestEnum t;

	public TestEnum getT() {
		return t;
	}

	public void setT(TestEnum t) {
		this.t = t;
	}
}
