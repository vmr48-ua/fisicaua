"""___________________________________________________________

Módulo guia_de_onda_DF: simula el comportamiento de una guía
de onda rectangular de dimnsiones axb.

@author: Cristian Neipp
Universidad de Alicante
  ______________________________________________________________

Calcula y dibuja los modos transversales eléctricos (TE) de la guía
  """

import numpy as np
from scipy import linalg
import dibujaEn as db
import Matriz_DF as mat
import Modes_Count as MS
import matplotlib.pyplot as plt

aa=15.0 #dimensión horizontal
bb=10.0 #dimensión vertical
h=2*aa #número de pasos
dx=aa/h #tamaño de paso horizontal
dy=aa/h #tamaño de paso vertical

x=np.arange(0,aa-1,dx)
y=np.arange(0,bb-1,dy)
xx,yy=np.meshgrid(x,y)
Lx=len(x)
Ly=len(y)

"""
Generamos a continuación la matriz A
Sus dimensiones son n x n siendo n=Lx*Ly
"""

A=mat.matriz(Lx,Ly)

""" Resolvemos la ecuación de autovalores """
"""" -------------------------------------"""

lamb,V=linalg.eig(A)

"""---------------------------------------"""

""" Inicializamos los campos """
hz=np.zeros((Ly,Lx))
Hz=np.zeros((Ly,Lx,Lx*Ly))
Ex=np.zeros((Ly,Lx,Lx*Ly))
Ey=np.zeros((Ly,Lx,Lx*Ly))
E=np.zeros((Ly,Lx,Lx*Ly))
En=np.zeros((Ly,Lx,Lx*Ly))
Exx=np.zeros((Ly,Lx))
Eyy=np.zeros((Ly,Lx))
kc=np.zeros(Lx*Ly)
""" Fin de inicialización de los campos """

for m in range(0,Lx*Ly):
    kc[m]=np.sqrt(lamb[m])/dx; #calculamos el número de onda
    Fi=V[:,m] #Asignamos el campo para cada modo de propagación


    """ Convertimos la matriz columna de los autovectores
        en una matriz de dos dimensiones"""
    p=0
    for n in range(0,Lx*(Ly-1)+1,Lx):
        hz[p,:]=Fi[n:n+Lx]
        p=p+1
    Hz[:,:,m]=hz #Guardamos para cada modo
    """  Fin de la conversión """


""" Aplicamos diferencias finitas (hacia adelante) para el cálculo de E """
for m in range(0,Lx*Ly):
    H=Hz[:,:,m]
    for ii in range(1,Ly-1):
        for mm in range(1,Lx-1):
            Exx[ii,mm]=-(H[ii+1,mm]-H[ii,mm])
            Eyy[ii,mm]=(H[ii,mm+1]-H[ii,mm])
    Ex[:,:,m]=Exx
    Ey[:,:,m]=Eyy
    E[:,:,m]=np.sqrt(Exx**2+Eyy**2)
    MaxE=np.amax(E[:,:,m])
    En[:,:,m]=E[:,:,m]/MaxE
""" Fin cálculo de E """
""" Dibujamos En """

modos = np.array([(1,0),(0,1),(1,1),(1,2),(2,1)])
k=0
plt.figure(figsize=(20,10))
plots=[231,232,233,234,235]
for modo in modos:
    m,n=modo
    modes=np.zeros((Lx*Ly,3),dtype=int)
    kc_th=np.zeros(Lx*Ly)
    for q in range(0,Lx*Ly):
        M,N=MS.modefinders(En[:,:,q],Lx,Ly)
        modes[q,0]=M
        modes[q,1]=N
        modes[q,2]=q
        kc_th[q]=np.pi*(np.sqrt((M/aa)**2+(N/bb)**2))

    nmodes=0
    for s in range(0,Lx*Ly):
        if modes[s,0]==m and modes[s,1]==n:
            nmodes=nmodes+1
            pmode=np.zeros(nmodes,dtype=int)
            kmode=np.zeros(nmodes)
    i=0
    for s in range(0,Lx*Ly):
        if modes[s,0]==m and modes[s,1]==n:
            pmode[i]=modes[s,2]
            kmode[i]=kc[modes[s,2]]
            i=i+1

    Kmin=np.min(kmode)
    lmode=np.where(kmode==Kmin)
    P0=pmode[lmode[0]]

    plt.subplot(plots[k])
    plt.title('Modo {}'.format(modo))
    plt.xlabel('$x$')
    plt.ylabel('$y$')
    plt.xlim(0,aa)
    plt.ylim(0,bb)
    plt.contourf(xx, yy, En[:,:,P0[0]], 50)
    M,N=MS.modefinders(En[:,:,P0[0]],Lx,Ly)
    k+=1
plt.show()