package handlers;
import java.util.*;
import java.util.stream.Collectors;

public class HandlerDtoRequest {

    public ArrayList<String[]> parse(String stringMeasuresExpression) {
        String someString;
        ArrayList<String[]> exprList = new ArrayList<>();
        String[] numerator = new String[0];
        String[] denominator = new String[0];
        //someString = "   км * м *     с* ч /     миля * попугай *     удав * сажень";
        someString = stringMeasuresExpression;
        String numeratorString = null;
        String denominatorString;
        if(someString == null || someString.trim().isEmpty()){
            exprList.add(new String[0]);
            exprList.add(new String[0]);
            return exprList;
        }
        if(someString.contains("/")){
            String[] arrString = someString.split("/", -1);
            numeratorString = arrString[0].replaceAll("\\s|\\*", " ");
            denominatorString = arrString[1].replaceAll("\\s|\\*", " ");
            numeratorString = numeratorString.trim();
            denominatorString = denominatorString.trim();
            if (numeratorString.trim().isEmpty()){
                numerator = new String[0];
            }
            else {numerator = numeratorString.split("[\\s]+");}
            if (denominatorString.trim().isEmpty()){
                denominator = new String[0];
            }
            else{denominator = denominatorString.split("[\\s]+");}
        }
        else{numeratorString = someString.replaceAll("\\s|\\*", " ");
            numeratorString = numeratorString.trim();
            numerator = numeratorString.split("[\\s]+");
            denominator = new String[0];

        }

        exprList.add(numerator);
        exprList.add(denominator);

        return exprList;
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
        return fullFraction.get(0).length == fullFraction.get(1).length;
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
        HashSet<String> dtoUniqueMeasure = new HashSet<>();
        for (String[]f: fullFraction){
            dtoUniqueMeasure.addAll(Arrays.asList(f));

        }
        return uniqueMeasure.containsAll(dtoUniqueMeasure) && !dtoUniqueMeasure.isEmpty();
    }
}

