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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public final class HttpServer {
    public static Map<String,String> config;

    public static void main(String[] argv) throws Exception {
        // Get the port number from the config.
        int port = Integer.parseInt(config.getOrDefault("Port","80"));


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

    static {
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("Config.txt")));
            String s;
            config=new HashMap<>();
            while ((s= reader.readLine())!=null){
                String[] strs=s.split(":",2);
                config.put(strs[0],strs[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
