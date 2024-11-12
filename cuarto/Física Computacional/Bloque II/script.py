import numpy as np
import matplotlib.pyplot as plt

# Constantes
G = 6.6738e-11  # m^3 kg^-1 s^-2
M = 1.9891e30   # kg (masa del Sol)
m = 5.9722e24   # kg (masa de la Tierra)
dt = 3600       # Paso de tiempo de 1 hora en segundos
t_total = 5 * 365.25 * 24 * 3600  # 5 años en segundos

# Condiciones iniciales
x = 1.4719e11  # m, distancia inicial (perihelio)
y = 0          # m
vx = 0         # m/s
vy = 3.0287e4  # m/s

def aceleracion(x, y):
    r = np.sqrt(x**2 + y**2)
    ax = -G * M * x / r**3
    ay = -G * M * y / r**3
    return ax, ay

# Inicializar listas para almacenar datos
t_points = []
x_points = []
y_points = []
vx_points = []
vy_points = []
r_points = []
E_kinetic_points = []
E_potential_points = []

# Variables iniciales
t = 0
ax, ay = aceleracion(x, y)

# Bucle para el cálculo durante el tiempo total
while t < t_total:
    # Almacenar valores
    t_points.append(t)
    x_points.append(x)
    y_points.append(y)
    vx_points.append(vx)
    vy_points.append(vy)
    r = np.sqrt(x**2 + y**2)
    r_points.append(r)
    
    # Energías
    E_kinetic = 0.5 * m * (vx**2 + vy**2)
    E_potential = -G * M * m / r
    E_kinetic_points.append(E_kinetic)
    E_potential_points.append(E_potential)

    # Algoritmo de Verlet
    x_new = x + vx * dt + 0.5 * ax * dt**2
    y_new = y + vy * dt + 0.5 * ay * dt**2
    ax_new, ay_new = aceleracion(x_new, y_new)
    vx += 0.5 * (ax + ax_new) * dt
    vy += 0.5 * (ay + ay_new) * dt
    x, y = x_new, y_new
    ax, ay = ax_new, ay_new
    
    # Incrementar el tiempo
    t += dt

# Gráfica de la trayectoria x vs y
plt.figure(figsize=(8, 8))
plt.plot(x_points, y_points)
plt.xlabel("x (m)")
plt.ylabel("y (m)")
plt.title("Trayectoria de la Tierra alrededor del Sol")
plt.grid()
plt.axis('equal')
plt.show()

# Gráfica del radio en función del tiempo
plt.figure()
plt.plot(t_points, r_points)
plt.xlabel("Tiempo (s)")
plt.ylabel("Radio (m)")
plt.title("Radio en función del tiempo")
plt.grid()
plt.show()

# Gráfica de energías en función del tiempo
plt.figure()
plt.plot(t_points, E_kinetic_points, label="Energía cinética")
plt.plot(t_points, E_potential_points, label="Energía potencial")
plt.plot(t_points, np.array(E_kinetic_points) + np.array(E_potential_points), label="Energía total")
plt.xlabel("Tiempo (s)")
plt.ylabel("Energía (J)")
plt.title("Energía cinética, potencial y total en función del tiempo")
plt.legend()
plt.grid()
plt.show()
