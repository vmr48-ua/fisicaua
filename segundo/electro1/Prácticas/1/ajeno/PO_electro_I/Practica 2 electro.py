#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May 27 14:54:34 2022

@author: maria
"""

import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import axes3d

I = 1000
R = 0.5

'''
Campo magnético debido a una espira (Ley de Biot-Savart)
'''

def dB(I,dl,r0,rp):
  # r0 = distancia al diferencial
  # rp = distancia al punto
  mu = 1
  r=(rp[0]-r0[0],rp[1]-r0[1],rp[2]-r0[2])
  den = ((r[0])**2+(r[1])**2+(r[2])**2)**1.5
  factor = mu/(4*np.pi)*I/den
  return factor*(dl[1]*r[2]-dl[2]*r[1]), factor*(dl[2]*r[0]-dl[0]*r[2]), factor*(dl[0]*r[1]-dl[1]*r[0])

def B(I,R,r):
  res = 500
  Bx = By = Bz = 0
  dalpha = 2*np.pi/res
  for i in range(0,res):
    alpha = i*dalpha
    dl = (-np.sin(alpha), np.cos(alpha),0)
    dBc = dB(I,dl,(R*np.cos(alpha),R*np.sin(alpha),0),r)
    Bx += dBc[0]
    By += dBc[1]
    Bz += dBc[2]
  factor = R*dalpha
  return factor*Bx, factor*By, factor*Bz

x = np.linspace(-4,4,30)
y = np.linspace(-4,4,30)
z = np.linspace(-4,4,30)

X,Z = np.meshgrid(x,z)
x1,y1,z1 = np.meshgrid(x,y,z)
bx, by, bz = B(I,R,(X,0,Z))
modulo = (bx**2+by**2+bz**2)**0.5

# Representamos el campo
fig = plt.figure()
az = fig.add_subplot(211)
color = 2*np.log(np.hypot(bx,bz))
plt.streamplot(X,Z,bx,bz,color = color,linewidth = 1,cmap = plt.cm.inferno,density = 2, arrowstyle = '->', arrowsize = 1.5)
plt.title('Campo magnético')

# Definimmos un mapa de colores para representar la magnitud contenida en la matriz
colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

#Terminamos dibujando todos los vectores de la matriz ("cortes en las direcciones horizontales y verticales)
a3=fig.add_subplot(223)
plt.plot(y,np.transpose(modulo))
a3.set_xlabel('$z$')
plt.ylim(0,2000)

a4=fig.add_subplot(224)
plt.plot(x,modulo)
a4.set_xlabel('$x$')
plt.ylim(0,2000)
plt.show()

# Representación en 3D
x = np.linspace(-4,4,8)
y = np.linspace(-4,4,8)
z = np.linspace(-4,4,8)

X,Z = np.meshgrid(x,z)
x1,y1,z1 = np.meshgrid(x,y,z)
bx, by, bz = B(I,R,(x1,y1,z1))
modulo = (bx**2+by**2+bz**2)**0.5

fig = plt.figure()
ax = fig.gca(projection = '3d')
ax.quiver(x1,y1,z1,bx,by,bz,color = 'b', length = 1, normalize = True)

'''
Campo magnético debido a un momento dipolar magnético
'''
'''
def Bdip(m,p):
    
    K = 1e-7
    x,y,z = p[0],p[1],p[2]
    r = (x**2+y**2+z**2)**(1/2)
    ru = p/r
    
    B = (K/r**3)*(3*np.dot(m,ru)*ru-m)
    
    return B

#Datos
#m = np.array([0,0,1000*np.pi*0.5**2])
m = np.array([0,0,200])
P = 200
y = np.linspace(-4,4,P)
z = np.linspace(4,-4,P)
#bx,by,bz 
grid3 = []
for j in z:
    line3 = []
    for i in y:
        B = Bdip(m,np.array([0,i,j]))
        line3.append(B)
    grid3.append(line3)
    
BZ = np.zeros((P,P))
BY = np.zeros((P,P))
BX = np.zeros((P,P))

for i in range(P):
    for j in range(P):
        BX[i,j] = grid3[i][j][0]
        BY[i,j] = grid3[i][j][1]
        BZ[i,j] = grid3[i][j][2]
modulo = (BX**2+BY**2+BZ**2)**0.5

#GRAFICAR
color = 2*np.log(np.hypot(BX,BZ))
plt.streamplot(y,z,BY,BZ, color = color, linewidth = 1,cmap = plt.cm.inferno,density = 2, arrowstyle = '->', arrowsize = 1.5)
plt.title('Campo magnético momento dipolar')
plt.xlim(-4,4)
plt.ylim(-4,4)
plt.show()

colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

a3=fig.add_subplot(223)
plt.plot(y,np.transpose(modulo))
a3.set_xlabel('$z$')

a4=fig.add_subplot(224)
plt.plot(x,modulo)
a4.set_xlabel('$x$')
plt.show()
'''