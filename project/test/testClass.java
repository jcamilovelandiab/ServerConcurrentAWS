package edu.escuelaing.arem.project.test;

import edu.escuelaing.arem.project.notation.Web;

public class testClass {
	
	@Web(url="testClass")
	public static String hello() {
		return "Hello World!";
	}
	
}
