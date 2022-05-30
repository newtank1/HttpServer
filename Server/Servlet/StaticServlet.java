package Server.Servlet;

import Server.Model.StreamContent;
import Server.Model.StreamContentFactory;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

import java.io.IOException;

public class StaticServlet extends HttpServlet{
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        StreamContentFactory factory=new StreamContentFactory();
        StreamContent content = factory.createContent(request.getHeader().getUri().substring(1));
        response.setData(content);
        response.setStatus(200);
    }

}
