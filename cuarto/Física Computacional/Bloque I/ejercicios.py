import numpy as np
import numpy.linalg as la
from numpy import sin, cos, pi, exp, sqrt

import time
import random
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation, FFMpegFileWriter
from matplotlib.patches import FancyArrowPatch
from mpl_toolkits.mplot3d import Axes3D
from scipy import sparse

'''
EJERCICIOS FÍSICA COMPUTACIONAL - BLOQUE I
INTRODUCCIÓN A LA MODELIZACIÓN EN FÍSICA

Víctor Mira Ramírez
74528754Z
vmr48@alu.ua.es
'''

# Ejercicios
def ejercicioI() -> None:    
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
            raise Exception('Error: 0 en la diagonal')
        else:
            raise Exception('Error: dimensión matricial')

    def TridiagonalRandom_NonSingular(n, m=-10, M=10):
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

    def EulerForward(x0,v0,t,dt) -> tuple[np.ndarray, np.ndarray]:
        '''
        \\begin{cases}
            x_{n+1} = x_n + h y_n
            y_{n+1} = y_n - h (\\omega^2 x_n + \\alpha y_n)
        \\end{cases}
        '''

        x,v = np.zeros(len(t)),np.zeros(len(t))
        x[0],v[0] = x0,v0
        x[1] = v0*dt + x0
        
        for i in range(1,len(t)-1): # desde 1 porque hago x[i-1]
            x[i+1] = x[i]*(2-alpha*dt - (omega**2)*(dt**2)) + x[i-1]*(alpha*dt-1)
            v[i] = v[i-1] - dt*(omega**2*x[i-1] + alpha*v[i-1])
        
        plt.subplot(2,1,1)
        plt.plot(t, x, label='Posición (x)')
        plt.plot(t, v, label='Velocidad (v)', linestyle='--')
        plt.title('Euler Forward')
        plt.xlabel('t')
        plt.ylabel('x, v')
        plt.grid()
        
        return x,v

    def EulerForwardMatrix(x0, v0, t, dt, plot=True) -> tuple[np.ndarray, np.ndarray]:
        """ 
        -   z_{n+1} = \\mathcal{A} z_n
        
        -   \\begin{cases}
                x_{n+1} = x_n + h y_n
                y_{n+1} = y_n - h (\\omega^2 x_n + \\alpha y_n)
            \\end{cases}
            
        \\implies
        A = ((1,dt),(-dt \\omega^2, 1 - dt \\alpha))
        """
        z = np.zeros((2, len(t)))  # 2 filas (x, v), columnas = len(t)
        z[0, 0], z[1, 0] = x0, v0 

        # Funciona mejor (hay que cambiar z en el for)
        # A = np.array([[1, dt], [-omega**2 * dt, 1 - alpha * dt]])

        A = np.array([[0         , 1     ],
                    [-omega**2 , -alpha]])

        for i in range(1, len(t)):
            z[:, i] = np.dot(np.eye(len(A)) + dt*A, z[:, i - 1])
            # z[:, i] = np.dot(A, z[:, i - 1])

        x, v = z[0, :], z[1, :]

        if plot:
            plt.subplot(2,1,2)
            plt.plot(t, x, label='Posición (x)')
            plt.plot(t, v, label='Velocidad (v)', linestyle='--')
            plt.title('Euler Forward - Matricial')
            plt.xlabel('t')
            plt.ylabel('x, v')
            plt.legend()
            plt.grid()
        
        return x, v

    def EulerBackward(x0,v0,t,dt) -> tuple[np.ndarray, np.ndarray]:
        '''
        Despejamos y_{n+1} de la segunda ecuación y sustituimos en la primera
        \\begin{cases}
            x_{n+1} = x_n + h y_{n+1}
            y_{n+1} = y_n - h (\\omega^2 x_{n+1} + \\alpha y_{n+1})
        \\end{cases}    
        
        Despejando
        y_{n+1} = y_n - h (\\omega^2 x_{n+1} + \\alpha y_{n+1}) \\Longleftrightarrow
        y_{n+1} = y_n + h (- \\omega^2 x_{n+1} - \\alpha y_{n+1}) \\Longleftrightarrow
        y_{n+1} (1 + h \\alpha) = y_n - h \\omega^2 x_{n+1} \\Longleftrightarrow
        y_{n+1} = \\frac{y_n - h \\omega^2 x_{n+1}}{1 + h \\alpha}
        
        Sustituyendo
        x_{n+1} = x_n + h y_{n+1} \\Longleftrightarrow
        x_{n+1} = x_n + h \\frac{y_n - h \\omega^2 x_{n+1}}{1 + h \\alpha}
        
        Finalmente
        \\begin{cases}
            x_{n+1} = x_n + h \\frac{y_n - h \\omega^2 x_{n+1}}{1 + h \\alpha}
            y_{n+1} = \\frac{y_n - h \\omega^2 x_{n+1}}{1 + h \\alpha}
        \\end{cases}
        '''
        
        x,v = np.zeros(len(t)),np.zeros(len(t))
        x[0],v[0] = x0,v0
        
        for i in range(1,len(t)):
            v[i] = (v[i-1] - dt*omega**2 *x[i-1])/(1 + dt*alpha)
            x[i] = x[i-1] + dt*v[i] # (v[i-1] + dt*alpha*x[i-1])/(1 + dt*alpha)
            
        plt.subplot(2,1,1)
        plt.plot(t, x, label='Posición (x)')
        plt.plot(t, v, label='Velocidad (v)', linestyle='--')
        plt.title('Euler Backward')
        plt.xlabel('t')
        plt.ylabel('x, v')
        plt.grid()
        
        return x,v

    def EulerBackwardMatrix(x0, v0, t, dt, plot=True) -> tuple[np.ndarray, np.ndarray]:
        """ 
        -   z_{n+1} = \\mathcal{A} z_n
        
        -   \\begin{cases}
                x_{n+1} = x_n + h \\frac{y_n - h \\omega^2 x_{n+1}}{1 + h \\alpha}
                y_{n+1} = \\frac{y_n - h \\omega^2 x_{n+1}}{1 + h \\alpha}
            \\end{cases}
            
        \\implies
        A = ((1 - \\frac{h^2 \\omega^2}{1+h \\alpha}, \\frac{h}{1+h\\alpha}),
            (   -\\frac{h   \\omega^2}{1+h \\alpha}, \\frac{1}{1+h \\alpha}))
        """
        z = np.zeros((2, len(t)))  # 2 filas (x, v), columnas = len(t)
        z[0, 0], z[1, 0] = x0, v0

        # B = np.array([[1-(dt**2 * omega**2)/(1 + dt*alpha),   dt/(1 + dt*alpha)],
        #              [-1 * (dt * omega**2)/(1 + dt*alpha),    1/(1 + dt*alpha)]])
        A = [[0,1],[-omega**2,-alpha]]
        B = la.inv(np.eye(2)-dt*np.array(A))

        for i in range(1, len(t)):
            z[:, i] = np.dot(B, z[:, i - 1])

        x, v = z[0, :], z[1, :]
        
        if plot:
            plt.subplot(2,1,2)
            plt.plot(t, x, label='Posición (x)')
            plt.plot(t, v, label='Velocidad (v)', linestyle='--')
            plt.title('Euler Backward - Matricial')
            plt.xlabel('t')
            plt.ylabel('x, v')
            plt.legend()
            plt.grid()
        
        return x, v

    def CrankNicholson(x0,v0,t,dt) -> tuple[np.ndarray, np.ndarray]:
        '''
        Hace la media entre las derivadas en el punto n y n+1
        \\begin{cases}
            x_{n+1} = x_n + \\frac{h}{2} (y_n + y_{n+1})
            y_{n+1} = y_n - \\frac{h}{2} ( (\\omega^2 x_n + \\alpha y_n) + (\\omega^2 x_{n+1} + \\alpha y_{n+1}) )
        \\end{cases}
        
        Despejamos y_{n+1}
        y_{n+1} = y_n - \\frac{h}{2} ( \\omega^2 x_n + \\alpha y_n + \\omega^2 x_{n+1} + \\alpha y_{n+1} ) \\Longleftrightarrow
        y_{n+1} (1 + \\frac{h}{2} \\alpha) = y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n + \\omega^2 x_{n+1} \\Longleftrightarrow
        y_{n+1} = \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n + \\omega^2 x_{n+1}}{1 + \\frac{h}{2} \\alpha}
        
        Sustituimos y_{n+1} en x_{n+1} y despejamos x_{n+1}
        x_{n+1} = x_n + \\frac{h}{2} (y_n + y_{n+1}) \\Longleftrightarrow
        x_{n+1} = x_n + \\frac{h}{2} (y_n + \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n + \\omega^2 x_{n+1}}{1 + \\frac{h}{2} \\alpha}) \\Longleftrightarrow
        x_{n+1} (1 + (\\frac{h}{2} \\omega)^2) = x_n + \\frac{h}{2} (y_n + \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n}{1 + \\frac{h}{2} \\alpha}) \\Longleftrightarrow
        x_{n+1} = \\frac{x_n + \\frac{h}{2} (y_n + \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n}{1 + \\frac{h}{2} \\alpha})}{1 + (\\frac{h}{2} \\omega)^2}

        Finalmente
        \\begin{cases}
            x_{n+1} = \\frac{x_n + \\frac{h}{2} (y_n + \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n}{1 + \\frac{h}{2} \\alpha})}{1 + (\\frac{h}{2} \\omega)^2}
            y_{n+1} = \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n + \\omega^2 x_{n+1}}{1 + \\frac{h}{2} \\alpha}
        \\end{cases}
        '''
        
        x,v = np.zeros(len(t)),np.zeros(len(t))
        x[0],v[0] = x0,v0
        
        for i in range(1,len(t)):
            x[i] = (x[i-1] + dt/2 * (v[i-1] + (v[i-1] - dt/2*(omega**2*x[i-1] + alpha*v[i-1]))/(1 + alpha*dt/2))) / (1 + (dt*omega/2)**2)
            v[i] = (v[i-1] - dt/2 * (omega**2*(x[i-1] + x[i]) + alpha*v[i-1])) / (1 + dt*alpha/2)
            
        plt.subplot(2,1,1)
        plt.plot(t, x, label='Posición (x)')
        plt.plot(t, v, label='Velocidad (v)', linestyle='--')
        plt.title('Crank-Nicholson')
        plt.xlabel('t')
        plt.ylabel('x, v')
        plt.grid()
        
        return x,v

    def CrankNicholsonMatrix(x0, v0, t, dt, plot=True) -> tuple[np.ndarray, np.ndarray]:
        """ 
        -   z_{n+1} = \\mathcal{A} z_n
        
        -   \\begin{cases}
                x_{n+1} = \\frac{x_n + \\frac{h}{2} (y_n + \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n}{1 + \\frac{h}{2} \\alpha})}{1 + (\\frac{h}{2} \\omega)^2}
                y_{n+1} = \\frac{y_n - \\frac{h}{2} (\\omega^2 x_n + \\alpha y_n + \\omega^2 x_{n+1}}{1 + \\frac{h}{2} \\alpha}
            \\end{cases}
        """
        z = np.zeros((2, len(t))) # 2 filas (x, v), columnas = len(t)
        z[0, 0], z[1, 0] = x0, v0
        
        A = np.array([[0,1],[-omega**2,-alpha]])
        B = np.dot(la.inv(np.eye(2)-(dt/2)*A),np.eye(2)+(dt/2)*A)

        for i in range(1, len(t)):
            z[:, i] = np.dot(B,z[:,i-1])

        x, v = z[0, :], z[1, :]

        if plot:
            plt.subplot(2,1,2)
            plt.plot(t, x, label='Posición (x)')
            plt.plot(t, v, label='Velocidad (v)', linestyle='--')
            plt.title('Crank-Nicholson - Matricial')
            plt.xlabel('t')
            plt.ylabel('x, v')
            plt.legend()
            plt.grid()
        
        return x, v

    def RungeKuttaIVMatrix(x0, v0, t, dt, plot=True) -> tuple[np.ndarray, np.ndarray]:
        z = np.zeros((2, len(t)))  # 2 filas (x, v), columnas = len(t)
        z[0, 0], z[1, 0] = x0, v0
        A = np.array([[0,1],[-omega**2,-alpha]])

        for i in range(1, len(t)):
            k1 = np.dot(A, z[:, i - 1])
            k2 = np.dot(A, z[:, i - 1] + dt / 2 * k1)
            k3 = np.dot(A, z[:, i - 1] + dt / 2 * k2)
            k4 = np.dot(A, z[:, i - 1] + dt * k3)
            z[:, i] = z[:, i - 1] + dt / 6 * (k1 + 2 * k2 + 2 * k3 + k4)

        x, v = z[0, :], z[1, :]
        
        if plot:
            plt.plot(t, x, label='Posición (x)')
            plt.plot(t, v, label='Velocidad (v)', linestyle='--')
            plt.xlabel('t')
            plt.ylabel('x, v')
            plt.legend()
            plt.grid()
        return x, v

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
    plt.axhline(np.mean(np.array(tiempo_tridiagonal)[:,1], dtype=float),alpha=0.4,c='orange')

    plt.scatter(*zip(*tiempo_linalg), label='Tiempo linalg.solve()', marker='x',c='blue')
    plt.axhline(np.mean(np.array(tiempo_linalg)[:,1], dtype=float),alpha=0.4,c='blue')
    
    plt.scatter(*zip(*tiempo_inv), label='Tiempo Inversa (A^-1 * b)', marker='x',c='green')
    plt.axhline(np.mean(np.array(tiempo_inv)[:,1], dtype=float),alpha=0.4,c='green')

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

    '''
    Notación Matricial
      i →
    j  ________
    ↓ |0 .     |
      |.       |
      |        |
       ________

    POISSON  
        Resolución de la Ecuación de Poisson en el intervalo [0,\\pi] con f(0)=0 y f'(\\pi)=-\\pi^2
                                f''(x) = (2 - x^2) * sin(x) + 4x cos(x) = g(x)
    '''
    def g(x):
        return (2-x**2) * sin(x) + 4*x*cos(x)
    def solution(x):             # solución analítica que cumple las condiciones de contorno
        return x**2 * sin(x)
    
    N = 100000                   # N representa el número de puntos de la malla
    h = pi/(N+1)                 # N+1 es el número de subintervalos
    xo = np.linspace(0,pi,N)     # Dimensión N+2 porque N + las 2 condiciones de frontera

    d = [-2 for _ in range(N)]                # Diagonal principal
    u = [1  for _ in range(N-1)]              # Diagonal inferior
    o = [1  for _ in range(N-1)]              # Diagonal superior
    r = [h**2 * g(xi) for xi in xo]           # h^2 * f''(x) vector de términos independientes, 
                                              # (h^2*g(xo) sin los extremos)
    # Condiciones de contorno
    d[0] = 1
    o[0] = 0
    r[0] = 0

    d[-1] = 1
    u[-1] = 1
    r[-1] = -pi**2 * h

    f = TridiagonalSolver(d, o, u, r)

    #f''(x) = (2 - x^2) * sin(x) + 4x cos(x)
    plt.figure()
    plt.title('Gráfica de $\\frac{d^2f}{dx^2} = (2-x^2)\\cdot \\sin x + 4x\\cdot\\cos x$')
    plt.grid()
    plt.plot(xo,f,label='$f(x)$')
    plt.plot(xo,solution(xo),label='$g(x)=x^2\\cdot\\sin x$',linestyle='--')
    plt.legend(loc='best')
    
    '''
    LAPLACE
        Resolución de la Ecuación de Laplace en 2D en un cuadrado de lado L=1 con 
        \\phi(x=0) = \\phi(x=1) = \\phi(y=0) = 0   y   \\phi(y=1) = x(1-x) (Dirichlet)

        - Jacobi
        - scipy.sparse.linalg
        - Gauss-Seidel
        - Sobrerelajación
    '''

    L = 1
    N = 100
    x = np.linspace(0, L, N+2)  # Coordenadas en x (incluyendo las fronteras)
    y = np.linspace(0, L, N+2)  # Coordenadas en y (incluyendo las fronteras)

    d = [-2 for _ in range(N)]     # Diagonal principal
    u = [1  for _ in range(N-1)]   # Diagonal inferior
    o = [1  for _ in range(N-1)]   # Diagonal superior
    r = np.zeros(N * N)
    I = np.eye(N)

    laplaciano1D = sparse.diags([d,u,o],[0,-1,1])
    laplaciano2D = sparse.kron(I, laplaciano1D) + sparse.kron(laplaciano1D, I)

    phi = np.zeros((N+2, N+2)) # se cumple \\phi(x=0) = \\phi(x=1) = \\phi(y=0) = 0
    phi[-1, :] = x * (1 - x)

    # Contorno en y=1 (phi(y=1) = x(1-x))
    # for i in range(N):
    #     r[i + N*(N-1)] = -phi[-1,i+1]  # Fila a y=1
    r[N * (N - 1):N * (N - 1) + N] = -phi[-1, 1:N + 1]

    phi_interior = sparse.linalg.spsolve(laplaciano2D, r)
    phi[1:N+1, 1:N+1] = phi_interior.reshape((N, N))

    plt.figure()
    plt.imshow(phi, origin='lower', cmap='inferno')
    plt.colorbar(label="$\\phi(x, y)$")
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title("Solución de la ecuación de Laplace en 2D")

    # X, Y = np.meshgrid(x, y)
    # fig = plt.figure()
    # ax = fig.add_subplot(111, projection='3d')
    # ax.plot_surface(X, Y, phi, cmap='inferno')
    # ax.set_xlabel('$x$')
    # ax.set_ylabel('$y$')
    # ax.set_zlabel('$\\phi(x, y)$')
    # ax.set_title('Solución de la ecuación de Laplace en 2D - Representación 3D')

    tol = 10e-6
    
    plt.show()

    return None

