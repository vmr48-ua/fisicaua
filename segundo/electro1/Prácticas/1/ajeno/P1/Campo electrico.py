
def E(q,r0,x,y):
    den = np.hypot(x-r0[0],y-r0[1])**3
    return q * (x-r0[0])/den/(4*np.pi), q*(y-r0[1])/den/(4*np.pi)

Ex, Ey = np.zeros((N,N)),np.zeros((N,N))

for charge in cargas:
    ex, ey = E(*charge,x=X,y=Y)
    Ex += ex
    Ey += ey

fig = plt.figure()
ax = fig.add_subplot(111)

color = 2 * np.log(np.hypot(Mx, My))
ax.streamplot(x, y, Mx, My, color=color, linewidth=1, cmap=plt.cm.inferno,
              density=2, arrowstyle='->', arrowsize=1.5)

charge_colors = {True: '#aa0000', False: '0000aa'}
for q, pos in cargas:
    ax.add_artist(Circle(pos,0.05,color = charge_colors[q>0]))


ax.set_xlabel('$x$')
ax.set_ylabel('$y$')
ax.set_xlim(-2,2)
ax.set_ylim(-2,2)
ax.set_aspect('equal')


