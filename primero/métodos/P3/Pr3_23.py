# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 3                                                      """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.interpolate import CubicSpline


# Interpolación polinómica a trozos - Splines (TEMA 2)

# https://docs.scipy.org/doc/scipy/reference/generated/scipy.interpolate.CubicSpline.html

''' Un spline es una función definida a trozos sobre una partición de un 
intervalo de la recta real por medio de diferentes polinomios de cierto grado 
que se "pegan" satisfaciendo ciertas condiciones de continuidad. 
En el caso de los splines cúbicos, los polinomios definidos sobre cada 
subintervalo son polinomios cúbicos y conforman una función continua y 
derivable dos veces. En Python los calcularemos mediante la función 
CubicSpline de la librería scipy.interpolate. '''

x = np.array([2,6,7])
y = np.array([4,5,6])

''' En este caso, el spline cúbico será definido por dos polinomios cúbicos
S_0 y S_1. El primero estará definido sobre el intervalo [2,6]; y el segundo, 
en el intervalo [6,7]. Más concretamente, tenemos que: 
  S_0(x) = a_0 + a_1(x-2) + a_2(x-2)^2 + a_3(x-2)^3
  S_1(x) = b_0 + b_1(x-6) + b_2(x-6)^2 + b_3(x-6)^3   '''

# CÁLCULO DEL SPLINE

S = CubicSpline(x,y)

# EVALUACIÓN DEL SPLINE

S(2.5)

xx = np.linspace(2,7,10)
vv = S(xx)

print('\n El Spline Cúbico evaluado en \n',xx,'\n es\n',vv)

# Sin embargo, CubicSpline calcula, por defecto, el spline de tipo 
# NOT-A-KNOT. Este tipo de spline satisface las condiciones: si los nodos 
# son x_0, x_1, ..., x_{n-1}, x_n, la tercera derivada del spline es continua
# en x_1 y x_{n-1}. Esto es: 
#                            S'''_0(x_1) = S'''_1(x_1)  
#                            S'''_{n-2}(x_{n-1}) = S'''_{n-1}(x_{n-1}).
    

# En las clases teóricas, sin embargo, hemos descrito el Spline natural y 
# el de frontera fija.

# SPLINE NATURAL

# Las segundas derivadas en los extremos son nulas.

S_Natural = CubicSpline(x,y,bc_type = 'natural')
S_Natural(3.2)

# SPLINE DE FRONTERA FIJA 

# Se especifica el valor de las primeras derivadas en los extremos.
#  bc_type = ((1, 0.3), (1, -0.3))
#  (1,0.3) significa que: la primera derivada en el punto inicial vale 0.3.
# (1,-0.3) significa que: la primera derivada en el punto final vale -0.3.

S_Frontera = CubicSpline(x,y,bc_type = ((1, 0.3), (1, -0.3)))

# COEFICIENTES DE LOS POLINOMIOS CÚBICOS

# Sabemos que un spline cúbico está formado por diferentes polinomios cúbicos.
# Si queremos visualizar los coeficientes de cada uno de ellos:
    
S_Natural.c

# S_0(x) = 4 - 0.05(x-2) + 0.01875(x-2)^3.
# S_1(x) = 5 + 0.85(x-6) + 0.225(x-6)^2 - 0.075(x-6)^3.
 
S_Frontera.c

# REPRESENTACIÓN GRÁFICA

# COMPROBACIÓN DE LOS COEFICIENTES DEL SPLINE 

# x = np.array([2,6,7])
L = 50
xr0 = np.linspace(2,6,L)
SF0 = S_Frontera.c[:,0]    # Coeficientes crecientes en (x-2)
print(SF0)
# SF0(x) = 0.0728125(x-2)^3 - 0.30375(x-2)^2 + 0.3(x-2) + 4

xr1 = np.linspace(6,7,L)
SF1 = S_Frontera.c[:,1]    # Coeficientes crecientes en (x-6)
print(SF1)
# S_1(x) = - 0.935(x-6)^3 + 0.57(x-6)^2 + 1.365(x-6) + 5

x1 = np.linspace(2,7)

plt.figure()
plt.plot(x,y,'*r')
plt.plot(x1,S_Frontera(x1),label='Spline de frontera',color='black',linestyle='-.',linewidth=3)
plt.plot(xr0,np.polyval(SF0,xr0-[2]*L),xr1,np.polyval(SF1,xr1-[6]*L))
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')
plt.title('Representación gráfica')
plt.legend(loc='best')
plt.show()


# REPRESENTACIÓN GRÁFICA DE TODOS LOS SPLINES CONSTRUIDOS

plt.figure()
plt.plot(x1,S(x1),label='Spline not-a-knot')
plt.plot(x1,S_Natural(x1),label='Spline natural')
plt.plot(x1,S_Frontera(x1),label='Spline de frontera')
plt.plot(x,y,'*r')
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')
plt.title('Representación gráfica')
plt.legend(loc='best')
plt.show()

# Observemos los extremos del spline de frontera y fijémonos en que, 
# efectivamente, la pendiente a la izquierda es 0.3 y, 
# a la derecha, -0.3 (al menos lo podemos intuir).

x2 = np.linspace(2,2.5)
plt.plot(x2,S_Frontera(x2))

x3 = np.linspace(6.5,7)
plt.plot(x3,S_Frontera(x3))


# ==========================================================================

# Aproximación mínimo-cuadrática discreta (TEMA 3)

