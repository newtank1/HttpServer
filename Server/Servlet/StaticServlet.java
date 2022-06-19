package Server.Servlet;

import Server.Content.StreamContent;
import Server.Content.StreamContentFactory;
import Server.Exceptions.HttpException;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;
import Server.Utils.DateUtil;

/**
* 默认的Servlet，只是简单的获取文件并返回。
* */

public class StaticServlet extends HttpServlet{
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws HttpException {
        StreamContentFactory factory=new StreamContentFactory();
        StreamContent content = null;
        content = factory.createContent(request);
        response.setData(content);
        DateUtil dateUtil=new DateUtil();
        String lastModified= dateUtil.longToDate(content.getLastModified());
        response.setAttribute("Last-Modified",lastModified);
        response.setStatus(200);
    }

}
