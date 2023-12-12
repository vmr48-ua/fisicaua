"""PRÁCTICA II ELECTROMAGNETISMO II"""
"""Víctor Mira Ramírez - 74528754Z"""

import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

def E(q, r0, y,z):
    # Calcula el campo de una carga q en r0
    den = np.hypot(y-r0[0], z-r0[1])**3
    return q * (y - r0[0]) / den, q * (z - r0[1]) / den

def plotCampo(Cxx,Cyy,title='',type='puntual',campo='electrico'):
    # Función que plotea el campo eléctrico/magnético para 
    # una carga puntual y un dipolo
    fig = plt.figure()
    ax = fig.add_subplot(111)

    # Comprueba si ploteamos el campo eléctrico o el magnético y
    # realiza la transformación adecuada
    if campo == 'electrico':
        Cx,Cy = gamma*Cxx, gamma*Cyy
    else:
        Cx,Cy = -v*gamma*Cxx, v*gamma*Cyy

    # Comprueba si ploteamos una carga puntual o un dipolo
    if type == 'puntual':
        ax.add_artist(Circle((0,0), 0.05, color='#0000aa')) 
    else:
        ax.add_artist(Circle((0.5,0.5), 0.05, color='#0000aa'))
        ax.add_artist(Circle((-0.5,-0.5), 0.05, color='#0000aa'))

    # Ploteo
    color = 2 * np.log(np.hypot(Cx, Cy))
    ax.streamplot(y, z, Cx, Cy, color=color, linewidth=1, cmap=plt.cm.hot,
                    density=2, arrowstyle='->', arrowsize=1.5)
    ax.set_xlabel('$y$')
    ax.set_ylabel('$z$')
    ax.set_xlim(-2,2)
    ax.set_ylim(-2,2)
    ax.set_aspect('equal')
    ax.set_title(title)

    return None

# Definimos constantes 
ny, nz = 64, 64
y, z = np.linspace(-2,2,ny), np.linspace(-2,2,nz)
Y, Z = np.meshgrid(y,z)

q = 1 
c = 1
v = 1/10
beta = v/c
gamma = (1)/np.sqrt(1-beta**2)


""" PARTE 1 """
Ey, Ez = np.zeros((ny, nz)), np.zeros((ny, nz))
r0 = np.array([0,0])

Ey, Ez = E(q, r0,y=Y, z=Z)

plotCampo(Ey, Ez,  title='Líneas de campo eléctrico: carga puntual')
plotCampo(Ez, Ey,  title='Líneas de campo magnético: carga puntual', campo='magnetico')


""" PARTE 2 """
Ey1, Ez1, Ey2, Ez2 = np.zeros((ny, nz)), np.zeros((ny, nz)), np.zeros((ny, nz)), np.zeros((ny, nz))
r0 = np.array([0.5,0.5])

Ey1, Ez1 = E(q, r0,y=Y, z=Z)
Ey2, Ez2 = E(-q, -r0,y=Y, z=Z)

plotCampo(Ey1+Ey2, Ez1+Ez2, title='Líneas de campo eléctrico: dipolo', type='dipolo')
plotCampo(Ez1+Ez2, Ey1+Ey2, title='Líneas de campo eléctrico: dipolo', type='dipolo', campo='magnetico')

plt.show()