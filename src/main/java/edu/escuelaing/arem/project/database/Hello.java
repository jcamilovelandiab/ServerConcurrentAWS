package edu.escuelaing.arem.project.database;

import edu.escuelaing.arem.project.notation.Web;

/**
 * This is a class for testing
 * @author Juan Camilo Velandia Botello
 */
public class Hello {

	/**
	 * @return a string indicating that this is the hello class.
	 */
	@Web(url="hello")
	public static String hello() {
		return "Hey!. This is the Hello class";
	}
	
	/**
	 * @param name the client's name
	 * @return a string saying "Welcome" to the client.
	 */
	@Web(url="hellofriend")
	public static String hello(String name){
		return "Welcome "+name+"!!!. This is the Hello class";
	}

}
