package client;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.executeTasksInFixedThreadPool(5, 1, 1);
    }


    private Runnable createTask(int requestsAmount) {
        return () -> {
            for (int i = 0; i < requestsAmount; i++) {
                new Thread(new ClientRunnable()).start();
            }
        };
    }

    public void executeTasksInFixedThreadPool(int poolSize, int requestsAmount,
                                                     int tasksAmount) {
        Executor executor = Executors.newFixedThreadPool(poolSize);
        this.execute(requestsAmount, tasksAmount, executor);
    }

    private void execute(int requestsAmount, int tasksAmount, Executor executor) {
        for (int i = 0; i < tasksAmount; i++) {
            executor.execute(createTask(requestsAmount));
        }
    }

    public void executeTasksInThreadPoolWithTimeBenchmark(int poolSize, int requestsAmount, int tasksAmount) {
        System.out.println("poolSize: " + poolSize + " requestAmount: " + requestsAmount + " taskAMount: " + tasksAmount);
        long startTime = System.currentTimeMillis();
        executeTasksInFixedThreadPool(poolSize, requestsAmount, tasksAmount);
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
    }
}
