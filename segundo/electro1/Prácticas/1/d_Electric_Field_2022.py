#import sys
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

def E(q, r0, x, y):
    """Return the electric field vector E=(Ex,Ey) due to charge q at r0."""
    den = np.hypot(x-r0[0], y-r0[1])**3
    return q * (x - r0[0]) / den/(4*np.pi), q * (y - r0[1]) / den/(4*np.pi)

# TamaÃ±o y resolucion
L  = 4.0
L0 = -2.0
N = 21
h = L/(N-1) #resolucion
q = 5. 


# Define arrays used for plotting
x = np.linspace(L0,L+L0,N) 
y = np.linspace(L0,L+L0,N)
# Aunque ahora no lo necesitamos. Podemos definir una red para un campo vectorial.
# Esto nos darÃ¡ un poco mÃ¡s de juego y nos permitirÃ¡ representar campos vectoriales.
X,Y = np.meshgrid(y,x)


charges = []
charges.append((q,(-0.5,0)))
charges.append((-q,(0.5,0)))

# Electric field vector, E=(Ex, Ey), as separate components
Ex, Ey = np.zeros((N, N)), np.zeros((N, N))

for charge in charges:
    ex, ey = E(*charge, x=X, y=Y)
    Ex += ex
    Ey += ey
   # print (charge)

fig = plt.figure()
ax = fig.add_subplot(111)

# Plot the streamlines with an appropriate colormap and arrow style
color = 2 * np.log(np.hypot(Ex, Ey))
ax.streamplot(x, y, Ex, Ey, color=color, linewidth=1, cmap=plt.cm.inferno,
              density=2, arrowstyle='->', arrowsize=1.5)

# Add filled circles for the charges themselves
charge_colors = {True: '#aa0000', False: '#0000aa'}
for q, pos in charges:
    ax.add_artist(Circle(pos, 0.05, color=charge_colors[q>0]))

ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')



z=np.hypot(Ex,Ey)

#x2 = fig.add_subplot(122)
fig = plt.figure()

ax = fig.add_subplot(111)

plt.plot(x,np.transpose(z))
ax.set_xlabel('$x$')
ax.set_ylabel('$E$')
# Show the result in the plot window
plt.show()
