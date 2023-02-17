import numpy as np
import numpy.linalg as la
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad

#Ejercicio 1
def f(x):
    return 1/(1+x**2)
xnod = np.linspace(-1,1,5)
P = np.polyfit(xnod,f(xnod),4)

x = np.linspace(-1,1)

plt.figure()
plt.plot(x,f(x),label='función')
plt.plot(x,np.polyval(P,x),label='interpolador')
plt.scatter(xnod,f(xnod))
plt.legend()

#Ejercicio 2
def PolLagrange(x,y):
    return la.solve(np.vander(x), y)

#Ejercicio 3
xn = np.sort(np.random.uniform(-5,4,size=6))
yn = np.random.uniform(-2,6,size=6)

L = PolLagrange(xn,yn)
P = np.polyfit(xn,yn,5)

x = np.linspace(-5,4)
plt.figure()
plt.plot(x,np.polyval(L,x),label='Lagrange')
plt.plot(x,np.polyval(P,x),label='Polyfit')
plt.scatter(xn,yn)
plt.legend()

#Ejercicio 4
def g(x):
    return np.exp(x)*np.cos(3*x)

xn = np.array([-1.5,-0.75,0.1,1.5,2,2.7])
L = PolLagrange(xn,g(xn))

x = np.linspace(-2,3)
plt.figure()
plt.ylim(-40,40)
plt.plot(x,g(x),label='Función')
plt.plot(x,np.polyval(L,x),label='Lagrange')
plt.scatter(xn,g(xn))
plt.legend()

#Ejercicio 5
def dif_divididas(x,y):
    n = len(x)-1
    A = np.zeros((n+1,n+1))
    A[:,0] = y
    for j in range(1,n+1):
        for i in range(n-j+1):
            A[i,j] = (A[i+1][j-1]-A[i][j-1])/(x[i+j]-x[i])
    return A

#Ejercicio 6
def PolNewton(x,y):
    dif = dif_divididas(x,y)[0,:]
    pol = np.zeros(len(x))
    for i in range(len(x)):
        pol = np.polyadd(pol,dif[i]*npol.polyfromroots(x[:i])[::-1])
    return pol

#Ejercicio 7
xn = np.array([-1.5,-0.75,0,1,1.5,2,2.7])
N = PolNewton(xn,g(xn))
L = PolLagrange(xn,g(xn))
P = np.polyfit(xn,g(xn),len(xn)-1)

x = np.linspace(-2,3)
plt.figure()
plt.ylim(-40,40)
plt.plot(x,g(x),label='Función')
plt.plot(x,np.polyval(L,x),label='Lagrange')
plt.plot(x,np.polyval(P,x),label='Polyval')
plt.plot(x,np.polyval(N,x),label='Newton')
plt.scatter(xn,g(xn))
plt.legend()

"""
print('Lagrange\n',np.poly1d(L))
print('Newton\n',np.poly1d(N))
print('Polyfit\n',np.poly1d(P))
"""

#Ejercicio 8
def h(x):
    return np.cos(x)**5

x6 = np.linspace(0,2,7)
x8 = np.linspace(0,2,9)
x10 = np.linspace(0,2,11)
P6 = np.polyfit(x6,h(x6),len(x6)-1)
P8 = np.polyfit(x8,h(x8),len(x8)-1)
P10 = np.polyfit(x10,h(x10),len(x10)-1)

def er6(x):
    return abs(np.polyval(P6,x)-h(x))
def er8(x):
    return abs(np.polyval(P8,x)-h(x))
def er10(x):
    return abs(np.polyval(P10,x)-h(x))

x = np.linspace(0,2,200)
plt.figure()
plt.plot(x,h(x),label='Función',color='blue')
plt.errorbar(x,np.polyval(P6,x),label='grado 6',color='red',yerr=er6(x))
plt.errorbar(x,np.polyval(P8,x),label='grado 8',color='yellow',yerr=er8(x))
plt.errorbar(x,np.polyval(P10,x),label='grado 10',color='green',yerr=er10(x))
plt.scatter(x6,h(x6),color='red')
plt.scatter(x8,h(x8),color='yellow')
plt.scatter(x10,h(x10),color='green')
plt.fill_between(x,h(x)+er6(x),h(x)-er6(x),color='red',alpha=0.4)
plt.fill_between(x,h(x)+er8(x),h(x)-er8(x),color='yellow',alpha=0.4)
plt.fill_between(x,h(x)+er10(x),h(x)-er10(x),color='green',alpha=0.4)
plt.legend()

#Ejercicio 9
def erf(x):
    return(2/np.pi**0.5)*(quad(lambda t:np.exp(-t**2),0,x)[0])

erfi = np.vectorize(erf)    
xn = 0.2*np.linspace(0,5,6)
yn = erfi(xn)

PL = np.polyfit(xn,yn,1)
PC = np.polyfit(xn,yn,2)

print('erf(1/3)   =',round(erf(1/3),4))
print('lineal     =',round(np.polyval(PL,1/3),4),'Error =',round(abs(np.polyval(PL,1/3)-erf(1/3)),4))
print('cuadrática =',round(np.polyval(PC,1/3),4),'Error =',round(abs(np.polyval(PC,1/3)-erf(1/3)),4))


#Ejercicio 10
def nodCheb(a,b,n):
    nod = []
    for i in range(n+1):
        nod.append((a+b)/2-(b-a)/2*np.cos(((2*i+1)*np.pi)/(2*(n+1))))
    return np.array(nod)

#Ejercicio 11

plt.figure()

x = np.linspace(-2,3,200)
plt.plot(x,g(x),label='Función')

xn = np.array([-1.5,-0.75,0.1,1.5,2,2.7])
L = PolLagrange(xn,g(xn))
plt.plot(x,np.polyval(L,x),label='Lagrange')

xc = nodCheb(-2,3,5)
C = np.polyfit(xc,g(xc),5)
plt.plot(x,np.polyval(C,x),label='Chebysev')

plt.scatter(xc,g(xc))
plt.legend()

print(xc)
plt.show()
