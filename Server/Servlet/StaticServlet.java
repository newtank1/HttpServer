package Server.Servlet;

import Server.Exceptions.HttpException;
import Server.Exceptions.NotFound;
import Server.Content.StreamContent;
import Server.Content.StreamContentFactory;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

import java.io.IOException;

public class StaticServlet extends HttpServlet{
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws HttpException {
        StreamContentFactory factory=new StreamContentFactory();
        StreamContent content = null;
        content = factory.createContent(request);
        response.setData(content);
        response.setStatus(200);
    }

}
