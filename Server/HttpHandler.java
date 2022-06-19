package Server;

import Server.Exceptions.HttpException;
import Server.Request.*;
import Server.Response.*;
import Server.Servlet.HttpServlet;
import Server.Servlet.StaticServlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;


/**
* 具体的连接类，用于处理一个来源的请求
* */

public class HttpHandler implements Runnable{

    Socket socket;
    private long endTime;
    private final HttpRequestParser parser=new HttpRequestParser();
    private final HttpResponseSender sender=new HttpResponseSender();
    private final HttpServlet servlet;

    public HttpHandler(Socket socket) {
        HttpServlet servlet1;
        this.socket = socket;
        try {  //从配置文件中寻找Servlet类
            Class<?> servletClass = Class.forName(HttpServer.getConfig("Servlet"));
            servlet1 = (HttpServlet) servletClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
            servlet1 =new StaticServlet();//没有找到就用默认的
        }
        long startTime = System.currentTimeMillis();
        if(HttpServer.getConfig("Alive")!=null){ //设置长连接时间
            try {
                endTime=Long.parseLong(HttpServer.getConfig("Alive"))*1000+ startTime;
                Thread daemon = new Thread(() -> {//守护线程，时间到就关闭连接
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
            endTime= startTime;  //没有长连接设置或设置无效则没有守护线程，服务完直接关闭
        }
        servlet = servlet1;

    }

    private void process() throws IOException {
        HttpRequest request=null;
        HttpResponse response;
        try {
            request=parser.parseRequest(socket);//解析请求
            response=new HttpResponse(request.getVersion());  //构建响应
            servlet.service(request,response);  //包含业务逻辑的代码处理请求
            if(response.getStatus()==0){
                response.setStatus(HttpResponseBuilder.OK);
            }
        } catch (HttpException e) {  //对异常情况进行包装，并直接构建对应的响应
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
            if("keep-alive".equalsIgnoreCase(isAlive)) {  //请求要求长连接
                if (nowTime >= endTime) {
                    response.setAttribute("Connection", "close");
                } else {
                    response.setAttribute("Connection", "keep-alive");
                    response.setAttribute("Keep-Alive","timeout="+HttpServer.getConfig("Alive")+",max=100");
                }
            }
            sender.send(response, socket);//发送响应
        }
        if(!socket.isClosed()&&(nowTime>=endTime||!"keep-alive".equalsIgnoreCase(isAlive))) {
            socket.close();
        }
    }
    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {//持续监听请求
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
