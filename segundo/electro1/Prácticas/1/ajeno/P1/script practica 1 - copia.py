
import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

# Podemos representar una magnitud escalar en 2 dimensiones con una matriz
# Asignando a cada elemento de la matriz una posici�n

# Primero definimos un tama�o del �rea que representar� la matriz LxL,
# con esquina inferior izquierda en el punto (L0,L0) y la resoluci�n h, en funci�n del n�muero de puntos N


#Funcion para representar la posici�n de la carga


# Tamaño y resolucion
L  = 4.0
L0 = -2.0
N = 21
h = L/(N-1) #resolucion
K = 9*10**9

#funcion posicion ejercicio 1

def pos(r):
    i = int((r[0]-L0)/h)
    j = int((r[1]-L0)/h)
    return i,j

q = 3

cargas = []
cargas.append((q,(-0.5,0)))    
cargas.append((-q,(0.5,0)))       

rho = np.zeros((N,N))

for carga in cargas:
    rho[pos(carga[1])] = carga[0]
    
rho = np.transpose(rho)  
  
#unos vectores x, y que contienen las posiciones
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Aunque ahora no lo necesitamos. Podemos definir una red para un campo vectorial.
# matrices de posiciones que nos ser�n �ltiles m�s tarde
# Esto nos dar� un poco m�s de juego y nos permitir� representar campos vectoriales.
X,Y = np.meshgrid(y,x)


# Generamos una matriz NxN
M=np.zeros((N,N))

'''
M[15,-2] = 2 #carga en (-0.5,0)
M[25,-2] = -2 #carga en (0.5,0)
dipolo hecho a mano

'''
#Calculo del potencial

def V(q,r0,x,y):
    #r0 posicion de la particula
    den = np.hypot(x-r0[0],y-r0[1]) #hipotenusa
    return q / den

Vv = np.zeros((N,N))

for carga in cargas:
    v0 = V(*carga,x=X,y=Y)
    Vv += v0

M = Vv
##HACER: modificar y representar la matriz M


# Definimmos un mapa de colores para representar la magnitud contenida en la matriz
colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

#primer subplot, representamos la matriz M

ax = fig.add_subplot(221)
# Configure the contour
plt.contourf(x, y , M, colorinterpolation, cmap=colourMap)
#TRANSPONER O DESTRANSPONER M SI ES NECESARIO
plt.title("M")

    
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')
plt.colorbar()


# otra forma de hacer lo mismo utilizando los meshgrids (atncion a transposicion)
a2 = fig.add_subplot(222)
plt.contourf(X, Y , M, colorinterpolation, cmap=colourMap)
#TRANSPONER O DESTRANSPONER M SI ES NECESARIO
plt.title("M")

    
a2.set_xlabel('$x$')
a2.set_ylabel('$y$')
a2.set_aspect('equal')
plt.colorbar()


#Terminamos dibujando todos los vectores de la matriz ("cortes en las direcciones horizontales y verticales)

a3=fig.add_subplot(223)
plt.plot(x,np.transpose(M))
a3.set_xlabel('$x$')

a4=fig.add_subplot(224)
plt.plot(y,M)
a4.set_xlabel('$y$')



##### Calculo de E


def E(q,r0,x,y):
    den = np.hypot(x-r0[0],y-r0[1])**3
    return q * (x-r0[0])/den/(4*np.pi), q*(y-r0[1])/den/(4*np.pi)

Ex, Ey = np.zeros((N,N)),np.zeros((N,N))

for charge in cargas:
    ex, ey = E(*charge,x=X,y=Y)
    Ex += ex
    Ey += ey

fig = plt.figure()
ax = fig.add_subplot(111)

color = 2 * np.log(np.hypot(Ex, Ey))
ax.streamplot(x, y, Ex, Ey, color=color, linewidth=1, cmap=plt.cm.inferno,
              density=2, arrowstyle='->', arrowsize=1.5)

charge_colors = {True: '#aa0000', False: '#0000aa'}
for q, pos in cargas:
    ax.add_artist(Circle(pos,0.05,color = charge_colors[q>0]))


ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')



#### Calculo de E por poisson

epsilon = 10**-8

error = 10

M = np.zeros((N,N))

while  error>epsilon:
    error = 0
    V_poisanterior = np.copy(M)
    for i in range(1,N-1):
        for j in range(1,N-1):
            M[i,j] = 0.25 * ( (M[i+1,j]) + (M[i-1,j]) + (M[i,j+1]) + (M[i,j-1]) + (rho[i,j])*h**2)
            error += M[i,j]-V_poisanterior[i,j]
    error = error/N**2

    

olorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

#primer subplot, representamos la matriz M

ax = fig.add_subplot(221)
# Configure the contour
plt.contourf(x, y , M, colorinterpolation, cmap=colourMap)
#TRANSPONER O DESTRANSPONER M SI ES NECESARIO
plt.title("M")

    
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')
plt.colorbar()


# otra forma de hacer lo mismo utilizando los meshgrids (atncion a transposicion)
a2 = fig.add_subplot(222)
plt.contourf(X, Y , M, colorinterpolation, cmap=colourMap)
#TRANSPONER O DESTRANSPONER M SI ES NECESARIO
plt.title("M")

    
a2.set_xlabel('$x$')
a2.set_ylabel('$y$')
a2.set_aspect('equal')
plt.colorbar()


#Terminamos dibujando todos los vectores de la matriz ("cortes en las direcciones horizontales y verticales)

a3=fig.add_subplot(223)
plt.plot(x,np.transpose(M))
a3.set_xlabel('$x$')

a4=fig.add_subplot(224)
plt.plot(y,M)
a4.set_xlabel('$y$')

plt.show()
