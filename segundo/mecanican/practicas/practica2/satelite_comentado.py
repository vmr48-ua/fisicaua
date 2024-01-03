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


fig=plt.figure()
fig.set_dpi(100)
#fig.set_size_inches(7,6.5)


# Datos a modificar en la simulacion 

m=1.0                           # Valor de las masa
mati=5.9*10**(24)
gr=6.67*10**(-11)
radi= 6378000  # radio tierra

tf=9500    #tiempo de simulacio



# Para dibujar el raadio de la Tierra

rapla = np.zeros(360,float)
radix = np.zeros(360,float)
radiy = np.zeros(360,float)

for j in range (0,360):
  rapla[j] = 2*j*np.pi/360
  radix[j]= radi*np.cos(rapla[j])  #  
  radiy[j]= radi*np.sin(rapla[j])  # 


par=[gr,mati,radi]


# Definiendo  gravedad

def tiro(z,t,par):
    z1,z2,z3,z4=z  
    dzdt=[z3,z4, 
          -gr*mati*z1/np.sqrt(z1**2+z2**2)**3,
         -gr*mati*z2/np.sqrt(z1**2+z2**2)**3 ]
    return dzdt



# Llamada a odeint que resuelve las ecuaciones de movimiento

nt=75000  #numero de intervalos de tiempo
dt=tf/nt

# Valores iniciales
z1_0= radi+300000                 # x punto  inicial
z2_0=0.0                         # y punto  inicial
z3_0= 0                          # Velocidad x 
z4_0= np.sqrt(gr*mati/(z1_0))*1.1     # Velocidad y

z0=[z1_0,z2_0,z3_0,z4_0] #Valores iniciales   
 
t=np.linspace(0,tf,nt)
at=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6

z=odeint(tiro,z0,t,args=(par,),atol=abserr, rtol=relerr)

# Calculo del momento angular inicial y energia total inicial
# SOLO SI V TIENE SOLO COMPONENTE Y LA POSICIÓN COMPONENTE X 

lo= m*z4_0*z1_0
E0=0.5*m*z4_0**2-gr*mati*m/z1_0

print (E0,lo,z4_0)

# definimos el tramo radial sobre el que representamos el 
# potencial y la energía. Hay que ajustarlo a mano.

rix= np.linspace(z1_0*0.9,1.6*z1_0,100)

Pote= 0.5*lo**2/m**2/rix**2 -gr*mati/rix
ene1=np.linspace(E0,E0,100)



### Gráficas

# Recordemos z[:,0] es x 
# Recordemos z[:,1] es y
# Recordemos z[:,2] es Vx
# Recordemos z[:,3] es Vy

plt.close('all')

# Trayectoria xy

plt.figure(figsize=(4,4))
plt.xlabel('x')
plt.ylabel('y')
plt.plot(z[:,0],z[:,1],linewidth=0.3 )
plt.plot(radix,radiy, linewidth=0.3)  # dibuja el contorno de la Tierra

# Evolución del radio con el tiempo

rad=np.sqrt( z[:,0]**2 + z[:,1]**2)

plt.figure(figsize=(4,4))
plt.xlabel('t')
plt.ylabel('R')
plt.plot(t[:],rad[:],linewidth=0.3 )

# Velocidades

plt.figure(figsize=(4,4))
plt.xlabel('Vx')
plt.ylabel('Vy')
plt.plot(z[:,2],z[:,3],linewidth=0.3 )

# Evolución de la velocidad con el tiempo

vel=np.sqrt( z[:,2]**2 + z[:,3]**2)

plt.figure(figsize=(4,4))
plt.xlabel('t')
plt.ylabel('V')
plt.plot(t[:],vel[:],linewidth=0.3)
    
     

plt.figure(figsize=(4,4))
plt.xlabel('r')
plt.ylabel('E')
plt.plot(rix,Pote,linewidth=0.5 )
plt.plot(rix,ene1 ,linewidth=1.5)  



#Animación
x=z[:,0]
y=z[:,1]

fig, ax = plt.subplots()
ax.set_xlim(min(x)-1e6, max(x)+1e6)
ax.set_ylim(min(y)-1e6, max(y)+1e6)
ax.plot(0, 0, "*r")
line, = ax.plot(x[:1], y[:1])
plt.plot(radix,radiy, linewidth=0.3) 
a = 100


def Orb(i):
    
    line.set_data(x[:a*i+1], y[:a*i+1])
    
    return line

ani = animation.FuncAnimation(fig, Orb, frames=len(x)//a, interval=5)

#ani.save("orbeli.gif")

plt.show()