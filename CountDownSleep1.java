package threads.code;

import java.util.Arrays;

public class CountDownSleep1 implements Runnable {

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
        Thread thread1 = new Thread(new CountDownSleep1());
        thread1.start();
        IO.println("Boom");
    }
}
