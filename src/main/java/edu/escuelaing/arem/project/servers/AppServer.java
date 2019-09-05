package edu.escuelaing.arem.project.servers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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

import edu.escuelaing.arem.project.UrlHandler;
import edu.escuelaing.arem.project.notation.Web;
import edu.escuelaing.arem.project.Handler;;

/**
 * This is a class that has a client(browser) and a server
 * that listens constantly on a port, and it has list of urls.
 * @author Juan Camilo Velandia Botello
 */
public class AppServer {

	private static HashMap<String, Handler> listUrl = new HashMap<String, Handler>();
	private static Socket clientSocket = null;
	private static ServerSocket serverSocket = null;
	
	/**
	 * Initializes a server with two classes
	 * @throws IOException
	 */
	public static void init() throws IOException {
		try {
			load("edu.escuelaing.arem.project.database.Test");
			load("edu.escuelaing.arem.project.database.Hello");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Uploads a class to our url list
	 * @param classpath
	 * @throws ClassNotFoundException
	 */
	public static void load(String classpath) throws ClassNotFoundException {
		Class<?> c = Class.forName(classpath);
		for(Method m : c.getMethods()){
            if(m.isAnnotationPresent(Web.class)){
            	Handler handler = new UrlHandler(m);
                listUrl.put("/apps/"+m.getAnnotation(Web.class).url(), handler);
            }
        }
	}
	
	/**
	 * This method listens, receives, and processes the requests from the browser.
	 * @throws Exception
	 */
	public static void listen() throws Exception {

        while(true){
			
			serverSocket = Server.startServer();
            clientSocket = Browser.startBroswer(serverSocket);
            
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
				//System.err.println("RECEIVED:["+inputLine+"]");
            	if(inputLine.contains("GET")) {
					String address = inputLine.split(" ")[1];
            		if(address.contains("/apps/")) {
                    	sendAPP(address, out);
                    }else if(address.contains("/resources/")) {
						String resource = address.substring("/resources/".length());
    	                if (resource.contains(".html")) {
    	                	sendHTML(resource, out);
    	                }else if(resource.contains(".jpg")){
    	                	sendJPG(resource, clientSocket, out);
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
			serverSocket.close();
        }
    }
	
	/**
	 * This method sends the requested class to the browser.
	 * @param address this is the class' address that the browser wants.
	 * @param out this is the printwritter, and it sends information to the browser.
	 */
	public static void sendAPP(String address, PrintWriter out) {
		String[] params = null; String className;
		if(address.matches("[a-z/]+[?]{1}[a-zA-Z0-9=&]+")){
			String[] arr = address.split("\\?");
			className = arr[0];
			params = (arr[1].contains("&")) ? arr[1].split("&") : new String[]{arr[1]};
			for(int i=0; i<params.length; i++){
				params[i] = params[i].split("=")[1];
			}
		}else{
			className = address;
		}
		out.println("HTTP/1.1 200 OK\r");
        out.println("Content-Type: text/html\r");
        out.println("\r\n");	
        out.println(listUrl.get(className).process(params));
	}

	/**
	 * This method sends the requested html file to the browser.
	 * @param resource this is the file with html extension that the browser wants.
	 * @param out this is the printwritter, and it sends information to the browser.
	 */
	public static void sendHTML(String resource, PrintWriter out) {
		String urlDirectoryServer = System.getProperty("user.dir") + "/resources/html/" + resource;
		try {
            BufferedReader readerFile = new BufferedReader(new InputStreamReader(new FileInputStream(urlDirectoryServer), "UTF8"));
			String header = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
			out.println(header);
            while (readerFile.ready()) {
                out.println(readerFile.readLine());
			}
            readerFile.close();
        }catch (Exception e) {
            System.err.println("ERROR: Could not read the HTML file");
        }
	}
	
	/**
	 * This method sends the requested image to the browser.
	 * @param resource this is the image with jpg extension that the browser wants.
	 * @param clientSocket this is the brower's socket
	 * @param out this is the printwritter, and it sends information to the browser.
	 */
	public static void sendJPG(String resource, Socket clientSocket, PrintWriter out) {
    	try {
    		String urlDirectoryServer = System.getProperty("user.dir") + "/resources/jpg/" +resource;
			BufferedImage img = ImageIO.read(new File(urlDirectoryServer));

			ByteArrayOutputStream arrayOutputBytes = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", arrayOutputBytes);
			byte [] arrayBytes = arrayOutputBytes.toByteArray();
			DataOutputStream outImgBytes = new DataOutputStream(clientSocket.getOutputStream());

			outImgBytes.writeBytes("HTTP/1.1 200 OK \r\n");
			outImgBytes.writeBytes("Content-Type: image/jpg\r\n");
			outImgBytes.writeBytes("Content-Length: " + arrayBytes.length);
			outImgBytes.writeBytes("\r\n\r\n");
			outImgBytes.write(arrayBytes);
			outImgBytes.close();

			out.println(outImgBytes.toString());

    	}catch(Exception e) {
			System.err.println("ERROR: Could not read the JPG image");
    	}
	}
}