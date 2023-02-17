# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 2                                                      """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol

# Dados (x0, y0), (x1, y1),..., (xn, yn), n+1 puntos en R2, donde x0, x1,...,xn son
# n+1 puntos de la recta real distintos dos a dos (llamados nodos de interpolación), el 
# problema de la interpolación polinomial de Lagrange consiste en determinar, si existe, 
# un polinomio Pn , de grado menor o igual que n, que pase por todos ellos 
# (Pn(xi) = yi, para todo i = 0,1,2,...,n). En muchas ocasiones, yi = f(xi), 
# para i = 0,1,...,n, donde f es una función que es desconocida o difícil de tratar 
# analíticamente. En las clases teóricas hemos comprobado que, en las condiciones anteriores, 
# el polinomio interpolador siempre existe y es único. Analíticamente, su cálculo se puede 
# realizar desde tres puntos de vista diferentes (a partir de la matriz de Vandermonde, 
# de los polinomios fundamentales de Lagrange y de las diferencias divididas de Newton). 
# A lo largo de esta práctica y las siguientes calcularemos los polinomios interpoladores.


# REPRESENTACIONES GRÁFICAS DE POLINOMIOS

P1=np.array([3,-2,1]) # En potencias decrecientes (3x**2-2x+1).
  
x=np.linspace(-2,2)
y=np.polyval(P1,x)

plt.figure()
plt.plot(x,y)
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')
plt.title('Gráfica del polinomio P1')
plt.show()

# También podemos pintar un conjunto de puntos:
    
xnodos = np.array([-1.5,-0.75,0,1,1.5,2])
ynodos = np.polyval(P1,xnodos)

plt.figure()
plt.plot(x,y)
plt.plot(xnodos,ynodos,'*r')
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')
plt.title('Gráfica del polinomio P1')
plt.show()



# INTERPOLACIÓN

# La librería numpy de Python contiene la función polyfit, que permite calcular el polinomio
# interpolador. Esta función necesita tres argumentos: los nodos, las imágenes y el
# grado del polinomio. Si el grado es uno menos que el número de puntos el resultado es
# precisamente el polinomio interpolador. En otros caso, se obtienen otro polinomios aproximantes.

x = np.array([0,3,5,8])
y = np.array([-2,2,1,4])

P = np.polyfit(x,y,3) # El tercer argumento es el grado del polinomio.

np.polyval(P,0)



# CÁLCULO DEL POLINOMIO INTERPOLADOR



# MATRIZ DE VANDERMONDE (polLagrange):
    
# Para calcular el polinomio interpolador usando la matriz de Vandermonde
# necesitamos resolver un sistema de ecuaciones donde la matriz de coeficientes
# viene dada por dicha matriz.
    
x = np.array([0,3,5,8])
np.vander(x) # Ojo porque las columnas están al contrario. Esto podría afectar a la 
             # representación del sistema de ecuaciones.
             
# Dado un sistema de ecuaciones Ax=b, np.linalg.solve(A,b) o aplicar x = A**(-1)*b.
# Recordemos que la inversa se calcula con np.linalg.inv y el producto matricial
# con np.dot.



# POLINOMIOS FUNDAMENTALES DE LAGRANGE:
                
# Aunque no hay ningún ejercicio para calcular el polinomio por esta vía, 
# deberíamos usar las operaciones con polinomio. Dado un sistema de ecuaciones Ax=b, np.linalg.solve o aplicar x = A**(-1)*b.
# Recordemos que la inversa se calcula con np.linalg.inv y el producto matricial
# con np.dot.



# DIFERENCIAS DIVIDIDAS DE NEWTON:
    
# Implementaremos una función que calcule las diferencias divididas (en forma de
# matriz y, posteriormente, las usaremos para construir el polinomio.



# REPRESENTACIONES GRÁFICAS


# Supongamos que queremos interpolar la función f(x)= sin(x)*exp(x) en tres puntos
# equiespaciados de [-2,2].

def f(x):
    return np.sin(x)*np.exp(x)

xnodos = np.linspace(-2,2,3)
ynodos = f(xnodos)

P = np.polyfit(xnodos,ynodos,2) # array([0.16004567, 0.80280723, 1.07029476, 0.08666592])
                      # En potencias decrecientes.
                    
# Si queremos representarlo gráficamente:
    
x = np.linspace(-2,2)
y = f(x)

plt.figure()
plt.plot(x,y,label='Función')
plt.plot(x,np.polyval(P,x),label='Polinomio interpolador')
plt.plot(xnodos,ynodos,'*')
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')
plt.title('Representación gráfica')
plt.legend(loc='best')
plt.show()

# Si queremos representar el error que se comete:

plt.plot(x,np.abs(np.polyval(P,x)-f(x)))


# NODOS DE CHEBYSHEV

# https://numpy.org/doc/stable/reference/generated/numpy.polynomial.chebyshev.Chebyshev.html

# Polinomio de Chebyshev de orden 2, en el intervalo [-1,1]

T2 = np.polynomial.chebyshev.Chebyshev([0, 0, 1]) 
nodCheby2 = T2.roots() 
P2 = npol.polyfromroots(nodCheby2)[::-1]
print(np.poly1d(P2))

# Polinomio de Chebyshev de orden 2, en un intervalo [a,b]

a, b = 2, 5
T2_ab = np.polynomial.chebyshev.Chebyshev([0, 0, 1],[a,b]) 
nodCheby2_ab = T2_ab.roots() 
P2_ab = npol.polyfromroots(nodCheby2_ab)[::-1]
print(np.poly1d(P2_ab))

# Comparación gráfica

plt.figure()
xr = np.linspace(-1,1)
plt.plot(xr,np.polyval(P2,xr),xr,0*xr,nodCheby2,0*nodCheby2,'o')
xrab = np.linspace(a,b)
plt.plot(xrab,np.polyval(P2_ab,xrab),xrab,0*xrab,nodCheby2_ab,0*nodCheby2_ab,'o')
plt.show()

# También es posible obtener los nodos de Chebyshev en un intervalo [a,b]
# nodCheby2_ab, a partir de nodCheb2, con una transformación lineal del 
# intervalo [-1,1] en el intervalo [a,b]. 

# PREGUNTA: ¿Qué transformación lineal lleva el intervalo [-1,1] al [a,b]?
    