def ejercicioIII() -> None: 
    '''
    Oscilador Armónico Amortiguado: 
    \\ddot{x} = -\\omega^2 x - \\alpha \\dot{x} \\implies
    \\begin{cases}
        \\dot{x} = y \\
        \\dot{y} = -\\omega^2 x - \\alpha y
    \\end{cases}
    '''
    def solucion_analitica(t, x0, v0, omega, alpha) -> np.ndarray:
        if alpha**2 < 4*omega**2: # Subamortiguado
            omega_prima = np.sqrt(omega**2 - (alpha**2) / 4)
            C1 = x0
            C2 = (v0 + alpha * x0 / 2) / omega_prima
            x_analitico = np.exp(-alpha * t / 2) * (C1 * np.cos(omega_prima * t) + C2 * np.sin(omega_prima * t))
            
        elif alpha**2 == 4*omega**2: # Críticamente amortiguado
            C1 = x0
            C2 = v0 + alpha/2 * x0
            x_analitico = (C1 + C2 * t) * np.exp(-alpha * t / 2)
            
        elif alpha**2 > 4*omega**2: # Sobreamortiguado
            gamma = np.sqrt((alpha**2) / 4 - omega**2)
            C1 = (x0 / 2) + (v0 + (alpha / 2) * x0) / (2 * gamma)
            C2 = (x0 / 2) - (v0 + (alpha / 2) * x0) / (2 * gamma)
            x_analitico = np.exp(-alpha * t / 2) * (C1 * np.exp(gamma * t) + C2 * np.exp(-gamma * t))
        
        return x_analitico
    def error(x_numerico, x_analitico) -> float:
        return np.sqrt(np.mean((x_numerico - x_analitico)**2))
    
    global omega 
    global alpha
    omega = 1.5
    alpha = 0.15    
    dt = 0.01     # h
    
    x0 = 1.
    v0 = 0.2      # y0
    t_max = 50
    t = np.arange(0,t_max,dt)
    
    x_analitica = solucion_analitica(t, x0, v0, omega, alpha)
    
    # Comparación con la solución analítica
    plt.figure(figsize=(12, 12))
    plt.suptitle('   ${\\bf OSCILADOR\\ ARMÓNICO\\ AMORTIGUADO}$')
    x_forward, _ = EulerForward(x0,v0,t,dt)
    plt.plot(t, x_analitica, label='Solución analítica', linestyle='--')
    plt.legend(loc='best')
    x_forward_matrix, _ = EulerForwardMatrix(x0,v0,t,dt)
    plt.plot(t, x_analitica, label='Solución analítica', linestyle='--')
    plt.legend(loc='best')
    
    plt.figure(figsize=(12, 12))
    plt.suptitle('   ${\\bf OSCILADOR\\ ARMÓNICO\\ AMORTIGUADO}$')   
    x_backward, _ = EulerBackward(x0,v0,t,dt)
    plt.plot(t, x_analitica, label='Solución analítica', linestyle='--')
    plt.legend(loc='best')
    x_backward_matrix, _ = EulerBackwardMatrix(x0,v0,t,dt)
    plt.plot(t, x_analitica, label='Solución analítica', linestyle='--')
    plt.legend(loc='best')
    
    plt.figure(figsize=(12, 12))
    plt.suptitle('   ${\\bf OSCILADOR\\ ARMÓNICO\\ AMORTIGUADO}$')
    x_CrankNich, _ = CrankNicholson(x0,v0,t,dt)
    plt.plot(t, x_analitica, label='Solución analítica', linestyle='--')
    plt.legend(loc='best')
    x_CrankNich_matrix, _ = CrankNicholsonMatrix(x0,v0,t,dt)
    plt.plot(t, x_analitica, label='Solución analítica', linestyle='--')
    plt.legend(loc='best')
    
    plt.figure(figsize=(12, 6))
    plt.title('   ${\\bf OSCILADOR\\ ARMÓNICO\\ AMORTIGUADO}$ \n RungeKutta IV - Matricial')
    x_RungeKuttaIV, _ = RungeKuttaIVMatrix(x0,v0,t,dt)
    plt.plot(t, x_analitica, label='Solución analítica', linestyle='--')
    plt.legend(loc='best')
    
    # Gráfica de los errores
    err_EuFw = error(x_forward, x_analitica)
    err_EuFw_M = error(x_forward_matrix, x_analitica)
    err_EuBw = error(x_backward, x_analitica)
    err_EuBw_M = error(x_backward_matrix, x_analitica)
    err_CrNi = error(x_CrankNich, x_analitica)
    err_CrNi_M = error(x_CrankNich_matrix, x_analitica)
    err_RuKIV = error(x_RungeKuttaIV, x_analitica)
    
    plt.figure(figsize=(12, 6))
    plt.title('Distancia a la solución analítica')
    plt.xlabel('t')
    plt.grid()
    
    plt.plot(t, np.abs(x_analitica-x_forward), label='Euler Forwards, Error medio = {}'.format(np.round(err_EuFw,5)))
    plt.plot(t, np.abs(x_analitica-x_forward_matrix), label='Euler Forwards Matricial, Error medio = {}'.format(np.round(err_EuFw_M,5)))
    plt.plot(t, np.abs(x_analitica-x_backward), label='Euler Backwards, Error medio = {}'.format(np.round(err_EuBw,5)))
    plt.plot(t, np.abs(x_analitica-x_backward_matrix), label='Euler Backwards Matricial, Error medio = {}'.format(np.round(err_EuBw_M,5)))
    plt.plot(t, np.abs(x_analitica-x_CrankNich), label='Crank Nicholson, Error medio = {}'.format(np.round(err_CrNi,5)))
    plt.plot(t, np.abs(x_analitica-x_CrankNich_matrix), label='Crank Nicholson Matricial, Error medio = {}'.format(np.round(err_CrNi_M,5)))
    plt.plot(t, np.abs(x_analitica-x_CrankNich_matrix), label='Runge Kutta Matricial, Error medio = {}'.format(np.round(err_RuKIV,5)))
    plt.plot(t, np.abs(x_analitica-x_RungeKuttaIV), label='Runge Kutta IV, Error medio = {}'.format(np.round(err_RuKIV,5)))
    
    plt.legend()
    plt.show()
    
    plt.figure()
    # Comparación del error con dt
    err_EuFw_M_list, err_EuBw_M_list, err_CrNi_M_list, err_RuKIV_list = [], [], [], []
    dt_list = np.logspace(0, -3.5, num=20)
    for dt in dt_list:
        print(f"dt: {dt}")
        t = np.arange(0,t_max,dt)
    
        x_analitica = solucion_analitica(t, x0, v0, omega, alpha)
        
        x_forward_matrix, _ = EulerForwardMatrix(x0,v0,t,dt,plot=False)
        x_backward_matrix, _ = EulerBackwardMatrix(x0,v0,t,dt,plot=False)
        x_CrankNich_matrix, _ = CrankNicholsonMatrix(x0,v0,t,dt,plot=False)
        x_RungeKuttaIV, _ = RungeKuttaIVMatrix(x0,v0,t,dt,plot=False)
        
        err_EuFw_M_list.append(error(x_forward_matrix, x_analitica))
        err_EuBw_M_list.append(error(x_backward_matrix, x_analitica))
        err_CrNi_M_list.append(error(x_CrankNich_matrix, x_analitica))
        err_RuKIV_list.append(error(x_RungeKuttaIV, x_analitica))
        
    plt.scatter(dt_list,err_EuFw_M_list,label='Euler Forwards Matricial', marker='x')
    plt.scatter(dt_list,err_EuBw_M_list,label='Euler Backwards Matricial', marker='x')
    plt.scatter(dt_list,err_CrNi_M_list,label='Crank Nicholson Matricial', marker='x')
    plt.scatter(dt_list,err_RuKIV_list,label='Runge Kutta IV Matricial', marker='x')
    plt.xscale('log')
    plt.yscale('log')
    plt.xlabel('dt')
    plt.ylabel('Error medio')   
    plt.legend()
    plt.grid()
    
    plt.show()

