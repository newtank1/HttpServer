package Server;

import Server.Exceptions.BadRequest;
import Server.Exceptions.UnsupportedMethod;
import Server.Model.StreamContent;
import Server.Request.*;
import Server.Response.*;
import Server.Servlet.HttpServlet;
import Server.Servlet.StaticServlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class HttpHandler implements Runnable{

    Socket socket;
    private final HttpRequestParser parser=new ParserImpl();
    private final HttpRequestProcessor processor=new ProcessorImpl();
    private final HttpResponseBuilder builder=new BuilderImpl();
    private final HttpResponseSender sender=new SenderImpl();
    private final HttpServlet servlet;

    public HttpHandler(Socket socket) {
        HttpServlet servlet1;
        this.socket = socket;
        try {
            Class<?> servletClass = Class.forName(HttpServer.config.get("Servlet"));
            servlet1 = (HttpServlet) servletClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
            servlet1 =new StaticServlet();
        }
        servlet = servlet1;
    }

    private void process() throws IOException {
        HttpRequest request = null;
        HttpResponse response;
        try {
            request=parser.parseRequest(socket);
            System.out.println("received request: "+request);
            response=new HttpResponse(request.getVersion());
//            StreamContent data= processor.processRequest(request);
//            response = builder.build(data, HttpResponseBuilder.OK,request.getHeader().getVersion());
            servlet.service(request,response);
            if(response.getStatus()==0){
                response.setStatus(HttpResponseBuilder.OK);
            }
        } catch (BadRequest e) {
            response= builder.build(HttpResponseBuilder.BAD_REQUEST,e.getVersion());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (request != null) {
                response= builder.build(HttpResponseBuilder.NOT_FOUND,request.getVersion());
            }else {
                socket.close();
                return;
            }
        }catch (UnsupportedMethod e) {
            e.printStackTrace();
            response=builder.build(HttpResponseBuilder.UNSUPPORTED_METHOD,request.getHeader().getVersion());
        }catch (Exception e){
            e.printStackTrace();
            if (request != null) {
                response=builder.build(HttpResponseBuilder.SERVER_ERROR,request.getVersion());
            }
            else {
                socket.close();
                return;
            }
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
