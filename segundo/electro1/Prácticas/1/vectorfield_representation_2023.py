import numpy as np
import matplotlib
import matplotlib.pyplot as plt

# Ahora queremos representar un campo vectorial en dos dimensiones.
# cada posici�n del espacio que definimos en 2D tiene asociadas dos coordenadas que son 
# las componentes del vector

# Tamaño y resolucion
L  = 4.0
L0 = -2.0
N = 21
h = L/(N-1) #resolucion

# Definimos de nuevo arrays
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Y un grid que ahora s� que nos ser� muy �til
X,Y = np.meshgrid(y,x)


# Generamos un campo vectorial
Mx, My = np.ones((N, N)), np.ones((N, N))
Mx, My = X, Y 

##HACER: modificar y representar la matriz M para que represente un campo convergente en lugar de divergente

fig = plt.figure()
ax = fig.add_subplot(111)

# Plot the streamlines with an appropriate colormap and arrow style
color = 2 * np.log(np.hypot(Mx, My))
ax.streamplot(x, y, Mx, My, color=color, linewidth=1, cmap=plt.cm.inferno,
              density=2, arrowstyle='->', arrowsize=1.5)



ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')

## Buscar ejemplos de funciones de python que sirvan para representar campos vectoriales en 3D
plt.show()