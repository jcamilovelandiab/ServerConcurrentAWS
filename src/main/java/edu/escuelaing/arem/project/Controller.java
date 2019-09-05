package edu.escuelaing.arem.project;

import edu.escuelaing.arem.project.servers.AppServer;


/**
 *	This is the App Controller that initializes a server, and then the server starts to listen on a port.
 */
public class Controller 
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome to my App!" );
        try {
			AppServer.init();
			AppServer.listen();
		} catch (Exception e) {
			System.err.println(e.toString());
			System.out.println("ERROR: It has occurred an error!.");
		}
    }
}
