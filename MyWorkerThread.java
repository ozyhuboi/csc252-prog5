/*
 * MyWorkerThread consists of the main thread of functions used and called back in the main 
 * 
 * Worker thread routine {
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
import java.util.*;

public class MyWorkerThread extends Thread { // This essentially allows each new call to be a new child thread 
	private Socket sock = null;
	
	public MyWorkerThread (Socket sock) throws IOException {
		this.sock = sock; 
	}
		/*
		Read the request line and header fields until two consecutive new lines;
	     (Note that a new line can be a single "\n" or a character pair "\r\n".)
	     Examine the first line (request line);
	     If the request method is not "GET" {
	         Return an error HTTP response with the status code "HTTP_BAD_METHOD";
	     }
	     */
	public void doit() { 
		try {
			// Gets info from socket
			DataOutputStream out = new DataOutputStream(sock.getOutputStream()); 
			System.out.println(out);
			// 
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream())); 
			System.out.println(in);
			
			String input, output, url;
			int count = 0;
			
			while( (input = in.readLine()) != null) {
				StringTokenizer tk = new StringTokenizer(input);
				tk.nextToken();
				
				if (count == 0) {
					String[] tokens = input.split(" ");
					url = tokens [1];
					System.out.println("Request to get: " + url );
				}
				
				count++;
			}
		} catch (IOException e ) {
			System.out.println("Well that was dissappointing");
		}
	}
	

}
