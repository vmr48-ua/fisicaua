import matplotlib.pyplot as plt
import numpy as np
import scipy.stats as scis

temp = np.arange(0,28,2)
tAgua = np.array([0,114,230,349,466,584,710,832,958,1084,1208,1336,1463,1602])
tMetanol = np.array([0,60,121,186,250,318,387,462,542,626,714,805,905,1016])
tEtanol = np.array([0,62,125,188,252,319,385,451,519,588,658,726,799,871])
secsErr = 1/np.sqrt(12)
V = 200 #+-5
verr = 5/np.sqrt(12)
I = 0.19 #+-0.01
ierr = 0.01/np.sqrt(12)
terr = 0.1
InstErrY = 0
PropErrY = np.sqrt((V*I*secsErr)**2+(verr*I*tAgua)**2+(V*ierr*tAgua)**2)
CaAgua = 4.186 
CaMetanol = 2549
CaEtanol = 2400 

plt.figure(figsize=(9,6),dpi=250)
P = np.polyfit(CaAgua * temp,V * I * tAgua,1)
Pajustado = np.array([559,-700])
result = scis.linregress(CaAgua *temp, V*I*tAgua)

x = np.linspace(0,110,200)
yerr = np.array([np.sqrt(InstErrY**2+PropErrY**2)])
xerr = np.array([CaAgua*0.1/(12)**(1/2)]*14)
plt.title('Masa equivalente del calorímetro',weight='bold',fontsize='22')
plt.xlabel('$c_a \cdot \Delta T\ \ (J\cdot g⁻¹)$',weight='bold',fontsize='22')
plt.ylabel('$V \cdot I \cdot t\ \ (J)$',weight='bold',fontsize='22')
plt.xticks(fontsize=14)
plt.yticks(fontsize=14)
plt.plot(x,np.polyval(Pajustado,x),color='magenta',label="y = {}\nR² = {}".format('$(559±3)x\ -\ (700±200)$',round(result.rvalue**2,4)))
plt.errorbar(CaAgua * temp,V * I * tAgua, yerr = yerr, xerr = xerr, ls='none', color='black') 
plt.legend(loc='best',fontsize=18)
plt.grid(linestyle=':',alpha=0.5)
plt.scatter(CaAgua * temp,V * I * tAgua,label='Mediciones',color = 'black',marker='.')
plt.savefig('agua.png')
print('Agua: m = ',result.slope,'±',result.stderr, 'b = ',result.intercept, '±', result.intercept_stderr)

plt.figure(figsize=(9,6),dpi=250)
Q = np.polyfit(temp,V * I * tMetanol,1)
x = np.linspace(0,27,200)
result2 = scis.linregress(temp, V*I*tMetanol)
PropErrY = np.sqrt((V*I*secsErr)**2+(verr*I*tMetanol)**2+(V*ierr*tMetanol)**2)
yerr = np.array([np.sqrt(InstErrY**2+PropErrY**2)])
xerr = np.array([0.1/(12)**(1/2)]*14)
plt.title('Calor específico del metanol',weight='bold',fontsize='22')
plt.xlabel('$\Delta T\ \ (k)$',weight='bold',fontsize='22')
plt.ylabel('$V \cdot I \cdot t\ \ (J)$',weight='bold',fontsize='22')
plt.xticks(fontsize=14)
plt.yticks(fontsize=14)
plt.plot(x,np.polyval(Q,x),color='magenta',label="y = {}\nR² = {}".format('$(1460±40)x\ -\ (1600±600)$',round(result2.rvalue**2,4)))
plt.errorbar(temp,V * I * tMetanol, yerr = yerr, xerr = xerr, ls='none', color='black') 
plt.legend(loc='best',fontsize=18)
plt.grid(linestyle=':',alpha=0.5)
plt.scatter(temp,V * I * tMetanol,label='Mediciones',color = 'black',marker='.')
plt.savefig('metanol.png')
print('Metanol: m = ',result2.slope,'±',result2.stderr, 'b = ',result2.intercept, '±', result2.intercept_stderr)


plt.figure(figsize=(9,6),dpi=250)
M = np.polyfit(temp,V * I * tEtanol,1)
x = np.linspace(0,27,200)
result3 = scis.linregress(temp, V*I*tEtanol)
PropErrY = np.sqrt((V*I*secsErr)**2+(verr*I*tEtanol)**2+(V*ierr*tEtanol)**2)
yerr = np.array([np.sqrt(InstErrY**2+PropErrY**2)])
xerr = np.array([0.1/(12)**(1/2)]*14)
plt.title('Calor específico del etanol',weight='bold',fontsize='22')
plt.xlabel('$\Delta T\ \ (k)$',weight='bold',fontsize='22')
plt.ylabel('$V \cdot I \cdot t\ \ (J)$',weight='bold',fontsize='22')
plt.xticks(fontsize=14)
plt.yticks(fontsize=14)
plt.plot(x,np.polyval(M,x),color='magenta',label="y = {}\nR² = {}".format('$(1273±8)x\ -\ (410±130)$',round(result3.rvalue**2,4)))
plt.errorbar(temp,V * I * tEtanol, yerr = yerr, xerr = xerr, ls='none', color='black') 
plt.legend(loc='best',fontsize=18)
plt.grid(linestyle=':',alpha=0.5)
plt.scatter(temp,V * I * tEtanol,label='Mediciones',color = 'black',marker='.')
plt.savefig('etanol.png')
print('Etanol: m = ',result3.slope,'±',result3.stderr, 'b = ',result3.intercept, '±', result3.intercept_stderr)

plt.show()