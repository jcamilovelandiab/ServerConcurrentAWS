package edu.escuelaing.arem.project.database;

import edu.escuelaing.arem.project.notation.Web;

public class Test {
	
	@Web(url="test")
	public static String test() {
		return "Hey!. This is the test class";
	}

	@Web(url="sum")
	public static String sum(String numberA, String numberB){
		Integer result = Integer.parseInt(numberA)+Integer.parseInt(numberB);
		return "The result of the sum is : "+String.valueOf(result);
	}
	
}
