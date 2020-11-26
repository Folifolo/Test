import javax.naming.Name;

import static java.lang.Thread.sleep;

public class SyncTest {

    static class NameThread extends Thread {
        Object lock;
        NameThread(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            while(true){
                synchronized (lock) {
                    try {
                    System.out.println(this.getName());
                    lock.notifyAll();
                    lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Object lock = new Object();
        Thread th2 = new NameThread(lock);
        Thread th1 = new NameThread(lock);

        th1.start();

        th2.start();
    }
}
