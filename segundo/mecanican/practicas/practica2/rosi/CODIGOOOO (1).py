# -*- coding: utf-8 -*-
"""
ups
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
    
MT = 5.972*(10**(24))                       #Masa de la Tierra 
RT = 6371000                                #Radio de la Tierra   
MS = 750                                  #La masa típica de un satélite
G = 6.67*(10**(-11))                        #Constante de gravedad universal
mr = MS*MT/(MS+MT)                         #Masa reducida
#hi = 35867000  
hi = 30000000
alpha = 0                                #inclinación de la velocidad inical                             #altura inicial
ri = hi + RT 
vi = np.sqrt(G*MT/ri)                                #Velocidad inicial
#vi = np.sqrt(G*MT/(ri*1.5))                     #Velocidad elipse
#vi = np.sqrt(G*MT/(ri*2))                       #Velocidad parábola
#vi = np.sqrt(G*MT/(ri*3))                               #Radio de órbita inicial
L = mr*ri*vi*np.sin(alpha+np.pi/2)          #Momento angular
E = ((1/2)*mr*(vi**2)-(G*MS*MT)/ri)/MS        #Energía mecánica

# 1. POTENCIAL EFECTIVO
def V(r):
    return  (L**2)/(2*mr*(r**2))-(G*MS*MT)/r

r = np.linspace(RT*3,RT*30,300)             #Rango de Radios para el potencial(¡¡Cuidado!!)
v = V(r)/MS
re = []
for i in range(len(v)):
    if E>=v[i]:
        re.append(r[i])
        
plt.figure()
plt.plot(r,v, label='Potencial')
plt.plot(r,[E]*len(r), color ='b',ls="--", label='Energía total')
if len(re)>0:
    plt.plot(re[0],E,marker="o",color="y", label = 'Perigeo')
    print("El perigeo es {}".format(re[0]))
    if re[-1]<r[-1]:
        plt.plot(re[-1],E,marker="o",color="g", label = 'Apogeo')
        print("El apogeo es {}".format(re[-1]))
    else:
        print("La orbita no está acotada")
plt.xlabel("$r$")
plt.ylabel("$V_{eff}(r)$")
#plt.plot(r,[-0.39*10**7]*len(r),color='r',label='$E_{min}$')
plt.grid()
plt.legend(loc='best')
plt.title('Potencial efectivo')
plt.show()



# 2. DIBUJO ÓRBITA
x0=[0, hi, vi, 0]  
par = [G,MT,RT]

tf = 200000
nt=25000 #numero de intervalos de tiempo
dt=tf/nt
t=np.linspace(0,tf,nt)

def tiro(z,t,par):
    z1,z2,z3,z4=z
    dzdt = [z3,z4,
            -G*MT*MS*z1/(mr*(z1**2+z2**2)**(3/2)),
            -G*MT*MS*z2/(mr*(z1**2+z2**2)**(3/2))]
    return dzdt
t=np.linspace(0,200000,1000)
dt=t[-1]/20000
z = odeint(tiro, x0,t,args=(par,))

rapla = np.zeros(len(t), float)
radix = np.zeros(len(t), float)
radiy = np.zeros(len(t), float)
rady = np.zeros(len(t),float)

for j in range(len(t)): #varía el ángulo, lo pasa a radianes y crea círculo
    rapla[j] = 2*j*np.pi/360
    radix[j] = RT*np.cos(rapla[j])
    radiy[j] = RT*np.sin(rapla[j])
    
angulos = np.linspace(0,2*np.pi,300)
fig = plt.figure()
ax = fig.add_subplot(111)
ax.set_aspect('equal', adjustable='box')
#plt.plot(ri*np.sin(angulos), ri*np.cos(angulos),color = 'orange', ls="--", label ='Satélite')
plt.plot(z[:,0], z[:,1], color = 'orange', ls="--", label ='Satélite')
plt.plot(radix,radiy, color ='b',label="Planeta Tierra")
plt.fill(radix,radiy, color ='c')
#plt.plot(a, y2, c='orange', ls="--", label='Satélite')
#plt.plot(a, y1, c='orange', ls="--")
plt.title('Órbita del satélite')
plt.xlabel("$x$")
plt.ylabel("$y$")
plt.legend(loc='best')
plt.grid()
plt.show()


# 3. VARIOS TIPOS DE ÓRBITA (queda circular y parábola)
# Potenciales
plt.figure()
plt.plot(r, [1.5e6]*len(r), color='brown',ls='--', label='$E_{hipérbola}$')
plt.plot(r,v, label='$V_{eff}(r)$')
plt.plot(r,[E+1e6]*len(r), color ='orange',ls="--", label='$E_{elipse}$')
plt.plot(r,[0]*len(r),color='r',ls ='--',label='$E_{círculo}$')
plt.plot(r, [-0.4*10**7]*len(r), color='g',ls='--', label ='$E_{parábola}$')
plt.xlabel("$r$")
plt.ylabel("$V_{eff}(r)$")
plt.grid()
plt.legend(loc='best')
plt.title('Energía total respecto al tipo de órbita')
plt.show()




# 5. POTENCIAL DEL EJERCICIO 3
r = np.linspace(RT*3,RT*30,300)    
def potencial(r):
    return (-(8*(RT**2)*(L**2))/MS)*(-4/(r**4))

def poteff(r):
    return potencial(r) + (L**2)/(2*MS*(r**2))

plt.plot(r,poteff(r),label='$V_{eff}(r)$')
plt.xlabel('$r$')
plt.ylabel('$V_{eff}$')
plt.grid()
plt.legend(loc='best')
plt.title('Potencial en el caso dado')
plt.show()



