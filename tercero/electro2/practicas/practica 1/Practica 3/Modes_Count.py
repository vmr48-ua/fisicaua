"""____________________________________________________________

Módulo Modes count: permite encontrar el número de modos (M, N)
dado el módulo del campo eléctrico

@author: Cristian Neipp
Universidad de Alicante
  ______________________________________________________________
  

Exporta la función modefinders que calcula el número de módulos
Los argumentos son: a=campo eléctrico como array Lx x Ly
                    Lx=longitud del array en x
                    Ly=longitud del array en y
Devuelve M,N 
        M = número de lóbulos en la dirección x
        N = número de lóbulos en la dirección y
"""


import numpy as np
from scipy.signal import find_peaks

def modefinders(a,Lx,Ly):
    d=a[1,1]
    y=np.floor(a*10000)/10000
    c=y[1,1:Lx-2]
    b=y[1:Ly-2,1]
 
    if d<0.5:
        m1,_=find_peaks(c)
        m=len(m1)
        n1,_=find_peaks(b)
        n=len(n1)
    else:
        m1,_=find_peaks(1-c)
        m=len(m1)
        n1,_=find_peaks(1-b)
        n=len(n1)
    
    return m,n
      
