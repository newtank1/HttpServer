package Server.Response;

import Server.Model.Content;

public class HttpResponse {
    public static final String CRLF="\r\n";

    HttpResponseHeader header;
    Content data;

    public HttpResponse(HttpResponseHeader header, Content data) {
        this.header = header;
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(header.toString()).append(CRLF).append(CRLF);
        if(data!=null)
            sb.append(data.getData());
        return sb.toString();
    }
}
