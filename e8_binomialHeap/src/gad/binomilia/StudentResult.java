package gad.binomilia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StudentResult implements Result {
	private List<BinomialTreeNode> currentHeap = new ArrayList<>();

	@Override
	public void startInsert(int value, Collection<BinomialTreeNode> heap) {
		System.out.println("Starte Einfügen mit Heap mit " + heap.size() + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(heap);
	}

	@Override
	public void startInsert(int value, BinomialTreeNode[] heap) {
		System.out.println("Starte Einfügen mit Heap mit " + heap.length + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(Arrays.stream(heap).toList());
	}

	@Override
	public void startDeleteMin(Collection<BinomialTreeNode> heap) {
		System.out.println("Starte Löschen mit Heap mit " + heap.size() + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(heap);
	}

	@Override
	public void startDeleteMin(BinomialTreeNode[] heap) {
		System.out.println("Starte Löschen mit Heap mit " + heap.length + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(Arrays.stream(heap).toList());
	}

	@Override
	public void logMerge(BinomialTreeNode treeOne, BinomialTreeNode treeTwo) {
		System.out.println("Merge zwei Bäume mit Rängen: " + treeOne.rank() + ", " + treeTwo.rank());
		currentHeap.remove(treeOne);
		currentHeap.remove(treeTwo);
	}

	@Override
	public void logMergedTree(BinomialTreeNode mergedTree) {
		currentHeap.add(mergedTree);
	}

	@Override
	public void printCurrentIntermediateStep() {
		System.out.println(BinomialHeap.dot(currentHeap));
	}
}
