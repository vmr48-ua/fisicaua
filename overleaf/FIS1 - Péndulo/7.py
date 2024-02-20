ang = int(input('Introduce el Angulo (deg): '))
k = float(input('Introduce la K: '))
file = input('Introduce el nombre de la foto: ') + ".png"
seg = int(input('Introduce los segundos a graficar: '))

from matplotlib.pyplot import plot,xlabel,ylabel,savefig,legend,title,subplots_adjust
from numpy             import zeros,radians,cos,sin

l,masa,g,dt,n = 1,1,9.8,0.001,1000*seg
t,w,th,ep,ec,em = zeros(n),zeros(n),zeros(n),zeros(n),zeros(n),zeros(n)

th[0] = radians(ang)
ep[0] = masa*g*(l-l*cos(th[0]))
em[0] = ep[0]

for i in range(1,n):
    t[i] = t[i-1] + dt
    th[i] = th[i-1] + w[i-1]*dt
    w[i] = w[i-1] - ((g/l)*sin(th[i]) + w[i-1]*k)*dt
    ep[i] = masa*g*(l-l*cos(th[i]))
    ec[i] = 1/2*masa*(w[i]**2)*(l**2)
    em[i] = ep[i]+ec[i]

plot(t,ep,label="Energia Potencial")
plot(t,ec,label="Energia Cinetica")
plot(t,em,label="Energia Mecanica")

subplots_adjust(left=0.12, bottom=0.11, right=0.93, top=0.9)
title((r'Energia del pendulo para $\theta_0 = {}\degree$ y $K = {}\ SI$').format(ang,k))
legend(loc="upper right")
xlabel('Tiempo (s)')
ylabel('Energia (J)')
savefig(file,dpi=350)