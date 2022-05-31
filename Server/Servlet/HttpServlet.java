package Server.Servlet;

import Server.Exceptions.HttpException;
import Server.Exceptions.ServerError;
import Server.Exceptions.UnsupportedMethod;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

import java.io.IOException;


public abstract class HttpServlet {
    public void service(HttpRequest request, HttpResponse response) throws HttpException {
        try {
            switch (request.getMethod().toLowerCase()){
                case "get":doGet(request, response);return;
                case "post":doPost(request, response);return;
            }
        }catch (RuntimeException e){
            throw new ServerError(request.getVersion());
        }
        throw new UnsupportedMethod(request.getVersion());
    }

    public void doGet(HttpRequest request, HttpResponse response) throws HttpException {
        throw new UnsupportedMethod(request.getVersion());
    }

    public void doPost(HttpRequest request, HttpResponse response) throws  HttpException {
        throw new UnsupportedMethod(request.getVersion());
    }
}
