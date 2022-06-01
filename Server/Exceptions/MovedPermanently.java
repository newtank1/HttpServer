package Server.Exceptions;

import Server.Response.HttpResponse;

public class MovedPermanently extends HttpException{
    private String location;

    public MovedPermanently(String version,String location){
        super(version);
        this.location=location;
    }
    @Override
    public HttpResponse buildResponse() {
        if (version != null) {
            HttpResponse response= builder.build(302,version);
            response.setAttribute("Location",location);
            return response;
        }
        return null;
    }
}
