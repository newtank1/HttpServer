package Server.Request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

    private final String method;
    private final String uri;
    private final String version;
    private final Map<String,String> attributes=new HashMap<>();

    public HttpRequestHeader(String header){
        String[] lines=header.split(HttpRequest.CRLF);
        String requestLine=lines[0];
        String[] s = requestLine.split(" ");
        method= s[0];
        uri= s[1];
        version= s[2];
        for(int i=1;i< lines.length;i++){
            String[] attr=lines[i].split(":");
            attributes.put(attr[0].strip().toLowerCase(),attr[1].strip());
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getUri() {
        return this.uri;
    }

    public String getVersion() {
        return this.version;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public String getAttribute(String key){
        return attributes.getOrDefault(key.toLowerCase(),null);
    }
}
