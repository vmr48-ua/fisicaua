ang = int(input('Introduce el Angulo (deg): '))
k = float(input('Introduce la K: '))
file = input('Introduce el nombre de la TH: ') + ".png"
file1 = input('Introduce el nombre de la W: ') + ".png"
seg = int(input('Introduce los segundos a graficar: '))

import matplotlib.pyplot as plt
from numpy import zeros,radians,cos,sin

l,g,n,dt = 1,9.8,1000*seg,0.001
t,w,th = zeros(n),zeros(n),zeros(n)
th[0] = radians(ang)

for i in range(1,n):
    t[i] = t[i-1] + dt
    th[i] = th[i-1] + w[i-1]*dt
    w[i] = w[i-1] - ((g/l)*sin(th[i]) + w[i-1]*k)*dt

plt.subplots_adjust(left=0.12, bottom=0.11, right=0.93, top=0.9)
plt.title((r'Evolucion de $\theta(t)$ con $\theta_0 = {}\degree$ y $K = {}\ SI$').format(ang,k))
plt.plot(t,th,label="Angulo")
plt.legend(loc="upper left")
plt.xlabel('Tiempo (s)')
plt.ylabel('Angulo (rad)')
plt.savefig(file,dpi=350)
plt.figure()

plt.subplots_adjust(left=0.12, bottom=0.11, right=0.93, top=0.9)
plt.title((r'Evolucion de $\omega(t)$ con $\theta_0 = {}\degree$ y $K = {}\ SI$').format(ang,k))
plt.plot(t,w,label="Velocidad Angular")
plt.legend(loc="upper left")
plt.xlabel('Tiempo (s)')
plt.ylabel('Velocidad (rad/s)')
plt.savefig(file1,dpi=350)