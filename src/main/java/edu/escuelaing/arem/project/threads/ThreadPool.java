package edu.escuelaing.arem.project.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import edu.escuelaing.arem.project.servers.Server;
import java.net.ServerSocket;
import java.net.Socket;
import edu.escuelaing.arem.project.servers.AppServerConcurrent;
import edu.escuelaing.arem.project.servers.Browser;

public class ThreadPool {
    public static ServerSocket serverSocket = Server.startServer();
    public static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void startPool(){
        while(true){
            Socket client = Browser.startBrowser(serverSocket);
            threadPool.execute(new AppServerConcurrent(client));        
        }
    }
}

    