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
		String form="<form action=\"/apps/hellofriend\">\r\n"
					+ "Enter your name:<br>\r\n"
					+ "<input type=\"text\" name=\"name\" value=\"camilo\">\r\n"
					+ "<br>\r\n"
					+ "<input type=\"submit\" value=\"Submit\">"
					+"</form>\r\n";
		return "Hey!. This is the Hello class\r\n"+form;
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
