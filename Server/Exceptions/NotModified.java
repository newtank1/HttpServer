package Server.Exceptions;

import Server.Response.HttpResponse;

public class NotModified extends HttpException{
    public NotModified(String version) {
        super(version);
    }

    public NotModified() {
        super(null);
    }

    @Override
    public HttpResponse buildResponse() {
        if (version != null) {
            return builder.build(304,version);
        }
        return null;
    }
}
