import numpy as np
from numpy import sin,pi,cos
import matplotlib.pyplot as plt

"""
EJERCICIO 1
"""

def dy_tres(f,x0,h):
    return (f(x0+h)-f(x0-h))/(2.*h)

def f(x):
    return sin(2*pi/(100)*x)
def dy(x):
    return dy_tres(f,x,10e-5)
# def derivadaAnalitica(x):
#     return (pi*cos((pi*x)/50))/50

x=np.linspace(0,100,100)
plt.figure()
plt.plot(x,f(x))
plt.plot(x,dy(x))
plt.grid()

# plt.figure()
# plt.plot(x,f(x))
# plt.plot(x,derivadaAnalitica(x))

plt.show()