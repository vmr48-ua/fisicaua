import numpy as np
import numpy.polynomial.polynomial as npol
from numpy import cos,sin,sqrt,pi
import matplotlib.pyplot as plt
import scipy.interpolate as interpol
import scipy.linalg as la
from scipy.integrate import quad
from scipy.interpolate import CubicSpline, pade, CubicSpline

def dy_cinco(f,x0,h):
    return (f(x0-2*h)-8*f(x0-h)+8*f(x0+h)-f(x0+2*h))/(12*h)

def solucionD(A,b):
    n = len(A)
    x = np.zeros(n)
    for i in range(n-1,-1,-1):
        x[i] = b[i]/A[i,i]
    return x

def solucionUpp(A,b):
    n = len(b)
    sol = np.zeros(n)
    for i in range(n-1,-1,-1):
        suma = 0
        for j in range(i,n,1):
            suma += A[i,j]*sol[j]
        sol[i] = (b[i]-suma)/A[i,i]
    return sol

def solucionLow(A,b):
    n = len(b)
    sol = np.zeros(n)
    for i in range(n):
        suma = 0
        for j in range(i,-1,-1):
            suma += A[i,j]*sol[j]
        sol[i] = (b[i]-suma)/A[i,i]
    return sol

def cambio_filas(A,i,j):
    subs = np.copy(A[i])
    A[i] = A[j]
    A[j] = subs
    return A
    
def suma_filas(A,i,j,c):
    subs = np.copy(A[i])
    A[i] = subs + c*A[j]
    return A

def prod_fila(A,i,c):
    subs = np.copy(A[i])
    A[i] = c*subs
    return A

def pivote_parcial(a):
    indice = 0
    for i in range(len(a)):
        if abs(a[i])>abs(a[indice]):
            indice = i
    return indice

def gauss_parcial(A,b):
    n = len(A)
    for i in range(n-1):
        indice_pivote = pivote_parcial(A[i:n,i]) + i
        cambio_filas(A,indice_pivote,i)
        cambio_filas(b,indice_pivote,i)
        for j in range(i+1,n,1):
            if A[j,i] != 0:
                suma_filas(b,j,i,-A[j,i]/A[i,i])
                suma_filas(A,j,i,-A[j,i]/A[i,i])
    return solucionUpp(A,b)

def pivote_parcial_escalado(a):
    indice = 0
    for i in range(len(a)):
        if abs(a[i,0])/np.max(np.abs(a[i,::]))>abs(a[indice,0])/np.max(np.abs(a[indice,::])):
            indice = i
    return indice

def gauss_parcial_escalado(A,b):
    n = len(A)
    for i in range(n-1):
        indice_pivote = pivote_parcial_escalado(A[i:n,i:n]) + i
        cambio_filas(A,indice_pivote,i)
        cambio_filas(b,indice_pivote,i)
        for j in range(i+1,n,1):
            if A[j,i] != 0:
                suma_filas(b,j,i,-A[j,i]/A[i,i])
                suma_filas(A,j,i,-A[j,i]/A[i,i])
    return solucionUpp(A,b)

def inversa(A):
    n = len(A)
    I = np.zeros([n,n])
    for i in range(n):
        I[i,i] = 1
    for i in range(n-1):
        indice_pivote = pivote_parcial(A[i:n,i]) + i
        cambio_filas(A,indice_pivote,i)
        cambio_filas(I,indice_pivote,i)
        for j in range(i+1,n,1):
            if A[j,i] != 0:
                suma_filas(I,j,i,-A[j,i]/A[i,i])
                suma_filas(A,j,i,-A[j,i]/A[i,i])
    for i in range(n):
        prod_fila(I,i,1/A[i,i])
        prod_fila(A,i,1/A[i,i])
    for i in range(n-1,0,-1):
        for j in range(i):
            suma_filas(I,j,i,-A[j,i])
            suma_filas(A,j,i,-A[j,i])
    return I

def LU(A):
    B = np.copy([A])
    n = len(A)
    for i in range(n):
        indice_pivote = pivote_parcial(A[i:n,i]) + i
        cambio_filas(A,indice_pivote,i)
        for j in range(i+1,n):
            suma_filas(A,j,i,-A[j,i]/A[i,i])
    C = np.copy(A)
    return np.dot(B,inversa(C)),A

def iteracion_SEL(x0,B,c):
    return np.dot(B,x0) + c

def M_Jacobi(A,b):
    n = len(A)
    D = np.zeros([n,n])
    U = np.zeros([n,n])
    L = np.zeros([n,n])
    for i in range(n):
        for j in range(n):
            if i==j:
                D[i,j] = A[i,j]
            if i > j:
                L[i,j] = A[i,j]
            if i < j:
                U[i,j] = A[i,j]
    B = np.dot(inversa(D),U+L)
    return B,np.dot(inversa(D),b),max(np.abs(la.eig(B)[0]))

