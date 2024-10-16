# -*- coding: utf-8 -*-
"""
Intro. a la Modelizacion en fisica

Aroa Anton Samaniego; 74533176C

Ejericio 5

"""
import numpy as np
import matplotlib.pyplot as plt
from scipy import sparse
from matplotlib import animation

#PARTE A: ECUACION DE ADVECCION

def dif_centradas(f, N, dt, dx, c, t):
    for k in range(len(t) - 1):
        f[k+1, 1:-1] = f[k, 1:-1] - (c * dt) / (2 * dx) * (f[k, 2:] - f[k, :-2])
        # Aplicar condiciones de contorno periódicas
        f[k+1, 0] = f[k+1, -2]  # El primer punto depende del penúltimo
        f[k+1, -1] = f[k+1, 1]  # El último punto depende del segundo
    return f

def upwind(f1, N, dt, dx, c, t):
    for k in range(len(t) - 1):
        f1[k+1, 1:-1] = f1[k, 1:-1] - (c * dt) / dx * (f1[k, 1:-1] - f1[k, :-2])
        # Aplicar condiciones de contorno periódicas
        f1[k+1, 0] = f1[k+1, -2]  # El primer punto depende del penúltimo
        f1[k+1, -1] = f1[k+1, 1]  # El último punto depende del segundo
    return f1

def downwind(f2, N, dt, dx, c, t):
    for k in range(len(t) - 1):
        f2[k+1, 1:-1] = f2[k, 1:-1] - (c * dt) / dx * (f2[k, 2:] - f2[k, 1:-1])
        # Aplicar condiciones de contorno periódicas
        f2[k+1, 0] = f2[k+1, -2]  # El primer punto depende del penúltimo
        f2[k+1, -1] = f2[k+1, 1]  # El último punto depende del segundo
    return f2

dx =0.1
dt=0.01
c = 2
C = (c*dt)/dx #condicion de estabilidad
if C>1:
    print('No se cumple la condición de estabilidad')
L = 5
x = np.arange(0,L,dx)
t=np.arange(0,10,dt)
N =len(x)
f = np.zeros((len(t),N))
f1 = np.zeros((len(t),N))
f2 = np.zeros((len(t),N))


#Aplicamos la condicion de contorno
for i in range(0,N):
    if 1<=x[i]<=2:
        f[0,i]=1
        f1[0,i]=1
        f2[0,i]=1
    else:
        f[0,i]=0
        f1[0,i]=0
        f2[0,i]=0
        

u1 = dif_centradas(f, N, dt, dx, c,t)
u2 = upwind(f1, N, dt, dx, c,t)
u3 = downwind(f2, N, dt, dx, c,t)


#Ahora construimos la grafica para hacer la animacion

fig, ax = plt.subplots()

line1, = ax.plot(x, u1[0, :], lw=2,label='Dif. centradas')
line2, = ax.plot(x, u2[0, :], lw=2,label='Upwind')
line3, = ax.plot(x, u3[0, :], lw=2,label='Downwind')
ax.set_ylim(-0.1, 1.1)
ax.set_xlim(0,5)
ax.set_title('Ecuacion de Advección')
ax.set_xlabel('$x$')
ax.set_ylabel('u(x)')
ax.legend(loc='best')
time_text = ax.text(0.05, 0.9, '', transform=ax.transAxes)

def animate(i):
    line1.set_ydata(u1[i, :])  
    line2.set_ydata(u2[i, :]) 
    line3.set_ydata(u3[i, :]) 
    time_text.set_text('t = {}'.format(np.round(dt*i,4)))
    return line1, line2, line3, time_text

anim = animation.FuncAnimation(fig, animate, frames=range(0,len(t),2), interval=1)
plt.show()
