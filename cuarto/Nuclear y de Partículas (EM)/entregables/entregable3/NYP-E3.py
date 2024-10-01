import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import odeint
from matplotlib.animation import FuncAnimation

# Constantes de desintegración
lambda_U238  = 4.916e-18
lambda_Th234 = 2.88e-6
lambda_Pa234 = 1.88e-4
lambda_U234  = 2.46e-5
lambda_Th230 = 9.19e-6
lambda_Ra226 = 4.33e-4
lambda_Rn222 = 2.10e-3
lambda_Po218 = 1.53e-3
lambda_Pb214 = 1.14e-3
lambda_Bi214 = 3.73e-3
lambda_Tl210 = 2.2e-4
lambda_Pb210 = 7.21e-4
lambda_Bi210 = 2.17e-4
lambda_Tl206 = 1.55e-4
lambda_Pb206 = 0  # estable

# Sistema de EDOs
def sistema(N, t):
    N_U238, N_Th234, N_Pa234, N_U234, N_Th230, N_Ra226, N_Rn222, N_Po218, N_Pb214, N_Bi214, N_Tl210, N_Pb210, N_Bi210, N_Tl206, N_Pb206 = N
    
    dN_U238_dt = -lambda_U238 * N_U238
    dN_Th234_dt = lambda_U238 * N_U238 - lambda_Th234 * N_Th234
    dN_Pa234_dt = lambda_Th234 * N_Th234 - lambda_Pa234 * N_Pa234
    dN_U234_dt = lambda_Pa234 * N_Pa234 - lambda_U234 * N_U234
    dN_Th230_dt = lambda_U234 * N_U234 - lambda_Th230 * N_Th230
    dN_Ra226_dt = lambda_Th230 * N_Th230 - lambda_Ra226 * N_Ra226
    dN_Rn222_dt = lambda_Ra226 * N_Ra226 - lambda_Rn222 * N_Rn222
    dN_Po218_dt = lambda_Rn222 * N_Rn222 - lambda_Po218 * N_Po218
    dN_Pb214_dt = lambda_Po218 * N_Po218 - lambda_Pb214 * N_Pb214
    dN_Bi214_dt = lambda_Pb214 * N_Pb214 - lambda_Bi214 * N_Bi214
    dN_Tl210_dt = lambda_Bi214 * N_Bi214 - lambda_Tl210 * N_Tl210
    dN_Pb210_dt = lambda_Tl210 * N_Tl210 - lambda_Pb210 * N_Pb210
    dN_Bi210_dt = lambda_Pb210 * N_Pb210 - lambda_Bi210 * N_Bi210
    dN_Tl206_dt = lambda_Bi210 * N_Bi210 - lambda_Tl206 * N_Tl206
    dN_Pb206_dt = lambda_Tl206 * N_Tl206 - lambda_Pb206 * N_Pb206

    return [dN_U238_dt, dN_Th234_dt, dN_Pa234_dt, dN_U234_dt, dN_Th230_dt, dN_Ra226_dt, dN_Rn222_dt, dN_Po218_dt, dN_Pb214_dt, dN_Bi214_dt, dN_Tl210_dt, dN_Pb210_dt, dN_Bi210_dt, dN_Tl206_dt, dN_Pb206_dt]

# Condiciones iniciales
N0 = [1e5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]  # Solo U-238 presente inicialmente ~4e-17 gramos (0.04 femtogramos)

# Tiempo de simulación (1.3e18 segundos es ~41 mil millones de años)
t_max =  1.3e18
t = np.linspace(0, t_max, 1000)

solucion = odeint(sistema, N0, t)

# Animación
fig, ax = plt.subplots(figsize=(10, 8))

line_U238, = ax.plot([], [], label='N_U238', lw=2)
line_Pb206, = ax.plot([], [], label='N_Pb206', lw=2)

ax.set_xlim(0, t_max)
ax.set_ylim(0, 1e5)
ax.set_xlabel('Tiempo (s)')
ax.set_ylabel('Número de núcleos')
ax.legend(loc='upper right')
ax.grid()

def init():
    line_U238.set_data([], [])
    line_Pb206.set_data([], [])
    return (line_U238, line_Pb206)

def animate(i):
    line_U238.set_data(t[:i], solucion[:i, 0])
    line_Pb206.set_data(t[:i], solucion[:i, 14])
    return (line_U238, line_Pb206)

anim = FuncAnimation(fig, animate, init_func=init, frames=len(t), interval=5, blit=True)

# Gráfica del último frame
fig_final, ax_final = plt.subplots(figsize=(10, 8))
ax_final.plot(t, solucion[:, 0], label='N_U238', lw=2)
ax_final.plot(t, solucion[:, 14], label='N_Pb206', lw=2)

ax_final.set_xlim(0, t_max)
ax_final.set_ylim(0, 1e5)
ax_final.set_xlabel('Tiempo (s)')
ax_final.set_ylabel('Número de núcleos')
ax_final.set_title('Decaimiento del $U_{238}$ en $Pb_{206}$')
ax_final.legend(loc='upper right')
ax_final.grid()

plt.show()