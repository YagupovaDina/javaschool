package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public static boolean find(List x, List y) {
        // TODO: Implement the logic here
        if (x == null || y == null || x.contains(null) || y.contains(null))
            throw new IllegalArgumentException();
        Object[] newX = x.toArray();
        Object[] newY = y.toArray();
        List<String> stringX = Stream.of(newX).map(Object::toString).collect(Collectors.toList());
        List<String> stringY = Stream.of(newY).map(Object::toString).collect(Collectors.toList());
        List<Integer> indexes = new ArrayList<>();
        for (String elemFromX : stringX) {
            if (stringY.contains(elemFromX)) {
                indexes.add(stringY.indexOf(elemFromX));
            }
        }
        boolean res;
        if (indexes.size() == x.size())
            res = indexes.stream().sorted().collect(Collectors.toList()).equals(indexes);
        else {
            res = false;
        }
        return res;
    }

    public static void main(String[] args) {
        find(Stream.of(3, 9, 1, 5, 7).collect(toList()), Stream.of(1, 2, 3, 4, 5, 7, 9, 20).collect(toList()));
    }
}
