package service;

import java.util.*;
import java.util.stream.Collectors;


public class PostService {

    String[] DTOHandlerResult;




    public ArrayList<SortedSet> parse(String stringMeasuresExpression) {
        String someString;
        SortedSet<String> num = new TreeSet<String>();
        SortedSet<String> denom = new TreeSet<String>();
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
        return (ArrayList<SortedSet>) exprList;
    }

    public SortedSet DefineAllOtherMeasure(String measure, List<String[]> list) {

        SortedSet<String> example = new TreeSet<String>();

        List<String[]> filteredList = list.stream().filter(e -> e[0].equals(measure) || e[1].equals(measure)).collect(Collectors.toList());

        filteredList.forEach(e -> example.addAll(Arrays.asList(String.valueOf(e[0]), String.valueOf(e[1]))));

        //List<String[]> anotherFilteredList = new ArrayList<>();

        int i = example.toArray().length;
        boolean logic = true;
        while (logic) {
            List<String[]> anotherFilteredList = new ArrayList<>();

            for (String entry : example) {
                filteredList = list.stream().filter(e -> e[0].equals(entry) || e[1].equals(entry)).collect(Collectors.toList());
                anotherFilteredList.addAll(filteredList);

            }
            anotherFilteredList = anotherFilteredList.stream().collect(Collectors.toList());
            anotherFilteredList.forEach(e -> example.addAll(Arrays.asList(String.valueOf(e[0]), String.valueOf(e[1]))));
            int j = example.toArray().length;
            if (j > i) {
                i = j;
                continue;
            } else {
                break;
            }

        }
        return example;
    }

