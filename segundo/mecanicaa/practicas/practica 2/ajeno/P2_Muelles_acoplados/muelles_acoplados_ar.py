import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import odeint
import matplotlib.animation as animation
from matplotlib.pylab import *


fig=plt.figure()
fig.set_dpi(100)


########################################################################
#                    DATOS A INTRODUCIR                                #       
########################################################################

m=2.5      # Valor de las masas
k=10       # Constante elástica de los muelles externos
kp=0.5  # Constante elástica del muelle que une ambas masas
tf=400.0   # Tiempo de simulacion
mi=1.0/m   # Inversa de la masa

# Posiciones de equilibrio de las dos masas
d1=1.0
d2=2.0

# m=0.250      # Valor de las masas
# k=1000.0      # Constante elástica de los muelles externos
# kp=0.5     # Constante elástica del muelle que une ambas masas
# tf=400.0    #tiempo de simulacion
# mi=1.0/m   # Inversa de la masa

# # Posiciones de equilibrio de las dos masa
d1=1.0
d2=2.0

par=[mi,k,kp]



########################################################################
#         ECUACIONES DE MOVIMIENTO DE LOS MUELLES ACOPLADOS            #       
########################################################################

def muelles_acoplados(z,t,par):
    z1,z2,z3,z4=z  
    dzdt=[z3,z4,(-k*z1+kp*(z2-z1))*mi,(-k*z2-kp*(z2-z1))*mi]
    return dzdt


# Llamada a odeint que resuelve las ecuaciones de movimiento
nt=10000  #numero de intervalos de tiempo
dt=tf/nt

# Valores iniciales
z1_0=0.1  # Desplazamiento inicial masa 1
z2_0=-0.1  # Desplazamiento inicial masa 2
z3_0=0.0  # Velocidad inicial masa 1
z4_0=0.0  # Velocidad inicial masa 2


z0=[z1_0,z2_0,z3_0,z4_0] #Valores iniciales   


t=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6
z=odeint(muelles_acoplados,z0,t,args=(par,),atol=abserr, rtol=relerr)


plt.close('all')


#######################
#Generate sine wave
Fs=10000/tf

# generate frequency axis
n=np.size(t)
fr=(Fs/2)*np.linspace(0,1,n//2)

#Compute FFT
x1=z[:,0]
X= fft(x1)
X_m=(2/n)*(abs(X[0:np.size(fr)]))

fig,ax2 = plt.subplots()
ax2.set_title('Transformada de Fourier (FFT)');
ax2.set_xlabel('Frecuencia (Hz)')
ax2.set_ylabel('Magnitud')
ax2.set_xlim(0.4,0.6)
line1, = ax2.plot(fr,X_m)
a=np.where(X_m==max(X_m))
print(fr[a])
auxiliar=np.delete(X_m,a)
b=np.where(auxiliar==max(auxiliar))
print(fr[b])
a1=int(np.where(fr==0.03250650130026005)[0])
a2=int(np.where(fr==9.71176478e-02)[0])


print(X_m)
np.set_printoptions(threshold=np.inf)
print(X_m[0:1000])
print(len(X_m))
auxiliar=X_m[a1:a2]
print(auxiliar)
b=np.where(X_m==max(auxiliar))
print(fr[b])
print(X_m[a1:a2])


########################################################################
#     EVOLUCIÓN DE LOS DESPLAZAMIENTOS EN FUNCIÓN DEL TIEMPO           #
########################################################################


fig, ax1 = plt.subplots()
ax2 = ax1.twinx()
ax1.set_xlabel('tiempo (s)')
ax1.set_ylabel('$x_1(m)$', color='b', fontsize=15)
ax2.set_ylabel('$x_2(m)$', color='r', fontsize=15)
ax1.tick_params('y', colors='b')
ax2.tick_params('y', colors='r')
ax1.set_xlim(xmin=0.,xmax=200.0) #limites del eje x
plt.title('Desplazamientos en función del tiempo')
line1, = ax1.plot(t[:],z[:,0], linewidth=2, color='b')
line2, = ax2.plot(t[:],z[:,1], linewidth=2, color='r')
plt.show()

########################################################################
#              ENERGÍA DE CADA MASA Y DEL SISTEMA                      #
########################################################################

T_1 = 1/2*m*z[:,2]**2 
T_2 = 1/2*m*z[:,3]**2 
T = T_1 + T_2
U_1 = 1/2*k*z[:,0]**2
U_2 = 1/2*k*z[:,1]**2
U_c = 1/2*kp*(z[:,1] - z[:,0])**2
U = U_1 + U_2 + U_c
E = T + U
E_1 = T_1 + U_1
E_2 = T_2 + U_2

plt.plot(t, E_1, c='b', label='Energía $m_1$')
plt.plot(t, E_2, c='r', label='Energía $m_2$')
plt.plot(t, U_c, c='orange', label='Energía potencial entre masas')
plt.plot(t, E, c='green', label='Energía total')
plt.legend(loc='best')
plt.xlabel('tiempo (s)', fontsize=12)
plt.ylabel('Energía (J)', fontsize=12)
plt.xlim(0,50)
plt.title('Energías en función del tiempo')
plt.show()
e1=0.5*m*z[:,2]**2+0.5*k*z[:,0]**2+0.25*kp*(z[:,1]-z[:,0])**2
e2=0.5*m*z[:,3]**2+0.5*k*z[:,1]**2+0.25*kp*(z[:,1]-z[:,0])**2
uc = 1/2*kp*(z[:,1] - z[:,0])**2
etot=e1+e2

fig, ax4 = plt.subplots()
ax24=ax4.twinx()
ax4.set_xlabel('time (s)')
ax4.set_ylabel('E1 (J)',color='b',fontsize=15)
ax4.set_ylabel('E2 (J)',color='r',fontsize=15)
ax4.tick_params('y',colors='b')
ax4.tick_params('y',colors='r')
ax4.set_xlim(xmin=0.,xmax=60.0)
line1, =ax4.plot(t[:],e1[:],linewidth=2, color='b')
line2, =ax24.plot(t[:],e2[:],linewidth=2, color='r')
line2, =ax24.plot(t[:],etot[:],linewidth=2, color='g')

########################################################################
#                          ANIMACION                                   #
########################################################################

Long=3.0
Rbob=0.2

fig, ax3 = plt.subplots()
ax3 = plt.axes(xlim=(0.0,Long), ylim=(-0.4*Long,0.4*Long))
ax3.set_xlabel('x (m)')
ax3.set_ylabel('y (m)')
plt.title('Animación del sistema de muelles acoplados')
line1,=ax3.plot([],[],lw=6,color='b')
line2,=ax3.plot([],[],lw=6,color='r')
line3,=ax3.plot([],[],lw=6,color='b')
bob1 = plt.Circle((1, 1),Rbob, fc='black')
bob2 = plt.Circle((1, 1),Rbob, fc='black')

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





