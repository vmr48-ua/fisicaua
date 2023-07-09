'''
JOSE CALDERÓN LORCA Y JESÚS FERRÁNDEZ BERNABEU
'''
# =============================================================================
# =============================================================================
# PRÁCTICA 2 OBTENCIÓN CAMPO MAGNÉTICO
# =============================================================================
# =============================================================================

import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import axes3d

# =============================================================================
# Definimos variables
# =============================================================================

N=10   #N=21
M=np.zeros((N,N))
mu0 = 4*np.pi*10**-7
res=60  #cuanto mas grande mejor, es en el numero de intervalos en los que divides la circunferencia res=6 -> Hexagono
R = 0.5        #Cambiamos res entre 10,100 un numero por ahi
I=10     #Menos de 10 A
x=np.linspace(-4,4,10) #el 10 debería de ser unos 200, pero con ese valor el ordenador va muy lento
y=np.linspace(-4,4,10) #el 10 son 200
z=np.linspace(-4,4,10) #el 10 son 200

# =============================================================================
# Definimos el diferencial del campo
# =============================================================================
def dB(I,dl,r0,rp):
    r=(rp[0]-r0[0],rp[1]-r0[1],rp[2]-r0[2])
    den=((r[0])**2+(r[1])**2+(r[2])**2)**1.5   #esta elevado a 3/2 por que arriba no tenemos
    factor=(mu0/(4*np.pi))*I/den                #el vector unitario del radio, si no que el radio vector
    dBx=factor*(dl[1]*r[2]-dl[2]*r[1])
    dBy=factor*(dl[2]*r[0]-dl[0]*r[2])
    dBz=factor*(dl[0]*r[1]-dl[1]*r[0])
    return dBx,dBy,dBz

# =============================================================================
# Integramos el diferencial del campo para obtener el campo (Obtenemos el campo en un punto)
# =============================================================================
def B(I,R,r):
    Bx=By=Bz=0
    dalpha=2*np.pi/(res)
    for i in range(res):
        alpha=i*dalpha
        dl=(-np.sin(alpha),np.cos(alpha),0)
        dBc=dB(I,dl,(R*np.cos(alpha),R*np.sin(alpha),0),r)
        Bx+=dBc[0]
        By+=dBc[1]
        Bz+=dBc[2]
    factor=R*dalpha
    return factor*Bx,factor*By,factor*Bz
    


#Creamos una especie de matriz
X,Z=np.meshgrid(x,z)

#Asignamos los valores de las componentes del campo y calculamos el modulo
bx,by,bz=B(I,0.5,(X,0,Z))
modulo = (bx**2+by**2+bz**2)**0.5

# =============================================================================
# DEFINIMOS LA GRÁFICA EN 2D Y LA PINTAMOS
# =============================================================================

fig = plt.figure()
az=fig.add_subplot(111)
color = 2*np.log(np.hypot(bx,bz))
plt.streamplot(X,Z,bx,bz,color=2*np.log(np.hypot(bx,bz)),linewidth=1,cmap=plt.cm.inferno,density=2,arrowstyle='->',arrowsize=1.5)
plt.title("Campo Magnético Espira")
plt.show()

# =============================================================================
# DEFINIMOS LA GRÁFICA EN 3D Y LA PINTAMOS
# =============================================================================

fig = plt.figure()
ax=fig.add_subplot(projection='3d')
u,v,w=np.meshgrid(x,y,z)
bx,by,bz=B(I,R,(u,v,w))
ax.quiver(u,v,w,bx,by,bz,color='b',length=1,normalize=True)

# =============================================================================
# Campo creado por un dipolo
# =============================================================================

