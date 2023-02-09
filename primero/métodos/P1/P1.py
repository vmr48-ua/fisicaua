import numpy as np
import numpy.linalg as la
import scipy as sci
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D


#Ejercicio 1
A = np.random.randint(-4,8,[5,5])
rango = la.matrix_rank(A)
trans = np.transpose(A)
inver = la.inv(A)
cuadr = A*A

"""print(A,'\n','\n',rango,'\n','\n',trans,'\n','\n',inver,'\n','\n',cuadr,'\n')"""

#Ejercicio 2
b = np.random.randint(2,6,[5,1])
sol = la.solve(A,b)

"""print(A,'\n'), print(b,'\n'), print(sol,'\n','\n')"""

#Ejercicio 3
B = np.delete(A,4,0)
suma = np.sum(B,axis=0)
B = np.append(B,[suma],0)
det = la.det(B)
"""print(B,"\n\nDeterminante de B = {}, por tanto B no es invertible".format(round(det)))"""

#Ejercicio 4
A,A = np.delete(A,4,0),np.delete(A,3,0)
diagonalPri = np.diag(A)
diagonalSup = np.diag(A,1)
diagonalInf = np.diag(A,-1)
"""print(A,'\n','\n',diagonalPri,'\n','\n',diagonalSup,'\n','\n',diagonalInf)"""

#Ejercicio 5
def f(x):
    return np.exp(-3*x)*np.sin(x)

x = np.linspace(-1,0,100)
plt.plot(x,f(x))
plt.figure()

#Ejercicio 6 y 7
def g1(x):
    return np.sin(x)
def g2(x):
    return np.sin(x)**2
def g3(x):
    return np.cos(x)
def g4(x):
    return np.cos(x)**2

x = np.linspace(-2,2)

plt.plot(x,g1(x),label='sin(x)')
plt.plot(x,g2(x),label='sin²(x)')
plt.plot(x,g3(x),label='cos(x)')
plt.plot(x,g4(x),label='cos²(x)')
plt.legend()
plt.figure()

plt.subplot(2,2,1)
plt.plot(x,g1(x),'-',color='red') 
plt.title('sin(x)')  

plt.subplot(2,2,2)
plt.plot(x,g2(x),'-.',color='blue') 
plt.title('sin²(x)')  

plt.subplot(2,2,3)
plt.plot(x,g3(x),'.',color='green') 
plt.title('cos(x)')  

plt.subplot(2,2,4)
plt.plot(x,g4(x),'--',color='yellow') 
plt.title('cos²(x)') 

plt.subplots_adjust(hspace=0.35)

#Ejercicio 8
def h(x,y):
    return x**2*np.sin(x*y) + np.exp(-(x**2+y**2))

fig = plt.figure()
ax = Axes3D(fig)
x = np.linspace(-1,2,20)
y = np.linspace(-2,3,20)
X,Y = np.meshgrid(x,y)
Z = X**2+Y**2
ax.plot_surface(X,Y,Z)

#Ejercicio 9
def f2(x):
    return np.exp(x**3)*np.sin(x**2)

a = sci.integrate.quad(f2,-2,1)[0]
x = np.linspace(-2,1)
plt.plot(x,f2(x))
plt.fill_between(x, f2(x))
"""plt.show()"""

#Ejercicio 10
def fibonacci(n):
    fib=[1]
    if n > 1:
        fib.append(1)
        for i in range(n):
            fib.append(fib[len(fib)-1]+fib[len(fib)-2])
    return fib
"""print(fibonacci(1),fibonacci(20))"""

#Ejercicio 11
def fibonacci2(n):
    for i in range(n):
        a = fibonacci(i)
        if a[len(a)-1] > n:
            a.pop()
            return a
    return 0
"""print(fibonacci2(1000))"""

#Ejercicio 12
P = np.array([1,0,-0.25,1,2])
"""print('P(x) =\n',np.poly1d(P),'\nP(1) =',np.polyval(P,1),'\nP(-2) =',np.polyval(P,-2))"""

#Ejercicio 13
r = np.array([1,-1,3])
P = np.polynomial.polynomial.polyfromroots(r)[::-1]
Q = np.array([1,0,-3,2])
print('P(x) =\n',np.poly1d(P),'\nQ(x) =\n',np.poly1d(Q))
print('P(x)+Q(x) =\n',np.poly1d(np.polyadd(P,Q)))
print('P(x)-Q(x) =\n',np.poly1d(np.polysub(P,Q)))
print('P(x)*Q(x) =\n',np.poly1d(np.polymul(P,Q)))
