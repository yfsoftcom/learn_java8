package example.stream;

import java.util.stream.*;
import java.util.function.*;

public class StreamDemo {

    public static void main(String[] args){
        System.out.println("test start >>>");

        // Stream 的创建方法
        // of()
        Stream<String> strArr = Stream.of("Java", "Scala", "C++", "Haskell", "Lisp", "Golang", "NodeJS", "Python", "C#", "Lua", "Ruby", "Shell", "awk", "Sed");

        strArr.forEach(System.out::println);

        // generator
        // 该函数接受一个 Supplier<?> 接口
        // 使用 limit 限制其创建无限的元素
        Stream<Double> generateA = Stream.generate(new Supplier<Double>() {
            @Override
            public Double get() {
                return java.lang.Math.random();
            }
        }).limit(10);

        // random() 函数恰好是该接口的实现
        
        Stream<Double> generateB = Stream.generate(()-> java.lang.Math.random()).limit(10);
        Stream<Double> generateC = Stream.generate(java.lang.Math::random).limit(10);

        generateA.forEach(System.out::println);
        generateB.forEach(System.out::println);
        generateC.forEach(System.out::println);

        //  itrator
        // 该方法接受一个起始值，一个叠加函数 Function<T, T>，使用上一次/起始值进行运算,返回一个值做为元素，并作为下一次执行的参数。
        Stream.iterate(1, x -> x + 1)
            .limit(10)
            .forEach(System.out::println);

        
        
        System.out.println("test end >>>");
    }
}