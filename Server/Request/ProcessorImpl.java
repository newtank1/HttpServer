package Server.Request;

import Server.Model.StreamContent;
import Server.Model.StreamContentFactory;

import java.io.IOException;

public class ProcessorImpl implements HttpRequestProcessor{
    @Override
    public StreamContent processRequest(HttpRequest request) throws IOException {
        StreamContentFactory factory=new StreamContentFactory();
        return factory.createContent(request.getHeader().getUri().substring(1));

    }
}
