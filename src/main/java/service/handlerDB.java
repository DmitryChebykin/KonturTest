package service;

import repository.DB;

import java.util.*;

public class handlerDB {

    public LinkedList<Integer> getConversionRows(String measureFrom, String measureTo, DB db) {
        LinkedList<Integer> queue = new LinkedList<Integer>();

        int ToRow = -1;
        int FromRow = -1;
        String startMsr = measureFrom;
        String nextMsr = measureTo;
        queue.addAll(db.getIndexesFilteredRows(measureFrom));
        Pool pool = new Pool();
        while (queue.size() != 0) {
            addNextRowsToQueue(queue, db, nextMsr, measureTo, pool);
        }
        return queue;
    }

    private void addNextRowsToQueue(LinkedList<Integer> queue, DB db, String nextMsr, String measureTo, Pool pool) {
        String currentMsr = nextMsr;
        while(!currentMsr.equals(measureTo)){
            int currentIndex = queue.getFirst();
            if(Arrays.stream((db.getDataRules().get(currentIndex))).anyMatch(measureTo::equals)){
                currentMsr = measureTo;

            }
            else{
                currentMsr = db.getPair(nextMsr, currentIndex);
                queue.removeFirst();
                pool.childs = db.getIndexesFilteredRows(currentMsr);
                pool.childs.removeIf(number -> number == currentIndex);
                for (Integer nextIndex : pool.childs) {
                    pool.linkedRows.add(new int[]{nextIndex, currentIndex});
                }
            }
        }
        int currentIndex = queue.getFirst();
    }

    private class  Pool{
        List<int[]> linkedRows;
        List<Integer> childs;

    }
}
