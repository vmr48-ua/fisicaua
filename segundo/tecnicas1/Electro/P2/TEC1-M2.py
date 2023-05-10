import matplotlib.pyplot as plt
import numpy as np
import scipy.stats as scis
from numpy import pi,sqrt

N = 154
mu = 4*pi*10**(-7)
R = 0.208
s = (5/4)**3*(2*R*R)/((mu*N)**2)
print(s)

rErr = 0.001/sqrt(12)
vErr = 5/np.sqrt(12)
iErr = 0.01/np.sqrt(12)

V =  np.array([100,102,104,106,108,110,112,114,116])

# r=0.05 m
I5 = np.array([0.86,0.88,0.92,0.93,0.95,0.96,0.98,0.99,1.01])
# r=0.04 m
I4 = np.array([1.12,1.15,1.15,1.16,1.18,1.19,1.25,1.25,1.25])
# r=0.03 m
I3 = np.array([1.54,1.57,1.61,1.62,1.63,1.66,1.67,1.72,1.73])
# r=0.02 m
I2 = np.array([2.34,2.36,2.38,2.38,2.44,2.44,2.48,2.54,2.54])

plt.figure()
plt.style.use('seaborn')
plt.rcParams.update({'lines.markeredgewidth': 1})
r=0.05
InstErrY = 0
PropErrY = sqrt((-2*s*V*(r**-3)*rErr)**2+(vErr*s*(r**-2))**2)
InstErrX = 0
PropErrX = sqrt((2*I5*iErr)**2)
plt.subplot(2,2,1)
xerr = np.array([PropErrX])
yerr = np.array([np.sqrt(InstErrY**2+PropErrY**2)])
plt.errorbar(I5**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='red',alpha=0.8) 
plt.title('Radi 5cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I5[0]**2,I5[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I5**2,s*r**-2*V,label='Dades',marker='x',c='k')
P5 = np.polyfit(I5**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P5,x),label='Linealització',c='b')
result = scis.linregress(I5**2,s*r**-2*V)
plt.legend(loc='best')
plt.grid(linestyle=':',alpha=0.5)
print('Radio 5cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))

r=0.04
plt.subplot(2,2,2)
PropErrX = sqrt((2*I4*iErr)**2)
xerr = np.array([PropErrX])
plt.errorbar(I4**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='red',alpha=0.8) 
plt.title('Radi 4cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I4[0]**2,I4[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I4**2,s*r**-2*V,label='Dades',marker='x',c='k')
P4 = np.polyfit(I4**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P4,x),label='Linealització',c='b')
result = scis.linregress(I4**2,s*r**-2*V)
plt.legend(loc='best')
plt.grid(linestyle=':',alpha=0.5)
print('Radio 4cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))

r=0.03
plt.subplot(2,2,3)
PropErrX = sqrt((2*I3*iErr)**2)
xerr = np.array([PropErrX])
plt.errorbar(I3**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='red',alpha=0.8) 
plt.title('Radi 3cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I3[0]**2,I3[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I3**2,s*r**-2*V,label='Dades',marker='x',c='k')
P3 = np.polyfit(I3**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P3,x),label='Linealització',c='b')
result = scis.linregress(I3**2,s*r**-2*V)
plt.legend(loc='best')
plt.grid(linestyle=':',alpha=0.5)
print('Radio 3cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))

r=0.02
plt.subplot(2,2,4)
PropErrX = sqrt((2*I2*iErr)**2)
xerr = np.array([PropErrX])
plt.errorbar(I2**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='red',alpha=0.8) 
plt.title('Radi 2cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I2[0]**2,I2[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I2**2,s*r**-2*V,label='Dades',marker='x',c='k')
P2 = np.polyfit(I2**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P2,x),label='Linealització',c='b')
result = scis.linregress(I2**2,s*r**-2*V)
plt.legend(loc='best')
plt.grid(linestyle=':',alpha=0.5)
print('Radio 2cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))
plt.tight_layout(pad=0.8)

plt.figure()
InstErrY = 0
PropErrY = sqrt((-2*s*V*(r**-3)*rErr)**2+(vErr*s*(r**-2))**2)
InstErrX = 0
PropErrX5 = sqrt((2*I5*iErr)**2)
PropErrX4 = sqrt((2*I4*iErr)**2)
PropErrX3 = sqrt((2*I3*iErr)**2)
PropErrX2 = sqrt((2*I2*iErr)**2)
xerr5 = np.array([PropErrX5])
xerr4 = np.array([PropErrX4])
xerr3 = np.array([PropErrX3])
xerr2 = np.array([PropErrX2])
yerr0 = np.array([np.sqrt(InstErrY**2+PropErrY**2)])

plt.title('$S\ \\frac{\Delta U}{r^2}$ davant $I^2$')
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
temp1 = np.concatenate((I5,I4),dtype=float)
temp2 = np.concatenate((temp1,I3),dtype=float)
I = np.concatenate((temp2,I2),dtype=float)
aux1 = np.concatenate((xerr5,xerr4),dtype=float)
aux2 = np.concatenate((aux1,xerr3),dtype=float)
xerr0 = np.concatenate((aux2,xerr2),dtype=float)
xerr = []
for col in xerr0:
    for elem in col:
        xerr.append(elem)
xerr = np.array(xerr)
yaux = np.concatenate((yerr0,yerr0),dtype=float)
yerr = np.concatenate((yaux,yerr0),dtype=float)
yerr0 = np.concatenate((yerr,yerr0),dtype=float)
yerr = []
for col in yerr0:
    for elem in col:
        yerr.append(elem)
yerr = np.array(yerr)
r = np.array([0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,
              0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02])
V = np.array([100,102,104,106,108,110,112,114,116,100,102,104,106,108,110,112,114,116,
              100,102,104,106,108,110,112,114,116,100,102,104,106,108,110,112,114,116])
plt.errorbar(I**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='red',alpha=0.8) 
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I5[0]**2,I2[-1]**2)
plt.scatter(I**2,s*r**-2*V,label='Dades',marker='x',c='k')
P = np.polyfit(I**2,s*r**-2*V,1)
result = scis.linregress(I**2,s*r**-2*V)
plt.plot(x,np.polyval(P,x),label="y = {}\nR² = {}".format('$(2.024\\times 10^{11}\pm 1.4\\times 10^9)x\ +\ (1.2\pm 4\\times 10^9)$',round(result.rvalue**2,4)),c='b')  
plt.legend(loc='best')
plt.grid(linestyle=':',alpha=0.5)
print('e/m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))

plt.show()