package service;
import repository.DB;
import java.util.*;
public class HandlerDB {

    public List<int[]> getConversionRows(String measureFrom, String measureTo, DB db) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        HashMap <Integer, String> stack = new HashMap<>();
        //detect rows on first iterate
        db.getIndexesFilteredRows(measureFrom).forEach(e -> {
            queue.add(e);
            stack.put(e, measureFrom);
        });
        String currentMsr = measureFrom;
        Pool pool = new Pool();
        pool.linkedRows.add(new int[] {queue.getFirst().intValue(),queue.getFirst().intValue()});
        while (queue.size() != 0) {
            pool.tempChilds.clear();
            int currentIndex = queue.getFirst();
            if(Arrays.stream((db.getDataRules().get(currentIndex))).anyMatch(measureTo::equals)){
                pool.linkedRows.add(new int[] {queue.getFirst().intValue(),queue.getFirst().intValue()});
                queue.clear();
            }
            else{
                queue.removeFirst();
                System.out.println(Arrays.toString(db.getDataRules().get(currentIndex)));
                String otherMeasure = db.getPair(stack.get(currentIndex),currentIndex);
                pool.tempChilds.addAll(db.getIndexesFilteredRows(otherMeasure));
                int finalCurrentIndex = currentIndex;
                pool.tempChilds.removeIf(number -> number == finalCurrentIndex);
                queue.addAll(pool.tempChilds);
                System.out.println("дочерние строки");
                for (Integer e : pool.tempChilds) {
                    System.out.println(Arrays.toString(db.getDataRules().get(e.intValue())));
                }
                System.out.println("////////////////////////////");

                for (Integer nextIndex : pool.tempChilds) {
                    int[] el = {nextIndex.intValue(), currentIndex};
                    pool.linkedRows.add(el);
                    stack.put(nextIndex, otherMeasure);
                }
            }

        }
        for (int[] e : pool.linkedRows) {
            System.out.println("Итерация № " + pool.linkedRows.indexOf(e));
            System.out.println("строка " + e[0] + " "+ Arrays.toString(db.getDataRules().get(e[0])));
            System.out.println("строка " + e[0] + " "+Arrays.toString(db.getDataRules().get(e[1])));
        }
        return pool.linkedRows;


    }
    private class  Pool{
        List<int[]> linkedRows;
        List<Integer> tempChilds;
        public Pool(){
            linkedRows = new LinkedList<int[]>();
            tempChilds = new ArrayList<Integer>();
        }


    }
}
