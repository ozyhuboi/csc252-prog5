Name: Yibo Zhou
Date: 4/27/2015
CSC252 Project 5 
Network Sockets

Compile Instructions: 

Use "javac *.java" to compile the files "MyProxyServer.java" and "MyWorkerThread.java". 

Running Instructions: 

Run the main file in "MyProxyServer.java" with "java MyProxyServer [port name]" where [port name] is default set at 8080.

Running Notes: 

When I tried using the ~cs252/labs15/proxy-test.py file to test my program, the program appeared to freeze. However, when I individually tested each request I managed to get meaningful output. The program can also grab data from the web as well. 

Files Descriptions: 

The file with the "main" method is "MyProxyServer.java" where the port number is taken from command line and a listener serverSocket is created where the proxy will listen to client connections. A while loop, which is always set to true, listens for incoming connections, accepts them, then spawns a new thread which is started to activate the "run" method in the "MyWorkerThread.java" class. 


The "MyWorkerThread.java" is the file which does the heavy lifting. First, the socket is passed into the thread. After the connection is started, the parser will parse the request from the socket side based on the number of tokens in the line to account for the possibility where the request line will be split onto several lines. When the get, url, and version are confirmed, the input will require two newlines, hitting enter three times after the last entry, before getting to the connection. A URL object is created and parsed into an HTTP request object which takes in the data from the page in an dataStream, which is parsed through a BufferedReader. The data from the BufferedReader is passed into a byte array which is finally displayed. At the end, all remaining resources are closed. 










