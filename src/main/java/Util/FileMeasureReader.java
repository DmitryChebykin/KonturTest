package Util;

import Service.CalcResponsePostService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileMeasureReader {

    //private ArrayList<String[]> records;
    //private String FileMeasurePath;

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
                        System.out.println(line[index]);
                    }
                    else
                        System.out.println("invalid data::" + data);
                    index++;
                }
                System.out.println(Arrays.toString(line));
                records.add(line);
            }
            CalcResponsePostService calcResponsePostService = new CalcResponsePostService();
            calcResponsePostService.filingDB(records);
            for (int i = 0; i < records.size(); i++) {
                System.out.println(Arrays.toString(records.get(i)));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
