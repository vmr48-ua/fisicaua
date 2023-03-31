import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

L  = 4.0
L0 = -2.0
N = 21
h = L/(N-1) #resolucion
q = 3. 

Q = [(q,(-0.5,0)),(-q,(0.5,0)),(3*q,(0,1))]

def Indice_a_posicion(r):
    i = int((r[0]-L0)/h)
    j = int((r[1]-L0)/h)
    return i,j

x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
X,Y = np.meshgrid(y,x)

M=np.zeros((N,N))
for carga in Q:
    M[Indice_a_posicion(carga[1])]=carga[0]

colorinterpolation = 50
colourMap = plt.cm.RdGy
fig = plt.figure()
# Configure the contour
plt.contourf(x, y , np.transpose(M), colorinterpolation, cmap=colourMap)
plt.title("M")

    
plt.xlabel('$x$')
plt.ylabel('$y$')
plt.colorbar()


plt.show()