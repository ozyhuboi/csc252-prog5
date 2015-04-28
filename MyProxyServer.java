/*
 * Name: Yibo Zhou
 * CSC252 Project 5 Network Sockets 
 * Main class from which the proxy will be run
 * 
 * The skeleton:  
  Main routine {
     Parse the command line input (server port number);
     Create a server socket listening on the specified port;
     For each incoming client socket connection {
         Spawn a worker thread to handle the connection;
         Loop back to wait for/handle the next connection;
     }
 }
 	
 */

import java.net.*;
import java.io.*;

public class MyProxyServer {

	public static void main(String[] args) throws IOException {
		/*
		 * Some resources I referenced 
		http://www.tutorialspoint.com/java/java_networking.htm
		https://docs.oracle.com/javase/tutorial/networking/sockets/index.html
		http://www.jtmelton.com/2007/11/27/a-simple-multi-threaded-java-http-proxy-server/
		*/
		
		boolean hey_listen = true; // This never is set to false, but Java doesn't like while loops set to true 
		int port = 8080; //Default set at port 8080, chosen arbitrarily 
		ServerSocket listener = null;
		
		// Parse the command line input 
		try {
			port = Integer.parseInt(args[0]);
			System.out.println("Entered: " + port);
		} catch (Exception e) {
			// I guess just ignore it and use the default port 
		}
		
		//	Create a server socket listening on the specified port;
		try {
			listener = new ServerSocket(port);
			System.out.println("Listening on: " + port );
		} catch (IOException e) { // If something goes wrong 
			System.out.println("ERROR: Not possible to listen at " + port);
			System.exit(-1);
		}
		/*	For each incoming client socket connection {
				Spawn a worker thread to handle the connection;
				Loop back to wait for/handle the next connection;
			}
		*/
		
		while(hey_listen) { // Infinite loop, listen is always true 
			new MyWorkerThread(listener.accept()).start(); // Worker thread spawned and started
		}
		
		listener.close(); // Socket closed 
		
	}

}
