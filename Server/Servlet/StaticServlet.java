package Server.Servlet;

import Server.Exceptions.HttpException;
import Server.Exceptions.NotFound;
import Server.Model.StreamContent;
import Server.Model.StreamContentFactory;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

import java.io.IOException;

public class StaticServlet extends HttpServlet{
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws HttpException {
        StreamContentFactory factory=new StreamContentFactory();
        StreamContent content = null;
        try {
            content = factory.createContent(request.getHeader().getUri().substring(1));
        } catch (IOException e) {
            throw new NotFound(request.getVersion());
        }
        response.setData(content);
        response.setStatus(200);
    }

}
