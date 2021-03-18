package RBTreeMap;

public class test_map {
    public static void main(String[] args) {
        mape<Integer, String> mp = new mape<Integer, String>();
        //mp.addPair(10, "Steve");
       // mp.addPair(15, "Angela");
        //mp.addPair(3, "Bob");
       // System.out.println(mp.isContains(15));
       for (int i = 0; i < 100; i++) {
           mp.addPair(i, "" + i);
       }
        mp.printTree(mp.getRoot(), 0);
        System.out.println();
       System.out.println(mp.isContains(110));
       System.out.println();
       System.out.println(mp.getValue(35));
       mp.deleteTree();

    }
}
