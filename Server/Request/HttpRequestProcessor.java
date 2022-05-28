package Server.Request;

import Server.Model.Content;

import java.io.IOException;

public interface HttpRequestProcessor {
    Content processRequest(HttpRequest request) throws IOException;
}
