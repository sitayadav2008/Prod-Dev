import java.lang.ref.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class Main {

    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final Map<String, WeakReference<ExpensiveObject>> cache = new ConcurrentHashMap<>();
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static volatile boolean running = true;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gracefully...");
            running = false;
            executor.shutdown();
        }));

        System.out.println("System initializing...");
        preloadCache();
        startBackgroundGCWatcher();

        IntStream.range(0, 10).forEach(i ->
            executor.submit(() -> processRequest("Request-" + i))
        );

        waitForCompletion();
        System.out.println("All tasks completed successfully!");
    }

    private static void preloadCache() {
        for (int i = 0; i < 5; i++) {
            String key = "Obj-" + i;
            cache.put(key, new WeakReference<>(new ExpensiveObject(key)));
        }
    }

    private static void processRequest(String request) {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " handling " + request);
            try {
                Optional.ofNullable(cache.get("Obj-" + (counter.get() % 5)))
                        .map(WeakReference::get)
                        .ifPresentOrElse(
                            ExpensiveObject::compute,
                            () -> System.out.println("Cache miss for " + request)
                        );

                counter.incrementAndGet();
                if (counter.get() % 3 == 0) {
                    System.gc(); // Simulate GC pressure
                }

                Thread.sleep(new Random().nextInt(400) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void startBackgroundGCWatcher() {
        Thread watcher = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Active Threads: " + Thread.activeCount());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        watcher.setDaemon(true);
        watcher.start();
    }

    private static void waitForCompletion() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Nested static class simulating a heavy computation object
    private static class ExpensiveObject {
        private final String id;
        private final byte[] data = new byte[1024 * 256]; // 256 KB of data

        ExpensiveObject(String id) {
            this.id = id;
        }

        void compute() {
            double result = Math.sqrt(System.nanoTime() * Math.random());
            if (result < 0) throw new IllegalStateException("Negative computation!");
            System.out.println("[" + id + "] Computation result: " + result);
        }
    }
}
// random change 11417
// random change 15036
// random change 27162
// random change 17814
// random change 9148
// random change 1868
// random change 28741
// random change 32690
// random change 17728
// random change 8926
// random change 15147
// random change 8262
// random change 2910
// random change 25580
// random change 26790
// random change 19543
// random change 1495
// random change 20878
// random change 16726
// random change 22828
// random change 3914
// random change 31881
// random change 21755
// random change 13468
// random change 26692
// random change 8202
// random change 24951
// random change 24912
// random change 32583
// random change 3000
// random change 15604
// random change 28848
// random change 28928
// random change 6824
// random change 32710
// random change 30109
// random change 18225
// random change 7435
// random change 13984
// random change 5231
// random change 17366
// random change 5325
// random change 21695
// random change 5927
// random change 28338
// random change 24940
// random change 6648
// random change 4099
// random change 24485
// random change 2302
// random change 24209
// random change 18261
// random change 25937
// random change 3400
// random change 1450
// random change 30597
// random change 16972
// random change 31264
// random change 16956
// random change 16205
// random change 527
// random change 13269
// random change 31144
// random change 1808
// random change 11915
// random change 21929
// random change 32722
// random change 23444
// random change 19569
// random change 3044
// random change 9684
// random change 5574
// random change 5169
// random change 31774
// random change 11812
// random change 6786
// random change 21140
// random change 4495
// random change 31515
// random change 10782
// random change 228
// random change 5057
// random change 17941
// random change 17460
// random change 390
// random change 24145
// random change 24173
// random change 7553
// random change 28364
// random change 27836
// random change 16745
// random change 9596
// random change 6213
// random change 17589
// random change 6739
// random change 20515
// random change 17508
// random change 6035
// random change 31419
// random change 22689
// random change 22575
// random change 14336
// random change 367
// random change 11138
// random change 24798
// random change 14193
// random change 4853
// random change 11235
// random change 10187
// random change 13085
// random change 13537
// random change 22065
// random change 17562
