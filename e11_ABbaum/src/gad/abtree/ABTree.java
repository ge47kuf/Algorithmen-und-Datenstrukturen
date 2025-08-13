package gad.abtree;

public class ABTree {

	private final int a;
	private final int b;

	private ABTreeInnerNode root;

	public ABTree(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public ABTreeInnerNode getRoot() {
		return root;
	}

	public void setRoot(ABTreeInnerNode root) {
		this.root = root;
	}
	
	public void insert(int keyToInsert) {
        // TODO
	}

	public int height() {
	    // TODO
		return 0;
	}

	public boolean find(int keyToFind) {
	    // TODO
		return false;
	}

	public boolean validAB() {
	    // TODO
		return false;
	}

	public boolean remove(int keyToRemove) {
	    // TODO
		return false;
	}

	/**
	 * Diese Methode wandelt den Baum in das Graphviz-Format um.
	 *
	 * @return der Baum im Graphiz-Format
	 */
	private String dot() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph {").append(System.lineSeparator());
		sb.append("\tnode [shape=record];").append(System.lineSeparator());
		if (root != null) {
			root.dot(sb, 0);
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String toString() {
		return dot();
	}
}
