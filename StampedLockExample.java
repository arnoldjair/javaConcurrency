package threads.code;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    private final StampedLock lock = new StampedLock();
    private int value = 0;

    public void optimisticRead() {
        long stamp = lock.tryOptimisticRead();
        int current = value;
        // Check if a write lock was acquired during the optimistic read
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                current = value;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + " read (optimistically) " + current);
    }

    public void write(int newValue) {
        long stamp = lock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + " writes " + newValue);
            value = newValue;
        } finally {
            lock.unlockWrite(stamp); // Always release the write lock
        }
    }

    public static void main(String[] args) {
        StampedLockExample example = new StampedLockExample();

        Runnable writer = () -> example.write((int) (Math.random() * 100));
        Runnable reader = example::optimisticRead;

        new Thread(reader).start();
        new Thread(writer).start();
        new Thread(reader).start();
        new Thread(reader).start();
        new Thread(writer).start();
        new Thread(reader).start();
        new Thread(reader).start();
        new Thread(writer).start();
        new Thread(reader).start();
    }
}