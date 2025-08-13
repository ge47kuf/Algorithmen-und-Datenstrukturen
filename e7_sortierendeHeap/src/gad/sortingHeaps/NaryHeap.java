package gad.sortingHeaps;

public class NaryHeap {

	private int dotSize;

	public NaryHeap(int[] values, int dimension, Result result) {

	}

	public NaryHeap(int[] values, int dimension, int maxSize, Result result) {

	}

	public int[] getHeap() {
		return null;
	}

	public int getParentIndex(int index) {
		return 0;
	}

	public int getChildIndex(int childIndex, int nodeIndex) {
		return 0;
	}

	public void insert(int value) {

	}

	public int extractMax() {
		return 0;
	}

	public void merge(NaryHeap other) {

	}

	public static void heapSort(int[] array, int dimension, Result result) {

	}

	public String dot() {
		StringBuilder sb = new StringBuilder();
		sb.append("graph {").append(System.lineSeparator());
		if (dotSize > 0) {
			sb.append(String.format("\t%d [label=\"%d\"];%n", 0, getHeap()[0]));
		}
		for (int i = 1; i < dotSize; i++) {
			sb.append(String.format("\t%d -- %d;%n", getParentIndex(i), i));
			sb.append(String.format("\t%d [label=\"%d\"];%n", i, getHeap()[i]));
		}
		sb.append(System.lineSeparator()).append("}");
		return sb.toString();
	}
}
