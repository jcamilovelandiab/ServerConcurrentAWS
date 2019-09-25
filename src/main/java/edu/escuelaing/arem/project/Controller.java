package edu.escuelaing.arem.project;

import edu.escuelaing.arem.project.servers.AppServerConcurrent;
import edu.escuelaing.arem.project.threads.ThreadPool;

/**
 *	This is the App Controller that initializes a server, and then the server starts to listen on a port.
 */
public class Controller 
{

    public static void main( String[] args ) throws Exception {
        System.out.println( "Welcome to my App!" );
        AppServerConcurrent.init();
        ThreadPool.startPool();
    }
}
