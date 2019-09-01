package edu.escuelaing.arem.project;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.imageio.ImageIO;

import edu.escuelaing.arem.project.notation.Web;


public class AppServer {

	private static HashMap<String, Handler> listUrl = new HashMap<String, Handler>();
	private static Socket clientSocket = null;
	private static ServerSocket serverSocket = null;
	
	
	public static void init() throws IOException {
		try {
			load("edu.escuelaing.arem.project.database.Test");
			load("edu.escuelaing.arem.project.database.Hello");
			System.out.println(listUrl.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void load(String classpath) throws ClassNotFoundException {
		Class<?> c = Class.forName(classpath);
		for(Method m : c.getMethods()){
            if(m.isAnnotationPresent(Web.class)){
            	Handler handler = new UrlHandler(m);
                listUrl.put("/apps/"+m.getAnnotation(Web.class).url(), handler);
            }
        }
	}
	
	public static void listen() throws IOException {
		serverSocket = null;
		int port = getPort();
        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Listening on port: "+port+".");
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+port+".");
            System.exit(1);
        }
 
        while(true){
        	
            clientSocket = null;
            try {
                System.out.println("Ready to receive ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
            	//System.err.println("INPUTLINE:["+inputLine +"]");
            	if(inputLine.contains("HTTP")) {
            		if(inputLine.contains("/apps/")) {
                    	sendAPP(inputLine, out);
                    }else if(inputLine.contains("/resources/")) {
    	                if (inputLine.contains(".html")) {
    	                	sendHTML(inputLine, out);
    	                }else if(inputLine.contains(".jpg")){
    	                	sendJPG(inputLine, clientSocket, out);
    	                }
                    }
            	}
                if (!in.ready()) {
                    break;
                }
            }
            in.close();
            out.close();
            clientSocket.close();
        }
        //serverSocket.close();
    }
	
	public static void sendAPP(String inputLine, PrintWriter out) {
		int begin = inputLine.indexOf("/apps/");
    	int end = inputLine.indexOf("HTTP")-1;
    	System.err.println("BEGIN: "+begin+" , END: "+end);
    	String resource = inputLine.substring(begin, end);
    	out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("\r\n");
        //System.err.println("What: " + resource);
        out.println(listUrl.get(resource).process());
	}
	
	public static void sendHTML(String inputLine, PrintWriter out) {
		int end = inputLine.indexOf(".html");
    	int begin = end-1;
    	while(begin>=0 && inputLine.charAt(begin)!='/') {
    		begin--;
    	}
    	String urlInputLine = inputLine.substring(begin+1,end+5);
        String urlDirectoryServer = System.getProperty("user.dir") + "\\resources\\html\\" + urlInputLine;
        try {
            BufferedReader readerFile = new BufferedReader(new InputStreamReader(new FileInputStream(urlDirectoryServer), "UTF8"));
            
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("\r\n");
            while (readerFile.ready()) {
                out.println(readerFile.readLine());
            }
            readerFile.close();
        }catch (Exception e) {
            //out.println("HTTP/2.0 404 Not found.");
            //out.println("Content-Type: text/html");
            //out.println("\r\n");
        }
	}
	
	public static void sendJPG(String inputLine, Socket clientSocket, PrintWriter out) {
		int end = inputLine.indexOf(".jpg");
    	int begin = end-1;
    	while(begin>=0 && inputLine.charAt(begin--)!='/');
    	String urlInputLine = inputLine.substring(begin+1,end+4);
    	try {
    		String urlDirectoryServer = System.getProperty("user.dir") + "\\resources\\jpg\\" +urlInputLine;
            BufferedImage img = ImageIO.read(new File(urlDirectoryServer));
            out.println("HTTP/1.1 200 OK");
            out.write("Content-Type: image/webp,*/*");
            out.println("\r\n");
            ImageIO.write(img, "jpg",clientSocket.getOutputStream());
    	}catch(Exception e) {
    		//System.err.println("I CANT READ "+inputLine);
    	}
	}
	
	public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

}