def M_Gauss_Seidel(A,b):
    n = len(A)
    D = np.zeros([n,n])
    U = np.zeros([n,n])
    L = np.zeros([n,n])
    for i in range(n):
        for j in range(n):
            if i==j:
                D[i,j] = A[i,j]
            if i > j:
                L[i,j] = A[i,j]
            if i < j:
                U[i,j] = A[i,j]
    B = np.dot(inversa(D-L),U)
    return B,np.dot(inversa(D-L),b),max(np.abs(la.eig(B)[0]))

def jacobi(A,b,x0,norm,error,maxi):
    B,c,re = M_Jacobi(A,b)
    if re >= 1:
        print('Error: Radio espectral mayor que uno,por tanto, el método no converge')
        return
    else:
        n = 1
        x1 = iteracion_SEL(x0,B,c)
        while n < maxi:
            if abs(la.norm(x1-x0,norm)) < error:
                break
            n += 1
            x0 = x1
            x1 = iteracion_SEL(x1,B,c)
    return x1,n,re

def gauss_seidel(A,b,x0,norm,error,maxi):
    B,c,re = M_Gauss_Seidel(A,b)
    if re >= 1:
        print('Error: Radio espectral mayor que uno,por tanto, el método no converge')
        return
    else:
        n = 1
        x1 = iteracion_SEL(x0,B,c)
        while n < maxi:
            if abs(la.norm(x1-x0,norm)) < error:
                break
            n += 1
            x0,x1 = x1,iteracion_SEL(x1,B,c)
    return x1,n,re

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
    n = int(np.log2(np.abs((b-a)/tol))) + 1
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

def f2(x):
    return np.exp(-x/10)*np.cos(x)

def ceros_biseccion(f,a,b,tol):
    ceros = []
    for x in busqueda_incremental(f, a, b, int((b-a)*1000)):
        ceros.append(biseccion(f, x[0], x[1], tol))
    return  ceros

def newton_5ptos(f,p0,tol,maxiter):
    p1= p0 - f(p0)/dy_cinco(f,p0,10**(-2))
    n = 1
    while abs(p1-p0) > tol:
        n +=  1
        p0 = p1
        p1 = p0 - f(p0)/dy_cinco(f,p0,10**(-2))
    return p1,n

def ceros_newton_5ptos(f,a,b,tol,maxiter):
    ceros = []
    for x in busqueda_incremental(f, a, b, int((b-a)*1000)):
        ceros.append(newton_5ptos(f, (x[0]+x[1])/2,tol,maxiter))
    return  ceros

def secante(f,p0,p1,tol,maxiter):
    p2= p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    n = 1
    while abs(p2-p1) > tol:
        n +=  1
        p0 = p1
        p1 = p2
        p2 = p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    return p2,n

def ceros_secante(f,a,b,tol,maxiter):
    ceros = []
    for x in busqueda_incremental(f, a, b, int((b-a)*1000)):
        ceros.append(secante(f, x[0],x[1],tol,maxiter))
    return  ceros

def regula_falsi(f,p0,p1,tol,maxiter):
    p2= p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    p3 = p2 - f(p2)*((p2-p1)/(f(p2)-f(p1)))
    n = 1
    while abs(p2-p1) > tol:
        n +=  1
        p0 = p1
        p1 = p2
        p2 = p3
        if f(p2)*f(p1) < 0:
             p3 = p2 - f(p2)*((p2-p1)/(f(p2)-f(p1)))
        else:
             p3 = p2 - f(p2)*((p2-p0)/(f(p2)-f(p0)))   
    return p2,n

def ceros_regula_falsi(f,a,b,tol,maxiter):
    ceros = []
    for x in busqueda_incremental(f, a, b, int((b-a)*1000)):
        ceros.append(regula_falsi(f, x[0],x[1],tol,maxiter))
    return  ceros

def punto_fijo_sist(G,p0,tol,maxiter):
    p1 = G(p0)
    n = 1
    while la.norm(np.array(p0)-np.array(p1))>=tol and n < maxiter:
        p0,p1 = p1,G(p1)
        n += 1
    return p1,n

def newton_sist(F,JF,p0,tol,maxiter):
    p1 = la.solve(JF(p0),-F(p0)) + p0 
    n = 1
    while la.norm(p0-p1)>=tol and n < maxiter:
        p0,p1 = p1,la.solve(JF(p1),-F(p1)) + p1
        n += 1
    return p1,n

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
        JFa[:,i] = (F(p0-2*h*v)-8*F(p0-h*v)+8*F(p0+h*v)-F(p0+2*h*v))/(12*h)
    return JFa

