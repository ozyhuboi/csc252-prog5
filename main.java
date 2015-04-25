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
 
 Worker thread routine {
     Read the request line and header fields until two consecutive new lines;
     (Note that a new line can be a single "\n" or a character pair "\r\n".)
     Examine the first line (request line);
     If the request method is not "GET" {
         Return an error HTTP response with the status code "HTTP_BAD_METHOD";
     }
     Make TCP connection to the "real" Web server; 
     Send over an HTTP request;
     Receive the server's response;
     Close the TCP connection to the server;
     Send the server's response back to the client;
     Close the connection socket to the client.
 }
 	
 */

import java.net.*;
import java.io.*;

public class main {

	public static void main(String[] args) throws IOException {
		//  http://www.tutorialspoint.com/java/java_networking.htm
		// https://docs.oracle.com/javase/tutorial/networking/sockets/index.html
		
		// Parse the command line input 
		int port = Integer.parseInt(args[0]);
		
		/* 	Create a server socket listening on the specified port;
			For each incoming client socket connection {
				Spawn a worker thread to handle the connection;
				Loop back to wait for/handle the next connection;
			}
		*/
	}

}
