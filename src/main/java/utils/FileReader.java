package utils;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class FileReader {

    public ArrayList<String[]> records;

    public void ReadCSVbyScanner(String fileMeasurePath) {
        records = new ArrayList<>();
        System.out.println("кодировка  системы" + System.getProperty("file.encoding"));
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileMeasurePath));
            CharsetDetector cd = new CharsetDetector();
            cd.setText(bis);
            CharsetMatch cm = cd.detect();
            System.out.println("кодировка  файла " + cm.getName());
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileMeasurePath), cm.getName());

            BufferedReader reader = new BufferedReader(inputStreamReader);
            while (true){
                String line = reader.readLine();
                if (line.isEmpty()) break;
                System.out.println(line);
                String [] arrStrings = line.split("\\s*,\\s*");
                records.add(arrStrings);
            }
        } catch (IOException exception){
            exception.getMessage();
        }
        records.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

}