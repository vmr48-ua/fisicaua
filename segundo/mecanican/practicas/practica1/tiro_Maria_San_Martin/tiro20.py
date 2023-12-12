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


#fig=plt.figure()
#fig.set_dpi(100)
#fig.set_size_inches(7,6.5)


# Datos a modificar en la simulacion 


g=9.8      # gravedad
omega=np.pi/12.0/3600.0     # velocidad rotacion
radi= 6378000  # radio tierra

tf=80.0    #tiempo de simulacion
velin =700.0 # velocidad de disparo



lat=-40.0  #latitud
alz=30.0 # angulo de alzada del tiro
#  pla=   # angulo plano 0º Sur eje X, 90º Este eje y, 180º Sur 270º Oeste

ralat= lat*np.pi/180.0
raalz= alz*np.pi/180.0
#rapla= pla*np.pi/180.0

omex= omega*np.cos(ralat)
omez= omega*np.sin(ralat)
ome2radx= omega**2*radi*np.cos(ralat)*np.sin(ralat)
ome2radz= omega**2*omega*radi*np.cos(ralat)**2


#par=[mi,g,omega]
par=[g,omex,omez,ome2radx,ome2radz]


# Definiendo tiro solo gravedad

def tiro(z,t,par):
    z1,z2,z3,z4,z5,z6=z  
    dzdt=[z4,z5,z6, 
          ome2radx +2*z5*omez,
          2*(z6*omex-z4*omez),
          ome2radz -2*z5*omex -g]
    return dzdt


def tirog(az,at,par):
    az1,az2,az3,az4,az5,az6=az  
    dazdt=[az4,az5,az6, 0, 0, -g]
    return dazdt


# Llamada a odeint que resuelve las ecuaciones de movimiento



nt=20000  #numero de intervalos de tiempo
dt=tf/nt

rapla=np.linspace(0.0,np.pi,360)
dist=np.linspace(0.0,np.pi,360)

for j in range (0,360):
# Valores iniciales
  rapla[j] = 2*j*np.pi/360
  z1_0= 0.0  # x punto disparo
  z2_0=0.0  # y punto disparo
  z3_0=0.0  # z punto disparo
  z4_0= velin*np.cos(raalz)*np.cos(rapla[j])  # Velocidad x 
  z5_0= velin*np.cos(raalz)*np.sin(rapla[j])  # Velocidad y
  z6_0= velin*np.sin(raalz)   # velocidad z
#  
  z0=[z1_0,z2_0,z3_0,z4_0,z5_0,z6_0] #Valores iniciales   
#
  az1_0= 0.0  # x punto disparo
  az2_0=0.0  # y punto disparo
  az3_0=0.0  # z punto disparo
  az4_0= velin*np.cos(raalz)*np.cos(rapla[j])  # Velocidad x 
  az5_0= velin*np.cos(raalz)*np.sin(rapla[j])  # Velocidad y
  az6_0= velin*np.sin(raalz)   # velocidad z
#
  az0=[az1_0,az2_0,az3_0,az4_0,az5_0,az6_0] #Valores inici
# 
  t=np.linspace(0,tf,nt)
  at=np.linspace(0,tf,nt)
  abserr = 1.0e-8
  relerr = 1.0e-6
#
  z=odeint(tiro,z0,t,args=(par,),atol=abserr, rtol=relerr)
#
  az=odeint(tirog,az0,at,args=(par,),atol=abserr, rtol=relerr)
#
  for i in range (0,nt): 
     if z[i-1,2]*z[i,2] <0 : nfinal=i
  for k in range (0,nt): 
     if az[k-1,2]*az[k,2] <0 : nafinal=k
  #print (j, rapla[j])
  dxy =np.sqrt((z[nfinal,0]-az[nafinal,0])**2+(z[nfinal,1] -az[nafinal,1])**2 )
  dist[j] = dxy
  #print ('Error en el tiro =', dxy)


 


# Definicion del grafico
# Aqui se grafica la evolucion de los desplazamientos x1 y x2 con el tiempo

fig, ax1 = plt.subplots()
#ax2 = ax1.twinx()
ax1.set_xlabel('Angulo')
ax1.set_ylabel('error', color='b', fontsize=15)
ax1.tick_params('y', colors='b')
line1, = ax1.plot(rapla[:], dist[:], linewidth=2, color='b')

