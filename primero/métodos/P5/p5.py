import numpy as np
import numpy.polynomial.polynomial as npol
from numpy import cos,sin,sqrt,pi,arctan,log
import matplotlib.pyplot as plt
import scipy.interpolate as interpol
import scipy.linalg as la
from scipy.integrate import quad
from scipy.interpolate import CubicSpline, pade, CubicSpline

def fourier(fun,x,l,n): #(funcion,x,periodo(=2l),orden)
    def a(fun,x,l,k):
        return (1/l)*quad(lambda x: fun(x)*cos(k*pi*x/l), -l, l)[0]
    def b(fun,x,l,k):
        return (1/l)*quad(lambda x: fun(x)*sin(k*pi*x/l), -l, l)[0]

    ans = 1/(2*l)*quad(fun,-l,l)[0]
    for i in range(1,n+1):
        ans += a(fun,x,l,i)*cos(i*pi*x/l)
    for i in range(1,n):
        ans += b(fun,x,l,i)*sin(i*pi*x/l)
    return ans

# def f2(x):
#     if -2 <= x < 1:
#         return 1.
#     else:
#         return x

# grado = 3
# L = 2

# f2 = np.vectorize(f2)
# xa = np.linspace(-2,2,200)

# plt.figure()
# plt.plot(xa,f2(xa),label='función')
# plt.plot(xa,fourier(f2,xa,L,grado),label='Aproximación Trigonométrica')
# plt.legend(loc='best')


# grado = 4
# L = pi
# def f3a(x):
#     if -pi <= x < pi:
#         return x+pi

# f3a = np.vectorize(f3a)
# xa = np.linspace(-pi,pi,200)

# plt.figure()
# plt.plot(xa,f3a(xa),label='función')
# plt.plot(xa,fourier(f3a,xa,L,grado),label='Aproximación Trigonométrica')
# plt.legend(loc='best')

def f3b(x):
    if 0 <= x < np.pi:
        return 1
    elif -np.pi <= x < 0:
        return -1
    else:
        return 0

f3b = np.vectorize(f3b)
xa = np.linspace(-np.pi,np.pi,2000)

grado = 10
L = np.pi

plt.figure()
plt.title('lol')
plt.plot(xa,f3b(xa),label='función')
plt.plot(xa,fourier(f3b,xa,L,grado),label='Aproximación Trigonométrica')
plt.legend(loc='best')

# L = pi
# def f4(x):
#     if -2*pi <= x < 0:
#         return -x
#     else:
#         return x

# f4 = np.vectorize(f4)
# xa = np.linspace(-1,1,200)

# plt.figure()
# plt.plot(xa,f4(xa),label='función')
# plt.plot(xa,fourier(f4,xa,L,2),label='Aproximación Trigonométrica grado 2')
# plt.plot(xa,fourier(f4,xa,L,3),label='Aproximación Trigonométrica grado 3')
# plt.plot(xa,fourier(f4,xa,L,4),label='Aproximación Trigonométrica grado 4')
# plt.legend(loc='best')

plt.show()
