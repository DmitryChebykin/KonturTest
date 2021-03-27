package handlers;

import repository.DB;

import java.util.*;
import java.util.stream.Collectors;

public class HandlerDB {

    public List<int[]> getConversionRows(String measureFrom, String measureTo, DB db) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        HashMap<Integer, String> stack = new HashMap<>();
        //detect rows on first iterate
        db.getIndexesFilteredRows(measureFrom).forEach(e -> {
            queue.add(e);
            stack.put(e, measureFrom);
        });
        String currentMsr = measureFrom;
        Pool pool = new Pool();
        pool.linkedRows.add(new int[]{queue.getFirst().intValue(), queue.getFirst().intValue()});
        while (queue.size() != 0) {
            pool.tempChilds.clear();
            int currentIndex = queue.getFirst();
            if (Arrays.stream((db.getDataRules().get(currentIndex))).anyMatch(measureTo::equals)) {
                pool.linkedRows.add(new int[]{queue.getFirst().intValue(), queue.getFirst().intValue()});
                queue.clear();
            } else {
                queue.removeFirst();
                //System.out.println(Arrays.toString(db.getDataRules().get(currentIndex)));
                String otherMeasure = db.getPair(stack.get(currentIndex), currentIndex);
                pool.tempChilds.addAll(db.getIndexesFilteredRows(otherMeasure));
                int finalCurrentIndex = currentIndex;
                pool.tempChilds.removeIf(number -> number == finalCurrentIndex);
                queue.addAll(pool.tempChilds);
                //System.out.println("дочерние строки");
                for (Integer e : pool.tempChilds) {
                    //System.out.println(Arrays.toString(db.getDataRules().get(e.intValue())));
                }
                //System.out.println("////////////////////////////");

                for (Integer nextIndex : pool.tempChilds) {
                    //new int[] el = {nextIndex.intValue(), currentIndex};
                    pool.linkedRows.add(new int[]{nextIndex.intValue(), currentIndex});
                    stack.put(nextIndex, otherMeasure);
                }
            }

        }
//        for (int[] e : pool.linkedRows) {
//            System.out.println("Итерация № " + pool.linkedRows.indexOf(e));
//            System.out.println("строка " + e[0] + " " + Arrays.toString(db.getDataRules().get(e[0])));
//            System.out.println("строка " + e[0] + " " + Arrays.toString(db.getDataRules().get(e[1])));
//        }
        return pool.linkedRows;
    }

    public double getRatio(String measureFrom, String measureTo, DB db) {
        double ratio = 1.0f;
        double ratioTemp = 1.0f;
        String iterateMeasure;
        String[] element;
        iterateMeasure = measureTo;
        LinkedList<int[]> conversionSteps = (LinkedList<int[]>) getConversionRows(measureFrom, measureTo, db);
        ArrayList<Integer> temp = normaliseLinkedRows(conversionSteps);
        for (Integer e : temp) {
            element = db.getDataRules().get(e);
            ratioTemp = Double.parseDouble(element[2]);
            if (iterateMeasure.equals(element[0])) {
                iterateMeasure = element[1];
                ratio = ratio / ratioTemp;
            } else {
                ratio = ratio * ratioTemp;
                iterateMeasure = element[0];
            }
            if (temp.get(0).equals(temp.get(1))) break;
            System.out.println(ratio);
        }

        //ratio = 1/ratio;
        System.out.println("преобразование " + measureFrom + "   " + measureTo);
        System.out.println("final ratio = " + ratio);
        return ratio;
    }

    public ArrayList<Integer> normaliseLinkedRows(LinkedList<int[]> linkedRows) {
        int[] array;
        ArrayList<Integer> normalise = new ArrayList<Integer>();
        array = linkedRows.removeLast();
        normalise.add(array[0]);
        int tem1 = -2;
        if (normalise.isEmpty()) {
            return normalise;
        } else {
            int[] finalArray = array;
            List<int[]> nextElenent = linkedRows.stream().filter(e -> e[0] == finalArray[0]).collect(Collectors.toList());
            while (!(tem1 == 0)) {
                normalise.add(nextElenent.get(0)[1]);
                int i = nextElenent.get(0)[1];
                nextElenent = linkedRows.stream().filter(e -> e[0] == i).collect(Collectors.toList());
                if (nextElenent.isEmpty()) break;
                tem1 = linkedRows.indexOf(nextElenent.get(0));

            }
            return normalise;
        }


    }

    private class Pool {
        List<int[]> linkedRows;
        List<Integer> tempChilds;

        public Pool() {
            linkedRows = new LinkedList<int[]>();
            tempChilds = new ArrayList<Integer>();
        }
    }

}
