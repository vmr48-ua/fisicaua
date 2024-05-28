#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Sun Sep 16 18:47:51 2018

@author: juan antonio miralles
"""

import numpy as np
import math
import random
import matplotlib.pyplot as plt
import matplotlib.mlab as mlab
from scipy.integrate import odeint
import matplotlib.animation as animation

w = 6
h=7
fig = plt.figure(frameon=False)
fig.set_size_inches(w,h)
#fig.set_dpi(100)
#fig.set_size_inches(7, 6.5)

def myParabola(z,t,par):
    x,vx=z
    dzdt=[vx,-(k/m)*(1+4*c**2*x**2)*vx-(4*c**2*x*vx**2+2*g*c*x)/
          (1+4*c**2*x**2)]
    return dzdt
#gravity
g=9.8
#mass
m=2
#parabola y=c x^2
c=1
#friction coefficient
k=0.
par=[g,m,c,k]
nt=1000
z0=[1.0,0]    
t=np.linspace(0,20,nt)
abserr = 1.0e-9
relerr = 1.0e-7
z=odeint(myParabola,z0,t,args=(par,),atol=abserr, rtol=relerr)



ax1=plt.subplot(211)
#ax = plt.axes(xlim=(-3, 3), ylim=(0, 6))
#ax1.axes(xlim=(-3, 3), ylim=(0, 6))
ax1.set_xlim([-2, 2])
ax1.set_ylim([-1, 3])
ax1.set_title('Physical Space')
ax1.set_xlabel('$x$',fontsize=15)
ax1.set_ylabel('$y$',fontsize=15)
#ax1.set_aspect(6./9.)#, 'datalim')
line1,=ax1.plot([],[],lw=2)
punto1,=ax1.plot([],[],"ro",ms=10)
#part = plt.Circle((1, 1), 0.1, fc='r')
#line = plt.plot(z[:,0],z[:,2])

ax2=plt.subplot(212)
ax2.set_xlim([-2, 2])
ax2.set_ylim([-10, 10])
ax2.set_title('Velocity Phase Space')
ax2.set_xlabel('$x$',fontsize=15)
ax2.set_ylabel('$\\dot{x}$',fontsize=15)
line2,=ax2.plot([],[],lw=2)

plt.subplots_adjust(hspace = 0.5)

def init():
#    part.center = (1, 1)
#    ax1.add_artist(part)
    line1.set_data([],[])
#    ax2.add_artist(part)
    line2.set_data([],[])
    return line1,line2,

def animate(i):
#    x, y = part.center
    x = z[i,0]
    xline=np.linspace(-3,3,100)
    yline=c*xline*xline
    y = c*z[i,0]*z[i,0]
    line1.set_data(xline,yline) #c*xline*xline)
    line2.set_data(z[0:i,0],z[0:i,1])
    punto1.set_data(x,y)
#    line1.set_data(z[0:i,0],z[0:i,2])
#    part.center = (x, y)
    return punto1,

anim = animation.FuncAnimation(fig, animate, 
                               init_func=init, 
                               frames=300, 
                               interval=50,repeat=False)
#anim.save('parabola_2_anim.mp4',writer="imagemagick")

plt.show()
