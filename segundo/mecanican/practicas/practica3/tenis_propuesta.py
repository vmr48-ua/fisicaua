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
from matplotlib import cm
from mpl_toolkits.axes_grid1 import host_subplot
from mpl_toolkits.mplot3d import Axes3D


fig=plt.figure()
fig.set_dpi(100)

tf=25.0
nt=500  #numero de intervalos de tiempo


#  Momentos de inercia

I1=4.0
I2=2.0
I3=1.0

# Valores iniciales
z1_0= 0.001                                        # WX0
z2_0= 2.001                                         # wy0 
z3_0= 0.001                                            # Wz0

par=[I1,I2,I3]
print('Momentos de inercia ',par)

lo2= (I1*z1_0)**2 + (I2*z2_0)**2 + (I3*z3_0)**2
eoci= 0.5*(  I1*z1_0**2 + I2*z2_0**2 + I3*z3_0**2  )


# Definiendo funcion

def rotacion(z,t,par):
    z1,z2,z3=z
    dzdt=[(I2-I3)*z2*z3/I1 ,(I3-I1)*z1*z3/I2, (I1-I2)*z1*z2/I3]
    return dzdt


# Llamada a odeint que resuelve las ecuaciones de movimiento

z0=[z1_0,z2_0,z3_0] #Valores iniciales   

print('Estado inicial',z0)

t=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6

z=odeint(rotacion,z0,t,args=(par,),atol=abserr, rtol=relerr)

plt.close('all')

plt.figure()
plt.xlabel('t')
plt.ylabel('$\omega_x$')
plt.plot(t[:],z[:,0] )

plt.figure()
plt.xlabel('t')
plt.ylabel('$\omega_y$')
plt.plot(t[:],z[:,1] )

plt.figure()
plt.xlabel('t')
plt.ylabel('$\omega_z$')
plt.plot(t[:],z[:,2] )

plt.figure()
plt.xlabel('t')
plt.ylabel(' $\omega$')
plt.plot(t[:],z[:,1] )
plt.plot(t[:],z[:,0] )
plt.plot(t[:],z[:,2] )

max1_value = None
max1_idx = None
min1_value = None
min1_idx = None

for idx, num in enumerate(z[:,2]):
    if (max1_value is None or num > max1_value):
        max1_value = num
        max1_idx = idx
    if (min1_value is None or num < min1_value):
        min1_value = num
        min1_idx = idx

print('Maximum valor:', max1_value, "indice: ", max1_idx)
print('Minimum valor:', min1_value, "indice: ", min1_idx)
print('T=' , 2*(  t[max1_idx] -t[min1_idx] )    )


# Calculo momento angular y energía cinética

ex=z[:,0]
ey=z[:,1]
ez=z[:,2]

lx=I1*ex
ly=I2*ey
lz=I3*ez

l22= np.sqrt(lx**2+ly**2+lz**2)

plt.figure()
# plt.ylim((0,5))
plt.xlabel('t')
plt.ylabel('|L|')
plt.plot(t[:],l22[:] )
plt.grid()
plt.title('Momento Angular')

enecine=0.5*(I1*z[:,0]**2+I2*z[:,1]**2+ I3*z[:,2]**2)

plt.figure()
# plt.ylim((0,5))
plt.xlabel('t')
plt.ylabel('E')
plt.plot(t[:],enecine[:],c='r')
plt.grid()
plt.title('Energia Cinética')


############## Recorrido de la velocidad angular y el momento angular

wxx=np.linspace(0,z1_0,10)
wxy=np.linspace(0,z2_0,10)
wxz=np.linspace(0,z3_0,10)

fig= plt.figure()
plt.title('Velocidad Angular')
ax= fig.add_subplot(projection='3d')
ax.view_init(10,-40)
ax.plot3D(ex,ey,ez)
ax.plot3D(wxx,wxy,wxz)

loxx=np.linspace(0,I1*z1_0,10)
loxy=np.linspace(0,I2*z2_0,10)
loxz=np.linspace(0,I3*z3_0,10)

fig= plt.figure()
plt.title('Momento Angular')
ax= fig.add_subplot(projection='3d')
ax.view_init(10,-40)
ax.plot3D(lx,ly,lz)
ax.plot3D(loxx,loxy,loxz)

plt.show()