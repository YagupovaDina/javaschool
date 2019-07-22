package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    private int[][] sortPyramid(List<Integer> inputNumbers) {
        inputNumbers.sort(Comparator.naturalOrder());
        int sizePyramid = 0;
        for (int sum = 0; sum < inputNumbers.size(); ) {
            sum += ++sizePyramid;
        }
        int[][] pyramid = new int[sizePyramid][sizePyramid];
        int i = 0;
        int j = 0;
        for (Integer number : inputNumbers) {
            if (j >= i + 1) {
                i++;
                j = 0;
            }
            pyramid[i][j++] = number;
        }
        if (i != j - 1)
            throw new CannotBuildPyramidException();
        return pyramid;
    }

    private int[][] addZeroToPyramid(int[][] pyramid) {
        int[][] res = new int[pyramid.length][pyramid.length * 2 - 1];
        for (int i = pyramid.length - 1; i >= 0; i--) {
            int tmp = -1;
            int count = pyramid.length - 1 - i;
            for (int j = 0; j < pyramid.length - (pyramid.length - 1 - i); j++) {
                if (tmp == -1)
                    tmp = count % 2;
                if (!((j + count) % 2 == tmp) && j + count < res[0].length)
                    res[i][j + count++] = 0;
                if (j + count < res[0].length)
                    res[i][j + count] = pyramid[i][j];
            }
        }
        return res;
    }

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        long tmp = Integer.MAX_VALUE - 1;
        if (inputNumbers.contains(null) || inputNumbers.size() >= tmp)
            throw new CannotBuildPyramidException();
        int[][] pyramid = sortPyramid(inputNumbers);
        int[][] res = addZeroToPyramid(pyramid);
        return res;
    }

}
