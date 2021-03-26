package service;

import repository.DB;

import java.util.*;

public class HandlerDB {

    public List<int[]> getConversionRows(String measureFrom, String measureTo, DB db) {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int ToRow = -1;
        int FromRow = -1;
        String startMsr = measureFrom;
        String nextMsr = measureTo;
        queue.addAll(db.getIndexesFilteredRows(measureFrom));
        Pool pool = new Pool();
        while (queue.size() != 0) {
            addNextRowsToQueue(queue, db, startMsr, measureTo, pool);
        }
        return pool.linkedRows;
    }

    private void addNextRowsToQueue(LinkedList<Integer> queue, DB db, String msr, String measureTo, Pool pool) {
        String currentMsr = msr;
        while(!currentMsr.equals(measureTo)){
            int currentIndex = queue.getFirst();
            if(Arrays.stream((db.getDataRules().get(currentIndex))).anyMatch(measureTo::equals)){
                currentMsr = measureTo;

            }
            else{

                queue.removeFirst();
                currentIndex = queue.getFirst();
                if(!Arrays.stream((db.getDataRules().get(currentIndex))).anyMatch(currentMsr::equals)){currentMsr = otherMsr;}
                pool.childs = db.getIndexesFilteredRows(currentMsr);
                pool.childs.removeIf(number -> number == currentIndex);
                for (Integer nextIndex : pool.childs) {
                    int[] el = {nextIndex.intValue(), currentIndex};
                    pool.linkedRows.add(el);
                }
                queue.addAll(pool.childs);


            }
        }
    }

    private ArrayList<Integer> getChildRowsIndexesByParentMeasure(int index, String parentMeasure, DB db){

        return  db.getIndexesFilteredRows(parentMeasure).removeIf((number -> number == index);

    }


    private class  Pool{
        List<int[]> linkedRows;
        List<Integer> childs;
        public Pool(){
            linkedRows = new LinkedList<int[]>();
        }


    }
}
