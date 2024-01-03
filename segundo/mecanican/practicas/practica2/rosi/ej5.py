import numpy as np
import math
import random
import matplotlib.pyplot as plt
import matplotlib.patches as patches
import matplotlib.mlab as mlab
from scipy.integrate import odeint
from scipy import signal
import matplotlib.animation as animation
from matplotlib.pylab import *
from mpl_toolkits.axes_grid1 import host_subplot
from mpl_toolkits.mplot3d import Axes3D
from collections import deque
from IPython import display
from IPython.display import HTML

ms =750
gr = 6.67*(10**(-11))                        #Constante de gravedad universal
mt = 5.972*(10**(24)) 
mr = 750*mt/(1+mt)                       #Masa de la Tierra 
Rt = 6371000
alpha=0

f = 1.5
v0 = np.sqrt((gr*mt)/(Rt+300000)*f) #v0 para orbita eliptica
l = mr*(Rt+300000)*v0*np.sin(alpha+np.pi/2)   

tf = 15500.0 #tiempo de simulacion
def radio(y,t,par):
    r1,r2,v1,v2 = y
    dydt =[v1,v2,-(8*Rt**2*l**2*r1)/(ms*m*np.sqrt(r1**2+r2**2)**6/2),-(8*Rt**2*l**2*r2)/(ms*m*np.sqrt(r1**2+r2**2)**6/2)]
    return dydt
def potencial(m,r,gr,mt,ms,l):
    return l**2/(2*m**2*r**2) - (8*Rt**2*l**2)/(m*ms*r**5)


par = [gr,mt,Rt]
y0 = [0,(Rt+300000),v0,0]
nt = 250000
dt = tf/nt

t=np.linspace(0,tf,nt)
#abserr = 1.0e-8
#relerr = 1.0e-6
#z=odeint(radio,y0,t,args=(par,),atol=abserr, rtol=relerr)
z1=odeint(radio,y0,t,args=(par,))

#gráfica orbita
plt.figure()
plt.ylabel('y')
plt.xlabel('x')
plt.plot( z1[:17000,0],z1[:17000,1] , color='c')
plt.plot(z1[16999,0],z1[16999,1], marker="o", color="red")
#plt.savefig("trayectoriaanguloaumento.jpg", bbox_inches='tight')
E_m = np.zeros(100,float)
E = 0.5*v0**2- (8*Rt**2*l**2)/(ms*(R+Rt)**5)


for j in range(0,100):
    E_m[j]=E
    
x = np.linspace(0.5*Rt,(Rt + 300000)*6,100)
#plot(x,potencial(m,x,gr,mt,ms,l))
plt.figure()
plt.title('Potencial efectivo')
plt.ylabel('E')
plt.xlabel('r')
plt.legend(loc='best')
plt.plot( x,potencial(m,x,gr,mt,ms,l), color='g')
#plt.savefig("potencial.jpg", bbox_inches='tight')
