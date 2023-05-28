import numpy as np
import matplotlib.pyplot as plt



###############################################################################
#CAMPO MAGNÉTICO EN UNA ESPIRA
###############################################################################

def B(punto,curva,I): #Campo magnético por la ley de Biot-Savat
    K = 1e-7 #Constante eléctrica
    N = len(curva)
    dl = []

    # dividimos la longitud del camino 
    for i in range(N-1):
        dli = [curva[i+1][0]-curva[i][0],curva[i+1][1]-curva[i][1],curva[i+1][2]-curva[i][2]]
        dl.append(dli)
        
    DL = np.array(dl)
    #Calculamos la matriz de r/radio respecto a la curva
    R1 = []
    for i in range(N-1):
        Ri = punto - curva[i]
        R1.append(Ri)
        
    R = np.array(R1)
    
    Sum = np.array([0,0,0])
    for i in range(N-1):
        Sum = Sum + np.cross(DL[i],R[i])/(np.linalg.norm(R[i]))**3
     
    Sum = np.array(Sum)
    
    return Sum*K*I


def espira(R,N,z=0):
    
    angulo = np.linspace(0,2*np.pi,N)
    coord = []
    for i in angulo:    
        x = R*np.cos(i)
        y = R*np.sin(i)
        coord.append([x,y,z])
        
    return np.array(coord)



#Datos
    
R1, n = 0.5, 100
R2, n = 0.005, 100
curva1 = espira(R1,n)
curva2 = espira(R1,n)

P = 40
x = np.linspace(-4,4,P)
y = np.linspace(-4,4,P)
z = np.linspace(-4,4,P)

'''

#bx, by, bz

linea1 = []
grid1 = []
linea2 = []
grid2 = []
for i in z:
    for j in y:            
        linea1.append(B((0,j,i),curva1,1000))
        linea2.append(B((0,j,i),curva2,1000))
    grid1.append(linea1)
    grid2.append(linea2)
    linea1 = []
    linea2 = []

BZ = np.zeros((P,P))
BY = np.zeros((P,P))
BX = np.zeros((P,P))

# Radio 0.5

for i in range(P):
    for j in range(P):
        BX[i,j] = grid1[i][j][0]
        BY[i,j] = grid1[i][j][1]
        BZ[i,j] = grid1[i][j][2]
        #modB.append(np.linalg.norm([BX[i][j], BY[i][j], BZ[i][j]]))
modulo = (BX**2+BY**2+BZ**2)**0.5

#GRAFICAR
color = 2*np.log(np.hypot(BX,BZ))
plt.streamplot(y,z,BY,BZ, color = color, linewidth = 1,cmap = plt.cm.inferno,density = 2, arrowstyle = '->', arrowsize = 1.5)
plt.title('Campo magnético espira circular R = 0,5')
plt.xlim(-4,4)
plt.ylim(-4,4)
plt.show()

colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

a1=fig.add_subplot(223)
plt.plot(y,np.transpose(modulo))
a1.set_xlabel('$z$')

a2=fig.add_subplot(224)
plt.plot(x,modulo)
a2.set_xlabel('$x$')
plt.show()

B = np.array([BX,BY,BZ])
modB = np.linalg.norm(B)

'''
#------------------------------------------------------------------------------
#CAMPO MAGNÉTICO DEBIDO A UN MOMENTO DIPOLAR MAGNÉTICO
#------------------------------------------------------------------------------
def Bdip(m,p):
    
    K = 1e-7
    x,y,z = p[0],p[1],p[2]
    r = (x**2+y**2+z**2)**(1/2)
    ru = p/r
    
    B = (K/r**3)*(3*np.dot(m,ru)*ru-m)
    
    return B

#Datos
m = np.array([0,0,1000*np.pi*0.5**2])
#m = np.array([0,0,200])
P = 300
y = np.linspace(-4,4,P)
z = np.linspace(4,-4,P)
#bx,by,bz 
grid3 = []
for j in z:
    line3 = []
    for i in y:
        B = Bdip(m,np.array([i,j,0]))
        line3.append(B)
    grid3.append(line3)
    
BZ = np.zeros((P,P))
BY = np.zeros((P,P))
BX = np.zeros((P,P))

for i in range(P):
    for j in range(P):
        BX[i,j] = grid3[i][j][0]
        BY[i,j] = grid3[i][j][1]
        BZ[i,j] = grid3[i][j][2]
modulo = (BX**2+BY**2+BZ**2)**0.5

#GRAFICAR
color = 2*np.log(np.hypot(BX,BZ))
plt.streamplot(y,z,BY,BZ, color = color, linewidth = 1,cmap = plt.cm.inferno,density = 2, arrowstyle = '->', arrowsize = 1.5)
plt.title('Campo magnético momento dipolar')
plt.xlim(-4,4)
plt.ylim(-4,4)
plt.show()

colorinterpolation = 50
colourMap = plt.cm.coolwarm
fig = plt.figure()

a1=fig.add_subplot(223)
plt.plot(y,np.transpose(modulo))
a1.set_xlabel('$z$')

a2=fig.add_subplot(224)
plt.plot(x,modulo)
a2.set_xlabel('$x$')
plt.show()
'''
#------------------------------------------------------------------------------
#CAMPO MAGNÉTICO DEBIDO A UNA DISTRIBUCIÓN DE MOMENTOS DIPOLARES MAGNÉTICOS
#------------------------------------------------------------------------------
def Bdip(m,p,d):
    K = 1e-7
    x,y,z = p[0],p[1],p[2]
    r = ((x)**2+(y)**2+(z+d)**2)**(1/2) #el factor 20 es para ampliar la resolución del centro de la gráfica
    ru = p/r
    B = (K/r**3)*(3*np.dot(m,ru)*ru-m)
    return B

#Datos
m = np.array([0,0,1000*np.pi*0.005**2])
P = 30
y = np.linspace(-4,4,P)
z = np.linspace(4,-4,P)

#bx,by,bz
dipol = np.linspace(-2,2,30)
sumBY = np.zeros((P,P))
sumBZ = np.zeros((P,P))

for k in dipol:
    grid3 = []
    for j in z:
        line3 = []
        for i in y:
            B = Bdip(m,np.array([0,i,j]),k)
            line3.append(B)
        grid3.append(line3)
'''
grid3 = np.zeros((P,P))
for i in range(0,P):
    for j in range(0,P):
        if 10<=i<=20 and 10<=j<=20:
            B = Bdip(m,np.array([0,i,j]),j)
'''            
BZ = np.zeros((P,P))
BY = np.zeros((P,P))
BX = np.zeros((P,P))
    
for i in range(P):
    for j in range(P):
        BX[i,j] = grid3[i][j][0]
        BY[i,j] = grid3[i][j][1]
        BZ[i,j] = grid3[i][j][2]
            
        sumBY[i,j] += BY[i,j]
        sumBZ[i,j] += BZ[i,j]

#GRAFICAR
plt.streamplot(y,z,sumBY,sumBZ, linewidth=1.35, density=1.45, arrowstyle='->', arrowsize=1.)
plt.title('Campo magnético distribución momentos dipolares')
plt.xlim(-4,4)
plt.ylim(-4,4)
plt.show()
'''