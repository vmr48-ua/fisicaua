
import numpy as np
import matplotlib
import matplotlib.pyplot as plt

# Podemos representar una magnitud escalar en 2 dimensiones con una matriz
# Asignando a cada elemento de la matriz una posición

# Primero definimos un tamaño del área que representará la matriz LxL,
# con esquina inferior izquierda en el punto (L0,L0) y la resolución h, en función del númuero de puntos N


# TamaÃ±o y resolucion
L  = 4.0
L0 = -2.0
N = 21
h = L/(N-1) #resolucion


#unos vectores x, y que contienen las posiciones
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Aunque ahora no lo necesitamos. Podemos definir una red para un campo vectorial.
# matrizces de posiciones que nos serán últiles más tarde
# Esto nos darÃ¡ un poco mÃ¡s de juego y nos permitirÃ¡ representar campos vectoriales.
X,Y = np.meshgrid(y,x)


# Generamos una matriz NxN
M=np.zeros((N,N))
M=X+Y

##HACER: modificar y representar la matriz M


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
plt.plot(x,np.transpose(M))
a3.set_xlabel('$x$')

a4=fig.add_subplot(224)
plt.plot(y,M)
a4.set_xlabel('$y$')


