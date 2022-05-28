package Server.Model;

import java.io.*;

public class ContentFactory {
    public Content createContent(String url) throws IOException {
        InputStream inputStream=new DataInputStream(new FileInputStream(url));
        byte[] bytes = inputStream.readAllBytes();
        if(url.endsWith(".txt")){
            return new Content("text/plain",bytes);
        }
        if(url.endsWith(".html")){
            return new Content("text/html",bytes);
        }
        if(url.endsWith(".css")){
            return new Content("text/css",bytes);
        }
        if(url.endsWith(".js")){
            return new Content("text/javascript",bytes);
        }
        if(url.endsWith(".png")){
            return new Content("image/png",bytes);
        }
        return null;
    }
}
