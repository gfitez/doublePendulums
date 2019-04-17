import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;

public class SimpleSystem {
    Mass m1;
    Spring s1;
    MathContext mc;
    BigDecimal g;
    int iterations=0;
    BigDecimal elapsedTime, timeInterval;
    PrintWriter logger;
    public SimpleSystem(MathContext mc){
        this.mc=mc;
        m1=new Mass(new Vector(new BigDecimal(0,mc),new BigDecimal("-2",mc),mc),new BigDecimal(1 ,mc),mc);
        s1=new Spring(new Vector(new BigDecimal(0,mc),new BigDecimal(0,mc),mc),m1.pos,new BigDecimal(1,mc),new BigDecimal(1,mc),mc);



        g=new BigDecimal(0,mc);



        elapsedTime=new BigDecimal(0,mc);
        timeInterval=new BigDecimal("0.0001",mc);

        try{
            logger=new PrintWriter("log.txt","utf-8");
        }catch(Exception e){

        }

    }
    public void run(){
        this.iterations++;
        s1.end=m1.pos;
        s1.end.y=m1.pos.y;
        System.out.println("Spring:"+s1.E().toPlainString()+", Mass:"+m1.E(g).toPlainString()+", total:"+E().toPlainString()+" pos:"+m1.pos.y.toPlainString());




        Vector f1=s1.force();
        f1.add(new Vector(new BigDecimal(0,mc),m1.mass.multiply(g,mc),mc));



        m1.update(f1,timeInterval);
        s1.end=m1.pos;

        elapsedTime=elapsedTime.add(timeInterval);

        logData();

    }
    public void repeatRun(long time){
        for(long i=0;i<time/timeInterval.doubleValue();i++){
            run();
        }
    }
    public void logData(){
        logger.println(elapsedTime+", "+m1.pos.y.toPlainString()+", "+s1.force().y+ ", "+E());

    }
    public void close(){
        logger.close();
    }
    public BigDecimal E(){
        return m1.E(g).add(s1.E());
    }
}
