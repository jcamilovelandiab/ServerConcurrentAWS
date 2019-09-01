package edu.escuelaing.arem.project.database;

import edu.escuelaing.arem.project.notation.Web;

public class Hello {

	@Web(url="hello")
	public static String test() {
		return "Hey!. This is the Hello class";
	}
	
}
