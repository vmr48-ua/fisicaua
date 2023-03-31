import numpy as np
import matplotlib
import matplotlib.pyplot as plt

# Podemos representar una magnitud escalar en 2 dimensiones con una matriz
# Asignando a cada elemento de la matriz una posición

# Primero definimos un tamaño del área que representará la matriz LxL,
# con esquina inferior izquierda en el punto (L0,L0) y la resolución h, en función del númuero de puntos N


# Tamaño y resolucion
L  = 4.0
L0 = -2.0 #l0 = l/2
N = 21
h = L/(N-1) #resolucion


#unos vectores x, y que contienen las posiciones
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Podemos definir una red (grid)
# son matrizces de posiciones que nos ser�n �ltiles m�s tarde
# Esto nos dará un poco más de juego y nos permitirá representar campos vectoriales.
X,Y = np.meshgrid(y,x)


# Generamos una matriz NxN cuyas componentes var�an con la posici�n x,y
M=np.zeros((N,N))
"""un ejemplo"""
M=X**2+Y**3 

##HACER: modificar y representar la matriz M


# Definimmos un mapa de colores para representar la magnitud contenida en la matriz
colorinterpolation = 50
colourMap = plt.cm.hsv
fig = plt.figure()

#primer subplot, representamos los valores de la matriz M para las posiciones x,y

ax = fig.add_subplot(221)
# Configure the contour
plt.contourf(x, y , np.transpose(M), colorinterpolation, cmap=colourMap)
plt.title("M")

    
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')
plt.colorbar()


# otra forma de hacer lo mismo utilizando los meshgrids (atencion a transposicion)
a2 = fig.add_subplot(222)
plt.contourf(X, Y , np.transpose(M), colorinterpolation, cmap=colourMap)
plt.title("M")

    
a2.set_xlabel('$x$')
a2.set_ylabel('$y$')
a2.set_aspect('equal')
plt.colorbar()


#Terminamos dibujando todos los vectores que componen de la matriz ("cortes o perfiles en las direcciones horizontales y verticales)

a3=fig.add_subplot(223)
plt.plot(x,np.transpose(M),color='k')
a3.set_xlabel('$x$')

a4=fig.add_subplot(224)
plt.plot(y,M,color='k')
a4.set_xlabel('$y$')

plt.show()
## puedes escribir una funci�n que represente solo el perfil de M en funcion x para solo un valor dado de y?