import numpy as np
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from numpy import e,cos,sin,pi,sqrt,log
from scipy.integrate import quad
import scipy.linalg as la
from scipy.optimize import fmin

###############
# Ejercicio 1 #
###############

def simpson_abierto(f,a,b):
    h = (b-a)/4.
    return 4*h*(2*f(a+h)-f(a+2*h)+2*f(a+3*h))/3.

def simpson_cerrado(f,a,b):
    h = (b-a)/2
    return h*(f(a)+4*f((a+h)+f(b)))/3.

def simpson_compuesto(f,a,b,n):
    xi = np.linspace(a,b,n+1)
    suma = 0.
    for i in range(n):
        suma += simpson_abierto(f,xi[i],xi[i+1])     
    return suma

def f(x):
    return log(x)*cos(x)+2

a,b = 3,6
xn = np.array([a,(a+b)/2,b])
yn = f(xn)
P = np.polyfit(xn,yn,2)

x = np.linspace(a,b)
plt.figure()
plt.plot(x,f(x),label='función')
plt.plot(x,np.polyval(P,x),label="Área aproximada")
plt.fill_between(x, np.polyval(P,x), facecolor='orange', alpha=0.15)
plt.legend()
#plt.show()

n=1
while(abs(quad(f, a, b)[0]-simpson_compuesto(f,a,b,n))>10e-3):
    n+=1

# print(abs(quad(f, a, b)[0]-simpson_compuesto(f,a,b,n)))
# print(quad(f, a, b)[0], '\t',simpson_compuesto(f,a,b,n))




###############
# Ejercicio 2 #
###############

A = np.array([[1,4,0],[-2,1,1],[2,1,1]],dtype=float)

def cambio_filas(A,i,j):
    subs = np.copy(A[i])
    A[i] = A[j]
    A[j] = subs
    return A
def suma_filas(A,i,j,c):
    subs = np.copy(A[i])
    A[i] = subs + c*A[j]
    return A

def LU(A):
    Ac = A.copy()
    n = len(Ac)
    for i in range(n):
        piv = np.argmax(abs(Ac[i::,i]))+i
        cambio_filas(Ac,piv,i)
        for j in range(i+1,n):
            suma_filas(Ac,j,i,-Ac[j,i]/Ac[i,i])
    C = np.copy(Ac)
    return np.dot(A,la.inv(C)),Ac

L,U = LU(A)[0],LU(A)[1]
p, l, u = la.lu(A)

# print(L,'\n',l)
# print('\n')
# print(U,'\n', u)
# print(np.dot(L,U))
# print(np.dot(l,u))





###############
# Ejercicio 3 #
###############

def g(x):
    return 2*cos(x)*log(x)-3

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

print(busqueda_incremental(g,4,8,50)) # [5.68 5.76] y [6.96 7.04]

a,b = busqueda_incremental(g,4,8,50)[0,0], busqueda_incremental(g,4,8,50)[0,1]
print(biseccion(g, a, b, 10**-6)) # 5.743865966796875 en 15 iteraciones
a,b = busqueda_incremental(g,4,8,50)[1,0], busqueda_incremental(g,4,8,50)[1,1]
print(biseccion(g, a, b, 10**-6)) # 6.971241455078125 en 15 iteraciones

plt.figure()
x = np.linspace(4,8)
plt.plot(x,g(x))
plt.show()



###############
# Ejercicio 4 #
###############

def F(w):
    x,y=w
    return np.array([4*x**2+y+2,-2*x+y**2-7])

def JF_approx2(F,p0,h):
    n = len(p0)
    JFa = np.zeros((n,n))
    for i in range(n):
        v = np.eye(n)[i]
        JFa[:,i] = (F(p0+2*h*v) - 8*F(p0-h*v) + 8*F(p0+h*v) - F(p0+2*h*v)) / (12*h)
    return JFa

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

#Representación gráfica
xd = np.linspace(-5,5,100)
yd = np.linspace(-5,5,100)
C = [0]
X, Y = np.meshgrid(xd, yd)
plt.figure()
plt.contour(X,Y,F([X,Y])[0],C,colors='red')
plt.contour(X,Y,F([X,Y])[1],C,colors='blue')
plt.xlabel('Eje X')
plt.ylabel('Eje Y')
plt.grid(True)

p0=[-1,-2]
print(newton_approx2(F,10e-2,p0,10**(-4),50))

p0=[1,-2]
print(newton_approx2(F,10e-2,p0,10**(-4),50))
plt.show()




###############
# Ejercicio 5 #
###############
def trapecio_cerrado(f,a,b):
    h = b-a
    return h*(f(a)+f(b))/2.

def trapecio_compuesto(f,a,b,n):
    xi = np.linspace(a,b,n+1)
    suma = 0.
    for i in range(n):
        suma += trapecio_cerrado(f,xi[i],xi[i+1])     
    return suma

def ITC(f,a,b,eps):
    n = 1
    I = trapecio_compuesto(f,a,b,n)
    n += 1
    Ik = trapecio_compuesto(f,a,b,n)
    while(abs(I-Ik)>eps):
        n += 1
        I = Ik
        Ik = trapecio_compuesto(f,a,b,n)
    return Ik

print(ITC(f,3,6,10**(-4)))