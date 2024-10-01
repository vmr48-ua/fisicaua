import numpy as np
import numpy.linalg as la
from numpy import sin, cos, pi

import time
import random
import matplotlib.pyplot as plt
from scipy import sparse
from mpl_toolkits.mplot3d import Axes3D

# Funciones Auxiliares de los ejercicios
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

def EulerForward(x0,v0,t,dt) -> np.ndarray:
    '''
    \\begin{cases}
        x_{n+1} = x_n + h y_n
        y_{n+1} = y_n - h (\omega^2 x_n + \alpha y_n)
    \end{cases}
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

def EulerForwardMatrix(x0, v0, t, dt) -> np.ndarray:
    """ 
    -   z_{n+1} = \mathcal{A} z_n
    
    -   \\begin{cases}
            x_{n+1} = x_n + h y_n
            y_{n+1} = y_n - h (\omega^2 x_n + \alpha y_n)
        \end{cases}
        
    \implies
    A = ((1,dt),(-dt \omega^2, 1 - dt \alpha))
    """
    z = np.zeros((2, len(t)))  # 2 filas (x, v), columnas = len(t)
    z[0, 0], z[1, 0] = x0, v0 

    # Manual funciona mejor (hay que cambiar z en el for)
    # A = np.array([[0         , 1     ],
    #               [-omega**2 , -alpha]])
    A = np.array([[1, dt], [-omega**2 * dt, 1 - alpha * dt]])

    for i in range(1, len(t)):
        # Manual
        # z[:, i] = np.dot(np.eye(len(A)) + dt*A, z[:, i - 1])
        z[:, i] = np.dot(A, z[:, i - 1])

    x, v = z[0, :], z[1, :]

    plt.subplot(2,1,2)
    plt.plot(t, x, label='Posición (x)')
    plt.plot(t, v, label='Velocidad (v)', linestyle='--')
    plt.title('Euler Forward - Matricial')
    plt.xlabel('t')
    plt.ylabel('x, v')
    plt.legend()
    plt.grid()
    
    return x, v

def EulerBackward(x0,v0,t,dt) -> np.ndarray:
    '''
    Despejamos y_{n+1} de la segunda ecuación y sustituimos en la primera
    \begin{cases}
        x_{n+1} = x_n + h y_{n+1}
        y_{n+1} = y_n - h (\omega^2 x_{n+1} + \alpha y_{n+1})
    \end{cases}    
    
    Despejando
    y_{n+1} = y_n - h (\omega^2 x_{n+1} + \alpha y_{n+1}) \Longleftrightarrow
    y_{n+1} = y_n + h (- \omega^2 x_{n+1} - \alpha y_{n+1}) \Longleftrightarrow
    y_{n+1} (1 + h \alpha) = y_n - h \omega^2 x_{n+1} \Longleftrightarrow
    y_{n+1} = \frac{y_n - h \omega^2 x_{n+1}}{1 + h \alpha}
    
    Sustituyendo
    x_{n+1} = x_n + h y_{n+1} \Longleftrightarrow
    x_{n+1} = x_n + h \frac{y_n - h \omega^2 x_{n+1}}{1 + h \alpha}
    
    Finalmente
    \begin{cases}
        x_{n+1} = x_n + h \frac{y_n - h \omega^2 x_{n+1}}{1 + h \alpha}
        y_{n+1} = \frac{y_n - h \omega^2 x_{n+1}}{1 + h \alpha}
    \end{cases}
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

def EulerBackwardMatrix(x0, v0, t, dt) -> np.ndarray:
    """ 
    -   z_{n+1} = \mathcal{A} z_n
    
    -   \begin{cases}
            x_{n+1} = x_n + h \frac{y_n - h \omega^2 x_{n+1}}{1 + h \alpha}
            y_{n+1} = \frac{y_n - h \omega^2 x_{n+1}}{1 + h \alpha}
        \end{cases}
        
    \implies
    A = ((1 - \frac{h^2 \omega^2}{1+h \alpha}, \frac{h}{1+h\alpha}),
         (   -\frac{h   \omega^2}{1+h \alpha}, \frac{1}{1+h \alpha}))
    """
    z = np.zeros((2, len(t)))  # 2 filas (x, v), columnas = len(t)
    z[0, 0], z[1, 0] = x0, v0

    # A = np.array([[1-(dt**2 * omega**2)/(1 + dt*alpha),   dt/(1 + dt*alpha)],
    #              [-1 * (dt * omega**2)/(1 + dt*alpha),    1/(1 + dt*alpha)]])
    A = la.inv(np.eye(2)-dt*np.array([[0,1],[-omega**2,-alpha]]))

    for i in range(1, len(t)):
        z[:, i] = np.dot(A, z[:, i - 1])

    x, v = z[0, :], z[1, :]

    plt.subplot(2,1,2)
    plt.plot(t, x, label='Posición (x)')
    plt.plot(t, v, label='Velocidad (v)', linestyle='--')
    plt.title('Euler Backward - Matricial')
    plt.xlabel('t')
    plt.ylabel('x, v')
    plt.legend()
    plt.grid()
    
    return x, v

