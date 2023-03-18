# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 5                                                      """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol



# APROXIMACIÓN TRIGONOMÉTRICA

# El polinomio trigonométrico sobre [-Pi,Pi] es una combinacion lineal de las 
# funciones periódicas {1/2,cos(x),sin(x),cos(2x),sin(2x),...,cos(nx), sin(nx),...}.
# Los coeficientes de dichas funciones se pueden consultar en la página 63
# de la presentación.

# Veamos un ejemplo:
    
def f(x):
    return np.exp(-2*x)

###################################################
from scipy.integrate import quad
###################################################

a0 = (1/np.pi)*quad(lambda x: f(x),-np.pi,np.pi)[0]

a1 = (1/np.pi)*quad(lambda x: f(x)*np.cos(x),-np.pi,np.pi)[0]
b1 = (1/np.pi)*quad(lambda x: f(x)*np.sin(x),-np.pi,np.pi)[0]

a2 = (1/np.pi)*quad(lambda x: f(x)*np.cos(2*x),-np.pi,np.pi)[0]
b2 = (1/np.pi)*quad(lambda x: f(x)*np.sin(2*x),-np.pi,np.pi)[0]

a3 = (1/np.pi)*quad(lambda x: f(x)*np.cos(3*x),-np.pi,np.pi)[0]

def Aprox_Fourier(x):
    return a0/2.+a1*np.cos(x)+b1*np.sin(x)+a2*np.cos(2*x)+b2*np.sin(2*x)+a3*np.cos(3*x)

xr = np.linspace(-np.pi,np.pi)

plt.plot(xr,f(xr))
plt.plot(xr,Aprox_Fourier(xr))
plt.legend(('Funcion','Apróx. Fourier 3'),loc = 'best')
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')

# Tal y como podemos ver en la página 65 de la presentación, si la función
# está definida en un intervalo [-L,L] también es posible encontrar el
# correspondiente polinomio trigonométrico. En este caso, dicho polinomio será
# combinación lineal de las funciones 
# {1/2,cos(Pi*x/L),sin(Pi*x/L),cos(2*Pi*x/L),sin(2*Pi*x/L),...}
# y los valores de los coeficientes se pueden consultar en la página 69
# de la presentación.

# El cálculo de Aprox_Fourier se puede automatizar y este es el objetivo del
# ejercicio 2. En los ejercicios 3, 4 y 5 construiremos los polinomios trigonométricos
# de otras funciones.

# APROXIMACIÓN RACIONAL DE PADÉ

# Dada una función f, la aproximación racional en x0 consiste en obtener 
# un cociente de polinomios r(x) = p(x)=q(x), de forma que f(i)(x0) = r(i)(x0), 
# para todo i=0,1,...,N=n+m.

# En scipy.interpolate podemos encontrar dos funciones útiles para obtener
# la aproximación racional. Por un lado, pade devuelve la aproximación racional 
# propiamente dicha; mientras que, en la sublibrería interpol, la función
# approximate_taylor_polynomial nos ofrecerá los coeficientes del desarrollo 
# de Taylor de cualquier función.

from scipy.interpolate import pade
import scipy.interpolate as interpol

# Supongamos, por ejemplo, que queremos aproximar la función exponencial en
# x0=0:
    
def fun(x):
    return np.exp(x)

# La función pade toma como argumento los coeficientes del polinomio de Taylor
# en el punto correspondiente hasta cierto grado en potencias crecientes. 
# Por ejemplo, si deseamos obtener la aproximación racional con N = n+m = 5, 
# donde n es el grado del numerador y m es el grado del denominador, 
# necesitamos los coeficientes del polinomio de Taylor de grado 5 en x0=0. 
# A tal fin, disponemos de dos opciones:

x0 = 0
N = 5

# OPCIÓN 1: Calcular los coeficientes de forma analítica 
#           Grado N = 5. Orden creciente (en (x-x0))

P_taylor1_Cre = np.array([1, 1, 1/2, 1/6, 1/24, 1/120],dtype=float)  # Orden CRECIENTE

P_taylor1_Dec = P_taylor1_Cre[::-1]        # Orden decreciente
print(np.poly1d(P_taylor1_Dec))

fun(x0)
np.polyval(P_taylor1_Dec,0)

# OPCIÓN 2: Usando "interpol.approximate_taylor_polynomial"
#           Grado N = 5 (seis términos). Orden decreciente      

taylor_aprox2 = interpol.approximate_taylor_polynomial(fun,x0,N,scale=0.1) 
print(taylor_aprox2)

fun(x0)
taylor_aprox2(0)

P_taylor2_Dec = np.array(taylor_aprox2)

P_taylor2_Cre = np.array(taylor_aprox2)[::-1]     # Orden CRECIENTE

# La función "pade" toma los coeficientes de Taylor en potencias CRECIENTES! 
# El segundo argumento es el grado del denominador (m = 2), 
# y el grado del denominador viene dado por N-m, es decir, n = N-m = 5-2 = 3.

m = 2

p,q = pade(P_taylor2_Cre, m)

# El resultado de "pade" son los polinomios en potencias decrecientes (en (x-x0))
# para ser usados por poly1d y, para evaluarlos, hacemos lo siguiente:
    
print(p)
print(q)

p(0)
p(0)/q(0)

def Pade_fun(x):
    return p(x-x0)/q(x-x0)

# Hagamos un gráfico:

xr = np.linspace(x0-4,x0+4)

plt.plot(xr,fun(xr))
plt.plot(xr,Pade_fun(xr))
plt.plot(xr,np.polyval(P_taylor1_Dec,xr))
plt.legend(('Función','Pade Aprox.','Taylor'),loc = 'best')
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')

# En los ejercicios 6 y 7 obtenemos la aproximación racional de otra función.