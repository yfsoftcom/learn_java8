# 关于 Class 的相关知识点

## 重点概念

为了使用类而做的准备工作实际包含三个步骤：

1) 加载，这是由类加载器执行的。该步骤将查找字节码（通常在 classpath 所指定的路径中查找，但这并非是必须的），并从这些字节码中创建一个 Class 对象。

2) 链接。在链接阶段将验证类中的字节码，为 static 域分配存储空间，并且如果需要的话，将解析这个类创建的对其他类的所有引用。

3) 初始化。如果该类具有超类，则对其进行初始化，执行 static 初始化器和 static 初始化块。

初始化被延迟到了对 static 方法（构造器隐式地是 static 的）或者非常数 static 域进行首次引用时才执行.





```java
// 检查类加载器工作方式
class Cookie {
    static { System.out.println("Loading Cookie"); }
}

class Gum {
    static { System.out.println("Loading Gum"); }
}

class Candy {
    static { System.out.println("Loading Candy"); }
}

public class SweetShop {
    public static void main(String[] args) {
        System.out.println("inside main");
        new Candy();
        System.out.println("After creating Candy");
        try {
            Class.forName("Gum");
        } catch(ClassNotFoundException e) {
            System.out.println("Couldn't find Gum");
        }
        System.out.println("After Class.forName(\"Gum\")");
        new Cookie();
        System.out.println("After creating Cookie");
    }
}
```

output：
```
inside main
Loading Candy
After creating Candy
Loading Gum
After Class.forName("Gum")
Loading Cookie
After creating Cookie
```

```java
import java.util.*;

class Initable {
    static final int STATIC_FINAL = 47;
    static final int STATIC_FINAL2 =
        ClassInitialization.rand.nextInt(1000);
    static {
        System.out.println("Initializing Initable");
    }
}

class Initable2 {
    static int staticNonFinal = 147;
    static {
        System.out.println("Initializing Initable2");
    }
}

class Initable3 {
    static int staticNonFinal = 74;
    static {
        System.out.println("Initializing Initable3");
    }
}

public class ClassInitialization {
    public static Random rand = new Random(47);
    public static void
    main(String[] args) throws Exception {
        Class initable = Initable.class;
        System.out.println("After creating Initable ref");
        // Does not trigger initialization:
        System.out.println(Initable.STATIC_FINAL);
        // Does trigger initialization:
        System.out.println(Initable.STATIC_FINAL2);
        // Does trigger initialization:
        System.out.println(Initable2.staticNonFinal);
        Class initable3 = Class.forName("Initable3");
        System.out.println("After creating Initable3 ref");
        System.out.println(Initable3.staticNonFinal);
    }
}
```

output
```
After creating Initable ref
47
Initializing Initable
258
Initializing Initable2
147
Initializing Initable3
After creating Initable3 ref
74
```

初始化有效地实现了尽可能的“惰性”，从对 initable 引用的创建中可以看到，仅使用 .class 语法来获得对类对象的引用不会引发初始化。但与此相反，使用 Class.forName() 来产生 Class 引用会立即就进行初始化，如 initable3。

如果一个 static final 值是“编译期常量”（如 Initable.staticFinal），那么这个值不需要对 Initable 类进行初始化就可以被读取。但是，如果只是将一个域设置成为 static 和 final，还不足以确保这种行为。例如，对 Initable.staticFinal2 的访问将强制进行类的初始化，因为它不是一个编译期常量。

如果一个 static 域不是 final 的，那么在对它访问时，总是要求在它被读取之前，要先进行链接（为这个域分配存储空间）和初始化（初始化该存储空间），就像在对 Initable2.staticNonFinal 的访问中所看到的那样。