def ejercicioIV() -> None:
    '''
    \\frac{\\partial T}{\\partial t}(x,t) = D \\frac{\\partial^2 T}{\\partial x^2}(x,t)
    en [-1,1] con T(x,0)=100e^{-20x^2} y condiciones de contorno de Dirichlet
    
    Usamos diferencias finitas
    Derivada espacial: $\\frac{\\partial^2 T}{\\partial x^2} = \\frac{T(x+dx,t) - 2T(x,t) + T(x-dx,t)}{dx^2}$
                        \\partial^2 T/ \\partial x^2 = (T[i+1,j] - 2T[i,j] + T[i-1,j])/dx^2
    Derivada temporal: \\frac{\\partial T}{\\partial t} = \\frac{T(x,t+dt) - T(x,t)}{dt}
                        \\partial T/ \\partial t = (T[i,j+1] - T[i,j])/dt
    \\implies T[i,j+1] = T[i,j] + D dt/dx^2 (T[i+1,j] - 2T[i,j] + T[i-1,j])
    
    Condición de estabilidad de Fourier:
            D dt/dx^2 <= 1/2
    '''
    
    D = 0.1      # coeficiente de difusión
    L = 1.       # longitud del dominio
    dx = 0.01    # diferencial espacial
    dt = 0.0001  # diferencial temporal
    cte = D*dt/dx**2
    t_max = 2. * L # para que el plot sea cuadrado    
    
    x = np.arange(-L, L, dx)
    t = np.arange(0, t_max, dt)
    
    ###########
    # PARTE I #
    ###########
    
    T = np.zeros((len(x),len(t)))
    
    if cte >= 0.5: # condición de estabilidad
        raise Exception('Condición de estabilidad no satisfecha, prueba a bajar dt')
    
    T[:,0] = 100*exp(-20*x**2)     # condición inicial
    T[0,:], T[-1,:] = 0, 0         # condiciones de frontera
    
    for j in range(len(t)-1):
        for i in range(1,len(x)-1): # sin los extremos
            T[i,j+1] = T[i,j] + cte*(T[i+1,j] - 2*T[i,j] + T[i-1,j])
        
    # Plot espacio/tiempo/magnitud
    plt.figure()
    plt.title('Evolución temporal de T(x) con difusión')
    plt.imshow(T, extent=[0,len(t)*dt,-L,L] ,cmap='inferno', origin='lower')
    plt.colorbar(label='Magnitud de T')
    plt.xlabel('Tiempo (t)')
    plt.ylabel('Espacio (x)')
    
    # Animación
    fig1, ax1 = plt.subplots()
    plt.title('Evolución temporal de T(x) con difusión')
    ax1.set_xlim(-L, L)            # Límite en el espacio  (x)
    ax1.set_ylim(0, np.max(T)*1.1) # Límite en la magnitud (T) (0,+10% del max)
    
    line1, = ax1.plot(x, T[:, 0], label='T(x,t)')
    time_text = ax1.text(0.82, 0.85, '', transform=ax1.transAxes, fontsize=10)

    def animate1(j):
        line1.set_ydata(T[:, j])
        time_text.set_text('t = {}s'.format(np.round(j*dt, 2)))
        ax1.legend()
        return line1, ax1
    
    anim1 = FuncAnimation(fig1, animate1, frames=range(0,len(t),80), interval=1)
    plt.xlabel('Espacio (x)')
    plt.ylabel('Magnitud (T)')
    
    plt.show()
        
    ############
    # PARTE II #
    ############
    '''
        \\frac{\\partial T}{\\partial t}(x,t) = D \\frac{\\partial^2 T}{\\partial x^2}(x,t)
        en [-1,1] con T(x,0)=100e^{-20x^2} y condiciones de contorno de Dirichlet excepto
        en x=1 donde se impone \\frac{dT}{dx} = -T
        
        \\frac{\\partial T}{\\partial t} \\equiv \\frac{T(x,t+dt) - T(x,t)}{dt}
        \\frac{\\partial^2 T}{\\partial x^2} \\equiv \\frac{T(x+dx,t) - 2T(x,t) + T(x-dx,t)}{dx^2}
        
        La EDO queda entonces como
        \\frac{T(x,t+dt) - T(x,t)}{dt} = \\frac12 D ((\\frac{T(x+dx,t+dt) - 2T(x,t+dt) + T(x-dx,t+dt)}{dx^2}) +
                                                    + (\\frac{T(x+dx,t) - 2T(x,t) + T(x-dx,t)}{dx^2}))          \\impiies
        T(x,t+dt) - T(x,t) = \\frac{D dt}{2 dx^2} (T(x+dx,t+dt) - 2T(x,t+dt) + T(x-dx,t+dt) + T(x+dx,t) - 2T(x,t) + T(x-dx,t)) \\implies
        T(x,t+dt) - T(x,t) =              \\alpha (T(x+dx,t+dt) - 2T(x,t+dt) + T(x-dx,t+dt) + T(x+dx,t) - 2T(x,t) + T(x-dx,t)) 
        
        Agrupamos T(x,t+dt) y T(x+dx,t+dt) a la izquierda y T(x,t) y T(x+dx,t) a la derecha
        T(x,t+dt) - \\alpha (T(x+dx,t+dt) - 2T(x,t+dt) + T(x-dx,t+dt)) = T(x,t) + \\alpha (T(x+dx,t) - 2T(x,t) + T(x-dx,t)) \\implies
        -\\alpha T(x-dx,t+dt) + (1+2\\alpha )T(x,t+dt) - \\alpha T(x+dx,t+dt) = \\alpha T(x-dx,t) + (1-2\\alpha )T(x,t) + \\alpha T(x+dx,t)
    '''
    
    T = np.zeros((len(x),len(t)))
    T[:,0] = 100*exp(-20*x**2)     # condición inicial
    T[0,:] = 0                     # condición de frontera x=-1
    
    d: list[float] = [1+2*cte for _ in range(len(x))]
    u: list[float] = [-cte    for _ in range(len(x))]
    o: list[float] = [-cte    for _ in range(len(x))]
    A = sparse.diags([d,u,o],[0,-1,1]).toarray()

    A[0, 0] = 1                     # T(0,t) = 0
    A[-1, -2] = -1                # dT/dx = -T en x=1
    A[-1, -1] = 1+dx    

    A_inv = la.inv(A)
    b = np.zeros(len(T))

    for j in range(len(t)-1):
        b[1:-1] = T[1:-1,j] + cte / 2 * (T[2:,j] - 2*T[1:-1,j] + T[:-2,j]) # punto + cte/2 (siguiente - punto + anterior)
        T[:,j+1] = np.dot(A_inv, b)
    
    # Plot espacio/tiempo/magnitud
    plt.figure()
    plt.title('Evolución temporal de T(x) con difusión, \n Método de Crank-Nicholson')
    plt.imshow(T, extent=[0,len(t)*dt,-L,L] ,cmap='inferno', origin='lower')
    plt.colorbar(label='Magnitud de T')
    plt.xlabel('Tiempo (t)')
    plt.ylabel('Espacio (x)')
    
    # Animación
    fig2, ax2 = plt.subplots()
    plt.title('Evolución temporal de T(x) con difusión, \n Método de Crank-Nicholson')
    ax2.set_xlim(-L, L)            # Límite en el espacio  (x)
    ax2.set_ylim(0, np.max(T)*1.1) # Límite en la magnitud (T) (0,+10% del max)
    
    #print(T[:,340])
    
    line2, = ax2.plot(x, T[:, 0], label='T(x,t)')
    time_text = ax2.text(0.82, 0.85, '', transform=ax2.transAxes, fontsize=10)
    
    def animate2(j):
        line2.set_ydata(T[:, j])
        time_text.set_text('t = {}s'.format(np.round(j*dt, 2)))
        ax2.legend()
        return line2, ax2
    
    anim2 = FuncAnimation(fig2, animate2, frames=range(0,len(t),80), interval=1)
    plt.xlabel('Espacio (x)')
    plt.ylabel('Magnitud (T)')
    
    plt.show()
    
    #####################################################################################
    # Apartado B                                                                        #
    #####################################################################################
    D = 0.1      # coeficiente de difusión
    L = 1.       # longitud del dominio
    dx = 0.03    # diferenciales espaciales
    dy = 0.03  
    dt = 0.002   # diferencial temporal
    cte = D*dt/dx**2
    t_max = 1. * L  
    
    if cte >= 0.5: # condición de estabilidad
        raise Exception('Condición de estabilidad no satisfecha, prueba a bajar dt')
    
    x, y = np.arange(-L, L, dx), np.arange(-L, L, dy)
    t = np.arange(0, t_max, dt)
    
    X, Y = np.meshgrid(x, y)               # para poder operar en la condición inicial
    T = np.zeros((len(x),len(y),len(t)))  
    T[:,:,0] = 100*exp(-20 * (X**2 + Y**2))
    
    d = [-4 for _ in range(len(x)**2)]
    u = [1  for _ in range(len(x)**2-1)]
    o = [1  for _ in range(len(x)**2-1)]
    v = [1  for _ in range(len(x)**2-len(x))]
    
    Laplaciano2D = sparse.diags([d,u,o,v,v],[0,-1,1,-len(x),len(x)],shape=(len(x)**2,len(x)**2))
    A1 = np.eye(len(x)**2)-cte*Laplaciano2D.toarray()
    A2 = np.eye(len(x)**2)+cte*Laplaciano2D.toarray()
    
    # CONTORNO
    for i in range(len(x)):
        zeros = np.zeros(len(x)**2)
        n = len(x)
        
        A1[i,:], A2[i,:] = zeros.copy(), zeros.copy()
        A1[i,i] = 1
        
        A1[i*len(x),:], A2[i*len(x),:] = zeros.copy(), zeros.copy()
        A1[i*len(x),i*len(x)] = 1
        
        A1[-i-1,:], A2[-i-1,:] = zeros.copy(), zeros.copy()
        A1[-i-1,-i-1] = 1
        
        # Condición \\frac{\\partial T}{\\partial x}(x=1) = 0
        A1[i*n + n-1, :], A2[i*n + n-1, :] = zeros.copy(), zeros.copy()
        A1[i*n + n-1, i*n + n-1] = 1
        A1[i*n + n-1, i*n + n-2] = -1
    
    A = np.dot(la.inv(A1),A2) # A2/A1
    for k in range(len(t)-1):
        print(np.round(k/(len(t)-1)*100,1),'%',end='\r')
        T[:,:,k+1] = np.dot(A,T[:,:,k].reshape(len(x)**2)).reshape(len(x),len(x))

    fig3, ax3 = plt.subplots()
    plt.title('Evolución temporal de T(x,y) con difusión, \n Método de Crank-Nicholson')
    ax3.set_xlim(-L, L)            # Límite en el espacio  (x)
    ax3.set_ylim(-L, L)            # Límite en el espacio  (y)
    
    # Hay que transponer para que los ejes estén bien
    line3 = ax3.imshow(T[:, :, 0].T, extent=[-L, L, -L, L], cmap='inferno', origin='lower')
    time_text = ax3.text(0.8, 0.93, '', transform=ax3.transAxes, color='white', fontsize=10)
    fig3.colorbar(line3, label='Magnitud de T')
    
    def animate3(j):
        line3.set_data(T[:, :, j].T) # Hay que transponer para que los ejes coincidan
        time_text.set_text('t = {}s'.format(np.round(j*dt,2)))
        return line3, ax3
    
    anim3 = FuncAnimation(fig3, animate3, frames=range(0,len(t),10), interval=1)
    plt.xlabel('Espacio (x)')
    plt.ylabel('Espacio (y)')
    plt.show()
    
                    ##############################
    ################# EXTRA: Advección constante #####################
                    ##############################
                    
    D = 0.1       # Coeficiente de difusión
    L = 1.        # Longitud del dominio
    dx = 0.03     # Diferenciales espaciales
    dy = 0.03  
    dt = 0.002    # Diferencial temporal
    vx = 0.4     # Velocidad de advección en x
    vy = -0.8     # Velocidad de advección en y
    cte = D * dt / dx**2
    t_max = 1. * L  

    if cte >= 0.5:
        raise Exception('Condición de estabilidad no satisfecha, prueba a bajar dt')

    x, y = np.arange(-L, L, dx), np.arange(-L, L, dy)
    t = np.arange(0, t_max, dt)

    X, Y = np.meshgrid(x, y)
    T = np.zeros((len(x), len(y), len(t)))
    # T[:,:,0] = 100*np.exp(-20*(X**2+Y**2))
    T[:,:,0] = 100*np.exp(-10*(X**2+Y**2)) # si le bajamos la "fuerza" a la exponencial se ve mejor

    d = [-4 for _ in range(len(x)**2)]
    u = [1  for _ in range(len(x)**2-1)]
    o = [1  for _ in range(len(x)**2-1)]
    v = [1  for _ in range(len(x)**2-len(x))]

    Laplaciano2D = sparse.diags([d, u, o, v, v], [0, -1, 1, -n, n], shape=(n**2, n**2))
    A1 = np.eye(len(x)**2)-cte*Laplaciano2D.toarray()
    A2 = np.eye(len(x)**2)+cte*Laplaciano2D.toarray()

    adveccion_x = vx*dt / (2*dx)
    adveccion_y = vy*dt / (2*dy)

    # T = 0 en los bordes
    for i in range(len(x)):
        n = len(x)
        zeros = np.zeros(n**2)
        
        A1[i, :], A2[i, :] = zeros.copy(), zeros.copy()  # x = -L
        A1[i, i] = 1

        A1[i*n, :], A2[i*n, :] = zeros.copy(), zeros.copy() # x = L
        A1[i*n, i*n] = 1

        A1[-i -1, :], A2[-i -1, :] = zeros.copy(), zeros.copy() # y = -L
        A1[-i -1, -i -1] = 1

        A1[i*n + n-1, :], A2[i*n + n-1, :] = zeros.copy(), zeros.copy() # y = L
        A1[i*n + n-1, i*n + n-1] = 1

    A = np.dot(la.inv(A1), A2)

    for k in range(len(t) - 1):
        print(np.round(k/(len(t)-1)*100,1),'%',end='\r')
        T_next = np.dot(A, T[:,:,k].reshape(n**2)).reshape(n, n)
        T_next[1:-1, 1:-1] -= adveccion_x * (T[2:, 1:-1, k] - T[:-2, 1:-1, k])
        T_next[1:-1, 1:-1] -= adveccion_y * (T[1:-1, 2:, k] - T[1:-1, :-2, k])
        
        T[:,:,k+1] = T_next

    fig4, ax4 = plt.subplots()
    plt.title('Evolución temporal de T(x,y) con advección y difusión, \n Método de Crank-Nicholson')
    ax4.set_xlim(-L, L)
    ax4.set_ylim(-L, L)  
    
    flecha = FancyArrowPatch((0, 0), (vx, vy), color='white', lw=0.6, arrowstyle='-|>', mutation_scale=20)
    ax4.add_patch(flecha)
    ax4.legend([flecha], ['Vector de Advección: {}$\\hat{{x}}$ + {}$\\hat{{y}}$'.format(np.round(vx, 2), np.round(vy, 2))], loc='upper right')

    line4 = ax4.imshow(T[:,:,0].T, extent=[-L, L, -L, L], cmap='inferno', origin='lower')
    time_text = ax4.text(0.8, 0.85, '', transform=ax4.transAxes, color='white', fontsize=10)
    fig4.colorbar(line4, label='Magnitud de T')

    def animate4(j):
        line4.set_data(T[:,:,j].T)
        time_text.set_text('t = {}s'.format(np.round(j*dt, 2)))
        return line4, ax4

    anim4 = FuncAnimation(fig4, animate4, frames=range(0, len(t), 10), interval=1)
    plt.xlabel('Espacio (x)')
    plt.ylabel('Espacio (y)')

    plt.show()
    
                    ############################################
    ################# EXTRA: Advección dependiente del espacio #####################
                    ############################################
    
    def vx(x, y):
        return sin(pi*x)*cos(pi*y)
    def vy(x, y):
        return -cos(pi*x)*sin(pi*y)

    D = 0.1       # Coeficiente de difusión
    L = 1.        # Longitud del dominio
    dx = 0.03     # Diferenciales espaciales
    dy = 0.03  
    dt = 0.002    # Diferencial temporal
    cte = D * dt / dx**2
    t_max = 1. * L  

    if cte >= 0.5:
        raise Exception('Condición de estabilidad no satisfecha, prueba a bajar dt')

    x, y = np.arange(-L, L, dx), np.arange(-L, L, dy)
    t = np.arange(0, t_max, dt)

    X, Y = np.meshgrid(x, y)
    T = np.zeros((len(x), len(y), len(t)))
    T[:,:,0] = 100 * np.exp(-10 * (X**2 + Y**2)) # Gaussiana más "floja"

    n = len(x)
    d = [-4 for _ in range(len(x)**2)]
    u = [1  for _ in range(len(x)**2-1)]
    o = [1  for _ in range(len(x)**2-1)]
    v = [1  for _ in range(len(x)**2-len(x))]

    Laplaciano2D = sparse.diags([d, u, o, v, v], [0, -1, 1, -n, n], shape=(n**2, n**2))
    A1 = np.eye(len(x)**2) - cte * Laplaciano2D.toarray()
    A2 = np.eye(len(x)**2) + cte * Laplaciano2D.toarray()

    for i in range(len(x)):
        n = len(x)
        zeros = np.zeros(n**2)
        
        A1[i, :], A2[i, :] = zeros.copy(), zeros.copy()  # x = -L
        A1[i, i] = 1

        A1[i*n, :], A2[i*n, :] = zeros.copy(), zeros.copy()  # x = L
        A1[i*n, i*n] = 1

        A1[-i -1, :], A2[-i -1, :] = zeros.copy(), zeros.copy()  # y = -L
        A1[-i -1, -i -1] = 1

        A1[i*n + n-1, :], A2[i*n + n-1, :] = zeros.copy(), zeros.copy()  # y = L
        A1[i*n + n-1, i*n + n-1] = 1

    A = np.dot(la.inv(A1), A2)

    for k in range(len(t) - 1):
        print(np.round(k / (len(t) - 1) * 100, 1), '%', end='\r')
        T_next = np.dot(A, T[:,:,k].reshape(n**2)).reshape(n, n)
        
        adveccion_x_local = np.array([[vx(x[i], y[j]) for j in range(1, n-1)] for i in range(1, n-1)])
        T_next[1:-1, 1:-1] -= (adveccion_x_local * dt / (2 * dx)) * (T[2:, 1:-1, k] - T[:-2, 1:-1, k])
        
        adveccion_y_local = np.array([[vy(x[i], y[j]) for j in range(1, n-1)] for i in range(1, n-1)])
        T_next[1:-1, 1:-1] -= (adveccion_y_local * dt / (2 * dy)) * (T[1:-1, 2:, k] - T[1:-1, :-2, k])

        T[:,:,k+1] = T_next

    fig5, ax5 = plt.subplots()
    plt.title('Evolución temporal de T(x,y) con advección variable y difusión, \n Método de Crank-Nicholson')
    ax5.set_xlim(-L, L)
    ax5.set_ylim(-L, L)

    line5 = ax5.imshow(T[:,:,0].T, extent=[-L, L, -L, L], cmap='inferno', origin='lower')
    time_text = ax5.text(0.8, 0.85, '', transform=ax5.transAxes, color='white', fontsize=10)
    fig5.colorbar(line5, label='Magnitud de T')

    def animate5(j):
        line5.set_data(T[:,:,j].T)
        time_text.set_text('t = {}s'.format(np.round(j * dt, 2)))
        return line5, ax5

    anim5 = FuncAnimation(fig5, animate5, frames=range(0, len(t), 10), interval=1)
    plt.xlabel('Espacio (x)')
    plt.ylabel('Espacio (y)')

    plt.show()

    return None

