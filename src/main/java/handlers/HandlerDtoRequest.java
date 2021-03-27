package handlers;

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

}
