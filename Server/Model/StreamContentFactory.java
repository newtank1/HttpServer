package Server.Model;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamContentFactory {
    public StreamContent createContent(String url) throws IOException {
        InputStream bytes=new DataInputStream(new FileInputStream(url));
        if(url.endsWith(".txt")){
            return new StreamContent("text/plain",bytes);
        }
        if(url.endsWith(".html")){
            return new StreamContent("text/html",bytes);
        }
        if(url.endsWith(".css")){
            return new StreamContent("text/css",bytes);
        }
        if(url.endsWith(".js")){
            return new StreamContent("text/javascript",bytes);
        }
        if(url.endsWith(".png")){
            return new StreamContent("image/png",bytes);
        }
        if(url.endsWith(".ico")){
            return new StreamContent("image/x-icon",bytes);
        }
        return null;
    }
}
