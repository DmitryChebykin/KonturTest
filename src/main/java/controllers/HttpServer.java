package controllers;

import com.sun.net.httpserver.HttpContext;
import handlers.HandlerHttpServer;
import repository.DB;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServer {

    public void StartUp(DB dbObject) {

        try {
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8081), 0);
            HttpContext httpContext;
            httpContext = server.createContext("/convert", new HandlerHttpServer(dbObject));
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}