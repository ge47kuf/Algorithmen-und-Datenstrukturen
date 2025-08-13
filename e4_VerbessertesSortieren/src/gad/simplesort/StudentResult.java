package gad.simplesort;

import java.util.Arrays;

public class StudentResult implements Result {

    @Override
    public void startSelectionsort(int[] array, int from, int to) {
        System.out.println(
                "Starting Selectionsort with the array " + Arrays.toString(array) + " from " + from + " to " + to);
    }

    @Override
    public void startMergesort(int[] array, int from, int to) {
        System.out
                .println("Starting Mergesort with the array " + Arrays.toString(array) + " from " + from + " to " + to);
    }

    @Override
    public void startQuicksort(int[] array, int from, int to) {
        System.out
                .println("Starting Quicksort with the array " + Arrays.toString(array) + " from " + from + " to " + to);
    }

    @Override
    public void startDualPivotQuicksort(int[] array, int from, int to) {
        System.out.println("Starting Dualpivot Quicksort with the array " + Arrays.toString(array) + " from " + from
                + " to " + to);
    }

    @Override
    public void startJavaSort(int[] array, int from, int to) {
        System.out.println("Starting the java sort algorithm with the array " + Arrays.toString(array) + " from " + from
                + " to " + to);
    }

    @Override
    public void logPartialArray(int[] array, int from, int to) {
        System.out.println(
                "Making next sorting step with the array " + Arrays.toString(array) + " from " + from + " to " + to);
    }
}