def ejercicioV() -> None:
    ###########
    # PARTE I #
    ###########
    
    def uf(x):
        if 1 <= x <= 2:
            return 1
        else:
            return 0
    uf = np.vectorize(uf)
    
    def contornoPeriódico(a):
        a[0] = a[-2]
        a[-1] = a[1]
        return a
    def energia(a):
        a = np.array(a)
        return np.sum(0.5*a**2)

    def diferencias_centrales(u,dx,dt):
        E = np.zeros(len(t))
        for i in range(len(t)-1):
            u[1:-1,i+1] = u[1:-1,i] - (c * dt) / (2 * dx) * (u[2:,i] - u[:-2,i])
            u[:,i+1] = contornoPeriódico(u[:,i+1])
            E[i] = energia(u[:,i])
        return u,E
    
    def upwind(u,dx,dt):
        E = np.zeros(len(t))
        for i in range(len(t)-1):
            u[1:-1,i+1] = u[1:-1,i] - (c * dt) / dx * (u[1:-1,i] - u[:-2,i])
            u[:,i+1] = contornoPeriódico(u[:,i+1])
            E[i] = energia(u[:,i])
        return u,E
    
    def downwind(u,dx,dt):
        E = np.zeros(len(t))
        for i in range(len(t)-1):
            u[1:-1,i+1] = u[1:-1,i] - (c * dt) / dx * (u[2:,i] - u[1:-1,i])
            u[:,i+1] = contornoPeriódico(u[:,i+1])
            E[i] = energia(u[:,i])
        return u,E
    
    
    # ¿ Qué método es estable y bajo qué condiciones es no difusivo ? Upwind
    L = 5.        # Longitud del dominio
    dx = 0.01     # Diferencial espacial
    dt = 0.005    # Diferencial temporal
    global c
    c = 2.        # cte
    t_max = 5.    # Tiempo de simulación

    if c*dt > dx:
        raise Exception('Condición de estabilidad no satisfecha')

    x = np.arange(0, L, dx)
    t = np.arange(0, t_max, dt)
    
    U = np.zeros((len(x),len(t)))
    U[:,0] = uf(x)

    fig, ax = plt.subplots()
    plt.suptitle('Método Upwind')
    ax.set_xlim(0, L)
    ax.set_ylim(-0.1, 1.1)
    line_upwind, = ax.plot([],[], label='u', color='blue')
    ax.set_xlabel('x')
    ax.set_ylabel('u(x, t)')
    ax.legend(loc = 'upper right')
    time_text = ax.text(0.8, 0.8, '', transform=ax.transAxes, fontsize=11)
    
    Uu, _ = upwind(U.copy(), dx, dt)
    def update(j):
        line_upwind.set_data(x, Uu[:, j])

        time_string = 't = {}s'.format(np.round(j * dt, 2))
        time_text.set_text(time_string)
        
        return line_upwind, time_text, ax
    
    ani = FuncAnimation(fig, update, frames=range(0,len(t)), interval=10)
    plt.show()
        
    ############################################3
    L = 5.        # Longitud del dominio
    dx = 0.05     # Diferencial espacial
    dt = 0.005    # Diferencial temporal
    t_max = 5.    # Tiempo de simulación

    if c*dt > dx:
        raise Exception('Condición de estabilidad no satisfecha')

    x = np.arange(0, L, dx)
    t = np.arange(0, t_max, dt)
    
    U = np.zeros((len(x),len(t)))
    U[:,0] = uf(x)

    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 8))
    plt.suptitle('Comparación de los 3 métodos')
    ax1.set_xlim(0, L)
    ax1.set_ylim(-0.1, 1.1)
    line_central, = ax1.plot([],[], label='Diferencias Centrales (Inestable)', color='blue')
    line_upwind, = ax1.plot([], [], label='Upwind (Difusivo)', color='green')
    line_downwind, = ax1.plot([], [], label='Downwind (Inestable)', color='red')
    time_text1 = ax1.text(0.86, 0.84, '', transform=ax1.transAxes, fontsize=11)
    time_text2 = ax2.text(0.86, 0.84, '', transform=ax1.transAxes, fontsize=11)
    ax1.set_xlabel('x')
    ax1.set_ylabel('u(x, t)')
    ax1.legend(loc = 'upper right')
    
    ax2.set_xlim(0, t_max)
    ax2.set_ylim(1, 1e5)  # Ajustar los límites iniciales para la escala logarítmica
    ax2.set_yscale('log')     # Escala logarítmica en el eje y
    line_energy_central, = ax2.plot([], [], label='Energía Diferencias Centrales', color='blue')
    line_energy_upwind, = ax2.plot([], [], label='Energía Upwind', color='green')
    line_energy_downwind, = ax2.plot([], [], label='Energía Downwind', color='red')
    ax2.set_xlabel('Tiempo (s)')
    ax2.set_ylabel('Energía total')
    ax2.legend(loc = 'upper right')

    Uc, Ec = diferencias_centrales(U.copy(), dx, dt)
    Uu, Eu = upwind(U.copy(), dx, dt)
    Ud, Ed = downwind(U.copy(), dx, dt)
    def update(j):
        line_central.set_data(x, Uc[:, j])
        line_upwind.set_data(x, Uu[:, j])
        line_downwind.set_data(x, Ud[:, j])

        line_energy_central.set_data(t[:j+1], Ec[:j+1])
        line_energy_upwind.set_data(t[:j+1], Eu[:j+1])
        line_energy_downwind.set_data(t[:j+1], Ed[:j+1])

        time_string = 't = {}s'.format(np.round(j * dt, 2))
        time_text1.set_text(time_string)
        time_text2.set_text(time_string)
        
        return line_central, line_upwind, line_downwind, line_energy_central, line_energy_upwind, line_energy_downwind, time_text1, time_text2

    ani = FuncAnimation(fig, update, frames=range(0,len(t),20), interval=1)
    plt.show()
        
    ############
    # PARTE II #
    ############         
    def diferencias_centrales_burguers(u, dx, dt):
        E = np.zeros(len(t))
        for i in range(len(t)-1):
            u[1:-1,i+1] = u[1:-1,i] * (1 - dt / (2 * dx) * (u[2:,i] - u[:-2,i]))
            u[:,i+1] = contornoPeriódico(u[:,i+1])
            
            E[i] = energia(u[:,i])
            umax= max(u[:,i+1])
            
            if dt*umax/dx >1:
                print('¡Estabilidad!')
        return u, E

    def upwind_conservativo(u, dx, dt):
        E = np.zeros(len(t))
        for i in range(len(t)-1):
            u[1:-1,i+1] = u[1:-1,i] - 0.5* dt / dx * (u[1:-1, i]**2 - u[:-2, i]**2)
            u[:,i+1] = contornoPeriódico(u[:,i+1])
            
            E[i] = energia(u[:,i])
        return u, E
        
    L = 1.        # Longitud del dominio
    dx = 0.01     # Diferencial espacial
    dt = 0.001    # Diferencial temporal
    t_max = 1.    # Tiempo de simulación

    if dt/dx > 1:
        raise Exception('Condición de estabilidad no satisfecha')

    x = np.arange(0, L, dx)
    t = np.arange(0, t_max, dt)
    
    U = np.zeros((len(x),len(t)))
    U[:,0] = 2+0.5*sin(2*pi*x.copy())
    
        
    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 8))
    plt.suptitle('Burguers vs Upwind para $u(x,0)=2 + \\frac{1}{2}\\sin(2\\pi x)$')
    ax1.set_xlim(0, L)
    ax1.set_ylim(1, 3)
    line_central, = ax1.plot([], [], label='Diferencias Centrales Burguers', color='blue')
    line_upwind, = ax1.plot([], [], label='Upwind Conservativo', color='green')
    time_text = ax1.text(0.8, 0.85, '', transform=ax1.transAxes, fontsize=10)
    ax1.set_xlabel('x')
    ax1.set_ylabel('u(x, t)')
    ax1.legend()

    ax2.set_xlim(0, t_max)
    ax2.set_ylim(1e2, 1e5)  # Ajustar límites para la escala logarítmica
    ax2.set_yscale('log')  # Escala logarítmica en el eje y
    line_energy_central, = ax2.plot([], [], label='Energía Diferencias Centrales Burguers', color='blue')
    line_energy_upwind, = ax2.plot([], [], label='Energía Upwind Conservativo', color='green')
    ax2.set_xlabel('Tiempo (s)')
    ax2.set_ylabel('Energía total')
    ax2.legend()

    Uc, Ec = diferencias_centrales_burguers(U.copy(), dx, dt)
    Uu, Eu = upwind_conservativo(U.copy(), dx, dt)
    def update(j):
        line_central.set_data(x, Uc[:, j])
        line_upwind.set_data(x, Uu[:, j])

        line_energy_central.set_data(t[:j+1], Ec[:j+1])
        line_energy_upwind.set_data(t[:j+1], Eu[:j+1])

        time_text.set_text('t = {}s'.format(np.round(j * dt, 2)))

        return line_central, line_upwind, line_energy_central, line_energy_upwind, time_text

    ani = FuncAnimation(fig, update, frames=range(0, len(t), 10), interval=1)
    plt.show()
    
    
    #########################################
    # Extra
    #########################################
    L = 1.        # Longitud del dominio
    dx = 0.01     # Diferencial espacial
    dt = 0.001    # Diferencial temporal
    t_max = 3.    # Tiempo de simulación
    
    x = np.arange(0, L, dx)
    t = np.arange(0, t_max, dt)
    
    def downwind_conservativo(u, dx, dt):
        E = np.zeros(len(t))
        for i in range(len(t)-1):
            u[1:-1,i+1] = u[1:-1,i] - 0.5* dt / dx * (u[2:, i]**2 - u[1:-1, i]**2)
            u[:,i+1] = contornoPeriódico(u[:,i+1])
            
            E[i] = energia(u[:,i])
        return u, E

    def funcion_metodo(u, dx, dt):
        E = np.zeros(len(t))
        for i in range(len(t)-1):
            for j in range(1,len(x)-1):
                if u[j,i] >0:     
                    u[j,i+1] = u[j,i] - 0.5* dt / dx * (u[j, i]**2 - u[j-1, i]**2)
                else:
                    u[j,i+1] = u[j,i] - 0.5* dt / dx * (u[j+1, i]**2 - u[j, i]**2)
                E[i] = energia(u[:,i])
                
                #u[:,i+1] = contornoPeriódico(u[:,i+1])
        return u, E

    if dt/dx > 1:
        raise Exception('Condición de estabilidad no satisfecha')

    x = np.arange(0, L, dx)
    t = np.arange(0, t_max, dt)
    
    U = np.zeros((len(x),len(t)))
    U[:,0] = sin(2*pi*x.copy())
    
    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(8, 8))
    plt.suptitle('Método Combinado $u(x,0)=\\sin(2\\pi x)$')
    ax1.set_xlim(0, L)
    ax1.set_ylim(-1.5, 1.5)
    line_upwind, = ax1.plot([], [], label='Upwind/Downwind Conservativo', color='green')
    time_text = ax1.text(0.8, 0.85, '', transform=ax1.transAxes, fontsize=10)
    ax1.set_xlabel('x')
    ax1.set_ylabel('u(x, t)')
    ax1.legend()

    ax2.set_xlim(0, t_max)
    ax2.set_ylim(0, 30)  # Ajustar límites para la escala logarítmica
    line_energy_upwind, = ax2.plot([], [], label='Energía Método Combinado', color='green')
    ax2.set_xlabel('Tiempo (s)')
    ax2.set_ylabel('Energía total')
    ax2.legend()

    Uu, Eu = upwind_conservativo(U.copy(), dx, dt)
    Ud, Ed = downwind_conservativo(U.copy(), dx, dt)
    
    metodo = funcion_metodo(U.copy(), dx, dt)
    
    def update(j):
        line_upwind.set_data(x, metodo[0][:,j])
        line_energy_upwind.set_data(t[:j+1], metodo[1][:j+1])
        time_text.set_text('t = {}s'.format(np.round(j * dt, 2)))
        return line_upwind, line_energy_upwind, time_text

    ani = FuncAnimation(fig, update, frames=range(0, len(t), 10), interval=1)
    plt.show()
    
    return None

