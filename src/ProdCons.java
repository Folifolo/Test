import java.util.Stack;

import static java.lang.Thread.sleep;

class Producer extends Thread {
    final Buffer buff;
    Producer(Buffer buff) {
        this.buff = buff;
    }

    void produce() {

        int product = (int) (Math.random()*100.0);
        buff.push(product);
        System.out.println("product " + product +" is generated");
    }

    @Override
    public void run() {
        while(true){
            synchronized (buff) {
                if(buff.isFull())
                {
                    try {
                        buff.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                produce();
                buff.notifyAll();
            }
        }
    }
}

class Consumer extends Thread {
    final Buffer buff;
    Consumer(Buffer buff) {
        this.buff = buff;
    }

    void consume() {
        int cons = buff.pop();
        System.out.println("consumable " + cons + " is consumed");
    }

    @Override
    public void run() {
        while(true) {
            synchronized (buff) {

                if(buff.isEmpty()){
                    try {
                        buff.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                consume();
                buff.notifyAll();
            }
        }
    }
}

class Buffer{
    private Stack<Integer> stack;
    private int limit;

    Buffer(int limit) {
        stack = new Stack<>();
        this.limit = limit;
    }

    void push(int elem) {
        if(!isFull()){
            stack.push(elem);
        }
        else System.out.println("overload");
    }
    int pop() {
        if(!stack.empty()) {
            return stack.pop();
        }
        else System.out.println("empty");
        return 0;
    }

    boolean isFull() {
        return stack.size() >= limit;
    }
    boolean isEmpty() {
        return stack.empty();
    }
}


class Main1 {
    public static void main(String[] args) {
        Thread pr, cs;
        Buffer buff = new Buffer(3);
        pr = new Producer(buff);
        cs = new Consumer(buff);

        pr.start();

        cs.start();
    }
}
