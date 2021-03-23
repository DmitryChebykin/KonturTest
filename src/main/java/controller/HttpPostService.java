package controller;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import handler.AppHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpPostService {

    public void StartUp() {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            HttpContext httpContext = server.createContext("/convert", new AppHttpHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}