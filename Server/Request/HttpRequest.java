package Server.Request;

import lombok.Getter;

public class HttpRequest {
    public static final String CRLF="\r\n";
    private final HttpRequestHeader header;
    private final String data;

    public HttpRequest(HttpRequestHeader header, String data) {
        this.header = header;
        this.data = data;
    }

    public HttpRequestHeader getHeader() {
        return header;
    }

    public String getData() {
        return data;
    }
}
