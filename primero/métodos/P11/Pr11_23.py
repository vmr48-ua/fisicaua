# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 11                                                   """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
import scipy.linalg as la

###################################################

# SISTEMAS DE ECUACIONES NO LINEALES (CONTINUACIÓN)

# Las ecuaciones no lineales del tipo F(x) = 0 se pueden escribir en una forma 
# equivalente del tipo x = G(x). De esta forma, el punto fijo de G coincide 
# con la solución de F(x) = 0. 

def F(w):
    x,y = w
    return np.array([3*x**2-y**2, 3*x*y**2-x**3-1])

# La solución (o soluciones) de un sistema de ecuaciones no lineales
# son los puntos de corte de las curvas ( o superficies) que representan
# cada una de ellas:
    
xd = np.linspace(-2,2,100)
yd = np.linspace(-2,2,100)
C = [0]
X, Y = np.meshgrid(xd, yd)
plt.figure()
plt.contour(X,Y,F([X,Y])[0],C,colors='red')
plt.contour(X,Y,F([X,Y])[1],C,colors='blue')
plt.xlabel('Eje X')
plt.ylabel('Eje Y')
plt.grid(True)
plt.show()

# Existen muchas formas de realizar la transformación. Una de ellas es, 
# por ejemplo, tomar una matriz cuadrada no singular de orden n cualquiera, 
# A(x), y considerar G(x) = x - A(x)F(x).

# En las clases de teoría, hemos visto el método de Newton que es un caso 
# particular de los métodos de punto fijo (tomando A(x) = JF(x)^(-1)) y, a 
# su vez, es una generalización a sistemas de ecuaciones no lineales del 
# método homónimo visto en la práctica anterior para ecuaciones no lineales. 
# Este método generalmente converge siempre que el punto semilla sea próximo 
# a p y que JF^(-1)(p) exista. El cálculo del jacobiano se puede realizar a
# mano y definirlo como sigue:

def JF(w):
    x,y = w
    return np.array([ [6.*x , -2.*y], [3.*y**2-3.*x**2 , 6.*x*y]])

p0 = np.array([1,1])
JF(p0)

# Un inconveniente del método de Newton es que el cálculo de la matriz 
# jacobiana y de su inversa no es, en general, un proceso muy
# eficiente desde el punto de vista numérico. Por ello, a la hora 
# de implementar el método se procede en dos etapas: en la primera se encuentra 
# un vector y tal que JF(p_k)y = -F(p_k) (sistema lineal), y, en la segunda,
# se obtiene p_(k+1) = p_k + y. 

# En el ejercicio 1 se pide implementar el método de Newton para sistemas
# de ecuaciones no lineales proporcionando como argumento de entrada la expresión
# del jacobiano. La función newton_sist puede responder al siguiente esquema:

# def newton_sist(F,JF,p0,tol,maxiter):
    # En cada iteración hasta el máximo número de iteraciones:
        # Resolvemos el sistema JF(p_0)y = -F(p_0) 
        # Obtenemos p_1 como p_1 = p_0 + y
        # Si p_0 y p_1 están lo suficientemente cerca:
            # Devolvemos p_1.
        # En otro caso continuamos con el siguiente paso.

# El ejercicio 2 pide aplicar la función anterior a dos sistemas de ecuaciones
# no lineales concretos.

# El jacobiano contiene las derivadas parciales de las componentes de F. 
# A fin de evitar el cálculo de la matriz jacobiana de forma analítica
# se puede aproximar según las fórmulas expuestas en la página 23 de la 
# presentación del tema 4. Por ejemplo, si usamos la fórmula (2) del PDF 
# de la práctica:

def JF_approx1(F,p0,h):
    n = len(p0)
    JFa = np.zeros((n,n))
    for i in range(n):
        v = np.eye(n)[i]
        JFa[:,i] = (F(p0+h*v)-F(p0))/(h)
    return JFa

# De esta forma, si queremos obtener la aproximación del jacobiano de F en el punto 
# p0=(1,1) con h = 0.01:
    
h = 0.01
p0 = np.array([1,1])
JF_approx1(F,p0,h)

# En el ejercicio 3 se pide implementar el método de Newton para sistemas
# de ecuaciones no lineales aproximando el jacobiano mediante las opciones 
# (2) y (3).

# ECUACIONES DIFERENCIALES

# El objetivo de una ecuación diferencial ordinaria (de primer orden) con 
# valor inicial consiste en obtener una función y(t) (definida en un intervalo) 
# de forma que satisfaga cierta ecuación en la que aparece su primera derivada, 
# y tome un valor dado en un instante dado (inicial), es decir, 
# y0(t) = f(t,y(t)); y(a) = alfa;
# donde t es la variable independiente con valores entre a y b.

# Entre los métodos numéricos más frecuentes para resolver este problema 
# se encuentran los llamados métodos de discretización, que consisten en 
# encontrar los valores aproximados de la función y en una colección finita
# de n+1 puntos equidistantes t_k del intervalo [a,b] (llamaremos h a la longitud
# de cada subintervalo [t_k,t_(k+1)]). Recuerda que y(t_k) representa el 
# valor exacto de la solución en t_k, mientras que y_k es el valor aproximado 
# de la solución al aplicar un método numérico. El método más simple es el 
# método de Euler según el cual:
# y_(k+1) = y_k + hf(t_k,y_k), k = 0,1,...

# Por ejemplo, supongamos que queremos obtener la aproximación del valor
# de la solución y, definida en [1,2], en el punto t=0.05, que satisface la 
# ecuación diferencial que se muestra en el apartado a del ejercicio 4, 
# sabiendo que y(1)=1.

def f(t,y):
    return (y/t)-(y/t)**2

t0 = 1
y0 = 1
t1 = 0.05
h = (t1-t0)
y1 = y0 + h*f(t0,y0)

# Obviamente, a fin de dar la aproximación de una función completa sobre un 
# intervalo se necesitar dar una aproximación no solo del valor de la función
# en un punto concreto, sino sobre los n+1 puntos equiespaciados.
# Para aproximar cada uno de los valores se utiliza el anterior. La 
# aproximación buscada es el conjunto de todas las aproximaciones. El error
# verdadero que se comete es el máximo de los errores cometidos en todos los
# puntos.

# En los ejercicios 4 y 5 se pide implementar el método de Euler y se calculará
# el error verdaderos cometido en los diferentes apartados. 