import numpy as np 
import matplotlib.pyplot as plt 

########################################################################

# def equation(x):
#     return np.cos(x)**2

# t=np.linspace(0,10,1000)
# plt.figure()
# plt.plot(t,equation(t),color='orange',label='$\cos^2(\omega t)$')
# plt.axhline(0.5,linestyle='--',label='x$=\\dfrac{1}{2}$')
# plt.legend(loc='best')
# plt.show()

########################################################################

# def cos(x):
#     return np.cos(x)**2
# def sin(x):
#     return np.sin(x)**2

# t=np.linspace(0,10,1000)
# plt.figure()
# plt.plot(t,sin(t),color='blue',label='Potential Energy')
# plt.plot(t,cos(t),color='orange',label='Kinetic Energy')
# plt.plot(t,sin(t)+cos(t),label='K+U')
# plt.axhline(0.5,linestyle='--',label='x$=\\dfrac{1}{2}$')
# plt.legend(loc='best')
# plt.show()

########################################################################

def f(x):
    return 0.8*x

t=np.linspace(0,10,1000)
plt.figure()
plt.plot(t,f(t))
plt.axvline(8,linestyle='--')
plt.legend(loc='best')
plt.show()