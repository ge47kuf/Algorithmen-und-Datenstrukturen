package gad.simplesort;

public class DualPivotQuicksort extends SortAlgorithm {
	private DualPivotFinder pivotFinder;
	private int selectionSortSize;
	private Selectionsort selectionSort;

	public DualPivotQuicksort(DualPivotFinder pivotFinder, int selectionSortSize) {
		this.pivotFinder = pivotFinder;
		this.selectionSortSize = selectionSortSize;
		// TODO: Selectionsort Optimierung
		selectionSort = new Selectionsort();
	}

	@Override
	public void sort(int[] numbers, Result result, int from, int to) {
		// TODO
	}

	@Override
	public String toString() {
		return "DualPivotQuicksort (Pivot: " + pivotFinder + ", Selectionsort for: " + selectionSortSize + ")";
	}
}
