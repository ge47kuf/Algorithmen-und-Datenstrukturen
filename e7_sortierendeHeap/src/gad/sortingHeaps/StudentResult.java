package gad.sortingHeaps;

public class StudentResult implements Result {

    @Override
    public void logSwap(int i, int j) {
        System.out.println("Logged swap: " + i + ", " + j);
    }
}
