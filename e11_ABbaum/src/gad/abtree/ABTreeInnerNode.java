package gad.abtree;

import java.util.ArrayList;
import java.util.List;

public class ABTreeInnerNode extends ABTreeNode {
	private List<Integer> keys;
	private List<ABTreeNode> children;

	public ABTreeInnerNode(List<Integer> keys, List<ABTreeNode> children, int a, int b) {
		super(a, b);
		this.keys = keys;
		this.children = children;
	}

	public ABTreeInnerNode(int key, ABTreeNode left, ABTreeNode right, int a, int b) {
		super(a, b);
		keys = new ArrayList<>();
		children = new ArrayList<>();
		keys.add(key);
		children.add(left);
		children.add(right);
	}

	public ABTreeInnerNode(int key, int a, int b) {
		this(key, new ABTreeLeaf(a, b), new ABTreeLeaf(a, b), a, b);
	}

	public List<ABTreeNode> getChildren() {
		return children;
	}

	public List<Integer> getKeys() {
		return keys;
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
		return false;
	}

	@Override
	public boolean remove(int keyToRemove) {
		// TODO ?
		return false;
	}

	@Override
	protected int dot(StringBuilder sb, int from) {
		int mine = from++;
		sb.append(String.format("\tstruct%s [label=\"", mine));
		for (int i = 0; i < 2 * keys.size() + 1; i++) {
			if (i > 0) {
				sb.append("|");
			}
			sb.append(String.format("<f%d> ", i));
			if ((i & 1) == 1) {
				sb.append(keys.get(i / 2));
			}
		}
		sb.append("\"];").append(System.lineSeparator());
		for (int i = 0; i < children.size(); i++) {
			int field = 2 * i;
			sb.append(String.format("\tstruct%d:<f%d> -> struct%d;%n", mine, field, from));
			from = children.get(i).dot(sb, from);
		}
		return from;
	}
}