def newton_approx1(F,p0,tol,maxiter):
    p1 = la.solve(JF_approx1(F,p0,0.01),-F(p0)) + p0 
    n = 1
    while la.norm(p0-p1)>=tol and n < maxiter:
        p0,p1 = p1,la.solve(JF_approx1(F,p1,0.01),-F(p1)) + p1
        n += 1
    return p1,n
    
def newton_approx2(F,p0,tol,maxiter):
    p1 = la.solve(JF_approx2(F,p0,0.01),-F(p0)) + p0 
    n = 1
    while la.norm(p0-p1)>=tol and n < maxiter:
        p0,p1 = p1,la.solve(JF_approx2(F,p1,0.01),-F(p1)) + p1
        n += 1
    return p1,n

def euler(f,a,b,n,y0):
    t = np.zeros(n+1)
    y = np.zeros(n+1)
    y[0],t[0],h = y0,a,(b-a)/n
    for i in range(n):
        t[i+1] = t[i] + h
        y[i+1] = y[i] + h*f(t[i+1],y[i])
    return t, y

def printm(matriz):      # Imprime una matriz A separada por espacios
    np.set_printoptions(sign=' ',suppress=True,floatmode='maxprec_equal')
    print(matriz)
    #print('\n'.join(['\t'.join([str(elemento) for elemento in fila]) for fila in matriz]))
    print()

"""
x = np.array([1,2,3,4])
y = np.array([0.5,4,2,0.7])

'''
#calcular el spline
S = CubicSpline(x,y)

#mostrar valores
S(-4,5)'''
S_Natural = CubicSpline(x,y,bc_type = 'natural')
S_Frontera = CubicSpline(x,y,bc_type = ((1, 0.3), (1, -0.3)))  #primera derivada igual a 0.3 para el punto ptimero , primera
            #derivada es -0.3 para el punto más alejado
"""

def dif_divididas(x,y):
    n = len(x)-1
    A = np.zeros((n+1,n+1))
    A[:,0] = y
    for j in range(1,n+1):
        for i in range(n-j+1):
            A[i,j] = (A[i+1][j-1]-A[i][j-1])/(x[i+j]-x[i])
    return A

def PolNewton(x,y):
    dif = dif_divididas(x,y)[0,:]
    pol = np.zeros(len(x))
    for i in range(len(x)):
        pol = np.polyadd(pol,dif[i]*npol.polyfromroots(x[:i])[::-1])
    return pol

def PolLagrange(x,y):
    return np.linalg.solve(np.vander(x), y)

def PolLagrange2(x,y):
    return np.dot(np.linalg.inv(np.vander(x)), y)

def aproximante(fun,a,b,grado,type='a',plot=False,showPol=False,showErr=False):
    def chebysevAB(fun,a,b,grado,plot=False,showPol=False,showErr=False):
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
            plt.figure()
            plt.plot(x,fun(x),x,np.polyval(poli,x))
            plt.legend(('Función','Aprox. Chebysev {}'.format(grado)),loc = 'best')
            plt.xlabel('Abscisas')
            plt.ylabel('Ordenadas')
            plt.grid()
        def errorCh(f,P,a,b):
            def wChT(x,a,b): # Función peso llevada al intervalo [a,b]
                return 1/(1-(-1+(2*(x-a))/(b-a))**2)**(0.5)
            return np.sqrt(quad(lambda x: wChT(x,a,b)*(f(x) - np.polyval(P,x))**2,a,b)[0])

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
        if showErr:
            print('Error obteniendo la norma inducida por el correspondiente error de ||f-p||:',errorCh(fun,polinomio,a,b))
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
            plt.figure()
            plt.plot(x,fun(x),x,np.polyval(poli,x))
            plt.legend(('Función','Aprox. Legendre {}'.format(grado)),loc = 'best')
            plt.xlabel('Abscisas')
            plt.ylabel('Ordenadas')
            plt.grid()

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

    if type == 'a':
        ans = [chebysevAB(fun,a,b,grado,plot,showPol),legendreAB(fun,a,b,grado,plot,showPol)]
    elif type == 'c':
        ans = chebysevAB(fun,a,b,grado,plot,showPol)
    elif type == 'l':
        ans = legendreAB(fun,a,b,grado,plot,showPol)

    return ans

