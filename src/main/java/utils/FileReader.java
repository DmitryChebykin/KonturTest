package utils;

import java.io.*;
import java.util.*;

public class FileReader {

    public ArrayList<String[]> records;

    public void ReadCSVbyScanner(String FileMeasurePath) {
        records = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FileMeasurePath)));
            while (true){
                String line = reader.readLine();
                if (line.isEmpty()) break;
                System.out.println(line);
                String [] arrStrings = line.split("\\s*,\\s*");
                records.add(arrStrings);
            }
        } catch (Exception exception){
            exception.getMessage();
        }
        records.forEach(System.out::println);

//        try {
//            Scanner scanner = new Scanner(new File(FileMeasurePath));
//            while (scanner.hasNextLine()) {
//                Scanner dataScanner = new Scanner(scanner.nextLine());
//                dataScanner.useDelimiter("[\\s,]+");
//                int index = 0;
//                String[] line = new String[3];
//                while (dataScanner.hasNext()) {
//                    String data = dataScanner.next();
//                    if (index < 3) {
//                        line[index] = data;
//                    } else
//
//                    index++;
//                }
//                records.add(line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}