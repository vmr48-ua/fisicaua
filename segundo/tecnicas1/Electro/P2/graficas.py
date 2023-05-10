import matplotlib.pyplot as plt
import numpy as np
import scipy.stats as scis
from numpy import pi,sqrt

N = 154
mu = 4*pi*10**(-7)
R = 0.201
s = (5/4)**3*(2*R*R)/((mu*N)**2)
print(s)

rErr = 0.001/sqrt(3)
vErr = 5/np.sqrt(12)
iErr = 0.01/np.sqrt(3)

V =  np.array([100,102,104,106,108,110,112,114,116])

# r=0.05 m
I5 = np.array([0.86,0.88,0.92,0.93,0.95,0.96,0.98,0.99,1.01])
# r=0.04 m
I4 = np.array([1.12,1.15,1.15,1.16,1.18,1.19,1.25,1.25,1.25])
# r=0.03 m
I3 = np.array([1.54,1.57,1.61,1.62,1.63,1.66,1.67,1.72,1.73])
# r=0.02 m
I2 = np.array([2.34,2.36,2.38,2.38,2.44,2.44,2.48,2.54,2.54])

V = np.array([90,93,96,99,102,105,108,111,114,117,121,124,127,130])

I5 = np.array([0.76,0.78,0.80,0.82,0.84,0.87,0.89,0.92,0.94,0.97,0.99,1.01,1.02,1.05])
I4 = np.array([0.95,0.97,0.99,1.01,1.02,1.04,1.06,1.08,1.12,1.14,1.20,1.23,1.26,1.29])
I3 = np.array([1.32,1.35,1.38,1.41,1.45,1.50,1.53,1.56,1.58,1.62,1.66,1.68,1.72,1.74])
I2 = np.array([1.74,1.78,1.81,1.85,1.89,1.93,1.99,2.02,2.05,2.09,2.12,2.15,2.19,2.22])

plt.figure()
r=0.05
InstErrY = 0
PropErrY = sqrt((-2*s*V*(r**-3)*rErr)**2+(vErr*s*(r**-2))**2)
InstErrX = 0
PropErrX = sqrt((2*I5*iErr)**2)
xerr = np.array([PropErrX])
yerr = np.array([np.sqrt(InstErrY**2+PropErrY**2)])
plt.errorbar(I5**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='blue',alpha=0.4,capsize=3) 
plt.title(' Radio 5cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I5[0]**2,I5[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I5**2,s*r**-2*V,label='Datos',marker='o',c='k')
P5 = np.polyfit(I5**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P5,x),label='Linealización',c='b')
result = scis.linregress(I5**2,s*r**-2*V)
plt.legend(loc='best')
plt.savefig('5.png')
 
print('  Radio 5cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))

plt.figure()
r=0.04
PropErrX = sqrt((2*I4*iErr)**2)
xerr = np.array([PropErrX])
plt.errorbar(I4**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='blue',alpha=0.4,capsize=3) 
plt.title(' Radio 4cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I4[0]**2,I4[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I4**2,s*r**-2*V,label='Datos',marker='o',c='k')
P4 = np.polyfit(I4**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P4,x),label='Linealización',c='b')
result = scis.linregress(I4**2,s*r**-2*V)
plt.legend(loc='best')
plt.savefig('4.png')

print('  Radio 4cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))

plt.figure()
r=0.03
PropErrX = sqrt((2*I3*iErr)**2)
xerr = np.array([PropErrX])
plt.errorbar(I3**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='blue',alpha=0.4,capsize=3) 
plt.title(' Radio 3cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I3[0]**2,I3[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I3**2,s*r**-2*V,label='Datos',marker='o',c='k')
P3 = np.polyfit(I3**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P3,x),label='Linealización',c='b')
result = scis.linregress(I3**2,s*r**-2*V)
plt.legend(loc='best')
plt.savefig('3.png')

print('  Radio 3cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))

plt.figure()
r=0.02
PropErrX = sqrt((2*I2*iErr)**2)
xerr = np.array([PropErrX])
plt.errorbar(I2**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='blue',alpha=0.4,capsize=3) 
plt.title(' Radio 2cm')
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I2[0]**2,I2[-1]**2)
plt.ticklabel_format(style='sci',scilimits=(0,0),useMathText=True)
plt.scatter(I2**2,s*r**-2*V,label='Datos',marker='o',c='k')
P2 = np.polyfit(I2**2,s*r**-2*V,1)
plt.plot(x,np.polyval(P2,x),label='Linealización',c='b')
result = scis.linregress(I2**2,s*r**-2*V)
plt.legend(loc='best')
plt.savefig('2.png')
 
print('  Radio 2cm: m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))
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

plt.title('$S\ \\frac{\Delta U}{r^2}$ frente $I^2$')
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
r = np.array([0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.05,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,0.04,
              0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.03,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02,0.02])
V = np.array([90,93,96,99,102,105,108,111,114,117,121,124,127,130,90,93,96,99,102,105,108,111,114,117,121,124,127,130,
              90,93,96,99,102,105,108,111,114,117,121,124,127,130,90,93,96,99,102,105,108,111,114,117,121,124,127,130])
plt.errorbar(I**2,s*r**-2*V, yerr = yerr, xerr = xerr, ls='none', color='blue',alpha=0.4,capsize=3) 
plt.xlabel('$I^2\ [A]$',weight='bold')
plt.ylabel('$S\ \\frac{\Delta U}{r^2} \left[\\frac{A^2V}{T^2m^2}\\right]$',weight='bold')
x=np.linspace(I5[0]**2,I2[-1]**2)
plt.scatter(I**2,s*r**-2*V,label='Datos',marker='o',c='k')
P = np.polyfit(I**2,s*r**-2*V,1)
result = scis.linregress(I**2,s*r**-2*V)
plt.plot(x,np.polyval(P,x),label="y = {}\nR² = {}".format('$(3.136\\times 10^{11}\pm 9\\times 10^9)x\ -\ (8\pm 2)\\times 10^{10}$',round(result.rvalue**2,4)),c='b')  
 
print('e/m = ',"{:e}".format(result.slope),'±',"{:e}".format(result.stderr), 'b = ',"{:e}".format(result.intercept), '±', "{:e}".format(result.intercept_stderr))
plt.savefig('todos.png')

plt.show()