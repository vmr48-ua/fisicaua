"""____________________________________________________________

Módulo dibuja En: permite dibujar el módulo del campo eléctrico

@author: Cristian Neipp
Universidad de Alicante
  ______________________________________________________________
  

"""

import numpy as np
import Modes_Count as MS
import matplotlib.pyplot as plt
def dibuja(aa,bb,xx,yy,kc,Lx,Ly,En,m,n):
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

    plt.figure(1)
    plt.contourf(xx, yy, En[:,:,P0[0]], 50)      
    M,N=MS.modefinders(En[:,:,P0[0]],Lx,Ly)
    print(M,N)
    plt.show()
    return 0