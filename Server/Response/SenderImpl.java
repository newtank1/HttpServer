package Server.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SenderImpl implements HttpResponseSender{
    @Override
    public void send(HttpResponse response, Socket socket) throws IOException {
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeBytes(response.toString());
        dataOutputStream.flush();
        dataOutputStream.close();
    }
}
