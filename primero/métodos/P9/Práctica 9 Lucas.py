# -*- coding: utf-8 -*-
"""
Created on Fri Mar 24 12:07:49 2023

@author: lucas
"""

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
from scipy.interpolate import CubicSpline
import scipy.linalg as la


#Ejercicio 1

A = np.array([[2,1,0],[2,5,-1],[0,-1,3]], dtype=float)
b = np.array([0,2,0], dtype=float)

D = np.diag(np.diag(A)) 
L = -np.tril(A-D)
U = -np.triu(A-D)

#Jacobi  M = D y N = L + U

Mj = np.copy(D)
Nj = L + U

B = np.dot(la.inv(Mj),Nj)
c = np.dot(la.inv(Mj),b)

resp = max(abs(la.eig(B)[0])) #Si que converge ya que resp < 1
x0 = np.array([0,0,0],dtype=float)

for i in range(3):
    x1 = np.dot(B,x0) + c
    la.norm(x1-x0,2)
    x0 = x1.copy()

#Gauss-Seindel M = D-L , N = U
Mg = D - L
Ng = np.copy(U)

B = np.dot(la.inv(Mg),Ng)
c = np.dot(la.inv(Mg),b)

resp = max(abs(la.eig(B)[0])) #Si que converge ya que resp < 1
x0 = np.array([0,0,0],dtype=float)

for i in range(3):
    x1 = np.dot(B,x0) + c
    la.norm(x1-x0,2)
    x0 = x1.copy()

#Ejercicio 2

def jacobi(A,b,x0,norma,error,k): #k: numero maximo de iteraciones a realizar, error: tolerancia
    D = np.diag(np.diag(A)) 
    L = -np.tril(A-D)
    U = -np.triu(A-D)
    M = np.copy(D)
    N = L + U
    B = np.dot(la.inv(M),N)
    c = np.dot(la.inv(M),b)
    resp = max(abs(la.eig(B)[0]))
    if resp < 1:
        cont = 0
        tolerancia = error +1
        while cont < k and tolerancia > error:
            x1 = np.dot(B,x0) + c
            tolerancia = la.norm(x1-x0,norma)
            x0 = x1.copy()
            cont += 1      
    else:
        print("El método no es convergente")
    return x0, cont


def gauss_seindel(A,b,x0,norma,error,k):
    D = np.diag(np.diag(A)) 
    L = -np.tril(A-D)
    U = -np.triu(A-D)
    M = D - L
    N = np.copy(U)
    B = np.dot(la.inv(M),N)
    c = np.dot(la.inv(M),b)
    resp = max(abs(la.eig(B)[0]))
    if resp < 1:
        cont = 0
        tolerancia = error + 1
        while cont < k and tolerancia > error:
            x1 = np.dot(B,x0) + c
            tolerancia = la.norm(x1-x0,norma)
            x0 = x1.copy()
            cont += 1      
    else:
        print("El método no es convergente")
    return x0, cont

#Ejercicio 3

#a) 
A = np.array([[4,3,0],[3,4,-1],[0,-1,4]],dtype=float)
b = np.array([24,30,-24])
x0 = np.array([0,0,0])

jacobi(A,b,x0,np.inf,10**(-5),100)
gauss_seindel(A,b,x0,np.inf,10**(-5),100)
la.solve(A,b)

#b)
A = np.array([[1,2,-2],[1,0,1],[2,2,1]])
b = np.array([1,3,5])

jacobi(A,b,x0,np.inf,10**(-5),100)
gauss_seindel(A,b,x0,np.inf,10**(-5),100)
la.solve(A,b)
#Nos da Singular matrix, ya que A tiene 0 en la diagonal, por lo que no es invertible
#hay que cambiar las filas para así poder aplicar el método


#Ejercicio 4

def relax(A,b,w,x0,norma,error,k):
    D = np.diag(np.diag(A))
    L = -np.tril(A-D)
    U = -np.triu(A-D)
    B = np.dot(la.inv((D-w*L)),((1-w)*D + w*U))   
    c = np.dot(la.inv((D-w*L)),w*b)
    resp = max(abs(la.eig(B)[0]))
    if resp < 1:
        cont = 0
        tolerancia = error + 1
        while cont < k and tolerancia > error:
            x1 = np.dot(B,x0) + c
            tolerancia = la.norm(x1-x0,norma)
            x0 = x1.copy()
            cont += 1      
    else:
        print("El método no es convergente")
    return x0, cont, resp


#Ejercico 5

#a)
x0 = np.zeros(3)

A5 = np.array([[4,3,0],[3,4,-1],[0,-1,4]], dtype = float)
b5 = np.array([24,30,-24], dtype = float)

relax(A5,b5,1.25,x0,np.inf,10**(-5),100)
la.solve(A5,b5)

#b)
x0 = np.zeros(3)
A5 = np.array([[1,2,-2],[1,1,1],[2,2,1]], dtype = float)
b5 = np.array([1,3,5], dtype = float)

relax(A5,b5,0.25,x0,np.inf,10**(-5),100)
la.solve(A5,b5)

































