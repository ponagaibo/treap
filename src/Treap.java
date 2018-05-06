import java.util.Random;

class Treap implements Vector {
    static final Random rand = new Random();
    private int treapSize = 0;

    private Node root = null;

    Treap() {
        root = null;
    }

    @Override
    public int size() {
        return treapSize;
    }

    @Override
    public void add(int index, Object obj) {
        if (index > treapSize) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + treapSize);
        }
        if (root == null) {
            root = new Node(obj, null, null);
        } else {
            Node.SplitResult tmp = root.split(index);
            Node m = new Node(obj, null, null);
            root = Node.merge(Node.merge(tmp.left, m), tmp.right);
        }
        treapSize++;
    }

    @Override
    public void add(Object obj) {
        add(treapSize, obj);
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        Node.SplitResult tmp = root.split(index);
        Node.SplitResult tmp2 = tmp.right.split(1);
        treapSize--;
        root = Node.merge(tmp.left, tmp2.right);
    }

    @Override
    public Object get(int index) {
        checkIndex(index);
        Node cur = root;
        while (cur != null) {
            int sizeLeft = Node.sizeOf(cur.left);
            if (sizeLeft == index) {
                return cur.value;
            }
            if (sizeLeft > index) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
            if (sizeLeft < index) {
                index -= sizeLeft + 1;
            }
        }
        throw new RuntimeException("Unexpected index error");
    }

    @Override
    public void set(int index, Object obj) {
        checkIndex(index);
        Node cur = root;
        while (cur != null) {
            int sizeLeft = Node.sizeOf(cur.left);
            if (sizeLeft == index) {
                cur.setValue(obj);
                return;
            }
            if (sizeLeft > index) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
            if (sizeLeft < index) {
                index -= sizeLeft + 1;
            }
        }
    }

    private static void printTree(Treap tree) {
        printTree(tree.root, 0);
        System.out.println();
    }

    private static void printTree(Node node, int tabIndex) {
        if (node == null) {
            return;
        }
        printTree(node.left, tabIndex + 1);
        for (int i = 0; i < 2 * tabIndex; i++) {
            System.out.print(" ");
        }
        System.out.println("Value: " + node.value + ", priority: " + node.priority + ", size: " + node.size);
        printTree(node.right, tabIndex + 1);
    }

    private void checkIndex(int index) {
        if (index >= treapSize || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + treapSize);
        }
    }

    private static class Node {
        Object value;
        int priority;
        int size = 1;
        Node right;
        Node left;

        private void recalculateSize() {
            this.size = sizeOf(left) + sizeOf(right) + 1;
        }

        private Node(int priority, Object value, Node left, Node right) {
            this.value = value;
            this.priority = priority;
            this.left = left;
            this.right = right;
            size = sizeOf(this.left) + sizeOf(this.right) + 1;
        }

        private Node(Object value, Node left, Node right) {
            this(rand.nextInt(), value, left, right);
        }

        private void setValue(Object obj) {
            value = obj;
        }

        private static int sizeOf(Node node) {
            if (node == null) {
                return 0;
            } else {
                return node.size;
            }
        }

        private SplitResult split(int x) {
            SplitResult res = new SplitResult();
            Node newTree = null;
            SplitResult tmp;
            int curIndex = sizeOf(left) + 1;
            if (curIndex <= x) {
                if (right == null) {
                    res.right = null;
                } else {
                    tmp = right.split(x - curIndex);
                    newTree = tmp.left;
                    res.right = tmp.right;
                }
                res.left = new Node(priority, value, left, newTree);
                res.left.recalculateSize();
            } else {
                if (left == null) {
                    res.left = null;
                } else {
                    tmp = left.split(x);
                    res.left = tmp.left;
                    newTree = tmp.right;
                }
                res.right = new Node(priority, value, newTree, right);
                res.right.recalculateSize();
            }
            return res;
        }

        private static Node merge(Node left, Node right) {
            if (left == null) return right;
            if (right == null) return left;
            if (left.priority > right.priority) {
                Node newR = merge(left.right, right);
                return new Node(left.priority, left.value, left.left, newR);
            } else {
                Node newL = merge(left, right.left);
                return new Node(right.priority, right.value, newL, right.right);
            }
        }

        private class SplitResult {
            Node left;
            Node right;
        }
    }
}
