# 异常相关

## 1. 丢失异常的行为

```java
class VeryImportantException extends Exception {
    @Override
    public String toString() {
        return "A very important exception!";
    }
}
class HoHumException extends Exception {
    @Override
    public String toString() {
        return "A trivial exception";
    }
}
public class LostMessage {
    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }
    void dispose() throws HoHumException {
        throw new HoHumException();
    }
    public static void main(String[] args) {
        try {
            LostMessage lm = new LostMessage();
            try {
                lm.f();
            } finally {
                lm.dispose();
            }
        } catch(VeryImportantException | HoHumException e) {
            System.out.println(e);
        }
    }
}
```

```
A trivial exception
```

## 2. 资源规范头

> try 总是后面跟着一个 {，但是现在可以跟一个带括号的定义 - 这里是我们创建的 FileInputStream 对象。括号内的部分称为资源规范头（resource specification header）。现在可用于整个 try 块的其余部分。更重要的是，无论你如何退出 try 块（通常或通过异常），都会执行前一个 finally 子句的等价物，但不会编写那些杂乱而棘手的代码。这是一项重要的改进。

资源头部分的对象都需要实现 Closeable 接口，因为会在 try 语句结束之后执行它。

```java
// exceptions/TryWithResources.java
import java.io.*;
public class TryWithResources {
    public static void main(String[] args) {
        try(
                InputStream in = new FileInputStream(
                        new File("TryWithResources.java"))
        ) {
            int contents = in.read();
            // Process contents
        } catch(IOException e) {
            // Handle the error
        }
    }
}
```

对于在try中定义多个资源，需要注意其定义的顺序，因为 资源销毁的顺序和 定义的顺序是 **相反** 的。

```java
// exceptions/AutoCloseableDetails.java
class Reporter implements AutoCloseable {
    String name = getClass().getSimpleName();
    Reporter() {
        System.out.println("Creating " + name);
    }
    public void close() {
        System.out.println("Closing " + name);
    }
}
class First extends Reporter {}
class Second extends Reporter {}
public class AutoCloseableDetails {
    public static void main(String[] args) {
        try(
                First f = new First();
                Second s = new Second()
        ) {
        }
    }
}
```

output:
```
Creating First
Creating Second
Closing Second
Closing First
```


```java
// exceptions/CloseExceptions.java
class CloseException extends Exception {}
class Reporter2 implements AutoCloseable {
    String name = getClass().getSimpleName();
    Reporter2() {
        System.out.println("Creating " + name);
    }
    public void close() throws CloseException {
        System.out.println("Closing " + name);
    }
}
class Closer extends Reporter2 {
    @Override
    public void close() throws CloseException {
        super.close();
        throw new CloseException();
    }
}
public class CloseExceptions {
    public static void main(String[] args) {
        try(
                First f = new First();
                Closer c = new Closer();
                Second s = new Second()
        ) {
            System.out.println("In body");
        } catch(CloseException e) {
            System.out.println("Caught: " + e);
        }
    }
}
```