package Server.Model;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class ContentFactory {
    public Content createContent(String url) throws IOException {
        InputStream bytes=new DataInputStream(new FileInputStream(url));
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
        if(url.endsWith(".ico")){
            return new Content("image/x-icon",bytes);
        }
        return null;
    }
}
