package Server;

import Server.Exceptions.BadRequest;
import Server.Exceptions.HttpException;
import Server.Request.*;
import Server.Response.*;
import Server.Model.Content;

import java.io.*;
import java.net.Socket;

public class HttpHandler implements Runnable{
    public final String CRLF="\r\n";
    Socket socket;
    private HttpRequestParser parser=new ParserImpl();
    private HttpRequestProcessor processor=new ProcessorImpl();
    private HttpResponseBuilder builder=new BuilderImpl();
    private HttpResponseSender sender=new SenderImpl();

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    private void process() throws IOException {
        HttpRequest request = null;
        HttpResponse response;
        try {
            request=parser.parseRequest(socket);
            Content data= processor.processRequest(request);
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
        sender.send(response,socket);
        socket.close();
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
