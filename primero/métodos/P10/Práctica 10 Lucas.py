# -*- coding: utf-8 -*-
"""
Created on Wed Apr 12 12:34:04 2023

@author: lucas
"""

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
import scipy.linalg as la

def busqueda_incremental(f,a,b,n): # Nos devuelve los intervalos donde hay solucion
    # f: funcion que determina la ecuación
    # a: extremo inferior del intervalo
    # b: extremo superior del intervalo
    # n: número de subintervalos
    extremos=np.linspace(a,b,n+1)
    intervalos=np.zeros((n,2))
    lista=[]
    for i in range(n):
        intervalos[i,0]=extremos[i]
        intervalos[i,1]=extremos[i+1]
        if f(extremos[i])*f(extremos[i+1])<=0:
            lista.append(i)
    return intervalos[lista,::]


#Ejercicio 1

def biseccion(f,a,b,tol):
    a0 = a
    b0 = b
    p0 = (a0+b0)/2
    n = int(np.log2(np.abs((b-a)/tol))) - 1
    for i in range(n):       
        if f(a0)*f(p0) < 0:
            b0 = p0
            p0 = (a0+b0)/2
            
        elif f(a0)*f(p0) > 0:
            a0 = p0
            p0 = (a0+b0)/2
            
        elif f(p0) == 0:
            return p0                              
    return p0, n
    
def f(x):
    return np.exp(-x/10)*np.cos(x)

biseccion(f,1.5,1.8,10**(-4))
    
x = np.linspace(1.571,1.58,150)
plt.plot(x,f(x),x,x*0)
plt.plot(1.572656,f(1.572656),"o")
plt.show()


#Ejercicio 2

def newton_5ptos(f,p0,tol,maxiter): #p0 es el punto medio del intervalo
    def dy_cinco(f,x0,h):
        return (f(x0-2*h)-8*f(x0-h)+8*f(x0+h)-f(x0+2*h))/(12*h)
    error = tol +1
    p1 = p0 - (f(p0))/(dy_cinco(f,p0,10**(-2)))
    cont = 1
    while error > tol and maxiter > cont:
        p0 = p1
        p1 = p0 - (f(p0))/(dy_cinco(f,p0,10**(-2)))
        error = abs(p1 - p0)
        cont += 1
    return p1, cont

busqueda_incremental(f, 0, 15, 50)

newton_5ptos(f,(1.8 + 1.5)/2,10**(-4),50)
    

#Ejercicio 3

def secante(f,p0,p1,tol,maxiter):
    cont = 1
    p2 = p1 - (f(p1)*(p1-p0))/(f(p1)-f(p0))
    
    while abs(p2 - p1) > tol and maxiter > cont:
        cont += 1
        p0 = p1
        p1 = p2
        p2 = p1 - (f(p1)*(p1-p0))/(f(p1)-f(p0))

    return p2, cont
    
secante(f,1.5,1.8,10**(-4),50)
    
    
def regula_fasi(f,p0,p1,tol,maxiter):
    cont = 1
    p2 = p1 - (f(p1)*(p1-p0))/(f(p1)-f(p0))
    p3 = p2 - (f(p2)*(p2-p1))/(f(p2)-f(p1))
    while abs(p2 - p1) > tol and maxiter > cont:
        cont += 1
        p0 = p1
        p1 = p2
        p2 = p3
        if f(p2)*f(p1) < 0:
            p3 = p2 - (f(p2)*(p2-p1))/(f(p2)-f(p1))
        else:
            p3 = p2 - (f(p2)*(p2-p0))/(f(p2)-f(p0))
    return p2, cont

regula_fasi(f,1.5,1.8,10**(-4),50)


#Ejercicio 4

def distancia(t):
    return la.norm((2*np.cos(t)-2,np.sin(t)-1))

distancia = np.vectorize(distancia)

busqueda_incremental(distancia,0,2,50)
#Vemos que no nos da ningún intervalo, ya que no corta nunca

x = np.linspace(0,2,100)
plt.plot(x,distancia(x)) 
plt.show()  
    
biseccion(distancia,0,2,50)

"""COMO NO CORTA EN NINGUN MOMENTO NO SABEMOS COMO
APLICAR LAS FUNCIONES ANTERIORES PARA ESTE CASO""" 
    

 #Ejercicio 5
 
def punto_fijo_sist(G,p0,tol,maxiter):
    p1 = G(p0)
    n = 1
    while la.norm(np.array(p0)-np.array(p1))>=tol and n < maxiter:
        p0,p1 = p1,G(p1)
        n += 1
    return p1,n


#Ejercicio 6
def g(w):
    x,y = w
    return np.array([-0.5*x**2+2*x+0.5*y-0.25, -0.125*x**2-0.5*y**2+y+0.5])

punto_fijo_sist(g, (2,0), 10**-4, 50)
punto_fijo_sist(g, (0,1), 10**-4, 50)



















    
    
    
    