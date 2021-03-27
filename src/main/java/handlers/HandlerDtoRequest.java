package handlers;
import java.util.*;

public class HandlerDtoRequest {

    public ArrayList<String[]> parse(String stringMeasuresExpression) {
        String someString;
        SortedSet<String> num = new TreeSet();
        SortedSet<String> denom = new TreeSet();
        List exprList = new ArrayList<String[]>();
        //someString = "   км * м *     с* ч /     миля * попугай *     удав * сажень";
        someString = stringMeasuresExpression;
        String[] arrString = someString.split("/");
        String numeratorString = arrString[0].replaceAll("\\s|\\*", " ");
        String denominatorString = arrString[1].replaceAll("\\s|\\*", " ");
        numeratorString = numeratorString.trim();
        denominatorString = denominatorString.trim();
        String[] numerator = numeratorString.split("[\\s]+");
        String[] denominator = denominatorString.split("[\\s]+");
        num.addAll(Arrays.asList(numerator));
        denom.addAll(Arrays.asList(denominator));
        System.out.println();
        exprList.add(num);
        exprList.add(denom);
        System.out.println();
        return (ArrayList<String[]>) exprList;
    }

    public ArrayList<String[]> getFullFraction(ArrayList<String[]> fromParsed, ArrayList<String[]> toParsed) {
        ArrayList<String[]> fullFraction = new ArrayList<>();

        String[] numerator = Arrays.copyOf(fromParsed.get(0), fromParsed.get(0).length + toParsed.get(1).length);
        System.arraycopy(toParsed.get(1), 0, numerator, fromParsed.get(0).length, toParsed.get(1).length);

        String[] denomenator = Arrays.copyOf(fromParsed.get(1), fromParsed.get(1).length + toParsed.get(0).length);
        System.arraycopy(toParsed.get(0), 0, denomenator, fromParsed.get(1).length, toParsed.get(0).length);

        fullFraction.add(0, numerator);
        fullFraction.add(1, denomenator);
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

        TreeSet from = new TreeSet<String>();
        TreeSet to = new TreeSet<String>();


        String [] numerator = fullFraction.get(0);
        String [] denominator = fullFraction.get(1);
        Arrays.stream(numerator).forEach(e -> from.add(tableTypeMeasures.get(e)));
        Arrays.stream(denominator).forEach(e -> to.add(tableTypeMeasures.get(e)));
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
}
