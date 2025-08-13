package gad.simplesort;

public class Selectionsort extends SortAlgorithm {

    @Override
    public void sort(int[] numbers, Result result, int from, int to) {
        result.startSelectionsort(numbers, from, to);
        for (int i = from; i <= to; i++) {
            int minValue = numbers[i];
            int minIndex = i;
            for (int j = i + 1; j <= to; j++) {
                if (numbers[j] < minValue) {
                    minValue = numbers[j];
                    minIndex = j;
                }
            }
            swap(numbers, i, minIndex);
        }
    }
}
