import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

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

def Pos(r):
	i = int((r[0]-L0)/h)
	j = int((r[1]-L0)/h)
	return i,j

# Tamaño y resolucion
L  = 4.0
L0 = -2.0
N = 21
h = L/(N-1) #resolucion
q = 5. 

# Definimos dosnde están las cargas

cargas=[]
cargas.append((q,(-0.5,0)))
cargas.append((-q,(0.5,0)))

# Hacemos la Matriz de densidad de Carga

# Define arrays used for plotting
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Aunque ahora no lo necesitamos. Podemos definir una red para un campo vectorial.
# Esto nos dará un poco más de juego y nos permitirá representar campos vectoriales.
Y,X = np.meshgrid(y,x)

rho = np.zeros((N,N))

for carga in cargas:
	rho[Pos(carga[1])]=carga[0]


def V(q,r0,x,y):
    den=np.hypot(x-r0[0],y-r0[1])
    return q/den

M = np.zeros((N,N))

for carga in cargas:
    v0=V(carga[0],carga[1],X,Y)
    M += v0

def V2(q,x,y,r0):
    return 1/4*(V(q,[r0[0]+h,r0[1]],x,y)+V(q,[r0[0]-h,r0[1]],x,y)+V(q,[r0[0],r0[1]+h],x,y)+V(q,[r0[0],r0[1]-h],x,y)+(q/(h**2))**(h**2))

