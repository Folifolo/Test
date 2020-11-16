public class List1<T> {

    Node<T> start;
    List1(T[] arr) {
        start = new Node<T>(arr[0]);
        Node<T> prev = start;
        for(int i = 1; i < arr.length; i++) {
            Node<T> node = new Node<T>(arr[i]);
            prev.addNode(node);
            prev = node;
        }
    }

    void add(int index, T value) {
        if (index == 0) {
            Node<T> prevStart = start;
            start = new Node<T>(value);
            start.addNode(prevStart);
        }
        else {
            Node<T> prev = getNode(index - 1);
            Node<T> next = prev.getNext();
            prev.addNode(new Node<T>(value));
            prev.getNext().addNode(next);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> curr = start;
        for(int i =0; i <index; i++) {
            if (curr == null)
                throw new ArrayIndexOutOfBoundsException();
            curr = curr.getNext();
        }
        return curr;
    }

    T get(int index) {
        return getNode(index).getValue();
    }

    void remove(int index) {
        if (index == 0) {
            start = getNode(index).getNext();
        }
        else {
            Node<T> prev = getNode(index-1);
            prev.addNode(prev.getNext().getNext());
        }
    }

    @Override
    public String toString() {
        Node<T> curr = start;
        String res =String.valueOf(curr.getValue());
        curr = curr.getNext();
        while(curr != null) {
            res += " " + curr.getValue();
            curr = curr.getNext();
        }
        return res;
    }

    class Node<T> {
        private T value;
        private Node<T> next;

        Node(T value) {
            this.value = value;
            next = null;
        }

        void addNode(Node<T> node) {
            next = node;
        }

        Node<T> getNext() {
            return next;
        }

        T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value) + ' ' + next;
        }
    }
}


