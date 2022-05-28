package Server.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class HttpResponseHeader {
    String version;
    String status;
    int statusCode;
    Map<String,String> attributes=new HashMap<>();

    public final Map<Integer,String> statusMap=new HashMap<>(){{
        put(200,"OK");
        put(404,"NOT FOUND");
        put(400,"BAD REQUEST");
        put(500,"SERVER ERROR");
    }};

    public HttpResponseHeader(String version, int statusCode) {
        this.version = version;
        this.statusCode = statusCode;
        status=statusMap.get(statusCode);
    }

    public void putAttribute(String key,String value){
        attributes.put(key,value);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner=new StringJoiner(HttpResponse.CRLF);
        stringJoiner.add(version+" "+statusCode+" "+status);
        for (String s : attributes.keySet()) {
            stringJoiner.add(s+": "+attributes.get(s));
        }
        return stringJoiner.toString();
    }
}
