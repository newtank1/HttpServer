package Server.Request;

import Server.Exceptions.BadRequest;
import Server.Exceptions.HttpException;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ParserImpl implements HttpRequestParser{
    @Override
    public HttpRequest parseRequest(Socket socket) throws IOException, BadRequest {
        InputStream reader=new DataInputStream(socket.getInputStream());
        String requestHeader = readHeader(reader);
        HttpRequestHeader header=new HttpRequestHeader(requestHeader);
        String length = header.getAttribute("Content-Length");
        if(length==null){
            throw new BadRequest(header.getVersion());
        }
        return new HttpRequest(header, new String(reader.readNBytes(Integer.parseInt(length))));
    }

    private String readHeader(InputStream inputStream) throws IOException {
        StringBuilder bytes=new StringBuilder();
        while (bytes.length()<4||!CRLFCRLF.equals(bytes.substring(bytes.length()-4,bytes.length()))){
            bytes.append((char) inputStream.read());
        }
        return bytes.toString();
    }
}
