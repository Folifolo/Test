public class BinarySearchTree {

    private Node root;
    BinarySearchTree(int key) {
        root = new Node(key);
    }

    void add(int key) {
        root.add(key);
    }

    Node find(int key) {
        return root.find(key);
    }

    void remove(int key) {
        if (root.key != key)
            root.remove(key, root);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    static class Node {
        private int key;
        private Node left;
        private Node right;

        Node(int key) {
            this.key = key;
            left = null;
            right = null;
        }

        void add(int key) {
            Node child = new Node(key);
            if(this.key > key)
                if(left != null)
                    left.add(key);
                else left = child;
            else if(this.key < key)
                if(right != null)
                    right.add(key);
                else right = child;
            else this.key = key;
        }

        Node find(int key) {
            if (this != null) {
                if(this.key > key)
                    left.find(key);
                else if(this.key < key)
                    right.find(key);
                else return this;
            }
            return this;
        }

        public int getKey() {
            return key;
        }

        void remove(int key, Node parent) {
            if(this.key > key)
                if(left != null)
                    left.remove(key, this);
                else System.out.println("Element not found");

            else if(this.key < key)
                if(right != null)
                    right.remove(key, this);
                else System.out.println("Element not found");

            else {
                if (right == null && left == null) {
                    if(parent.key > key)
                        parent.left = null;
                    else
                        parent.right = null;
                }
                else if (right != null) {
                    Node tmp = right;
                    int tmpKey;
                    while (tmp.left != null)
                        tmp = tmp.left;
                    tmpKey = tmp.key;
                    this.remove(tmp.key, parent);
                    this.key = tmpKey;
                }
                else {
                    if(parent.key > key)
                        parent.left = left;
                    else
                        parent.right = left;
                }
            }
        }

            String  traverse() {
            String res = "";
                if (left != null)
                    res += left.traverse();
                res += key + " ";
                if (right !=null)
                    res += right.traverse();
                return res;
            }
            @Override
            public String toString() {
                return traverse();
            }
    }
    }