def CrankNicholson(x0,v0,t,dt) -> np.ndarray:
    '''
    Hace la media entre las derivadas en el punto n y n+1
    \begin{cases}
        x_{n+1} = x_n + \frac{h}{2} (y_n + y_{n+1})
        y_{n+1} = y_n - \frac{h}{2} ( (\omega^2 x_n + \alpha y_n) + (\omega^2 x_{n+1} + \alpha y_{n+1}) )
    \end{cases}
    
    Despejamos y_{n+1}
    y_{n+1} = y_n - \frac{h}{2} ( \omega^2 x_n + \alpha y_n + \omega^2 x_{n+1} + \alpha y_{n+1} ) \Longleftrightarrow
    y_{n+1} (1 + \frac{h}{2} \alpha) = y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n + \omega^2 x_{n+1} \Longleftrightarrow
    y_{n+1} = \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n + \omega^2 x_{n+1}}{1 + \frac{h}{2} \alpha}
    
    Sustituimos y_{n+1} en x_{n+1} y despejamos x_{n+1}
    x_{n+1} = x_n + \frac{h}{2} (y_n + y_{n+1}) \Longleftrightarrow
    x_{n+1} = x_n + \frac{h}{2} (y_n + \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n + \omega^2 x_{n+1}}{1 + \frac{h}{2} \alpha}) \Longleftrightarrow
    x_{n+1} (1 + (\frac{h}{2} \omega)^2) = x_n + \frac{h}{2} (y_n + \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n}{1 + \frac{h}{2} \alpha}) \Longleftrightarrow
    x_{n+1} = \frac{x_n + \frac{h}{2} (y_n + \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n}{1 + \frac{h}{2} \alpha})}{1 + (\frac{h}{2} \omega)^2}

    Finalmente
    \begin{cases}
        x_{n+1} = \frac{x_n + \frac{h}{2} (y_n + \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n}{1 + \frac{h}{2} \alpha})}{1 + (\frac{h}{2} \omega)^2}
        y_{n+1} = \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n + \omega^2 x_{n+1}}{1 + \frac{h}{2} \alpha}
    \end{cases}
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

def CrankNicholsonMatrix(x0, v0, t, dt) -> np.ndarray:
    """ 
    -   z_{n+1} = \mathcal{A} z_n
    
    -   \begin{cases}
            x_{n+1} = \frac{x_n + \frac{h}{2} (y_n + \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n}{1 + \frac{h}{2} \alpha})}{1 + (\frac{h}{2} \omega)^2}
            y_{n+1} = \frac{y_n - \frac{h}{2} (\omega^2 x_n + \alpha y_n + \omega^2 x_{n+1}}{1 + \frac{h}{2} \alpha}
        \end{cases}
    """
    z = np.zeros((2, len(t)))  # 2 filas (x, v), columnas = len(t)
    z[0, 0], z[1, 0] = x0, v0

    # A = np.array([[1              , -dt/2           ],
    #               [dt/2 * omega**2, 1 + dt/2 * alpha]])
    
    # B = np.array([[1               , dt/2            ],
    #               [-dt/2 * omega**2, 1 - dt/2 * alpha]])
    # A_inv = la.inv(A)
    A = np.dot(la.inv(np.eye(2)-(dt/2)*np.array([[0,1],[-omega**2,-alpha]])),np.eye(2)+(dt/2)*np.array([[0,1],[-omega**2,-alpha]]))

    for i in range(1, len(t)):
        z[:, i] = np.dot(A,z[:,i-1])
        #z[:, i] = np.dot(A_inv, np.dot(B, z[:, i - 1]))  

    x, v = z[0, :], z[1, :]

    plt.subplot(2,1,2)
    plt.plot(t, x, label='Posición (x)')
    plt.plot(t, v, label='Velocidad (v)', linestyle='--')
    plt.title('Crank-Nicholson - Matricial')
    plt.xlabel('t')
    plt.ylabel('x, v')
    plt.legend()
    plt.grid()
    
    return x, v

# Ejercicios
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
        \phi(x=0) = \phi(x=1) = \phi(y=0) = 0   y   \phi(y=1) = x(1-x) (Dirichlet)

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

    phi = np.zeros((N+2, N+2)) # se cumple \phi(x=0) = \phi(x=1) = \phi(y=0) = 0
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
    \ddot{x} = -\omega^2 x - \alpha \dot{x} \implies
    \begin{cases}
        \dot{x} = y \\
        \dot{y} = -\omega^2 x - \alpha y
    \end{cases}
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
    omega = 1.
    alpha = 0.2    
    dt = 0.1     # h
    
    x0 = 1.
    v0 = 0.4      # y0
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

    # Gráfica de los errores
    err_EuFw = error(x_forward, x_analitica)
    err_EuFw_M = error(x_forward_matrix, x_analitica)
    err_EuBw = error(x_backward, x_analitica)
    err_EuBw_M = error(x_backward_matrix, x_analitica)
    err_CrNi = error(x_CrankNich, x_analitica)
    err_CrNi_M = error(x_CrankNich_matrix, x_analitica)
    
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
        
    plt.legend()
    plt.show()
    
# Main
def main() -> None:
    #ejercicioI()
    #ejercicioII()
    ejercicioIII()

if __name__ == '__main__':
    main()