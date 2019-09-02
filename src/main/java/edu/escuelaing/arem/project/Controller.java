package edu.escuelaing.arem.project;

import java.io.IOException;

import edu.escuelaing.arem.project.servers.AppServer;

/**
 * Hello world!
 *
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
			System.out.println("It has occurred an error!.");
		}
    }
}
