package repository;

import java.util.*;
import java.util.stream.Collectors;


public class DB {

    private ArrayList<String[]> dataRules;

    private final ArrayList<LinkedList<String>> groupsMeasures;

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

    private final HashMap<String, Integer> tableTypeMeasures;

    public DB() {
        groupsMeasures = new ArrayList<>();
        tableTypeMeasures = new HashMap<>();
    }


    public ArrayList<String[]> getDataRules() {
        return dataRules;
    }

    public void setDataRules(ArrayList<String[]> dataRules) {
        this.dataRules = dataRules;
    }

    private List<String[]> getFilteredRows(String measure) {

        return dataRules.stream().filter(e -> e[0].equals(measure) || e[1].equals(measure)).collect(Collectors.toList());

    }

    public ArrayList<Integer> getIndexesFilteredRows(String measure) {
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

    private List<String> getUniqueMeasures(List<String[]> list) {
        SortedSet<String> temp = new TreeSet<>();
        list.forEach(e -> temp.addAll(Arrays.asList(String.valueOf(e[0]), String.valueOf(e[1]))));
        return new LinkedList<>(temp);
    }

    private List<String[]> getFilteredRows(List<String> measures) {
        List<String[]> filteredList = new LinkedList<String[]>();
        for (String e : measures) {
            filteredList.addAll(getFilteredRows(e));
        }
        return filteredList;
    }

    private LinkedList<String> findSameTypeMeasures(String measure) {
        List<String[]> filteredRows = getFilteredRows(measure);
        List<String> uniqueMeasures = getUniqueMeasures(filteredRows);
        int prevStep;
        int nextStep = -1;
        do {
            filteredRows = getFilteredRows(uniqueMeasures);
            uniqueMeasures = getUniqueMeasures(filteredRows);
            prevStep = nextStep;
            nextStep = uniqueMeasures.size();
        }
        while (nextStep > prevStep);
        return (LinkedList<String>) uniqueMeasures;
    }

    public void makeGroupsMeasures() {
        LinkedList<String> temp;
        List<String> measures = getUniqueMeasures(dataRules);
        while (!measures.isEmpty()) {
            String e = measures.get(0);
            temp = findSameTypeMeasures(e);
            groupsMeasures.add(temp);
            measures.removeAll(temp);
        }
    }

    public void makeTableTypeMeasures() {
        for (List<String> list : groupsMeasures) {
            for (int i = 0; i < list.size(); i++) {
                Integer value = groupsMeasures.indexOf(list);
                String index = list.get(i);
                tableTypeMeasures.put(index, value);
            }
        }
    }

    public String getPair(String Msr, int RowIndex) {
        if (dataRules.get(RowIndex)[0].equals(Msr)) return dataRules.get(RowIndex)[1];
        else
            return dataRules.get(RowIndex)[0];
    }

    public void makeUniqueMeasure() {
        HashSet<String> tempSet = new HashSet<>(getUniqueMeasures(dataRules));
        setUniqueMeasure(tempSet);
    }
}
