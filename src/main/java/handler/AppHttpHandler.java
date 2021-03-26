package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.AppController;
import dto.RequestDto;

import java.io.IOException;
import java.io.OutputStream;

public class AppHttpHandler implements HttpHandler {

    private ObjectMapper objectMapper;
    private AppController controller;

    public AppHttpHandler() {
        objectMapper = new ObjectMapper();
        controller = new AppController();
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
