package Server.Exceptions;

import Server.Response.BuilderImpl;
import Server.Response.HttpResponse;
import Server.Response.HttpResponseBuilder;

public abstract class HttpException extends Exception{

    protected String version;

    protected final HttpResponseBuilder builder=new BuilderImpl();

    public HttpException(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public abstract HttpResponse buildResponse();
}
