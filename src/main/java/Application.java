import controller.HttpPostService;
import util.FileReader;

import java.io.FileNotFoundException;



public class Application {

    public void Run(String path) {
        new HttpPostService().StartUp();


        FileReader fmr = new FileReader();
        try {
            fmr.ReadCSVbyScanner("/file.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
