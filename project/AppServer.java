package edu.escuelaing.arem.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import edu.escuelaing.arem.project.notation.Web;

public class AppServer {

	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	private static HashMap<String, Handler> listUrl = new HashMap<String, Handler>();
	
	public static void init() throws IOException {
		try {
			load("edu.escuelaing.arem.project.test.testClass");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void load(String classpath) throws ClassNotFoundException {
		Class<?> c = Class.forName(classpath);
		for(Method m : c.getMethods()){
            if(m.isAnnotationPresent(Web.class)){
            	Handler handler = new UrlHandler(m);
                listUrl.put(m.getAnnotation(Web.class).url(), handler);
            }
        }
	}
	
	public static void listen() throws IOException {
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(45000);
			System.out.println("Listening on port: 45000.");
		}catch(IOException e) {
			System.err.println("Could not listen on port: 45000.");
			System.exit(1);
		}
		while(true) {
			clientSocket = null;
			try {
				System.out.println("Ready to receive requests...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("\r\n");
            outputLine = "<!DOCTYPE html>" + "<html>" + "<head>" +
            			"<meta charset=\"UTF-8\">"
                    + "<title>Title of the document</title>\n" + 
            			"</head>" + "<body>" + "My Web Site" + "</body>"
                    + "</html>" + inputLine;
    		
            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
            //serverSocket.close(); The server is going to listen forever.
		}
	}
	
}
