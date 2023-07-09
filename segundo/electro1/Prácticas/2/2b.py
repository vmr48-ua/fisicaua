import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

L  = 4.0
L0 = -2.0
N = 21
h = L/(N-1) #resolucion
q = 3. 
k = 1/(4*np.pi*8.8541878176e-12)

Q = [(q,(-0.5,0)),(-q,(0.5,0)),(3*q,(0,1))]

def V(q,r0,x,y):
    den=np.hypot(x-r0[0],y-r0[1])
    return k*q/den

x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
X,Y = np.meshgrid(y,x)

M = np.zeros((N,N))

for carga in Q:
    M += V(carga[0],carga[1],X,Y)

colorinterpolation = 50
colourMap = plt.cm.RdGy
fig = plt.figure()
plt.contourf(x, y , np.transpose(M), colorinterpolation, cmap=colourMap)
plt.title("M")
plt.xlabel('$x$')
plt.ylabel('$y$')
plt.colorbar()


plt.show()