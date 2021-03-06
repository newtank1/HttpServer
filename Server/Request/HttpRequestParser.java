package Server.Request;

import Server.Exceptions.BadRequest;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
* 从socket读取报文，构建Http请求头和请求体
* */

public class HttpRequestParser {
    String CRLFCRLF="\r\n\r\n";

    public HttpRequest parseRequest(Socket socket) throws BadRequest {
        InputStream reader;
        String requestHeader = null;
        try {
            reader = new DataInputStream(socket.getInputStream());
            requestHeader = readHeader(reader);
            HttpRequestHeader header;
            header=new HttpRequestHeader(requestHeader);
            String length = header.getAttribute("Content-Length");
            if("post".equalsIgnoreCase(header.getMethod())&&length==null){
                throw new BadRequest(header.getVersion());
            }
            if(length==null) length="0";
            return new HttpRequest(header, new String(reader.readNBytes(Integer.parseInt(length))));
        } catch (IOException e) {
            throw new BadRequest();
        }
    }

    private String readHeader(InputStream inputStream) throws IOException, BadRequest {
        StringBuilder bytes=new StringBuilder();
        while (bytes.length()<4||!CRLFCRLF.equals(bytes.substring(bytes.length()-4,bytes.length()))){  //截取\r\n\r\n
            bytes.append((char) inputStream.read());
            if(bytes.length()>8192) {
                HttpRequestHeader header=new HttpRequestHeader(bytes.toString());
                throw new BadRequest(header.getVersion());
            }
        }
        return bytes.toString();
    }
}
