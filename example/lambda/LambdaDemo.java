package example.lambda;

import java.util.Arrays;

public class LambdaDemo{


    public String toStr( Object src) {
        return "_" + String.valueOf(src) + " |";
    }

    public static void main(String[] args){
        LambdaDemo demo = new LambdaDemo();
        System.out.println("test start >>>");
        String finalStr = Arrays.asList("a,b,c,d".split(",")).stream()
            .map(demo::toStr).reduce("", (x, y ) -> (x + "," + y));
        System.out.println(String.format("finalString %s", finalStr));
        System.out.println("test end >>>");
    }
}