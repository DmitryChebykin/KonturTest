package repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


public class DB {

    private ArrayList<String[]> dataRules;
    private ArrayList<LinkedList> groupsMeasures;

    public HashSet<String> getUniqueMeasure() {
        return uniqueMeasure;
    }

    public void setUniqueMeasure(HashSet<String> uniqueMeasure) {
        this.uniqueMeasure = uniqueMeasure;
    }

    private HashSet<String> uniqueMeasure;

    public HashMap<String, Integer> getTableTypeMeasures() {
        return tableTypeMeasures;
    }

    private HashMap<String, Integer> tableTypeMeasures;

    public DB() {
        groupsMeasures = new ArrayList<LinkedList>();
        tableTypeMeasures = new HashMap<String,Integer>();
    }


    public ArrayList<String[]> getDataRules() {
        return dataRules;
    }

    public void setDataRules(ArrayList<String[]> dataRules) {
        this.dataRules = dataRules;
    }

    private List getFilteredRows (String measure){

        @SuppressWarnings("UnnecessaryLocalVariable") AtomicReference<List<String[]>> filteredList = new AtomicReference<>(dataRules.stream().filter(e -> e[0].equals(measure) || e[1].equals(measure)).collect(Collectors.toList()));

        return filteredList.get();
    }

    public ArrayList<Integer> getIndexesFilteredRows(String measure){
        ArrayList<Integer> rowsIndexes = new ArrayList<Integer>();
        List<String[]> filteredList = new ArrayList<>();
        for (String[] e : dataRules) {
            if (e[0].equals(measure) || e[1].equals(measure)) {
                filteredList.add(e);
                rowsIndexes.add(dataRules.indexOf(e));
            }
        }

        return rowsIndexes;


    }


    private List getUniqueMeasures (List<String[]> list){

        SortedSet <String> temp = new TreeSet<String>();
        List <String> measures = new LinkedList<String>();
        list.forEach(e -> temp.addAll(Arrays.asList(String.valueOf(e[0]), String.valueOf(e[1]))));
        measures.addAll(temp);
        return measures;
        }

    private List getFilteredRows (List<String> measures){
        List<String[]> filteredList = new LinkedList<String[]>();
        for(String e: measures){
            filteredList.addAll(getFilteredRows(e));
        }
        return filteredList;
    }

    private List findSameTypeMeasures(String measure){
        List filteredRows = getFilteredRows(measure);
        List uniqueMeasures = getUniqueMeasures(filteredRows);
        int prevStep = uniqueMeasures.size();
        int nextStep = -1;
        do {
            filteredRows = getFilteredRows(uniqueMeasures);
            uniqueMeasures = getUniqueMeasures(filteredRows);
            prevStep = nextStep;
            nextStep = uniqueMeasures.size();
        }
        while(nextStep > prevStep);
        return uniqueMeasures;
    }

    public void makeGroupsMeasures(){
        List temp;
        List <String> measures = getUniqueMeasures(dataRules);
        while(!measures.isEmpty()) {
            String e = measures.get(0);
            temp = findSameTypeMeasures(e);
            groupsMeasures.add((LinkedList) temp);
            measures.removeAll(temp);
            }
        }

    public void makeTableTypeMeasures() {
        for (List list : groupsMeasures){
            for (int i = 0; i < list.size(); i++){
                Integer value = groupsMeasures.indexOf(list);
                String index = (String) list.get(i);
                tableTypeMeasures.put(index, value);
            }
        }
    }

    public String getPair(String Msr, int RowIndex) {
        if (dataRules.get(RowIndex)[0].equals(Msr)) return dataRules.get(RowIndex)[1];
        else
        return dataRules.get(RowIndex)[0];
    }

    public void makeUniqueMeasure(){


        HashSet<String> tempSet = new HashSet<>();
        tempSet.addAll(getUniqueMeasures(dataRules));
        setUniqueMeasure(tempSet);

    }
}
