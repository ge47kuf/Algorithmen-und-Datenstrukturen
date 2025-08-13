package gad.treetraverse;

public class Node {

    private Node leftChild;
    private Node rightChild;
    private Node parent;

    private int value;

    public Node(int value) {
        this.value = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setChildren(Node leftChild, Node rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;

        this.leftChild.parent = this;
        this.rightChild.parent = this;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getValue() {
        return value;
    }

    /**
     * Baut den Baum aus dem Beispiel auf.
     * @return Die Wurzel des neu erzeugten Baumes
     */
    public Node buildExampleTree() {
        Node[] nodes = new Node[15];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i + 1);
        }
        nodes[0].setChildren(nodes[1], nodes[8]);
        nodes[1].setChildren(nodes[2], nodes[5]);
        nodes[2].setChildren(nodes[3], nodes[4]);
        nodes[5].setChildren(nodes[6], nodes[7]);
        nodes[8].setChildren(nodes[9], nodes[12]);
        nodes[9].setChildren(nodes[10], nodes[11]);
        nodes[12].setChildren(nodes[13], nodes[14]);

        return nodes[0];
    }

    /**
     * Baut einen Baum mit den angegebenen Leveln. Dabei ist ein Blatt auf Level 0.
     * Die Werte des Baumes sind die erwartete Traversierungsreihenfolge von Preorder, wenn man bei der Wurzel beginnt
     * @param level Das Level des zu erzeugenden Baumes
     * @param startValue Der Wert des Knotens, der zurückgegeben wird. Hier soll 1 übergeben werden
     * @return Die Wurzel des neu erzeugten Baumes
     */
    public Node buildBigPreTree(int level, int startValue) {
        Node root = new Node(startValue);
        if (level != 0) {
            Node leftChild = buildBigPreTree(level - 1, startValue + 1);
            int rightValue = (int) (Math.pow(2, level));
            Node rightChild = buildBigPreTree(level - 1, startValue + rightValue);
            root.setChildren(leftChild, rightChild);
        }
        return root;
    }

    /**
     * Baut einen Baum mit den angegebenen Leveln. Dabei ist ein Blatt auf Level 0.
     * Die Werte des Baumes sind die erwartete Traversierungsreihenfolge von Postorder, wenn man bei dem untersten linken Knoten beginnt
     * @param level Das Level des zu erzeugenden Baumes
     * @param offset Der Offset, der auf die Knotenwerte aufaddiert werden soll. Hier soll 0 übergeben werden
     * @return Die Wurzel des neu erzeugten Baumes
     */
    public Node buildBigPostTree(int level, int offset) {
        int rootValue = (int) (Math.pow(2, level + 1)) - 1 + offset;
        Node root = new Node(rootValue);
        if (level != 0) {
            Node leftChild = buildBigPostTree(level - 1, offset);
            int rightOffset = (int) (Math.pow(2, level)) - 1;
            Node rightChild = buildBigPostTree(level - 1, offset + rightOffset);
            root.setChildren(leftChild, rightChild);
        }
        return root;
    }

    /**
     * Baut einen Baum mit den angegebenen Leveln. Dabei ist ein Blatt auf Level 0.
     * Die Werte des Baumes sind die erwartete Traversierungsreihenfolge von Inorder, wenn man bei dem untersten linken Knoten beginnt
     * @param level Das Level des zu erzeugenden Baumes
     * @param offset Der Offset, der auf die Knotenwerte aufaddiert werden soll. Hier soll 0 übergeben werden
     * @return Die Wurzel des neu erzeugten Baumes
     */
    public Node buildBigInTree(int level, int offset) {
        int rootValue = (int) (Math.pow(2, level)) + offset;
        Node root = new Node(rootValue);
        if (level != 0) {
            Node leftChild = buildBigInTree(level - 1, offset);
            int rightOffset = (int) (Math.pow(2, level));
            Node rightChild = buildBigInTree(level - 1, offset + rightOffset);
            root.setChildren(leftChild, rightChild);
        }
        return root;
    }
}