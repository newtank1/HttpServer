package Server.Response;

import Server.Model.StreamContent;

public interface HttpResponseBuilder {
    int OK=200;
    int NOT_FOUND=404;
    int SERVER_ERROR=500;
    int BAD_REQUEST=400;
    int UNSUPPORTED_METHOD=405;

    HttpResponse build(StreamContent data, int status, String version);

    HttpResponse build(int status,String version);
}
