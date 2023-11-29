import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

#Parte 2: Campos creados por un dipolo en movimento

#CAMPO ELECTRICO EN S'

"""Definimos las función campo eléctrico"""
def E(q, r0, y,z):
 """Devuelve el campo electrico vector E=(Ey,Ez) debido a una carga q en r0."""
 den = np.hypot(y-r0[0], z-r0[1])**3
 return q * (y - r0[0]) / den, q * (z - r0[1]) / den

"""Creamos un mallado de puntos"""
ny, nz = 64, 64
y = np.linspace(-2, 2, ny)
z = np.linspace(-2,2,nz)
Y, Z = np.meshgrid(y, z)
q = 1 # Le damos un valor unidad a la carga

"""Hallamos el campo eléctrico en el sistema S'"""
# Vector de campo eléctrico, E=(Ex, Ey), como componentes separadas
Ey1, Ez1 = np.zeros((ny, nz)), np.zeros((ny, nz))
r0=np.array([0.5,0.5])
Ey1, Ez1 = E(q, r0,y=Y, z=Z)
Ey2, Ez2 = np.zeros((ny, nz)), np.zeros((ny, nz))
Ey2, Ez2 = E(-q, -r0,y=Y, z=Z)
Ez = Ez1 + Ez2
Ey = Ey1 + Ey2

#CAMPOS EN S
"""Aplicamos las transformaciones de Lorentz para hallar los campos en S"""
#Bz = Bx = By = 0 en el sistema en reposo
c = 1
v = 1/10
b = v
gamma = (1)/np.sqrt(1-b**2)
Eys = gamma*Ey
Ezs = gamma*Ez
Bys = gamma*(-v)*Ez
Bzs = gamma*v*Ey

"""Dibujamos el campo eléctrico en S"""
fig = plt.figure()
ax = fig.add_subplot(111)
ax.add_artist(Circle((0.5,0.5), 0.05, color='#0000aa')) 
ax.add_artist(Circle((-0.5,-0.5), 0.05, color='#0000aa')) 
color = 2 * np.log(np.hypot(Eys, Ezs))
ax.streamplot(y, z, Eys, Ezs, color=color, linewidth=1, cmap=plt.cm.hot,
 density=2, arrowstyle='->', arrowsize=1.5)
ax.set_xlabel('$y$')
ax.set_ylabel('$z$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')

"""Dibujamos el campo magnético en S"""
fig = plt.figure()
ax = fig.add_subplot(111)
color = 2 * np.log(np.hypot(Bys, Bzs))
ax.streamplot(y, z, Bys, Bzs, color=color, linewidth=1, cmap=plt.cm.hot,
 density=2, arrowstyle='->', arrowsize=1.5)
ax.add_artist(Circle((0.5,0.5), 0.05, color='#0000aa')) 
ax.add_artist(Circle((-0.5,-0.5), 0.05, color='#0000aa')) 
ax.set_xlabel('$y$')
ax.set_ylabel('$z$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')
