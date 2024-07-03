'''
PROGRAMA MUELLES ACOPLADOS
'''
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
'''
valores que hemos dado
kp = 10 (modos normales tmb)
kp = 30
kp = 200
kp = 4
kp = 0.5 (modos normales tmb)
kp = 0.1
vinicial para normales = 1.0
pos ini = 0.
'''


fig=plt.figure()
fig.set_dpi(100)
########################################################################
#                    DATOS A INTRODUCIR                                #       
########################################################################
m=1.0      # Valor de las masas
k=10      # Constante elástica de los muelles externos
kp = 5   # Constante elástica del muelle que une ambas masas
tf=400.0    #tiempo de simulacion
mi=1.0/m   # Inversa de la masa
g = 9.8
# Posiciones de equilibrio de las dos masa
d1=1.0
d2=2.0


par=[mi,k,kp]

########################################################################
#                   ECS. DE MOVIMIENTO                              #       
#######################################################################
def double_pendulum(z,t,par):
    z1,z2,z3,z4=z  
    dzdt=[z3,z4,(-k*z1+kp*(z2-z1))*mi,(-k*z2-kp*(z2-z1))*mi] #para agregar garvedad sumar a ambos términos
    return dzdt


# Llamada a odeint que resuelve las ecuaciones de movimiento

nt=10000  #numero de intervalos de tiempo
dt=tf/nt

# Valores iniciales
z1_0=0.  # Desplazamiento inicial masa 1
z2_0=0.0  # Desplazamiento inicila masa 2
z3_0=1.0  # Velocidad inicial masa 1
z4_0=1.0  # Velocidad inicial masa 2


z0=[z1_0,z2_0,z3_0,z4_0] #Valores iniciales   


 
t=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6
z=odeint(double_pendulum,z0,t,args=(par,),atol=abserr, rtol=relerr)


plt.close('all')

########################################################################
#                         DINÁMICA DEL SISTEMA                       #       
########################################################################
'''
MOVIMIENTO DE LAS MASAS
'''
fig, ax1 = plt.subplots()
ax2 = ax1.twinx()
ax1.set_xlabel('t(s)')
ax1.set_ylabel('x1(m)', color='b', fontsize=15)
ax2.set_ylabel('x2(m)', color='r', fontsize=15)
ax1.tick_params('y', colors='b')
ax2.tick_params('y', colors='r')
ax1.set_xlim(xmin=0.,xmax=60.0) #limites del eje x
line1, = ax1.plot(t[:],z[:,0], linewidth=2, color='b')
line2, = ax2.plot(t[:],z[:,1], linewidth=2, color='r')

'''
ANIMACIÓN DEL MOVIMIENTO
'''
Long=3.0
Rbob=0.15

fig, ax3 = plt.subplots()
ax3 = plt.axes(xlim=(0.0,Long), ylim=(-0.4*Long,0.4*Long))
ax3.set_xlabel('x (m)')
ax3.set_ylabel('y (m)')

bob1 = plt.Circle((1, 1),Rbob, fc='b') #MASAS
bob2 = plt.Circle((1, 1),Rbob, fc='r')

line1,=ax3.plot([],[],lw=12,color='k') #MUELLES
line2,=ax3.plot([],[],lw=12,color='orange')
line3,=ax3.plot([],[],lw=12,color='k')







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

'''
ENERGÍAS
'''
T_1 = 1/2*m*z[:,2]**2 #energia cinetica m1
T_2 = 1/2*m*z[:,3]**2 #energia cinetica m2
T = T_1 + T_2 #energia cinetica total
U_1 = 1/2*k*z[:,0]**2 #energia potencial m1
U_2 = 1/2*k*z[:,1]**2 #energia potencial m2
U_c = 1/2*kp*(z[:,1] - z[:,0])**2 #energia potencial del tercer muelle

U = U_1 + U_2 + U_c
E = T + U
E_1 = T_1 + U_1
E_2 = T_2 + U_2
plt.figure()
plt.plot(t, E_1, c='b', label='Energía $m_1$')
plt.plot(t, E_2, c='r', label='Energía $m_2$')
plt.plot(t, U_c, c='orange', label='Energía potencial entre masas')
plt.plot(t, E, c='k', label='Energía total')
plt.legend(loc='best')
plt.xlabel('tiempo (s)', fontsize=12)
plt.ylabel('Energía (J)', fontsize=12)
plt.xlim(0,50)
#plt.title('Energías en función del tiempo')
plt.show()

plt.figure()
plt.plot(t[:],z[:,2])
plt.plot(t[:],z[:,3])

