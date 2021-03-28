package controllers;

import dto.RequestDto;
import dto.RespounceDto;
import handlers.HandlerDB;
import handlers.HandlerDtoRequest;
import repository.DB;
import services.RespounceBuilder;
import utils.FileReader;

import java.io.FileNotFoundException;

public class App {

    public RespounceDto convert(RequestDto body) {

        FileReader fmr = new FileReader();

        try {
            fmr.ReadCSVbyScanner("/file.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            DB dbObject = new DB();
            dbObject.setDataRules(fmr.records);
            dbObject.makeGroupsMeasures();
            dbObject.makeTableTypeMeasures();
            dbObject.makeUniqueMeasure();
            HandlerDB handlerDb = new HandlerDB();
            RequestDto requestDto = new RequestDto();
            //requestDto.setFrom("    км");
            //requestDto.setTo("м ");
            RespounceBuilder postService = new RespounceBuilder();
            HandlerDtoRequest handlerDto = new HandlerDtoRequest();
            postService.setDbObject(dbObject);
            postService.setHandlerDB(handlerDb);
            postService.setHandlerDtoRequest(handlerDto);
            postService.setInputDto(body);
            postService.run();
            return postService.getOutputDto();
        }
    }

}
