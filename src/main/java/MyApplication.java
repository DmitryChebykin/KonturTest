import controllers.HttpServer;
import handlers.HandlerDtoRequest;
import repository.DB;
import handlers.HandlerDB;
import services.PostService;
import utils.FileReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;


public class MyApplication {

    public void Run(String path) {


        FileReader fmr = new FileReader();
        ArrayList<String[]> records = null;
        try {
            fmr.ReadCSVbyScanner("/file.csv");
            records = fmr.records;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            new HttpServer().StartUp();
            DB dbObject = new DB();
            HandlerDB handlerDb = new HandlerDB();
            PostService postService = new PostService();
            HandlerDtoRequest handlerDto = new HandlerDtoRequest();
            ArrayList<String[]> from = new ArrayList<String[]>(Arrays.asList(new String[]{"м", "сутки", "неделя", "удав"}, new String[]{"миля", "аршин", "локоть", "час"}));
            ArrayList<String[]> to = new ArrayList<String[]>(Arrays.asList(new String[]{"парсек", "год", "сажень", "удав"}, new String[]{"foot", "км", "локоть", "час"}));
            ArrayList<String[]> fraction =  new ArrayList<>();
            fraction = handlerDto.getFullFraction(from, to);



            dbObject.setDataRules(records);
            dbObject.makeGroupsMeasures();
            dbObject.makeTableTypeMeasures();
            dbObject.makeUniqueMeasure();
            dbObject.getUniqueMeasure();

            handlerDto.checkDtoMeasureInDatabase(fraction, dbObject.getUniqueMeasure());

            handlerDb.getConversionRows("foot", "сажень", dbObject);
            handlerDb.getRatio("foot", "сажень", dbObject);
            handlerDb.getRatio("foot", "см", dbObject);
            handlerDb.getRatio("foot", "мм", dbObject);
            handlerDb.getRatio("foot", "км", dbObject);
            handlerDb.getRatio("foot", "м", dbObject);
            handlerDb.getRatio("foot", "дюйм", dbObject);
            handlerDb.getRatio("мм", "м", dbObject);
            handlerDb.getRatio("мм", "дюйм", dbObject);
            handlerDb.getRatio("дюйм", "мм", dbObject);
            handlerDb.getRatio("локоть", "мм", dbObject);
            handlerDb.getRatio("кабельтов", "мм", dbObject);
            handlerDb.getRatio("удав", "foot", dbObject);



        }


    }

}
