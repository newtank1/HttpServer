package Server.Exceptions;

public class BadRequest extends HttpException{
    private String version;

    public BadRequest(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
