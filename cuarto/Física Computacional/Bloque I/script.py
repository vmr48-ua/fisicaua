import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# Función inicial
def uf(x):
    if 1 <= x <= 2:
        return 1
    else:
        return 0

uf = np.vectorize(uf)

# Función para aplicar las condiciones de contorno periódicas
def contornoPeriódico(a):
    a[0] = a[-2]
    a[-1] = a[1]
    return a

# Función para calcular la energía
def energia(a):
    a = np.array(a)
    return np.sum(0.5 * a**2)

# Método de diferencias centrales
def diferencias_centrales(u, dx, dt):
    for i in range(len(t)-1):
        u[1:-1, i+1, 0] = u[1:-1, i, 0] - (c * dt) / (2 * dx) * (u[2:, i, 0] - u[:-2, i, 0])
        u[:, i+1, 0] = contornoPeriódico(u[:, i+1, 0])
        u[:, i+1, 2] = energia(u[:, i+1, 0])  # Calculamos la energía y la guardamos en la tercera componente
    return u

# Método Upwind
def upwind(u, dx, dt):
    for i in range(len(t)-1):
        u[1:-1, i+1, 0] = u[1:-1, i, 0] - (c * dt) / dx * (u[1:-1, i, 0] - u[:-2, i, 0])
        u[:, i+1, 0] = contornoPeriódico(u[:, i+1, 0])
        u[:, i+1, 2] = energia(u[:, i+1, 0])  # Calculamos la energía
    return u

# Método Downwind
def downwind(u, dx, dt):
    for i in range(len(t)-1):
        u[1:-1, i+1, 0] = u[1:-1, i, 0] - (c * dt) / dx * (u[2:, i, 0] - u[1:-1, i, 0])
        u[:, i+1, 0] = contornoPeriódico(u[:, i+1, 0])
        u[:, i+1, 2] = energia(u[:, i+1, 0])  # Calculamos la energía
    return contornoPeriódico(u)

# Parámetros
L = 5.        # Longitud del dominio
dx = 0.05     # Diferencial espacial
dt = 0.004    # Diferencial temporal
global c
c = 2.        # cte
t_max = 5.    # Tiempo de simulación

if c > 0 and c * dt > dx:
    raise Exception('Condición de estabilidad no satisfecha')

# Dominio
x = np.arange(0, L, dx)
t = np.arange(0, t_max, dt)

# Inicializamos U con tres componentes: posición, tiempo y energía
U = np.zeros((len(x), len(t), 3))
U[:, 0, 0] = uf(x)  # Condiciones iniciales para u(x, t)

# Configuración de la figura con dos subplots
fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 8))
ax1.set_xlim(0, L)
ax1.set_ylim(-0.1, 1.1)
ax2.set_xlim(0, t_max)
#ax2.set_ylim(0, 1.5)  # Ajusta según el rango esperado de la energía

# Gráficas
line_central, = ax1.plot([], [], label='Diferencias Centrales', color='blue')
line_upwind, = ax1.plot([], [], label='Upwind', color='green')
line_downwind, = ax1.plot([], [], label='Downwind', color='red')
line_energy, = ax2.plot([], [], label='Energía', color='black')

# Texto del tiempo
time_text = ax1.text(0.8, 0.85, '', transform=ax1.transAxes, fontsize=12)

# Etiquetas
ax1.set_xlabel('x')
ax1.set_ylabel('u(x, t)')
ax1.legend(loc='upper right')

ax2.set_xlabel('Tiempo (s)')
ax2.set_ylabel('Energía total')

# Función para actualizar la animación
def update(j):
    # Actualización de u(x, t)
    line_central.set_data(x, diferencias_centrales(U, dx, dt)[:, j, 0])
    line_upwind.set_data(x, upwind(U, dx, dt)[:, j, 0])
    line_downwind.set_data(x, downwind(U, dx, dt)[:, j, 0])

    # Actualización de la energía
    energia_total = U[:, j, 2]
    line_energy.set_data(t[:j+1], U[0, :j+1, 2])  # Usamos el valor en la posición U[0,:,2] para la energía

    # Actualización del texto del tiempo
    time_text.set_text('t = {}s'.format(np.round(j * dt, 2)))
    
    return line_central, line_upwind, line_downwind, line_energy, time_text

# Animación
ani = FuncAnimation(fig, update, frames=range(0, len(t), 12), interval=1)
plt.tight_layout()
plt.show()
