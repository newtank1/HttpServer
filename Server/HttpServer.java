package Server;//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server.HttpServer {
//
//    public static void main(String[] args) throws IOException {
//        try (ServerSocket socket = new ServerSocket(20000)) {
//            System.out.println("listening");
//            while (true){
//                Socket client=socket.accept();
//                System.out.println("accepted: "+client);
//                HttpRequest httpRequest=new HttpRequest(client);
//                Thread thread=new Thread(httpRequest);
//                thread.start();
//            }
//        }
//    }
//}

import Server.Request.HttpRequestProcessor;

import java.net.* ;


public final class HttpServer {
    public static void main(String argv[]) throws Exception {
        // Get the port number from the command line.
        int port = 20000;

        // Establish the listen socket.
        ServerSocket socket = new ServerSocket(port);

        // Process HTTP service requests in an infinite loop.
        while (true) {
            // Listen for a TCP connection request.
            Socket connection = socket.accept();

            // Construct an object to process the HTTP request message.
            HttpHandler request = new HttpHandler(connection);

            // Create a new thread to process the request.
            Thread thread = new Thread(request);

            // Start the thread.
            thread.start();
        }
    }
}
