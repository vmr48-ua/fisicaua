import numpy as np
import numpy.linalg as la
from numpy import sin, cos, pi

import time
import random
import matplotlib.pyplot as plt
from scipy import sparse

def TridiagonalSolver(d, o, u, r) -> np.ndarray:
    n = len(d)
    D = np.min(np.abs(d))
    x = np.zeros(n)

    if n - 1 == len(o) == len(u) and D != 0:
        for i in range(n-1):
            o[i],r[i] = o[i]/d[i],r[i]/d[i]
            d[i] = 1
            d[i+1],r[i+1] = d[i+1] - o[i]*u[i],r[i+1] - r[i]*u[i]   
        x[-1] = r[-1]/d[-1] # el último
        for i in range(2,n+1):
            x[-i] = r[-i] - o[-i+1] * x[-i+1]
        return x
    if D == 0:
        print('Error: 0 en la diagonal')
    else:
        print('Error: dimensión matricial')

def TridiagonalRandom_NonSingular(n, m=-10, M=10) -> np.ndarray:
    """
    Genera una matriz tridiagonal no singular aleatoria para ser usada 
    en TridiagonalSolver(), linalg.solve() y linalg.inv()
    """
    while True:
        d = [random.uniform(m, M) for _ in range(n)]
        u = [random.uniform(m, M) for _ in range(n-1)]
        o = [random.uniform(m, M) for _ in range(n-1)]
        r = [random.uniform(m, M) for _ in range(n)]

        A = np.zeros((n, n))
        for i in range(n):
            A[i, i] = d[i]        # diagonal
            if i < n-1:
                A[i, i+1] = o[i]  # over
                A[i+1, i] = u[i]  # under

            # Lo retrasa mucho
        # if np.linalg.det(A) != 0:
        #     return d,o,u,r,A # sólo si es no singular (det != 0)
        return d,o,u,r,A 
    
def ejercicioI() -> None:    
    """
    EJEMPLO DE MATRIZ Y CÁLCULO TEMPORAL
    
    [[2,-1,0,0],   [x1] 
     [3,1,-2,0], * [x2] = [[2,9,-5,4]]
     [0,-1,5,4],   [x3]
     [0,0,4,-3]]   [x4] 
    """

    A = [[2., -1., 0., 0.], [3., 1., -2., 0.], [0., -1., 5., 4.], [0., 0., 4., -3.]]
    b = [2., 9., -5., 4.] # Ax = b

    d = [2., 1., 5., -3.] # diagonal
    o = [-1., -2., 4.]   # over
    u = [3., -1., 4.]    # under
    r = b.copy()

    # Medir tiempo de TridiagonalSolver
    start = time.perf_counter()
    resultado_tridiagonal = TridiagonalSolver(d, o, u, r)
    end = time.perf_counter()
    print("TridiagonalSolver:", resultado_tridiagonal, 'en {:e}s'.format(end - start))

    # Medir tiempo de la.solve
    start = time.perf_counter()
    resultado_linalg = la.solve(A.copy(), b.copy())
    end = time.perf_counter()
    print("Linalg.solve:", resultado_linalg, 'en {:e}s'.format(end - start))

    # Medir tiempo usando la inversa de A
    start = time.perf_counter()
    resultado_inv = np.dot(la.inv(A.copy()), b.copy())
    end = time.time()
    print("Inversa (A^-1 * b):", resultado_inv, 'en {:e}s'.format(end - start))
    

    """
    GENERACIÓN DE LAS GRÁFICAS
    """

    # GRÁFICA DEL COSTE TEMPORAL CON EL TAMAÑO MATRICIAL PARA TAMAÑOS GRANDES
    tri_time, solve_time, inv_time = [], [], []
    
    xo = np.ceil(np.linspace(100,10000,6)).astype(int)
    for i in xo:
        d,o,u,r,A = TridiagonalRandom_NonSingular(i)

        start = time.perf_counter()
        sol = TridiagonalSolver(d, o, u, r)
        end = time.perf_counter()
        if sol is not None: # Caso en el que no se alcanza la solución
            tri_time.append(end - start)
        
        start = time.perf_counter()
        la.solve(A,r)
        end = time.perf_counter()
        solve_time.append(end - start)

    plt.figure()
    plt.title('Coste temporal frente a tamaño matricial grande')
    plt.xlabel('Dimensión Matricial')
    plt.ylabel('Tiempo de ejecución (s)')

    plt.scatter(xo,tri_time, label='Tiempo TridiagonalSolver()', marker='x',c='orange')
    plt.scatter(xo,solve_time, label='Tiempo linalg.solve()', marker='x',c='blue')

    plt.legend()

    # GRÁFICA DEL COSTE TEMPORAL CON EL TAMAÑO MATRICIAL
    tri_time, solve_time, inv_time = [], [], []
    
    xo = np.ceil(np.logspace(2.5,3.8,7)).astype(int) # Pasar de float a int en numpy
    for i in xo:
        d,o,u,r,A = TridiagonalRandom_NonSingular(i)

        start = time.perf_counter()
        sol = TridiagonalSolver(d, o, u, r)
        end = time.perf_counter()
        if sol is not None: # Caso en el que no se alcanza la solución
            tri_time.append(end - start)
        
        start = time.perf_counter()
        la.solve(A,r)
        end = time.perf_counter()
        solve_time.append(end - start)

        # La inversa es muy lenta
        start = time.perf_counter()
        np.dot(la.inv(A), r)
        end = time.perf_counter()
        inv_time.append( end - start)

    plt.figure()
    plt.title('Coste temporal frente a tamaño matricial')
    plt.xlabel('Dimensión Matricial')
    plt.ylabel('Tiempo de ejecución (s)')

    plt.scatter(xo,tri_time, label='Tiempo TridiagonalSolver()', marker='x',c='orange')
    plt.scatter(xo,solve_time, label='Tiempo linalg.solve()', marker='x',c='blue')
    plt.scatter(xo,inv_time, label='Tiempo Inversa (A^-1 * b)', marker='x',c='green')
    
    plt.yscale("log")
    plt.xscale("log")

    plt.legend()

    # EXTRA - MEDIA
    N = 20 # Número de matrices aleatorias que vamos a generar
    n = 1000  # dimensión de las matrices generadas (nxn)
    tiempo_tridiagonal, tiempo_linalg, tiempo_inv = [], [], []

    for i in range(N):
        d,o,u,r,A = TridiagonalRandom_NonSingular(n)
        
        start = time.time()
        sol = TridiagonalSolver(d, o, u, r)
        end = time.time()
        if sol is not None: # Caso en el que no se alcanza la solución
            tiempo_tridiagonal.append((i,end - start))

        start = time.time()
        la.solve(A.copy(),r.copy())
        end = time.time()
        tiempo_linalg.append((i,end - start))

        start = time.time()
        np.dot(la.inv(A.copy()), r.copy())
        end = time.time()
        tiempo_inv.append((i, end - start))
        print(i*100/N,'%',end='\r')

    plt.figure()
    plt.title('Comparación temporal de distintos algoritmos de resolución\n' +
              ' de sistemas de ecuaciones con matrices asociadas {}x{}'.format(n,n))
    
    plt.scatter(*zip(*tiempo_tridiagonal), label='Tiempo TridiagonalSolver()', marker='x',c='orange')
    plt.axhline(np.mean(np.array(tiempo_tridiagonal)[:,1]),alpha=0.4,c='orange')

    plt.scatter(*zip(*tiempo_linalg), label='Tiempo linalg.solve()', marker='x',c='blue')
    plt.axhline(np.mean(np.array(tiempo_linalg)[:,1]),alpha=0.4,c='blue')
    
    plt.scatter(*zip(*tiempo_inv), label='Tiempo Inversa (A^-1 * b)', marker='x',c='green')
    plt.axhline(np.mean(np.array(tiempo_inv)[:,1]),alpha=0.4,c='green')

    plt.xlabel('Número de ejecución')
    plt.ylabel('Tiempo de ejecución (s)')
    plt.legend()
    plt.show()

    """
    De conclusión podemos ver que el solve() es algo más eficiente que usar la inversa (creo que solve
    usa la descomposición LU) y nuestro método es significativamente más rápido. Esto es, para el caso
    específico de una matriz tridiagonal claro, para matrices cualquiera la.solve() es el método más 
    rápido.
    """

