###############################################################################
#               PRÁCTICA 1 DE ELECTROMAGNETISMO 1                             #    
###############################################################################
import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

# Podemos representar una magnitud escalar en 2 dimensiones con una matriz
# Asignando a cada elemento de la matriz una posición

# Primero definimos un tamaño del área que representará la matriz LxL,
# con esquina inferior izquierda en el punto (L0,L0) y la resolución h, en función del númuero de puntos N


# Tamaño y resolucion
L  = 4.0
L0 = -2.0
N = 28
h = L/(N-1) #resolucion


#unos vectores x, y que contienen las posiciones
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Aunque ahora no lo necesitamos. Podemos definir una red para un campo vectorial.
# matrices de posiciones que nos serán últiles más tarde
# Esto nos dará un poco más de juego y nos permitirá representar campos vectoriales.
X,Y = np.meshgrid(y,x)


# Generamos una matriz NxN
M=np.zeros((N,N))
M=X+Y

##HACER: modificar y representar la matriz M
'''

# Definimmos un mapa de colores para representar la magnitud contenida en la matriz
colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

#primer subplot, representamos la matriz M

ax = fig.add_subplot(221)
# Configure the contour
plt.contourf(x, y , np.transpose(M), colorinterpolation, cmap=colourMap)
plt.title("M")

    
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')
plt.colorbar()


# otra forma de hacer lo mismo utilizando los meshgrids (atncion a transposicion)
a2 = fig.add_subplot(222)
plt.contourf(X, Y , np.transpose(M), colorinterpolation, cmap=colourMap)
plt.title("M")

    
a2.set_xlabel('$x$')
a2.set_ylabel('$y$')
a2.set_aspect('equal')
plt.colorbar()

#Terminamos dibujando todos los vectores de la matriz ("cortes en las direcciones horizontales y verticales)

a3=fig.add_subplot(223)
plt.plot(y,np.transpose(M))
a3.set_xlabel('$y$')

a4=fig.add_subplot(224)
plt.plot(x,M)
a4.set_xlabel('$x$')
'''

'''
DENSIDAD DE CARGA
'''

def Pos(r):
    i = int((r[0]-L0)/h)
    j = int((r[1]-L0)/h)
    return i,j

q = 3.
cargas = []
cargas.append((q,(-0.5,0)))
cargas.append((-q,(0.5,0)))

rho = np.zeros((N,N))
for carga in cargas:
    rho[Pos(carga[1])] = carga[0]

colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

ax = fig.add_subplot(221)
plt.contourf(x, y , np.transpose(rho), colorinterpolation, cmap=colourMap)
plt.title("Densidad")
    
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')
plt.colorbar()

a2 = fig.add_subplot(222)
plt.contourf(X, Y , np.transpose(rho), colorinterpolation, cmap=colourMap)
plt.title("Densidad")

a3=fig.add_subplot(223)
plt.plot(y,np.transpose(rho))
a3.set_xlabel('$y$')

a4=fig.add_subplot(224)
plt.plot(x,rho)
a4.set_xlabel('$x$')
   
'''
POTENCIAL DEBIDO A UN DIPOLO POR DEFINICIÓN
'''
def V(q,r0,x,y):
    den = np.hypot(x-r0[0],y-r0[1])
    return q/den

Vv = np.zeros((N,N))
for carga in cargas:
    v0 = V(*carga,x=X,y=Y)
    Vv+= v0
            
colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

ax = fig.add_subplot(221)
plt.contourf(x, y , Vv, colorinterpolation, cmap=colourMap)
plt.title("Potencial")
    
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')
plt.colorbar()

a2 = fig.add_subplot(222)
plt.contourf(X, Y , Vv, colorinterpolation, cmap=colourMap)
plt.title("Potencial")
    
a2.set_xlabel('$x$')
a2.set_ylabel('$y$')
a2.set_aspect('equal')
plt.colorbar()

a3=fig.add_subplot(223)
plt.plot(y,np.transpose(Vv))
plt.ylim(-40,40)
a3.set_xlabel('$y$')

