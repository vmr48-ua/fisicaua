# -*- coding: utf-8 -*-
"""
Created on Wed Dec 13 08:15:22 2023

@author: sempe
"""

'''
PRACTICA 2 MECANICA ESTADISTICA
'''
import numpy as np
import matplotlib.pyplot as plt

J = 1.
H = 0.
N = 30

I = 2000
beta = 10.

#en vez de hacerlo NxN hay que hacerlo con N+2  (¡modulos!!! np.mod)
malla = np.zeros([N+2,N+2])


for i in range (1,N+1):
    for j in range (1,N+1):
        malla[i][j] =np.random.choice([1,-1],p=[0.5,0.5])
        
malla[0,:] = malla[N,:] 
malla[:,0] = malla[:,N]
malla[N+1,:] = malla[1,:]
malla[:,N+1]  = malla[:,1]       


#Mapwado de spines
plt.figure()
plt.imshow(malla)
plt.title('Modelo de Ising en 2D')


#Momento magnético
def mum(malla):
    return np.sum(malla)

def Energia(malla):
    E = 0
    for i in range (1,N+1):
        for j in range (1,N+1):
            E += -(J/2)*malla[i][j]*(malla[i+1][j]+malla[i-1][j]+malla[i][j+1]+malla[i][j-1]) - malla[i][j]*H

    return E

#Método de Montecarlo
E = []
mu = []

for k in range(I):
    
    Ei = Energia(malla)
    mui = mum(malla)

    ir = int(np.random.choice(np.linspace(1,N+1)))
    jr = int(np.random.choice(np.linspace(1,N+1)))

    malla[ir][jr] = -1*malla[ir][jr]

 
    Delta = Energia(malla)-Ei


    probn = 1/(1+np.exp(beta*Delta))
    probv = 1 - probn


    nrand = np.random.choice(np.linspace(0,1,1000))

    if nrand <= probn:
        E.append(Energia(malla))
        mu.append(mum(malla))
    else:
        malla[ir][jr] = -1*malla[ir][jr]
        E.append(Ei)
        mu.append(mui)    
    
    malla[0,:] = malla[N,:] 
    malla[:,0] = malla[:,N]
    malla[N+1,:] = malla[1,:]
    malla[:,N+1]  = malla[:,1]



    
#Graficamos 

plt.figure()
xplot2 = np.linspace(0,len(mu),len(mu))
plt.plot(xplot2,mu,label='M')
plt.title('Magnetización')
plt.xlabel('Iteraciones')
plt.ylabel('M (unidades de J)')
plt.legend()

plt.figure()
xplot = np.linspace(0,len(E),len(E))
plt.title('Energía')
plt.xlabel('Iteraciones')
plt.ylabel('E (unidades de J)')
plt.plot(xplot,E,label='E')
plt.legend()

plt.figure()
xplot = np.linspace(0,len(E),len(E))
plt.plot(xplot,E,label='E')
xplot2 = np.linspace(0,len(mu),len(mu))
plt.plot(xplot2,mu,label='mu')
plt.title('Evolución de la Energia y de mu')
plt.legend()
plt.show()





#EJERCICIO

#coger los valores de energia cuando se estabilizan en el metodo de montecarlo
#no se que de la temperatara
# Dispersión de la energía
'''
dispersion_energia = np.var(E)

# Calor específico
kb = 1.0  # Boltzmann
calor_especifico = dispersion_energia / (kb * beta ** 2)
plt.figure()
plt.plot(E, label='Energía')
plt.title('Variación de la Energía con el Calor Específico')
plt.xlabel('Iteraciones')
plt.ylabel('Energía (unidades de J)')
plt.axhline(y=calor_especifico, color='r', linestyle='--', label='Calor Específico')
plt.legend()
plt.show()
'''