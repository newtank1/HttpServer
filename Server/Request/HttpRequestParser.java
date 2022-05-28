package Server.Request;

import Server.Exceptions.BadRequest;
import Server.Exceptions.HttpException;

import java.io.IOException;
import java.net.Socket;

public interface HttpRequestParser {
    String CRLFCRLF="\r\n\r\n";
    HttpRequest parseRequest(Socket socket) throws IOException, BadRequest;
}
