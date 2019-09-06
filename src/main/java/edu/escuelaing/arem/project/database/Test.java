package edu.escuelaing.arem.project.database;

import edu.escuelaing.arem.project.notation.Web;

/**
 * This is a class for testing
 * @author Juan Camilo Velandia Botello
 */
public class Test {
	
	/**
	 * @return a string indicating that this is the test class.
	 */
	@Web(url="test")
	public static String test() {
		String form="<form action=\"/apps/sum\">\r\n"
					+ "If you want to sum two values enter the numbers!!:<br>\r\n"
					+ "<input type=\"text\" name=\"numberA\" value=\"0\">\r\n"
					+ "<br>\r\n"
					+ "<input type=\"text\" name=\"numberB\" value=\"0\">\r\n"
					+ "<br>\r\n"
					+ "<input type=\"submit\" value=\"Submit\">"
					+"</form>\r\n";
		return "Hey!. This is the test class"+form;
	}

	/**
	 * @param numberA a number A of string type
	 * @param numberB a number B of string type
	 * @return the sum between number A and numberB
	 */
	@Web(url="sum")
	public static String sum(String numberA, String numberB){
		Integer result = Integer.parseInt(numberA)+Integer.parseInt(numberB);
		return "The result of the sum is : "+String.valueOf(result);
	}
	
}
