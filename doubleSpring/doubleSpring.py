import math, time
import matplotlib.pyplot as plt
import numpy as np

g=-9.8

class Vector:
    def __init__(self,x,y):
        self.x=x;
        self.y=y;

    def dist(self,other):
        return math.sqrt(pow(self.x-other.x,2)+pow(self.y-other.y,2));
    def mag(self):
        return math.sqrt(pow(self.x,2)+pow(self.y,2))
    def add(self,other):
        self.x+=other.x
        self.y+=other.y


def vAdd(v1,v2):
    return Vector(v1.x+v2.x,v1.y+v2.y)


def vMult(v1,i):
    return Vector(v1.x*i,v1.y*i)

def vDiv(v1,i):
    return Vector(v1.x/i,v1.y/i)


class Spring:
    def __init__(self,start,end,length,k):
        self.start=start;
        self.end=end;
        self.naturalLength=length;
        self.k=k;
    def length(self):
        return self.start.dist(self.end)

    def force(self):
        f=(self.naturalLength-self.length())*self.k;
        theta=math.atan2(-(self.start.y-self.end.y),-(self.start.x-self.end.x));
        return Vector(f*math.cos(theta),f*math.sin(theta));
    def E(self):
        return 0.5*self.k*pow(self.length()-self.naturalLength,2)

class Mass:
    def __init__(self,pos,mass):
        self.pos=pos
        self.mass=mass
        self.vel=Vector(0,0)
        self.acc=Vector(0,0)
    def update(self,force,timeInterval):
        #pos +=                        (   v     *     t    )      +     (                a   * 0.5) *  t          ^ 2
        self.pos.add(vAdd(vMult(self.vel,timeInterval),vMult(vMult(self.acc,0.5),pow(timeInterval,2))))
        oldAcc=self.acc
        self.acc=vDiv(force,self.mass)

        #v+=                           (           (   a    + olda )*0.5)*timeInterval)
        self.vel.add(vMult(vMult(vAdd(self.acc,oldAcc),0.5),timeInterval))
    def KE(self):
        return 0.5*self.mass*pow(self.vel.mag(),2)
    def PE(self):
        return self.pos.y*-g*self.mass;
    def E(self):
        return self.KE()+self.PE()

class Sys:
    def __init__(self):
        self.m1=Mass(Vector(0,-1),1)
        self.m2=Mass(Vector(0,-2),1)
        self.s1=Spring(Vector(0,0),self.m1.pos,1,10)
        self.s2=Spring(self.m1.pos,self.m2.pos,1,10)

        self.timeInterval=0.001;
        self.timeElapsed=0;

        self.times=[]
        self.energies=[];
        self.m2y=[];
        self.m1y=[]

        self.freq=0.1;
        self.amplitude=1;
    def run(self):
        self.m1.pos.y=self.amplitude*math.sin(self.timeElapsed*2*math.pi*self.freq)

        self.s1.end=self.m1.pos;
        self.s2.start=self.m1.pos;
        self.s2.end=self.m2.pos;

        f1=self.s1.force();
        f1.add(vMult(self.s2.force(),-1))
        f1.add(Vector(0,g*self.m1.mass))

        f2=self.s2.force();
        f2.add(Vector(0,g*self.m2.mass))

        self.m1.update(f1,self.timeInterval)
        self.m2.update(f2,self.timeInterval)

        self.timeElapsed+=self.timeInterval;

    def logData(self):
        self.energies.append(self.m1.E()+self.m2.E()+self.s1.E()+self.s2.E())
        self.m2y.append(self.m2.pos.y)
        self.m1y.append(self.m1.pos.y)
        self.times.append(self.timeElapsed)
    def runSim(self,time):
        for i in range(math.floor(time/self.timeInterval)):
            self.run()
            self.logData()


freq=0;
pos=[]
freqs=[]
maxFreq=5;
increment=0.0025
startTime=time.time()
for i in range(math.floor(maxFreq/increment)):
    s=Sys();
    s.freq=freq
    s.runSim(10);
    pos.append(s.m2.pos.y)
    freqs.append(freq)
    freq+=increment

    elapsed=time.time()-startTime
    complete=freq/maxFreq
    print(str(complete*100)+"% ("+str(elapsed/complete-elapsed) + " seconds remaining)")

plt.plot(freqs,pos)
#plt.plot(s.times,s.m1y)
#print(s.energies[len(s.energies)-1]/s.energies[0])
plt.show()
