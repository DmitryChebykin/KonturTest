package handlers;
import java.util.*;
import java.util.stream.Collectors;

public class HandlerDtoRequest {

    public ArrayList<String[]> parse(String stringMeasuresExpression) {
        String someString;
        ArrayList<String[]> num = new ArrayList();
        ArrayList<String[]> denom = new ArrayList();
        List exprList = new ArrayList<String[]>();
        String[] numerator;
        String[] denominator;
        //someString = "   км * м *     с* ч /     миля * попугай *     удав * сажень";
        someString = stringMeasuresExpression;
        String numeratorString = null;
        String denominatorString;
        if(someString == null || someString.trim().isEmpty()){
            exprList.add(new String[]{" "});
            exprList.add(new String[]{" "});
            return (ArrayList<String[]>) exprList;
        }
        if(someString.contains("/")){
            String[] arrString = someString.split("/");
            numeratorString = arrString[0].replaceAll("\\s|\\*", " ");
            denominatorString = arrString[1].replaceAll("\\s|\\*", " ");
            numeratorString = numeratorString.trim();
            denominatorString = denominatorString.trim();
            numerator = numeratorString.split("[\\s]+");
            denominator = denominatorString.split("[\\s]+");
        }
        else{numeratorString = someString.replaceAll("\\s|\\*", " ");
            numeratorString = numeratorString.trim();
            numerator = numeratorString.split("[\\s]+");
            denominator = new String[]{" "};

        }

        exprList.add(numerator);
        exprList.add(denominator);

        return (ArrayList<String[]>) exprList;
    }

    public ArrayList<String[]> getFullFraction(ArrayList<String[]> fromParsed, ArrayList<String[]> toParsed) {

        ArrayList<String[]> fullFraction = new ArrayList<>();

        String[] numerator = Arrays.copyOf(fromParsed.get(0), fromParsed.get(0).length + toParsed.get(1).length);
        System.arraycopy(toParsed.get(1), 0, numerator, fromParsed.get(0).length, toParsed.get(1).length);
        numerator = Arrays.stream(numerator).filter(s -> !s.equals(" ")).toArray(String[]::new);

        String[] denomenator = Arrays.copyOf(fromParsed.get(1), fromParsed.get(1).length + toParsed.get(0).length);
        System.arraycopy(toParsed.get(0), 0, denomenator, fromParsed.get(1).length, toParsed.get(0).length);
        denomenator = Arrays.stream(denomenator).filter(s -> !s.equals(" ")).toArray(String[]::new);

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
        boolean check = Objects.deepEquals(from, to);
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

