import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle
from numpy import pi

L  = 4.0
N = 40
res = 80
R = 0.05
I = 1
mu_0 = 4*np.pi*10**-7

n=15
x=np.linspace(-L,L,n) 
y=np.linspace(-L,L,n) 
z=np.linspace(-L,L,n) 

def dB(I,dl,r0,rp):
    r=(rp[0]-r0[0],rp[1]-r0[1],rp[2]-r0[2])
    den=((r[0])**2+(r[1])**2+(r[2])**2)**1.5    #esta elevado a 3/2 por que arriba no tenemos
    factor=(mu_0/(4*np.pi))*I/den                #el vector unitario del radio, si no que el radio vector
    dBx=factor*(dl[1]*r[2]-dl[2]*r[1])
    dBy=factor*(dl[2]*r[0]-dl[0]*r[2])
    dBz=factor*(dl[0]*r[1]-dl[1]*r[0])
    return dBx,dBy,dBz

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

X,Z=np.meshgrid(x,z)
bx,by,bz = B(I,R,(X,0,Z))
modulo = (bx**2+by**2+bz**2)**0.5

fig = plt.figure()
color = 2*np.log(np.hypot(bx,bz))
plt.streamplot(X,Z,bx,bz,color=2*np.log(np.hypot(bx,bz)),linewidth=1,cmap=plt.cm.cool,density=1.5,arrowstyle='->',arrowsize=1.5)

fig = plt.figure()
ax = fig.add_subplot(projection='3d')
u,v,w=np.meshgrid(x,y,z)
bx,by,bz=B(I,R,(u,v,w))
ax.quiver(u,v,w,bx,by,bz,color='b',length=1,normalize=True)

plt.show()