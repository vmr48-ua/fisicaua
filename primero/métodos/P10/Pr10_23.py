# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 10                                                   """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
import scipy.linalg as la

###################################################

# ECUACIONES NO LINEALES

# En ocasiones estamos interesados en hallar un valor, p, tal que f(p) = 0 donde 
# f es una función real de variable real. Si f es una función continua tal que
# f(a)f(b) < 0, entonces por el Teorema de Bolzano, existe al menos un punto p en 
# (a,b) tal que f(p) = 0. Sin pérdida de generalidad podemos suponer que f(x) 
# solo tiene una raíz en [a,b] (si tiene más de una raíz se puede considerar
# otro intervalo más pequeño donde solo haya una raíz).

def fun(x):
    return np.exp(-x/10)*np.cos(x)

xr = np.linspace(-10,10)
plt.plot(xr,fun(xr))

# Una opción para escoger el intervalo [a,b] donde aplicar estos métodos es la
# función búsqueda incremental:
    
def busqueda_incremental(f,a,b,n):
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

busqueda_incremental(fun,0,15,50)
x = np.linspace(0,15,150)
plt.plot(x,fun(x),x,0*x)

# MÉTODO DE LA BISECCIÓN

# Tomamos a0 = a y b0 = b y, como primera aproximación, p0 = (a0+b0)/2.
# Si f(p0) = 0, hemos terminado. De lo contrario, dos posibilidades pueden
# darse:
# 1) Si f(a0)f(p0) < 0, entonces tomamos a1 = a0, b1 = p0 y p1 = (a1+b1)/2.
# 2) Si f(a0)f(p0) > 0, entonces tomamos a1 = p0, b1 = b0 y p1 = (a1+b1)/2.
# El proceso permite obtener una sucesión que aproxima la solucion
# buscada.

# En el ejercicio 1 se pide implementar el método de la bisección.

# MÉTODO DE PUNTO FIJO 

# Una buena parte de los métodos de resolución de ecuaciones no lineales son 
# de punto fijo. En términos generales, estos métodos consisten en transformar la 
# ecuación f(x) = 0 en una equivalente del tipo g(x) = x y, a partir de un punto 
# inicial p0, se obtiene una sucesión que, bajo ciertas condiciones, 
# tiene como límite p. El cálculo de las iteraciones se detendrá cuando, o bien
# se alcance un numero máximo de iteraciones (que llamaremos maxiter), 
# o bien se satisfaga algún criterio de parada en términos de la tolerancia
# tol > 0.
# El método de Newton-Raphson, por ejemplo, es un método de punto fijo.

# MÉTODO DE NEWTON

# Si utilizamos el método de Newton, por ejemplo, la solución se obtiene como 
# el límite de la sucesión:
# pn+1 = pn - f(pn)/df(pn), n = 0,1,...
# Observa que se necesita disponer del valor de la derivada en pn.
    
def newton(f,df,p0,tol,maxiter):
    for i in range(maxiter):      
        p1 = p0-f(p0)/df(p0)
        if abs(p1-p0)<tol:
            return [p1,i]
        p0 = p1
    print('Número máximo de iteraciones alcanzado!')
    return [None,None]

# En el ejercicio 2 se pide implementar el método de Newton obteniendo la 
# derivada aproximada por medio de la fórmula de los cinco puntos.
    
# MÉTODO DE LA SECANTE

# Si utilizamos el método de la secante la solución se obtiene como 
# el límite de la sucesión:
# pn+1 = pn - f(pn)(xn-x_(n-1)))/(f(pn)-f(p_(n-1)), n = 1,...

# MÉTODO DE LA REGULA FALSI

# El método de regula falsi es una ligera variación del método de la secante 
# mediante la cual se escogen los dos puntos para los que se calcula la recta de 
# forma que la raíz está siempre entre ellos.

# En el ejercicio 3 se pide implementar los métodos de la secante y regula falsi.

# En el ejercicio 4 se muestra una aplicación de estos métodos.

# SISTEMAS DE ECUACIONES NO LINEALES

# Los métodos de resoluciÓn de sistemas de ecuaciones no lineales más
# conocidos son los métodos iterativos de punto fijo. Como en el caso de 
# una variable se basan en la transformación de la ecuación F(x)=0 en una 
# ecuación del tipo G(x)=x. Las funciones F y G son funciones de R^n a R^n.
# Por ejemplo:
    
def G(w):
    x,y = w
    return np.array([-0.5*x**2+2*x+0.5*y-0.25, -0.125*x**2-0.5*y**2+y+0.5])

# Lógicamente el cálculo de las iteraciones debe parar cuando se satisfaga
# algún criterio de parada. Por ejemplo, cuando la distancia entre dos
# iteraciones sea menor que una cierta tolerancia. 

# En el ejercicio 5 se implementará el método de punto fijo para una ecuación
# del tipo G(x)=x. El esquema de la función 
# puede ser el siguiente:

# def punto_fijo_sist(G,p0,tol,maxiter):
    # En cada iteración hasta el máximo número de iteraciones:
        # Calculamos p_1 como p_1 = G(p_0)
        # Si p_0 y p_1 están lo suficientemente cerca:
            # Devolvemos p_1.
        # En otro caso continuamos con el siguiente paso.
        # Si se supera el número de iteraciones devolverá un mensaje.
        
        
# En el ejercicio 6 se pide aplicar esta función a un ejemplo concreto.