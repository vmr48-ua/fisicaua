import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle


def E(q, r0, y,z):
    den = np.hypot(y-r0[0], z-r0[1])**3
    return q * (y - r0[0]) / den, q * (z - r0[1]) / den

def plotCampo(Cx,Cy,yP,zP,title=''):
    fig = plt.figure()
    ax = fig.add_subplot(111)
    color = 2 * np.log(np.hypot(Cx, Cy))
    ax.streamplot(yP, zP, Cx, Cy, color=color, linewidth=1, cmap=plt.cm.hot,
                    density=2, arrowstyle='->', arrowsize=1.5)
    ax.add_artist(Circle((0,0), 0.05, color='#0000aa')) 
    ax.set_xlabel('$y$')
    ax.set_ylabel('$z$')
    ax.set_xlim(-2,2)
    ax.set_ylim(-2,2)
    ax.set_aspect('equal')
    ax.set_title(title)

    return None

ny, nz = 64, 64
y = np.linspace(-2,2,ny)
z = np.linspace(-2,2,nz)
Y, Z = np.meshgrid(y,z)

q = 1 
c = 1
v = 1/10
beta = v/c
gamma = (1)/np.sqrt(1-beta**2)

Ey, Ez = np.zeros((ny, nz)), np.zeros((ny, nz))
r0=np.array([0,0])
Ey, Ez = E(q, r0,y=Y, z=Z)

plotCampo(gamma*Ey, gamma*Ez, y,z, title='Líneas de campo eléctrico')
plotCampo(-v*gamma*Ez, gamma*v*Ey, y,z, title='Líneas de campo magnético')

plt.show()