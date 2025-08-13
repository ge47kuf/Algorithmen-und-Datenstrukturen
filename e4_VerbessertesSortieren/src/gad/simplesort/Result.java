package gad.simplesort;

public interface Result {

    void startSelectionsort(int[] array, int from, int to);

    void startMergesort(int[] array, int from, int to);

    void startQuicksort(int[] array, int from, int to);

    void startDualPivotQuicksort(int[] array, int from, int to);

    void startJavaSort(int[] array, int from, int to);

    void logPartialArray(int[] array, int from, int to);
}
