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
    private final long startTime=System.currentTimeMillis();
    private long endTime;
    private final HttpRequestParser parser=new ParserImpl();
    private final HttpRequestProcessor processor=new ProcessorImpl();
    private final HttpResponseBuilder builder=new BuilderImpl();
    private final HttpResponseSender sender=new SenderImpl();
    private final HttpServlet servlet;
    private Thread daemon;

    public HttpHandler(Socket socket) {
        HttpServlet servlet1;
        this.socket = socket;
        try {
            Class<?> servletClass = Class.forName(HttpServer.getConfig("Servlet"));
            servlet1 = (HttpServlet) servletClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
            servlet1 =new StaticServlet();
        }
        if(HttpServer.getConfig("Alive")!=null){
            try {
                endTime=Long.parseLong(HttpServer.getConfig("Alive"))*1000+startTime;
            }catch (NumberFormatException e){
                endTime=startTime;
            }
        }else {
            endTime=startTime;
        }
        servlet = servlet1;
        System.out.println("Socket "+socket+" init "+startTime+" end in "+endTime);

        daemon=new Thread(()->{
            while (!socket.isClosed()) {
                if (isTimeout()) {
                    try {
                        socket.getInputStream().close();
                        socket.close();
                        System.out.println("Socket "+socket+" closed in "+endTime+" now "+System.currentTimeMillis());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        daemon.start();
    }

    private void process() throws IOException {
        HttpRequest request=null;
        HttpResponse response;
        try {
            request=parser.parseRequest(socket);
            //System.out.println("received request: "+request);
            response=new HttpResponse(request.getVersion());
            servlet.service(request,response);
            if(response.getStatus()==0){
                response.setStatus(HttpResponseBuilder.OK);
            }
        } catch (HttpException e) {
            response= e.buildResponse();
        }
        //System.out.println("response: "+response);
        long nowTime=System.currentTimeMillis();
        String isAlive = null;
        if (request != null) {
            isAlive=request.getAttribute("Connection");
            //System.out.println("isAlive: "+isAlive);
        }
        if(response!=null) {
            if("keep-alive".equalsIgnoreCase(isAlive)) {
                if (nowTime >= endTime) {
                    response.setAttribute("Connection", "close");
                } else {
                    response.setAttribute("Connection", "keep-alive");
                    response.setAttribute("Keep-Alive","timeout="+HttpServer.getConfig("Alive")+",max=100");
                    //System.out.println(response);
                }
            }
            sender.send(response, socket);
            System.out.println("Socket "+socket+" response");
        }
        if(!socket.isClosed()&&(nowTime>=endTime||"close".equalsIgnoreCase(isAlive))) {
            socket.close();
            System.out.println("Socket "+socket+" closed in "+endTime+" now "+nowTime);
        }
        //System.out.println();
    }
    @Override
    public void run() {
        try {
            while (System.currentTimeMillis()<=endTime) {
                //System.out.println("Socket "+socket+" process in "+System.currentTimeMillis());
                process();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isTimeout(){
        return System.currentTimeMillis()>=endTime;
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
