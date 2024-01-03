"""PRACTICA 3"""

import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import odeint

I1=4
I2=2
I3=1

def dwdt(w,I):
    w1,w2,w3=w
    I1=4
    I2=2
    I3=1
    dwdt=[((I3-I2)*w2*w3)/I1,
          ((I1-I3)*w1*w3)/I2,
          ((I2-I1)*w1*w2)/I3]
    return dwdt

t=np.linspace(0,25)
sol=odeint(dwdt,np.array([0.02,2,0.02]),t)


w1=np.zeros(len(sol))
w2=np.zeros(len(sol))
w3=np.zeros(len(sol))

for i in range(len(sol)):
    w1[i]=sol[i][0]
    w2[i]=sol[i][1]
    w3[i]=sol[i][2]


plt.plot(t,w1,color='orange')
plt.plot(t,w2,c='g')
plt.plot(t,w3,color='r')
plt.plot(t,sol[:,0],c='orange',label='$\omega_3$')
plt.plot(t,sol[:,1],c='g',label='$\omega_2$')
plt.plot(t,sol[:,2],c='r',label='$\omega_1$')
plt.legend(loc='best')
plt.grid()
plt.xlabel('t')
plt.ylabel('$\omega$')
plt.show()


def dwdt2I1 (w,t):
    I1=4
    I2=2
    I3=1
    w1,w2,w3,a1,a2,a3=w #w vel angular, a ac. angular
    dwdt2=[((I3-I2)*w2*w3)/I1,((I1-I3)*w1*w3)/I2,
           ((I2-I1)*w1*w2)/I3,
           ((I3-I2)*(I1-I3)*w3**2*w1)/(I1*I3),
           ((I3-I2)*(I1-I3)*w3**2*w1)/(I2*I3),
           ((I2-I1)*(I1-I3)*w1**2*w2)/(I2*I3)]
    return dwdt2


t1=np.linspace(0,100,50)
sol1=odeint(dwdt2I1,np.array([2,0.2,0.2,0,0,0]),t1)
print(len(sol1))
print(sol1)

w11=np.zeros(len(t1))
w22=np.zeros(len(t1))
w33=np.zeros(len(t1))

for i in range(len(t1)):
    w11[i]=sol1[i][0]
    w22[i]=sol1[i][1]
    w33[i]=sol1[i][2]
    
plt.plot(t1,sol1[:,0],color='orange')
plt.plot(t1,sol1[:,1],color='m')
plt.plot(t1,sol1[:,2],color='g')
plt.show()

A=np.amax(w22[0:20])
B=np.amax(w22[20:40])
a=0
b=0
for i in range(0,20):
    if w22[i]==A:
        a=i
        break
    
for i in range(20,40):
    if w22[i]==B:
        b=i
        break

    

T=t1[b]-t1[a]
print(2*np.pi/T)
wa=np.sqrt(((I1-I2)*(I1-I3))/(I2*I3))*2
print(wa)



    