import numpy as np
from numpy import sqrt
import matplotlib.pyplot as plt
import scipy.special

'''
MECÁNICA ESTADÍSTICA, PRÁCTICA I
CAMINO ALEATORIO EN UNA DIMENSIÓN

Víctor Mira Ramírez
74528754Z
vmr48@alu.ua.es
'''

def random_walk(I: int, N: int, P_r: float, L: int, R: int) -> list:
    x_f = []
    dcha = []# Posición final
    for i in range(I):
        print(f'Progreso: {int(i/I*100)}%', end='\r') # Porcentaje de completado
        posicion, contador = 0, 0
        for _ in range(N):
            # posicion += np.random.choice([-L, R], p=[1-P_r, P_r])
            if np.random.rand() < P_r:  # Salto derecho
                posicion += R
                contador += 1
            else:                       # Salto izquierdo
                posicion -= L
        x_f.append(posicion)
        dcha.append(contador)
    return x_f, dcha

def gaussiana(x, media, desviacion) -> None:
    return np.exp(-(x-media)**2/(2*desviacion**2)) / np.sqrt(2*np.pi*desviacion**2)

def poisson(x, N, P_r) -> None:
    return np.exp(-N*P_r) * (N*P_r)**(x) / scipy.special.factorial(x)

def main() -> None:
    I = int(5e4)   # Iteraciones
    N = 125        # Saltos por iteración
    P_r = 0.5      # Probabilidad de salto a la derecha
    L = 1          # Tamaño de paso izquierdo
    R = 1          # Tamaño de paso derecho
    
    walk, poi = random_walk(I,N,P_r,L,R)
    
    plt.figure()
    media = np.mean(walk)
    desviacion = np.std(walk)
    xo = np.linspace(media - 4*desviacion, media + 4*desviacion,1000)
    plt.plot(xo, gaussiana(xo, media, desviacion), label='Gaussiana')
    plt.hist(walk, bins=int(len(set(walk))), density=True, alpha=0.6, color='lime')
    plt.legend()
    
    media_teorica = N * (P_r * R - (1 - P_r) * L)
    desviacion_teorica = sqrt(N*P_r*R**2 + N*(1-P_r)*L**2)
    print(f'Media: {media}\nDesviación: {desviacion}\n')
    print(f'Media teórica: {media_teorica} \nDesviación teórica: {desviacion_teorica}')
    
    plt.figure()
    media = np.mean(poi)
    desviacion = np.std(poi)
    xo = np.linspace(media - 4*desviacion, media + 4*desviacion,1000)
    plt.plot(xo, poisson(xo, N, P_r), label='Poisson')
    plt.hist(poi, bins=int(len(set(poi))), density=True, alpha=0.6, color='lime')
    plt.legend()
    
    plt.show()

if __name__ == '__main__':
    main()