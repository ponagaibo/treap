public class Main {
    public static void main(String[] args) {
        Vector myTree = new Treap();
/*        myTree.add(5);
        myTree.add(0, 9);
        myTree.add(2);
        myTree.add(1);
        myTree.add(0);
        myTree.add(12);
        myTree.add(8);
        myTree.add(16);
        myTree.add(6);

        for (int i = 0; i < myTree.size(); i++) {
            Object k = myTree.get(i);
            System.out.println("Value: " + k);
        }

        myTree.set(1, 7);
        myTree.remove(0);
        System.out.println();
        for (int i = 0; i < myTree.size(); i++) {
            Object k = myTree.get(i);
            System.out.println("Value: " + k);
        }*/

        for (int i = 0; i < 10000; i++) {
            myTree.add(i);
        }


        for (int j = myTree.size() - 1; j >= 0; j -= 2) {
            myTree.remove(j);
        }

        for (int i = 0; i < myTree.size(); i++) {
            int tmp = (int) myTree.get(i);
            if (tmp != 2 * i) {
                System.out.println("Wrong: " + i + ", tmp: " + tmp);
            }
        }

        int size = myTree.size();

        for (int i = 0; i < size; i++) {
            myTree.add(2 * i + 1, 2 * i + 1);
        }

        for (int i = 0; i < 10000; i++) {
            if ((int) myTree.get(i) != i) {
                System.out.println("Wrong: " + i);
            }
        }
    }
}
