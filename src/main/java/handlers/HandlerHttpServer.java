package handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.App;
import dto.RequestDto;
import dto.RespounceDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class HandlerHttpServer implements HttpHandler {

    private static ObjectMapper objectMapper;
    private App controller;

    public HandlerHttpServer() {
        objectMapper = new ObjectMapper();
        controller = new App();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                RequestDto body = new RequestDto();
                body = objectMapper.readValue(exchange.getRequestBody(), RequestDto.class);
                RespounceDto respounceDto = controller.convert(body);
                byte [] res = respounceDto.getBody().getBytes();
                exchange.sendResponseHeaders(Integer.parseInt(respounceDto.getStatusCode()), res.length);
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
