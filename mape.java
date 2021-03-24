
public class mape<Key extends Comparable<?super Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    private Node nullNode;

    public class Node {
        private boolean color = RED;
        private Key key;
        private Value value;
        private Node leftChild, rightChild, parent;
        private boolean nullFlag;

        public Node (boolean nullFlag){
            this.nullFlag = nullFlag;
        }

        public Node(boolean nullFlag, boolean color, 
                    Node leftChild, Node rightChild, Node parent, 
                    Key key, Value value){
            this.nullFlag = nullFlag;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
            this.key = key;
            this.value = value;
            this.color = color;
        }

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Node getParent() {
			return parent;
		}

		public void setLeftChild(Node leftChild) {
			this.leftChild = leftChild;
		}

		public Node getLeftChild() {
			return leftChild;
		}

		public void setRightChild(Node rightChild) {
			this.rightChild = rightChild;
		}

        public Node getRightChild() {
		
			return rightChild;
		}

		public Key getKey() {
			return key;
		}

		public void setKey(Key key) {
			this.key = key;
		}

		public Value getValue() {
			return value;
		}

		public void setValue(Value value) {
			this.value = value;
		}

		public boolean getColor() {
			return color;
		}

		public void setColor(boolean color) {
			this.color = color;
		}

		public boolean isNull() {
			return nullFlag;
		}
    }

    // конструктор map
    public mape(){
        nullNode = new Node(true);
        nullNode.setColor(BLACK);
        root = nullNode;
    }
    public void copy(mape<Key, Value> newMape, mape<Key, Value> oldMape){
        newMape.root = copyNode(newMape.root, oldMape.root);
        System.out.println("Tree copied");
    }
    // метод копирования
    private Node copyNode(Node mapeNodeToCopy, Node oldMapeNode) {
        if(oldMapeNode != null){
            Node newMapeNode = new Node(oldMapeNode.nullFlag, oldMapeNode.color, oldMapeNode.leftChild, oldMapeNode.rightChild, oldMapeNode.parent, oldMapeNode.key, oldMapeNode.value);
            newMapeNode.leftChild = copyNode(newMapeNode.leftChild, oldMapeNode.leftChild);
            newMapeNode.rightChild = copyNode(newMapeNode.rightChild, oldMapeNode.rightChild);
            return newMapeNode;
        }
        return null;
    }

    // возвращает корень дерева
    public Node getRoot(){
        return this.root;
    }
    
    // проверка на пустоту
    public boolean isEmpty() {
        return root.isNull();
    }

    // удаление дерева
    public void deleteTree(){
        root = nullNode;
        System.out.println("Tree deleted: " + this.isEmpty());
    }

    // получение значения по ключу
    public Value getValue(Key key){
        if(key == null) throw new IllegalArgumentException("invlaid key.");
        Node elt = root;
        // пока временный корень не равен null и ключ не такой же, как и у временного корня
        while (!elt.isNull() && key.compareTo(elt.getKey()) != 0){
            // если ключ больше ключа сравнимаего узла, то переходим на правого ребенка этого узла
            if (key.compareTo(elt.getKey()) > 0){
                elt = elt.getRightChild();
            } 
            // иначе переходим на левого ребенка
            else {
                elt = elt.getLeftChild();
            }
        }
        // если значение временного узла (в котором хранится либо null, либо наше искомое значение)
        // не равно null, то возвращаем значение
        if (!elt.isNull()) return elt.getValue();
        // в ином случае возвращаем null
        return null;
    }

    // проверка на наличие значения в дереве
    public boolean isContains(Key key){
        if(key == null) throw new IllegalArgumentException("invalid key.");
        Node elt = root;
        // пока временный корень не равен null и ключ не такой же, как и у временного корня
        while (!elt.isNull() && key.compareTo(elt.getKey()) != 0){
            // если ключ больше ключа сравнимаего узла, то переходим на правого ребенка этого узла
            if (key.compareTo(elt.getKey()) > 0){
                elt = elt.getRightChild();
            } 
            // иначе переходим на левого ребенка
            else {
                elt = elt.getLeftChild();
            }
        }
        // если значение временного узла (в котором хранится либо null, либо наше искомое значение)
        // не равно null, то возвращаем true
        if (!elt.isNull()) return true;

        return false;
    }

    // добавление узла ключ-значение
    public void addPair(Key key, Value value){
        // если ключ или значение =null: выкидываем исключение
        if (key == null || value == null) throw new IllegalArgumentException("invalid parameters.");
        Node localRoot = root;
        Node nN = nullNode;
        // пока не дойдем до значения null в bst
        while(!localRoot.isNull()){
            nN = localRoot;
            // если ключ схож с ключем сравниваемого, то обновляем значение узла
            if (key.compareTo(localRoot.getKey()) == 0){
                localRoot.setValue(value);
                return;
            // если ключ меньше ключа узла, переходим к левому ребенку
            } else if (key.compareTo(localRoot.getKey()) < 0){
                localRoot = localRoot.getLeftChild();
            // иначе переходим на правого ребенка
            } else {
                localRoot = localRoot.getRightChild();
            }
        }
        Node newNode = new Node(false);
        newNode.setParent(nN);
        newNode.setKey(key);
        newNode.setValue(value);
        if (nN == nullNode)
            root = newNode;
        else if (key.compareTo(nN.getKey()) < 0)
            nN.setLeftChild(newNode);
        else
            nN.setRightChild(newNode);
        newNode.setRightChild(nullNode);
        newNode.setLeftChild(nullNode);
        newNode.setColor(RED);
        balance(newNode);
    }

    private void balance (Node node){
        while(node.getParent().getColor()){
            if (node.getParent() == node.getParent().getParent().getLeftChild()){
                node = leftBalance(node);
            } else if (node.getParent() == node.getParent().getParent().getRightChild()){
                node = rightBalance(node);
            }
        }
        root.setColor(BLACK);
    }

    private Node leftBalance(Node node){
        Node elt = node.getParent().getParent().getRightChild();
        if (elt.getColor()){
            elt.setColor(BLACK);
            node.getParent().setColor(BLACK);
            node.getParent().getParent().setColor(RED);
            node = node.getParent().getParent();
        } else {
            if (node == node.getParent().getRightChild()){
                node = node.getParent();
                rotateLeft(node);
            }
            node.getParent().setColor(BLACK);
            node.getParent().getParent().setColor(RED);
            rotateRight(node.getParent().getParent());
        }
        return node;
    }

    private Node rightBalance(Node node) {
        Node elt = node.getParent().getParent().getLeftChild();
        if (elt.getColor()){
            elt.setColor(BLACK);
            node.getParent().setColor(BLACK);
            node.getParent().getParent().setColor(RED);
            node = node.getParent().getParent();
        } else {
            if (node == node.getParent().getLeftChild()){
                node = node.getParent();
                rotateRight(node);
            }
            node.getParent().setColor(BLACK);
            node.getParent().getParent().setColor(RED);
            rotateLeft(node.getParent().getParent());
        }
        return node;
    }

    private void rotateLeft (Node node) {
        Node elt = node.getRightChild();
        node.setRightChild(elt.getLeftChild());
        if (!elt.getLeftChild().isNull()){
            elt.getLeftChild().setParent(node);
        }
        elt.setParent(node.getParent());
        if (node.getParent().isNull()){
            this.root = elt;
        } else if (node == node.getParent().getLeftChild()){
            node.getParent().setLeftChild(elt);
        } else {
            node.getParent().setRightChild(elt);
        }
        elt.setLeftChild(node);
        node.setParent(elt);
    }


    private void rotateRight (Node node) {
        Node elt = node.getLeftChild();
        node.setLeftChild(elt.getLeftChild());
        if (!elt.getRightChild().isNull()){
            elt.getRightChild().setParent(node);
        }
        elt.setParent(node.getParent());
        if (node.getParent().isNull()){
            this.root = elt;
        } else if (node == node.getParent().getRightChild()){
            node.getParent().setRightChild(elt);
        } else {
            node.getParent().setLeftChild(elt);
        }
        elt.setRightChild(node);
        node.setParent(elt);
    }

    public void printTree(Node root, int height){ 

        if(root==null){ 
        return; 
        } 
        printTree(root.rightChild, height+1); 
        if(height!=0){ 
            for(int i=0;i<height-1;i++) 
                System.out.print("|\t");
                if (root.getColor()) 
                    System.out.println( "├──────"+ "Red " + root.value + " " + root.key); 
                else
                System.out.println( "├──────"+ "Black " + root.value + " " + root.key);
        } 
        else 
            System.out.println("Black " + root.value + " " + root.key); 
        printTree(root.leftChild, height+1); 
    }
}