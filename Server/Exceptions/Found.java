package Server.Exceptions;

import Server.Response.HttpResponse;

public class Found extends HttpException{
    private String location;

    public Found(String version,String location){
        super(version);
        this.location=location;
    }
    @Override
    public HttpResponse buildResponse() {
        if (version != null) {
           HttpResponse response= builder.build(301,version);
           response.setAttribute("Location",location);
           return response;
        }
        return null;
    }
}
