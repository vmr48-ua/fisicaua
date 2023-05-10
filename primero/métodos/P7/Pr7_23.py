# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 7                                                      """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad

###################################################

# DERIVACIÓN 

# Las fórmulas de derivación son expresiones sencillas que solo necesitan información
# sobre el valor de la función en varios puntos (que, en nuestro caso, son puntos equiespaciados). 
# A nivel computacional el cálculo se puede abordar desde dos perspectivas distintas:
# o bien conocemos la función completa en todo su dominio, o bien solo conocemos el valor
# de la función solo en dichos puntos.

# Por ejemplo, a continuación se muestra la fórmula de los tres puntos:

def dy_tres(f,x0,h):
    return (f(x0+h)-f(x0-h))/(2.*h)

# Veamos un ejemplo:
    
def fun1(x):
    return np.exp(-x)

dy_tres(fun1,1,0.2)
dy_tres(fun1,1,0.1)

# La derivada real es:
    
def dfun1(x):
    return -np.exp(-x)

dfun1(1)

# Para aplicar esta fórmula, solo necesitamos el valor de la función en los
# tres puntos implicados. De hecho, puede que solo conozcamos tres puntos sobre
# abscisas equiespaciadas y queramos aproximar la derivada en el punto central:
    
x = np.array([0.75,1,1.25])
y = np.array([0.47236655,0.36787944, 0.2865048])

def dy_tres_discreto(x,y):
    h = x[1]-x[0]
    return (y[2]-y[0])/(2.*h)


dy_tres_discreto(x,y)

# INTEGRACIÓN

# La aproximación de integrales definidas en base al polinomio interpolador en puntos
# equiespaciados da lugar a las fórmulas de Newton-Cotes. Estas fórmulas dependen de 
# si la función está definida en los extremos del intervalo (fórmulas cerradas) o no 
# (fórmulas abiertas) y, de nuevo, solo requieren de la evaluación de la función sobre 
# un conjunto de puntos equiespaciados. Además, también podemos dar las correspondientes
# versiones discretas.

def trapecio_cerrado(f,a,b):
    h = b-a
    return h*(f(a)+f(b))/2.

def trapecio_abierto(f,a,b):
    h = (b-a)/3
    return 3*h*(f(a+h)+f(a+2*h))/2.

def trapecio_cerrado_discreto(x,y):
    # x es un array con los dos extremos del intervalo 
    # y es un array con los valores de la función en los dos extremos del intervalo
    h = x[1]-x[0]
    return h*(y[0]+y[1])/2.

def trapecio_abierto_discreto(x,y):
    # x es un array con dos puntos interiores del intervalo equiespaciados con los extremos
    # y es un array con los valores de la función en los dos puntos anteriores
    h = x[1]-x[0]
    return 3*h*(y[1]+y[2])/2.

# Veamos un ejemplo:
    
def fun1(x):
    return np.exp(-x)

a,b = 1,2

quad(fun1,a,b)

# TRAPECIO CERRADO

trapecio_cerrado(fun1,a,b)

xn = np.array([a,b])
yn = fun1(xn)

P = np.polyfit(xn,yn,1)

quad(lambda x: np.polyval(P,x),a,b)

# REPRESENTACIÓN GRÁFICA: TRAPECIO CERRADO

xreal = np.linspace(a,b,100)

plt.figure()
plt.plot(xn,yn,'ok')
plt.plot(xreal,fun1(xreal),'g')
plt.plot(xreal,np.polyval(P,xreal),'m')
plt.fill_between(xreal, 0, np.polyval(P,xreal), facecolor='blue', alpha=0.15)
plt.show() 

# TRAPECIO ABIERTO

trapecio_abierto(fun1,a,b)

a1, b1 = a+(b-a)/3., a+2*(b-a)/3.
xn1 = np.array([a1,b1])
yn1 = fun1(xn1)

P1 = np.polyfit(xn1,yn1,1)

quad(lambda x: np.polyval(P1,x),a,b)

# REPRESENTACIÓN GRÁFICA: TRAPECIO ABIERTO

xreal = np.linspace(a,b,100)

plt.figure()
plt.plot(xn1,yn1,'ok')
plt.plot(xreal,fun1(xreal),'g')
plt.plot(xreal,np.polyval(P1,xreal),'m')
plt.fill_between(xreal, 0, np.polyval(P1,xreal), facecolor='blue', alpha=0.15)
plt.show() 

# El mismo ejemplo con las fórmulas discretas:

x = np.array([1,2])
y = fun1(x)

trapecio_cerrado_discreto(x,y)

x = np.linspace(1,2,4)
y = fun1(x)

trapecio_abierto_discreto(x,y)

# A  fin de reducir el error de la aproximación de las integrales definidas se 
# pueden considerar métodos compuestos para los cuales se divide el intervalo original 
# en un número determinado de subintervalos y se aplican sobre ellos las fórmulas 
# simples.

def trapecio_compuesto(f,a,b,n):
    xi = np.linspace(a,b,n+1)
    suma = 0.
    for i in range(n):
        suma += trapecio_cerrado(f,xi[i],xi[i+1])     
    return suma
 
    return suma 

trapecio_compuesto(fun1,1,2,3)

# Es evidente que cuanto mayor sea el número de intervalos menor será el error. 
# Conocer la expresión del error nos permitirá determinar el número de intervalos
# que debemos utilizar para asegurar que sea menor que un cierto valor.

def fun1(x):
    return np.exp(-x)

a, b = 1, 2
eps = 10**(-4)

# El error del método del trapecio compuesto viene dado por: 
#  E = -(b-a)h^2 f''(xi)/12,  donde h=(b-a)/n. 
# Debemos encontrar n de forma que |E|<eps.

# Obtenemos el máximo de la segunda derivada.
#  OPCIÓN 1: Por aproximación (Tema 4, diapositiva 22) 

xs = np.linspace(a,b,500)
fx = fun1(xs)

plt.plot(xs,fx,'b')

h = xs[1]-xs[0]
d2f = []
for i in range(1,len(xs)-1):
    d2f.append((fx[i+1]-2*fx[i]+fx[i-1])/h**2)
    
A = max(abs(np.array(d2f)))

#  OPCIÓN 2: Calculando la segunda derivada teóricamente.

def d2fun1(x):
    return np.exp(-x)

xs = np.linspace(a,b,500)
A = max(abs(d2fun1(xs)))

# Obtenemos n como en la presentación: n > np.sqrt((b-a)^3*max|f''|/(12*eps)) 

n = int(np.ceil(np.sqrt((A*(b-a)**3)/(12*eps))))

plt.figure()
plt.plot(xs,fun1(xs),xs,d2fun1(xs))
plt.legend(['Función','2a derivada'])
plt.show()

trapecio_compuesto(fun1,a,b,n)

quad(fun1,a,b)