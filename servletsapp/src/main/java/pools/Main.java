package pools;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Executor executor1 = Executors.newSingleThreadExecutor();
        Executor executor3 = Executors.newSingleThreadScheduledExecutor();
        Executor executor4 = Executors.newCachedThreadPool();
        Executor executor5 = Executors.newFixedThreadPool(3);
        Executor executor6 = Executors.newScheduledThreadPool(3);
        Executor executor7 = Executors.newWorkStealingPool();
    }
}
