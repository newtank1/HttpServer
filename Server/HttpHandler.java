package Server;

import Server.Exceptions.HttpException;
import Server.Request.*;
import Server.Response.*;
import Server.Servlet.HttpServlet;
import Server.Servlet.StaticServlet;

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
        HttpRequest request;
        HttpResponse response;
        try {
            request=parser.parseRequest(socket);
            System.out.println("received request: "+request);
            response=new HttpResponse(request.getVersion());
            servlet.service(request,response);
            if(response.getStatus()==0){
                response.setStatus(HttpResponseBuilder.OK);
            }
        } catch (HttpException e) {
            response= e.buildResponse();
        }
        System.out.println("response: "+response);
        if(response!=null)
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
