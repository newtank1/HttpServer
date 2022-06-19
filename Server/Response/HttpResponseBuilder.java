package Server.Response;

import Server.Content.StreamContent;

/*
* 直接构建完整响应，用于非200状态的场合
* */

public class HttpResponseBuilder {
    public static final int OK=200;

    public HttpResponse build(StreamContent data, int status, String version) {
        HttpResponseHeader header=new HttpResponseHeader(version,status);
        HttpResponse response=new HttpResponse(header,data);
        if (data != null) {
            header.putAttribute("Content-Length", String.valueOf(data.getLength()));
            header.putAttribute("Content-Type", data.getType());
        }
        return response;
    }

    public HttpResponse build(int status, String version) {
        return build(null,status,version);
    }
}
