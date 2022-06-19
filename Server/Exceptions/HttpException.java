package Server.Exceptions;

import Server.Response.HttpResponse;
import Server.Response.HttpResponseBuilder;

/**
* 非200状态代表的异常基类
* */
public abstract class HttpException extends Exception{

    protected String version;

    protected final HttpResponseBuilder builder=new HttpResponseBuilder();  //用于构建对应的响应报文

    public HttpException(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public abstract HttpResponse buildResponse();
}
