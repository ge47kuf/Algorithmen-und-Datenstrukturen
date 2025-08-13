package gad.simplesort;

import java.util.Arrays;

public class JavaSort extends SortAlgorithm {

    @Override
    public void sort(int[] numbers, Result result, int from, int to) {
        result.startJavaSort(numbers, from, to);
        Arrays.sort(numbers, from, to + 1);
    }

    @Override
    public String toString() {
        return "The sorting algorithm from Java";
    }
}
