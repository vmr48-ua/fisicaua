import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from numpy import e,cos,sin
from scipy.integrate import quad
import scipy.linalg as la
from scipy.optimize import fmin


"""###########"""
"""Ejercicio 1"""
"""###########"""

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

def biseccion(f,a,b,tol):
    n = int(np.log2(np.abs((b-a)/tol))) - 1
    x0 = a
    x1 = b
    for i in range(n):
        if f((x1+x0)/2) == 0:
            return (x1+x0)/2
        elif f((x1+x0)/2)*f(x0) < 0:
            x1 = (x1+x0)/2
        else:
            x0 = (x1+x0)/2
    return (x1+x0)/2,n

def f(x):
    return e**(-x/10)*cos(x)

a,b=busqueda_incremental(f,0,15,50)[0,0], busqueda_incremental(f,0,15,50)[0,1]
print(biseccion(f, a, b, 10**-4))

x = np.linspace(0,4,150)
plt.plot(x,f(x),x,0*x)
plt.scatter(biseccion(f, a, b, 10**-4)[0],f(biseccion(f, a, b, 10**-4)[0]))
plt.show()

"""###########"""
"""Ejercicio 2"""
"""###########"""

def dy_cinco(f,x0,h):
    return (f(x0-2.*h)-8.*f(x0-h)+8.*f(x0+h)-f(x0+2.*h))/(12.*h)

def newton_5ptos(f,p0,tol,maxiter):
    p1 = p0 - f(p0)/dy_cinco(f,p0,10**(-2))
    n = 1
    while abs(p1-p0) > tol and n < maxiter:
        n +=  1
        p0 = p1
        p1 = p0 - f(p0)/dy_cinco(f,p0,10**(-2))
    return p1,n

newton_5ptos(f, (a+b)/2, 10**-4, 50)

"""###########"""
"""Ejercicio 3"""
"""###########"""

def secante(f,p0,p1,tol,maxiter):
    p2 = p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    n = 1
    while abs(p2-p1) > tol and n < maxiter:
        n +=  1
        p0 = p1
        p1 = p2
        p2 = p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    return p2,n

def regula_falsi(f,p0,p1,tol,maxiter):
    p2 = p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    p3 = p2 - f(p2)*((p2-p1)/(f(p2)-f(p1)))
    n = 1
    while abs(p2-p1) > tol:
        n +=  1
        p0 = p1
        p1 = p2
        p2 = p3
        if f(p2)*f(p1) < 0:
            p3 = p2 - f(p2)*((p2-p1)/(f(p2)-f(p1)))
        else:
            p3 = p2 - f(p2)*((p2-p0)/(f(p2)-f(p0)))   
    return p2,n

secante(f, a, b, 10**-4, 50)
regula_falsi(f, a, b, 10**-4, 50)

"""###########"""
"""Ejercicio 4"""
"""###########"""
def distanciaO(t):
    return la.norm((2*cos(t)-2,sin(t)-1))
distanciaO = np.vectorize(distanciaO)

x=np.linspace(0,2,150)
plt.plot(x,distanciaO(x))
busqueda_incremental(distanciaO, 0, 2, 50) 

biseccion(distanciaO,0.5,0.75,10**-4)
newton_5ptos(distanciaO, 0, 10**-4, 5)
secante(distanciaO, 0, 2, 10**-4, 5)
regula_falsi(distanciaO, 0, 2, 10**-4, 5)
fmin(distanciaO,0.5) #array([0.58720703])

"""###########"""
"""Ejercicio 5"""
"""###########"""

def punto_fijo_sist(G,p0,tol,maxiter):
    p1 = G(p0)
    n = 1
    while la.norm(np.array(p0)-np.array(p1))>=tol and n < maxiter:
        p0,p1 = p1,G(p1)
        n += 1
    return p1,n

"""###########"""
"""Ejercicio 6"""
"""###########"""

def g(w):
    x,y = w
    return np.array([-0.5*x**2+2*x+0.5*y-0.25, -0.125*x**2-0.5*y**2+y+0.5])

punto_fijo_sist(g, (2,0), 10**-4, 50)
punto_fijo_sist(g, (0,1), 10**-4, 50)