import numpy as np
import scipy.stats as scis
import matplotlib.pyplot as plt
from numpy import cos, sqrt, sin
from statistics import linear_regression

If1 = np.array([ 35,    115,   143,   123,   78,    150,   173,   123,   166,   185,   107,   101,   174,   103,   100])*1e-3 #microW
Ii1 = np.array([ 733.2, 733.2, 733.2, 786.3, 786.3, 786.3, 802.2, 802.2, 802.2, 808.5, 808.5, 808.5, 785.3, 785.3, 785.3])    #microW
Ir1 = np.array([ 30.83, 33.03, 36.09, 38.58, 44.56, 49.17, 59.73, 69.75, 89.36, 105.4, 141.8, 174.3, 239.4, 303.6, 385.5])    #microW
ang1 = np.array([10,    14,    20,    24,    30,    34,    40,    44,    50,    54,    60,    64,    70,    74,    78])       #deg

If2 = np.array([ 56,    112,   316,   285,   242,   236,   230,   182,   60,    166,   91,    83,    102,   85,    71,    106,   202])*1e-3  #microW
Ii2 = np.array([ 2.462, 2.462, 2.462, 2.456, 2.456, 2.456, 2.471, 2.471, 2.471, 2.470, 2.470, 2.470, 2.446, 2.446, 2.446, 2.442, 2.442])*1e3 #microW
Ir2 = np.array([ 94.53, 81.54, 62.22, 35.44, 9.97,  6.58,  3.485, 3.251, 5.020, 9.825, 18.06, 33.17, 52.06, 78.81, 128.6, 246.1, 446.5])     #microW
ang2 = np.array([10,    20,    30,    40,    50,    52,    54,    56,    58,    60,    62,    64,    66,    68,    70,    74,    78])        #deg

Ir_1 = Ir1 - If1
Ir_2 = Ir2 - If2

Rexp1 = Ir_1/Ii1
Rexp2 = Ir_2/Ii2

n_aire = 1.00029

def Rs(inc):
    angle = (np.pi/180)*inc
    num = n_aire*cos(angle)-n*sqrt(1-(sin(angle)/n)**2)
    den = n_aire*cos(angle)+n*sqrt(1-(sin(angle)/n)**2)
    return abs(num/den)**2

def Rp(inc):
    angle = (np.pi/180)*inc
    num = n_aire*sqrt(1-(sin(angle)/n)**2)-n*cos(angle)
    den = n_aire*sqrt(1-(sin(angle)/n)**2)+n*cos(angle)
    return abs(num/den)**2

xo = np.linspace(0,85,1000)
greatestR=0
n=1
while(n<=2):
    resultP = scis.linregress(Rp(ang2),Ir_2/Ii2)
    if resultP.rvalue**2 >= greatestR**2:    #type: ignore
        greatestR = resultP.rvalue           #type: ignore
        finalN = n
    n+=0.01
print(finalN)

greatestR=0
n=1
while(n<=2):
    resultS = scis.linregress(Rs(ang1),Ir_1/Ii1)
    if resultS.rvalue**2 >= greatestR**2: #type: ignore
        greatestR = resultS.rvalue        #type: ignore
        finalN2 = n
    n+=0.01
print(finalN2)

n = 1.46
#el mejor n posible is n=1.46, it maximizes the R^2 value

###
plt.figure(figsize=(7,7))
plt.title('Fresnel Reflection')
plt.xlim(-2,82)
plt.ylim(-0.01,0.52)
plt.axhline(0,linestyle='--',c='k',alpha=0.5, label='Brewster Angle = {}$^\\circ$'.format(ang2[np.argmin(Ir_2)]))

plt.xlabel('Angle of incidence (deg)')
plt.ylabel('Intensity ($I$/$I_0$)')
plt.scatter(ang2,Rexp2,c='b',label='p-polarization',marker='x')
plt.scatter(ang1,Rexp1,c='r',label='s-polarization',marker='x')

plt.plot(xo,Rp(xo),label='p-fit',c='b')
plt.plot(xo,Rs(xo),label='s-fit',c='r')

plt.legend(loc='best')

######
# Rs #
######
plt.figure(figsize=(7,7))
plt.title('Fresnel Reflection as a funciton of Rs')
plt.ylabel('Intensity ($I$/$I_0$)')
plt.xlabel('$R_s(\\theta)$')
plt.ylim(0,0.51)
plt.xlim(0,0.61)

P = np.polyfit(Rs(ang1),Rexp1,1)
resultS = scis.linregress(Rs(ang1),Rexp1)
print(linear_regression(Rs(ang1),Rexp1,proportional=True),resultS.rvalue**2) # type: ignore

x4s = Rs(xo)
plt.scatter(Rs(ang1),Rexp1,label='Data',marker='x',c='r')
plt.plot(x4s,np.polyval(P,x4s),label=f'$y=1.0754x$\n$R^2=${round(resultS.rvalue**2,6)}',c='r') # type: ignore
plt.legend(loc='best')

######
# Rp #
######
plt.figure(figsize=(7,7))
plt.title('Fresnel Reflection as a funciton of Rp')
plt.ylabel('Intensity ($I$/$I_0$)')
plt.xlabel('$R_p(\\theta)$')
plt.ylim(0,0.25)
plt.xlim(0,0.25)

P = np.polyfit(Rp(ang2),Rexp2,1)
resultP = scis.linregress(Rp(ang2),Rexp2)
print(linear_regression(Rp(ang2),Rexp2,proportional=True),resultP.rvalue**2) #type: ignore

x4p = Rp(xo)
plt.scatter(Rp(ang2),Rexp2,label='Data',marker='x',c='b')
plt.plot(x4p,np.polyval(P,x4p),label=f'$y=1.0640x$\n$R^2=${round(resultP.rvalue**2,6)}',c='b') #type: ignore
plt.legend(loc='best')


plt.show()