package Server.Exceptions;

import Server.Response.HttpResponse;

public class NotFound extends HttpException{
    public NotFound(String version) {
        super(version);
    }

    public NotFound() {
        super(null);
    }

    @Override
    public HttpResponse buildResponse() {
        if(version!=null)
            return builder.build(404,version);
        return null;
    }
}
