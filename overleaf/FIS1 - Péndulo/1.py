ang = int(input('Introduce el Angulo (deg): '))
file = input('Introduce el nombre de la foto: ') + ".png"

from matplotlib.pyplot import plot,xlabel,ylabel,savefig,legend,subplots_adjust,title
from numpy             import zeros,sin,cos,radians,degrees

l,g,n,dt = 1,9.8,100000,0.0001
tiempo,w,theta,angulo = zeros(n),zeros(n),zeros(n),zeros(n)
theta[0], angulo[0] = radians(ang), radians(ang)

for i in range(1,n):
    tiempo[i] = tiempo[i-1] + dt
    w[i] = w[i-1] - (g/l)*sin(angulo[i-1])*dt
    angulo[i] = angulo[i-1] + w[i-1]*dt
for i in range(len(angulo)):
    angulo[i] = degrees(angulo[i])
plot(tiempo,angulo,label="Angulo Numerico")

for i in range(1,n):
    tiempo[i] = i * dt
    theta[i] = radians(ang) * cos(((g/l)**0.5)*tiempo[i])
for i in range(len(theta)):
    theta[i] = degrees(theta[i])
plot(tiempo,theta,'r--',label="Bajos Angulos")

subplots_adjust(left=0.1, bottom=0.11, right=0.95, top=0.9)
title((r'Evolucion del angulo en funcion del tiempo para $\theta_0 = {}\degree$').format(ang))
legend(loc="upper left")
xlabel('Tiempo (segundos)')
ylabel('Angulo (grados)')
savefig(file,dpi=350)