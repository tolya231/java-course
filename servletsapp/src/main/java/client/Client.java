package client;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.executeTasksInFixedThreadPool(5, 1, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private Runnable createTask(int requestsAmount) {
        return () -> {
            for (int i = 0; i < requestsAmount; i++) {
                new Thread(new ClientRunnable()).start();
            }
        };
    }

    public void executeTasksInFixedThreadPool(int poolSize, int requestsAmount, int tasksAmount) throws InterruptedException {
        System.out.println("poolSize: " + poolSize + " requestAmount: " + requestsAmount + " taskAMount: " + tasksAmount);
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < tasksAmount; i++) {
            executor.execute(createTask(requestsAmount));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Cancel currently executing tasks
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        //time measurement is not working :(
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
    }
}
