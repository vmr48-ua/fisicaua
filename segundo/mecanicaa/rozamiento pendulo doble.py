# -*- coding: utf-8 -*-
"""
Created on Thu May  2 16:51:14 2024

@author: lucas
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

fig=plt.figure()
fig.set_dpi(100)
#fig.set_size_inches(7,6.5)


# 0. Datos a modificar en la simulacion 
# Péndulo doble

L1=0.1      #longitud del péndulo 1
L2=0.1      #longitud del pendulo 2
m1=1.0      #masa del pendulo 1
m2=1.0      #masa del pendulo 2
g=9.81      #aceleracion de la gravedad
tf=10.0     #tiempo de simulacion
m12=m1+m2

# Ángulos iniciales en grados
theta1_0_deg= 20
theta2_0_deg= 20
# Ángulos iniciales en radianes
theta1_0=theta1_0_deg*np.pi/180.0
theta2_0=theta2_0_deg*np.pi/180.0

z0=[theta1_0,theta2_0,0.0,0.0] #Valores iniciales (velocidades inciales nulas)

par=[L1,L2,m1,m2,m12,g] #Resto de variables que necesitamos


#####################################################################
#  1. Definición de las ecuaciones de movimiento del péndulo doble  #
#####################################################################

# Desarrollo y despejo la ecuación de Euler-Lagrange
def double_pendulum(z,t,par, c1, c2):
    z1,z2,z3,z4=z  
    sinz=np.sin(z1-z2)
    cosz=np.cos(z1-z2)
    sinz1=np.sin(z1)
    sinz2=np.sin(z2)
    z42=z4*z4
    z32=z3*z3
    coszsq=cosz*cosz
    dzdt=[z3,
          z4,
          (-m2*L1*z32*sinz*cosz+g*m2*sinz2*cosz-m2*L2*z42*sinz-m12*g*sinz1)/(L1*m12-m2*L1*coszsq) - c1*z3,
          (m2*L2*z42*sinz*cosz+g*sinz1*cosz*m12+L1*z32*sinz*m12-g*sinz2*m12)/(L2*m12-m2*L2*coszsq) - c2*z4]
    return dzdt


####################################################################
#  2. Llamada a odeint, que resuelve las ecuaciones de movimiento  #
####################################################################

nt=10000  #número de intervalos de tiempo
dt=tf/nt
 
t=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6

# Coeficientes de rozamiento
c1 = 0.1  # Coeficiente de rozamiento para el péndulo 1
c2 = 0.1  # Coeficiente de rozamiento para el péndulo 2

z=odeint(double_pendulum,z0,t,args=(par,c1,c2),atol=abserr, rtol=relerr)
plt.close('all')


#########################################
#  3. Gráficas de movimiento y ángulos  #
#########################################


# Evolucion de los ángulos theta1 y theta2 con el tiempo
plt.figure()
plt.xlabel('t (s)')
plt.ylabel('$\\theta$(rad)')
plt.plot(t[:],z[:,0], linewidth=2, color='r', label ='$\\theta_1$')
plt.plot(t[:],z[:,1], linewidth=2, color='b', label ='$\\theta_2$')
plt.grid()
plt.legend(loc='best')
plt.title('Evolución de los ángulos con el tiempo')
plt.show()


fig, ax1 = plt.subplots()
ax2 = ax1.twinx()
ax1.set_xlabel('time (s)')
ax1.set_ylabel('$\\theta_1$(rad)', color='r', fontsize=15)
ax2.set_ylabel('$\\theta_2$(rad)', color='b', fontsize=15)
ax1.tick_params('y', colors='b')
ax2.tick_params('y', colors='r')
ax1.set_xlim(xmin=0.,xmax=tf) #limites del eje x
line1, = ax1.plot(t[:],z[:,0], linewidth=2, color='b')
line2, = ax2.plot(t[:],z[:,1], linewidth=2, color='r')
plt.grid()


# Animación del movimiento del péndulo doble en el espacio real (x,y)
Llong=(L1+L2)*1.1

fig, ax3 = plt.subplots()
ax3 = plt.axes(xlim=(-Llong,Llong), ylim=(-Llong,Llong))
ax3.set_xlabel('x (m)')
ax3.set_ylabel('y (m)')

line1,=ax3.plot([],[],lw=2)
line2,=ax3.plot([],[],lw=2)
line3,=ax3.plot([],[],lw=1)
bob1 = plt.Circle((1, 1),Llong*0.02, fc='b')
bob2 = plt.Circle((1, 1),Llong*0.02, fc='r')
time_template = 'time = %.1fs'
time_text = ax3.text(0.05, 0.9, '', transform=ax3.transAxes)

def init():
    bob1.center = (1, 1)
    ax3.add_artist(bob1)
    bob2.center = (0,0)
    ax3.add_artist(bob2)
    line1.set_data([],[])
    line2.set_data([],[]) 
    line3.set_data([],[])
    time_text.set_text('')
    return bob1,bob2,line1,line2,line3,time_text


def animate(i):
    x1, y1 = bob1.center
    x1 = L1*np.sin(z[i,0])
    y1 = -L1*np.cos(z[i,0])
    line1.set_data((0,x1),(0,y1))
    bob1.center = (x1, y1)
    x2, y2 = bob2.center
    x2 = x1+L2*np.sin(z[i,1])
    y2 = y1-L2*np.cos(z[i,1])
    line2.set_data((x1,x2),(y1,y2))
    line3.set_data(L1*np.sin(z[0:i,0])+L2*np.sin(z[0:i,1]),-L1*np.cos(z[0:i,0])-L2*np.cos(z[0:i,1]))

    bob2.center = (x2, y2)

    time_text.set_text(time_template%(i*dt))

    return bob1,bob2,time_text

anim = animation.FuncAnimation(fig, animate, 
                               init_func=init, 
                               frames=10000,
                               interval=5)
plt.show()



###################################################
#  4. Trayectoria del péndulo en el espacio real  #
###################################################

# Definimos x e y con coordenadas polares (z es la lista de los ángulos)


x41 = L1*np.sin(z[:,0])
y41 = -L1*np.cos(z[:,0])
x42 = x41 + L2*np.sin(z[:,1])
y42 = y41 - L2*np.cos(z[:,1])
Llong=(L1+L2)*1.1

plt.figure()
plt.axes(xlim=(-Llong,Llong), ylim=(-Llong,Llong))
plt.plot(x41,y41,"--",c='red',label='Péndulo 1')
plt.plot(x42,y42,"--",c='blue',label='Péndulo 2')
plt.xlabel('x(m)')
plt.ylabel('y(m)')
plt.title('Trayectoria en el espacio real (x,y)')
plt.grid()
plt.legend(loc='best')
plt.show()



###########################################
#  5. Trayectoria en el espacio de fases  #
###########################################
plt.figure()
plt.plot(z[:,0], z[:,2], linewidth=2, c='r', label='Péndulo 1')
plt.plot(z[:,1], z[:,3], linewidth=2, c='b', label='Péndulo 2')
plt.xlabel('$\\theta$(rad)')
plt.ylabel('$\dot{\\theta}$(rad/s)')
plt.title('Movimiento en el espacio de fases')
plt.legend(loc='best')
plt.show()



##################################
#  6. Evolución de las energías  #
##################################

# Primero definimos la energía cinética y potencial de cada una de las masas
T1 = 0.5*m1*(z[:,2]**2)*L1**2
T2 = 0.5*m2*((z[:,2]**2)*L1**2+(z[:,3]**2)*L2**2 + 2*z[:,2]*L1*z[:,3]*L2*np.cos(z[:,0]-z[:,1]))
U1 = -m1*L1*g*np.cos(z[:,0])
U2 = -m2*g*(L1*np.cos(z[:,0])+L2*np.cos(z[:,1]))
E1 = T1 + U1
E2 = T2 + U2
E = E1 + E2
    

#Graficamos todo esto que hemos conseguido
plt.figure()
plt.plot(t,T1,c='green', label='E. cinética')
plt.plot(t,U1,c='blue', label='E. potencial')
plt.plot(t,E1,c='orange', label='E. mecánica')
plt.title('Evolución de la energía para la Masa 1')
plt.grid()
plt.legend(loc='best')
plt.xlabel('tiempo (s)')
plt.ylabel('Energía (J)')
plt.show()


plt.figure()
plt.plot(t,T2,c='green', label='E. cinética')
plt.plot(t,U2,c='blue', label='E. potencial')
plt.plot(t,E2,c='orange', label='E. mecánica')
plt.title('Evolución de la energía para la Masa 2')
plt.grid()
plt.legend(loc='best')
plt.xlabel('tiempo (s)')
plt.ylabel('Energía (J)')
plt.show()          


plt.figure()
plt.plot(t,E1,c='green', label='E. mecánica 1')
plt.plot(t,E2,c='blue', label='E. mecánica 2')
plt.plot(t,E,c='orange', label='E. mecánica total')
plt.title('Evolución de la energía para el sistema')
plt.grid()
plt.legend(loc='best')
plt.xlabel('tiempo (s)')
plt.ylabel('Energía (J)')
plt.show()


####################
#     7. Caos      #
####################

theta_inicial = np.linspace(0.0, np.pi, 200)
theta_final = np.linspace(0.0, np.pi, 200)

for i in range(200):
    z0 = [theta_inicial[i], theta_inicial[i], 0.0, 0.0]
    t = np.linspace(0, tf, nt)
    z = odeint(double_pendulum, z0, t, args=(par,), atol=abserr, rtol=relerr)
    theta_final[i] = z[-1, 0]

plt.figure()
plt.plot(theta_inicial, theta_final, 'o', c='blue')
plt.xlabel('$\\theta_0$ (rad)')
plt.ylabel('$\\theta_f $ (rad)')
plt.title('Comparación de ángulos finales e iniciales para $t = 10s$')
plt.grid()
plt.show()


# Gráfico comparativo para los casos dados
# Ángulos iniciales en grados
theta1_0_deg=120
theta2_0_deg=120
theta3_0_deg=120.00001
theta4_0_deg=120.00001

# Angulos iniciales en radianes
theta1_0=theta1_0_deg*np.pi/180.0
theta2_0=theta2_0_deg*np.pi/180.0
theta3_0=theta3_0_deg*np.pi/180.0
theta4_0=theta4_0_deg*np.pi/180.0

# z0 = [θ10, θ20, ω10, ω20] # Consideremos que no tienen ω0
z0 = [theta1_0,theta2_0,0.0,0.0]
z1 = [theta3_0,theta4_0,0.0,0.0]
t=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6
z=odeint(double_pendulum,z0,t,args=(par,),atol=abserr, rtol=relerr)
z2=odeint(double_pendulum,z1,t,args=(par,),atol=abserr, rtol=relerr)
plt.close('all')

plt.figure()
plt.xlabel('$t (s)$')
plt.ylabel('$\\theta_1$(rad)')
plt.plot(t[:],z[:,0], linewidth=2, color='orange', label='$\\theta_{1}(t)$ con $\\theta_{1}(0)=120°$')
plt.plot(t[:],z2[:,0], linewidth=2, color='blue', ls='--', label='$\\theta_{1}(t)$ con $\\theta_{1}(0)=120.00001°$')
plt.legend(loc='best')
plt.title('Evolución de $\\theta_1$')
plt.grid()
plt.show()

plt.figure()
plt.xlabel('$t (s)$')
plt.ylabel('$\\theta_2$(rad)')
plt.plot(t[:],z[:,1], linewidth=2, color='orange', label='$\\theta_{2}(t)$ con $\\theta_{2}(0)=120°$')
plt.plot(t[:],z2[:,1], linewidth=2, color='blue', ls='--', label='$\\theta_{2}(t)$ con $\\theta_{2}(0)=120.00001°$')
plt.legend(loc='best')
plt.title('Evolución de $\\theta_2$')
plt.grid()
plt.show()