import java.math.MathContext;
import java.math.RoundingMode;


public class Main {
    public static void main(String[] args) {
        MathContext mc = new MathContext(10,RoundingMode.HALF_UP);

        long start= System.currentTimeMillis();
        Sys s = new Sys(mc);
        s.repeatRun(10);
        s.close();
        System.out.println(System.currentTimeMillis()-start);
    }
}
