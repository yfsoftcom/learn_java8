package example.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class LambdaDemo{


    public String toStr( Object src) {
        return "_" + String.valueOf(src) + " |";
    }

    public static void main(String[] args){

        System.out.println("test start >>>");
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp", "Golang", "NodeJS", "Python", "C#", "Lua", "Ruby", "Shell", "awk", "Sed");

        // map  filter java/golang

        languages.stream().map(x -> String.format("I like %s", x)).forEach(System.out::println);

        // Predicate<String>  接受 1个 String 参数，返回1个 Boolean 类型的结果

        Predicate<String> notStartC = x -> !x.startsWith("C");

        Predicate<String> notLua = x -> !"Lua".equals(x);

        // Supplier<String> 不接受输入参数，返回一个String类型的结果

        Supplier<String> sayHi = () -> "Hi There：";

        // Consumer<String> 接受1个String 参数, 不返回任何值

        Consumer<String> output = x -> System.out.println(sayHi.get() + x);

        languages.stream().filter(notStartC.and(notLua)).forEach(output);

        System.out.println("test end >>>");
    }
}