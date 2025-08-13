package gad.abtree;

public abstract class ABTreeNode {

	protected final int a;
	protected final int b;

	ABTreeNode(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public abstract void insert(int keyToInsert);

	public abstract boolean find(int keyToFind);

	public abstract boolean remove(int keyToRemove);

	public abstract int height();

	public abstract boolean validAB();

	/**
	 * Diese Methode wandelt den Baum in das Graphviz-Format um.
	 *
	 * @return der nächste freie Index für das Graphviz-Format
	 */
	protected abstract int dot(StringBuilder sb, int from);
}
