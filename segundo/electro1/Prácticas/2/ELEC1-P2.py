import numpy as np
import matplotlib
import matplotlib.pyplot as plt
from matplotlib.patches import Circle
from numpy import pi

L  = 4.0
N = 21
res = 80
R = 0.5
I = 1
mu_0 = 4*np.pi*10**-7

n=50
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

def representa_perfil(x,y,M,title='',ztitle='$rho$'):
    plt.figure(figsize=(10,10))
    plt.plot(x,np.transpose(M),color='k')
    plt.title(title + "$\ visto\ desde\ el\ plano\ z$")
    plt.xlabel('$z(m)$')
    plt.ylabel(ztitle)

X,Z=np.meshgrid(x,z)
bx,by,bz = B(I,R,(X,0,Z))
modulo = (bx**2+by**2+bz**2)**0.5

#representamos el módulo del campo magnético en función de la distancia en z
representa_perfil(x,y,np.transpose(modulo),title='$Módulo\ del\ campo\ magnético\ de\ una\ espira$',ztitle='$B(T)$')

#representamos el campo vectorial en 2d
fig = plt.figure(figsize=(10,10))
color = 2*np.log(np.hypot(bx,bz))
plt.streamplot(X,Z,bx,bz,color=color,linewidth=1,cmap=plt.cm.cool,density=1.5,arrowstyle='->',arrowsize=1.5)
plt.title("Campo magnético espira radio {} $m$".format(R))

#representamos el campo vectorial en 3d
n=10                    # Reducimos las n para poder verlo mejor
x=np.linspace(-L,L,n) 
y=np.linspace(-L,L,n) 
z=np.linspace(-L,L,n) 

fig = plt.figure(figsize=(10,10))
ax = fig.add_subplot(projection='3d')
u,v,w=np.meshgrid(x,y,z)
bx,by,bz=B(I,R,(u,v,w))
ax.quiver(u,v,w,bx,by,bz,color='b',length=1,normalize=True)


""" MOMENTO DIPOLAR """
def dipolo (I, rp, Radio, r0=(0,0,0)):
    r=(rp[0]-r0[0],rp[1]-r0[1],rp[2]-r0[2])
    den=((r[0])**2+(r[1])**2+(r[2])**2)**2.5 
    den2=((r[0])**2+(r[1])**2+(r[2])**2)**0.5
    
    factor = (mu_0/(4*np.pi*den))
    S = np.pi*Radio**2
    dip = (0,0, I*S)
    
    p = (dip[0]*r[0])+(dip[1]*r[1])+(dip[2]*r[2])
    
    Bx = factor*(-(3*p)*r[0] + (den2)**2*dip[0])
    By = factor*(-(3*p)*r[1] + (den2)**2*dip[1])
    Bz = factor*(-(3*p)*r[2] + (den2)**2*dip[2])
    
    return Bx,By,Bz

n=50
x=np.linspace(-L,L,n)
y=np.linspace(-L,L,n) 
z=np.linspace(-L,L,n)
X,Z=np.meshgrid(x,z)

Bx,By,Bz=dipolo(I,(X,0,Z), R)

#representamos el campo vectorial en 2d
fig = plt.figure(figsize=(10,10))
color = 2*np.log(np.hypot(Bx,Bz))
plt.streamplot(X,Z,Bx,Bz,color=color,linewidth=1,cmap=plt.cm.cool,density=1.5,arrowstyle='->',arrowsize=1.5)
plt.title("Campo magnético dipolo")

#representamos el módulo
representa_perfil(x,y,np.transpose(modulo),title='$Módulo\ del\ campo\ magnético\ de\ un\ dipolo$',ztitle='$B(T)$')



""" DISTRIBUCIÓN RETICULAR DE MOMENTOS MAGNÉTICOS """
#Parametrización de un círculo
N = 51
x=np.linspace(-L,L,N)
y=np.linspace(-L,L,N) 
z=np.linspace(-L,L,N)
X, Y, Z = np.meshgrid(x, y)[0], np.meshgrid(x, y)[1], np.meshgrid(x, z)[1]
B = np.zeros((3,N,N))
t = np.linspace(0,2*np.pi,3600)
for i in range(len(t)):
    r0 = (1/2*np.cos(t[i]),0,2*np.sin(t[i]))
    B[0] += dipolo(I, (X,Y,Z), 2*R, r0=r0)[0]
    B[1] += dipolo(I, (X,Y,Z), 2*R, r0=r0)[1]
    B[2] += dipolo(I, (X,Y,Z), 2*R, r0=r0)[2]
        
fig = plt.figure(figsize=(10,10))
color = 2*np.log(np.hypot(B[0],B[2]))
plt.streamplot(X,Z,B[0],B[1],color=color,linewidth=1,cmap=plt.cm.seismic,density=2.5,arrowstyle='->',arrowsize=1.5)

plt.title("Campo Magnético distribución reticular")

plt.show()
