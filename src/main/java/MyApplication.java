import controllers.HttpServer;
import repository.DB;
import utils.FileReader;

public class MyApplication {
    public void Run(String path) {
        FileReader fmr = new FileReader();
        fmr.ReadCSVbyScanner(path);
        DB dbObject = new DB();
        dbObject.setDataRules(fmr.records);
        dbObject.makeGroupsMeasures();
        dbObject.makeTableTypeMeasures();
        dbObject.makeUniqueMeasure();
        new HttpServer().StartUp(dbObject);
    }
}