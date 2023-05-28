
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



# Define arrays used for plotting
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Aunque ahora no lo necesitamos. Podemos definir una red para un campo vectorial.
# Esto nos darÃ¡ un poco mÃ¡s de juego y nos permitirÃ¡ representar campos vectoriales.
X,Y = np.meshgrid(y,x)



# Generamos un campo vectorial
Mx, My = np.ones((N, N)), np.ones((N, N))
Mx, My = X, Y 

##HACER: modificar y representar la matriz M


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


