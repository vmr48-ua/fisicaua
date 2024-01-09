# -*- coding: utf-8 -*-
"""
PRÁCTICA 3
"""
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
from mpl_toolkits import mplot3d
    

# 0. DATOS
I1 = 1
I2 = 2
I3 = 4
m = 1
w01 = 0.1
w02 = 2
w03 = 0.1

#########################################################################

# 1. CUESTIÓN 1: variación de la velocidad angular
# Intentamos dar las componentes angulares
y0 = [w01,w02,w03]
par = [I1,I2,I3]

def angulares(y,t,par):
    w1, w2, w3 = y
    dydt = [-((I3-I2)*w3*w2)/I1, -((I1-I3)*w1*w3)/I2, -((I2-I1)*w2*w1)/I3,]
    return dydt

t=np.linspace(0,25,100)
dt=t[-1]/25
y = odeint(angulares, y0,t,args=(par,))
w1 = y[:,0]
w2 = y[:,1]
w3 = y[:,2]


#Plotea las velocidades angulares
plt.plot(t,y[:,0],c='b',label='$\omega_3$')
plt.plot(t,y[:,1],c='orange',label='$\omega_2$')
plt.plot(t,y[:,2],c='g',label='$\omega_1$')
plt.grid()
plt.xlabel('t')
plt.ylabel('$\omega$')
plt.legend(loc='best')
plt.title('Velocidades angulares')
plt.show()


#Velocidades angulares en el eje 1
y01 = [2,0.1,0.1,0,0]
par1 = [I1,I2,I3]

def ang1(y,t,par1):
    w11,w12,w13,wdd12,wdd13 = y
    dydt = [-((I3-I2)*w13*w12)/I1, -((I1-I3)*w11*w13)/I2,-((I2-I1)*w12*w11)/I3,
            ((I2-I3)*(I1-I3)*(w11**2)*w12)/(I2*I3),((I2-I3)*(I1-I3)*(w11**2)*w13)/(I2*I3)]
    return dydt

y1 = odeint(ang1,y01,t,args =(par1,))
#plt.plot(t,y1[:,0],label='$\omega_1$')
plt.plot(t,y1[:,1],label='$\omega_2$')
plt.plot(t,y1[:,2],label='$\omega_3$')
plt.grid()
plt.xlabel('t')
plt.ylabel('$\omega$')
plt.title('Velocidades angulares para el eje 1')
plt.legend(loc='best')
plt.show()

#Velocidades angulares en el eje 3
def ang3(y,t,par1):
    w31,w32,w33,wdd31,wdd32 = y
    dydt = [-((I3-I2)*w33*w32)/I1, -((I1-I3)*w31*w33)/I2,-((I2-I1)*w32*w31)/I3,
            ((I3-I2)*(I1-I3)*(w33**2)*w31)/(I2*I3),((I2-I3)*(I1-I3)*(w33**2)*w32)/(I2*I3)]
    return dydt

y3 = odeint(ang3,y01,t,args =(par1,))
plt.plot(t,y3[:,0],label='$\omega_1$')
#plt.plot(t,y3[:,1],label='$\omega_2$')
plt.plot(t,y3[:,2],label='$\omega_3$')
plt.grid()
plt.xlabel('t')
plt.ylabel('$\omega$')
plt.title('Velocidades angulares para el eje 3')
plt.legend(loc='best')
plt.show()



# Damos la velocidad angular calculada y como debería dar sabiendo que T = 5
print("w calculada: {}  w experimental. {}".format(np.sqrt((I1-I2)*(I1-I3)/(I2*I3))*y1[:,0][0], 
                                                   (2*np.pi)/5))


####################################################################

# CUESTIÓN 2: conservación de la energía cinética y del módulo del momento angular
def Ec(I1, I2, I3, w1,w2,w3):
    return 0.5*(I1*(w1**2)+I2*(w2**2)+I3*(w3**2))

def momang(I1, I2, I3, w1,w2,w3):
    return np.sqrt((I1**2)*(w1**2)+ (I2**2)*(w2**2)+(I3**2)*(w3**2))

#Eje 1
plt.figure()
plt.plot(t,Ec(I1, I2, I3, y1[:,0],y1[:,1],y1[:,2]),label='Energía cinética')
plt.legend(loc='best')
plt.ylim(0,4)
plt.ylabel('$E_{cinética}$')
plt.xlabel('$t (s)$')
plt.title('Energía cinética')
plt.grid()
plt.show()

plt.figure()
plt.plot(t,momang(I1, I2, I3,y1[:,0],y1[:,1],y1[:,2]),label='Módulo del momento angular')
plt.legend(loc='best')
plt.ylim(0,4)
plt.ylabel('$L$')
plt.xlabel('$t (s)$')
plt.title('Momento angular')
plt.grid()
plt.show()

plt.figure()
plt.plot(t,momang(I1, I2, I3, y1[:,0],y1[:,1],y1[:,2]),label='Módulo del momento angular')
plt.legend(loc='best')
plt.ylabel('$L$')
plt.xlabel('$t (s)$')
plt.title('Precesión en el eje de giro')
plt.grid()
plt.show()

#Damos los valores iniciales y finales para ver que hay un leve movimiento
print(momang(I1, I2, I3, y1[:,0][0],y1[:,1][0],y1[:,2][0]),
      momang(I1, I2, I3, y1[:,0][-1],y1[:,1][0],y1[:,2][0]))


#Eje 3
plt.figure()
plt.plot(t,Ec(I1, I2, I3, y3[:,0],y3[:,1],y3[:,2]),label='Energía cinética')
plt.legend(loc='best')
plt.ylim(0,4)
plt.ylabel('$E_{cinética}$')
plt.xlabel('$t (s)$')
plt.title('Energía cinética')
plt.grid()
plt.show()

