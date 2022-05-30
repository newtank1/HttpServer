package Server.Request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

    private final String method;
    private final String uri;
    private final String version;
    private final Map<String,String> attributes=new HashMap<>();
    private final Map<String,String> params=new HashMap<>();

    public HttpRequestHeader(String header){
        String[] lines=header.split(HttpRequest.CRLF);
        String requestLine=lines[0];
        String[] s = requestLine.split(" ");
        method= s[0];
        String[] uriParams=s[1].split("\\?",2);
        uri=uriParams[0];
        if("get".equalsIgnoreCase(method)&&uriParams.length>1) {
            String par = uriParams[1];
            String[] pars = par.split("&");
            for (String value : pars) {
                String[] param = value.split("=");
                params.put(param[0], param[1]);
            }
        }
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

    protected void putParam(String key,String value){
        params.put(key, value);
    }

    protected String getParam(String key){
        return params.get(key);
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(method).append(" ").append(uri).append(" ").append(version).append(HttpRequest.CRLF);
        for (String s : attributes.keySet()) {
            sb.append(s).append(":").append(attributes.get(s)).append(HttpRequest.CRLF);
        }
        sb.append(HttpRequest.CRLF);
        return sb.toString();
    }
}
