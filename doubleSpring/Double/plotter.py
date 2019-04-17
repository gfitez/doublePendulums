import csv
import matplotlib.pyplot as plt

m1 = []
m2 = []
t=[]
e=[]

with open('log.txt','r') as csvfile:
    plots = csv.reader(csvfile, delimiter=',')
    for row in plots:
        t.append(float(row[0]))
        m1.append(float(row[1]))
        m2.append(float(row[2]))
        e.append(float(row[3]))

#plt.plot(t,m2)
plt.plot(t,m1)
#plt.plot(t,e)
plt.xlabel('time')
plt.ylabel('time')
plt.title('')
plt.legend()
plt.show()
