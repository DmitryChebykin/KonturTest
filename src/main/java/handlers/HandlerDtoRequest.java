package handlers;
import java.util.*;
import java.util.stream.Collectors;

public class HandlerDtoRequest {

    public ArrayList<String[]> parse(String stringMeasuresExpression) {
        String someString;
        ArrayList<String[]> num = new ArrayList();
        ArrayList<String[]> denom = new ArrayList();
        List exprList = new ArrayList<String[]>();
        //someString = "   км * м *     с* ч /     миля * попугай *     удав * сажень";
        someString = stringMeasuresExpression;
        if(someString == null || someString.trim().isEmpty()){
            exprList.add(null);
            exprList.add(null);
            return (ArrayList<String[]>) exprList;
        }
        String[] arrString = someString.split("/");
        String numeratorString = arrString[0].replaceAll("\\s|\\*", " ");
        String denominatorString = arrString[1].replaceAll("\\s|\\*", " ");
        numeratorString = numeratorString.trim();
        denominatorString = denominatorString.trim();
        String[] numerator = numeratorString.split("[\\s]+");
        String[] denominator = denominatorString.split("[\\s]+");

        exprList.add(numerator);
        exprList.add(denominator);

        return (ArrayList<String[]>) exprList;
    }

    public ArrayList<String[]> getFullFraction(ArrayList<String[]> fromParsed, ArrayList<String[]> toParsed) {
        ArrayList<String[]> fullFraction = new ArrayList<>();

        ArrayList<String> numerator = null;
        ArrayList<String> denomenator = null;

        try{
        numerator.addAll(Arrays.asList(fromParsed.get(0)));} catch (Exception e) {
            e.printStackTrace();
        }
        try{
            numerator.addAll(Arrays.asList(toParsed.get(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

        }
        try{
        denomenator.addAll(Arrays.asList(fromParsed.get(1)));} catch (Exception e) {
            e.printStackTrace();
        }
        try {
            denomenator.addAll(Arrays.asList(toParsed.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {fullFraction.add(0, (String[]) numerator.toArray());
            fullFraction.add(1, (String[]) denomenator.toArray());

        }

//        fullFraction.add(0, (String[]) numerator.toArray());
//        fullFraction.add(1, (String[]) denomenator.toArray());
        return fullFraction;
    }

    public boolean checkConversionEnable(ArrayList<String[]> fullFraction) {
        if (fullFraction.get(0).length == fullFraction.get(1).length) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkConversionEnable(ArrayList<String[]> fullFraction, HashMap<String, Integer> tableTypeMeasures){

        ArrayList<String> from = new ArrayList<>();
        ArrayList<String> to = new ArrayList<>();


        String [] numerator = fullFraction.get(0);
        String [] denominator = fullFraction.get(1);
        ArrayList<String> finalFrom = from;
        Arrays.stream(numerator).forEach(e -> finalFrom.add(String.valueOf(tableTypeMeasures.get(e))));
        ArrayList<String> finalTo = to;
        Arrays.stream(denominator).forEach(e -> finalTo.add(String.valueOf(tableTypeMeasures.get(e))));
        from = (ArrayList<String>) from.stream().sorted().collect(Collectors.toList());
        to = (ArrayList<String>) to.stream().sorted().collect(Collectors.toList());

        return Objects.deepEquals(from, to);
    }

    public boolean checkConversionEnable(ArrayList<String[]> fullFraction, HashSet<String> uniqueMeasure){
        HashSet<String> dtoUniqueMeasure = new HashSet();
        for (String[]f: fullFraction){
            Arrays.stream(f).forEach(num -> dtoUniqueMeasure.add(num));

        }
        boolean res = uniqueMeasure.containsAll(dtoUniqueMeasure);
        return uniqueMeasure.containsAll(dtoUniqueMeasure);

    }

}

