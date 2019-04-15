import java.io.PrintWriter;

public class Sys {
    Spring s1,s2;
    Mass m1,m2;
    final double g=0;

    int iterations;
    double elapsedTime;
    double timeInterval;

    double freq,amplitude;

    PrintWriter logger;
    public Sys(){
        m1=new Mass(new Vector(0,-1),1);
        m2=new Mass(new Vector(0,-3),1);
        s1=new Spring(new Vector(0,0),m1.pos,1,1);
        s2=new Spring(m1.pos,m2.pos,1,1);

        freq=0;
        amplitude=1;

        elapsedTime=0;
        timeInterval=0.0001;


        try{
            logger=new PrintWriter("log.txt","utf-8");
        }catch(Exception e){

        }
    }
    public void run(){
        s1.start.y=amplitude*Math.sin(elapsedTime*2*Math.PI*freq);
        s1.end=m1.pos;
        s2.start=m1.pos;
        s2.end=m2.pos;

        Vector f1=s1.force();
        f1.add(new Vector(0,m1.mass*g));
        f1.add(Vector.mult(s2.force(),-1));

        Vector f2=s2.force();
        f2.add(new Vector(0,m2.mass*g));

        m1.update(f1,timeInterval);
        m2.update(f2,timeInterval);


        iterations++;
        elapsedTime+=timeInterval;

        logData();
    }
    public void repeatRun(int secs){
        for(long i=0;i<secs/timeInterval;i++){
            run();
        }
    }
    public void logData(){
        logger.println(elapsedTime+", "+m1.pos.y+", "+m2.pos.y+ ", "+E());
    }
    public void close(){
        logger.close();
    }
    public double E(){
        return m1.E(g)+m2.E(g)+s1.E()+s2.E();
    }
}
