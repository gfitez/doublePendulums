float g=0.00001;

class Pendulum{
  float x, y, r;
  
  float theta;
  float omega=0;
 public Pendulum(float x,float y,float r,float theta){
    this.x=x;
    this.y=y;
    this.r=r;
    this.theta=theta;

  }
  void draw(){
    line(x,y,x+r*cos(theta),y+r*sin(theta));
    circle(x+r*cos(theta),y+r*sin(theta),10);
  }
  void update(){
    omega+=cos(theta)*g;
    theta+=omega;
  }  
}
class HistoryPoint{
  int x,y;
  color c;
  public HistoryPoint(int x,int y,color c){
    this.x=x;
    this.y=y;
    this.c=c;
  }
}
class DoublePendulum{
  float x,y;
  float theta1, theta2;
  float omega1, omega2;
  float r1, r2;
  float m1,m2;
  float vMax=0.0001;
  ArrayList<HistoryPoint> hist1;
  ArrayList<HistoryPoint> hist2;
  color c=color(255);
  public DoublePendulum(float x,float y){
    theta1=0;theta2=0;
    this.x=x;
    this.y=y;
    r1=75;
    r2=75;
    m1=1;//5
    m2=1;
    
    hist1=new ArrayList();
    hist2=new ArrayList();
  }
  public void draw(){
    strokeWeight(2);
    stroke(0);
    float p1x=x+r1*cos(theta1+PI/2);
    float p1y=y+r1*sin(theta1+PI/2);
    
    float p2x=p1x+r2*cos(theta2+PI/2);
    float p2y=p1y+r2*sin(theta2+PI/2);
    
    line(x,y,p1x,p1y);
    circle(p1x,p1y,10);
    line(p1x,p1y,p2x,p2y);
    circle(p2x,p2y,10);
  }
  public color getColor(){
    return c;
  }
  public void update(){
    //http://web.mit.edu/jorloff/www/chaosTalk/double-pendulum/double-pendulum-en.html

    
    float numerator=-g*(2*m1+m2)*sin(theta1)-m2*g*sin(theta1-2*theta2)-2*sin(theta1-theta2)*m2*(pow(omega2,2)*r2+pow(omega1,2)*r1*cos(theta1-theta2));
    float alpha1=numerator/(r1*(2*m1+m2-m2*cos(2*theta1-2*theta2)));
    
    numerator=2*sin(theta1-theta2)*(pow(omega1,2)*r1*(m1+m2)+g*(m1+m2)*cos(theta1)+pow(omega2,2)*r2*m2*cos(theta1-theta2));
    float alpha2=numerator/(r2*(2*m1+m2-m2*cos(2*theta1-2*theta2)));
    
    omega1+=alpha1;
    omega2+=alpha2;
    theta1+=omega1+0.5*pow(alpha1,2);
    theta2+=omega2+0.5*pow(alpha2,2);
    
    theta1%=2*PI;
    theta2%=2*PI;
    
  }
  public void repeatUpdate(int n){
    for(int i=0;i<n;i++)update();
    
    
    colorMode(HSB,255);
    int x1=(int)(this.x+r1*cos(theta1+PI/2));
    int y1=(int)(this.y+r1*sin(theta1+PI/2));
    color c = getColor();
    hist1.add(new HistoryPoint(x1,y1,c));
    
    
    int x2=(int)(x1+r2*cos(theta2+PI/2));
    int y2=(int)(y1+r2*sin(theta2+PI/2));
    
    c=this.getColor();
    hist2.add(new HistoryPoint(x2,y2,c));
    
    
    colorMode(RGB,255);
    
  }
  public void drawHistory(){
    strokeWeight(3);
    for(int i=1;i<hist1.size();i+=1){
      //stroke(hist1.get(i).c);
      //line(hist1.get(i-1).x,hist1.get(i-1).y,hist1.get(i).x,hist1.get(i).y);

      
      stroke(hist2.get(i).c,i);
      line(hist2.get(i-1).x,hist2.get(i-1).y,hist2.get(i).x,hist2.get(i).y);
    }
    if(hist1.size()>300)hist1.remove(0);
    if(hist2.size()>300)hist2.remove(0);
    
  }
  public float KE(){
    //http://scienceworld.wolfram.com/physics/DoublePendulum.html
    return 0.5*((m1+m2)*pow(r1,2)*pow(omega1,2)+2*r1*r2*cos(theta1-theta2)*omega1*omega2+m2*pow(r2,2)*pow(omega2,2));
    
    //return 0.5*m1*pow(r1,2)*pow(omega1,2)+0.5*m2*(pow(r1,2)*pow(omega1,2)+pow(r2,2)*pow(omega2,2)+2*r1*r2*omega1*omega2*cos(theta1-theta2));
  }
  public float PE(){
    float p1x=x+r1*cos(theta1+PI/2);
    float p1y=y+r1*sin(theta1+PI/2);
    
    float p2x=p1x+r2*cos(theta2+PI/2);
    float p2y=p1y+r2*sin(theta2+PI/2);
    
    return m1*(y-p1y)*g+m2*(y-p2y)*g;
  }
}

class ColoredDoublePendulum extends DoublePendulum{
  public ColoredDoublePendulum(float x,float y){
    super(x,y);
  }
  public color getColor(){
    float vt=sqrt(pow(cos(theta1)*omega1*r1+cos(theta2)*omega2*r1,2)+pow(sin(theta1)*omega1*r1+sin(theta2)*omega2*r2,2));
    vMax=max(vt,vMax);
    return color(map(abs(vt),0,vMax,255,0),255,255);
  }
}

class PendulumGrid{
  ColoredDoublePendulum[] pendulums;
  public PendulumGrid(int side){
    int count=side*side;
    pendulums=new ColoredDoublePendulum[count];
    int at=0;
    for(int i=1000/side/2;i<1000;i+=1000/side){
      for(int j=1000/side/2;j<1000;j+=1000/side){
        pendulums[at]=new ColoredDoublePendulum(i,j);
        pendulums[at].theta1=PI;
        pendulums[at].theta2=random(0.000001)+0.1;
        pendulums[at].theta1=PI+0.001;
        at++;
      }
    }
  }
void run(){
    for(int i=0;i<pendulums.length;i++){
     
     DoublePendulum pendulum=pendulums[i];
     pendulum.repeatUpdate(100);
     pendulum.drawHistory();
     pendulum.draw();
   }
  }
}

class PendulumOverlay{
  DoublePendulum[] pendulums;
  public PendulumOverlay(int count){
    pendulums=new DoublePendulum[count];
    float spread=0.0001;
    for(int i=0;i<count;i++){
      pendulums[i]=new DoublePendulum(width/2,height/2);
      pendulums[i].theta1=PI+map(i,0,count,1,1+spread);
      colorMode(HSB,255);
      pendulums[i].c=color(map(i,0,count,0,255),255,255);
      colorMode(RGB,255);
    }
    g*=100;
  }
  void run(){
    for(int i=0;i<pendulums.length;i++){
     
     DoublePendulum pendulum=pendulums[i];
     pendulum.repeatUpdate(10);
     pendulum.drawHistory();
     pendulum.draw();
   }
  }
}



PendulumGrid p;
void setup(){
  size(1000,1000);
  frameRate(60);
  
  p=new PendulumGrid(3);
}

void draw(){
   background(255);
   p.run();

   
   
}
