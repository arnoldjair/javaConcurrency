package threads.code;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    private final ReentrantLock lock = new ReentrantLock();

    public void performTask() {
        IO.println(Thread.currentThread().getName() + " is trying to acquire the lock...");
        lock.lock(); // Acquire the lock
        try {
            // Critical section
            System.out.println(Thread.currentThread().getName() + " is performing the task");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            IO.println(Thread.currentThread().getName() + " is releasing the lock...");
            lock.unlock(); // Always release the lock!
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample example = new ReentrantLockExample();
        Runnable task = example::performTask;
        new Thread(task).start();
        new Thread(task).start();
    }
}