package Server.Request;

import Server.Content.StreamContent;
import Server.Exceptions.HttpException;

import java.io.IOException;

public interface HttpRequestProcessor {
    StreamContent processRequest(HttpRequest request) throws IOException, HttpException;
}
