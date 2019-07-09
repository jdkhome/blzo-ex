import java.util.concurrent.*;

public class TimeoutCallable<V> implements Callable<V> {

    private final Callable<V> callable;
    private final V timeoutV;
    private final long timeout;

    /**
     * 构造一个 TimeoutCallable
     *
     * @param callable 要运行的 Callable
     * @param timeout Callable 的最大运行时间
     * @param timeoutV Callable 超时的返回结果
     */
    public TimeoutCallable(Callable<V> callable, long timeout, V timeoutV) {
        this.timeout = timeout;
        this.callable = callable;
        this.timeoutV = timeoutV;
    }

    @Override
    public V call() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<V> future = executor.submit(callable);

        V v = null;
        try {
            v = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException ex) {
            System.out.println("Callble 超时");
        }

        executor.shutdownNow(); // 给线程池中所有正在运行的线程发送 中断 信号

        return v != null ? v : timeoutV;
    }

}
