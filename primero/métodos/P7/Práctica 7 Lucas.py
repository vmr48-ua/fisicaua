# -*- coding: utf-8 -*-
"""
Created on Fri Mar 10 11:36:02 2023

@author: alumno
"""
import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
from scipy.interpolate import CubicSpline

"""DERIVADAS"""

#Ejercicio 1

def dy_tres(f,x0,h):
    return (f(x0+h)-f(x0-h))/(2.*h)

def dy_tres_discreto(x,y):
    h = x[1]-x[0]
    return (y[2]-y[0])/(2.*h)

def dy_cinco(f,x0,h):
    return (f(x0-2*h)-8*f(x0-h)+8*f(x0+h)-f(x0+2*h))/(12*h)

def dy_cinco_discreto(x,y):
    h = x[1] - x[0]
    return ((y[0]-8*y[1]+8*y[3])-y[4])/(12*h)
    

#Ejercicio 2

def f2(x):
    return np.exp(-2*x)*np.cos(3*x)

#a)
def df2(x):
    return -2*np.exp(-2*x)*np.cos(3*x) - 3*np.exp(-2*x)*np.sin(3*x)

x0 = 2

h = 0.1
print(dy_tres(f2,x0,h))
print(dy_cinco(f2,x0,h))
print("El valor de la derivada es: ",df2(x0))

h = 0.05
print(dy_tres(f2,x0,h))
print(dy_cinco(f2,x0,h))
print("El valor de la derivada es:",df2(x0))

""""Vemos que la aproximación de los cinco puntos es más precisa"""

#b)

x = np.array([0,5,10,15,20,25,30])
y = np.array([0,20,50,100,150,180,200])

#b.1)
v10 = dy_cinco_discreto(x[0:5],y[0:5])
v15 = dy_cinco_discreto(x[1:6],y[1:6])
v20 = dy_cinco_discreto(x[2:7],y[2:7])

#b.2)
v = np.array([v10,v15,v20])

a15 = dy_tres_discreto(x[2:5],v)

#b.3)
"""Podríamos calcular el polinomio interpolador y calcular sus
derivadas empleando h = 2s"""

S_Natural = CubicSpline(x,y,bc_type = 'natural')

h = 2

v10pol = dy_cinco(S_Natural,10,h)
v15pol = dy_cinco(S_Natural,15,h)
v20pol = dy_cinco(S_Natural,20,h)



"""INTEGRALES"""

#Ejercico 3
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


def simpson_cerrado(f,a,b):
    h = (b-a)/2
    return h*(f(a)+4*f((a+h)+f(b)))/3.

def simpson_abierto(f,a,b):
    h = (b-a)/4.
    return 4*h*(2*f(a+h)-f(a+2*h)+2*f(a+3*h))/3.

def simpson_cerrado_discreto(x,y):
    # x los dos extremos del intervalo
    # y los valores de la funcion en el intervalo y en el punto medio
    h = (x[1]-x[0])/2
    return h*(y[0]+4*y[1]+y[2])/3.


def simpson38_cerrado(f,a,b):
    h = (b-a)/3
    return 3*h*(f(a)+3*f(a+h)+3*f(a+2*h)+f(b))/8.

def simpson38_abierto(f,a,b):
    h = (b-a)/5
    return 5*h*(11*f(a+h)+f(a+2*h)+f(a+3*h)+11*f(a+4*h))

def simpson38_cerrado_discreto(x,y):
    h = (x[0]-x[1])/3.
    return 3*h*(y[0]+3*y[1]+3*y[2]+y[3])/8.



#Ejercicio 4

#a)

def f3a(x):
    return x*np.sin(x)

def f3b(x):
    return np.sin(x)/x

a,b = 0,np.pi/4

aproxint1 = simpson_cerrado(f3a,a,b)
aproxint2 = simpson_abierto(f3b,a,b)

xn1 = np.array([a,(a+b)/2,b])
yn1 = f3a(xn1)
P1 = np.polyfit(xn1,yn1,2)

xr = np.linspace(a,b,100)
yr1 = f3a(xr)

plt.figure()
plt.plot(xr,yr1,label="xsin(x)")
plt.plot(xn1,np.zeros(3), "o")
plt.plot(xr,np.polyval(P1,xr),label="Área aproximada")
plt.fill_between(xr, np.polyval(P1,xr), facecolor='orange', alpha=0.15)
plt.legend()
plt.title("Simpson cerrado de xsen(x)")
plt.show()

#Graficamos la segunda integral
h = (b-a)/4
xn2 = np.array([a+h,a+2*h,a+3*h])
yn2 = f3b(xn2)
P2 = np.polyfit(xn2,yn2,2)


yr2 = f3b(xr)

plt.figure()
plt.plot(xr,yr2,label="sin(x)/x")
plt.plot(xn2,np.zeros(3), "o")
plt.plot(xr,np.polyval(P2,xr),label="Área aproximada")
plt.fill_between(xr, np.polyval(P2,xr), facecolor='orange', alpha=0.15)
plt.legend()
plt.title("Simpson abierto de sen(x)/x")
plt.show()


#b)

x4 = np.array([0.0,0.12,0.22,0.32,0.36,0.40,0.44])
y4 = np.array([0.2,1.309729,1.305241,1.743393,2.074903,2.456,2.542985])

tramo1 = trapecio_cerrado_discreto(x4[0:2], y4[0:2])
tramo2 = simpson_cerrado_discreto(x4[1:4], y4[1:4])
tramo3 = simpson38_cerrado_discreto(x4[3:8], y4[3:8])

P1 = np.polyfit(x4[0:2], y4[0:2],1)
P2 = np.polyfit(x4[1:4], y4[1:4],2)
P3 = np.polyfit(x4[3:8], y4[3:8],3)

xr = np.linspace(0,0.44,100)

xt1 = np.linspace(0,x4[1],100)
xt2 = np.linspace(x4[1],x4[3],100)
xt3 = np.linspace(x4[3],x4[6],100)

plt.figure()
plt.plot(x4,y4,"o")
plt.plot(xt1,np.polyval(P1,xt1), label = "Trapecio")
plt.plot(xt2,np.polyval(P2,xt2), label = "Simpson")
plt.plot(xt3,np.polyval(P3,xt3), label = "Simpson 38")
plt.fill_between(xt1, np.polyval(P1,xt1), facecolor='orange', alpha=0.15)
plt.fill_between(xt2, np.polyval(P2,xt2), facecolor='green', alpha=0.15)
plt.fill_between(xt3, np.polyval(P3,xt3), facecolor='red', alpha=0.15)
plt.legend()
plt.show()



#Ejercicio 5 

def trapecio_compuesto(f,a,b,n):
    xi = np.linspace(a,b,n+1)
    suma = 0.
    for i in range(n):
        suma += trapecio_cerrado(f,xi[i],xi[i+1])     
    return suma

def simpson_compuesto(f,a,b,n):
    xi = np.linspace(a,b,n+1)
    suma = 0.
    for i in range(n):
        suma += simpson_cerrado(f,xi[i],xi[i+1])     
    return suma


#Ejercicio 6

def f6(x):
    return (np.sin(2*x) + x)

a, b = 1, 3

trapecio_cerrado(f6, a, b)
simpson_cerrado(f6, a, b)
simpson38_cerrado(f6, a, b)

n = 52
trapecio_compuesto(f6, a, b, n)
simpson_compuesto(f6, a, b, n)


quad(f6, a, b)[0]





