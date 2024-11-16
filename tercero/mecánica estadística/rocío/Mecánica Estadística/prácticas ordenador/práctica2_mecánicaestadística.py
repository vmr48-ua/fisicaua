# -*- coding: utf-8 -*-
"""
PRÁCTICA 2 MECÁNICA ESTADÍSTICA
Rocío Ponsoda Orgilés
Elena Sempere Gonzálvez
"""
import numpy as np
import matplotlib.pyplot as plt
import math
import scipy.misc
import random
from matplotlib import animation

# 1. GENERAMOS UNA RED NxN DE ESPINES ALEATORIOS
# 1.1. Datos de la simulación
J = 1 #energía de interacción entre vecinos
P = 1/2 #probabilidad de spin +1 o -1
N = 30 #dimensiones de la malla
I = 2000 #número iteraciones
T = 3
k = 1
beta = 1/(k*T)

# 1.2. Malla con espines +1 ó -1 aleatoriamente (y su gráfica)
S = np.zeros([N,N]) #crea malla NxN llena de ceros
for i in range(N):
    for j in range(N):
        S[i,j] = np.random.choice([1,-1], p = [P,1-P])
So = np.copy(S)
        
# 1.3. Representación del mallado de spines
plt.figure()
plt.title('Mallado de spines en 2D - inicial')
plt.imshow(S)

# 2. MÉTODO MONTECARLO
# 2.1. Energía de la red
def Energia(S):
    Eo = 0
    for i in range(N):
        for j in range(N):
            Eo += S[i,j]*(S[(i+1)%N,j%N]+S[(i-1)%N,j%N]+S[i%N,(j+1)%N]+S[i%N,(j-1)%N])
    return -0.5*J*Eo

# 2.2. Momento magnético total de la red (suma de espines)
def mu(S): 
    return np.sum(S)

# 2.3. Método Montecarlo: hacemos los cambios y vemos si se acepta o no
def montecarlo(S,I,beta):
    E = []
    M = []
    for i in range(0,I):
        Eo = Energia(S)
        muo = mu(S)
        
        # Cambiamos uno de los espines
        A = np.copy(S)
        k = int(np.random.choice(np.linspace(0,N-1)))
        l = int(np.random.choice(np.linspace(0,N-1)))
        A[k,l] = -A[k,l]
        En = Energia(A)
        mun = mu(A)
        
        # ¿Aceptamos el cambio?
        dE = En - Eo
        pnew = 1/(1+np.exp(beta*dE))
        pold = 1 - pnew
        # Con esta probabilidad vamos a dar un número que nos permita tomar la decisión
        nrand = np.random.choice([1,2], p = (pnew,1-pnew))
        if nrand == 1: # Cambiamos de estado
            S[k,l] = -S[k,l]
            E.append(En)
            M.append(mun)
        else: # Nos mantenemos
            E.append(Eo)
            M.append(muo)
    return E, M, S

Ener = montecarlo(S,I,beta)[0]
Magnet = montecarlo(S,I,beta)[1]
Matriz = montecarlo(S,I,beta)[2]
MLIST = [Matriz]
plt.figure()
plt.title('Mallado de spines en 2D - final')
plt.imshow(montecarlo(S,I,beta)[2])

# 2.5. Gráficas
plt.figure()
xplot = np.linspace(0,len(Ener),len(Ener))
plt.title('Energía')
plt.xlabel('Iteraciones')
plt.ylabel('E (unidades de J)')
plt.plot(xplot,Ener,label='E')
plt.grid()
plt.legend()

plt.figure()
plt.title('Momento magnético')
plt.xlabel('Iteraciones')
plt.ylabel('M (unidades de J)')
plt.plot(xplot,Magnet,label='M')
plt.grid()
plt.legend()     

plt.figure()
plt.plot(xplot,Ener,label='E')
plt.plot(xplot,Magnet,label='$\mu $')
plt.title('Evolución de la Energía y de $\mu$')
plt.legend()
plt.grid()
plt.show()

'''
# 3. CALOR ESPECÍFICO: hay que dar la energía en función de la temperatura
T = np.linspace(0.5,3,20)
Et = [] #array de energías medias
Et2 = [] #media del cuadrado

for temperatura in T:
    beta = 1/(k*temperatura)
    E = montecarlo(S,I,beta)[0]
    mediaE = np.mean(E[100:])
    E2 = []
    for e in E:
        E2.append(e**2)
    mediaE2 = np.mean(E2[100:])
    Et.append(mediaE)
    Et2.append(mediaE2)
    
Ed = []
for i in range(len(Et)):
    Ed.append(Et2[i]-Et[i]**2)
 
# 3.1. Relación con el calor específico
Cv = []
for i in range(len(T)):
    Cv.append(Ed[i]/(k*(T[i])**2))

# 3.2. Gráficas
plt.figure()
plt.title('Dispersión de la energía en función de T')
plt.plot(T,Ed,'o')
plt.grid()
plt.xlabel('$T$')
plt.ylabel('$<\Delta E^2 >$')
plt.show()

plt.figure()
plt.title('Calor específico en función de T')
plt.plot(T,Cv,'o')
plt.grid()
plt.xlabel('$T$')
plt.ylabel('$C_v$')
plt.show()


# 4. ANIMACIÓN
fig = plt.figure()
im = plt.imshow(MLIST[0], aspect="auto")
def init():
    im.set_data(np.zeros(N,N))   
def updatefig(i):
    im.set_data(MLIST[i])
    return im  
anim = animation.FuncAnimation(fig, updatefig, frames=len(MLIST),
                               interval=10,repeat_delay = 1000)
plt.show()
plt.figure()
plt.imshow(Matriz)
plt.show()
'''