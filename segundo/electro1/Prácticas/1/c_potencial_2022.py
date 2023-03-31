# Representación del potencial
import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

#Función de Posición en la matriz
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

#print (rho)

def V(q,r0,x,y):
    den=np.hypot(x-r0[0],y-r0[1])
    return q/den

M = np.zeros((N,N))

for carga in cargas:
    v0=V(carga[0],carga[1],X,Y)
    M += v0



# Set colour interpolation and colour map
colorinterpolation = 50
colourMap = plt.cm.coolwarm

fig = plt.figure()

#primer subplot

ax = fig.add_subplot(221)
# Configure the contour
plt.contourf(x, y , np.transpose(Vv), colorinterpolation, cmap=colourMap)
plt.title("Potencial")

# Add filled circles for the charges themselves
#charge_colors = {True: '#aa0000', False: '#0000aa'}
#for q, pos in cargas:
#    ax.add_artist(Circle(pos, 0.1, color=charge_colors[q>0]))
    
ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_aspect('equal')

plt.colorbar()


a3=fig.add_subplot(222)
plt.plot(x,np.transpose(Vv))
a3.set_xlabel('$x$')
a3.set_ylabel('$rho$')

a4=fig.add_subplot(224)
plt.plot(y,Vv)
a4.set_xlabel('$y$')
a4.set_ylabel('$rho$')


# Mostramos el resultado
plt.show()
