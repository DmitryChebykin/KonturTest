package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {

    public ArrayList<String[]> records;


    public void ReadCSVbyScanner(String FileMeasurePath) throws FileNotFoundException {
        List<String[]> records = new ArrayList<>();

        FileMeasurePath = "file.csv";
        try {
            Scanner scanner = new Scanner(new File(FileMeasurePath));
            while (scanner.hasNextLine()) {
                Scanner dataScanner = new Scanner(scanner.nextLine());
                dataScanner.useDelimiter(",");
                int index = 0;
                String[] line = new String[3];
                while (dataScanner.hasNext()) {
                    String data = dataScanner.next();
                    if (index < 3){
                    line[index] = data;

                    }
                    else
                        System.out.println("invalid data::" + data);
                    index++;
                }

                records.add(line);
            }
            this.records = (ArrayList<String[]>) records;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
