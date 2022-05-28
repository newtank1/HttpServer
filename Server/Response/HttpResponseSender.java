package Server.Response;

import java.io.IOException;
import java.net.Socket;

public interface HttpResponseSender {
    void send(HttpResponse response, Socket socket) throws IOException;
}
