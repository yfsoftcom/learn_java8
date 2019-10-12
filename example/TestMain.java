package example;

import java.util.Arrays;

public class TestMain {
  
    public static void main(String... args) {
    
        String[] arr = ",a,,b,".split(",");
        System.out.println(Arrays.toString(arr));

        //  '', 'a', '', 'b', ''
        
    }
}
