package edu.escuelaing.arem.project.servers;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

	public static ServerSocket serverSocket = null;
	
	public static ServerSocket startServer() {
		serverSocket = null;
		int port = getPort();
        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Listening on port: "+port+".");
        } catch (IOException e) {
            System.err.println("ERROR: Could not listen on port: "+port+".");
            System.exit(1);
        }
		return serverSocket;
	}
	
	public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
	
}
