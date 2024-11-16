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
T = 0.1
k = 1
beta = 1/(k*T)
H=0


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
def plot_spins(M):
    plt.figure()
    colors = {1: "red", -1: "blue"}
    for i in range(0,Z+2):
        for j in range(0,Z+2):
            plt.quiver(i, j, 0, M[i][j], pivot="middle", color=colors[M[i][j]])
    plt.xticks(range(-1,Z+1))
    plt.yticks(range(-1,Z+1))
    plt.gca().set_aspect("equal")
    plt.grid()
    plt.show
    
#Variamos el valor de un spin aleatorio
def SpinChange(M):
    A = np.copy(M)
    x=np.random.randint(1,Z+1)
    y=np.random.randint(1,Z+1)
    A[x][y]=A[x][y]*(-1)
    return A
    
#Definimos la función probabilidad de cambiar de estado
def ProbabilityChange(dE):
    return 1/(1+np.exp(beta*dE))

#Sorteamos con igual propiedad el valor de los spines
np.random.choice([-1,1], p=(0.5,0.5))

#Definimos la matriz (red de spines 2D) y las C.C.
Z=30
M=np.array([[np.random.choice([-1,1], p=(0.5,0.5)) for j in range (Z+2)] for i in range(Z+2)])
M[0]=M[Z]
M[Z+1]=M[1]
M[:,0]=M[:,Z]
M[:,Z+1]=M[:,1]

#Magnetiación total
mt = np.sum(M[1:Z,1:Z])

#Definimos la matriz energía
E=np.zeros((Z+2,Z+2))

def E (M):
    E=np.zeros((Z+2,Z+2))
    for i in range(1,Z+1):
        for j in range (1,Z+1):
            E[i][j]=-0.5*M[i][j]*(M[i+1,j]+M[i-1,j]+M[i,j+1]+M[i,j-1])
    return E

En=E(M)

#Energía total
et=np.sum(En[1:Z+1,1:Z+1])
ENERGIA=[et]
MAGN=[mt]

#Creamos bucle
    
MLIST = [M]
iterac=0
for k in range(0,5000):
    NM=SpinChange(M)
    nmt = np.sum(NM[0:Z+1,0:Z+1])
    NEn = E(NM)
    net = np.sum(NEn[1:Z+1,1:Z+1])
    dE = net-et
    pnew = ProbabilityChange(dE)
    
    if np.random.choice([True,False],p=(pnew,1-pnew)):
        ENERGIA += [net]
        MAGN += [nmt]
        M = NM
        iterac+=1
        et = net
        if k/2 == k//2:
            MLIST.append(M)
            
#Animación
fig = plt.figure()
im = plt.imshow(MLIST[0], aspect="auto")

def init():
    im.set_data(np.zeros(Z+2,Z+2))
    
def updatefig(i):
    im.set_data(MLIST[i])
    return im
    
anim = animation.FuncAnimation(fig, updatefig, frames=len(MLIST),
                               interval=10,repeat_delay = 1000)

plt.show()