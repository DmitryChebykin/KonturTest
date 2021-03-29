package handlers;
import repository.DB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class HandlerDB {

    public List<int[]> getConversionRows(String measureFrom, String measureTo, DB db) {
        LinkedList<Integer> queue = new LinkedList<>();
        HashMap<Integer, String> stack = new HashMap<>();
        //detect rows on first iterate
        db.getIndexesFilteredRows(measureFrom).forEach(e -> {
            queue.add(e);
            stack.put(e, measureFrom);
        });

        Pool pool = new Pool();
        pool.linkedRows.add(new int[]{queue.getFirst(), queue.getFirst()});
        while (queue.size() != 0) {
            pool.tempChildren.clear();
            int currentIndex = queue.getFirst();
            if (Arrays.asList((db.getDataRules().get(currentIndex))).contains(measureTo)) {
                pool.linkedRows.add(new int[]{queue.getFirst(), queue.getFirst()});
                queue.clear();
            } else {
                queue.removeFirst();
                String otherMeasure = db.getPair(stack.get(currentIndex), currentIndex);
                pool.tempChildren.addAll(db.getIndexesFilteredRows(otherMeasure));
                pool.tempChildren.removeIf(number -> number == currentIndex);
                queue.addAll(pool.tempChildren);

                for (Integer nextIndex : pool.tempChildren) {
                    pool.linkedRows.add(new int[]{nextIndex, currentIndex});
                    stack.put(nextIndex, otherMeasure);
                }
            }

        }
        return pool.linkedRows;
    }

    public BigDecimal getRatio(String measureFrom, String measureTo, DB db) {
        BigDecimal ratio = BigDecimal.valueOf(1.0f);
        BigDecimal ratioTemp;
        String iterateMeasure;
        String[] element;
        iterateMeasure = measureTo;
        LinkedList<int[]> conversionSteps = (LinkedList<int[]>) getConversionRows(measureFrom, measureTo, db);
        ArrayList<Integer> temp = normaliseLinkedRows(conversionSteps);
        if(temp.size() == 1) temp.add(temp.get(0));
        for (Integer e : temp) {
            element = db.getDataRules().get(e);
            ratioTemp = BigDecimal.valueOf(Double.parseDouble(element[2]));
            if (temp.get(0).equals(temp.get(1))) {
                if (iterateMeasure.equals(element[0])) {
                    ratio = ratio.divide(ratioTemp, 15, RoundingMode.HALF_UP);
                } else {
                    ratio = ratio.multiply(ratioTemp);
                }
                break;}
            if (iterateMeasure.equals(element[0])) {
                iterateMeasure = element[1];
                ratio = ratio.divide(ratioTemp, 15, RoundingMode.HALF_UP);
            } else {
                ratio = ratio.multiply(ratioTemp);
                iterateMeasure = element[0];
            }
        }
        return ratio;
    }

    public ArrayList<Integer> normaliseLinkedRows(LinkedList<int[]> linkedRows) {
        int[] array;
        ArrayList<Integer> normalise = new ArrayList<>();
        array = linkedRows.removeLast();

        normalise.add(array[0]);
        int tem1 = -2;
        if (normalise.isEmpty()) {
            return normalise;
        } else {
            List<int[]> nextElement = linkedRows.stream().filter(e -> e[0] == array[0]).collect(Collectors.toList());
            while (!(tem1 == 0) && nextElement.size() > 0 ) {
                normalise.add(nextElement.get(0)[1]);
                int i = nextElement.get(0)[1];
                nextElement = linkedRows.stream().filter(e -> e[0] == i).collect(Collectors.toList());
                if (nextElement.isEmpty()) break;
                tem1 = linkedRows.indexOf(nextElement.get(0));
            }
            return normalise;
        }
    }

    private class Pool {
        List<int[]> linkedRows;
        List<Integer> tempChildren;

        public Pool() {
            linkedRows = new LinkedList<>();
            tempChildren = new ArrayList<>();
        }
    }
}