import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import odeint
import matplotlib.animation as animation
from numpy import sin, cos


fig=plt.figure()
fig.set_dpi(100)

# Datos a modificar en la simulacion 
# Pendulo doble

L1=0.1      #longitud del péndulo 1 en m
L2=0.1      #longitud del pendulo 2 en m
m1=1.0      #masa del pendulo 1 en kg
m2=1.0      #masa del pendulo 2 en kg
g=9.8      #aceleracion de la gravedad en m/s^2
tf=100.0     #tiempo de simulacion en s
m12=m1+m2

# Valores iniciales del péndulo doble
z0=[0.0, 0.0, 120.*np.pi/180., 120.*np.pi/180] #Valores iniciales [theta._1.0,theta._2.0,theta_1.0, theta_2.0]
Z0=[0.0, 0.0, 120.00001*np.pi/180., 120.00001*np.pi/180] #Valores iniciales [theta._1.0,theta._2.0,theta_1.0, theta_2.0]

par=[L1,L2,m1,m2,m12,g]



# Definicion de las ecuaciones de movimiento del pendulo doble
def double_pendulum(z,t,par):
    z1,z2,z3,z4=z  

    sinz=sin(z1-z2)
    cosz=cos(z1-z2)
    sinz1=sin(z1)
    sinz2=sin(z2)
    z42=z4*z4
    z32=z3*z3
    coszsq=cosz*cosz

    dzdt=[z3,z4,(-m2*L1*z32*sinz*cosz+g*m2*sinz2*cosz-m2*L2*z42*sinz-m12*g*sinz1)/(L1*m12-m2*L1*coszsq),
         (m2*L2*z42*sinz*cosz+g*sinz1*cosz*m12+L1*z32*sinz*m12-g*sinz2*m12)/(L2*m12-m2*L2*coszsq)]
    return dzdt


# Llamada a odeint que resuelve las ecuaciones de movimiento

nt=20000  #numero de intervalos de tiempo
dt=tf/nt
 
t=np.linspace(0,tf,nt)
abserr = 1.0e-8
relerr = 1.0e-6
z=odeint(double_pendulum,z0,t,args=(par,),atol=abserr, rtol=relerr)
Z=odeint(double_pendulum,Z0,t,args=(par,),atol=abserr, rtol=relerr)

plt.close('all')




# Definicion del grafico
# Aqui se grafica la evolucion de los angulos theta1 y theta 2 con el tiempo

fig, ax1 = plt.subplots()
plt.grid()
ax2 = ax1.twinx()
ax1.set_xlabel('time (s)')
ax1.set_ylabel('$\\theta_1$(rad)', color='b', fontsize=15)
ax2.set_ylabel('$\\theta_2$(rad)', color='r', fontsize=15)
ax1.tick_params('y', colors='b')
ax2.tick_params('y', colors='r')
ax1.set_xlim(xmin=0.,xmax=tf) #limites del eje x
line1, = ax1.plot(t[:],z[:,0], linewidth=2, color='b')
line2, = ax2.plot(t[:],z[:,1], linewidth=2, color='r')

Line1, = ax1.plot(t[:],Z[:,0], linewidth=2, color='c')
Line2, = ax2.plot(t[:],Z[:,1], linewidth=2, color='m')


fig, ax3 = plt.subplots()
plt.grid()
ax4 = ax3.twinx()
ax3.set_xlabel('$\\theta$ (rad)')
ax3.set_ylabel('$\\omega_1$(rad/s)', color='b', fontsize=15)
ax4.set_ylabel('$\\omega_2$(rad/s)', color='r', fontsize=15)
ax3.tick_params('y', colors='b')
ax4.tick_params('y', colors='r')
line1, = ax3.plot(z[:,0],z[:,2], linewidth=2, color='b')
line2, = ax4.plot(z[:,1],z[:,3], linewidth=2, color='r')

Line1, = ax3.plot(Z[:,0],Z[:,2], linewidth=2, color='c')
Line2, = ax4.plot(Z[:,1],Z[:,3], linewidth=2, color='m')


# Aqui se hace una animacion del movimiento del péndulo doble en
# el espacio real (x,y)

