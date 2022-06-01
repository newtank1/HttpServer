package Server.Request;

import Server.Content.StreamContent;
import Server.Content.StreamContentFactory;
import Server.Exceptions.HttpException;

import java.io.IOException;

public class ProcessorImpl implements HttpRequestProcessor{
    @Override
    public StreamContent processRequest(HttpRequest request) throws IOException, HttpException {
        StreamContentFactory factory=new StreamContentFactory();
        return factory.createContent(request);

    }
}
