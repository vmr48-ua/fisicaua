import numpy as np
import numpy.linalg as la
import scipy as sci
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as npol
from scipy.integrate import quad
from scipy.interpolate import CubicSpline
import scipy

"""LISTAS"""

lista=[3,4,3,5,6]
lista.count(3)        # = 2
lista.index(5)        # = 3
lista.sort()          # [3, 3, 4, 5, 6]
lista = lista[::-1]   # [6, 5, 4, 3, 3]
lista.insert(1,'hola')# [6, 'hola', 5, 4, 3, 3]
lista.pop(1)          # [6, 5, 4, 3, 3]
lista.pop()           # [6, 5, 4, 3]

z = np.linspace(0,1,6)# [0., 0.2, 0.4, 0.6, 0.8, 1. ]
#round(z)             # error
Round = np.vectorize(lambda x: round(x))
Round(z)              # [0, 0, 0, 1, 1, 1]

"""POLINOMIOS"""

P1=np.array([3,-2,1]) # (3x**2-2x+1)
Q1=np.array([2,1]) # En potencias decrecientes (2x+1).
np.polyadd(P1,Q1) # (3x**2+2).
np.polysub(P1,Q1) # (3x**2-4x).
np.polymul(P1,Q1) # (6x**3-x**2+1).
np.polydiv(P1,Q1) # cociente (1.5x-1.75) y resto (2.75).
print(np.poly1d(P1))
2*P1 # (6x**2-4x+2)
P1+2 # error
np.polyadd(P1,np.array([2]))
np.polyval(P1,z)
npol.polyfromroots(np.roots(P1))[::-1] # P1

"""MATRICES"""

A = np.array([[1, 2, 3],[-1, 0, 7],[0, -2, 3]])
b = np.array([1, 0, 3])
A*A                  # cuadrado
A.T                  # transpuesta
la.det(A)            # determinante
la.matrix_rank(A)    # rango
la.inv(A)            # inversa  
la.eig(A)            # autovalores
la.solve(A,b)        # sistemas de ecuaciones
np.dot(A,b)          # multiplicacion matricial
np.diag(A)           # diagonal principal
np.diag(A,1)         # diagonal superior

"""ALEATORIOS"""

np.random.seed(0)
np.random.rand(3,2) # matriz NxN entre (0,1)
np.random.randint(-1,3,[2,3]) # matriz 2x3 entre (-1,3)
np.arange(2,5) # [2, 3, 4]

def fibonacci(n):
    fib=[1]
    if n > 1:
        fib.append(1)
        for i in range(n):
            fib.append(fib[len(fib)-1]+fib[len(fib)-2])
    return fib
def fibonacci2(n):
    for i in range(n):
        a = fibonacci(i)
        if a[len(a)-1] > n:
            a.pop()
            return a
    return 0


"""INTERPOLACION"""
P1=np.array([3,-2,1]) # (3x**2-2x+1)
x = np.array([0,3,5,8])
y = np.polyval(P1,x)
P = np.polyfit(x,y,3) # grado 3 (potencia decreciente)
la.solve(np.vander(x).T[::-1].T,y)[::-1] # (3x**2-2x+1)
def PolLagrange(x,y):
    return la.solve(np.vander(x), y)
# error: np.abs(np.polyval(P,x)-f(x))

a,b = -1,1
T2_ab = np.polynomial.chebyshev.Chebyshev([0,0,1],[a,b]) 
nodCheby2_ab = T2_ab.roots() # 2 nodos de chebysev => GRADO 1
P2_ab = npol.polyfromroots(nodCheby2_ab)[::-1] # polinomio ortogonal
# igual para legendre.Legendre
def nodCheb(a,b,n): # n es el grado del pol (n+1 nodos)
    nod = []
    for i in range(n+1):
        nod.append((a+b)/2-(b-a)/2*np.cos(((2*i+1)*np.pi)/(2*(n+1))))
    return np.array(nod)

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

