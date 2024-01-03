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

#Definimos los valores de nuestra matriz de inercia(que es diagonal)
I1=4
I2=2
I3=1.0
par =[I1,I2,I3]
#Definimos ahora los valores iniciales de w
z0 = [0.1,2.,0.1]

#Definimos la rotacion
def rotacion(z,t,par):
    z1,z2,z3=z
    dzdt=[-(I2-I3)*z2*z3/I1,-(I3-I1)*z1*z3/I2,-(I1-I2)*z1*z2/I3]
    return dzdt
t=np.linspace(0,15,5000)
abserr = 1.0e-8
relerr = 1.0e-6
z=odeint(rotacion,z0,t,args=(par,),atol=abserr, rtol=relerr)
#Represenatamos la velocidad angular 
plt.figure()
plt.xlabel('t')
plt.ylabel('w')
plt.plot(t[:],z[:,0],label='wx',linewidth=1)
plt.plot(t[:],z[:,1],'g',label='wy',linewidth=1)
plt.plot(t[:],z[:,2],'k',label='wz',linewidth=1)
plt.legend(loc='best')
'''--------------------------1---------------------'''
y1=max(z[:3000,0])
y2=max(z[3000:,0])
T = []
for i in range(0,len(z[:,0])):
    if z[i,0]==y1:
        T.append(t[i])
    if z[i,0]==y2:
        T.append(t[i])

periodo = T[1]-T[0]
p = round(periodo,3)
frecx =( 2*np.pi)/p    
wpx = np.sqrt((((I1-I2)*(I1-I3))/(I2*I3))*0.001) 
'''------------------------------2----------------------'''
Ec = []
for i in range(0,len(z[:,0])):
    c = round(0.5*(I1*(z[i,0])**2+I2*(z[i,1])**2+I3*(z[i,2])**2),3)
    Ec.append(c)
    

plt.figure()
plt.plot(t[:],Ec)
   