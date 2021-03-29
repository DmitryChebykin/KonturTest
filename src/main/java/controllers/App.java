package controllers;

import dto.RequestDto;
import dto.ResponseDto;
import handlers.HandlerDB;
import handlers.HandlerDtoRequest;
import repository.DB;
import services.ResponseBuilder;

public class App {

    public ResponseDto convert(RequestDto body, DB dbObject) {

        HandlerDB handlerDb = new HandlerDB();

        HandlerDtoRequest handlerDto = new HandlerDtoRequest();

        ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.setDbObject(dbObject);
        responseBuilder.setHandlerDB(handlerDb);
        responseBuilder.setHandlerDtoRequest(handlerDto);
        responseBuilder.setInputDto(body);
        responseBuilder.run();
        return responseBuilder.getOutputDto();
    }
}


