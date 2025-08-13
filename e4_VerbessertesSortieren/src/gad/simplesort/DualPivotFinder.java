package gad.simplesort;

import java.util.Random;

public interface DualPivotFinder {

	int[] findPivot(int[] numbers, int from, int to);

	static DualPivotFinder getFirstLastPivot() {
		return new DualPivotFinder() {

			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				return null;
			}

			@Override
			public String toString() {
				return "The first and last element";
			}
		};
	}

	static DualPivotFinder getRandomPivot(int seed) {
		Random random = new Random(seed);

		return new DualPivotFinder() {
			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				return null;
			}

			@Override
			public String toString() {
				return "Two random elements";
			}
		};
	}

	static DualPivotFinder getMedianPivotFront(int numberOfConsideredElements) {
		return new DualPivotFinder() {
			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				return null;
			}

			@Override
			public String toString() {
				return "The thirds of the first " + numberOfConsideredElements + " elements";
			}
		};
	}

	static DualPivotFinder getMedianPivotDistributed(int numberOfConsideredElements) {
		return new DualPivotFinder() {
			@Override
			public int[] findPivot(int[] numbers, int from, int to) {
				// TODO
				return null;
			}

			@Override
			public String toString() {
				return "The thirds of " + numberOfConsideredElements + " elements distributed thoughout the array";
			}
		};
	}
}
