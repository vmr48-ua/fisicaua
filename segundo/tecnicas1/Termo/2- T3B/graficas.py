import matplotlib.pyplot as plt
import numpy as np

temp = np.arange(0,28,2)
tAgua = np.array([0,114,230,349,466,584,710,832,958,1084,1208,1336,1463,1602])
tMetanol = np.array([0,60,121,186,250,318,387,462,542,626,714,805,905,1016])
tEtanol = np.array([0,62,125,188,252,319,385,451,519,588,658,726,799,871])
V = 200 #+-5
verr = 5
I = 0.19 #+-0.01
ierr = 0.01
terr = 0.1
CaAgua = 4.186 
CaMetanol = 2549
CaEtanol = 2400 

plt.figure()
P = np.polyfit(CaAgua * temp,V * I * tAgua,1)
x = np.linspace(0,110,200)
yerr = np.array([np.sqrt((verr/np.sqrt(12))**(2)+(ierr/np.sqrt(12))**(2)+(terr/np.sqrt(12))**(2))]*14)
xerr = np.array([CaAgua*0.1/(12)**(1/2)]*14)
plt.title('Agua')
plt.xlabel('$C_a \cdot \Delta t$')
plt.ylabel('$V \cdot I \cdot t$')
plt.scatter(CaAgua * temp,V * I * tAgua,label='Mediciones',color = 'black',marker='x')
plt.plot(x,np.polyval(P,x),color='magenta',label='Aproximación lineal: y = {}'.format(np.poly1d(P)))
plt.errorbar(CaAgua * temp,V * I * tAgua, yerr = yerr, xerr = xerr, ls='none', color='black') 
plt.legend(loc='best')

plt.figure()
Q = np.polyfit(temp,V * I * tMetanol,1)
x = np.linspace(0,27,200)
yerr = np.array([np.sqrt((verr/np.sqrt(12))**(2)+(ierr/np.sqrt(12))**(2)+(terr/np.sqrt(12))**(2))]*14)
xerr = np.array([0.1/(12)**(1/2)]*14)
plt.title('Metanol')
plt.xlabel('$\Delta t$')
plt.ylabel('$V \cdot I \cdot t$')
plt.scatter(temp,V * I * tMetanol,label='Mediciones')
plt.plot(x,np.polyval(Q,x),color='magenta',label='Aproximación lineal: y = {}'.format(np.poly1d(Q)))
plt.errorbar(temp,V * I * tMetanol, yerr = yerr, xerr = xerr, ls='none', color='black') 
plt.legend(loc='best')

plt.figure()
R = np.polyfit(temp,V * I * tEtanol,1)
x = np.linspace(0,27,200)
yerr = np.array([np.sqrt((verr/np.sqrt(12))**(2)+(ierr/np.sqrt(12))**(2)+(terr/np.sqrt(12))**(2))]*14)
xerr = np.array([0.1/(12)**(1/2)]*14)
plt.title('Etanol')
plt.xlabel('$\Delta t$')
plt.ylabel('$V \cdot I \cdot t$')
plt.scatter(temp,V * I * tEtanol,label='Mediciones')
plt.plot(x,np.polyval(R,x),color='magenta',label='Aproximación lineal: y = {}'.format(np.poly1d(R)))
plt.errorbar(temp,V * I * tEtanol, yerr = yerr, xerr = xerr, ls='none', color='black') 
plt.legend(loc='best')

plt.show()
