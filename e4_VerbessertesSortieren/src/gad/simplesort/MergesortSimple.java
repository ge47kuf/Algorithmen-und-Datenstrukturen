package gad.simplesort;

public class MergesortSimple extends SortAlgorithm {
	private int selectionSortSize;
	private Selectionsort selectionSort;

	public MergesortSimple(int selectionSortSize) {
		this.selectionSortSize = selectionSortSize;
		// TODO: Selectionsort Optimierung
		selectionSort = new Selectionsort();
	}

	@Override
	public void sort(int[] numbers, Result result, int from, int to) {
		if (from >= to) {
			return;
		}
		result.startMergesort(numbers, from, to);

		int mid = (from + to) / 2;
		sort(numbers, result, from, mid);
		sort(numbers, result, mid + 1, to);
		merge(numbers, from, mid, to);
		result.logPartialArray(numbers, from, to);
	}

	public void merge(int[] numbers, int left, int mid, int right) {
		int indexL = left;
		int indexR = mid + 1;
		int length = right - left + 1;
		int[] helper = new int[right - left + 1];

		for (int i = 0; i < length; i++) {
			if (indexL > mid) { // linker Teil leer
				helper[i] = numbers[indexR];
				indexR++;
			} else if (indexR > right) { // rechter Teil leer
				helper[i] = numbers[indexL];
				indexL++;
			} else if (numbers[indexL] <= numbers[indexR]) {
				helper[i] = numbers[indexL];
				indexL++;
			} else {
				helper[i] = numbers[indexR];
				indexR++;
			}
		}

		// Zurückkopieren
		for (int i = 0; i < length; i++) {
			numbers[left + i] = helper[i];
		}
	}

	@Override
	public String toString() {
		return "MergesortSimple (Selectionsort for: " + selectionSortSize + ")";
	}
}
