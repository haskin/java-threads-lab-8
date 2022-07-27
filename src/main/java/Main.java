import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final int FUTURE_AMOUNT = 10;
    private static final int THREAD_AMOUNT = 3;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_AMOUNT);

        List<Future> futures = IntStream.range(0, FUTURE_AMOUNT).mapToObj(i -> executorService.submit(() -> {
            Random random = new Random();
            try {
                Thread.sleep(random.nextInt(5));
            } catch (Exception e) {
                return;
            }
        })).collect(Collectors.toList());
        System.out.println(
                String.format("%s out of %s threads are finished processing", countFinishedFutures(futures),
                        futures.size()));

        executorService.shutdown();
        System.exit(0);
    }

    public static int countFinishedFutures(List<Future> futures) {
        return (int) futures.stream().filter(Future::isDone).count();
    }
}