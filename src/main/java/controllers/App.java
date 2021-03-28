package controllers;
import dto.RequestDto;
import dto.RespounceDto;
import handlers.HandlerDB;
import handlers.HandlerDtoRequest;
import repository.DB;
import services.RespounceBuilder;

public class App {

    public RespounceDto convert(RequestDto body, DB dbObject) {

        HandlerDB handlerDb = new HandlerDB();


        HandlerDtoRequest handlerDto = new HandlerDtoRequest();

        RespounceBuilder respounceBuilder = new RespounceBuilder();
            respounceBuilder.setDbObject(dbObject);
            respounceBuilder.setHandlerDB(handlerDb);
            respounceBuilder.setHandlerDtoRequest(handlerDto);
            respounceBuilder.setInputDto(body);
            respounceBuilder.run();
            return respounceBuilder.getOutputDto();
        }
    }


