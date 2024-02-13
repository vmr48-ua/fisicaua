import numpy as np
from numpy import sin,cos,sqrt
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


# t=np.linspace(0,10,1000)
# plt.figure()
# plt.plot(t,sin(t)**2,color='blue',label='Potential Energy')
# plt.plot(t,cos(t)**2,color='orange',label='Kinetic Energy')
# plt.plot(t,sin(t)**2+cos(t)**2,label='K+U')
# plt.axhline(0.5,linestyle='--',label='x$=\\dfrac{1}{2}$')
# plt.legend(loc='best')
# plt.show()

########################################################################

# def f(x):
#     return 0.8*x

# t=np.linspace(0,10,1000)
# plt.figure()
# plt.plot(t,f(t))
# plt.axvline(8,linestyle='--')
# plt.legend(loc='best')
# plt.show()

#------------------------------------------------------------------

import matplotlib.pyplot as plt
import matplotlib.animation as animation

g = 9.81 # Acceleration due to gravity, m.s-2.
XMAX = 1 # The maximum x-range of ball's trajectory to plot.
YMAX = 1.25
cor = 0.99 # The coefficient of restitution for bounces (-v_up/v_down).
dt = 0.005 # The time step for the animation.

# Initial position and velocity vectors.
x0, y0 = 0, 1
vx0, vy0 = 0, 0

def get_pos(t=0):
    """A generator yielding the ball's position at time t."""
    x, y, vx, vy = x0, y0, vx0, vy0
    while x < XMAX:
        t += dt
        x += vx0 * dt
        y += vy * dt
        vy -= g * dt
        if y < 0:
            y = 0 # bounce!
            vy = -vy * cor
        yield x, y

def init():
    """Initialize the animation figure."""
    ax.set_xlim(-XMAX, XMAX)
    ax.set_ylim(0, YMAX)
    ax.set_xlabel('$x$ /m')
    ax.set_ylabel('$y$ /m')
    line.set_data(xdata, ydata)
    ball.set_center((x0, y0))
    height_text.set_text(f'Height: {y0:.1f} m')
    return line, ball, height_text

def animate(pos):
    """For each frame, advance the animation to the new position, pos."""
    x, y = pos
    xdata.append(x)
    ydata.append(y)
    line.set_data(xdata, ydata)
    ball.set_center((x, y))
    height_text.set_text(f'Height: {y:.1f} m')
    return line, ball, height_text

# Set up a new Figure, with equal aspect ratio so the ball appears round.
fig, ax = plt.subplots()
ax.set_aspect('equal')

# These are the objects we need to keep track of.
line, = ax.plot([], [], lw=2)
ball = plt.Circle((x0, y0), 0.03)
height_text = ax.text(XMAX*0.5, y0*0.8, f'Height: {y0:.1f} m')
ax.add_patch(ball)
xdata, ydata = [], []

interval = 1000*dt
ani = animation.FuncAnimation(fig, animate, get_pos, blit=True,
                      interval=interval, repeat=False, init_func=init,save_count=188)
writergif = animation.PillowWriter(fps=30)
ani.save('animation.gif', writer=writergif)

Y=ydata.copy()

for i in range(len(Y)):
    if Y[i]==0:
        bounce = i
        break
for i in range(bounce, len(Y)):
        Y[i] = -Y[i]

Y=np.append(Y,np.negative(Y))
Y=np.append(Y,Y)

plt.figure()
plt.title('"Position" over four bounces')
plt.ylabel('position ($m$)')
plt.xlabel('time ($s$)')
time = np.linspace(0,2*sqrt(2/g)+0.032,len(Y))
plt.plot(time,Y,label='y')
plt.plot(time,np.cos(13.5*time),label='cos(13.5t)',alpha=0.5,linestyle='--')
plt.legend()

dt = time[1]-time[0]
V = []
for i in range(len(Y)-1):
    V.append((Y[i+1]-Y[i])/dt)
A = []
for i in range(len(V)-1):
    A.append((V[i+1]-V[i])/dt)

time = time[::-1]
time = time[:-1]
time = time[::-1]
plt.figure()
plt.title('Velocity over one bounce')
plt.ylabel('velocity ($\\frac{m}{s}$)')
plt.xlabel('time ($s$)')
plt.plot(time,V)

time = time[::-1]
time = time[:-1]
time = time[::-1]
plt.figure()
plt.title('Acceleration over one bounce')
plt.ylabel('acceleration ($\\frac{m}{s^2}$)')
plt.xlabel('time ($s$)')
plt.plot(time,A)

plt.ylim((-10,3))
plt.show()
