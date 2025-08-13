package gad.binomilia;

import java.util.Collection;

public interface Result {
	void startInsert(int value, Collection<BinomialTreeNode> heap);

	void startInsert(int value, BinomialTreeNode[] heap);

	void startDeleteMin(Collection<BinomialTreeNode> heap);

	void startDeleteMin(BinomialTreeNode[] heap);

	void logMerge(BinomialTreeNode treeOne, BinomialTreeNode treeTwo);

	void logMergedTree(BinomialTreeNode mergedTree);

	void printCurrentIntermediateStep();
}
