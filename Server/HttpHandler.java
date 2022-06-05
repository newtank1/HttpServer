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
    private long endTime;
    private final HttpRequestParser parser=new HttpRequestParser();
    private final HttpResponseSender sender=new HttpResponseSender();
    private final HttpServlet servlet;

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
        long startTime = System.currentTimeMillis();
        if(HttpServer.getConfig("Alive")!=null){
            try {
                endTime=Long.parseLong(HttpServer.getConfig("Alive"))*1000+ startTime;
                Thread daemon = new Thread(() -> {
                    while (!socket.isClosed()) {
                        if (isTimeout()) {
                            try {
                                socket.getInputStream().close();
                                socket.close();
                                System.out.println("Socket " + socket + " closed in " + endTime + " now " + System.currentTimeMillis());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                daemon.start();
            }catch (NumberFormatException e){
                endTime= startTime;
            }
        }else {
            endTime= startTime;
        }
        servlet = servlet1;

    }

    private void process() throws IOException {
        HttpRequest request=null;
        HttpResponse response;
        try {
            request=parser.parseRequest(socket);
            response=new HttpResponse(request.getVersion());
            servlet.service(request,response);
            if(response.getStatus()==0){
                response.setStatus(HttpResponseBuilder.OK);
            }
        } catch (HttpException e) {
            response= e.buildResponse();
        }
        System.out.println(request);
        long nowTime=System.currentTimeMillis();
        String isAlive = null;
        if (request != null) {
            isAlive=request.getAttribute("Connection");
        }
        System.out.println(response);
        if(response!=null) {
            if("keep-alive".equalsIgnoreCase(isAlive)) {
                if (nowTime >= endTime) {
                    response.setAttribute("Connection", "close");
                } else {
                    response.setAttribute("Connection", "keep-alive");
                    response.setAttribute("Keep-Alive","timeout="+HttpServer.getConfig("Alive")+",max=100");
                }
            }
            sender.send(response, socket);
        }
        if(!socket.isClosed()&&(nowTime>=endTime||!"keep-alive".equalsIgnoreCase(isAlive))) {
            socket.close();
        }
    }
    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                process();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isTimeout(){
        return System.currentTimeMillis()>=endTime;
    }

}
