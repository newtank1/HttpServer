package Server.Request;

import Server.Model.StreamContent;

import java.io.IOException;

public interface HttpRequestProcessor {
    StreamContent processRequest(HttpRequest request) throws IOException;
}
