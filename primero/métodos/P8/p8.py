import numpy as np
import scipy.linalg as la

"""###########"""
"""Ejercicio 1"""
"""###########"""

A = np.array([[1,2,-3],[0,1,-2],[0,0,3]],dtype=float)
b = np.array([-2,0,-4])

def solucionU(A,b): #Método de sustitución regresiva
    n = len(b)
    sol = np.zeros(n)
    for i in range(n-1,-1,-1):
        suma = 0
        for j in range(i,n):
            suma += A[i,j]*sol[j]
        sol[i] = (b[i]-suma)/A[i,i]
    return sol

def solucionD(A,b):
    n = len(A)
    x = np.zeros(n)
    for i in range(n-1,-1,-1):
        x[i] = b[i]/A[i,i]
    return x

# def solucionU(A,b): #Método de sustitución regresiva
#     n = len(b)
#     sol = np.zeros(n)
#     for i in range(n-1,-1,-1):
#         sol[i] = (1/A[i,i])*(b[i]-np.sum(A[i]*x))
#     return sol

#print(solucionU(A,b))
#print(la.solve(A,b))

"""###########"""
"""Ejercicio 2"""
"""###########"""

A = np.array([[1,2,-1,3],[2,0,2,-1],[-1,1,1,-1],[3,3,-1,2]],dtype=float)
amax = np.amax(A)
b = np.array([-8,13,8,-1],dtype=float)

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
    for j in range(n-1): 
        piv = np.argmax(abs(Ac[j::,j]))+j
        cambio_filas(Ac,j,piv)
        cambio_filas(bc,j,piv)
        for i in range(j+1,n):
            suma_filas(bc,i,j,-Ac[i,j]/Ac[j,j])              
            suma_filas(Ac,i,j,-Ac[i,j]/Ac[j,j])
    return solucionU(Ac,bc)


sol = la.solve(A,b)
Gp = gauss_parcial(A,b)


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

gpe = gauss_parcial_escalado(A,b)

A = np.array([[0,1,2],[1,1,-1],[2,1,0]],dtype=float)


"""###########"""
"""Ejercicio 3"""
"""###########"""

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

print(inversa(A),'inversa')
print(la.inv(A))

"""###########"""
"""Ejercicio 4"""
"""###########"""

A = np.array([[7,3,-1],[3,8,1],[2,-4,-1]],dtype=float)
B = np.array([[1,-2,-2,-3],[3,-9,0,-9],[-1,2,4,7],[-3,-6,26,2]],dtype=float)

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

L,U = LU(B)[0],LU(B)[1]
p, l, u = la.lu(B)

print(np.round(np.dot(L,U)))
print(B)