x,y = np.array([2,6,7]), np.array([4,5,6])
S = scipy.interpolate.CubicSpline(x,y,bc_type = 'natural') # si no es knot a knot
# natural   => S''(x_0) = S''(x_n) = 0
# frontera  => S'(x_0) = f'(x_0)     S'(x_n) = f'(x_n)
S_Fr = CubicSpline(x,y,bc_type = ((1, 0.3), (1, -0.3))) # f'(x_0)=0.3, f'(x_n)=-0.3
S.c
# array([[ 1.87500000e-02, -7.50000000e-02],
#        [-1.38777878e-17,  2.25000000e-01],
#        [-5.00000000e-02,  8.50000000e-01],
#        [ 4.00000000e+00,  5.00000000e+00]])
# S_0(x) = 4 - 0.05(x-2) + 0.01875(x-2)^3.
# S_1(x) = 5 + 0.85(x-6) + 0.225(x-6)^2 - 0.075(x-6)^3.
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
#f = np.vectorize(f) # L=pi

"""DERIVADAS"""
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


"""INTEGRALES"""
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

# n en compuesto es n = int(np.ceil(np.sqrt((A*(b-a)**3)/(12*eps))))
# con A = max|f''|, 
# que podemos caluclar o bien analiticamente:
xs = np.linspace(0,10,10)
d2f = lambda x: x # f''
A = max(abs(d2f(xs)))
# o bien con 
d2f = []
fx = [0]*10
h = 0.001
for i in range(1,len(xs)-1):
    d2f.append((fx[i+1]-2*fx[i]+fx[i-1])/h**2)
A = max(abs(np.array(d2f)))

"""SISTEMAS DE ECUACIONES"""

def solucionU(A,b): #Método de sustitución regresiva
    n = len(b)
    sol = np.zeros(n)
    for i in range(n-1,-1,-1):
        suma = 0
        for j in range(i,n):
            suma += A[i,j]*sol[j]
        sol[i] = (b[i]-suma)/A[i,i]
    return sol
def solucionL(A,b):
    n = len(b)
    sol = np.zeros(n)
    for i in range(0,n):
        suma = 0
        for j in range(0,i):
            suma += A[i,j]*sol[j]
        sol[i] = (b[i]-suma)/A[i,i]
    return sol
def solucionD(A,b):
    n = len(A)
    x = np.zeros(n)
    for i in range(n-1,-1,-1):
        x[i] = b[i]/A[i,i]
    return x

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
def gauss_parcial(A,b):
    Ac = A.copy()
    bc = b.copy()
    n = len(b)
    for j in range(n-1): #cols
        piv = np.argmax(abs(Ac[j::,j]))+j
        cambio_filas(Ac,j,piv)
        cambio_filas(bc,j,piv)
        for i in range(j+1,n): #filas
            if A[i,j]!= 0:
                suma_filas(bc,i,j,-Ac[i,j]/Ac[j,j])              
                suma_filas(Ac,i,j,-Ac[i,j]/Ac[j,j])
    return solucionU(Ac,bc)
def gauss_parcial_escalado(A,b):
    Ac=A.copy()
    bc=b.copy()
    n = len(Ac)
    for i in range(n-1):
        piv = np.argmax(abs(Ac[i::,i])/[max(abs(Ac[j,i::])) for j in range(i,n)])+i
        cambio_filas(Ac,piv,i)
        cambio_filas(bc,piv,i)
        for j in range(i+1,n):
            suma_filas(bc,j,i,-Ac[j,i]/Ac[i,i])
            suma_filas(Ac,j,i,-Ac[j,i]/Ac[i,i])
    return solucionU(Ac,bc)
def inversa(A):
    Ac = A.copy()
    n = len(Ac)
    I = np.zeros([n,n])
    for i in range(n):
        I[i,i] = 1
    for i in range(n-1):
        piv = np.argmax(abs(Ac[i::,i]))+i
        cambio_filas(Ac,piv,i)
        cambio_filas(I,piv,i)
        for j in range(i+1,n):
            suma_filas(I,j,i,-Ac[j,i]/Ac[i,i])
            suma_filas(Ac,j,i,-Ac[j,i]/Ac[i,i])
    for i in range(n):
        prod_fila(I,i,1/Ac[i,i])
        prod_fila(Ac,i,1/Ac[i,i])
    for i in range(n-1,0,-1):
        for j in range(i):
            suma_filas(I,j,i,-Ac[j,i])
            suma_filas(Ac,j,i,-Ac[j,i])
    return I
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

