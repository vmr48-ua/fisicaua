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

m=750                          # Valor de las masa
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
z1_0= 300000.0                  # x punto  inicial
z2_0=0.0                         # y punto  inicial
z3_0= 0                          # Velocidad x 
z4_0= np.sqrt(gr*mati/z1_0)*1.1      # Velocidad y

z0=[z1_0,z2_0,z3_0,z4_0] #Valores iniciales   
 
t=np.linspace(0,tf,nt)
at=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6

z=odeint(tiro,z0,t,args=(par,),atol=abserr, rtol=relerr)


 
lo= m*z4_0*z1_0
E0=0.5*m*z4_0**2-gr*mati*m/z1_0
#E0=0.5*m*z4_0**2 +0.5*ke*z1_0**2

print (E0,lo,z4_0)

rix= np.linspace(z1_0*0.9,1.5*z1_0,100)

Pote= 0.5*lo**2/m**2/rix**2 -gr*mati/rix
ene1=np.linspace(E0,E0,100)



### Gráficas


plt.close('all')

plt.figure(figsize=(4,4))
plt.xlabel('x')
plt.ylabel('y')
plt.plot(z[:,0],z[:,1],linewidth=0.3 )
plt.plot(radix,radiy, linewidth=0.3)  # dibuja el contorno de la Tierra

a=0.5
xlim1=-radi*a
xlim2=radi*a
ylim1=-radi*a
ylim2=radi*a




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



# ms =750
# gr = 6.67*(10**(-11))                        #Constante de gravedad universal
mt = 5.972*(10**(24)) 
# mr = 750*mt/(1+mt)                       #Masa de la Tierra 
Rt = 6371000
# alpha=0


f = 1.5
v0 = np.sqrt((gr*mt)/(Rt+300000)*f) #v0 para orbita eliptica
tf = 15500.0 #tiempo de simulacion
def radio(y,t,par):
    r1,r2,v1,v2 = y
    dydt =[v1,v2,-(8*Rt**2*l**2*r1)/(ms*m*np.sqrt(r1**2+r2**2)**6/2),-(8*Rt**2*l**2*r2)/(ms*m*np.sqrt(r1**2+r2**2)**6/2)]
    return dydt
def potencial(m,r,gr,mt,ms,l):
    return l**2/(2*m**2*r**2) - (8*Rt**2*l**2)/(m*ms*r**5)


par = [l,m,ms,mt,gr]
y0 = [0,(Rt+300000),v0,0]
nt = 250000
dt = tf/nt

t=np.linspace(0,tf,nt)
#abserr = 1.0e-8
#relerr = 1.0e-6
#z=odeint(radio,y0,t,args=(par,),atol=abserr, rtol=relerr)
z1=odeint(radio,y0,t,args=(par,))

#gráfica orbita
plt.figure()
plt.ylabel('y')
plt.xlabel('x')
plt.plot( z1[:17000,0],z1[:17000,1] , color='c')
plt.plot(z1[16999,0],z1[16999,1], marker="o", color="red")
#plt.savefig("trayectoriaanguloaumento.jpg", bbox_inches='tight')
E_m = np.zeros(100,float)
E = 0.5*v0**2- (8*Rt**2*l**2)/(ms*(R+Rt)**5)


for j in range(0,100):
    E_m[j]=E
    
x = np.linspace(0.5*Rt,(Rt + 300000)*6,100)
#plot(x,potencial(m,x,gr,mt,ms,l))
plt.figure()
plt.title('Potencial efectivo')
plt.ylabel('E')
plt.xlabel('r')
plt.legend(loc='best')
plt.plot( x,potencial(m,x,gr,mt,ms,l), color='g')
#plt.savefig("potencial.jpg", bbox_inches='tight')
plt.show()