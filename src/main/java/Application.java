import Util.FileMeasureReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Application {



    public void Run(String path) {
        System.out.println(path);
        FileMeasureReader fmr = new FileMeasureReader();
        try {
            fmr.ReadCSVbyScanner("/file.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
