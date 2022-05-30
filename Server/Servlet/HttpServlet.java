package Server.Servlet;

import Server.Exceptions.HttpException;
import Server.Exceptions.UnsupportedMethod;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

import java.io.IOException;

public abstract class HttpServlet {
    public void service(HttpRequest request, HttpResponse response) throws IOException, HttpException {
        switch (request.getMethod().toLowerCase()){
            case "get":doGet(request, response);return;
            case "post":doPost(request, response);return;
        }
        throw new UnsupportedMethod();
    }

    public void doGet(HttpRequest request, HttpResponse response) throws IOException,HttpException {
        throw new UnsupportedMethod();
    }

    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedMethod {
        throw new UnsupportedMethod();
    }
}
