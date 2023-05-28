import xlrd
import numpy as np 
from numpy import sin,cos,pi,sqrt,log
import matplotlib.pyplot as plt 
import scipy.stats as scis

excel = xlrd.open_workbook("/home/victor/fisicaua/segundo/tecnicas1/Mecánica/P1/datos.xls")
hoja = excel.sheet_by_index(0)

t,y,v,ac,w = [],[],[],[],[]
a,b = 1,1322

for i in range(a,b):
    t.append(float(hoja.cell_value(i, 0)))
    y.append(float(hoja.cell_value(i, 1)))
    v.append(float(hoja.cell_value(i, 2)))

plt.figure()
plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.scatter(t,y,c='r',s=0.1)
plt.ylabel('$y(m)$')
plt.xlabel('$t(s)$')

######################################################################
y = np.copy(-np.array(y))
plt.figure()
P = np.polyfit(log(abs(np.array(t[3:162]))),log(abs(np.array(y[3:162])-y[0])),1)
x = np.linspace(log(t[3]),log(t[162]))
plt.plot(x,np.polyval(P,x),c='b',alpha=1)
plt.title('Representació logarítmica')
plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.scatter(log(abs(np.array(t[3:162]))),log(abs(np.array(y[3:162])-y[0])),c='r',s=1)
plt.ylabel('$\log(y(m))$')
plt.xlabel('$\log(t(s))$')
result = scis.linregress(log(abs(np.array(t[3:162]))),log(abs(np.array(y[3:162])-y[0])))
plt.annotate('$y=1.95695x - 3.875$',(1,-2.65))
plt.annotate('$R² = 0.9817$',(1,-3))

print('y = (',round(result.slope,5),' ± ',round(result.stderr,5),')x + (',round(result.intercept,3),' ± ',round(result.intercept_stderr,3),')',sep='')
print('R² = ',round(result.rvalue**2,4),sep='')

plt.show()
y = -np.array(y)

######################################################################

plt.figure()
plt.subplots_adjust(hspace=0.65)
bottom,top=0,0.8

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(221)
plt.title('Primera caiguda')
P1 = np.polyfit(t[0:162],y[0:162],2)
x = np.linspace(t[0],t[162])
plt.plot(x,np.polyval(P1,x),c='b',alpha=0.7)
plt.scatter(t[0:162],y[0:162],c='r',s=1)
plt.ylabel('$y(m)$')
plt.xlabel('t(s)')
plt.ylim(bottom, top)
plt.annotate('$y=-0.01206 x^2 - 0.03579 x + 0.648$',(1.6,0.7))
plt.annotate('$R² = 0.902$',(1.6,0.6))

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(222)
plt.title('Primer bot')
P2 = np.polyfit(t[163:567],y[163:567],2)
x = np.linspace(t[163],t[567])
plt.plot(x,np.polyval(P2,x),c='b',alpha=0.7)
plt.scatter(t[163:567],y[163:567],c='r',s=1)
plt.ylabel('$y(m)$')
plt.xlabel('t(s)')
plt.ylim(bottom, top) 
plt.annotate('$y=-0.01331 x^2 + 0.3366 x - 1.524$',(8.5,0.7)) 
plt.annotate('$R² = 0.957$',(8.5,0.6))

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(223)
plt.title('Segon bot')
P3 = np.polyfit(t[567:952],y[567:952],2)
x = np.linspace(t[567],t[952])
plt.plot(x,np.polyval(P3,x),c='b',alpha=0.7)
plt.scatter(t[567:952],y[567:952],c='r',s=1)
plt.ylabel('$y(m)$')
plt.xlabel('t(s)')
plt.ylim(bottom, top) 
plt.annotate('$y=-0.01312 x^2 + 0.6772 x - 8.189$',(21.5,0.7)) 
plt.annotate('$R² = 0.959$',(21.5,0.6))

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(224)
plt.title('Tercer bot')
P4 = np.polyfit(t[952:],y[952:],2)
x = np.linspace(t[952],t[-1])
plt.plot(x,np.polyval(P4,x),c='b',alpha=0.7)
plt.scatter(t[952:],y[952:],c='r',s=1)
plt.ylabel('$y(m)$')
plt.xlabel('t(s)')
plt.ylim(bottom, top) 
plt.annotate('$y=-0.01324 x^2 + 1.017 x - 19.03$',(34.5,0.7)) 
plt.annotate('$R² = 0.912$',(34.5,0.6))

########################################################################

