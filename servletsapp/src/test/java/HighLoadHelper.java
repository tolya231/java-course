import static io.restassured.RestAssured.get;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HighLoadHelper {

  private static Runnable createTask(int requestsAmount) {
    return () -> {
      for (int i = 0; i < requestsAmount; i++) {
        new Thread(() -> get("/hi").then().statusCode(200)).start();
      }
    };
  }

  public static void executeTasksInCachedThreadPool(int requestsAmount, int tasksAmount) {
    Executor executor = Executors.newCachedThreadPool();
    execute(requestsAmount, tasksAmount, executor);
  }

  public static void executeTasksInFixedThreadPool(int poolSize, int requestsAmount,
      int tasksAmount) {
    Executor executor = Executors.newFixedThreadPool(poolSize);
    execute(requestsAmount, tasksAmount, executor);
  }

  public static void executeTasksInWorkStealingThreadPool(int requestsAmount, int tasksAmount) {
    Executor executor = Executors.newWorkStealingPool();
    execute(requestsAmount, tasksAmount, executor);
  }

  private static void execute(int requestsAmount, int tasksAmount, Executor executor) {
    for (int i = 0; i < tasksAmount; i++) {
      executor.execute(createTask(requestsAmount));
    }
  }

  public static void executeTasksInThreadPoolWithTimeBenchmark(int poolSize, int requestsAmount,
      int tasksAmount, String tpType) {
    long startTime = System.currentTimeMillis();
    switch (tpType) {
      case "fixed":
        executeTasksInFixedThreadPool(poolSize, requestsAmount, tasksAmount);
        break;
      case "cached":
        executeTasksInCachedThreadPool(requestsAmount, tasksAmount);
        break;
      case "workstealing":
        executeTasksInWorkStealingThreadPool(requestsAmount, tasksAmount);
        break;
      default:
        break;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Total execution time: " + (endTime - startTime) + "ms");
  }
}
