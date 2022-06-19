package Server.Servlet;

import Server.Exceptions.HttpException;
import Server.Exceptions.ServerError;
import Server.Exceptions.UnsupportedMethod;
import Server.Request.HttpRequest;
import Server.Response.HttpResponse;

import java.io.IOException;

/**
* 存放处理请求逻辑的基类，一定程度参考了tomcat的实现。没有完全按Servlet规范写
* 具体使用哪个Servlet要写在配置里。
* */
public abstract class HttpServlet {
    public void service(HttpRequest request, HttpResponse response) throws HttpException {
        try {
            switch (request.getMethod().toLowerCase()){
                case "get":doGet(request, response);return;
                case "post":doPost(request, response);return;
            }
        }catch (RuntimeException e){  //包装500异常
            throw new ServerError(request.getVersion());
        }
        throw new UnsupportedMethod(request.getVersion());  //只支持get和post
    }

    public void doGet(HttpRequest request, HttpResponse response) throws HttpException {  //实现类没有重写就会产生405响应
        throw new UnsupportedMethod(request.getVersion());
    }

    public void doPost(HttpRequest request, HttpResponse response) throws  HttpException {
        throw new UnsupportedMethod(request.getVersion());
    }
}
