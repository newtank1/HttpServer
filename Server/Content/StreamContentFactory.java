package Server.Content;

import Server.Exceptions.Found;
import Server.Exceptions.HttpException;
import Server.Exceptions.MovedPermanently;
import Server.Exceptions.NotFound;
import Server.RedirectTable;
import Server.Request.HttpRequest;

import java.io.*;

public class StreamContentFactory {
    public StreamContent createContent(HttpRequest request) throws HttpException {
        String uri=request.getUri();
        InputStream bytes= null;
        RedirectTable redirectTable=RedirectTable.getTable();
        switch (redirectTable.getStatus(uri)){
            case RedirectTable.TEMPORARY_MOVED:throw new Found(request.getVersion(),redirectTable.getRedirection(uri).get());
            case RedirectTable.PERMANENT_MOVED:throw new MovedPermanently(request.getVersion(),redirectTable.getRedirection(uri).get());
        }
        try {
            bytes = new DataInputStream(new FileInputStream(uri.substring(1)));
        } catch (FileNotFoundException e) {
            throw new NotFound(request.getVersion());
        }
        if(uri.endsWith(".txt")){
            return new StreamContent("text/plain",bytes);
        }
        if(uri.endsWith(".html")){
            return new StreamContent("text/html",bytes);
        }
        if(uri.endsWith(".css")){
            return new StreamContent("text/css",bytes);
        }
        if(uri.endsWith(".js")){
            return new StreamContent("text/javascript",bytes);
        }
        if(uri.endsWith(".png")){
            return new StreamContent("image/png",bytes);
        }
        if(uri.endsWith(".ico")){
            return new StreamContent("image/x-icon",bytes);
        }
        return null;
    }
}