def ejercicioVI() -> None:
    #####################################################################################
    #                                     OPCIÓN A)                                     #
    ##################################################################################### 
    global Laplaciano, dt, x, cte, Nx, Nt, L
    
    ##############
    # CONSTANTES #
    ##############
    # Espacio
    L = 1.
    Nx = 100
    dx = L / Nx
    x = np.linspace(0, L, Nx)
    
    # Tiempo
    tmax = 1.
    Nt = int(2e4)
    dt = tmax / Nt
    t = np.linspace(0, tmax, Nt)
    
    # Otros
    cte = 1j * dt / (2 * dx**2)
    if dt > dx**2 / 2:
        raise Exception('Condición de estabilidad no satisfecha')
    
    # Laplaciano
    d = np.array([-2 for _ in range(Nx)])
    u = np.array([1 for _ in range(Nx - 1)])
    o = np.array(u.copy())
    Laplaciano = sparse.diags([d,u,o], [0,-1,1], shape=(len(x), len(x))).toarray()
    
    ###################
    # FUNCIÓN INICIAL #
    ###################
    def psi_0(x):
        sigma = 1e-2
        psi = exp(-(x-L/2)**2/(2*sigma)) # * exp(-5j*(x-L/2)) # Término con momento
        norm = np.sqrt(np.trapz(np.abs(psi)**2, x))
        return psi / norm   # para que se cumpla la normalización en psi0

    ##########################
    # FUNCIÓN SHRODINGER C-N #
    ##########################
    def schrodinger(V, index, total):
        A = np.eye(len(x)) + cte * Laplaciano
        B = np.eye(len(x)) - cte * Laplaciano
        
        for i in range(len(x)):
            A[i, i] += 1j * dt * V[i] * 0.5
            B[i, i] -= 1j * dt * V[i] * 0.5
        
        psi = np.zeros((Nx, Nt), dtype=complex)
        psi[:, 0] = psi_0(x) # función inicial
        
        psi[0, 0] = 0 # Dirichlet
        psi[-1, 0] = 0
        
        alpha = 0.999
        A_inv = la.inv(A)
        for i in range(len(t) - 1):
            print(f'Progreso en el cálculo para el Potencial [{index}/{total}]: {int(i / (len(t)-1) * 100)}%', end='\r')
            psi[:, i+1] = np.dot(A_inv, np.dot(B, psi[:, i]))
                        
            # psi[0, i+1], psi[-1, i+1] = 0, 0 # Dirichlet
            psi[0, i+1] *= alpha
            psi[-1, i+1] *= alpha
            
        norm = np.array([np.sqrt(np.trapz(np.abs(psi[:,i])**2, x)) for i in range(0, Nt, 30)])
        esperado_pos = np.array([np.trapz(x * np.abs(psi[:,i])**2, x) for i in range(0, Nt, 5)])
        
        return psi, norm, esperado_pos
    
    ###############
    # POTENCIALES #
    ###############
    potenciales, p_name = [], []
    
    # Armónico
    omega = 10.
    m = 1.
    V1 = 1/2*m*omega**2* (x - L/2)**2
    potenciales.append(V1)
    p_name.append('Armónico')
    
    # Pozo Infinito
    V2 = np.array([1e6 if x[i] <= 0 or x[i] >= L else 0 for i in range(Nx)])
    potenciales.append(V2)
    p_name.append('Pozo Infinito')
    
    # Delta Dirac    
    delta_max = 1e6
    centro = int(Nx/4)
    ancho = 0

    V3 = np.zeros(Nx)
    if ancho != 0:
        V3[centro-ancho:centro+ancho] = delta_max
    else:
        V3[centro] = delta_max
    potenciales.append(V3)
    p_name.append('Delta de Dirac')
    
    # Pozo Dirac    
    delta_max = -1e6
    centro = int(Nx/4)
    ancho = 0

    V4 = np.zeros(Nx)
    if ancho != 0:
        V4[centro-ancho:centro+ancho] = delta_max
    else:
        V4[centro] = delta_max
    potenciales.append(V4)
    p_name.append('Pozo de Dirac')
    
    # Escalón de potencial
    V5 = np.array([80 if x[i] < L/5 else 0 for i in range(Nx)])
    potenciales.append(V5)
    p_name.append('Escalón')

    resultados = [schrodinger(V, i+1, len(potenciales)) for i, V in enumerate(potenciales)]
    
    ####################################
    # FUNCIÓN PARA GENERAR ANIMACIONES #
    ####################################
    def generar_animaciones(resultados):
        fig, axes = plt.subplots(len(resultados), 4, figsize=(20, 4*len(resultados)), tight_layout=True)
        if len(resultados) == 1:
            axes = [axes]  # problemas caso un solo potencial
        
        lines, imshows, time_texts = [], [], []
        psi_max = np.max([np.abs(psi)**2 for psi,_,_ in resultados])
        
        for idx, (psi, norm, esperado_pos) in enumerate(resultados):
            # Animación de la función de onda
            line_psi, = axes[idx, 0].plot([], [], color='blue')
            lines.append(line_psi)
            axes[idx, 0].set_xlim(0, L)
            axes[idx, 0].set_ylim(0, psi_max)
            axes[idx, 0].set_title(f'Potencial {p_name[idx]}: Evolución de $\\left|\\psi(x,t)\\right|^2$')
            axes[idx, 0].set_xlabel('Posición (x)')
            axes[idx, 0].set_ylabel('$\\left|\\psi(x,t)\\right|^2$')
            time_text = axes[idx, 0].text(0.8, 0.5, '', transform=axes[idx, 0].transAxes, fontsize=10)
            time_texts.append(time_text)

            # Potencial
            axes[idx, 1].plot(x, potenciales[idx], color='red')
            axes[idx, 1].set_xlim(0, L)
            axes[idx, 1].set_ylim(min(potenciales[idx]) * 1.1, max(potenciales[idx]) * 1.1)
            axes[idx, 1].set_xlim(0-0.05, L+0.05)
            axes[idx, 1].set_title(f'Potencial {p_name[idx]}')
            axes[idx, 1].set_xlabel('Posición (x)')
            axes[idx, 1].set_ylabel('V(x)')
            
            # Imshow del espacio-tiempo
            imshow = axes[idx, 2].imshow(np.abs(psi[:, ::30].T)**2, aspect='auto', extent=[0, L, 0, tmax], cmap='inferno')
            imshows.append(imshow)
            axes[idx, 2].set_title(f'Potencial {p_name[idx]}: Espacio-Tiempo')
            axes[idx, 2].set_xlabel('Posición (x)')
            axes[idx, 2].set_ylabel('Tiempo (t)')
            axes[idx, 2].set_ylim(0, 0.12)
            fig.colorbar(imshow, ax=axes[idx, 2])

            # Norma y Valor Esperado de x
            ax_norm = axes[idx, 3].twinx()
            axes[idx, 3].plot(np.linspace(0, tmax, len(norm)), norm, color='blue', label='Norma $\\|\\psi(x,t)\\|$', linestyle='dotted')
            ax_norm.plot(np.linspace(0, tmax, len(esperado_pos)), esperado_pos, color='green', label='$\\langle x(t) \\rangle$', linestyle='solid')
            axes[idx, 3].set_ylim(0, 1.05) # norma
            ax_norm.set_ylim(0, L) # <x>
            axes[idx, 3].set_title(f'Potencial {p_name[idx]}: Norma y Valor Esperado $\\langle x(t) \\rangle$')
            axes[idx, 3].set_xlabel('Tiempo (t)')
            axes[idx, 3].set_ylabel('Norma $\\|\\psi(x,t)\\|$', color='blue')
            ax_norm.set_ylabel('$\\langle x(t) \\rangle$', color='green')
            axes[idx, 3].legend(loc='lower left')
            ax_norm.legend(loc='lower right')

        def init():
            for line in lines:
                line.set_data([], [])
            for time_text in time_texts:
                time_text.set_text('')
            return lines + time_texts

        def animate(i):
            for idx, (psi, _, _) in enumerate(resultados):
                lines[idx].set_data(x, np.abs(psi[:, i])**2)
                time_texts[idx].set_text(f't = {i*dt:.2f}s')
            return lines + time_texts
        
        anim = FuncAnimation(fig, animate, init_func=init, frames=range(0,Nt,10), interval=1, blit=True)
        # gif_writer = FFMpegFileWriter(fps=60)
        # anim.save("/home/victor/fisicaua/cuarto/definitivo.gif",writer=gif_writer)
        plt.show()

    generar_animaciones(resultados)
    
