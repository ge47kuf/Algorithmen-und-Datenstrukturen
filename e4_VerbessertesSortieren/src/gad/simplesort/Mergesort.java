package gad.simplesort;

public class Mergesort extends SortAlgorithm {
	private int selectionSortSize;
	private Selectionsort selectionSort;

	public Mergesort(int selectionSortSize) {
		this.selectionSortSize = selectionSortSize;
		// TODO: Selectionsort Optimierung
		selectionSort = new Selectionsort();
	}

	@Override
	public void sort(int[] numbers, Result result, int from, int to) {
		// TODO
	}

	public void sort(int[] numbers, Result result, int from, int to, int[] helper) {
		// TODO
	}

	@Override
	public String toString() {
		return "Mergesort with helper array (Selectionsort for: " + selectionSortSize + ")";
	}
}
