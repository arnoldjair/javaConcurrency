package threads.code;
public class MyThread1 extends Thread {
    public MyThread1(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("Thread " + getName() + " is running.");
    }

    public static void main(String[] args) {
        MyThread1 thread1 = new MyThread1("Thread-1");
        MyThread1 thread2 = new MyThread1("Thread-2");
        MyThread1 thread3 = new MyThread1("Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
