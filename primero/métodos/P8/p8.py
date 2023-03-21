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

# def solucionU(A,b): #Método de sustitución regresiva
#     n = len(b)
#     sol = np.zeros(n)
#     for i in range(n-1,-1,-1):
#         sol[i] = (1/A[i,i])*(b[i]-np.sum(A[i]*x))
#     return sol

print(solucionU(A,b))
print(la.solve(A,b))

"""###########"""
"""Ejercicio 2"""
"""###########"""

A = np.array([[1,2,-1,3],[2,0,3,-1],[-1,0,2,-1],[3,3,-1,2]],dtype=float)
b = np.array([-8,13,8,-1])

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
    Ac=A.copy()
    bc=b.copy()
    n = len(b)
    for i in range(n-1): 
        piv = np.argmax(abs(Ac[i::,i]))+i
        cambio_filas(Ac,i,piv)
        cambio_filas(bc,i,piv)
        for j in range(i+1,n):
            suma_filas(Ac,j,i,-Ac[j,i]/Ac[i,i])
            suma_filas(bc,j,i,-Ac[j,i]/Ac[i,i])                
            print(Ac)
    return solucionU(Ac)
gauss_parcial(A, b)

def gauss_parcial_escalado(A,b):
    n = len(b)
    sol = np.zeros(n)  
    return sol

"""###########"""
"""Ejercicio 3"""
"""###########"""

"""###########"""
"""Ejercicio 4"""
"""###########"""