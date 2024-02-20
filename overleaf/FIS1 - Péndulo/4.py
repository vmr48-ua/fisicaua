file = input('Introduce el nombre de la foto: ') + ".png"

from matplotlib.pyplot import plot,legend,xlabel,ylabel,savefig,text,axvline,title,subplots_adjust
from numpy             import cos,sin,zeros,radians,degrees

l,masa,g,n,ang,c = 1,1,9.8,10000,0,1500 
da,dt =              radians(0.1),0.001 
T,Tp,angulo =        [],[],[]
t,w,th =             zeros(n),zeros(n),zeros(n)

def periodo(theta):
    th[0] = theta
    for i in range(1,n):
        t[i] = t[i-1] + dt
        th[i] = th[i-1] + w[i-1]*dt
        w[i] = w[i-1] - ((g/l)*sin(th[i]))*dt
        if i > 1 and th[i-2] < th[i-1] < th[i]:
            T = 2*t[i-1]
            break
    return T

def periodop(theta):
    th[0] = theta
    for i in range(1,n):
        t[i] = i * dt
        th[i] = theta * cos(((g/l)**0.5)*t[i])
        if i > 1 and th[i-2] < th[i-1] < th[i]:
            T = 2*t[i-1]
            break
    return T

def separacion(a,b,per):
    for i in range (len(a)):
        if a[i] >= b[i]:
            ma = a[i]
            mi = b[i]
        else:
            ma = b[i]
            mi = a[i]
        if round((100 * (ma - mi))/ma) == per:
            return i    

for i in range(c):
    angulo.append(degrees(ang))
    ang += da
    T.append(periodo(ang))
    Tp.append(periodop(ang))

axvline(x=round(angulo[separacion(T,Tp,5)],2),color='k', linestyle='-', ymin=0.05,ymax=0.1)
axvline(x=round(angulo[separacion(T,Tp,2)],2),color='k', linestyle='-', ymin=0.05,ymax=0.06)
plot(angulo,T,label="Periodo")
plot(angulo,Tp,label="Periodo Bajos Angulos")
text(-4.5,3.15,"  Error del 5% - {} degrees".format(round(angulo[separacion(T,Tp,5)],2)))
text(-4.5,3.25,"  Error del 2% - {} degrees".format(round(angulo[separacion(T,Tp,2)],2)))
subplots_adjust(left=0.12, bottom=0.11, right=0.93, top=0.9)
title('Periodo de oscilacion en funcion al angulo inicial')

legend(loc="upper left")
xlabel('Angulo (deg)')
ylabel('Periodo (s)')
savefig(file,dpi=350)