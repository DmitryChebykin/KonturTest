package service;

import java.util.*;

public class ListSorter implements Comparator <LinkedList>{
    @Override
    public int compare(LinkedList o1, LinkedList o2) {
        String str1 = (String) o1.get(0);
        String str2= (String) o2.get(0);
        return str1.compareTo(str2);

    }

    @Override
    public Comparator reversed() {
        return null;
    }
}
