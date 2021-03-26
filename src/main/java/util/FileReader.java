package util;

import repository.DB;

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

            DB dbObject = new DB();
            dbObject.setDataRules((ArrayList<String[]>) records);
            dbObject.setGroupsMeasures();
            dbObject.setTableTypeMeasures();
            dbObject.getIndexesFilteredRows("удав");

            //CalcResponsePostService calcResponsePostService = new CalcResponsePostService();


            //double res = calcResponsePostService.ReturnRatioOfTwoMeasure("миля","удав",records);
            //ArrayList<SortedSet> exprList = new ArrayList<SortedSet>();
            //exprList = calcResponsePostService.parse("   км * м *     сек* час /     миля * попугай *     удав * сажень");
            //exprList = calcResponsePostService.parse("   км * м *     сек* час /     км * м *     сек* час");

            //boolean check = calcResponsePostService.IsConversionAvailabilityByTypesOfMeasures(exprList, exprList, records);
            //boolean check1 = calcResponsePostService.IsConversionAvailabilityByCountsOfMeasures(exprList, exprList, records);
            //boolean check2 = calcResponsePostService.IsConversionPossibleByExpression(exprList, exprList, records);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