def dipolo2 (I, r0, rp, RadioEspira):
    
    r=(rp[0]-r0[0],rp[1]-r0[1],rp[2]-r0[2])
    den=((r[0])**2+(r[1])**2+(r[2])**2)**2.5 
    den2=((r[0])**2+(r[1])**2+(r[2])**2)**0.5
    
    factor = (mu0/(4*np.pi*den))
    Superficie = np.pi*RadioEspira**2
    dip = (0,0, I*Superficie)
    
    p = (dip[0]*r[0])+(dip[1]*r[1])+(dip[2]*r[2])
    
    Bx = factor*(-(3*p)*r[0] + (den2)**2*dip[0])
    By = factor*(-(3*p)*r[1] + (den2)**2*dip[1])
    Bz = factor*(-(3*p)*r[2] + (den2)**2*dip[2])
    
    return Bx,By,Bz


print(dipolo2(100, (0,0,0), (2,4,1), 0.5))
print(dB(100,(0,0,1),(0,0,0),(0,4,1)))
print(B(10,0.005,(1,2,0)))


x=np.linspace(-4,4,10) 
y=np.linspace(-4,4,10)
X,Z=np.meshgrid(x,z)

Bx2,By2,Bz2=dipolo2(100,(0,0,0),(X,0,Z), 0.5)


fig = plt.figure()
az2=fig.add_subplot(111)
color2 = 2*np.log(np.hypot(Bx2,Bz2))
plt.streamplot(X,Z,Bx2,Bz2,color=color,linewidth=1,cmap=plt.cm.inferno,density=2,arrowstyle='->',arrowsize=1.5)
plt.title("Campo Magnético dipolo")
plt.show()

X2, Y2, Z2 = np.meshgrid(x, y)[0], np.meshgrid(x, y)[1], np.meshgrid(x, z)[1]
B = np.zeros((3,N,N))
t = np.linspace(0,2*np.pi,200)
for i in range(len(t)):
    x9 = 2*np.cos(t[i])
    y9 = 2*np.sin(t[i])
    B[0] += dipolo2(100,(x9,0,y9),(X2,Y2,Z2), 0.5)[0]
    B[1] += dipolo2(100,(x9,0,y9),(X2,Y2,Z2), 0.5)[1]
    B[2] += dipolo2(100,(x9,0,y9),(X2,Y2,Z2), 0.5)[2]
        

fig = plt.figure()
az2=fig.add_subplot(111)
color2 = 2*np.log(np.hypot(B[0],B[2]))
plt.streamplot(X2,Y2,B[0],B[1],color=color,linewidth=1,cmap=plt.cm.inferno,density=2,arrowstyle='->',arrowsize=1.5)

plt.title("Campo Magnético distribución cuadrada")
plt.show()

X2, Y2, Z2 = np.meshgrid(x, y)[0], np.meshgrid(x, y)[1], np.meshgrid(x, z)[1]
B = np.zeros((3,N,N))



B[0] += dipolo2(100,(2,0,0),(X2,0,Z2), 0.5)[0]
B[1] += dipolo2(100,(2,0,0),(X2,0,Z2), 0.5)[1]
B[2] += dipolo2(100,(2,0,0),(X2,0,Z2), 0.5)[2]
        
B[0] += dipolo2(100,(-2,0,0),(X2,0,Z2), 0.5)[0]
B[1] += dipolo2(100,(-2,0,0),(X2,0,Z2), 0.5)[1]
B[2] += dipolo2(100,(-2,0,0),(X2,0,Z2), 0.5)[2]

fig = plt.figure()
az2=fig.add_subplot(111)
color2 = 2*np.log(np.hypot(B[0],B[2]))
plt.streamplot(X2,Z2,B[0],B[2],color=color,linewidth=1,cmap=plt.cm.inferno,density=2,arrowstyle='->',arrowsize=1.5)
plt.title("Campo Magnético 2 dipolos")
plt.show()



