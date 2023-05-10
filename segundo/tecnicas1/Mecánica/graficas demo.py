import matplotlib.pyplot as plt
import numpy as np
import scipy.stats as scis
from numpy import pi,sqrt 

g40 = np.array([22,30,37,41,49,59,64,74,82,88]) #10
g70 = np.array([25,32,41,52,60,72,80,90,100])   #9
g120 = np.array([28,39,47,61,74,85,100])        #7
g170 = np.array([30,41,58,72,88,100])           #6

plt.figure()
plt.subplots_adjust(wspace=0.3, hspace=0.3)
plt.subplot(221)
npicos = np.linspace(1,10,10)
x = np.linspace(1,10)
plt.ticklabel_format(style='sci',useMathText=True)
plt.title('Frequència - nº antinodos - 40g',weight='bold')
plt.xlabel('nº antinodos',weight='bold')
plt.ylabel('Frequència (hz)',weight='bold')
plt.scatter(npicos,g40,label='Dades',marker='o',c='k')
P40 = np.polyfit(npicos,g40,1)
plt.plot(x,np.polyval(P40,x),label='Linealització',c='b')
result40 = scis.linregress(npicos,g40)
plt.legend(loc='best')

plt.subplot(222)
npicos = np.linspace(1,9,9)
x = np.linspace(1,9)
plt.ticklabel_format(style='sci',useMathText=True)
plt.title('Frequència - nº antinodos - 70g',weight='bold')
plt.xlabel('nº antinodos',weight='bold')
plt.ylabel('Frequència (hz)',weight='bold')
plt.scatter(npicos,g70,label='Dades',marker='o',c='k')
P70 = np.polyfit(npicos,g70,1)
plt.plot(x,np.polyval(P70,x),label='Linealització',c='b')
result70 = scis.linregress(npicos,g70)
plt.legend(loc='best')

plt.subplot(223)
npicos = np.linspace(1,7,7)
x = np.linspace(1,7)
plt.ticklabel_format(style='sci',useMathText=True)
plt.title('Frequència - nº antinodos - 120g',weight='bold')
plt.xlabel('nº antinodos',weight='bold')
plt.ylabel('Frequència (hz)',weight='bold')
plt.scatter(npicos,g120,label='Dades',marker='o',c='k')
P120 = np.polyfit(npicos,g120,1)
plt.plot(x,np.polyval(P120,x),label='Linealització',c='b')
result120 = scis.linregress(npicos,g120)
plt.legend(loc='best')

plt.subplot(224)
npicos = np.linspace(1,6,6)
x = np.linspace(1,6)
plt.ticklabel_format(style='sci',useMathText=True)
plt.title('Frequència - nº antinodos - 170g',weight='bold')
plt.xlabel('nº antinodos',weight='bold')
plt.ylabel('Frequència (hz)',weight='bold')
plt.scatter(npicos,g170,label='Dades',marker='o',c='k')
P170 = np.polyfit(npicos,g170,1)
plt.plot(x,np.polyval(P170,x),label='Linealització',c='b')
result170 = scis.linregress(npicos,g170)
plt.legend(loc='best')

plt.show()

print('40g:  y = (',round(result40.slope,2),' ± ',round(result40.stderr,2),')x + (',round(result40.intercept,0),' ± ',round(result40.intercept_stderr,0),')',sep='')
print('70g:  y = (',round(result70.slope,2),' ± ',round(result70.stderr,2),')x + (',round(result70.intercept,1),' ± ',round(result70.intercept_stderr,1),')',sep='')
print('120g: y = (',round(result120.slope,1),' ± ',round(result120.stderr,1),')x + (',round(result120.intercept,1),' ± ',round(result120.intercept_stderr,1),')',sep='')
print('170g: y = (',round(result170.slope,1),' ± ',round(result170.stderr,1),')x + (',round(result170.intercept,1),' ± ',round(result170.intercept_stderr,1),')',sep='')

print('R² = ',round(result40.rvalue**2,4),sep='')
print('R² = ',round(result70.rvalue**2,4),sep='')
print('R² = ',round(result120.rvalue**2,4),sep='')
print('R² = ',round(result170.rvalue**2,4),sep='')