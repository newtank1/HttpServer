package Server.Response;

import Server.Model.Content;

import java.io.IOException;

public class HttpResponse {
    public static final String CRLF="\r\n";

    HttpResponseHeader header;
    Content data;

    public HttpResponse(HttpResponseHeader header, Content data) {
        this.header = header;
        this.data = data;
    }

    public HttpResponse(){

    }

    public byte[] getDataBytes(){
        byte[] headerBytes= (header.toString() + CRLF + CRLF).getBytes();
        byte[] bytes=new byte[0];
        try {
            bytes=new byte[data.getLength()];
            int read=bytes.length;
            while (read>0) {
                bytes = data.readData(read);
                read-=read;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        byte[] ret=new byte[headerBytes.length+bytes.length];
        System.arraycopy(headerBytes,0,ret,0,headerBytes.length);
        System.arraycopy(bytes,0,ret,headerBytes.length,bytes.length);
        return ret;
    }

    public void setStatus(int statusCode){
        header.setStatus(statusCode);
    }

    public void setAttribute(String key,String value){
        header.putAttribute(key, value);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "header=" + header +
                ", data=" + data +
                '}';
    }
}