def modelo_discreto_general(x,y,m):
    sol = []
    base = [np.array([1.]+i*[0]) for i in range(m+1)]

    for i in range(m+1):
        sol.append(np.zeros(m+1))
    np.array(sol)

    for i in range(len(sol)):
        for j in range(len(sol[0])):
            sol[i][j] = np.dot(np.polyval(base[j],x),np.polyval(base[i],x))

    terms = np.zeros(m+1)
    for i in range(m+1):
        terms[i] = np.dot(y,np.polyval(base[i],x))

    return np.linalg.solve(sol,terms)[::-1]

def fourier(fun,x,l,n): #(funcion,x,periodo(=2l),orden)
    def a(fun,x,l,k):
        return np.cos(k*np.pi*x/l)*1/l*quad(lambda x: fun(x)*np.cos(k*np.pi*x/l), -l, l)[0]
    def b(fun,x,l,k):
        return np.sin(k*np.pi*x/l)*1/l*quad(lambda x: fun(x)*np.sin(k*np.pi*x/l), -l, l)[0]
    
    ans = 1/(2*l)*quad(fun,-l,l)[0]
    for i in range(1,n):
        ans += a(fun,x,l,i)
    for i in range(1,n-1):
        ans += b(fun,x,l,i)
    return ans

def g(x):
    if -2*pi < x < 0:
        return 1
    else:
        return pi-x

g = np.vectorize(g)
grado = 3
xa = np.linspace(-2*pi,2*pi,1000)
plt.figure()
plt.plot(xa,g(xa),label='función')
plt.plot(xa,fourier(g,xa,2*pi,grado))
plt.xlim(-pi,pi)
plt.show()

#------------------------------------------------------------------------------

"""
N = np.array([0,1/np.math.factorial(1),0,-1/np.math.factorial(3),0,1/np.math.factorial(5),0])

p, q = pade(N,3,3)

print(p,q)

def fpade(x):
    return p(x)/q(x)

xc = np.linspace(-np.pi,np.pi)

plt.figure()
plt.plot(xc,np.sin(xc),label='Función')
plt.plot(xc,fpade(xc),label='Pade')
"""

def dy_tres(f,x0,h):
    return (f(x0+h)-f(x0-h))/(2.*h)
def dy_tres_discreto(x,y):
    h=abs(x[1]-x[0])
    return (y[2]-y[0])/(2.*h)

def dy_cinco(f,x0,h):
    return (f(x0-2.*h)-8.*f(x0-h)+8.*f(x0+h)-f(x0+2.*h))/(12.*h)
def dy_cinco_discreto(x,y):
    h = abs(x[4]-x[0])/5
    return (y[0]-8.*y[1]+8.*y[3]-y[4])/(12.*h)

def simpson_cerrado(f,a,b):
    h = abs(b-a)/2
    return h*(f(a)+4*f((a+b)/2)+f(b))/3
def simpson_cerrado_discreto(x,y):
    h = abs(x[0]-x[1])
    return h*(y[0]+4*y[1]+y[2])/3

def simpson_abierto(f,a,b):
    h = abs(b-a)/4
    return 4*h(2*f(a)-f((a+b)/2)+2*f(b))/3
def simpson_abierto_discreto(x,y):
    h = abs(x[0]-x[1])
    return 4*h(2*y[0]-y[1]+2*y[2])/3

def simpson38_cerrado(f,a,b):
    h = abs(b-a)/3
    return 3*h*(f(a)+3*f((a+b)/3)+3*f((2*(a+b))/3)+f(b))/8
def simpson38_cerrado_discreto(x,y):
    h = abs(x[0]-x[1])
    return 3*h*(y[0]+3*y[1]+3*y[2]+y[3])/8

def simspon38_abierto(f,a,b):
    h = abs(b-1)/5
    return  5*h*(11*f(a)+f((a+b)/3)+f((2*(a+b))/3)+11*f(b))/24
def simpson38_abierto_discreto(x,y):
    h = abs(x[0]-x[1])
    return 5*h*(11*y[0]+y[1]+y[2]+11*y[3])/24

def trapecio_compuesto(f,a,b,n):
    xi = np.linspace(a,b,n+1)
    suma = 0.
    for i in range(n):
        suma += trapecio_cerrado(f,xi[i],xi[i+1])     
    return suma

def simpson_compuesto_cerrado(f,a,b,n):
    xi = np.linspace(a,b,n+1)
    suma = 0.
    for i in range(n):
        suma += simpson_cerrado(f,xi[i],xi[i+1])     
    return suma
def simspon_compuesto_abierto(f,a,b,n): #?
    xi = np.linspace(a,b,n+1)
    suma = 0
    for i in range(n):
        suma += simpson_abierto(f,xi[i],xi[i+1])
    return suma 

def trapecio_cerrado_discreto(x,y):
    h=x[1]-x[0]
    return h*(y[0]+y[1])/2.