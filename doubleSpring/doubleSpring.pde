float pixelsPerMeter=50;
float timeInterval=0.01;
float g=9.8;

class Spring{
  PVector start,end;
  float naturalLength;
  float k;
  public Spring(PVector start, PVector end, float naturalLength, float k){
    this.start=start;
    this.end=end;
    this.naturalLength=naturalLength;
    this.k=k;
  }
  public float length(){
    float dist =start.dist(end);
    return dist;
  }
  public void setPos(PVector start, PVector end){
    this.start=start;
    this.end=end;
  }
  public void setStart(PVector start){
    this.start=start;
  }
  public void setEnd(PVector end){
    this.end=end;
  }
  public PVector force(){
    float f=(naturalLength-length())*k;
    float theta=atan2(-(start.y-end.y),-(start.x-end.x));
    return new PVector(f*cos(theta),f*sin(theta));
  }
  public void draw(){
    stroke(map(abs(naturalLength-length()),0,1*naturalLength,0,255),0,0);
    strokeWeight(10);
    line(start.x*pixelsPerMeter,start.y*pixelsPerMeter,end.x*pixelsPerMeter,end.y*pixelsPerMeter);
  }
  public float E(){
    return 0.5*k*pow(length()-naturalLength,2);
  }
  
}
class Mass{
  PVector pos;
  PVector vel;
  float mass;
  public Mass(PVector pos,float mass){
    this.pos=pos;
    this.mass=mass;
    this.vel=new PVector(0,0);
  }
  public void draw(){
    stroke(0);
    strokeWeight(1);
    circle(pos.x*pixelsPerMeter,pos.y*pixelsPerMeter,sqrt(mass)*20);
  }
  public void update(PVector force){
    PVector acc=PVector.div(force,mass);
    vel.add(PVector.mult(acc,timeInterval));
    pos.add(PVector.mult(vel,timeInterval));
  }
  public PVector getPos(){
    return pos;
  }
  public float KE(){
    return 0.5*mass*pow(vel.mag(),2);
  }
  public float PE(){
    return -pos.y*g*mass;
  }
  public float E(){
    return KE()+PE();
  }
}

class System{
  Mass m1,m2;
  Spring s1,s2;
  public System(){
    s1=new Spring(new PVector(10,6),new PVector(10,8),2,40);
    m1=new Mass(s1.end,1);
    s2=new Spring(m1.pos,new PVector(11,10),2,80);
    m2=new Mass(s2.end,2);
  }
  public void run(){
    PVector f1=s1.force();
    f1.add(new PVector(0,9.8*m1.mass));
    f1.add(s2.force().mult(-1));
    m1.update(f1);
    
    PVector f2=s2.force();
    f2.add(new PVector(0,9.8*m2.mass));
    m2.update(f2);
    
    
    s1.setEnd(m1.pos);
    s2.setPos(m1.pos,m2.pos);
  }
  public void draw(){
    s1.draw();
    s2.draw();
    m1.draw();
    m2.draw();
  }
  public float E(){
    return s1.E()+s2.E()+m1.E()+m2.E();
  }
}

System s=new System();
void setup(){
  frameRate(60);
  size(1000,1000);
  
}
void draw(){
  background(255);
  //s.s1.setStart(new PVector(10,5+sin(2*PI*millis()/1000.0/2)));
  

  s.run();
  
  s.draw();
println(s.E());
  
  
}
