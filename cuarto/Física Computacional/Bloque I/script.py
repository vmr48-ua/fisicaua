import numpy as np
from numpy import pi
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from scipy import sparse
import numpy.linalg as la

L = 2.
T = 40.  # t_max
Nx = 100
Nt = 3000
dx = L/Nx
dt = T/Nt

c = 1.
k_rho = 0.1
a = -2.0

x = np.linspace(0, L, Nx)
t = np.linspace(0, T, Nt)

modos = [1, 2, 3, 4]
u = np.zeros((len(modos), Nx, Nt))  # modo, espacio y tiempo

for idx, n in enumerate(modos):
    u[idx, :, 0] = np.sin(n*pi*x/L)  # cada modo con sin(n*pi*0/L)

# Contorno
u[:, 0, :] = 0 # extremos fijos
u[:, -1, :] = 0

for idx in range(len(modos)):
    u[idx, :, 1] = u[idx, :, 0]  # du/dt(0) = 0

for j in range(1, Nt-1):
    for idx in range(len(modos)):           
        u[idx,1:-1,j+1] = (2*u[idx,1:-1,j] - u[idx,1:-1,j-1] + (c**2*dt**2/dx**2) * (u[idx,2:,j] 
                           - 2*u[idx,1:-1,j] + u[idx,:-2,j]) - (2*k_rho*dt * u[idx,1:-1,j] 
                           + a*dt**2*u[idx,1:-1,j]))

# Crank-Nicholson
# cte = (c*dt/dx)**2 * 2

# d = np.array([1 - 2*cte for _ in range(Nx)])
# ud = np.array([cte for _ in range(Nx - 1)])
# o = np.array(ud.copy())

# A = sparse.diags([d,ud,o],[0,-1,1]).toarray()
# B = sparse.diags([2-d,ud,o],[0,-1,1]).toarray()

# A_inv = la.inv(A)
# # A u^{j+1} = B u^j
# for j in range(1, Nt - 1):
#     for idx in range(len(modos)):
#         b = np.dot(B, u[idx, :, j]) - (2*k_rho*dt - a*dt**2)*u[idx, :, j]
#         b[0],b[-1] = 0,0
#         u[idx, :, j+1] = np.dot(A_inv, b)
        
#         u[idx,0,j+1],u[idx,-1,j+1] = 0,0

fig, ax = plt.subplots()
ax.set_xlim(-0.05, L+0.05)
ax.set_ylim(-1.05, 1.05)

lines = []
for idx, n in enumerate(modos):
    line, = ax.plot(x, u[idx, :, 0], label='Modo {}'.format(n))
    lines.append(line)
time_text = ax.text(0.85, 0.65, '', transform=ax.transAxes, fontsize=10)

def animate(j):
    for idx, line in enumerate(lines):
        line.set_ydata(u[idx, :, j])
    time_text.set_text('t = {}s'.format(np.round(j*dt, 2)))
    return *lines, time_text

anim = FuncAnimation(fig, animate, frames=range(Nt), interval=20, blit=True)
ax.legend(loc="upper right")
plt.show()
