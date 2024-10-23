import numpy as np
import numpy.linalg as la
from numpy import sin, cos, pi, exp

import time
import random
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation, FFMpegFileWriter
from matplotlib.patches import FancyArrowPatch
from mpl_toolkits.mplot3d import Axes3D
from scipy import sparse
from scipy.integrate import quad

import numpy as np
from scipy import sparse
import scipy.linalg as la
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

import numpy as np
from scipy import sparse
import scipy.linalg as la
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# Parámetros físicos y numéricos
L = 5.0       # Longitud del dominio
dx = 0.1     # Paso espacial
dt = 0.05    # Paso temporal
t_max = 2.0   # Tiempo máximo de simulación
hbar = 1      # Constante de Planck reducida (ħ = 1)
m = 1         # Masa de la partícula (m = 1)
omega = 1     # Frecuencia del oscilador armónico
cte = dt / (2 * dx**2)

# Discretización del espacio y tiempo
x = np.arange(-L, L, dx)
t = np.arange(0, t_max, dt)

# Potencial del oscilador armónico
V = 0.5 * m * omega**2 * x**2

# Condición inicial: función de onda gaussiana (estado fundamental)
psi = np.zeros((len(x), len(t)), dtype=complex)
psi[:, 0] = 0.85*np.exp(-x**2*np.sqrt(m*omega/hbar))*np.exp(-1j*x**2*np.sqrt(m*omega/hbar))

# Matriz de diferencias finitas para el Laplaciano
d = [-2 for _ in range(len(x))]
u = [1 for _ in range(len(x)-1)]
o = [1 for _ in range(len(x)-1)]

Laplaciano1D = sparse.diags([d, u, o], [0, -1, 1], shape=(len(x), len(x)))

# Matrices del método de Crank-Nicholson
A1 = np.eye(len(x)) + 1j * cte * Laplaciano1D.toarray()
A2 = np.eye(len(x)) - 1j * cte * Laplaciano1D.toarray()

# Incorporación del potencial en las matrices A1 y A2
for i in range(len(x)):
    A1[i, i] += 1j * dt * V[i] / 2
    A2[i, i] -= 1j * dt * V[i] / 2

# Condiciones de contorno (psi(x = -L) = psi(x = L) = 0)
A1[0, :] = A1[-1, :] = np.zeros(len(x))
A1[0, 0] = A1[-1, -1] = 1
A2[0, :] = A2[-1, :] = np.zeros(len(x))
A2[0, 0] = A2[-1, -1] = 1
psi[0, :] = psi[-1, :] = 0  # Función de onda en los bordes = 0

# Evolución temporal con el método de Crank-Nicholson
for k in range(len(t)-1):
    print(f"Tiempo: {t[k]:.2f} / {t_max:.2f} s", end="\r")
    psi[:, k+1] = la.solve(A1, np.dot(A2, psi[:, k]))

# Gráfica y animación
fig, ax = plt.subplots()
line, = ax.plot(x, np.abs(psi[:, 0])**2)
ax.set_ylim(0, 1)
ax.set_xlim(-L, L)
plt.title("Evolución temporal de $|\\psi(x,t)|^2$ (Oscilador armónico)")

def animate(j):
    line.set_ydata(np.abs(psi[:, j])**2)
    return line,

anim = FuncAnimation(fig, animate, frames=len(t), interval=5)
plt.xlabel('Posición (x)')
plt.ylabel(r'$|\psi(x,t)|^2$')
plt.show()