#     #####################################################################################
#     #                                     OPCIÓN B)                                     #
#     #####################################################################################
#     L = 2.
#     T = 40.  # t_max
#     Nx = 100
#     Nt = 3000
#     dx = L/Nx
#     dt = T/Nt

#     c = 1.
#     k_rho = 0.1
#     a = -2.0

#     x = np.linspace(0, L, Nx)
#     t = np.linspace(0, T, Nt)

#     modos = [1, 2, 3, 4]
#     u = np.zeros((len(modos), Nx, Nt))  # modo, espacio y tiempo

#     for idx, n in enumerate(modos):
#         u[idx, :, 0] = np.sin(n*pi*x/L)  # cada modo con sin(n*pi*0/L)

#     # Contorno
#     u[:, 0, :] = 0 # extremos fijos
#     u[:, -1, :] = 0

#     for idx in range(len(modos)):
#         u[idx, :, 1] = u[idx, :, 0]  # du/dt(0) = 0

#     for j in range(1, Nt-1):
#         for idx in range(len(modos)):           
#             u[idx,1:-1,j+1] = (2*u[idx,1:-1,j] - u[idx,1:-1,j-1] + (c**2*dt**2/dx**2) * (u[idx,2:,j] 
#                             - 2*u[idx,1:-1,j] + u[idx,:-2,j]) - (2*k_rho*dt * u[idx,1:-1,j] 
#                             + a*dt**2*u[idx,1:-1,j]))

