## Java 中的 Lock

`java.util.concurrent.Lock` 包中的常用类型

Question: 

- 锁和同步块的区别

  - 1.同步块只能包含在一个方法内----而lock()和unlock()操作却可以在跨越多个不同的方法使用。 
  - 2.同步块不支持公平性，任一个线程都能获取已经被释放的锁，不能指定优先权。但我们却可以使用Lock API指定公平属性从而实现公平性。它能确保等待时间最长的线程优先获取锁。  
  - 3.当一个线程不能访问同步块时，它会被阻塞住。而 Lock API提供的有 tryLock()方法，使用该方法，只有在锁不被其他线程持有且可用时，才会真正获取锁。这将极大地降低阻塞时间。  
  - 4.那些获取访问同步块的等待线程不能被中断，Lock API提供了一个 lockInterruptbly()方法，当线程正在等待锁时，该方法可以用于中断该线程。


- 悲观锁和乐观锁的区别

- CAP计算

- Conditions

