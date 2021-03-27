package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.App;
import dto.RequestDto;

import java.io.IOException;
import java.io.OutputStream;

public class HandlerHttpServer implements HttpHandler {

    private ObjectMapper objectMapper;
    private App controller;

    public HandlerHttpServer() {
        objectMapper = new ObjectMapper();
        controller = new App();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("POST".equals(exchange.getRequestMethod())) {
                RequestDto body = objectMapper.readValue(exchange.getRequestBody(), RequestDto.class);
                String [] result = controller.convert(body);
                byte [] res = "3.6".getBytes();
                exchange.sendResponseHeaders(200, res.length);
                OutputStream os = exchange.getResponseBody();
                os.write(res);
                os.close();
            } else {
                exchange.sendResponseHeaders(301, -1);
            }
            exchange.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
