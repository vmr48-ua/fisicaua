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


# Datos a modificar en la simulacion 
# Muelles acoplados

m=1.0      # Valor de las masas
k=10.0      # Constante elástica de los muelles externos
kp=0.5      # Constante elástica del muelle que une ambas masas
tf=60.0    #tiempo de simulacion
mi=1.0/m   # Inversa de la masa


# Posiciones de equilibrio de las dos masas
d1=1.0
d2=2.0


par=[mi,k,kp]



# Definicion de las ecuaciones de movimiento de los muelles acoplados
def double_pendulum(z,t,par):
    z1,z2,z3,z4=z  
    dzdt=[z3,z4,(-k*z1+kp*(z2-z1))*mi,(-k*z2-kp*(z2-z1))*mi]
    return dzdt

def double_pendulum2(z,t,par):
    z1,z2,z3,z4=z  
    dzdt=[z3,z4,(-k*z1+kp*(z2-z1))*mi-z3*0.2,(-k*z2-kp*(z2-z1))*mi-z4*0.2]
    return dzdt


# Llamada a odeint que resuelve las ecuaciones de movimiento

nt=10000  #numero de intervalos de tiempo
dt=tf/nt

# Valores iniciales
z1_0=0.01  # Desplazamiento inicial masa 1
z2_0=-0.01  # Desplazamiento inicial masa 2
z3_0=0.0  # Velocidad inicial masa 1
z4_0=0.0  # Velocidad inicial masa 2


z0=[z1_0,z2_0,z3_0,z4_0] #Valores iniciales   


 
t=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6
z=odeint(double_pendulum2,z0,t,args=(par,),atol=abserr, rtol=relerr)


plt.close('all')


#######################
#Generate sine wave
Fs=10000/tf

# generate frequency axis
n=np.size(t)
fr=(Fs/2)*np.linspace(0,1,n//2)



#Compute FFT
x1=z[:,0]
X=fft(x1)
X_m=(2/n)*(abs(X[0:np.size(fr)]))

fig,ax2 = plt.subplots()
ax2.set_title('Magnitude Spectrum');
ax2.set_xlabel('Frequency(Hz)')
ax2.set_ylabel('Magnitude')
ax2.set_xlim(0.4,0.6)
line1, = ax2.plot(fr,X_m)





# Definicion del grafico
# Aqui se grafica la evolucion de los desplazamientos x1 y x2 con el tiempo

fig, ax1 = plt.subplots()
ax2 = ax1.twinx()
ax1.set_xlabel('time (s)')
ax1.set_ylabel('x1(m)', color='b', fontsize=15)
ax2.set_ylabel('x2(m)', color='r', fontsize=15)
ax1.tick_params('y', colors='b')
ax2.tick_params('y', colors='r')
#ax1.set_xlim(xmin=0.,xmax=60.0) #limites del eje x
line1, = ax1.plot(t[:],z[:,0], linewidth=2, color='b')
line2, = ax2.plot(t[:],z[:,1], linewidth=2, color='r')


#Definición gráfico energías
# Aqui se grafica la evolucion de la energía de m1 y m2 con el tiempo

#Energía del primer oscilador
E1 = 1/2*m*(z[:,2])**2+1/2*(k+kp)*(z[:,0])**2
#Energía del segundo oscilador
E2 = 1/2*m*(z[:,3])**2+1/2*(k+kp)*(z[:,1])**2
#Energía de acoplamiento
E3 = -kp*(z[:,0])*(z[:,1])
#Energía total
E = 1/2*m*(z[:,2])**2+1/2*m*(z[:,3])**2+1/2*k*(z[:,0])**2+1/2*k*(z[:,1])**2+1/2*kp*((z[:,1]-z[:,0]))**2

plt.figure()
plt.title('Energía en función del tiempo')
plt.plot(t[:], E, linewidth=2, color='tab:olive',label="Energía total")
plt.plot(t[:], E1, linewidth=2, color='tab:purple',label="Energía del primer oscilador")
plt.plot(t[:], E2, linewidth=2, color='tab:pink',label="Energía del segundo oscilador")
plt.plot(t[:], E3, linewidth=2, color='tab:red',label="Energía de acoplamiento")
plt.legend(loc='best')
plt.xlabel('tiempo (s)')
plt.ylabel('E(J)', fontsize=15)
plt.show()



# Aqui se hace una animacion del movimiento de los muelles acoplados

Long=3.0
Rbob=0.2

fig, ax3 = plt.subplots()
ax3 = plt.axes(xlim=(0.0,Long), ylim=(-0.4*Long,0.4*Long))
ax3.set_xlabel('x (m)')
ax3.set_ylabel('y (m)')

line1,=ax3.plot([],[],lw=12,color='b')
line2,=ax3.plot([],[],lw=12,color='r')
line3,=ax3.plot([],[],lw=12,color='b')


bob1 = plt.Circle((1, 1),Rbob, fc='g')
bob2 = plt.Circle((1, 1),Rbob, fc='g')




time_template = 'time = %.1fs'
time_text = ax3.text(0.05, 0.9, '', transform=ax3.transAxes)







def init():


    line1.set_data([],[])
    line2.set_data([],[]) 
    line3.set_data([],[]) 

    bob1.center = (1, 1)
    ax3.add_artist(bob1)
    bob2.center = (0,0)
    ax3.add_artist(bob2)



    time_text.set_text('')
    return line1,line2,line3,bob1,bob2,time_text









def animate(i):
    x1, y1 = bob1.center
    x1 =d1+z[i,0]
    y1 =0.0


    x2, y2 = bob2.center
    x2 =d2+z[i,1]
    y2 = 0.0

    line1.set_data((0,x1-Rbob),(0,0))

    line2.set_data((x1+Rbob,x2-Rbob),(0,0))

    line3.set_data((x2+Rbob,3),(0,0))
   


    bob1.center = (x1, y1)


  


 
    bob2.center = (x2, y2)

    time_text.set_text(time_template%(i*dt))

    return bob1,bob2,time_text

anim = animation.FuncAnimation(fig, animate, 
                               init_func=init, 
                               frames=10000,
                               interval=5)


plt.show()





