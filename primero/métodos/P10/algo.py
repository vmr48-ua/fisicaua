import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from numpy import e,cos,sin,pi,sqrt
from scipy.integrate import quad
import scipy.linalg as la
from scipy.optimize import fmin

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
    while abs(p2-p1) > tol and maxiter > n:
        n +=  1
        p0 = p1
        p1 = p2
        p2 = p3
        if f(p2)*f(p1) < 0:
            p3 = p2 - f(p2)*((p2-p1)/(f(p2)-f(p1)))
        else:
            p3 = p2 - f(p2)*((p2-p0)/(f(p2)-f(p0)))   
    return p2,n

def distanciaO(t):
    return la.norm((2*cos(t)-2,sin(t)-1))

def distanciaPrima(t):
    return -((3*cos(t)-4)*sin(t)+cos(t))/sqrt((sin(t)-1)**2+(2*cos(t)-2)**2)

distanciaPrima = np.vectorize(distanciaPrima)
distanciaO = np.vectorize(distanciaO)

x=np.linspace(0,2,150)
plt.plot(x,distanciaO(x))
plt.plot(x,distanciaPrima(x))

print(busqueda_incremental(distanciaPrima, 0, 2, 50))

print(biseccion(distanciaPrima,0.5,0.75,10**-4))
print(newton_5ptos(distanciaPrima, 0.56, 10**-4, 5))
print(secante(distanciaPrima, 0, 2, 10**-4, 5))
print(regula_falsi(distanciaPrima, 0, 2, 10**-4, 5))

plt.show()

