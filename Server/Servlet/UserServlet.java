package Server.Servlet;

import Server.Exceptions.HttpException;
import Server.Exceptions.UnsupportedMethod;
import Server.Model.TextContent;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet{
    public static Map<String,String> accounts=new HashMap<>();

    public void doGet(HttpRequest request, HttpResponse response) throws  HttpException {
        if(request.getUri().equals("/register")){
            String account=request.getParam("account");
            String password=request.getParam("password");
            accounts.put(account,password);
            response.setData(new TextContent("register success, account is "+account+", password is "+password));
        }else throw new UnsupportedMethod(request.getVersion());
    }


    public void doPost(HttpRequest request, HttpResponse response) throws HttpException {
        if(request.getUri().equals("/login")){
            String account=request.getParam("account");
            String password=request.getParam("password");
            if(accounts.get(account).equals(password)){
                response.setData(new TextContent("login success"));
            }else {
                response.setData(new TextContent("login failed"));
            }
        }else throw new UnsupportedMethod(request.getVersion());
    }
}
