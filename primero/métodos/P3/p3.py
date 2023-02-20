import numpy as np
import numpy.polynomial.polynomial as npol
from numpy import cos,sin,sqrt,pi
import matplotlib.pyplot as plt
import scipy.interpolate as interpol
import scipy.linalg as la
from scipy.integrate import quad
from scipy.interpolate import CubicSpline, pade, CubicSpline

#Ejercicio 1
x = np.array([1,2,3,4])
y = np.array([0.5,4,2,0.7])

S_Natural = CubicSpline(x,y,bc_type = 'natural')
S_Frontera = CubicSpline(x,y,bc_type = ((1,0.3),(1,-0.3)))

"""print(S_Frontera.c)"""

#Ejercicio 2
def f(t):
    return 100/(2+999*np.exp(-2.1*t))

def df(t):
    return 209790*np.exp(-21*t/10)/(999*np.exp(-21*t/10)+2)**2

def nodCheb(a,b,n):
    nod = []
    for i in range(n+1):
        nod.append((a+b)/2-(b-a)/2*np.cos(((2*i+1)*np.pi)/(2*(n+1))))
    return np.array(nod)

x4 = np.linspace(0,7,5)
x6 = np.linspace(0,7,7)
x8 = np.linspace(0,7,9)

c4 = nodCheb(0,7,4)
c6 = nodCheb(0,7,6)
c8 = nodCheb(0,7,8)

N4 = np.polyfit(x4,f(x4),4)
N6 = np.polyfit(x6,f(x6),6)
N8 = np.polyfit(x8,f(x8),8)

C4 = np.polyfit(c4,f(c4),4)
C6 = np.polyfit(c6,f(c6),6)
C8 = np.polyfit(c8,f(c8),8)

x = np.linspace(0,7,6)
y = f(x)

SNat = CubicSpline(x,y,bc_type = 'natural')
SFro = CubicSpline(x,y,bc_type = ((1,df(0)),(1,df(7))))

x = np.linspace(0,7,100)
plt.figure()
plt.subplot(1,3,1)
plt.plot(x,f(x),label='Función')
plt.plot(x,np.polyval(N4,x),'--',label='Interpolador Grado 4')
plt.plot(x,np.polyval(N6,x),'--',label='Interpolador Grado 6')
plt.plot(x,np.polyval(N8,x),'--',label='Interpolador Grado 8')
plt.grid()
plt.legend(loc='best')

plt.subplot(1,3,2)
plt.plot(x,f(x),label='Función')
plt.plot(x,np.polyval(C4,x),'--',label='Pol Chebysev Grado 4')
plt.plot(x,np.polyval(C6,x),'--',label='Pol Chebysev Grado 6')
plt.plot(x,np.polyval(C8,x),'--',label='Pol Chebysev Grado 8')
plt.grid()
plt.legend(loc='best')

plt.subplot(1,3,3)
plt.plot(x,f(x),label='Función')
plt.plot(x,SNat(x),'--',label='Spline Natural')
plt.plot(x,SFro(x),'--',label='Spline Frontera')
plt.grid()
plt.legend(loc='best')

#Ejercicio 3
def modelo_discreto_general(x,y,m):
    sol = []
    base = [np.array([1.]+i*[0]) for i in range(m+1)] #fis

    for i in range(m+1):
        sol.append(np.zeros(m+1))
    sol = np.array(sol)

    for i in range(len(sol)):
        for j in range(len(sol[0])):
            sol[i][j] = np.dot(np.polyval(base[j],x),np.polyval(base[i],x))

    terms = np.zeros(m+1)
    for i in range(m+1):
        terms[i] = np.dot(y,np.polyval(base[i],x)) #(f,fi)

    return la.solve(sol,terms)[::-1] #orden decreciente

#Ejercicio 4
x = np.array([0,0.1,0.2,0.3,0.4])
y = np.array([0,0.078,0.138,0.192,0.244])
A = []

for i in range(4):
    A.append(modelo_discreto_general(x,y,i+1))

P = np.polyfit(x,y,4)

print(np.poly1d(P))
print(np.poly1d(A[3]))

xplot = np.linspace(0,0.4)
plt.figure()
plt.scatter(x,y)
for i in range(4):
    plt.plot(xplot,np.polyval(A[i],xplot),label='Polinomio de grado {}'.format(i+1))
plt.grid()
plt.legend(loc='best')

#Ejercicio 5
x = np.array([0,900,1800,2700])
y = np.array([17.6,40.4,67.7,90.1])
B = []

for i in range(2):
    B.append(modelo_discreto_general(x,y,i+1))

xplot = np.linspace(0,2700)
plt.figure()
plt.scatter(x,y)
for i in range(2):
    plt.plot(xplot,np.polyval(B[i],xplot),label='Polinomio de grado {}'.format(i+1))
plt.grid()
plt.legend(loc='best')

print(np.poly1d(B[0]))
print(np.poly1d(B[1]))
print('Error máximo del polinomio grado 1:',max(abs(np.polyval(B[0],0)-y[0]), abs(np.polyval(B[0],900)-y[1]), abs(np.polyval(B[0],1800)-y[2]), abs(np.polyval(B[0],2700)-y[3])))
print('Error máximo del polinomio grado 2:',max(abs(np.polyval(B[1],0)-y[0]), abs(np.polyval(B[1],900)-y[1]), abs(np.polyval(B[1],1800)-y[2]), abs(np.polyval(B[1],2700)-y[3])))

plt.show()
