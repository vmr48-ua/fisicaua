ang = int(input('Introduce el Angulo (deg): '))
file = input('Introduce el nombre de la foto: ') + ".png"

from matplotlib.pyplot import plot,xlabel,ylabel,savefig,legend,subplots_adjust,title
from numpy             import zeros,sin,cos,radians

l,masa,g,n,dt = 1,1,9.8,10000,0.001
tiempo,w,theta,wb = zeros(n),zeros(n),zeros(n),zeros(n)
theta[0] = radians(ang)

for i in range(1,n):
    tiempo[i] = i * dt
    theta[i] = radians(ang) * cos(((g/l)**0.5)*tiempo[i])
    wb[i] = radians(ang) * ((g/l)**0.5) * -1*sin(((g/l)**0.5)*tiempo[i])

for i in range(1,n):
    tiempo[i] = tiempo[i-1] + dt
    theta[i] = theta[i-1] + w[i-1]*dt
    w[i] = w[i-1] - ((g/l)*sin(theta[i]))*dt

plot(tiempo,w,label="Velocidad Angular")
plot(tiempo,wb,'r--',label="Bajos Angulos")

subplots_adjust(left=0.12, bottom=0.11, right=0.93, top=0.9)
title((r'Evolucion de la velocidad angular en funcion del tiempo para $\theta_0 = {}\degree$').format(ang))
legend(loc="upper left")
xlabel('Tiempo (s)')
ylabel('Velocidad Angular (rad/s)')
savefig(file)