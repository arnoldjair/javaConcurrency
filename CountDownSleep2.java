package threads.code;

import java.util.Arrays;

public class CountDownSleep2 implements Runnable {

    @Override
    public void run() {

        String[] timeStr = {
                "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"
        };

        Arrays.asList(timeStr).reversed().stream().forEach(current -> {
            IO.println(current);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

    };

    public static void main(String[] args) {
        Thread thread1 = new Thread(new CountDownSleep2());
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        IO.println("Boom");
    }
}
