# -*- coding: utf-8 -*-
"""
Created on Thu Mar 21 12:03:18 2019

@author: cristian
"""

import math , cmath
import numpy as np
from scipy import linalg
from scipy.signal import find_peaks
from scipy.misc import electrocardiogram
import matplotlib.pyplot as plt



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
      

def Bzmn(x,y,m,n,a,b):
    return np.cos(m*np.pi*x/a)*np.cos(n*np.pi*y/b)


a=20.0
b=10.0
m=8
n=10
x = np.linspace(0, a, 256, endpoint=True)
y = np.linspace(0,b,256,endpoint=True)
Lx=len(x)
Ly=len(y)
xx,yy = np.meshgrid(x,y)
Bz=Bzmn(xx,yy,m,n,a,b)
plt.figure(2)
plt.contourf(xx, yy,Bz, 50)
M,N=modefinders(Bz,Lx,Ly)
print(M,N)

plt.figure(3)
x = electrocardiogram()[2000:4000]
peaks, _ = find_peaks(x, height=0)
plt.plot(x)
plt.plot(peaks, x[peaks], "x")
plt.plot(np.zeros_like(x), "--", color="gray")
plt.show()
x=np.array([1, 2, 3])
y=np.where(x==3)
print(y[0])