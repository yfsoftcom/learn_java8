### Java8 多线程

传统方式是用 Thread 或者 Runnable 接口来实现多任务并行，代码冗长且容易出错。因此在2004年java5中正式引入并行编程API，位于java.util.concurrent包中，其中包括许多有用的操作并行编程的类。从此，每个java新版都增强并行API，java8也提供了新类和方法。


[https://docs.oracle.com/javase/8/docs/api/index.html](https://docs.oracle.com/javase/8/docs/api/index.html)
#### Executor

- 几个创建线程池的函数

  - newSingleThreadExecutor()
  - newFixedThreadPool(int)
  - newWorkStealingPool()

    newWorkSteaingPool()方法创建另一种类型的executor，其为java8提供的工厂方法，返回ForkJoinPool类型的执行器，与正常的executor稍微不同，代替给定一个固定大小的线程池，ForkJoinPool创建线程池大小默认为主机CPU的核数。java7中ForkJoinPool已经存在。


FixedThreadPool适用于为了满足资源管理的需求，而需要限制当前线程数量的应用场景，它适用于负载比较重的服务器。
SingleThreadExecutor适用于需要保证顺序地执行各个任务；并且在任意时间点，不会有多个线程是活动的应用场景。
CachedThreadPool是大小无界的线程池，适用于执行很多的短期异步任务的小程序，或者是负载较轻的服务器


- 创建一个线程池来执行任务

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
        String threadName = Thread.currentThread().getName();
        System.out.println("Hello " + threadName);
});

// => Hello pool-1-thread-1
```

- executor延时关闭，用于结束当前正在运行的任务。等待最大时间5秒之后，中断未完成的任务并关闭executor。

```java
try {
    System.out.println("attempt to shutdown executor");
    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);
}
catch (InterruptedException e) {
    System.err.println("tasks interrupted");
}
finally {
    if (!executor.isTerminated()) {
        System.err.println("cancel non-finished tasks");
    }
    executor.shutdownNow();
    System.out.println("shutdown finished");
}
```

- Callable<T> 、 Future 接口


Future的接口定义
```java
public interface Future<V> {

    boolean cancel(boolean mayInterruptIfRunning);

    boolean isCancelled();

    boolean isDone();

    V get() throws InterruptedException, ExecutionException;

    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

```java
// 定义一个 1s 之后返回 123 的 Callable 接口
Callable<Integer> task = () -> {
    try {
        TimeUnit.SECONDS.sleep(1);
        return 123;
    }
    catch (InterruptedException e) {
        throw new IllegalStateException("task interrupted", e);
    }
};

// 提交给Executor 并获取一个 Future

ExecutorService executor = Executors.newFixedThreadPool(1);
Future<Integer> future = executor.submit(task);

System.out.println("future done? " + future.isDone());

Integer result = future.get();

// 传入参数可以设定任务执行的超时时间，超过这个时间，会接受到超时的异常错误

// future.get(1, TimeUnit.SECONDS);

/*
Exception in thread "main" java.util.concurrent.TimeoutException
at java.util.concurrent.FutureTask.get(FutureTask.java:205)
*/

System.out.println("future done? " + future.isDone());
System.out.print("result: " + result);

```

输出结果
```
future done? false
future done? true
result: 123
```

Question: `CompletableFuture` 具体的用法和使用场景


- InvokeAll/InvokeAny

它们都接受一组任务集合，不同的是返回结果；

`InvokeAll` 返回所有的任务的 Future；
`InvokeAny` 返回第一个执行的 Future;

Question: InvokeAny 中没有来得及执行的其他任务结果会如何？是否会继续执行？


- ScheduledExecutorService

通过 `ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);` 来创建


为了调度任务周期性执行，executor提供两个方法，scheduleAtFixedRate() 和 scheduleWithFixedDelay()，第一个方法能够使用固定的时间频率执行任务

```java
ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

int initialDelay = 0;
int period = 1;
executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
```

每秒钟输出一次

另外，该方法接受一个初始延迟参数，即第一次执行之前需等待的时间。需要注意的是scheduleAtFixedRate()方法没有考虑实际执行任务的时间，所以你指定1秒周期，但是任务需要2秒，那么线程执行则更快。这种情况下，应该考虑使用scheduleWithFixedDelay()方法代替。这个方法就如前面描述的方式吻合，不同之处是等待周期是任务结束和下一个任务开始的间隔

```java
ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

Runnable task = () -> {
    try {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Scheduling: " + System.nanoTime());
    }
    catch (InterruptedException e) {
        System.err.println("task interrupted");
    }
};

executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
```

该示例调度任务使用固定时间间隔，即任务结束和下一个任务开始间的时间间隔。初始延时时间为0，执行任务所需时间为2秒。所以我们最终执行间隔时间为0s，3s，6s，9s…… 
所以如果你不能预测调度任务执行时间，使用scheduleWithFixedDelay()是非常方便的。