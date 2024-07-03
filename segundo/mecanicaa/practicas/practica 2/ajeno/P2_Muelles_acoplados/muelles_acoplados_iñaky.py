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
k=15      # Constante elástica de los muelles externos
kp=10    # Constante elástica del muelle que une ambas masas
tf=400.0    #tiempo de simulacion
mi=1.0/m   # Inversa de la masa

# Posiciones de equilibrio de las dos masa
d1=1.0
d2=2.0


par=[mi,k,kp]

# Valores iniciales
z1_0=0.1  # Desplazamiento inicial masa 1
z2_0=-0.2  # Desplazamiento inicila masa 2
z3_0=0 #Velocidad inicial masa 1
z4_0=0  # Velocidad inicial masa 2
#c = 0.1 #Parámetro viscosidad


# Definicion de las ecuaciones de movimiento de los muelles acoplados
def double_pendulum(z,t,par):
    z1,z2,z3,z4=z  
    dzdt=[z3,z4,(-k*z1+kp*(z2-z1))*mi,(-k*z2-kp*(z2-z1))*mi]
    return dzdt

# Definición de la energía total del sistema.
def energia(m,z,par):
    z1,z2,z3,z4 = z
    Ec = 0.5*m*z3**2+0.5*m*z4**2
    Ep = 0.5*k*(z1**2+z2**2)+0.5*kp*(z1-z2)**2
    Em = [Ec,Ep]
    return Em

# Llamada a odeint que resuelve las ecuaciones de movimiento

nt=10000  #numero de intervalos de tiempo
dt=tf/nt



z0=[z1_0,z2_0,z3_0,z4_0] #Valores iniciales   



t=np.linspace(0,tf,nt)
t0 = t[0:300]
abserr = 1.0e-8
relerr = 1.0e-6
z=odeint(double_pendulum,z0,t,args=(par,),atol=abserr, rtol=relerr)


Ec = []
Ep = []
Em = []
for i in range(len(t0)):
    Ec.append(energia(m,z[i],par)[0])
    Ep.append(energia(m,z[i],par)[1])
    Em.append(energia(m,z[i],par)[0] + energia(m,z[i],par)[1])




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


# Calcular el espectro de frecuencia
X_m = (2/n) * abs(X[0:np.size(fr)])
valor = max(X_m)
indice = np.where(X_m == valor)[0]

print(indice)
print('El pico de frecuencia encontrado es :{}'.format(fr[indice]))
# Crear el gráfico del espectro de magnitud
fig, ax2 = plt.subplots()
ax2.set_title('Magnitude Spectrum')
ax2.set_xlabel('Frequency (Hz)')
ax2.set_ylabel('Magnitude')
ax2.set_xlim(0, Fs/2)
line1, = ax2.plot(fr, X_m)

plt.show()


#Gráfica de la energía en función del tiempo
plt.figure()
plt.title("Energía en función del tiempo", fontsize = 15)
plt.plot(t0, Em, label='Energía mecánica')
plt.plot(t0, Ec, label = 'Energía cinética')
plt.plot(t0, Ep, label= 'Energía potencial')
plt.xlabel('$t$ $(s)$', fontsize = 15)
plt.ylabel("$Em$ $(J)$", fontsize = 15)
plt.legend(fontsize = 15,shadow = True)
plt.show()



# Definicion del grafico
# Aqui se grafica la evolucion de los desplazamientos x1 y x2 con el tiempo
titulo = 'k = {}; k" = {}'.format(k,kp)
fig, ax1 = plt.subplots()
ax2 = ax1.twinx()
plt.title(titulo, fontsize = 15)
ax1.set_xlabel('Tiempo (s)')
ax1.set_ylabel('x1(m)', color='b', fontsize=15)
ax2.set_ylabel('x2(m)', color='r', fontsize=15)
ax1.tick_params('y', colors='b')
ax2.tick_params('y', colors='r')
ax1.set_xlim(xmin=0.,xmax=60.0) #limites del eje x
line1, = ax1.plot(t[:],z[:,0], linewidth=2, color='b')
line2, = ax2.plot(t[:],z[:,1], linewidth=2, color='r')





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





