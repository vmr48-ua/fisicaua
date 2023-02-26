# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 4                                                    """

# LIBRERÍAS

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol

  
# APROXIMACIÓN DE FUNCIONES CASO CONTINUO

# En la aproximación de funciones continuas, se utilizan las bases ortogonales de los po-
# linomios de Chebyshev y de los polinomios de Legendre. Estas bases se obtienen, entre
# otros procedimientos, aplicando # la ortogonalización de Gram-Schmidt a la 
# base canónica de los polinomios considerando diferentes pesos. 

# ¿Cómo construirlos?
    
# Opción 1: Utilizar las relaciones de recurrencia que aparecen en el dossier de la 
# práctica y que debemos implementar en el ejercicio 1. Hemos de tener en cuenta que estas
# relaciones no nos devuelven los polinomios mónicos.

# Opción 2: Extraer los polinomios en Python con las clases de objetos chebyshev y legendre.
# Por ejemplo, para extraer el polinomio mónico de Chebyshev de grado 2 sobre el intervalo [-1,1]:
    
C2 = np.polynomial.chebyshev.Chebyshev([0, 0, 1]) # Se podría usar indicar el intervalo [-1,1], 
                                                # pero este es el que considera por defecto.

L2 = np.polynomial.legendre.Legendre([0, 0, 1]) # Se podría usar indicar el intervalo [-1,1], 

# ¿Qué coeficientes tienen?

nodC2 = C2.roots()
npol.polyfromroots(nodC2)[::-1]

nodL2 = L2.roots()
npol.polyfromroots(nodL2)[::-1]

# Dado que estas bases son ortogonales con respecto al producto escalar
# dado por la integral del producto de la función peso y las dos funciones, 
# para calcular sus coeficientes se puede aplicar la expresión ai=(f,Phii)/(Phii,Phii), 
# para cada i. 

# Evidentemente, este proceso se puede mecanizar, pero no lo vamos a hacer en estas
# prácticas. Si alguien quiere hacerlo, puede intentarlo.

# APROXIMACIÓN POLINÓMICA CON BASES ORTOGONALES EN [-1,1]

# Nuestro objetivo es obtener el polinomio de grado m, con m entre 1 y n, para el cual la distancia
# a los puntos sea mínima. Más concretamente, si llamamos:
# Pm(x)=a0*Phi0(x)+a1*Phi1(x)+...+am*Phim(x),
# donde {Phi0(x),Phi1(x),...,Phi2(x)} es una base ortogonal para los polinomios
# de grado menor o igual que m. En este caso, el sistema de ecuaciones normales
# se transforma en un sistema diagonal y las soluciones se pueden obtener fácilmente
# como:
# ai=(f,Phii)/(Phii,Phii), para i=0,1,...,m.

# Hay que tener muy claro qué polinomios consideramos y con respecto a qué
# producto escalar estamos trabajando.

# Por ejemplo, si, dada una función f, buscamos un polinomio aproximante de grado 2
# para la base ortogonal de los polinomios de Chebyshev en [-1,1]:
# P2(x)=a0*Phi0(x)+a1*Phi1(x)+a2*Phi2(x),
# donde Phi0(x)=1, Phi1(x)=x y Phi2(x)=x**2-0.5,
# a0=(f,Phi0)/(Phi0,Phi0), a0=(f,Phi1)/(Phi1,Phi1) y a0=(f,Phi2)/(Phi2,Phi2),
# donde (f,g)=int_{-1}^{1} w(x)f(x)g(x)dx.

def fun1(x):
    return np.exp(-2*x)*np.cos(3*x)

def wCh(x):
    return 1/np.sqrt(1-x**2)

from scipy.integrate import quad

a0Ch = quad(lambda x:fun1(x)*wCh(x),-1,1)[0]/quad(wCh,-1,1)[0]
a1Ch = quad(lambda x:fun1(x)*wCh(x)*x,-1,1)[0]/quad(lambda x:wCh(x)*x**2,-1,1)[0]
a2Ch = quad(lambda x:fun1(x)*wCh(x)*(x**2-0.5),-1,1)[0]/quad(lambda x:wCh(x)*(x**2-0.5)**2,-1,1)[0]

def aproxCheb(x):
    return a0Ch+a1Ch*x+a2Ch*(x**2-0.5)

xreal = np.linspace(-1,1)

plt.plot(xreal,fun1(xreal),'k',xreal,aproxCheb(xreal),'r')
plt.legend(('Función','Aprox. Chebyshev 2'),loc = 'best')
plt.xlabel('Abscisas')
plt.ylabel('Ordenadas')

# Este es el objetivo de los ejercicios 2 y 3.

# APROXIMACIÓN POLINÓMICA CON BASES ORTOGONALES EN [a,b]

# Supongamos ahora que la función f está definida en un intervalo cualquiera [a,b].
# En primer lugar, debemos obtener los polinomios ortogonales (Chebyshev o Legendre)
# en dicho intervalo. Se trata de aprovechar la definición en [-1,1].

# A tal fin, disponemos de dos opciones:
    
# Opción 1: Estos polinomios se pueden obtener por medio de un cambio de variable.
# Si Phii(x) está definido para x en [-1,1],  Phii*(x)=Phii(h(x)) está definido 
# para x en [a,b] si h(x) transforma el intervalo [a,b] en [-1,1]. 

# Opción 2: Las raíces de los nuevos polinomios se obtienen al transformar
# las raíces de los polinomios en el intervalo [-1,1] al intervalo [a,b]. Una vez
# realizada esta transformación se pueden "reconstruir" los polinomios. Lo haremos 
# en el ejercicio 4.

# Una vez que tenemos la base de los polinomios se trata de obtener el valor de
# a0,a1,...,am donde
# ai=(f,Phii)/(Phii,Phii), para i=0,1,...,m,
# teniendo en cuenta que el producto escalar ahora es
# (f,g)=int_{a}^{b} w*(x)f(x)g(x)dx,
# y la función peso también debe ser adaptada al intervalo [a,b] mediante
# la misma transformación, es decir, w*(x)=w(h(x)).

# Lo haremos en los ejercicios 5 y 6.