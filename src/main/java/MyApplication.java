import controllers.HttpServer;
import dto.RequestDto;
import handlers.HandlerDtoRequest;
import repository.DB;
import handlers.HandlerDB;
import services.RespounceBuilder;
import utils.FileReader;

import java.io.FileNotFoundException;


public class MyApplication {

    public void Run(String path) {


        FileReader fmr = new FileReader();

        try {
            fmr.ReadCSVbyScanner("/file.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            new HttpServer().StartUp();
            DB dbObject = new DB();
            dbObject.setDataRules(fmr.records);
            dbObject.makeGroupsMeasures();
            dbObject.makeTableTypeMeasures();
            dbObject.makeUniqueMeasure();
            HandlerDB handlerDb = new HandlerDB();
            RequestDto requestDto = new RequestDto();
            requestDto.setFrom(" км * час / м * сек");
            requestDto.setTo("");
            RespounceBuilder postService = new RespounceBuilder();
            HandlerDtoRequest handlerDto = new HandlerDtoRequest();
            postService.setDbObject(dbObject);
            postService.setHandlerDB(handlerDb);
            postService.setHandlerDtoRequest(handlerDto);
            postService.setInputDto(requestDto);
            postService.run();


//            ArrayList<String[]> from = new ArrayList<String[]>(Arrays.asList(new String[]{"м", "сутки", "неделя", "удав"}, new String[]{"миля", "аршин", "локоть", "час"}));
//            ArrayList<String[]> to = new ArrayList<String[]>(Arrays.asList(new String[]{"парсек", "год", "сажень", "удав"}, new String[]{"foot", "км", "локоть", "час"}));
//            ArrayList<String[]> to = new ArrayList<String[]>(Arrays.asList(new String[]{}, new String[]{}));
//            ArrayList<String[]> fraction =  new ArrayList<>();
//            fraction = handlerDto.getFullFraction(from, to);
//            handlerDto.checkConversionEnable(fraction, dbObject.getUniqueMeasure());
//
//            handlerDb.getConversionRows("foot", "сажень", dbObject);
//            handlerDb.getRatio("foot", "см", dbObject);
//            handlerDb.getRatio("foot", "см", dbObject);
//            handlerDb.getRatio("foot", "мм", dbObject);
//            handlerDb.getRatio("foot", "км", dbObject);
//            handlerDb.getRatio("foot", "м", dbObject);
//            handlerDb.getRatio("foot", "дюйм", dbObject);
//            handlerDb.getRatio("мм", "м", dbObject);
//            handlerDb.getRatio("мм", "дюйм", dbObject);
//            handlerDb.getRatio("дюйм", "мм", dbObject);
//            handlerDb.getRatio("локоть", "мм", dbObject);
//            handlerDb.getRatio("кабельтов", "мм", dbObject);
//            handlerDb.getRatio("удав", "foot", dbObject);


        }


    }

}
