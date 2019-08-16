package example.lambda;

import java.util.function.Consumer;

/**
 * 必包，作用域的语法。
 */
public class LambdaScopeTest {

    public int x = 0;

    class FirstLevel {

        int x = 1;

        void methodInFirstLevel(int x) {
            
            // The following statement causes the compiler to generate
            // the error "local variables referenced from a lambda expression
            // must be final or effectively final" in statement A:
            //
            // x = 99;

            Consumer<Integer> testa = new Consumer<Integer>() {
                @Override
                public void accept(Integer i) {
                    System.out.println("Out print from testa:" + i);
                }
            };
            
            Consumer<Integer> myConsumer = (y) -> 
            {
                
                System.out.println("x = " + x); // Statement A  // 23
                System.out.println("y = " + y);
                System.out.println("this.x = " + this.x);   // 23
                System.out.println("LambdaScopeTest.this.x = " +
                    LambdaScopeTest.this.x);    //0
            };

            myConsumer.accept(x);
            testa.andThen(myConsumer).accept(x);;
        }
    }
    

    public static void main(String... args) {
        LambdaScopeTest st = new LambdaScopeTest();
        LambdaScopeTest.FirstLevel fl = st.new FirstLevel();
        fl.methodInFirstLevel(23);
    }
}