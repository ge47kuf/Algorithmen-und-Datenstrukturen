package gad.abtree;

public class ABTreeLeaf extends ABTreeNode {

	public ABTreeLeaf(int a, int b) {
		super(a, b);
	}

	@Override
	public void insert(int keyToInsert) {
        // TODO ?
	}
	
	
	@Override
	public int height() {
	    // TODO ?
		return 0;
	}

	@Override
	public boolean find(int keyToFind) {
	    // TODO ?
		return false;
	}
	
	@Override
	public boolean validAB() {
	    // TODO ?
		return true;
	}

	@Override
	public boolean remove(int keyToRemove) {
	    // TODO ?
		return false;
	}

	@Override
	protected int dot(StringBuilder sb, int from) {
		sb.append(String.format("\tstruct%d [label=leaf, shape=ellipse];%n", from));
		return from + 1;
	}
}
