package Server.Exceptions;

import Server.Response.HttpResponse;

public class UnsupportedMethod extends HttpException {

    public UnsupportedMethod(String version) {
        super(version);
    }

    @Override
    public HttpResponse buildResponse() {
        return builder.build(405,version);
    }
}
