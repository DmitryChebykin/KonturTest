package controllers;

import com.sun.net.httpserver.HttpContext;
import handlers.HandlerHttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServer {

    public void StartUp() {

        try {
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8081), 0);
            HttpContext httpContext = server.createContext("/convert", new HandlerHttpServer());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}