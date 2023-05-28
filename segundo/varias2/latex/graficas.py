import matplotlib.pyplot as plt
from numpy import arctan,log,sin,cos,log10,sqrt
import numpy as np

#color de fondo de los ejercicios
color_ejemplo = '#F2FBF8' 
color_comment = '#F3F3F3'

"""
#################
Integral de línea
#################
"""
def f1(x):
    return arctan(x-3)+4

x = np.linspace(1,6)
plt.figure(figsize=(10,6))
plt.plot(x,f1(x),c='k')
plt.scatter([1,6],[f1(1),f1(6)],c='k')
plt.annotate('A',[1,f1(1)+0.2],fontsize=18)
plt.annotate('B',[6,f1(6)+0.2],fontsize=18)
plt.vlines(x=1, ymin = 0, ymax= f1(1), color='k', linestyle='--')
plt.vlines(x=6, ymin = 0, ymax= f1(6), color='k', linestyle='--')
plt.annotate("", [2.5,f1(2.5)], xytext=(2.49, f1(2.49)),arrowprops=dict(arrowstyle="->"),fontsize=30)
plt.annotate("", [4.5,f1(4.5)], xytext=(4.49, f1(4.49)),arrowprops=dict(arrowstyle="->"),fontsize=30)
plt.xticks([1, 6], ['a', 'b'])
plt.yticks([],[])
plt.axis('off')
plt.xlim(0,7)
plt.ylim(2,6)
#plt.savefig('latex/integraldelinea.png')

"""
#############
Ejemplo 4.1.3
#############
"""
def f2(x):
    return x
def f3(x):
    return x**2

x = np.linspace(-0.1,1.1)
xf= np.linspace(0,1)
fig, ax = plt.subplots()
fig.patch.set_facecolor(color_ejemplo)
ax.set_facecolor(color_ejemplo)
ax.plot(x,f3(x),c='k')
ax.plot(x,f2(x),c='k')
ax.fill_between(xf,f3(xf),f2(xf),color="none",hatch="//",edgecolor="k")
ax.annotate('$\mathcal{D}$', xy=(0.45, 0.3), xytext=(0.3, 0.8),
            arrowprops=dict(facecolor='black', shrink=0.05),fontsize=22)
ax.annotate('$y=x^2$',[0.7,f3(0.7)-0.2],fontsize=18)
ax.annotate('$y=x$',[0.7,f2(0.7)+0.3],fontsize=18)
ax.vlines(x=1, ymin = 0, ymax= f2(1), color='k', linestyle='--')
ax.spines['left'].set_position('zero')
ax.spines['right'].set_color('none')
ax.yaxis.tick_left()
ax.spines['bottom'].set_position('zero')
ax.spines['top'].set_color('none')
ax.xaxis.tick_bottom()
ax.set_xticks([1],['1'],fontsize=14)
ax.set_yticks([],[])
#plt.savefig('latex/ejemplo4.1.3.png')

"""
#############
Ejemplo 4.1.4
#############
"""
def f4(x):
    return 2
f4=np.vectorize(f4)
def f5(x):
    return np.e**x

x = np.linspace(-0.05,np.log(2)+0.1)
xf= np.linspace(0,np.log(2))
fig, ax = plt.subplots()
fig.patch.set_facecolor(color_ejemplo)
ax.set_facecolor(color_ejemplo)
ax.plot(x,f4(x),c='k')
ax.plot(x,f5(x),c='k')
ax.fill_between(xf,f4(xf),f5(xf),color="none",hatch="//",edgecolor="k")
ax.annotate('$\mathcal{D}$', xy=(0.25, 1.7), xytext=(0.1, 0.7),
            arrowprops=dict(facecolor='black', shrink=0.05),fontsize=22)
ax.annotate('$y=2$',[np.log(2)/2+0.1,2.1],fontsize=18)
ax.annotate('$y=e^x$',[np.log(2)/2+0.1,1.35],fontsize=18)
ax.vlines(x=np.log(2), ymin = 0, ymax= f4(1), color='k', linestyle='--')
ax.spines['left'].set_position('zero')
ax.spines['right'].set_color('none')
ax.yaxis.tick_left()
ax.spines['bottom'].set_position('zero')
ax.spines['top'].set_color('none')
ax.tick_params(axis=u'both', which=u'both',length=0)
ax.set_ylim(ymin=-0.4, ymax=3)
ax.set_xticks([np.log(2)],['$\log\left(2\\right)$'],fontsize=14)
ax.set_yticks([1.1,2.1],['1','2'],fontsize=14)
#plt.savefig('latex/ejemplo4.1.4.png')

"""
#################
Integrales dobles
#################
"""
def f6(x):
    return 1
def f7(x):
    return 3
def f8(x):
    return 1.2
def f9(x):
    return 1.7
f6,f7,f8,f9=np.vectorize(f6),np.vectorize(f7),np.vectorize(f8),np.vectorize(f9) 

