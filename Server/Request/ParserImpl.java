package Server.Request;

import Server.Exceptions.BadRequest;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ParserImpl implements HttpRequestParser{
    @Override
    public HttpRequest parseRequest(Socket socket) throws IOException, BadRequest {
        InputStream reader=new DataInputStream(socket.getInputStream());
        String requestHeader = readHeader(reader);
        HttpRequestHeader header=new HttpRequestHeader(requestHeader);
        String length = header.getAttribute("Content-Length");
        if("post".equalsIgnoreCase(header.getMethod())&&length==null){
            throw new BadRequest(header.getVersion());
        }
        if(length==null) length="0";
        return new HttpRequest(header, new String(reader.readNBytes(Integer.parseInt(length))));
    }

    private String readHeader(InputStream inputStream) throws IOException, BadRequest {
        StringBuilder bytes=new StringBuilder();
        while (bytes.length()<4||!CRLFCRLF.equals(bytes.substring(bytes.length()-4,bytes.length()))){
            bytes.append((char) inputStream.read());
            if(bytes.length()>8192) {
                HttpRequestHeader header=new HttpRequestHeader(bytes.toString());
                throw new BadRequest(header.getVersion());
            }
        }
        return bytes.toString();
    }
}
