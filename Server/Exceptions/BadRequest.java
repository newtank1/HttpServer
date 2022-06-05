package Server.Exceptions;

import Server.Response.HttpResponse;

public class BadRequest extends HttpException{

    public BadRequest(String version) {
        super(version);
    }

    public BadRequest(){
        super(null);
    }

    @Override
    public HttpResponse buildResponse() {
        if (version != null) {
            return builder.build(400,version);
        }
        return null;
    }
}
