package Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务器类，用于创建连接
* */
public final class HttpServer {
    public static Map<String,String> config;

    public static void main(String[] argv) throws Exception {
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
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("Config.txt")));//读配置
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
