import numpy as np
from numpy import sin,pi,cos,log
import matplotlib.pyplot as plt

"""
EJERCICIO 2
"""
modos = np.array([(1,0),(0,1),(1,1),(1,2),(2,1)])
a,b=15,10
B0=1


x2=np.linspace(0,a,100)
y2=np.linspace(0,b,100)
plots=[231,232,233,234,235]
colorinterpolation = 50

#Representación de B(x,y)
# plt.figure(figsize=(20,10))
# i=0

# for modo in modos:
#     m,n=modo
#     plt.subplot(plots[i])
#     plt.tight_layout(pad=2.0)
#     X,Y = np.meshgrid(x2,y2)
#     M=np.zeros((a,b))
#     M=B0*cos((m*pi*X)/a)*cos((n*pi*Y)/b)

#     plt.contourf(X, Y, M, colorinterpolation)
#     plt.title('Modo {}'.format(modo))
#     plt.xlabel('$x$')
#     plt.ylabel('$y$')
#     plt.colorbar()
#     i+=1
# plt.show()


#Representación de E(x,y)
def B(x,y):
    return B0*cos((m*pi*x)/a)*cos((n*pi*y)/(b))

h=10e-5
def Exf(x,y):
    return (B(x,y+h)-B(x,y))/h
def Eyf(x,y):
    return (B(x,y)-B(x+h,y))/h


plt.figure(figsize=(20,10))
k=0
for modo in modos:
    m,n=modo
    plt.subplot(plots[k])
    X,Y = np.meshgrid(x2,y2)
    E = np.zeros((len(x2),len(y2)))
    for i in range(len(x2)):
        for j in range(len(y2)):
            E[i,j]=abs(np.hypot(Exf(x2[i],y2[j]),Eyf(x2[i],y2[j])))

    plt.contourf(X, Y, np.transpose(E), colorinterpolation)
    plt.title('Modo {}'.format(modo))
    plt.xlabel('$x$')
    plt.ylabel('$y$')
    k+=1
plt.show()