import numpy as np
import matplotlib.pyplot as plt
import scipy.special
from math import sqrt

def random_walk(I: int, N: int, P_r: float, L: int, R: int) -> tuple:
    x_f = []
    dcha = []  # Contador de saltos hacia la derecha
    for i in range(I):
        print(f'Progreso: {int(i/I*100)}%', end='\r')  # Porcentaje de completado
        posicion, contador = 0, 0
        for _ in range(N):
            if np.random.rand() < P_r:  # Salto derecho
                posicion += R
                contador += 1
            else:                       # Salto izquierdo
                posicion -= L
        x_f.append(posicion)
        dcha.append(contador)
    return x_f, dcha

def gaussiana(x, media, desviacion) -> float:
    return np.exp(-(x-media)**2/(2*desviacion**2)) / np.sqrt(2*np.pi*desviacion**2)

def poisson(k, N, P_r) -> float:
    """Calcula la probabilidad de k saltos hacia la derecha en un total de N saltos."""
    lambd = N * P_r  # Parámetro lambda de Poisson
    return np.exp(-lambd) * (lambd ** k) / scipy.special.factorial(k)

def main() -> None:
    I = int(5e4)   # Iteraciones
    N = 120        # Saltos por iteración
    P_r = 0.5      # Probabilidad de salto a la derecha
    L = 1          # Tamaño de paso izquierdo
    R = 1          # Tamaño de paso derecho

    walk, poi = random_walk(I, N, P_r, L, R)

    # Gráfica Gaussiana
    plt.figure()
    media = np.mean(walk)
    desviacion = np.std(walk)
    xo = np.linspace(media - 4*desviacion, media + 4*desviacion, 1000)
    plt.plot(xo, gaussiana(xo, media, desviacion), label='Gaussiana')
    plt.hist(walk, bins=int(len(set(walk))), density=True, alpha=0.6, color='lime')
    plt.legend()

    media_teorica = N * (P_r * R - (1 - P_r) * L)
    desviacion_teorica = sqrt(N * P_r * R**2 + N * (1 - P_r) * L**2)
    print(f'Media: {media}\nDesviación: {desviacion}\n')
    print(f'Media teórica: {media_teorica} \nDesviación teórica: {desviacion_teorica}')

    # Gráfica Poisson
    plt.figure()
    media_poi = np.mean(poi)
    desviacion_poi = np.std(poi)
    # Valores discretos de k para la Poisson
    k_vals = np.arange(0, max(poi) + 1)
    poisson_probs = [poisson(k, N, P_r) for k in k_vals]

    plt.plot(k_vals, poisson_probs, 'o-', label='Poisson')
    plt.hist(poi, bins=max(poi)-min(poi), density=True, alpha=0.6, color='lime', align='left')
    plt.legend()

    plt.show()

if __name__ == '__main__':
    main()
