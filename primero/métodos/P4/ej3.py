import numpy as np
import numpy.polynomial.polynomial as npol
from numpy import cos,sin,sqrt,pi,arctan,log
import matplotlib.pyplot as plt
import scipy.interpolate as interpol
import scipy.linalg as la
from scipy.integrate import quad
from scipy.interpolate import CubicSpline, pade, CubicSpline


def chebysevAB(a,b,grado):
    poly = []
    def BaseChebysev(n): # Calcula la base ortogonalizada de grado n
        if n == 0:
            return np.array([1.])
        else:
            T = [np.array([1.]),np.array([1.,0])]  
            while len(T) < n+1:
                T.append(np.polysub(np.polymul(np.array([2.,0]),T[-1]),T[-2]))
            return np.array(T,dtype=object)[n]

    for i in range(grado+1):
        poly.append(npol.polyfromroots(a+((np.roots(BaseChebysev(i))+1)*(b-a))/2)[::-1])
    return poly

def legendreAB(a,b,grado):
    poly = []
    def BaseLegendre(n): # Calcula la base ortogonalizada de grado n
        if n == 0:
            return np.array([1])
        if n == 1:
            return np.array([1,0])
        return np.polyadd(((2*(n-1)+1)/(n))*np.polymul(np.array([1,0]),BaseLegendre(n-1)),(-(n-1))/(n)*BaseLegendre(n-2))
    
    for i in range(grado+1):
        poly.append(npol.polyfromroots(a+((np.roots(BaseLegendre(i))+1)*(b-a))/2)[::-1])
    return poly

baseCheb = chebysevAB(3, 6, 3)
baseLege = legendreAB(3, 6, 3)
print('Chebysev:')
for i in baseCheb:
    print(np.poly1d(i))
print('Legendre:')
for i in baseLege:
    print(np.poly1d(i))