    public double ReturnRatioOfTwoMeasure(String measure1, String measure2, List<String[]> list) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        List<Integer> visitedRow = new ArrayList();
        List<int[]> parentChild = new ArrayList();
        List<Integer> childs = new ArrayList();
        int finalRow = -1;
        int startRow = -1;
        String lastNode = "";
        String secondMeasure;
        String currentMeasure = measure1;
        queue.addAll(ReturnAllRowsWithThisMeasure(list, currentMeasure));
        while (queue.size() != 0) {
            int i = queue.getFirst();
            if (startRow == -1) startRow = i;
            if (IsMeasureInRow(list.get(i), measure2)) {
                finalRow = i;
                break;
            } else {
                //            System.out.println(Arrays.toString(list.get(i)));
                secondMeasure = GetAnotherMeasureInRow(i, currentMeasure, list);
                visitedRow.add(i);
                queue.removeFirst();
                childs = ReturnAdditionalRowsWithOtherMeasure(list, i, secondMeasure);
                for (Integer e : childs) {
                    parentChild.add(new int[]{e.intValue(), i});
                }
                queue.addAll(childs);
                currentMeasure = secondMeasure;
            }


        }
        System.out.println("Start row : " + startRow + " " + Arrays.toString(list.get(startRow)));
        System.out.println("End row : " + finalRow + " " + Arrays.toString(list.get(finalRow)));
        LinkedList<Integer> way = buildWayFromStartRowToFinalRow(parentChild, startRow, finalRow);
        for (Integer e : way) {
            System.out.println(Arrays.toString(list.get(e)));
        }
        double ratio = getRatio(measure2, way, list);
        return ratio;
    }

    public List<Integer> ReturnAdditionalRowsWithOtherMeasure(List<String[]> fullList, int numberCurrentRow, String otherMeasure) {
        List<Integer> listIndex = new ArrayList();
        for (int i = 0; i < fullList.size(); i++) {
           if ((fullList.get(i)[0].equals(otherMeasure) || fullList.get(i)[1].equals(otherMeasure)) && i != numberCurrentRow) {
                listIndex.add(i);
            }
        }
        return listIndex;
    }

    public List<Integer> ReturnAllRowsWithThisMeasure(List<String[]> fullList, String otherMeasure) {
        List<Integer> listIndex = new ArrayList();
        for (int i = 0; i < fullList.size(); i++) {
            String[] e = fullList.get(i);
            if (e[0].equals(otherMeasure) || e[1].equals(otherMeasure)) {
                listIndex.add(i);
            }
        }
        return listIndex;
    }

    public boolean IsMeasureInRow(String row[], String measure) {
        if (measure.equals(row[0]) || measure.equals(row[1])) return true;
        return false;
    }

    public String GetAnotherMeasureInRow(int rowNumber, String InitMeasure, List<String[]> list) {
        String[] convertString = list.get(rowNumber);
        String AnotherMeasureInRow;
        if (convertString[0].equals(InitMeasure)) AnotherMeasureInRow = convertString[1];
        else AnotherMeasureInRow = convertString[0];
        return AnotherMeasureInRow;
    }

    public LinkedList<Integer> buildWayFromStartRowToFinalRow(List<int[]> parentChild, int startRow, int finalRow) {
        LinkedList<Integer> way = new LinkedList<Integer>();
        List<int[]> filteredList = new ArrayList<int[]>();
        way.add(finalRow);
        int childRow = finalRow;
        int parentRow = -1;
        int[] element;
        while (!(way.getLast().equals(startRow))) {
            int finalChildRow = childRow;
            filteredList = parentChild.stream().filter(e -> e[0] == finalChildRow).collect(Collectors.toList());
            element = filteredList.get(0);
            parentRow = element[1];
            way.add(parentRow);
            childRow = parentRow;
        }
        return way;
    }

    public double getRatio(String measure2, LinkedList<Integer> way, List<String[]> list) {
        double ratio = 1.0f;
        double ratioTemp = 1.0f;
        String childMeasure;
        String[] element;
        childMeasure = measure2;
        for (Integer e : way) {
            element = list.get(e);
            ratioTemp = Double.parseDouble(element[2]);
            if (childMeasure.equals(element[0])) {
                ratio = ratio / ratioTemp;
            } else {
                ratio = ratio * ratioTemp;
            }
            childMeasure = GetAnotherMeasureInRow(e, childMeasure, list);
        }
        System.out.println("ratio = " + ratio);
        return 0f;
    }

    public boolean IsConversionAvailabilityByTypesOfMeasures(ArrayList<SortedSet> from, ArrayList<SortedSet> to, List<String[]> list) {
        boolean availability = true;
        Set<String> measuresInRequest = new HashSet<String>();
        measuresInRequest.addAll(from.get(0));
        measuresInRequest.addAll(from.get(1));
        measuresInRequest.addAll(to.get(0));
        measuresInRequest.addAll(to.get(1));

        for (String measure : measuresInRequest) {
            boolean temp = false;
            for (int j = 0; j < list.size(); j++) {
                String[] e = list.get(j);
                if (e[0].equals(measure) || e[1].equals(measure)) {
                    temp = temp || true;
                    break;
                } else {
                    temp = temp || false;
                }
            }
            if (temp) {
                availability = availability & temp;
            } else {
                availability = false;
                break;
            }
        }
        System.out.println();
        return availability;
    }

    public boolean IsConversionAvailabilityByCountsOfMeasures(ArrayList<SortedSet> from, ArrayList<SortedSet> to, List<String[]> list) {

        if (from.get(0).size() != to.get(0).size()) {
            return false;
        } else if (from.get(1).size() != to.get(1).size()) {
            return false;
        }
        return true;
    }

    public boolean IsConversionPossibleByCountsOfMeasure(ArrayList<SortedSet> from, ArrayList<SortedSet> to, List<String[]> list) {
        // Метод проверяет возможность преобразования по соотетствию типов переменных.
        // Особенности:
        // Метод работает с  проверенными  from: и  to:, т.е. на раннем этапе мы уже убедились, что
        // в теле запроса содержатся корректные единицы измерения (они есть в нашей базе в файле files.csv),
        // и количество переменных в числителях(знаменателях) у from: и to: - одинаковое.
        // Метод проверяет, а одинаковый ли набор типов единиц измерения в числителях/знаменателях.
        // Например в числителе from
        Set<String> temp = new HashSet<String>();
        SortedSet<String> example = new TreeSet<String>();
        List<String> tempList = new LinkedList<String>();
        List typesMeasureUp = new ArrayList<LinkedList>();
        List typesMeasureDown = new ArrayList<LinkedList>();

        temp.addAll(from.get(0));

        for (String e : temp) {
            example = DefineAllOtherMeasure(e, list);
            tempList.addAll(example);
            typesMeasureUp.add(tempList);
            tempList = new LinkedList<String>();
        }


        tempList = new LinkedList<String>();
        temp.clear();
        temp.addAll(from.get(1));


        for (String e : temp) {
            example = DefineAllOtherMeasure(e, list);
            tempList.addAll(example);
            typesMeasureDown.add(tempList);
            tempList = new LinkedList<String>();
        }

        System.out.println();
        Collections.sort(typesMeasureUp, new ListSorter());
        Collections.sort(typesMeasureDown, new ListSorter());
        if (Objects.deepEquals(typesMeasureUp, typesMeasureDown)) {
            int test = 123;
            System.out.println(test);
        }
        ;
        int test2 = 222;


        List<SortedSet> typesOfMeasures = new ArrayList<SortedSet>();

        return false;
    }


}
