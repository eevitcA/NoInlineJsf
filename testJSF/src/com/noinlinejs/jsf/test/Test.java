package com.noinlinejs.jsf.test;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Test {
	private String value1;
	private String value2;
	
	public void test(){
		value1 = "1";
		value2 = "2";
				
		System.out.println("test");
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
}
