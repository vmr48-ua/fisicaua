"""
Escribe un programa que resuelva la ecuación diferencial
y′′(x) + sen(y(x)) + sen(x) = 0 con las condiciones iniciales
y(−10) = 0,  y′(−10) = π  en el intervalo [−10,10]. Muestra
el resultado mediante una gráfica guardada con el nombre
grafica.png (no se debe mostrar la gráfica por pantalla)
"""
from numpy import linspace,sin,pi
from scipy.integrate import odeint
import matplotlib.pyplot as plt

def f(u,x):
    return (u[1],-sin(u[0])-sin(x))

#a,b = 2,23
a,b = -10,10

t = linspace(a,b,200)
y = odeint(f,[0,pi],t)

fig = plt.figure(figsize=(6,8), constrained_layout = True)
gs = fig.add_gridspec(2,hspace=0.2)
axs = gs.subplots()

plt.xlim(a,b)
axs[0].title.set_text('x, y')
axs[0].plot(t,y[:,0])

plt.xlim(a,b) # con (0,25) de límite quedaría mejor (por ejemplo)
axs[1].title.set_text('y, y\'')
axs[1].plot(y[:,0],y[:,1])

plt.savefig('grafica.png',dpi=350)
plt.show()
plt.figure()