package Server.Response;

import Server.Model.Content;

public interface HttpResponseBuilder {
    int OK=200;
    int NOT_FOUND=404;
    int SERVER_ERROR=500;
    int BAD_REQUEST=400;

    HttpResponse build(Content data, int status, String version);

    HttpResponse build(int status,String version);
}
