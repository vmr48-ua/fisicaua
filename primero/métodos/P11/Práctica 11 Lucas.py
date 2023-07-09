# -*- coding: utf-8 -*-
"""
Created on Wed Apr 26 12:04:42 2023

@author: lucas
"""

import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
import scipy.linalg as la


#Ejercicio 1

def newton_sist(F,JF,p0,tol,maxiter):
    cont = 0    
    error = 10**4
    while cont < maxiter and error >= tol:
        y = la.solve(JF(p0),-F(p0))                
        p1 = p0 + y
        error = la.norm(p0-p1)
        cont += 1
        p0 = p1

    return p1, cont
       


#Ejercicio 2

def F(w):
    x,y = w
    return np.array([3*x**2-y**2, 3*x*y**2-x**3-1])

def JF(w):
    x,y = w
    return np.array([ [6.*x , -2.*y], [3.*y**2-3.*x**2 , 6.*x*y]])

p0 = np.array([1,1])
newton_sist(F,JF,p0,10**(-4),50)

p0 = np.array([-1,-1])
newton_sist(F,JF,p0,10**(-4),50)


#Ejercicio 3
   

""""Las siguientes funciones hacen lo mismo que las anteriores,
pero no hace falta que le pasemos el jacobiano. La función lo 
calcula por su cuenta.""" 
    

"""Las siguientes nos aproximan el jacobiano"""
def JF_approx1(F,p0,h):
    n = len(p0)
    JFa = np.zeros((n,n))
    for i in range(n):
        v = np.eye(n)[i]
        JFa[:,i] = (F(p0+h*v)-F(p0))/(h)
    return JFa

def JF_approx2(F,p0,h):
    n = len(p0)
    JFa = np.zeros((n,n))
    for i in range(n):
        v = np.eye(n)[i]
        JFa[:,i] = (F(p0+2*h*v) - 8*F(p0-h*v) + 8*F(p0+h*v) - F(p0+2*h*v)) / (12*h)
    return JFa



"""Estas nos aproximan la solucion del sistema con el jacobiano aproximado"""
def newton_approx1(F,h,p0,tol,maxiter):
    cont = 0    
    error = 10**4
    while cont < maxiter and error >= tol:
        y = la.solve(JF_approx1(F,p0,h),-F(p0))                
        p1 = p0 + y
        error = la.norm(p0-p1)
        cont += 1
        p0 = p1
    return p1, cont


newton_approx1(F,0.01,p0,10**(-4),50)

def newton_approx2(F,h,p0,tol,maxiter):
    cont = 0    
    error = 10**4
    while cont < maxiter and error >= tol:
        y = la.solve(JF_approx2(F,p0,h),-F(p0))                
        p1 = p0 + y
        error = la.norm(p0-p1)
        cont += 1
        p0 = p1
    return p1, cont

newton_approx2(F,0.01,p0,10**(-4),50)

#Representación gráfica
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


#Ejercicio 4

def euler(f,a,b,n,y0):
    h = (b-a)/n
    t = np.linspace(a,b,n+1)
    y = np.zeros(n+1)
    y[0] = y0
    for i in range(n):
        y[i+1] = y[i] + h*f(t[i],y[i]) 
    return (t,y)



def f(t,y):
    return (y/t)-(y/t)**2

y0 = 1
sol = euler(f,1,2,50,y0)
euler(f,1,2,50,y0)


def fun(x):
    return x/(1 + np.log(x))

plt.figure()
x= np.linspace(1,2)
plt.plot(sol[0],sol[1])
plt.show()




















        
        
        
        
        
        
        
        
        
        
        
        