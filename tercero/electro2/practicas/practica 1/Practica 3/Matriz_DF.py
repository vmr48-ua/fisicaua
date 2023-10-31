"""____________________________________________________________

Módulo Matriz_DF: permite calcular la matriz que define las 
diferencias finitas de una guia de onda

@author: Cristian Neipp
Universidad de Alicante
  ______________________________________________________________
  

Exporta la función matriz que calcula la matriz
Los argumentos son: 
                    Lx=longitud del array en x
                    Ly=longitud del array en y
Devuelve A (la matriz)
"""
import numpy as np
def matriz(Lx,Ly):
    n=Lx*Ly
    A=np.zeros((n,n)) #inicializamos la matriz
    for i in range(0,n):
        
        """ Evaluamos las esquinas  """
    
        if i==0:
            A[i,i]=4
            A[i,i+1]=-2
            A[i,i+Lx]=-2
        if i == Lx-1:
            A[i,i]=4
            A[i,i-1]=-2
            A[i,i+Lx]=-2
        if i == Lx*(Ly-1):
            A[i,i]=4
            A[i,i-Lx]=-2
            A[i,i+1]=-2
        if i == Lx*Ly-1:
            A[i,i]=4
            A[i,i-1]=-2
            A[i,i-Lx]=-2
        """ Fin de evaluación de esquinas   """
  
        """ Evaluamos los bordes """
    
        if i>0 and i<Lx-1: #borde superior
            A[i,i]=4
            A[i,i-1]=-1
            A[i,i+1]=-1
            A[i,i+Lx]=-2
        if i>Lx*(Ly-1) and i<Lx*Ly-1: #borde inferior
            A[i,i]=4
            A[i,i-1]=-1
            A[i,i+1]=-1
            A[i,i-Lx]=-2
        a1=np.arange(Lx,Lx*(Ly-2)+Lx,Lx) #borde izquierdo
        if i in a1:
            A[i,i]=4
            A[i,i+1]=-2
            A[i,i+Lx]=-1
            A[i,i-Lx]=-1
        a2=np.arange(2*Lx-1,Lx*(Ly-1)+Lx-1,Lx) #borde derecho
        if i in a2:
            A[i,i]=4
            A[i,i-1]=-2
            A[i,i+Lx]=-1
            A[i,i-Lx]=-1
        
        """ Fin de evaluación de los bordes  """
   
        """ Todos los demás puntos    """
    
        a3=np.zeros(2*Lx+len(a1)+len(a2))
        a3[0:Lx]=np.arange(0,Lx)
        a3[Lx:2*Lx]=np.arange(Lx*(Ly-1),Lx*Ly)
        a3[2*Lx:2*Lx+len(a1)]=a1
        a3[2*Lx+len(a1):2*Lx+len(a1)+len(a2)]=a2

        if not (i in a3):
            A[i,i]=4
            A[i,i-1]=-1
            A[i,i+1]=-1
            A[i,i+Lx]=-1
            A[i,i-Lx]=-1
        """ Fin todos los demás puntos """
    return A 
 
    