# Un caso particular del problema de aproximación por mínimos cuadrados es la aproximación discreta. 
# Frecuentemente se recopilan observaciones a las cuales queremos asignar un modelo matemático. 
# Supongamos que disponemos de n + 1 puntos en el plano, (x0,y0),(x1,y1),...,(xn,yn), 
# y consideramos el producto escalar en el espacio de las funciones definidas sobre x0, x1,..., xn.
# Nuestro objetivo es encontrar el polinomio 'más cercano' a los n+1 puntos respecto a la 
# distancia inducida por el producto escalar anterior.

# Notemos que polyfit devuelve precisamente este polinomio cuando le especificamos
# un grado menor al número de nodos menos 1.

x = np.array([1,2,4,6])
y = np.array([2,4,5,5])

P1 = np.polyfit(x,y,1)
P2 = np.polyfit(x,y,2)

xr = np.linspace(1,6)

plt.figure()
plt.plot(xr,np.polyval(P1,xr),label='Polinomio aprox. grado 1')
plt.plot(xr,np.polyval(P2,xr),label='Polinomio aprox. grado 2')
plt.plot(x,y,'*r')
plt.title('Gráfica')
plt.legend(loc='best')
plt.show()

# Pero... ¿Cómo hacerlo por nosotros/as mismos/as?

# Nuestro objetivo es obtener el polinomio de grado m, con m entre 1 y n, para el cual la distancia
# a los puntos sea mínima. Más concretamente, si llamamos:
# Pm(x) = a0 + a1*x + a2*x**2 + ... + am*x**m;
# y consideramos la base canónica de los polinomios de grado menor o igual que m, e yi = f(xi) 
# para i = 0,1,...,n, sabemos que los coeficientes a0,a1,...,am son las
# soluciones del sistema de m+1 ecuaciones con m+1 incógnitas (llamado sistema
# de ecuaciones normales).

# Por ejemplo, si buscamos un polinomio de grado 2 se escribirá como 
# P1(x)=a0*Phi0(x)+a1*Phi1(x)+a2*Phi2(x),
# donde Phi0(x)=1, Phi1(x)=x y Phi2(x)=x**2, y ai son la solución de:
# a0(Phi0,Phi0)+a1(Phi1,Phi0)+a2(Phi2,Phi0)=(f,Phi0)
# a0(Phi0,Phi1)+a1(Phi1,Phi1)+a2(Phi2,Phi0)=(f,Phi1)
# a0(Phi0,Phi2)+a1(Phi1,Phi2)+a2(Phi2,Phi0)=(f,Phi2)

# ¿Cómo obtener los productos escalares? 

# En Python tenemos la función np.dot que deuelve el producto escalar de dos
# vectores:
    
fx = np.array([0,2])
gx = np.array([1,-1])
np.dot(fx,gx)

# Necesitamos los productos escalares de los vectores de las evaluaciones
# en los puntos x. Por ejemplo, si x=(1,2) y queremos hacer el producto escalar de 
# Phi1 = x y Phi2 = x**2 en dichos puntos, procedemos como sigue:
    
# 1: Representamos nuestros polinomios como arrays:
    
P1 = np.array([1,0])
P2 = np.array([1,0,0])

# 2: Calculamos las evaluaciones:

x = np.array([1,2])

P1x = np.polyval(P1,x) # array([1, 2])
P2x = np.polyval(P2,x) # array([1, 4])
    
np.dot(P1x,P2x)

# Es evidente que necesitamos disponer de un procedimiento para construir
# los polinomios de la base canónica. ¿Cómo se pueden obtener?    

# Opción 1: Los coeficientes de estos polinomios son un 1 seguido de tantos
# ceros como el grado del polinomio. 
    
base0 = np.array([1.]+0*[0]) # array([1.])
base1 = np.array([1.]+1*[0]) # array([1., 0.])
base2 = np.array([1.]+2*[0]) # array([1., 0., 0.]) 

# Opción 2: Estos polinomios tienen una única raíz igual a 0 
# con multiplicidad el grado del polinomio. 

base0 = npol.polyfromroots(np.zeros(0))[::-1] # array([1.])
base1 = npol.polyfromroots(np.zeros(1))[::-1] # array([1., 0.])
base2 = npol.polyfromroots(np.zeros(2))[::-1] # array([1., 0., 0.])

# ¡¡¡ En potencias decrecientes !!!

# Para construir todos de una vez:
    
base = [np.array([1.]+i*[0]) for i in range(3)]
    
# Sirvan como ejemplo los siguientes casos:

np.dot(np.polyval(base[0],x),np.polyval(base[1],x))
np.dot(y,np.polyval(base[1],x))
np.dot(y,np.polyval(base[2],x))

# Una vez obtenidos los productos escalares, np.solve puede proporcionarnos la 
# solución del sistema.

# En resumen:

def modelo_discreto_general(x,y,m):
    # Construimos la base de los polinomios.
    # Inicializamos la matriz de coeficientes del sistema como una matriz de ceros.
    # Recorremos las filas de la matriz de términos independientes y matriz de coeficientes:
        # Calculamos el término independiente.
        # Recorremos las columnas de la matriz de coeficientes:
            # Calculamos el coeficiente correspondiente.
    return # Devolvemos la solución del sistema.

# Este es el objetivo del ejercicio 1. Los ejercicios 2, 3 y 4 son aplicaciones de
# esta función.




