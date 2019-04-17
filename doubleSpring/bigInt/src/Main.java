import java.math.MathContext;
import java.math.RoundingMode;


public class Main {
    public static void main(String[] args) {
        MathContext mc = new MathContext(50,RoundingMode.HALF_UP);
        long start= System.currentTimeMillis();



        SimpleSystem s = new SimpleSystem(mc);
        for(int i=0;i<30000;i++){
            s.run();
        }
        /**SimpleSystem s = new SimpleSystem(mc);
        s.repeatRun(10);
        s.close();**/
        System.out.println(System.currentTimeMillis()-start);
    }
}
