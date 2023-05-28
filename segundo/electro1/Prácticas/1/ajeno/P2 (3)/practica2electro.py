import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import axes3d


def dB(I,dl,r):
    a = (r[0]**2+r[1]**2+r[2]**2)**(1.5)
    b = 10**(-7)*I/a
    return b*(dl[1]*r[2]-r[1]*dl[2]), b*(r[0]*dl[2]-dl[0]*r[2]), b*(dl[0]*r[1]-dl[1]*r[0])

def B(I,R,r):
    Bx=By=Bz=0
    dc=2*np.pi/1000
    for i in range(0,1000):
        c=i*dc
        dl=(-np.sin(c), np.cos(c),0)
        dBc=dB(I,dl,(r[0]-R*np.cos(c),r[1]-R*np.sin(c),r[2]))
        Bx += dBc[0]
        By += dBc[1]
        Bz += dBc[2]
    
    return Bx, By, Bz
        
    

x1 = np.linspace(-4,4,200)
y1= np.linspace(-4,4,200)
z1 = np.linspace(-4,4,200)

X,Z = np.meshgrid(x1,z1)
x,y,z = np.meshgrid(x1,y1,z1)

bx,by,bz = B(100,0.5,(X,0,Z))

modulo= (bx**2+by**2+bz**2)**(1./2)


fig=plt.figure()

az= fig.add_subplot(211)
color = 2*np.log(np.hypot(bx,bz))
plt.streamplot(X,Z,bx,bz, color=color, linewidth=1, cmap=plt.cm.inferno, density=2, arrowstyle='->', arrowsize=1.5)
plt.title('Campo Magnético')
plt.show()

y2= np.linspace(0,4,200)
plt.plot(x1,bx)
plt.xlabel('x')
plt.ylabel('B')



plt.show()

x2 = np.linspace(-4,4,10)
y2 = np.linspace(-4,4,10)
z2 = np.linspace(-4,4,10)
X,Z = np.meshgrid(x2,z2)
x2,y2,z2 = np.meshgrid(x2,y2,z2)
bx,by,bz = B(1000,0.1,(x2,y2,z2))

fig = plt.figure()
ax = fig.gca(projection='3d')
ax.quiver(x2,y2,z2,bx,by,bz,color='b',length=1,normalize=True)
plt.show()


