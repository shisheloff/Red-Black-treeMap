
public class test_map {
    public static void main(String[] args) {
        mape<Integer, String> mp = new mape<Integer, String>();
        mape<Integer, String> nmp = new mape<Integer, String>();
        // добавление элементов в дерево
        System.out.println("-----------------------------------");
        mp.addPair(10, "Steve");
        mp.addPair(15, "Angela");
        mp.addPair(3, "Bob");
        mp.addPair(21, "Egor");
        mp.addPair(2, "Jojo");
        // вывод дерева
        mp.printTree(mp.getRoot(), 0);
        System.out.println("-----------------------------------");
        System.out.println("Проверка на наличие узла со значением 110: " + mp.isContains(110));
        System.out.println("Проверка на наличие узла со значением 3: " + mp.isContains(3));
        System.out.println("-----------------------------------");
        System.out.println("Получение значения по ключу 35: " + mp.getValue(35));
        System.out.println("Получение значения по ключу 10: " + mp.getValue(10));

        System.out.println("-----------------------------------");
        nmp.copy(nmp, mp);
        nmp.printTree(nmp.getRoot(), 0);

        System.out.println("-----------------------------------");
        mp.deleteTree();
        System.out.println("Проверка на пустоту: " + mp.isEmpty());

    }
}
