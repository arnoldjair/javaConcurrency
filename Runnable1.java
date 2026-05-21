package threads.code;

public class Runnable1 implements Runnable {
    private String name;

    public Runnable1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Thread " + name + " is running.");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable1("Thread-1"));
        Thread thread2 = new Thread(new Runnable1("Thread-2"));
        Thread thread3 = new Thread(new Runnable1("Thread-3"));

        Thread thread4 = new Thread(
                () -> System.out.println("Thread Lambda " + Thread.currentThread().getName() + " is running."));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

}
