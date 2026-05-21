package threads.code;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int value = 0;

    public void write(int newValue) {
        rwLock.writeLock().lock(); // Acquire write lock
        try {
            IO.println(Thread.currentThread().getName() + " writes " + newValue);
            value = newValue;
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            rwLock.writeLock().unlock(); // Always release write lock
        }
    }

    public void read() {
        rwLock.readLock().lock(); // Acquire read lock
        try {
            IO.println(Thread.currentThread().getName() + " reads " + value);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            rwLock.readLock().unlock(); // Always release read lock
        }
    }

    public static void main(String[] args) {
        ReadWriteLockExample example = new ReadWriteLockExample();

        Runnable writer = () -> example.write((int) (Math.random() * 100));
        Runnable reader = example::read;

        new Thread(writer).start();
        new Thread(writer).start();
        new Thread(writer).start();
        new Thread(reader).start();
        new Thread(reader).start();
    }
}