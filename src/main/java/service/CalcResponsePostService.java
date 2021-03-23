package service;

import dto.RequestDto;
import repository.H2MemoryDatabase;
import config.BootStrap;

import java.util.*;
import java.util.stream.Collectors;


public class CalcResponsePostService {

    String[] DTOHandlerResult;

    private H2MemoryDatabase h2MemoryDatabase;

    public CalcResponsePostService() {
        this.h2MemoryDatabase = new H2MemoryDatabase();
        BootStrap.Init();
    }

    public void filingDB(List<String[]> records) {
        h2MemoryDatabase.AddArrayListToTable(records);
    }

    public String getResult(RequestDto input) {

        String[] arrMeasureFrom = parse(input.getFrom());
        String[] arrMeasureTo = parse(input.getFrom());

        int froms = h2MemoryDatabase.getCountFroms();
        int tos = h2MemoryDatabase.getCountTos();

        if (froms == 0 && tos == 0) {
            System.out.println("Error");
            throw new NullPointerException();
        }

        if (arrMeasureFrom.length != arrMeasureTo.length) {
            System.out.println("Error");
            throw new ArithmeticException();
        }


        return null;
    }

    private String[] parse(String from) {
        return null;
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
        while (queue.size()!= 0) {
            int i = queue.getFirst();
            if(startRow == -1) startRow = i;
            if(IsMeasureInRow(list.get(i), measure2)) {finalRow = i; break;}
            else{
    //            System.out.println(Arrays.toString(list.get(i)));
            secondMeasure = GetAnotherMeasureInRow(i, currentMeasure, list);
            visitedRow.add(i);
            queue.removeFirst();
            childs = ReturnAdditionalRowsWithOtherMeasure(list,i,secondMeasure);
            for (Integer e: childs){
                parentChild.add(new int[]{e.intValue(), i});
            }
            queue.addAll(childs);
            currentMeasure = secondMeasure;}


        }
        System.out.println("Start row : " + startRow + " " + Arrays.toString(list.get(startRow)));
        System.out.println("End row : " + finalRow + " " + Arrays.toString(list.get(finalRow)));
        LinkedList<Integer> way = buildWayFromStartRowToFinalRow(parentChild, startRow, finalRow);
        for (Integer e: way){
            System.out.println(Arrays.toString(list.get(e)));
        }
        double ratio = getRatio(measure2, way, list);
        return ratio;
        }




    public  List<Integer> ReturnAdditionalRowsWithOtherMeasure(List<String[]> fullList, int numberCurrentRow, String otherMeasure) {
        List<Integer> listIndex = new ArrayList();
        for (int i = 0; i < fullList.size(); i++) {
            String[] e = fullList.get(i);
            if (i == numberCurrentRow) continue;
            else if (e[0].equals(otherMeasure) || e[1].equals(otherMeasure)) {
                listIndex.add(i);
            }
        }
        return listIndex;
    }
    public  List<Integer> ReturnAllRowsWithThisMeasure(List<String[]> fullList, String otherMeasure) {
        List<Integer> listIndex = new ArrayList();
        for (int i = 0; i < fullList.size(); i++) {
            String[] e = fullList.get(i);
            if (e[0].equals(otherMeasure) || e[1].equals(otherMeasure)) {
                listIndex.add(i);
            }
        }
        return listIndex;
    }
    public boolean IsMeasureInRow(String row[], String measure){
        if (measure.equals(row[0]) || measure.equals(row[1]) ) return true;
        return false;
    }

    public String GetAnotherMeasureInRow(int rowNumber, String InitMeasure, List<String[]> list){
    String[] convertString = list.get(rowNumber);
    String AnotherMeasureInRow;
    if(convertString[0].equals(InitMeasure)) AnotherMeasureInRow = convertString[1];
        else AnotherMeasureInRow = convertString[0];
        return AnotherMeasureInRow;
    }

    public LinkedList <Integer> buildWayFromStartRowToFinalRow (List<int[]> parentChild, int startRow, int finalRow){
        LinkedList<Integer> way = new LinkedList<Integer>();
        List<int[]> filteredList = new ArrayList<int[]>();
        way.add(finalRow);
        int childRow =  finalRow;
        int parentRow = -1;
        int[] element;
        while (!(way.getLast().equals(startRow))){
            int finalChildRow = childRow;
            filteredList = parentChild.stream().filter(e -> e[0] == finalChildRow).collect(Collectors.toList());
            element  = filteredList.get(0);
            parentRow = element[1];
            way.add(parentRow);
            childRow = parentRow;
        }
        return way;
    }

    public double getRatio(String measure2, LinkedList <Integer> way, List<String[]> list){
        double ratio = 1.0f;
        double ratioTemp = 1.0f;
        String childMeasure;
        String[] element;
        childMeasure = measure2;
        for (Integer e : way){
            element = list.get(e);
            ratioTemp = Double.parseDouble(element[2]);
            if(childMeasure.equals(element[0])){ratio = ratio/ratioTemp;}
            else{ratio = ratio*ratioTemp;}
            childMeasure = GetAnotherMeasureInRow(e, childMeasure, list);
        }
        System.out.println("ratio = " + ratio);
        return 0f;
    }

}
