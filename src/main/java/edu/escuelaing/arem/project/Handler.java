package edu.escuelaing.arem.project;

/**
 * This is an interface to execute a method.
 * @author Juan Camilo Velandia Botello
 */
public interface Handler {

	/**
	 * 	It executes a method
	 * @param params An array with parameters that will receive the method to be invoked
	 * @return The string value returned by the executed method
	 * @throws Exception
	 */
	public String process(String[] params) throws Exception;
	
}
