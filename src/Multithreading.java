public class Multithreading {



    public static void main(String[] args) {
        Object key = new Object();

        Thread th2 = new Thread() {
            @Override
            public void run() {
                System.out.println(getState());
                try {
                    sleep(1000);
                } catch (InterruptedException ignored) {}

                synchronized (key) {
                    System.out.println("Enter sync block");
                    key.notifyAll();
                    try {
                        key.wait();
                    } catch (InterruptedException ignored) {}
                    System.out.println("Exit sync block");
                    return;
                }
            }
        };
        System.out.println("First thread");
        System.out.println(th2.getState());
        th2.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {}
        System.out.println(th2.getState());

        synchronized (key){
            System.out.println("First enter sync");

            try {
            key.wait();
            System.out.println(th2.getState());
            key.notifyAll();
            } catch (InterruptedException ignored) {}

            System.out.println(th2.getState());
            System.out.println("First exit sync");
        }
        try {
            th2.join();
        } catch (InterruptedException ignored) {}
        System.out.println(th2.getState());
    }
}
