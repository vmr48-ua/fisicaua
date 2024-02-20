import matplotlib.pyplot as plt 
import numpy as np 

f=100*10**(-3)
n=1.5
h=f/2
p=0
tol = 10e-10

def aberracion(q):
    return (h**2)/(8*f**3) * 1/(n*(n-1)) * (((n+2)/(n-1))*q**2 + 4*(n+1)*p*q + (3*n+2)*(n-1)*p**2 + (n**3)/(n-1))
def dA_dq(q):
    return (h**2)/(8*f**3) * 1/(n*(n-1)) * (((n+2)/(n-1))*q*2 + 4*(n+1)*p)

x = np.linspace(-3,3,10000)
y = aberracion(x)
dy = dA_dq(x)

minY = np.min(y)
for i in range(len(x)):
    if aberracion(x[i]) - minY <= tol:
        minX = x[i]

minDY = np.min(np.abs(dy))
for i in range(len(x)):
    if dA_dq(x[i]) - minDY <= tol:
        minDX = x[i]

plt.figure()
plt.title('Aberración esférica en función al factor de forma (q)')
plt.xlabel('Factor de forma (q)')
plt.ylabel('Aberración (A)')

plt.plot(x,y,color='teal',label='Aberración')
plt.plot(x,dy,color='peru',label='$\dfrac{dA}{dq}$')

plt.axhline(0,color='k',linewidth=0.5) # Imprime el eje x
plt.axvline(minDX,linestyle='--',color='salmon')

plt.scatter(minX,minY,marker='x',label='q = '+str(round(minX,3)),color='r')

plt.legend()
plt.show()