#     # Crank-Nicholson
#     # cte = (c*dt/dx)**2 * 2

#     # d = np.array([1 - 2*cte for _ in range(Nx)])
#     # ud = np.array([cte for _ in range(Nx - 1)])
#     # o = np.array(ud.copy())

#     # A = sparse.diags([d,ud,o],[0,-1,1]).toarray()
#     # B = sparse.diags([2-d,ud,o],[0,-1,1]).toarray()

#     # A_inv = la.inv(A)
#     # # A u^{j+1} = B u^j
#     # for j in range(1, Nt - 1):
#     #     for idx in range(len(modos)):
#     #         b = np.dot(B, u[idx, :, j]) - (2*k_rho*dt - a*dt**2)*u[idx, :, j]
#     #         b[0],b[-1] = 0,0
#     #         u[idx, :, j+1] = np.dot(A_inv, b)
            
#     #         u[idx,0,j+1],u[idx,-1,j+1] = 0,0

#     fig, ax = plt.subplots()
#     ax.set_xlim(-0.05, L+0.05)
#     ax.set_ylim(-1.05, 1.05)

#     lines = []
#     for idx, n in enumerate(modos):
#         line, = ax.plot(x, u[idx, :, 0], label='Modo {}'.format(n))
#         lines.append(line)
#     time_text = ax.text(0.85, 0.65, '', transform=ax.transAxes, fontsize=10)

#     def animate(j):
#         for idx, line in enumerate(lines):
#             line.set_ydata(u[idx, :, j])
#         time_text.set_text('t = {}s'.format(np.round(j*dt, 2)))
#         return *lines, time_text

#     anim = FuncAnimation(fig, animate, frames=range(Nt), interval=20, blit=True)
#     ax.legend(loc="upper right")
#     #plt.show()

# Main
def main() -> None:
    #ejercicioI()
    #ejercicioII()
    #ejercicioIII()
    #ejercicioIV()
    #ejercicioV()
    ejercicioVI()

if __name__ == '__main__':
    main()