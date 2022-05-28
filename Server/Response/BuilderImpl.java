package Server.Response;

import Server.Model.Content;

public class BuilderImpl implements HttpResponseBuilder{

    @Override
    public HttpResponse build(Content data, int status, String version) {
        HttpResponseHeader header=new HttpResponseHeader(version,status);
        HttpResponse response=new HttpResponse(header,data);
        if (data != null) {
            header.putAttribute("Content-Length", String.valueOf(data.getLength()));
            header.putAttribute("Content-Type", data.getType());
        }
        return response;
    }

    @Override
    public HttpResponse build(int status, String version) {
        return build(null,status,version);
    }
}
