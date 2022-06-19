package Server.Content;

import Server.Exceptions.*;
import Server.RedirectTable;
import Server.Request.HttpRequest;
import Server.Utils.DateUtil;

import java.io.*;
import java.text.ParseException;

/**
* 根据请求url获取文件的类
* */
public class StreamContentFactory {
    public StreamContent createContent(HttpRequest request) throws HttpException {
        String uri=request.getUri();
        InputStream bytes= null;
        RedirectTable redirectTable=RedirectTable.getTable();
        switch (redirectTable.getStatus(uri)){  //301，302
            case RedirectTable.TEMPORARY_MOVED:throw new Found(request.getVersion(),redirectTable.getRedirection(uri).get());
            case RedirectTable.PERMANENT_MOVED:throw new MovedPermanently(request.getVersion(),redirectTable.getRedirection(uri).get());
        }
        long lastModified=0;
        try {
            File file=new File(uri.substring(1));
            bytes = new DataInputStream(new FileInputStream(file));
            lastModified=file.lastModified()/1000*1000;
        } catch (FileNotFoundException e) {
            throw new NotFound(request.getVersion());
        }
        String ifModified = request.getAttribute("if-modified-since");
        if(ifModified !=null){
            DateUtil dateUtil=new DateUtil();
            try {
                long cacheTime=dateUtil.dateToLong(ifModified);
                if(cacheTime>=lastModified)
                    throw new NotModified(request.getVersion());  //304
            } catch (ParseException ignored) {

            }
        }
        //根据url扩展名选择Content-Type
        if(uri.endsWith(".txt")){
            return new StreamContent("text/plain",bytes,lastModified);
        }
        if(uri.endsWith(".html")){
            return new StreamContent("text/html",bytes,lastModified);
        }
        if(uri.endsWith(".css")){
            return new StreamContent("text/css",bytes,lastModified);
        }
        if(uri.endsWith(".js")){
            return new StreamContent("text/javascript",bytes,lastModified);
        }
        if(uri.endsWith(".png")){
            return new StreamContent("image/png",bytes,lastModified);
        }
        if(uri.endsWith(".ico")){
            return new StreamContent("image/x-icon",bytes,lastModified);
        }
        return null;
    }
}