def ejercicioII() -> None:

    """
    Notación Matricial
      i →
    j  ________
    ↓ |0 .     |
      |.       |
      |        |
       ________

    POISSON  
        Resolución de la Ecuación de Poisson en el intervalo [0,\pi] con f(0)=0 y f'(\pi)=-\pi^2
                                f''(x) = (2 - x^2) * sin(x) + 4x cos(x) = g(x)
    """
    def g(x):
        return 2-x**2 * sin(x) + 4*x*cos(x)
    
    N = 100                      # N representa el número de puntos interiores de la malla
    h = pi/(N+1)                 # N+1 es el número de subintervalos
    xo = np.linspace(0,pi,N+2)   # Dimensión N+2 porque N + las 2 condiciones de frontera

    d = [-2 for _ in range(N)]                # Diagonal principal
    u = [1  for _ in range(N-1)]              # Diagonal inferior
    o = [1  for _ in range(N-1)]              # Diagonal superior
    r = [h**2 * g(xi) for xi in xo[1:N+1]]    # h^2 * f''(x) vector de términos independientes

    # Condiciones de contorno
    r[-1] = h * -pi**2 # h sin ir al cuadrado porque es la primera derivada
    
    # A = sparse.diags([d,o,u],[0,1,-1],shape=(N,N)).toarray() # [0,1,-1] indica [diagonal, d. inferior, d. superior]
    
    f_interior = TridiagonalSolver(d, o, u, r)
    f = np.zeros(N+2)
    f[1:N+1] = f_interior
    f[0] = 0                    # Redundante porque f ya está llena de ceros
    f[-1] = f[-2] - h * pi**2   # f'(pi)= -pi^2 =(f(pi)-f(x_N))/h ==> f(pi) = f(x_N) - h*pi^2 

    #f''(x) = (2 - x^2) * sin(x) + 4x cos(x)
    plt.figure()
    plt.title('Gráfica de $\\frac{d^2f}{dx^2} = (2-x^2)\cdot \sin x + 4x\cdot\cos x$')
    plt.grid()
    plt.plot(xo,f,label='$f(x)$')
    plt.legend(loc='best')
    plt.show()

    """
    LAPLACE
        Resolución de la Ecuación de Laplace en 2D en un cuadrado de lado L=1 con 
        \phi(x=0) = \phi(x=1) = \phi(y=0) = 0   y   \phi(y=1) = x(1-x) (Dirichlet)

        - Jacobi
        - scipy.sparse.linalg
        - Gauss-Seidel
        - Sobrerelajación
    """

    return None

def main() -> None:
    #ejercicioI()
    ejercicioII()

if __name__ == '__main__':
    main()
