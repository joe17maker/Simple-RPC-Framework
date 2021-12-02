package com.rpc.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @描述
 * @创建人 Xiong Nie
 * @创建时间 2021/12/1
 * @修改人和其它信息
 */
@Slf4j
public class HTTPTransportServer implements TransportServer {
    private RequestHandler handler;
    private Server server;
    @Override
    public void init(int port, RequestHandler handler) {
        this.handler=handler;
        this.server=new Server(port);

        ServletContextHandler ctx=new ServletContextHandler();
        server.setHandler(ctx);

        ServletHolder holder =new ServletHolder(new RequsetServlet());
        ctx.addServlet(holder,"/*");

    }

    @Override
    public void start() {
        try{
            server.start();
            server.join();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

    }
    class  RequsetServlet extends HttpServlet{
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");
            InputStream in=req.getInputStream();
            OutputStream out=resp.getOutputStream();
             if(handler!=null){
                 handler.onRequest(in,out);
             }
             out.flush();
        }
    }
}
