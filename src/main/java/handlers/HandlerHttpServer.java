package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.App;
import dto.RequestDto;
import dto.ResponseDto;
import repository.DB;
import java.io.OutputStream;


public class HandlerHttpServer implements HttpHandler {

    private final DB dbObject;
    private App controller;

    public HandlerHttpServer(DB dbObject) {
        this.dbObject = dbObject;
        controller = new App();
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            if ("POST".equals(exchange.getRequestMethod())) {
                ObjectMapper objectMapper = new ObjectMapper();
                RequestDto body = objectMapper.readValue(exchange.getRequestBody(), RequestDto.class);
                ResponseDto responseDto = controller.convert(body, dbObject);
                byte [] res = responseDto.getBody().getBytes();
                exchange.sendResponseHeaders(Integer.parseInt(responseDto.getStatusCode()), res.length);
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
