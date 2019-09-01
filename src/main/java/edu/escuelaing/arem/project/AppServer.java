package edu.escuelaing.arem.project;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            	System.out.println(m.getAnnotation(Web.class).url());
                listUrl.put("/apps/"+m.getAnnotation(Web.class).url(), handler);
            }
        }
	}
	
	public static void listen() throws IOException {
		ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 80.");
            System.exit(1);
        }
        while(true){
            Socket clientSocket = null;
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
                int begin,end;
                String urlInputLine;
                if(inputLine.contains("/apps/")) {
                	begin = inputLine.indexOf("/apps/");
                	end = inputLine.indexOf("HTTP")-1;
                	
                	String resource = inputLine.substring(begin, end);
                	out.println("HTTP/2.0 200 OK");
                    out.println("Content-Type: text/html");
                    out.println("\r\n");
                    //System.err.println("What: " + resource);
                    out.println(listUrl.get(resource).process());
                	
                }else if(inputLine.contains("resources")) {
	                if (inputLine.contains(".html")) {
	                	end = inputLine.indexOf(".html");
	                	begin = end-1;
	                	while(begin>=0 && inputLine.charAt(begin)!='/') {
	                		begin--;
	                	}
	                	urlInputLine = inputLine.substring(begin+1,end+5);
	                    String urlDirectoryServer = System.getProperty("user.dir") + "\\resources\\html\\" + urlInputLine;
	                    try {
	                        BufferedReader readerFile = new BufferedReader(new InputStreamReader(new FileInputStream(urlDirectoryServer), "UTF8"));
	                        out.println("HTTP/2.0 200 OK");
	                        out.println("Content-Type: text/html");
	                        out.println("\r\n");
	                        while (readerFile.ready()) {
	                            out.println(readerFile.readLine());
	                        }
	                    }catch (FileNotFoundException e) {
	                        //out.println("HTTP/2.0 404 Not found.");
	                        //out.println("Content-Type: text/html");
	                        //out.println("\r\n");
	                    }
	                }else if(inputLine.contains(".jpg")){
	                	end = inputLine.indexOf(".jpg");
	                	begin = end-1;
	                	while(begin>=0 && inputLine.charAt(begin--)!='/');
	                	urlInputLine = inputLine.substring(begin+1,end+4);
	                	try {
	                		String urlDirectoryServer = System.getProperty("user.dir") + "\\resources\\jpg\\" +urlInputLine;
	                        BufferedImage img = ImageIO.read(new File(urlDirectoryServer));
	                        out.println("HTTP/2.0 200 OK");
	                        out.write("Content-Type: image/webp,*/*");
	                        out.println("\r\n");
	                        ImageIO.write(img, "jpg",clientSocket.getOutputStream());
	                	}catch(Exception e) {
	                		//System.err.println("I CANT READ "+inputLine);
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
	
}
