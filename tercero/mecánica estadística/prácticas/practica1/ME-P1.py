import numpy as np
from numpy import sqrt, exp, pi
import matplotlib.pyplot as plt
import scipy.special

'''
MECÁNICA ESTADÍSTICA, PRÁCTICA I
CAMINO ALEATORIO EN UNA DIMENSIÓN

Víctor Mira Ramírez
Vera Juan Moll

74528754Z
54379937W

vmr48@alu.ua.es
fvjm1@alu.ua.es
'''

def random_walk(I: int, N: int, P_r: float, L: int, R: int) -> tuple:
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

def gaussiana(x, media, desviacion) -> float:
    return np.exp(-(x-media)**2/(2*desviacion**2)) / np.sqrt(2*np.pi*desviacion**2)

def poisson(x, N, P_r) -> float:
    return np.exp(-N*P_r) * (N*P_r)**(x) / scipy.special.factorial(x)

def main() -> None:
    I = int(3e4)   # Iteraciones
    N = 100        # Saltos por iteración
    P_r = 0.5      # Probabilidad de salto a la derecha
    L = 3          # Tamaño de paso izquierdo
    R = 1          # Tamaño de paso derecho
    
    walk, poi = random_walk(I,N,P_r,L,R)
    
    fig = plt.figure(figsize=(10,5))
    plt.tight_layout()
    ax1 = fig.add_subplot(1,2,1)
    #plt.suptitle(f'Random Walk\n{I} Iteraciones - {N} Saltos por iteración',fontsize=14,weight='bold')
    media = np.mean(walk, dtype=np.float64)
    desviacion = np.std(walk, dtype=np.float64)
    xo = np.linspace(media - 4*desviacion, media + 4*desviacion,1000)
    ax1.plot(xo, gaussiana(xo, media, desviacion), label='Gaussiana')
    ax1.hist(walk, bins=int(len(set(walk))), density=True, alpha=0.4, color='#01F9FF',
            label=f'Paso izquierdo: {L}\nPaso derecho: {R}\nP. de salto derecho: {P_r}')
    ax1.legend(loc='center')
    ax1.annotate(f'Media = {media:.2f}\n$\\sigma$ = {desviacion:.2f}',
            xy=(0.05, 0.95), xycoords='axes fraction',
            fontsize=10, ha='left', va='top')
    
    media_teorica = N * (P_r * R - (1 - P_r) * L)
    desviacion_teorica = sqrt(N*P_r*R**2 + N*(1-P_r)*L**2)
    print(f'Media: {media}\nDesviación: {desviacion}\n')
    print(f'Media teórica: {media_teorica} \nDesviación teórica: {desviacion_teorica}')
    
    ax2 = fig.add_subplot(1,2,2)
    media = np.mean(poi)
    desviacion = np.std(poi)
    xo = np.linspace(media - 4*desviacion, media + 4*desviacion,1000)
    ax2.plot(xo, poisson(xo, N, P_r), label='Poisson')
    ax2.hist(poi, bins=max(poi)-min(poi), density=True, alpha=0.4, color='#01F9FF',
            label=f'Paso izquierdo: {L}\nPaso derecho: {R}\nP. de salto derecho: {P_r}')
    ax2.legend(loc='center')
    ax2.annotate(f'Media = {media:.2f}\n$\\sigma$ = {desviacion:.2f}',
            xy=(0.05, 0.95), xycoords='axes fraction',
            fontsize=10, ha='left', va='top')
    
    plt.show()

if __name__ == '__main__':
    main()