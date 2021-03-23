import controller.HttpPostService;
import util.FileMeasureReader;

import java.io.FileNotFoundException;



public class Application {

    public void Run(String path) {
        new HttpPostService().StartUp();


        FileMeasureReader fmr = new FileMeasureReader();
        try {
            fmr.ReadCSVbyScanner("/file.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
