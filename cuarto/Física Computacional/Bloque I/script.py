import numpy as np
import numpy.linalg as la
from numpy import sin, cos, pi, exp

import time
import random
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from matplotlib.patches import FancyArrowPatch
from mpl_toolkits.mplot3d import Axes3D
from scipy import sparse

def v_x(x, y):
    return 0.4 * sin(pi*x)
def v_y(x, y):
    return 0.4 * cos(pi*y)

D = 0.1       # Coeficiente de difusión
L = 1.        # Longitud del dominio
dx = 0.03     # Diferenciales espaciales
dy = 0.03  
dt = 0.002    # Diferencial temporal
cte = D * dt / dx**2
t_max = 1. * L  

if cte >= 0.5:
    raise Exception('Condición de estabilidad no satisfecha, prueba a bajar dt')

x, y = np.arange(-L, L, dx), np.arange(-L, L, dy)
t = np.arange(0, t_max, dt)

X, Y = np.meshgrid(x, y)
T = np.zeros((len(x), len(y), len(t)))
T[:,:,0] = 100 * np.exp(-10 * (X**2 + Y**2))

n = len(x)
d = [-4 for _ in range(len(x)**2)]
u = [1  for _ in range(len(x)**2-1)]
o = [1  for _ in range(len(x)**2-1)]
v = [1  for _ in range(len(x)**2-len(x))]

Laplaciano2D = sparse.diags([d, u, o, v, v], [0, -1, 1, -n, n], shape=(n**2, n**2))
A1 = np.eye(len(x)**2) - cte * Laplaciano2D.toarray()
A2 = np.eye(len(x)**2) + cte * Laplaciano2D.toarray()

for i in range(len(x)):
    n = len(x)
    zeros = np.zeros(n**2)
    
    A1[i, :], A2[i, :] = zeros.copy(), zeros.copy()  # x = -L
    A1[i, i] = 1

    A1[i*n, :], A2[i*n, :] = zeros.copy(), zeros.copy()  # x = L
    A1[i*n, i*n] = 1

    A1[-i -1, :], A2[-i -1, :] = zeros.copy(), zeros.copy()  # y = -L
    A1[-i -1, -i -1] = 1

    A1[i*n + n-1, :], A2[i*n + n-1, :] = zeros.copy(), zeros.copy()  # y = L
    A1[i*n + n-1, i*n + n-1] = 1

A = np.dot(la.inv(A1), A2)

for k in range(len(t) - 1):
    print(np.round(k / (len(t) - 1) * 100, 1), '%', end='\r')
    T_next = np.dot(A, T[:,:,k].reshape(n**2)).reshape(n, n)
    
    adveccion_x_local = np.array([[v_x(x[i], y[j]) for j in range(1, n-1)] for i in range(1, n-1)])
    T_next[1:-1, 1:-1] -= (adveccion_x_local * dt / (2 * dx)) * (T[2:, 1:-1, k] - T[:-2, 1:-1, k])
    
    adveccion_y_local = np.array([[v_y(x[i], y[j]) for j in range(1, n-1)] for i in range(1, n-1)])
    T_next[1:-1, 1:-1] -= (adveccion_y_local * dt / (2 * dy)) * (T[1:-1, 2:, k] - T[1:-1, :-2, k])

    T[:,:,k+1] = T_next

# Gráfica final
fig4, ax4 = plt.subplots()
plt.title('Evolución temporal de T(x,y) con advección variable y difusión, \n Método de Crank-Nicholson')
ax4.set_xlim(-L, L)
ax4.set_ylim(-L, L)

line4 = ax4.imshow(T[:,:,0].T, extent=[-L, L, -L, L], cmap='inferno', origin='lower')
time_text = ax4.text(0.8, 0.85, '', transform=ax4.transAxes, color='white', fontsize=10)
fig4.colorbar(line4, label='Magnitud de T')

def animate4(j):
    line4.set_data(T[:,:,j].T)
    time_text.set_text('t = {}s'.format(np.round(j * dt, 2)))
    return line4, ax4

anim4 = FuncAnimation(fig4, animate4, frames=range(0, len(t), 10), interval=1)
plt.xlabel('Espacio (x)')
plt.ylabel('Espacio (y)')

plt.show()
