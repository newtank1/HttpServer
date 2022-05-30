package Server.Servlet;

import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

public abstract class HttpServlet {
    public void service(HttpRequest request, HttpResponse response){
        switch (request.getMethod().toLowerCase()){
            case "get":doGet(request, response);return;
            case "post":doPost(request, response);return;
        }
        response.setStatus(405);
    }

    public void doGet(HttpRequest request, HttpResponse response){
        response.setStatus(405);
    }

    public void doPost(HttpRequest request, HttpResponse response){
        response.setStatus(405);
    }
}
