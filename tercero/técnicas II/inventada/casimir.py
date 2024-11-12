import numpy as np
import matplotlib.pyplot as plt

# Constantes físicas
hbar = 1.054571817e-34  # Constante de Planck reducida (J·s)
c = 3e8  # Velocidad de la luz (m/s)
pi = np.pi

# Función para calcular la fuerza de Casimir entre dos placas
def fuerza_casimir(area, distancia):
    """
    Calcula la fuerza de Casimir entre dos placas paralelas.\n    
    :math:`F = \\frac{\\pi^2 \\hbar c A}{240 d^4}`

    Input:
    - Área de las placas (m^2)
    - Separación entre las placas (m)
    
    Output:
    - Fuerza de Casimir (N)
    """
    if distancia <= 0:
        raise Exception("La distancia debe ser mayor que cero.")
    return (pi**2 * hbar * c * area) / (240 * distancia**4)

area = 1e-4  # Área de las placas en m^2 (por ejemplo, 1 cm^2)
distancias = np.linspace(1e-9, 1e-6, 1000)  # Distancias desde 1 nm hasta 1 μm

# Cálculo de la fuerza de Casimir para diferentes distancias
fuerzas = [fuerza_casimir(area, d) for d in distancias]

# Gráfica de la fuerza de Casimir en función de la distancia
plt.figure(figsize=(10, 6))
plt.plot(distancias * 1e9, fuerzas, label="Fuerza de Casimir")
plt.xlabel("Distancia (nm)")
plt.ylabel("Fuerza (N)")
plt.title("Fuerza de Casimir entre dos placas en función de la distancia")
plt.yscale('log')  # Escala logarítmica para la fuerza
plt.grid(True, which="both", linestyle="--", linewidth=0.5)
plt.legend()
plt.show()