x41 = L1*np.sin(z[:,0])
y41 = -L1*np.cos(z[:,0])
x42 = x41 + L2*np.sin(z[:,1])
y42 = y41 - L2*np.cos(z[:,1])
Llong=(L1+L2)*1.1

plt.figure()
plt.axes(xlim=(-Llong,Llong), ylim=(-Llong,Llong))
plt.plot(x41,y41,c='c',label='Péndulo 1',antialiased=True,lw=1)
plt.plot(x42,y42,c='m',label='Péndulo 2',antialiased=True,lw=1)
plt.xlabel('x(m)')
plt.ylabel('y(m)')
plt.title('Trayectoria del péndulo en el espacio real (x,y)')
plt.legend(loc='best')
plt.show()

Llong=(L1+L2)*1.1

fig, ax3 = plt.subplots()
plt.grid()
ax3 = plt.axes(xlim=(-Llong,Llong), ylim=(-Llong,Llong))
ax3.set_xlabel('x (m)')
ax3.set_ylabel('y (m)')

line1,=ax3.plot([],[],lw=2,c='b')
line2,=ax3.plot([],[],lw=2,c='r')
line3,=ax3.plot([],[],lw=1,c='r',linestyle=':')

Line1,=ax3.plot([],[],lw=2,c='c')
Line2,=ax3.plot([],[],lw=2,c='m')
Line3,=ax3.plot([],[],lw=1,c='m',linestyle=':')

bob1 = plt.Circle((1, 1),Llong*0.015, fc='b')
bob2 = plt.Circle((1, 1),Llong*0.015, fc='r')

Bob1 = plt.Circle((1, 1),Llong*0.015, fc='c')
Bob2 = plt.Circle((1, 1),Llong*0.015, fc='m')

time_template = 'Time = %.1fs'
time_text = ax3.text(0.05, 0.9, '', transform=ax3.transAxes)


def init():
    bob1.center = (1, 1)
    Bob1.center = (1, 1)
    ax3.add_artist(bob1)
    ax3.add_artist(Bob1)
    bob2.center = (0,0)
    Bob2.center = (0,0)
    ax3.add_artist(bob2)
    ax3.add_artist(Bob2)
    line1.set_data([],[])
    line2.set_data([],[]) 
    line3.set_data([],[])
    Line1.set_data([],[])
    Line2.set_data([],[]) 
    Line3.set_data([],[])
    time_text.set_text('')
    return bob1,bob2,Bob1,Bob2,line1,line2,line3,Line1,Line2,Line3,time_text


def animate(i):
    if i < nt:
        x1, y1 = bob1.center
        X1, Y1 = Bob1.center
        x1 = L1*sin(z[i,0])
        y1 = -L1*cos(z[i,0])
        X1 = L1*sin(Z[i,0])
        Y1 = -L1*cos(Z[i,0])
        line1.set_data((0,x1),(0,y1))
        Line1.set_data((0,X1),(0,Y1))
        bob1.center = (x1, y1)
        Bob1.center = (X1, Y1)
        x2, y2 = bob2.center
        X2, Y2 = Bob2.center
        x2 = x1+L2*sin(z[i,1])
        y2 = y1-L2*cos(z[i,1])
        X2 = X1+L2*sin(Z[i,1])
        Y2 = Y1-L2*cos(Z[i,1])
        line2.set_data((x1,x2),(y1,y2))
        Line2.set_data((X1,X2),(Y1,Y2))
        line3.set_data(L1*sin(z[0:i,0])+L2*sin(z[0:i,1]),-L1*cos(z[0:i,0])-L2*cos(z[0:i,1]))
        Line3.set_data(L1*sin(Z[0:i,0])+L2*sin(Z[0:i,1]),-L1*cos(Z[0:i,0])-L2*cos(Z[0:i,1]))
        
        bob2.center = (x2, y2)
        Bob2.center = (X2, Y2)

        time_text.set_text(time_template%(i*dt))

    return bob1,bob2,Bob1,Bob2,time_text

anim = animation.FuncAnimation(fig, animate, 
                               init_func=init, 
                               frames=nt,
                               interval=0, blit=True
                               )
#writervideo = animation.FFMpegWriter(fps=60) 
#anim.save('increasingStraightLine.mp4', writer=writervideo) 
plt.show()





