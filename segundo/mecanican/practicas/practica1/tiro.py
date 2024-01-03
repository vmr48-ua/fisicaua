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


# Datos a modificar en la simulacion 

m=1.0                         # Valor de las masa
g=9.8                        # gravedad
omega=np.pi/12.0/3600.0     # velocidad rotacion
radi= 6378000               # radio tierra

tf=125.0                 #tiempo de simulacion
velin =700.0              # velocidad de disparo
rz= 0.000023                  # rozamiento

lat= 40.0  #latitud
alz=15 # angulo de alzada del tiro
pla= 90.0  # angulo sobre el  plano 0º Sur eje X, 90º Este eje y, 180º Sur 270º Oeste

angcorrealz=0
angcorrepla=0

def funcion(angcorrepla):
    correalz=  angcorrealz*np.pi/180.0
    correpla=  angcorrepla*np.pi/180.0

    ralat= lat*np.pi/180.0
    raalz= alz*np.pi/180.0
    rapla= pla*np.pi/180.0

    omex= omega*np.cos(ralat)
    omez= omega*np.sin(ralat)
    ome2radx= omega**2*radi*np.cos(ralat)*np.sin(ralat)
    ome2radz= omega**2*radi*np.cos(ralat)**2

    par=[g,omex,omez,ome2radx,ome2radz,rz]

    def tiro(z,t,par):
        z1,z2,z3,z4,z5,z6=z  
        dzdt=[z4,z5,z6, 
            ome2radx +2*z5*omez -rz*z4*np.sqrt(z4**2+z5**2+z6**2),
            2*(z6*omex-z4*omez)-rz*z5*np.sqrt(z4**2+z5**2+z6**2) ,
            ome2radz -2*z5*omex -rz*z6*np.sqrt(z4**2+z5**2+z6**2)-g]
        return dzdt

    def tirog(az,at,par):
        az1,az2,az3,az4,az5,az6=az  
        dazdt=[az4,az5,az6, 0, 0, -g]
        return dazdt


    # Llamada a odeint que resuelve las ecuaciones de movimiento

    nt=25000  #numero de intervalos de tiempo
    dt=tf/nt

    # Valores iniciales tiro real
    z1_0= 0.0               # x punto disparo
    z2_0=0.0                # y punto disparo
    z3_0=0.0                # z punto disparo
    z4_0= velin*np.cos(raalz+correalz)*np.cos(rapla+correpla)  # Velocidad x 
    z5_0= velin*np.cos(raalz+correalz)*np.sin(rapla+correpla)  # Velocidad y
    z6_0= velin*np.sin(raalz+correalz)                         # velocidad z

    z0=[z1_0,z2_0,z3_0,z4_0,z5_0,z6_0] #Valores iniciales   tiro parabólico

    az1_0= 0.0                          # x punto disparo
    az2_0=0.0                           # y punto disparo
    az3_0=0.0                           # z punto disparo
    az4_0= velin*np.cos(raalz)*np.cos(rapla)     # Velocidad x 
    az5_0= velin*np.cos(raalz)*np.sin(rapla)     # Velocidad y
    az6_0= velin*np.sin(raalz)                   # velocidad z

    az0=[az1_0,az2_0,az3_0,az4_0,az5_0,az6_0]  #Valores inici
    
    t=np.linspace(0,tf,nt)
    at=np.linspace(0,tf,nt)
    abserr = 1.0e-8
    relerr = 1.0e-6

    z=odeint(tiro,z0,t,args=(par,),atol=abserr, rtol=relerr)
    az=odeint(tirog,az0,at,args=(par,),atol=abserr, rtol=relerr)

    plt.close('all')

    for i in range (0,nt): 
        if z[i-1,2]*z[i,2] <0 : nfinal=i
    for i in range (0,nt): 
        if az[i-1,2]*az[i,2] <0 : nafinal=i

    dist =np.sqrt(  (z[nfinal,0] -az[nafinal,0])**2  +(z[nfinal,1] -az[nafinal,1])**2  )


    return dist


angcorrealz=10.72

plt.figure()
angulos = np.arange(0,45,0.1)

y = []
for i in angulos:
    print(i)
    y.append(funcion(i))

plt.plot(angulos,y)
plt.show()