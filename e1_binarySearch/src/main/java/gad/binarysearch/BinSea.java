package java.gad.binarysearch;

import java.gad.binarysearch.Interval.NonEmptyInterval;


public final class BinSea {

    private BinSea() {
    }

    /**
     * Searches for the first occurrence of the value in the sorted array.
     * If search area <= 1 and the value is not found, breaks the search and return index of next bigger or
     * smaller value.
     *
     * @param sortedData the sorted array to search in
     * @param value      the value to search for
     * @param result     the result object to log the search steps
     * @return the index of the first occurrence of the value or the index of the next bigger or smaller value
     * if not found
     */
    public static int search(int[] sortedData, int value, Result result) {
        int low = 0;
        int high = sortedData.length - 1;
        int mid = (low + high) / 2;
        while (low <= high) {
            result.addStep(mid);
            if (sortedData[mid] < value) {
                low = mid + 1;
            } else if (sortedData[mid] > value) {
                high = mid - 1;
            } else return mid; // found the value
            mid = (low + high) / 2;
        }
        return mid;
    }

    /**
     * Searches for the lower or upper bound of the value in the sorted array. If the value is not found, return -1.
     *
     * @param sortedData   the sorted array to search in
     * @param value        the value to search for
     * @param lowerBound   if true, returns the index of the array >= value (lowerBound), otherwise returns the index of the
     *                     array <= value (upperBound)
     * @param result       the result object to log the search steps
     * @return             the index of the lower or upper bound of the value in the sorted array, or -1 if not found
     */
    public static int search(int[] sortedData, int value, boolean lowerBound, Result result) {

        return 0;
    }

    public static Interval search(int[] sortedData, NonEmptyInterval valueRange, Result resultLower, Result resultHigher) {
        return null;
    }

    public static void main(String[] args) {
        int[] array = new int[] { 2, 7, 7, 42, 69, 1337, 2000, 9001 };

        // Test cases for the binary search
        System.out.println(search(array, 7, new StudentResult()));
        System.out.println(search(array, 100, new StudentResult()));

        // Test cases for the binary search with lower bound
        System.out.println(search(array, 7, false, new StudentResult()));
        System.out.println(search(array, 100, true, new StudentResult()));

        // Test cases for the interval search
        System.out.println(search(array, new NonEmptyInterval(7, 1500), new StudentResult(), new StudentResult()));
        System.out.println(search(array, new NonEmptyInterval(9002, 10000), new StudentResult(), new StudentResult()));
    }
}


