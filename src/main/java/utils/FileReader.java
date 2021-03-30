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
        System.out.println("кодировка " + System.getProperty("file.encoding"));
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileMeasurePath));
            CharsetDetector cd = new CharsetDetector();
            cd.setText(bis);
            CharsetMatch cm = cd.detect();
            transform(new File(fileMeasurePath), cm.getName(),new File(fileMeasurePath + "1"),StandardCharsets.UTF_8.toString());

            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileMeasurePath+ "1"), StandardCharsets.UTF_8);

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

    public void transform(File source, String srcEncoding, File target, String tgtEncoding) throws IOException {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source), srcEncoding));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), tgtEncoding)); ) {
            char[] buffer = new char[16384];
            int read;
            while ((read = br.read(buffer)) != -1)
                bw.write(buffer, 0, read);
        }
    }
}