x = np.linspace(1,4)
xr = np.linspace(2.75,3.5)
xf= np.linspace(0,np.log(2))
fig, ax = plt.subplots()

ax.plot(x,f6(x),c='k')
ax.plot(x,f7(x),c='k')
ax.plot(xr,f8(xr),c='k')
ax.plot(xr,f9(xr),c='k')
ax.vlines(x=1, ymin = 1, ymax= 3, color='k', linestyle='-')
ax.vlines(x=1, ymin = 0, ymax= 1, color='k', linestyle='--')
ax.vlines(x=4, ymin = 1, ymax= 3, color='k', linestyle='-')
ax.vlines(x=4, ymin = 0, ymax= 1, color='k', linestyle='--')
ax.hlines(y=1, xmin = 0, xmax= 1, color='k', linestyle='--')
ax.hlines(y=3, xmin = 0, xmax= 1, color='k', linestyle='--')

ax.vlines(x=3.5, ymin = 1.2, ymax= 1.7, color='k', linestyle='-')
ax.vlines(x=2.75, ymin = 1.2, ymax= 1.7, color='k', linestyle='-')
ax.vlines(x=3.5, ymin = 0, ymax= 1.2, color='k', linestyle='--')
ax.vlines(x=2.75, ymin = 0, ymax= 1.2, color='k', linestyle='--')
ax.hlines(y=1.2, xmin = 0, xmax= 2.75, color='k', linestyle='--')
ax.hlines(y=1.7, xmin = 0, xmax= 2.75, color='k', linestyle='--')

ax.fill_between(xr,f8(xf),f9(xf),color="none",hatch="//",edgecolor="k")

ax.spines['left'].set_position('zero')
ax.spines['right'].set_color('none')
ax.yaxis.tick_left()
ax.spines['bottom'].set_position('zero')
ax.spines['top'].set_color('none')
ax.tick_params(axis=u'both', which=u'both',length=0)
ax.set_xlim(xmin=-0.4, xmax=5)
ax.set_ylim(ymin=-0.4, ymax=4)
ax.set_xticks([1,4],['a','b'],fontsize=12)
ax.set_yticks([0.98,1.25,1.75,2.98],['c','$y_{j-1}$','$y_j$','d'],fontsize=12)
#plt.savefig('latex/integralesdobles.png')

"""
################
Comentario 4.1.3
################
"""
def f6(x):
    return 1
def f7(x):
    return 3
def f8(x):
    return 1.2
def f9(x):
    return 1.7
def f10(x):
    if x < 3.88:
        return 1.028*sqrt(log10(x-0.804)+sin(x)**2)+2
    else: 
        return 3
def f11(x):
    return -0.93*sqrt(log10(x-0.712)+cos(x-2))+2

f6,f7,f8,f9,f10=np.vectorize(f6),np.vectorize(f7),np.vectorize(f8),np.vectorize(f9),np.vectorize(f10)   

x = np.linspace(1,4,5000)
xr = np.linspace(1,4,5000)
xr1 = np.linspace(1,4,5000)
fig, ax = plt.subplots()
ax.fill_between(x,f10(x),f11(x),color="none",hatch="//",edgecolor="r")

fig.patch.set_facecolor(color_comment)
ax.set_facecolor(color_comment)

ax.plot(x, f6( x ),c='k')
ax.plot(x, f7( x ),c='k')
ax.plot(xr,f10(xr),c='r')
ax.plot(xr1,f11(xr1),c='r')

ax.annotate('$\mathcal{D}$', xy=(2, 1.7), xytext=(2.3, 1.8),fontsize=22)

ax.vlines(x=1, ymin = 1, ymax= 3, color='k', linestyle='-')
ax.vlines(x=1, ymin = 0, ymax= 1, color='k', linestyle='--')
ax.vlines(x=4, ymin = 1, ymax= 3, color='k', linestyle='-')
ax.vlines(x=4, ymin = 0, ymax= 1, color='k', linestyle='--')
ax.hlines(y=1, xmin = 0, xmax= 1, color='k', linestyle='--')
ax.hlines(y=3, xmin = 0, xmax= 1, color='k', linestyle='--')

ax.spines['left'].set_position('zero')
ax.spines['right'].set_color('none')
ax.yaxis.tick_left()
ax.spines['bottom'].set_position('zero')
ax.spines['top'].set_color('none')
ax.tick_params(axis=u'both', which=u'both',length=0)
ax.set_xlim(xmin=-0.4, xmax=5)
ax.set_ylim(ymin=-0.4, ymax=4)
ax.set_xticks([1,4],['a','b'],fontsize=12)
ax.set_yticks([0.98,2.98],['c','d'],fontsize=12)

ax.vlines(x=4, ymin = f11(4), ymax= 3, color='r', linestyle='-')

plt.savefig('latex/comentario4.1.3.png')

plt.show()