'''

######################## REHACER #####################################

# =============================================================================
# Campo creado por una distribución de dipolos
# =============================================================================


def disCuadrada (N, m, X2, Y2, Z2):
    
    B = np.zeros((3,N,N))    
    """
    Recorremos cada una de las matrices de B
    """
    for i in range (0, N):
        for j in range(0, N):
            
            
            #Para cada elemento de la matriz, calculamos 
            #el campo producido por cada dipolo y los sumamos
            
            for k in range (0, N):
                
                r = X2[i][j],Y2[i][j],Z2[i][j]
                den=((r[0])**2+(r[1])**2+(r[2])**2)**1.5 
                factor = (mu0/(4*np.pi*den))

                p = (m[0]*r[0])+(m[1]*r[1])+(m[2]*r[2])
                
                B[0][i][j] += factor*(m[0]-3*(p)*X2[j][k])
                B[1][i][j] += factor*(m[1]-3*(p)*Y2[j][k])
                B[2][i][j] += factor*(m[2]-3*(p)*Z2[j][k])
                
    return B[0], B[1], B[2]


x=np.linspace(-2,2,10) 
y=np.linspace(-2,2,10) 
z=np.linspace(-2,2,10)

X2, Y2, Z2 = np.meshgrid(x, y)[0], np.meshgrid(x, y)[1], np.meshgrid(x, z)[1]

Bx3,By3,Bz3 = disCuadrada (10, (1,0,1), X2, Y2, Z2)

fig = plt.figure()
az2=fig.add_subplot(111)
color2 = 2*np.log(np.hypot(Bx3,Bz3))
plt.streamplot(X2,Z2,Bx3,Bz3,color=color,linewidth=1,cmap=plt.cm.inferno,density=2,arrowstyle='->',arrowsize=1.5)
plt.title("Campo Magnético distribución cuadrada")



# =============================================================================
# Campo creado por una distribución de dipolos segunda parte
# =============================================================================


def disCuadrada2 (N,m, X3, Y3, Z3):
    
    B = [0,0,0]
    m0 = np.linspace(-5,5,N)
    distribucion = []

    """
    Recorremos cada una de las matrices de B
    """
        
    for i in range(0,len(m0)):
        for j in range(0,len(m0)):
            distribucion.append([m0[i],m0[j]])
        
    for k in range(0,len(distribucion)):
        
        distancia = (X3-(distribucion[k]+[0])[0]), (Y3-(distribucion[k]+[0])[1]), (Z3-(distribucion[k]+[0])[2])
        normadistancia = (distancia[0]**2+distancia[1]**2+distancia[2]**2)**0.5
        tr = ((1/normadistancia)*distancia[0],(1/normadistancia)*distancia[1], (1/normadistancia)*distancia[2])
        p = (tr[0]*m[0])+(tr[1]*m[1])+(tr[2]*m[2])
        den = ((X3,Y3,Z3)[0]**2+(X3,Y3,Z3)[1]**2+(X3,Y3,Z3)[2]**2)**0.5
    
        factor = (mu0/(4*np.pi*den))

    
        B[0] += factor*(m[0]-3*(p)*tr[0])
        B[1] += factor*(m[1]-3*(p)*tr[1])
        B[2] += factor*(m[2]-3*(p)*tr[2])
        
    return B[0], B[1], B[2]


x=np.linspace(-20,20,10) 
y=np.linspace(-20,20,10) 
z=np.linspace(-20,20,10)

X3, Y3, Z3 = np.meshgrid(x, y)[0], np.meshgrid(x, y)[1], np.meshgrid(x, z)[1]

Bx4,By4,Bz4 = disCuadrada2 (10, (1,0,0), X3, Y3, Z3)

fig = plt.figure()
az2=fig.add_subplot(111)
color2 = 2*np.log(np.hypot(Bx4,Bz4))
plt.streamplot(X3,Z3,Bx4,Bz4,color=color,linewidth=1,cmap=plt.cm.inferno,density=2,arrowstyle='->',arrowsize=1.5)
plt.title("Campo Magnético distribución cuadrada PLANO XZ")


'''