A = np.array([[5,1,2],[1,4,1],[2,2,5]],dtype=float)
L,U = LU(A)[0],LU(A)[1]
p, l, u = la.lu(B)

D = np.diag(A) # [5, 4, 5]
D = np.diag(np.diag(A)) 
# [[5., 0., 0.],
#  [0., 4., 0.],
#  [0., 0., 5.]]
L = -np.tril(A-D)
U = -np.triu(A-D)
(eivl, eivc) = la.eig(B)
respectral = max(abs(eivl))
la.norm(x1-x0,2)
def jacobi(A,b,x0,norma,error,k): #k: numero maximo de iteraciones a realizar, error: tolerancia
    D = np.diag(np.diag(A)) 
    L = -np.tril(A-D)
    U = -np.triu(A-D)
    M = np.copy(D)
    N = L + U
    B = np.dot(la.inv(M),N)
    c = np.dot(la.inv(M),b)
    resp = max(abs(la.eig(B)[0]))
    if resp < 1:
        cont = 0
        tolerancia = error +1
        while cont < k and tolerancia > error:
            x1 = np.dot(B,x0) + c
            tolerancia = la.norm(x1-x0,norma)
            x0 = x1.copy()
            cont += 1      
    else:
        print("El método no es convergente")
    return x0, cont
def gauss_seindel(A,b,x0,norma,error,k):
    D = np.diag(np.diag(A)) 
    L = -np.tril(A-D)
    U = -np.triu(A-D)
    M = D - L
    N = np.copy(U)
    B = np.dot(la.inv(M),N)
    c = np.dot(la.inv(M),b)
    resp = max(abs(la.eig(B)[0]))
    if resp < 1:
        cont = 0
        tolerancia = error + 1
        while cont < k and tolerancia > error:
            x1 = np.dot(B,x0) + c
            tolerancia = la.norm(x1-x0,norma)
            x0 = x1.copy()
            cont += 1      
    else:
        print("El método no es convergente")
    return x0, cont
def relax(A,b,w,x0,norma,error,k):
    D = np.diag(np.diag(A))
    L = -np.tril(A-D)
    U = -np.triu(A-D)
    B = np.dot(la.inv((D-w*L)),((1-w)*D + w*U))   
    c = np.dot(la.inv((D-w*L)),w*b)
    resp = max(abs(la.eig(B)[0]))
    if resp < 1:
        cont = 0
        tolerancia = error + 1
        while cont < k and tolerancia > error:
            x1 = np.dot(B,x0) + c
            tolerancia = la.norm(x1-x0,norma)
            x0 = x1.copy()
            cont += 1      
    else:
        print("El método no es convergente")
    return x0, cont, resp


"""ECUACIONES NO LINEALES"""

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
    print(intervalos)
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
def newton_5ptos(f,p0,tol,maxiter):
    p1 = p0 - f(p0)/dy_cinco(f,p0,10**(-2))
    n = 1
    while abs(p1-p0) > tol and n < maxiter:
        n +=  1
        p0 = p1
        p1 = p0 - f(p0)/dy_cinco(f,p0,10**(-2))
    return p1,n
def secante(f,p0,p1,tol,maxiter):
    p2 = p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    n = 1
    while abs(p2-p1) > tol and n < maxiter:
        n +=  1
        p0 = p1
        p1 = p2
        p2 = p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
    return p2,n
def regula_falsi(f,p0,p1,tol,maxiter):
    p2 = p1 - f(p1)*((p1-p0)/(f(p1)-f(p0)))
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
def euler(f,a,b,n,y0):
    h = (b-a)/n
    t = np.linspace(a,b,n+1)
    y = np.zeros(n+1)
    y[0] = y0
    for i in range(n):
        y[i+1] = y[i] + h*f(t[i],y[i]) 
    return (t,y)