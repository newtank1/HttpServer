package Server.Request;

import Server.Model.Content;

import java.io.FileNotFoundException;

public class ProcessorImpl implements HttpRequestProcessor{
    @Override
    public Content processRequest(HttpRequest request) throws FileNotFoundException {
        throw new FileNotFoundException();
    }
}
