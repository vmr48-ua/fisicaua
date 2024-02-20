ang = int(input('Introduce el Angulo (deg): '))
file = input('Introduce el nombre de la foto: ') + ".png"

from matplotlib.pyplot import plot,xlabel,ylabel,savefig,legend,title,subplots_adjust
from numpy             import zeros,sin,cos,radians

l,masa,g,n,dt = 1,1,9.8,10000,0.001
tiempo,w,theta,tension = zeros(n),zeros(n),zeros(n),zeros(n)

theta[0] = radians(ang)
tension[0] = masa*g*cos(theta[0])

for i in range(1,n):
    tiempo[i] = tiempo[i-1] + dt
    theta[i] = theta[i-1] + w[i-1]*dt
    w[i] = w[i-1] - ((g/l)*sin(theta[i]))*dt
    tension[i] = masa*(g*cos(theta[i-1]) + masa * l * w[i-1]**2)

plot(tiempo,tension,label="Tension")

subplots_adjust(left=0.12, bottom=0.11, right=0.93, top=0.9)
title((r'Evolucion de la tension en funcion del tiempo para $\theta_0 = {}\degree$').format(ang))
legend(loc="upper left")
xlabel('Tiempo (s)')
ylabel('Tension (N)')
savefig(file)