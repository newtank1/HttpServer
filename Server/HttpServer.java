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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public final class HttpServer {
    public static Map<String,String> config;

    public static void main(String[] argv) throws Exception {
        // Get the port number from the config.
        int port = Integer.parseInt(config.getOrDefault("Port","80"));

        ServerSocket socket = new ServerSocket(port);

        ArrayList<HttpHandler> connections=new ArrayList<>();


        while (true) {
            Socket connection = socket.accept();

            connection.setKeepAlive(true);


            HttpHandler request = new HttpHandler(connection);

            connections.add(request);

            Thread thread = new Thread(request);

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
            System.out.println("Config loaded");
        } catch (IOException e) {
            System.err.println("Failed to load Config.txt");
            e.printStackTrace();
        }
    }

    public static String getConfig(String key){
        return config.get(key);
    }
}
