package gad.simplesort;

public abstract class SortAlgorithm {
    public void sort(int[] numbers, Result result) {
        sort(numbers, result, 0, numbers.length - 1);
    }

    public abstract void sort(int[] numbers, Result result, int from, int to);

    protected void swap(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    // Not actually faster or better, but cooler (⌐■_■)
    protected void coolSwap(int[] numbers, int i, int j) {
        if (i == j) {
            return;
        }

        numbers[i] = numbers[i] ^ numbers[j];
        numbers[j] = numbers[i] ^ numbers[j];
        numbers[i] = numbers[i] ^ numbers[j];
    }
}