plt.figure()
plt.subplots_adjust(hspace=0.65)
bottom,top=-0.25,0.25

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(221)
plt.title('Primera caiguda')
P1 = np.polyfit(t[0:162],v[0:162],1)
x = np.linspace(t[0],t[162])
plt.plot(x,np.polyval(P1,x),c='b',alpha=0.7)
plt.scatter(t[0:162],v[0:162],c='r',s=1)
plt.ylabel('$v(m/s)$')
plt.xlabel('$t(s)$')
plt.ylim(bottom, top)
result0 = scis.linregress(t[0:162],v[0:162])
plt.annotate('$y = (-0.024 ± 0.0004)x + (-0.0356 ± 0.0013)$',(1,0.2)) 
plt.annotate('$R² = 0.964$',(1,0.15))

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(222)
plt.title('Primer bot')
P2 = np.polyfit(t[163:567],v[163:567],1)
x = np.linspace(t[163],t[567])
plt.plot(x,np.polyval(P2,x),c='b',alpha=0.7)
plt.scatter(t[163:567],v[163:567],c='r',s=1)
plt.ylabel('$v(m/s)$')
plt.xlabel('$t(s)$')
plt.ylim(bottom, top)
result1 = scis.linregress(t[163:567],v[163:567])
plt.annotate('$y = (-0.0263 ± 0.0001)x + (0.333 ± 0.002)$',(8,0.2)) 
plt.annotate('$R² = 0.9873$',(8,0.15))

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(223)
plt.title('Segon bot')
P3 = np.polyfit(t[567:952],v[567:952],1)
x = np.linspace(t[567],t[952])
plt.plot(x,np.polyval(P3,x),c='b',alpha=0.7)
plt.scatter(t[567:952],v[567:952],c='r',s=1)
plt.ylabel('$v(m/s)$')
plt.xlabel('$t(s)$')
plt.ylim(bottom, top) 
result2 = scis.linregress(t[567:952],v[567:952])
plt.annotate('$y = (-0.0261 ± 0.0001)x + (0.674 ± 0.004)$',(21,0.2)) 
plt.annotate('$R² = 0.9883$',(21,0.15))

plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.subplot(224)
plt.title('Tercer bot')
P4 = np.polyfit(t[952:],v[952:],1)
x = np.linspace(t[952],t[-1])
plt.plot(x,np.polyval(P4,x),c='b',alpha=0.7)
plt.scatter(t[952:],v[952:],c='r',s=1)
plt.ylabel('$v(m/s)$')
plt.xlabel('$t(s)$')
plt.ylim(bottom, top) 
result3 = scis.linregress(t[952:],v[952:])
plt.annotate('$y = (-0.02625 ± 0.00014)x + (1.009 ± 0.005)$',(34,0.2)) 
plt.annotate('$R² = 0.99$',(34,0.15))

# print('y = (',round(result0.slope,4),' ± ',round(result0.stderr,4),')x + (',round(result0.intercept,4),' ± ',round(result0.intercept_stderr,4),')',sep='')
# print('y = (',round(result1.slope,4),' ± ',round(result1.stderr,4),')x + (',round(result1.intercept,3),' ± ',round(result1.intercept_stderr,3),')',sep='')
# print('y = (',round(result2.slope,4),' ± ',round(result2.stderr,4),')x + (',round(result2.intercept,3),' ± ',round(result2.intercept_stderr,3),')',sep='')
# print('y = (',round(result3.slope,5),' ± ',round(result3.stderr,5),')x + (',round(result3.intercept,3),' ± ',round(result3.intercept_stderr,3),')',sep='')

# print('R² = ',round(result0.rvalue**2,4),sep='')
# print('R² = ',round(result1.rvalue**2,4),sep='')
# print('R² = ',round(result2.rvalue**2,4),sep='')
# print('R² = ',round(result3.rvalue**2,4),sep='')


###############################################

g=9.80665
m=0.5104
I=5.052e-3

U  = m*g*np.array(y)
Kr = 0.5*I*np.square(np.array(v)/5.1e-3)
Kt = 0.5*m*np.square(np.array(v))
K  = Kr + Kt
E  = U + K

plt.figure()
plt.title('Energia potencial')
plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.scatter(t,U ,s=0.5,label='Energia potencial')
plt.ylabel('$E(J)$')
plt.xlabel('$t(s)$')
plt.legend(loc='best')

plt.figure()
plt.title('Energia cinètica de rotació')
plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.scatter(t,Kr,s=0.5,label='Energia cinètica de rotació')
plt.ylabel('$E(J)$')
plt.xlabel('$t(s)$')
plt.legend(loc='best')

plt.figure()
plt.title('Energia cinètica de translació')
plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.scatter(t,Kt,s=0.5,label='Energia cinètica de translació')
plt.ylabel('$E(J)$')
plt.xlabel('$t(s)$')
plt.legend(loc='best')

plt.figure()
plt.title('Energia mecànica')
plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.scatter(t,E ,s=0.5,label='Energia mecànica')
plt.scatter(t,K ,s=0.5,label='Energia cinètica')
plt.scatter(t,U ,s=0.5,label='Energia potencial')
plt.ylabel('$E(J)$')
plt.xlabel('$t(s)$')
plt.legend(loc='best')

plt.figure()
plt.title('Energia cinètica')
plt.rcParams['axes.spines.right'] = False
plt.rcParams['axes.spines.top'] = False
plt.scatter(t,Kt,s=0.5,label='Energia cinètica de translació')
plt.scatter(t,Kr,s=0.5,label='Energia cinètica de rotació')
plt.scatter(t,K ,s=0.5,label='Energia cinètica')
plt.ylabel('$E(J)$')
plt.xlabel('$t(s)$')
plt.legend(loc='best')