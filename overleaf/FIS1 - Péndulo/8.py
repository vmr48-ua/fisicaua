file = input('Introduce el nombre de la foto: ') + ".png"
k = list(map(float,input('Introduce las K: ').split()))

from matplotlib.pyplot import plot,legend,xlabel,ylabel,savefig,title,subplots_adjust
from numpy             import cos,sin,zeros,radians,degrees

l,g,ang,c,n = 1,9.8,0,1500,1000*15  
da,dt =       radians(0.1),0.001 
T0,angulo =   [],[]
t,w,th =      zeros(n),zeros(n),zeros(n)

def periodo(theta,j):
    T = 0
    th[0] = theta
    for i in range(1,n):
        t[i] = t[i-1] + dt
        th[i] = th[i-1] + w[i-1]*dt
        w[i] = w[i-1] - ((g/l)*sin(th[i]) + w[i-1]*k[j])*dt
        if i > 1 and th[i-2] < th[i-1] < th[i]:
            T = 2*t[i-1]
            break
    return T

for j in range(len(k)):
    for i in range(c):
        angulo.append(degrees(ang))
        ang += da
        T0.append(periodo(ang,j))
    plot(angulo,T0,label="Periodo K = {}".format(k[j]))
    T0.clear()
    angulo.clear()
    ang = 0

subplots_adjust(left=0.12, bottom=0.11, right=0.93, top=0.9)
title(r'Evolucion de T en funcion de $\theta_0$ para diferentes K')
legend(loc="upper left")
xlabel('Angulo (deg)')
ylabel('T (s)')
savefig(file,dpi=350)