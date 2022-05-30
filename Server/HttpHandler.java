package Server;

import Server.Exceptions.BadRequest;
import Server.Model.StreamContent;
import Server.Request.*;
import Server.Response.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class HttpHandler implements Runnable{

    Socket socket;
    private final HttpRequestParser parser=new ParserImpl();
    private final HttpRequestProcessor processor=new ProcessorImpl();
    private final HttpResponseBuilder builder=new BuilderImpl();
    private final HttpResponseSender sender=new SenderImpl();

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    private void process() throws IOException {
        HttpRequest request = null;
        HttpResponse response;
        try {
            request=parser.parseRequest(socket);
            System.out.println("received request: "+request);
            StreamContent data= processor.processRequest(request);
            response = builder.build(data, HttpResponseBuilder.OK,request.getHeader().getVersion());
        } catch (BadRequest e) {
            response= builder.build(HttpResponseBuilder.BAD_REQUEST,e.getVersion());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            response= builder.build(HttpResponseBuilder.NOT_FOUND,request.getHeader().getVersion());
        }catch (Exception e){
            e.printStackTrace();
            response=builder.build(HttpResponseBuilder.SERVER_ERROR,request.getHeader().getVersion());
        }
        System.out.println("response: "+response);
        sender.send(response,socket);
        socket.close();
        System.out.println();
    }
    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
