# Learn java8

笔记一些关于 java8 的特性，和 java 相关的相对复杂的知识点。

## Contents

### Basic

- Lambda 表达式
  - Offical Lession: [https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)

- 方法引用 
   `DefaultableImpl::new` new 指向构造函数。

- Stream 用法

- 接口提供 默认方法 & 静态方法
  - Offical Lession: [https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)

- Optional 类型

### Advance

- Thread 的 join()/wait()
  - Executor 创建执行线程池的几种方式
- 线程池
- 锁、原子类型
  - java.util.concurrent.locks.StampedLock / java.util.concurrent.locks.ReadWriteLock
  - java.util.concurrent.atomic
    - LongAdder
- 并发性
  - java.util.concurrent.ConcurrentHashMap / java.util.concurrentForkJoinPool
