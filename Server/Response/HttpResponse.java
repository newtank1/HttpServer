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

    public HttpResponse(String version){
        header=new HttpResponseHeader(version);
    }

    public byte[] getDataBytes(){
        byte[] headerBytes= (header.toString() + CRLF + CRLF).getBytes();
        byte[] bytes=new byte[0];
        if(data!=null) {
            try {
                bytes = new byte[data.getLength()];
                int read = bytes.length;
                while (read > 0) {
                    bytes = data.readData(read);
                    read -= read;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void setData(Content content){
        data=content;
        setAttribute("Content-Length", String.valueOf(data.getLength()));
        setAttribute("Content-Type", data.getType());
    }

    public int getStatus(){
        return header.getStatus();
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "header=" + header +
                ", data=" + data +
                '}';
    }
}
