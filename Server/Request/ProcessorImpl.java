package Server.Request;

import Server.Model.Content;
import Server.Model.ContentFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProcessorImpl implements HttpRequestProcessor{
    @Override
    public Content processRequest(HttpRequest request) throws IOException {
        ContentFactory factory=new ContentFactory();
        return factory.createContent(request.getHeader().getUri().substring(1));
    }
}
