import numpy as np
import matplotlib.pyplot as plt
from scipy.sparse import diags
from scipy.sparse.linalg import spsolve

# Parámetros
L = 1   # Lado del cuadrado
N = 500  # Número de puntos de la malla en cada dirección
h = L / (N+1)  # Tamaño del paso
x = np.linspace(0, L, N+2)  # Incluye los puntos de borde

# Crear la matriz A para la ecuación discreta de Laplace
d = [-4 for _ in range(N*N)]    # Diagonal principal
o = [1  for _ in range(N*N)]   # Diagonal superior

# Crear las diagonales correspondientes
diagonals = [d, o, o, o, o]

# Posiciones en la matriz dispersa
positions = [0, -1, 1, -N, N]

# Crear la matriz dispersa A
A = diags(diagonals, positions, shape=(N * N, N * N))

# Vector de términos independientes b
b = np.zeros(N * N)

# Implementar las condiciones de contorno
for i in range(N):
    for j in range(N):
        idx = i * N + j
        if i == 0:  # y = 0 -> Phi(x, 0) = 0
            b[idx] -= 0
        if i == N-1:  # y = L -> Phi(x, L) = x(1 - x)
            b[idx] -= x[j+1] * (1 - x[j+1])
        if j == 0:  # x = 0 -> Phi(0, y) = 0
            b[idx] -= 0
        if j == N-1:  # x = L -> Phi(L, y) = 0
            b[idx] -= 0

# Resolver el sistema
Phi_interior = spsolve(A, b)
print(Phi_interior.reshape((N,N)))

# Convertir la solución en una matriz 2D para visualización
Phi = np.zeros((N+2, N+2))
Phi[1:-1, 1:-1] = Phi_interior.reshape((N, N))

# Condiciones de contorno
Phi[-1, 1:-1] = x[1:-1] * (1 - x[1:-1])  # Phi(y = 1) = x(1-x)

# Graficar la solución
plt.imshow(Phi, extent=[0, L, 0, L], origin='lower', cmap='inferno', interpolation='nearest')
plt.colorbar(label="Φ(x, y)")
plt.title("Solución de la ecuación de Laplace en 2D")
plt.xlabel("x")
plt.ylabel("y")
plt.show()
