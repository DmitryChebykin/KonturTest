package utils;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.*;
import java.util.ArrayList;

public class FileReader {

    public ArrayList<String[]> records;

    public void ReadCSVbyScanner(String fileMeasurePath) {
        records = new ArrayList<>();
        String line;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileMeasurePath));
            CharsetDetector cd = new CharsetDetector();
            cd.setText(bis);
            CharsetMatch cm = cd.detect();
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileMeasurePath),cm.getName());

            BufferedReader reader = new BufferedReader(inputStreamReader);
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s*,\\s*");
                records.add((values));
            }
        } catch (IOException exception){
            exception.getMessage();
        }
            }

}