plt.figure()
plt.plot(t,momang(I1, I2, I3,y3[:,0],y3[:,1],y3[:,2]),label='Módulo del momento angular')
plt.legend(loc='best')
plt.ylim(0,4)
plt.ylabel('$L$')
plt.xlabel('$t (s)$')
plt.title('Momento angular')
plt.grid()
plt.show()

plt.figure()
plt.plot(t,momang(I1, I2, I3, y3[:,0],y3[:,1],y3[:,2]),label='Módulo del momento angular')
plt.legend(loc='best')
plt.ylabel('$L$')
plt.xlabel('$t (s)$')
plt.title('Precesión en el eje de giro')
plt.grid()
plt.show()

#Damos los valores iniciales y finales para ver que hay un leve movimiento
print(momang(I1, I2, I3, y3[:,0][0],y3[:,1][0],y3[:,2][0]),
      momang(I1, I2, I3, y3[:,0][-1],y3[:,1][0],y3[:,2][0]))

#############################################################################
# 3. CUESTIÓN 3: LO MISMO, EN EL EJE 2
y02 = [2,0.2,0.2,0,0]
par2 = [I1,I2,I3]

def ang2(y,t,par2):
    w21,w22,w23,wdd22,wdd23 = y
    dydt = [-((I3-I2)*w23*w22)/I1, -((I1-I3)*w21*w23)/I2,-((I2-I1)*w22*w21)/I3,
            ((I3-I2)*(I2-I1)*(w22**2)*w21)/(I1*I3),((I3-I2)*(I2-I1)*(w22**2)*w23)/(I1*I3)]
    return dydt

y2 = odeint(ang2,y02,t,args =(par2,))
plt.plot(t,y2[:,0],label='$\omega_1$')
#plt.plot(t,y2[:,1],label='$\omega_2$')
plt.plot(t,y2[:,2],label='$\omega_3$')
plt.grid()
plt.xlabel('t')
plt.ylabel('$\omega$')
plt.title('$\omega_1$ frente a $\omega_2$')
plt.legend(loc='best')
plt.show()


####################################################################
# 4. CUESTIÓN 4: energía cinética y momento angular en este eje
plt.figure()
plt.plot(t,Ec(I1, I2, I3, y2[:,0],y2[:,1],y2[:,2]),c='r')
plt.ylim(0,5)
plt.ylabel('$E$')
plt.xlabel('$t$')
plt.title('Energía Cinética')
plt.grid()
plt.show()

plt.figure()
plt.plot(t,momang(I1, I2, I3,y2[:,0],y2[:,1],y2[:,2]))
plt.ylim(0,5)
plt.ylabel('$|L|$')
plt.xlabel('$t$')
plt.title('Momento Angular')
plt.grid()
plt.show()


######################################################################
# 5. CUESTIÓN 5: representar los vectores w y L
# Momento angular
Lmod = momang(I1, I2, I3, w1,w2,w3)[0]
Lx = (I1*w1)/Lmod
Ly = (-I2*w2)/Lmod
Lz = (I3*w3)/Lmod

fig = plt.figure()
ax1 = fig.add_subplot(111,projection='3d')
ax1.plot3D(Lx,Ly,Lz,label='$\\vec{L}$')
ax1.set_xlabel('$\\omega_1$')
ax1.set_ylabel('$\\omega_2$')
ax1.set_zlabel('$\\omega_3$')
plt.title('Variación del vector momento angular')
plt.legend(loc='best')
plt.show()


# Velocidad angular
fig = plt.figure()
ax1 = fig.add_subplot(111,projection='3d')
ax1.plot3D(w1,w2,w3,label='$\\vec{\\omega}$')
ax1.set_xlabel('$\\omega_1$')
ax1.set_ylabel('$\\omega_2$')
ax1.set_zlabel('$\\omega_3$')
plt.title('Variación del vector velocidad angular')
plt.legend(loc='best')
plt.show()

# Variación del periodo según las condiciones iniciales de wi (pero no las de
# Ij) -> gráficos guardados
I1v = 1
I2v = 2
I3v = 4
m = 1
w01v = 0.2
w02v = 4
w03v = 0.1

y0v = [w01v,w02v,w03v]
parv = [I1v,I2v,I3v]

def angularesv(y,tv,parv):
    w1, w2, w3 = y
    dydt = [-((I3v-I2v)*w3*w2)/I1v, -((I1v-I3v)*w1*w3)/I2v, -((I2v-I1v)*w2*w1)/I3v]
    return dydt

tv=np.linspace(0,25,100)
dtv=tv[-1]/25
yv = odeint(angularesv, y0v,tv,args=(parv,))
w1v = yv[:,0]
w2v = yv[:,1]
w3v = yv[:,2]

Lxv = I1v*w1v
Lyv = -I2v*w2v
Lzv = I3v*w3v

fig = plt.figure()
ax1 = fig.add_subplot(111,projection='3d')
ax1.plot3D(Lx,Ly,Lz,label='$\\vec{L}$ original')
ax1.plot3D(Lxv,Lyv,Lzv,ls = '--',label='$\\vec{L}$ donde hemos cambiado las condiciones inciales')
plt.title('Variación del vector momento angular')
plt.legend(loc='best')
plt.show()

# Velocidad angular
fig = plt.figure()
ax1 = fig.add_subplot(111,projection='3d')
ax1.plot3D(w1,w2,w3,label='$\\vec{\\omega}$')
ax1.plot3D(w1v,w2v,w3v,ls = '--', label='$\\vec{\\omega}$ donde hemos cambiado las condiciones inciales')
plt.title('Variación del vector velocidad angular')
plt.legend(loc='best')
plt.show()






