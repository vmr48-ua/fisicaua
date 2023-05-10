# -*- coding: utf-8 -*-
"""
Created on Tue Mar 21 11:38:14 2023

@author: lucas
"""

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
from scipy.interpolate import CubicSpline
import scipy.linalg as la

#Ejercicio 1

def solucionD(A,b):
    n = len(A)
    x = np.zeros(n)
    for i in range(n-1,-1,-1):
        x[i] = b[i]/A[i,i]
    return x


def solucionU(A,b):
    n = len(A)
    x = np.zeros(n)
    for i in range (n-1,-1,-1):
        suma = 0
        for j in range(i+1,n):
            suma += A[i][j]*x[j]
        x[i] = (b[i]-suma)/A[i,i]
    return x


def solucionL(A,b):
    n = len(A)
    x = np.zeros(n)
    for i in range (0,n):
        suma = 0
        for j in range(0,i):
            suma += A[i][j]*x[j]
        x[i] = (b[i]-suma)/A[i,i]
    return x


A2 = np.array([[6,0,0],[2,3,0],[8,5,6]])
b2 = np.array([1,5,3])
la.solve(A2,b2)
solucionL(A2,b2)

A1 = np.array([[1,2,-3],[0,1,-2],[0,0,4]], dtype=float)
b1 = np.array([-2,0,-4])

solucionU(A1,b1)
la.solve(A1,b1)


#Ejercicio 2

def cambio_filas(A,i,j):
    subs = np.copy(A[i])
    A[i] = A[j]
    A[j] = subs
    return A
def suma_filas(A,i,j,c): #La operacion es: i = i + C*j
    subs = np.copy(A[i])
    A[i] = subs + c*A[j]
    return A
def prod_fila(A,i,c):
    subs = np.copy(A[i])
    A[i] = c*subs
    return A

def gauss_parcial(A,b):
    Ac = np.copy(A)
    bc = np.copy(b)
    n = len(A) 
    for j in range(n-1): #Columnas
        pivote = np.argmax(abs(Ac[j::,j])) + j #Posición donde está el pivote
        cambio_filas(Ac,j,pivote)
        cambio_filas(bc,j,pivote)
        for i in range(j+1,n): #Filas
            if A[i,j] != 0:
                c = -1*(Ac[i,j])/(Ac[j,j])
                suma_filas(Ac,i,j,c)
                suma_filas(bc,i,j,c)
    return solucionU(Ac,bc)


A8 = np.array([[1,2,-1,3],[2,0,2,-1],[-1,1,1,-1],[3,3,-1,2]], dtype=float)
b8 = np.array([-8,13,8,-1],dtype=float)

gauss_parcial(A8,b8)
la.solve(A8,b8)


def gauss_parcial_escalado(A,b):
    Ac = np.copy(A)
    bc = np.copy(b)
    n = len(A)
    for i in range(n-1): #Columnas
        pivote = np.argmax(abs(Ac[i::,i])/max(abs(Ac[i,i::]))) + i
        cambio_filas(Ac,i,pivote)
        cambio_filas(bc,i,pivote)
        for j in range(i+1,n): #Filas
            c = -1*(Ac[j,i])/(Ac[i,i])
            suma_filas(Ac,j,i,c)
            suma_filas(bc,j,i,c)
    return solucionU(Ac,bc)

A2 = np.array([[1,2,-1,3],[2,0,2,-1],[-1,1,1,-1],[3,3,-1,2]], dtype=float)
b2 = np.array([-8,13,8,-1], dtype = float)

gauss_parcial_escalado(A2,b2)
la.solve(A2,b2)



#Ejercicio 3

A3 = np.array([[0,1,2],[1,1,-1],[2,1,0]],dtype = float)

def inversa(A):
    Ac = A.copy()
    n = len(Ac)
    I = np.zeros([n,n])
    for i in range(n):
        I[i,i] = 1
    for i in range(n-1):
        piv = np.argmax(abs(Ac[i::,i]))+i
        cambio_filas(Ac,piv,i)
        cambio_filas(I,piv,i)
        for j in range(i+1,n):
            suma_filas(I,j,i,-Ac[j,i]/Ac[i,i])
            suma_filas(Ac,j,i,-Ac[j,i]/Ac[i,i])
    for i in range(n):
        prod_fila(I,i,1/Ac[i,i])
        prod_fila(Ac,i,1/Ac[i,i])
    for i in range(n-1,0,-1):
        for j in range(i):
            c = -Ac[j,i]
            suma_filas(I,j,i,c)
            suma_filas(Ac,j,i,c)
    return I

invA = inversa(A3)

invA3 = la.inv(A3)
np.dot(invA,A3)


#Ejercicio 4

A4 = np.array([[7,3,-1],[3,8,1],[2,-4,-1]],dtype=float)

A5 = np.array([[1,-2,-2,-3],[3,-9,0,-9],[-1,2,4,7],[-3,-6,26,2]],dtype=float)


def LU(A):
    Ac = np.copy(A)
    n = len(A)
    for i in range(n):
        pivote = np.argmax(abs(Ac[i::,i])) + i
        cambio_filas(Ac,pivote,i)
        for j in range(i+1,n):
            suma_filas(Ac,j,i,-Ac[j,i]/Ac[i,i])
    return np.dot(A,la.inv(Ac)),Ac

LU(A4)
LU(A5)




