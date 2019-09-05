package edu.escuelaing.arem.project.servers;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * This is the server class which has a ServerSocket and a port as attributes.
 * @author Juan Camilo Velandia Botello
 */
public class Server {

    public static ServerSocket serverSocket = null;
    public static int port;

    /**
     * @return A server that listens on a port.
     */
	public static ServerSocket startServer() {
		serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
            System.err.println("Listening on port: "+port+".");
        } catch (IOException e) {
            System.err.println("ERROR: Could not listen on port: "+port+".");
            System.exit(1);
        }
		return serverSocket;
	}
    
    /**
     * @return The port on which the server will listen.
     */
	public static int getPort() {
        if (System.getenv("PORT") != null) {
            return port=Integer.parseInt(System.getenv("PORT"));
        }
        return port=4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
	
}
