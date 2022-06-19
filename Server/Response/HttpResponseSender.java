package Server.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
*发送响应报文的类
* */

public class HttpResponseSender {
    public void send(HttpResponse response, Socket socket) throws IOException {
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
        byte[] dataString = response.getDataBytes();
        dataOutputStream.write(dataString);
        dataOutputStream.flush();
    }
}
