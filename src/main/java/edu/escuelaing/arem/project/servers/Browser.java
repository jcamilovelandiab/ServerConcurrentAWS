package edu.escuelaing.arem.project.servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the client(browser) class which has a socket as attribute.
 * @author Juan Camilo Velandia Botello
 */
public class Browser {

	public static Socket browser;
	
	/**
	 * @param server The server which will connect to the client(browser)
	 * @return A socket (the browser).
	 */
	public static Socket startBroswer(ServerSocket server) {
		browser = null;
	    try {
	        System.out.println("Ready to receive ...");
	        browser = server.accept();
	    } catch (IOException e) {
	        System.err.println("ERROR: Accept failed.");
	        System.exit(1);
	    }
		return browser;
	}
	
}
