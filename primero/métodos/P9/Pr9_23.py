# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 9                                                      """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
import scipy.linalg as la

###################################################

# MÉTODOS ITERATIVOS

# Los métodos iterativos tratan de obtener la solución de un sistema de ecuaciones 
# construyendo una sucesión de aproximaciones. Estos métodos son del tipo:
# x^(m+1) = Bx^(m)+c, para m = 0,1,2...


A = np.array([[5,1,2],[1,4,1],[2,2,5]],dtype=float)
b = np.array([1,2,2],dtype=float) 
   
D = np.diag(np.diag(A))
L = -np.tril(A-D)
U = -np.triu(A-D)

D-L-U

# En el caso particular de los métodos de Jacobi y Gauss-Seidel, B = M^(-1)N
# y c = M^(-1)b, donde:
    # Jacobi: M = D y N = L+U.
    # Gauss-Seidel: M = D-L y N = U.
# Los valores de B y c se obtienen usando convenientemente las matrices 
# anteriores.

# Estos métodos son, por construcción, consistentes. Su convergencia depende,
# por tanto, del radio espectral, que viene dado por el máximo de los valores
# absolutos de los autovalores de B (en caso de ser menor que 1, el método
# es convergente). Los autovalores los podemos obtener con:

autovalores, autovectores = la.eig(B)
print('El radio espectral de B es',max(abs(autovalores)))

# En el ejercicio 1 se pide calcular "a mano" (es decir, sin automatizar - que
# lo haremos después) las matrices y algunas iteraciones del método.

# En caso de ser convergente, la construcción de las iteraciones se detendrá
# cuando la norma de la diferencia entre dos aproximaciones sucesivas sea menor
# que una cierta tolerancia. Veamos cómo podemos calcular la norma:
    
x0 = np.array([0,0,0])
x1 = np.array([1,1,1])

la.norm(x1-x0,1)
la.norm(x1-x0,2)
la.norm(x1-x0,np.inf)

# En el ejercicio 2 se pide automatizar el proceso a partir del siguiente
# esquema (que puede ser enfocado a jacobi o a gauss_seidel).

def funcion(A,b,x0,norma,error,k):
    # Construimos D, L y U.
    # Construimos M y N.
    # Construimos B y c.
    # Comprobamos que es convergente (a partir del radio espectral).
        # Si no es convergente, devolvemos el mensaje "El método no es convergente".
    # Calculamos aproximaciones mientras que no sobrepasemos el máximo de 
    # iteraciones y la norma de dos aproximaciones consecutivas no sea menor
    # que la tolerancia.
    # Devolvemos solucion aproximada, nº iteraciones

# Esta función será aplicada a dos sistemas concretos en el ejercicio 3.

