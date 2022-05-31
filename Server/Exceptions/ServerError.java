package Server.Exceptions;

import Server.Response.HttpResponse;

public class ServerError extends HttpException{
    public ServerError(String version) {
        super(version);
    }
    public ServerError(){
        super(null);
    }

    @Override
    public HttpResponse buildResponse() {
        if (version != null) {
            return builder.build(500,version);
        }
        return null;
    }
}
