package Server.Request;

import Server.Model.Content;

import java.io.*;

public interface HttpRequestProcessor {
    Content processRequest(HttpRequest request) throws FileNotFoundException;
}
