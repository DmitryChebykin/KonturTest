package handlers;

import repository.DB;

import java.util.*;

public class HandlerDtoRequest {


    public ArrayList<SortedSet> parse(String stringMeasuresExpression) {
        String someString;
        SortedSet<String> num = new TreeSet<String>();
        SortedSet<String> denom = new TreeSet<String>();
        List exprList = new ArrayList<String[]>();
        someString = "   км * м *     с* ч /     миля * попугай *     удав * сажень";
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
        return (ArrayList<SortedSet>) exprList;
    }
   public boolean checkMeasureInDatabase(String measure, DB db) {
       return db.getTableTypeMeasures().containsValue(measure);
   }
   public ArrayList<String[]> getFullFraction (ArrayList<String[]> fromParsed, ArrayList<String[]> toParsed){
       ArrayList<String[]> fullFraction = new ArrayList<String[]>();

       String[] numerator = Arrays.copyOf(fromParsed.get(0), fromParsed.get(0).length + toParsed.get(1).length);
       System.arraycopy(toParsed.get(1), 0, numerator, fromParsed.get(0).length, toParsed.get(1).length);

       String[] denomenator = Arrays.copyOf(fromParsed.get(1), fromParsed.get(1).length + toParsed.get(0).length);
       System.arraycopy(toParsed.get(0), 0, denomenator, fromParsed.get(1).length, toParsed.get(0).length);

       fullFraction.add(0, numerator);
       fullFraction.add(1, denomenator);
       return fullFraction;
   }
}