a4=fig.add_subplot(224)
plt.plot(x,Vv)
plt.ylim(-40,40)
a4.set_xlabel('$x$')

'''
CAMPO ELÉCTRICO DEBIDO A UN DIPOLO POR DEFINICIÓN
'''
def E(q,r0,x,y):
  den = np.hypot(x-r0[0],y-r0[1])**3
  return q*(x-r0[0])/den/(4*np.pi), q*(y-r0[1])/den/(4*np.pi)

Ex, Ey, Etotal = np.zeros((N,N)), np.zeros((N,N)), np.zeros((N,N))
for carga in cargas:
  ex, ey = E(*carga, x=X, y=Y)
  Ex+=ex
  Ey+=ey
  
for i in range(1,N):
    for j in range(1,N):
        Etotal[i,j] = (Ex[i,j]**2+Ey[i,j]**2)**0.5

fig = plt.figure()
ax = fig.add_subplot(111)
color = 2*np.log(np.hypot(Ex,Ey))
ax.streamplot(x,y,Ex,Ey, color=color, linewidth=1,cmap=plt.cm.inferno, density=2,arrowstyle='->',arrowsize = 1.5)
carga_colors = {False:'#aa0000',True:'#0000aa'}
for q, pos in cargas:
  ax.add_artist(Circle(pos,0.05,color=carga_colors[q<0]))
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')
plt.show()

#Prefiles del campo eléctrico
fig = plt.figure()
a3=fig.add_subplot(223)
plt.plot(y,np.transpose(Etotal))
plt.ylim(0,40)
a3.set_xlabel('$y$')

a4=fig.add_subplot(224)
plt.plot(x,Etotal)
plt.ylim(0,40)
a4.set_xlabel('$x$')
plt.show()

'''
POTENCIAL DEBIDO A UN DIPOLO POR LA ECUACIÓN DE POISSON
'''
v = np.zeros((N,N))
Vtemp = np.zeros((N,N))
eps = 1e-8    # Definimos un epsilon

for i in range(1000):
  k = 0         # Definimos un contador
  error = 10    # Definimos un error
  while k < 1e4 and error > eps:
    error = 0
    for i in range(1,N-1):
      for j in range(1,N-1):
        v[i,j] = (v[i+1,j]+v[i-1,j]+v[i,j+1]+v[i,j-1]+rho[i,j]*h**2)/4
        error += v[i,j]-Vtemp[i,j]
        k+=1
    error = error/N**2

colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

ax = fig.add_subplot(221)
plt.contourf(x, y , np.transpose(v), colorinterpolation, cmap=colourMap)
plt.title("Poisson")

ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')
plt.colorbar()

a2 = fig.add_subplot(222)
plt.contourf(X, Y , np.transpose(v), colorinterpolation, cmap=colourMap)
plt.title("Poisson")

a2.set_xlabel('$x$')
a2.set_ylabel('$y$')
a2.set_aspect('equal')
plt.colorbar()

a3=fig.add_subplot(223)
plt.plot(x,np.transpose(v))
a3.set_xlabel('$x$')

a4=fig.add_subplot(224)
plt.plot(y,v)
a4.set_xlabel('$y$')
plt.show()
'''
CAMPO ELÉCTRICO DEBIDO A UN DIPOLO POR EL GRADIENTE DEL POTENCIAL
'''

Exg, Eyg = np.zeros((N,N)), np.zeros((N,N))
for i in range(1,N-1):
    for j in range(1,N-1):
        Exg[i,j] = -(v[i+1,j]-v[i,j])/h
        Eyg[i,j] = -(v[i,j+1]-v[i,j])/h
        
fig = plt.figure()
ax = fig.add_subplot(111)
color = 2*np.log(np.hypot(Exg,Eyg))
ax.streamplot(x,y,np.transpose(Exg),np.transpose(Eyg), color=color, linewidth=1,cmap=plt.cm.inferno, density=2,arrowstyle='->',arrowsize = 1.5)
carga_colors = {False:'#aa0000',True:'#0000aa'}
for q, pos in cargas:
  ax.add_artist(Circle(pos,0.05,color=carga_colors[q<0]))
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')
plt.show()



