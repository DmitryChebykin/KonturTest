package service;
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
                    int[] el = {nextIndex.intValue(), currentIndex};
                    pool.linkedRows.add(el);
                    stack.put(nextIndex, otherMeasure);
                }
            }

        }
        for (int[] e : pool.linkedRows) {
            System.out.println("Итерация № " + pool.linkedRows.indexOf(e));
            System.out.println("строка " + e[0] + " " + Arrays.toString(db.getDataRules().get(e[0])));
            System.out.println("строка " + e[0] + " " + Arrays.toString(db.getDataRules().get(e[1])));
        }
        return pool.linkedRows;
    }

    public double getRatio(String measureFrom, String measureTo, DB db) {
        double ratio = 1.0f;
        double ratioTemp = 1.0f;
        String iterateMeasure;
        String[] element;
        //iterateMeasure = measureFrom;
        LinkedList<int[]> conversionSteps = (LinkedList<int[]>) getConversionRows(measureFrom, measureTo, db);
        int[] start = conversionSteps.getLast();
        //conversionSteps.removeLast();
        iterateMeasure = db.getDataRules().get(start[0])[0];
        //conversionSteps.removeFirst();
        while (!(conversionSteps.indexOf(start) == 0 )) {
            //System.out.println(Arrays.toString(start));
            List<int[]> filteredList;
            List<int[]> list = new ArrayList<>();
            for (int[] e : conversionSteps) {
                if (e[0] == start[1]) {
                    list.add(e);
                }
            }
            filteredList = list;
            int[] filteredElement = filteredList.get(0);
            start = filteredElement;
            element = db.getDataRules().get(filteredElement[0]);
            ratioTemp = Double.parseDouble(element[2]);
            if (iterateMeasure.equals(element[0])) {
                iterateMeasure = element[1];
                ratio = ratio * ratioTemp;
            } else {
                ratio = ratio / ratioTemp;
                iterateMeasure = element[0];
            }


//        }
//        for (int[] e : conversionSteps) {
//            element = db.getDataRules().get(e);
//            ratioTemp = Double.parseDouble(element[2]);
//            if (childMeasure.equals(element[0])) {
//                ratio = ratio / ratioTemp;
//            } else {
//                ratio = ratio * ratioTemp;
//            }
//            childMeasure = GetAnotherMeasureInRow(e, childMeasure, list);
//        }
//        System.out.println("ratio = " + ratio);
            //System.out.println(ratio);

        }
        ratio = 1f/ratio;
        System.out.println("преобразование " + measureFrom + "   "+measureTo);
        System.out.println("final ratio = " + ratio);
        return ratio;
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
