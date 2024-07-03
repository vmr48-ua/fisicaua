"""
Aroa Anton 

Práctica 1: solución de la ecuación de Schrödinger
para el átomo de hidógeno
"""

import numpy as np
import matplotlib.pyplot as plt
import cmath as cm

'''
 PARTE 1: REPRESENTACION SOLUCION POLAR
'''
m = 1

theta=np.arange(0,2*np.pi,0.01)

Phi = np. arange (0, 2*np.pi , 0.01)

f_th = np.exp (1j*m*Phi)

ri = f_th.imag

ra = abs(f_th)

rr = f_th.real

#Gráfica parte imaginaria
#Hacemos la representación en coordenadas Polares
plt.figure()
ax = plt.subplot(221,projection='polar')
#Escalamos la gama de colores
scaled_r = (ri - ri.min()) / ri.ptp()
colors = plt.cm.coolwarm(scaled_r)
#Representamos
plt.scatter(theta,np.abs(ri), c=colors)
ax.grid(True)
ax.set_title("Soluciones del Eje Polar", va='bottom')

#Gráfica parte real
#Hacemos la representación en coordenadas Polares
ax = plt.subplot(222,projection='polar')
#Escalamos la gama de colores
scaled_r = (rr - rr.min()) / rr.ptp()
colors = plt.cm.coolwarm(scaled_r)
#Representamos
plt.scatter(theta,np.abs(rr), c=colors)
ax.grid(True)
ax.set_title("Soluciones del Eje Polar", va='bottom')

#Gráfica módulo
#Hacemos la representación en coordenadas Polares
ax = plt.subplot(223,projection='polar')
#Escalamos la gama de colores
scaled_r = (ra - ra.min()) / ra.ptp()
colors = plt.cm.coolwarm(scaled_r)
#Representamos
plt.scatter(theta,np.abs(ra), c=colors)
ax.grid(True)
ax.set_title("Soluciones del Eje Polar", va='bottom')
plt.show()

'''
PARTE 2: REPRESENTACION SOLUCION ACIMUTAL
'''

l=2
#funcion para calcular derivadas de orden n
def recursive_deriv (f,k):
    def compute (x,dx =0.3):
        return 0.5*(f(x+dx) - f(x-dx))/dx
    if (k==0):
        return f
    if (k==1):
        return compute
    else:
        return recursive_deriv (compute ,k-1)

#polinomios de legendre
def Pl(x,l): 
    def f(x):
        return (x**2-1)**l
    df =  recursive_deriv(f,l)
    return 1.0/(2**l*np.math.factorial(l))*df(x)

def Plm(x,l,m):
    def f(x):
        return Pl(x,l)
    df = recursive_deriv(f, np.abs(m))
    return (1-x**2)**( np.abs(m)/2)*df(x)

theta = np.arange(0, np.pi , 0.0001)
#Solucion generica en complejos
f_ph = Plm(np.cos(theta), l, m)

#Grafica parte real
r = f_ph.real
plt.figure()
#Hacemos la representacion en coordenadas Polares
ax = plt.subplot(111,projection='polar')
#Escalamos la gama de colores
scaled_r = (r - r.min()) / r.ptp()
colors = plt.cm.coolwarm(scaled_r)
#Representamos
plt.scatter(theta,np.abs(r), c=colors)
ax.grid(True)
ax.set_title("Soluciones del Eje azimutal parte real", va='bottom')
plt.show()

'''
PARTE 3: ARMONICOS ESFERICOS 3D
'''
#DEFINIMOS LOS ARMÓNICOS

def normalizacion(l,m):
    a=(-1)**m
    s=(2*l+1)*np.math.factorial(l-np.abs(m))
    t=4*np.pi*np.math.factorial(l+np.abs(m))
    b=np.sqrt(s/t)
    return a*b

def armonicos (th,ph,l,m):
    norma=normalizacion(l,m)
    f_th=np.exp(1j*m*th)
    f_ph=Plm(np.cos(ph),l,m)
    return norma*f_th*f_ph

#REPRESENTACIoN

import matplotlib.colors as mcolors
#Ahora Vamos a Calcular para todos los  ngulos el valor de la funci n de onda angular.
theta, phi = np.linspace(0,np.pi, 200), np.linspace(0, 2*np.pi, 40)
THETA, PHI = np.meshgrid(theta, phi)
 #R = (np.absolute(Y_l_m(THETA,PHI,l,m)))**2
R = (np.real(armonicos(PHI,THETA,l,m)))**2#+(np.imag(armonicos(PHI,THETA,l,m)))**2
#Pasamos a coordenadas Cartesinas
X = R*np.sin(THETA) * np.cos(PHI)
Y = R*np.sin(THETA) * np.sin(PHI)
Z = R * np.cos(THETA)
#Representamos. El m dulo de la Funci n de onda aparece c mo R y un color
fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
cmap = plt.get_cmap('coolwarm')
norm = mcolors.Normalize(vmin=R.min(), vmax=R.max())
plot = ax.plot_surface(
X, Y, Z, rstride=1, cstride=1, facecolors=cmap(norm(R)),linewidth=0, antialiased=False, alpha=.4)
plt.show()

'''
PARTE 4: SOLUCION RADIAL
'''
n=1
#polinomio de Laguerre
def Lq(x, q):
    def f(x):
        return np.exp(-x)*x**q
    df = recursive_deriv (f,q)
    return np.exp(x)*df(x)

#polinomio asociado de Laguerre
def Lqp(x, p, q_p):
    def f(x):
        return Lq(x, p+q_p)
    df = recursive_deriv (f,p)
    return (( -1)**p)*df(x)


def R(n,l,m,r):
    def f(x):
        return Lqp(x, 2*l+1, n-l-1)
    rho = (2*r)/(n*0.529)
    a = np.math.factorial(n-l-1)
    b = 2*n*(( np.math.factorial(n+l))**3)
    c = (2/n)**3
    norm = np.sqrt(c*a/b)
    return norm*np.exp(-rho/2)*((rho)**l)*f(rho)

r = np. arange (0, 8*n**1.5 , 0.001)
f_r = R(n,l,m,r)
plt.figure()
#Hacemos la representación en coordenadas cartesianas
ax = plt.subplot(111)
#Escalamos la gama de colores
scaled_fr = (f_r - f_r.min()) / f_r.ptp()
colors = plt.cm.coolwarm(scaled_fr)

#Representamos
plt.scatter(r,f_r, c=colors)
ax.grid(True)
ax.set_title("Soluciones del Eje radial", va='bottom')
plt.show()

'''
PARTE 5: FUNCIÓN DE ONDA DEL HIDRÓGENO 
(PARTE ANGULAR Y RADIAL)
'''
r = np.arange(0, 10*n, 0.1)
th= np.arange(0, 2.1*np.pi, 0.1)
Rl, TH = np.meshgrid(r, th)
VAL=(np.abs(R(n,l,m,Rl)*armonicos(0,TH,l,m)))
X = Rl*np.cos(TH)
Y = Rl*np.sin(TH) 
# Set colour interpolation and colour map
colorinterpolation = 100
colourMap = plt.cm.inferno
fig = plt.figure()
ax = fig.add_subplot(111)
# Configure the contour
plt.contourf(X, Y, VAL, colorinterpolation, cmap=colourMap)
plt.title("Átomo de Hidrógeno")
plt.show()






