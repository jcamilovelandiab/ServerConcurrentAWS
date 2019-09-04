package edu.escuelaing.arem.project.database;

import edu.escuelaing.arem.project.notation.Web;

public class Hello {

	@Web(url="hello")
	public static String hello() {
		return "Hey!. This is the Hello class";
	}
	
	@Web(url="helloSr")
	public static String helloGivenName(String name){
		return "Welcome "+name+"!!! This is the Hello class";
	}

}
