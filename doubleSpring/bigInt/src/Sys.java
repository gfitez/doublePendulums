import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;

public class Sys {
    Mass m1,m2;
    Spring s1,s2;
    MathContext mc;
    BigDecimal g;
    int iterations=0;
    BigDecimal elapsedTime, timeInterval;
    BigDecimal freq,amplitude;
    PrintWriter logger;
    public Sys(MathContext mc){
        this.mc=mc;
        m1=new Mass(new Vector(new BigDecimal(0,mc),new BigDecimal(-1,mc),mc),new BigDecimal(1 ),mc);
        m2=new Mass(new Vector(new BigDecimal(0,mc),new BigDecimal(-3,mc),mc),new BigDecimal(1),mc);
        s1=new Spring(new Vector(new BigDecimal(0,mc),new BigDecimal(0,mc),mc),m2.pos,new BigDecimal(1),new BigDecimal(1),mc);
        s2=new Spring(m1.pos,m2.pos,new BigDecimal(1),new BigDecimal(1),mc);



        g=new BigDecimal(0,mc);

        freq=new BigDecimal(0,mc);
        amplitude=new BigDecimal(1,mc);

        elapsedTime=new BigDecimal(0,mc);

        timeInterval=new BigDecimal(0.00001,mc);

        try{
            logger=new PrintWriter("log.txt","utf-8");
        }catch(Exception e){

        }

    }
    public void run(){
        this.iterations++;

        s1.start.y=amplitude.multiply(new BigDecimal(Math.sin(elapsedTime.doubleValue()*2*Math.PI*freq.doubleValue()),mc),mc);
        s1.end=m1.pos;
        s2.start=m1.pos;
        s2.end=m2.pos;

        Vector f1=s1.force();
        f1.add(new Vector(new BigDecimal(0,mc),m1.mass.multiply(g,mc),mc));
        f1.add(Vector.mult(s2.force(),(new BigDecimal(-1,mc)),mc));

        Vector f2=s2.force();
        f1.add(new Vector(new BigDecimal(0,mc),m2.mass.multiply(g,mc),mc));

        m1.update(f1,timeInterval);
        m2.update(f2,timeInterval);

        elapsedTime=elapsedTime.add(timeInterval);

        logData();
    }
    public void repeatRun(long time){
        for(long i=0;i<time/timeInterval.doubleValue();i++){
            run();
        }
    }
    public void logData(){
        logger.println(elapsedTime+", "+m1.pos.y+", "+m2.pos.y+ ", "+E());
    }
    public void close(){
        logger.close();
    }
    public BigDecimal E(){
        return m1.E(g).add(m2.E(g)).add(s1.E()).add(s2.E());
    }
}
