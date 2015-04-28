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
	private static final int BUFFER_SIZE = 32768;
	
	public MyWorkerThread (Socket sock) throws IOException {
		super("MyWorkerThread");
		this.sock = sock; 
	}
		
	public void run() {  // Running method that does the work 
		
		/*
		Read the request line and header fields until two consecutive new lines;
	     (Note that a new line can be a single "\n" or a character pair "\r\n".)
	     Examine the first line (request line);
	     If the request method is not "GET" {
	         Return an error HTTP response with the status code "HTTP_BAD_METHOD";
	     }
	     */
		// Begin Parsing 
		try {
			String input, urlString = null;
			String version = "HTTP/1.1"; // Required version is HTTP/1.1 
			boolean prev = false;
			boolean found_get = false; // False if get not found, True if found
			boolean found_url = false; // False if url not found, True if found
			boolean found_version = false; // False if version not found, True if found
			
			Scanner in = new Scanner(sock.getInputStream());
			
				// Begin Parsing loop 
				while (  !((input = in.nextLine()).isEmpty()) || !prev  ) { 
					
					
					String[] tokens = input.split(" ");				
					
					if ( tokens.length > 2) { // All three values are in a single line
						if (!tokens[0].equalsIgnoreCase("GET") ) {
							System.out.println("HTTP_BAD_METHOD");
											
						} else {
							found_get = true;
							
						}
						if ( !tokens[1].startsWith("http://") ) { // Checks if protocol is good 
							System.out.println("Bad URL: Bad protocol"); 
							
						} else {
							urlString = tokens[1];
							found_url = true;
						}
						if ( !version.equalsIgnoreCase(tokens[2]) ) { // Checks if version is good 
							System.out.println("Bad Version" );
							
						} else {
							found_version = true; 
						}
					}
					
					if (  tokens.length == 2 ) { // Two tokens in the line 
						if (!found_get) { // If GET has not been found 
							if (!tokens[0].equalsIgnoreCase("GET") ) {
								System.out.println("HTTP_BAD_METHOD");											
							} else {
								found_get = true;
							}
							
							if ( !tokens[1].startsWith("http://") ) { // Checks if protocol is good 
								System.out.println("Bad URL: Bad protocol"); 
								
							} else {
								urlString = tokens[1];
								found_url = true;
							}
						} else { // GET has been found 
							if ( !tokens[0].startsWith("http://") ) { // Checks if protocol is good 
								System.out.println("Bad URL: Bad protocol"); 
								
							} else {
								urlString = tokens[0];
								found_url = true;
							}
							
							if ( !version.equalsIgnoreCase(tokens[1]) ) { // Checks if version is good 
								System.out.println("Bad Version" );
								
							} else {
								found_version = true; 
							}
						}
					}
					
					if (  tokens.length == 1 ) { // Only one token in the line 
						if (!found_get) { // If GET is not found 
							if (!tokens[0].equalsIgnoreCase("GET") ) {
								System.out.println("HTTP_BAD_METHOD");											
							} else {
								found_get = true;
							}
						}  else {
						
						if (!found_url) { // If GET is found already 
							if ( !tokens[0].startsWith("http://") ) { // Checks if protocol is good 
								System.out.println("Bad URL: Bad protocol"); 
								
							} else {
								urlString = tokens[0];
								found_url = true;
							}
						} else {
						
						if (!found_version ) { // If GET and URL are found already 
							if ( !version.equalsIgnoreCase(tokens[0]) ) { // Checks if version is good 
								System.out.println("Bad Version" );
								
							} else {
								found_version = true; 
							}
						}
						}
						}
					}
				
					// Check for two newlines 
					if ( input.isEmpty() ) {						
						prev = true; 
					} else {
						prev = false; 
					}
					 
				} // End parsing loop 
				
			DataOutputStream out = new DataOutputStream(sock.getOutputStream()); 
			BufferedReader rd = null;
			
			try {
				//Make TCP connection to the "real" Web server; 
				URL url = new URL(urlString);
				// Send over an HTTP request;
				URLConnection uConn = url.openConnection();
				uConn.setDoInput(true);
				uConn.setDoOutput(false); // Not Doing posts 
				
				// Receive the server's response;
				InputStream input_s = null;
				HttpURLConnection huc = (HttpURLConnection)uConn; 
				
				if (huc.getContentLength() > 0) {
					try {
						input_s = uConn.getInputStream();
						rd = new BufferedReader(new InputStreamReader(input_s));
					} catch (IOException ioe ) {
						System.out.println("IO Exception!" + ioe);
					}
				}
				// End request to server, get response from server
				
				// Send the server's response back to the client;
				byte b[] = new byte[BUFFER_SIZE];
				int index = input_s.read(b, 0, BUFFER_SIZE);
				while (index != -1 ) {
					out.write(b, 0, index);
					index = input_s.read(b, 0, BUFFER_SIZE);
				}
				out.flush();
				// End proxy response to client 
			} catch (Exception e ) {
				System.err.println("ERROR: Proxy to Server connection failed");
			}
			
			// Close out all resources
            if (rd != null) {
                rd.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (sock != null) {
                sock.close();
            }
			
		} catch (IOException e ) {
			System.out.println("Well that was dissappointing");
		}
	}
	

}
