# -*- coding: utf-8 -*-
"""
PRÁCTICA MECÁNICA ESTADÍSTICA - 3º Grado en Física, curso 2023/2024
Rocío Ponsoda
Elena Sempere
"""
import numpy as np
import matplotlib.pyplot as plt
import math
import scipy.misc

#####################################################################

# 1. CAMINO ALEATORIO EN 1 DIMENSIÓN
# 1.1. Datos de entrada del programa
N = 80 #total de saltos en cada realización
I = 5000 #iteraciones
L = 2 #longitud del paso a la izq
D = 1 #longitud del paso a la dcha
Pd = 0.3 #probabilidad a la dcha

# 1.2. Función que nos devuelva un array con el desplazamiento en cada paso
m = [] #array donde vamos a ir guardando los resultados
def caminoaleatorio(N,Pd,r,l):
    izq = 0 
    dcha = 0
    for i in range(N):
        paso = np.random.choice([r,l], p = [Pd, 1-Pd])
        if paso == r:
            dcha += 1
        elif paso == l:
            izq += 1
    x = dcha*r - izq*l
    return x #que nos devuelva la posición final dados N pasos

# Ahora repetimos el random walk I veces para sacar la media
for i in range(I):
    m.append(caminoaleatorio(N,Pd,D,L)) #array con el desplazamiento final

# 1.3. Cálculo valores medios
media = np.mean(m)
desv_est = np.std(m)
print("CASO UNIDIMENSIONAL")
print("La media de la distribución es:", media)
print("La desviación estándar de la distribución es:", desv_est)

# 1.4. Graficamos el histograma
plt.figure()
plt.title("Camino aleatorio en una dimensión")
plt.hist(m,bins=10,density=True)
plt.show()

# 1.5. Comparación con Gaussiana
def Gaussiana(x,mu,sigma):
    return (1/(sigma*(2*np.pi)**0.5))*np.exp((-(x-mu)**2)/(2*sigma**2))
x = np.linspace(media-3*desv_est,media+3*desv_est)

plt.figure()
plt.title("Comparación con la distribución Gaussiana")
plt.hist(m,bins=10,density=True, label='Histograma normalizado')
plt.plot(x,Gaussiana(x,media,desv_est),label='Distribución Gaussiana')
plt.legend(loc='best')
plt.show()

# 1.6. Comparación con Poisson: asumir que solo damos pasos a la derecha
N = 100
P_poison = 0.1 #Poisson cuando p << 1
lmd=P_poison*N
xp=np.arange(0,N,1)
n = []
for i in range(I):
    n.append(caminoaleatorio(N,P_poison,D,0))


def poisson(x,lmd):
    return((lmd**x)*np.exp(-lmd))/scipy.special.factorial(x)

plt.figure()
plt.hist(n,bins=10,density=True, label='Histograma normalizado')
plt.plot(xp,poisson(xp, lmd), label='Distribución de Poisson')
plt.title('Comparación con la distribución de Poisson')
plt.legend(loc='best')
plt.xlim(-5,25)
plt.show()


median = np.mean(n)
desv_est_n = np.std(n)
print("\nCASO POISSON")
print("La media de la distribución es:", median)
print("La desviación estándar de la distribución es:", desv_est_n)

       
########################################################################
# 2. CAMINO ALEATORIO EN 2 DIMENSIONES
# 2.1. Datos de entrada del programa
N = 50 #total de saltos en cada realización
I = 5000 #iteraciones
L = 2 #longitud del paso a la izq
R = 1 #longitud del paso a la dcha
U = 2 #longitud del paso arriba
D = 1 #longitud del paso abajo
p1 = 0.25
q1 = 0.25
p2 = 0.2
q2 = 0.3
prob = [p1,q1,p2,q2]

# 2.2. Función que nos devuelva un array con el desplazamiento en cada paso
l = [] #pasos derecha, izq
a = [] #pasos arriba, abajo
c = [] #módulo desplazamiento final
def caminoaleatorio2(N,prob,r,l,u,d):
    izq = 0 
    dcha = 0
    arriba = 0  
    abajo = 0
    for i in range(N):
        paso = np.random.choice(['r','l','u','d'], p = prob)
        if paso == 'r':
            dcha += 1
        elif paso == 'l':
            izq += 1
        elif paso == 'u':
            arriba += 1
        elif paso == 'd':
            abajo += 1
    x = dcha*r - izq*l
    y = arriba*u - abajo*d
    return x,y #que nos devuelva la posición final dados N pasos

for i in range(I):
    l.append(caminoaleatorio2(N,prob,R,L,U,D)[0])
    a.append(caminoaleatorio2(N,prob,R,L,U,D)[1])
    c.append(np.sqrt(l[i]**2+a[i]**2))
    
media2d = np.mean(c)
desv_est_2d = np.std(c)

plt.figure()
plt.title('Posición final tras dar N pasos')
plt.plot(l,a,'o')
plt.grid()
plt.show()
           
plt.figure()
plt.title('Histograma de la posición final tras dar N pasos') 
plt.hist2d(l,a,bins=10,density=True)
plt.show()

plt.figure()
plt.title('Histograma del módulo de la posición tras dar N pasos')
plt.hist(c,bins=10,density=True, label='Histograma normalizado')
plt.show()

z = np.linspace(media2d-3*desv_est_2d,media2d+3*desv_est_2d)

plt.figure()
plt.title("Comparación con la distribución Gaussiana")
plt.hist(c,bins=10,density=True, label='Histograma normalizado')
plt.plot(z,Gaussiana(z,media2d,desv_est_2d),label='Distribución Gaussiana')
plt.legend(loc='best')
plt.show()
    


