import controllers.HttpServer;
import repository.DB;
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
            DB dbObject = new DB();
            dbObject.setDataRules(fmr.records);
            dbObject.makeGroupsMeasures();
            dbObject.makeTableTypeMeasures();
            dbObject.makeUniqueMeasure();
            new HttpServer().StartUp(dbObject);
        }
    }
}


