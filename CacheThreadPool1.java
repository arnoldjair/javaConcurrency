package threads.code;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CacheThreadPool1 {

  public static void main(String[] args) {
    int countCPU = Runtime.getRuntime().availableProcessors();
    IO.println("CPU Cores: " + countCPU);

    // 1. Use try-with-resources with your CachedThreadPool.
    // The close() method automatically shuts down the executor and blocks
    // until all submitted tasks are finished.
    try (ExecutorService es = Executors.newCachedThreadPool()) {

      Runnable runnableTask = () -> {
        IO.println("Hello from runnable thread: " + Thread.currentThread().getName());
        try {
          TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(500, 2000));
          IO.println("Runnable task completed: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      };

      Callable<String> callableTask = () -> {
        IO.println("Hello from callable thread: " + Thread.currentThread().getName());
        try {
          TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(500, 2000));
          IO.println("Callable task completed: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        return "Task completed";
      };

      List<Runnable> runnableTasks = List.of(runnableTask, runnableTask, runnableTask);
      List<Callable<String>> callableTasks = List.of(callableTask, callableTask, callableTask);

      runnableTasks.forEach(es::execute);

      // 2. Use timed invokeAll to establish a single time budget for the whole batch.
      // Tasks not completing within 1000ms will be automatically cancelled.
      List<Future<String>> futures = es.invokeAll(callableTasks, 1000, TimeUnit.MILLISECONDS);

      for (Future<String> future : futures) {
        // 3. Review modern Future methods. Check the state directly rather than
        // blocking with get() and catching TimeoutException/ExecutionException.
        switch (future.state()) {
          case SUCCESS -> IO.println("Result: " + future.resultNow());
          case CANCELLED -> IO.println("Task was cancelled due to timeout.");
          case FAILED -> IO.println("Task failed: " + future.exceptionNow());
          case RUNNING -> IO.println("Task is still running.");
        }
      }

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    // No manual es.shutdown() is needed because of the try-with-resources block.

    IO.println("END");
  }
}