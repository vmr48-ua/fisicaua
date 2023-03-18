import numpy as np
import numpy.polynomial.polynomial as npol
from numpy import cos,sin,sqrt,pi,arctan,log
import matplotlib.pyplot as plt
from scipy.integrate import quad

def aproximante(fun,a,b,grado,type='a',plot=False,showPol=False):
    def chebysevAB(fun,a,b,grado,plot=False,showPol=False):
        poly,coef = [],[]
        def wChT(x,a,b): # Función peso llevada al intervalo [a,b]
            return 1/(1-(-1+(2*(x-a))/(b-a))**2)**(0.5)
        def BaseChebysev(n): # Calcula la base ortogonalizada de grado n
            if n == 0:
                return np.array([1.])
            else:
                T = [np.array([1.]),np.array([1.,0])]  
                while len(T) < n+1:
                    T.append(np.polysub(np.polymul(np.array([2.,0]),T[-1]),T[-2]))
                return np.array(T,dtype=object)[n]
        def printCheb(fun,a,b,grado,poli): # Imprime el polinomio con la función
            x = np.linspace(a,b)
            plt.plot(x,np.polyval(poli,x),label='Aprox. Chebysev {}'.format(grado))
            plt.legend(loc = 'best')

        for i in range(grado+1):
            poly.append(npol.polyfromroots(a+((np.roots(BaseChebysev(i))+1)*(b-a))/2)[::-1])
            coef.append(quad(lambda t:fun(t)*wChT(t,a,b)*np.polyval(poly[i],t),a,b)[0]/quad(lambda t:wChT(t,a,b)*np.polyval(poly[i],t)**2,a,b)[0])

        polinomio = np.array([0])
        for i in range (grado+1):
            polinomio = np.polyadd(coef[i]*poly[i],polinomio)
        if plot:
            printCheb(fun,a,b,grado,polinomio)
        if showPol:
            print('Polinomio aproximante de Chebysev de grado {}:'.format(grado))
            print(np.poly1d(polinomio),'\n')
        return polinomio

    def legendreAB(fun,a,b,grado,plot=False,showPol=False):
        poly,coef = [],[]
        # La función peso será siempre 1 para cualquier intervalo [a,b]
        def BaseLegendre(n): # Calcula la base ortogonalizada de grado n
            if n == 0:
                return np.array([1])
            if n == 1:
                return np.array([1,0])
            return np.polyadd(((2*(n-1)+1)/(n))*np.polymul(np.array([1,0]),BaseLegendre(n-1)),(-(n-1))/(n)*BaseLegendre(n-2))
        def printLeg(fun,a,b,grado,poli): # Imprime el polinomio con la función
            x = np.linspace(a,b)
            plt.plot(x,np.polyval(poli,x),label='Aprox. Legendre {}'.format(grado))
        
        for i in range(grado+1):
            poly.append(npol.polyfromroots(a+((np.roots(BaseLegendre(i))+1)*(b-a))/2)[::-1])
            coef.append(quad(lambda t:fun(t)*np.polyval(poly[i],t),a,b)[0]/quad(lambda t:np.polyval(poly[i],t)**2,a,b)[0])

        polinomio = np.array([0])
        for i in range (grado+1):
            polinomio = np.polyadd(coef[i]*poly[i],polinomio)

        if plot:
            printLeg(fun,a,b,grado,polinomio)
        if showPol:
            print('Polinomio aproximante de Legendre de grado {}:'.format(grado))
            print(np.poly1d(polinomio),'\n')
        return polinomio
    
    if plot:
        plt.figure()
    
    if type == 'a':
        ans = [chebysevAB(fun,a,b,grado,plot,showPol),legendreAB(fun,a,b,grado,plot,showPol)]
    elif type == 'c':
        ans = chebysevAB(fun,a,b,grado,plot,showPol)
    elif type == 'l':
        ans = legendreAB(fun,a,b,grado,plot,showPol)
        
    if plot:
        x = np.linspace(a,b)
        plt.plot(x,fun(x),label='Función')
        plt.xlabel('Abscisas')
        plt.ylabel('Ordenadas')
        plt.legend(loc = 'best')
        plt.grid()
        plt.show()

    return ans

def g(x):
    return cos(arctan(x))-np.exp(x**2)*log(x+2)

aproximante(g,2,5,4,plot=True,showPol=True)