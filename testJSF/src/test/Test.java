package test;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Test {

	public void test(){
		System.out.println("test");
	}
}
