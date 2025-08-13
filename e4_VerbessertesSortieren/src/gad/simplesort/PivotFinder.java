package gad.simplesort;

import java.util.Random;

public interface PivotFinder {

	int findPivot(int[] numbers, int from, int to);

	static PivotFinder getLastPivot() {
		return new PivotFinder() {

			@Override
			public int findPivot(int[] numbers, int from, int to) {
				return to;
			}

			@Override
			public String toString() {
				return "The last element";
			}
		};
	}

	static PivotFinder getMidPivot() {
		return new PivotFinder() {

			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				return 0;
			}

			@Override
			public String toString() {
				return "The middle element";
			}
		};
	}

	static PivotFinder getRandomPivot(int seed) {
		Random random = new Random(seed);

		return new PivotFinder() {
			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				return 0;
			}

			@Override
			public String toString() {
				return "A random element";
			}
		};
	}

	static PivotFinder getMedianPivotFront(int numberOfConsideredElements) {
		return new PivotFinder() {
			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				return 0;
			}

			@Override
			public String toString() {
				return "The median of the first " + numberOfConsideredElements + " elements";
			}
		};
	}

	static PivotFinder getMedianPivotDistributed(int numberOfConsideredElements) {
		return new PivotFinder() {
			@Override
			public int findPivot(int[] numbers, int from, int to) {
				// TODO
				return 0;
			}

			@Override
			public String toString() {
				return "The median of " + numberOfConsideredElements + " elements distributed throughout the array";
			}
		};
	}
}
