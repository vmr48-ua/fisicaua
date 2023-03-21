# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 8                                                      """

# LIBRERÍAS

import numpy as np
import scipy.linalg as la

###################################################

# En la librería scipy.linalg disponemos de la función solve:

A = np.array([[1,2,-3],[0,1,-2],[0,0,1]],dtype=float)
b = np.array([-2,0,-4.]) 

la.solve(A,b)


# MÉTODOS DIRECTOS

# Solución para sistemas lineales cuadrados de la forma Ax = b cuya 
# matriz del sistema A es diagonal.

def solucionD(A,b):
    n = len(A)
    x = np.zeros(n)
    for i in range(n-1,-1,-1):
        x[i] = b[i]/A[i,i]
    return x

A = np.array([[2,0,0],[0,3,0],[0,0,2]],dtype=float)
b = np.array([3,1,-4.]) 

solucionD(A,b)
la.solve(A,b)

# En el ejercicio 1 deberás implementar solucionU para obtener las soluciones
# de un sistema triangular con matriz de coeficientes triangular superior 
# (página 9 de la presentación de teoría).

# Las operaciones elementales (intercambio de filas, suma de una fila más otra
# multiplicada por un escalar, producto de una fila por un escalar) permiten 
# transformar los sistemas de ecuaciones en otros equivalentes que serán
# diagonales o triangulares.
    
# INPUTS: A es una array de orden n (o nxm), i,j están en {0,1,2,...,n-1}
#         y c es un escalar. 

# Fij -> Intercambio de las filas i+1 y j+1.

def cambio_filas(A,i,j):
    subs = np.copy(A[i])
    A[i] = A[j]
    A[j] = subs
    return A

# Fij(c) -> A la fila i+1 se le suma la fila j+1 multiplicada por el escalar c.
    
def suma_filas(A,i,j,c):
    subs = np.copy(A[i])
    A[i] = subs + c*A[j]
    return A

# Fi(c) -> Se multiplica la fila i+1 por el escalar c.
    
def prod_fila(A,i,c):
    subs = np.copy(A[i])
    A[i] = c*subs
    return A

# Ejemplos:
    
A = np.identity(3)
cambio_filas(A,0,2)
suma_filas(A,2,0,3./2)
prod_fila(A,1,1./2)

A = np.array([[1,0,2],[-1,2,1],[3,2,-2]],dtype=float)
cambio_filas(A,0,2)
suma_filas(A,2,0,3./2)
prod_fila(A,1,1/2)

b = np.array([1.,0,2])
cambio_filas(b,1,2)
suma_filas(b,0,1,3./2)
prod_fila(b,0,1./2)

# Observa cómo las matrices han cambiado al aplicarles transformaciones elementales.
# Si fuera necesario, podemos hacer una copia de A antes de aplicar
# las transformaciones elementales:
    
Acopy = np.copy(A)

# ¿Cómo utilizar las transformaciones elementales para convertir
# nuestro sistema en otro equivalente que sea triangular superior? Debemos hacer
# ceros bajo la diagonal principal escogiendo previamente un pivote (método de 
# Gauss). Supongamos, además, que deseamos aplicar pivoteo parcial.

A = np.array([[1,2,-3],[0,1,-2],[3,2,1]],dtype=float)
b = np.array([-2,0,-4.]) 

la.solve(A,b)

# Primera columna

k0 = np.argmax(abs(A[0::,0])) # Devuelve el índice del máximo de los valores absolutos.
cambio_filas(b,0,k0)
cambio_filas(A,0,k0)

suma_filas(b,2,0,-A[2,0]/A[0,0]) 
suma_filas(A,2,0,-A[2,0]/A[0,0]) 

# Segunda columna

k1 = np.argmax(abs(A[1::,1])) + 1 # Cuidado con ese + 1.
cambio_filas(b,1,k1)
cambio_filas(A,1,k1)

suma_filas(b,2,1,-A[2,1]/A[1,1]) 
suma_filas(A,2,1,-A[2,1]/A[1,1]) 

# Una vez obtenida la "forma" triangular superior de la matriz podemos usar 
# solucionU (implementada en el ejercicio 1) para resolver el sistema (que aquí 
# resolvemos con solve):
    
la.solve(A,b) 

# solucionU(A,b)

# Obviamente, podemos usar cualquiera de los métodos de pivoteo que hemos visto
# en teoría: parcial, parcial escalado o total. Además, no debe ser muy difícil
# implementar el método de Gauss como una función. Esto es lo que se pide en el 
# ejercicio 2 (también se podría implementar Gauss-Jordan con cualquier pivoteo).

# Además de la resolución de sistemas de ecuaciones lineales, dos aplicaciones 
# interesantes de las transformaciones elementales son:
# el cálculo de la inversa y la descomposición LU de la matriz.

# 1) Cálculo de la inversa (ejercicio 3): La matriz inversa se obtiene al aplicar las mismas
# transformaciones que sirven para transformar la matriz A en la matriz identidad.
# En scipy.linalg está la función inv.

A = np.array([[3,2],[1,2]],dtype=float)

Ainv = la.inv(A)
np.dot(A,Ainv)
np.dot(Ainv,A)

# 2) Cálculo de la descomposición LU (ejercicio 4):  

A = np.array([[3,1,1],[1,3,1],[0,2,1]],dtype=float)

# P es una matriz de permutaciones (eventualmente necesaria)
# L es una matriz triangular inferior con unos en la diagonal principal
# U es una matriz triangular superior

P, L, U = la.lu(A)
np.dot(L,U)

# Hagámoslo con transformaciones elementales. En primer lugar, hacemos las 
# transformaciones sobre A para obtener U:
    
U1 = np.array([[3,1,1],[1,3,1],[0,2,1]],dtype=float)
    
suma_filas(U1,1,0,-1/3) 

p1 = U1[2,1]/U1[1,1]
suma_filas(U1,2,1,-p1)

# En segundo lugar, aplicamos las inversas de dichas transformaciones a la 
# identidad:

L1 = np.identity(3)

suma_filas(L1,2,1,p1) 
suma_filas(L1,1,0,1./3) 





