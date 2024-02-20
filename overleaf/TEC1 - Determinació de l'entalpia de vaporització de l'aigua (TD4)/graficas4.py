import matplotlib.pyplot as plt
import numpy as np
import scipy.stats as scis
from numpy import sqrt,exp

vol = np.array([7,6.9,6.8,6.7,6.6,6.5,6.4,6.3,6.2,6.1])  * 10**(-6)
errVol = (0.1*10**(-6))/(12**(1/2))
temp = 273.15 + np.array([65,64,62.6,61.4,60.4,57.8,56.1,54.8,52.8,50.1])
errTemp = (0.1)/(12**(1/2))

t0=0.3+273.15
v0=4.8*10**(-6)
tlab = 22.2+273.15
plab = 101820
errPlab = 13/(12**(1/2))
R = 8.314

err1 = ((1 - (v0/t0)*(temp/vol))*errPlab)
err2 = ((plab*v0*temp*errVol)/(t0*vol*vol))
err3 = ((plab*v0*errTemp)/(t0*vol))
err4 = ((plab*temp*errVol)/(t0*vol))
err5 = ((plab*v0*temp*errTemp)/(t0*t0*vol))

"""Presion de Vapor"""
P_v = plab*(1 - (v0/t0)*temp/vol)
errpv = sqrt(err1**2 + err2**2 + err3**2 + err4**2 + err5**2)
print(plab,v0,t0,temp,vol)

P = np.polyfit(temp,P_v,1)
plt.figure(figsize=(9,6),dpi=250)
plt.title("Pressio de vapor en funcio de la temperatura", fontsize = 15)
x = np.linspace(temp[0],temp[-1])
plt.errorbar(temp,P_v,yerr=errpv, fmt='o',label='Dades experimentals',
ms=4,marker='x',elinewidth = 1, capsize = 4, capthick = 1,)
#plt.plot(x,np.polyval(P,x),label='Linealizacion',color = 'r')
plt.legend(loc='best')
plt.grid()
plt.ticklabel_format(style = "sci", scilimits=(0,0), useMathText=True)
plt.ylim(0.6*10**(4),1.7*10**(4))
plt.xlim(3.225*10**(2),3.396*10**(2))
plt.xlabel('Temperatura $(k)$')
plt.ylabel('Pressio $(Pa)$')

ajuste = scis.linregress(P_v,vol)
print('$R* 2 =$',round(ajuste.rvalue,4))
plt.savefig('Presion de vapor.png')

plt.figure(figsize=(9,6),dpi=250)
plt.title("Pressio de vapor en funcio de la temperatura corregida", fontsize = 15)
x = np.linspace(temp[0],temp[-1])
plt.plot(temp, plab-((plab-211)*v0*temp)/(t0*vol),"ro", ms = 4,label = "Dades corregides")
plt.errorbar(temp,P_v,yerr=errpv, fmt='o',label='Dades experimentals',
ms=4,marker='x',elinewidth = 1, capsize = 4, capthick = 1,)
#plt.plot(x,np.polyval(P,x),label='Linealizacion',color = 'r')
plt.legend(loc='best')
plt.grid()
plt.ticklabel_format(style = "sci", scilimits=(0,0), useMathText=True)
plt.ylim(0.6*10**(4),1.7*10**(4))
plt.xlim(3.225*10**(2),3.396*10**(2))
plt.xlabel('Temperatura $(k)$')
plt.ylabel('Pressio $(Pa)$')
plt.savefig('Presion de vapor corregida.png')

"""Ecuacion de Clausisus-Clapeyron"""

T = temp
R = scis.linregress(1/temp,np.log(P_v/plab)).rvalue
print('$R^2 =$',round(R**2,4))
R = R**2
errCl = np.sqrt((errpv/P_v)**2+(errPlab/plab)**2)


plt.figure(figsize=(9,6),dpi=250)
plt.errorbar(1/temp,np.log(P_v/plab), marker='x',yerr=errCl , fmt = 'o', ms=4,
elinewidth = 1, capsize = 4, capthick = 1, label = "Dades experimentals")
plt.title("Equacio de Clausius-Clapeyron", fontsize = 15)
plt.xlabel('$T^{-1}$ $(K^{-1})$')
plt.ylabel("$ \\log(\\frac{P_v}{P_0})$")
label = "$ \\log\\left(\\frac{P_v}{P_0}\\right) = \\left(-5800 \\pm 200\\right)"+"\\frac{1}{T} + \\left(15,3 \\pm 0,6\\right) $ \n $ R^2 = 0.9909"
plt.plot(1/T,np.polyval(np.polyfit(1/temp,np.log(P_v/plab),1),1/temp),"b",
label = label)
plt.ticklabel_format(style = "sci", scilimits=(0,0), useMathText=True)
plt.legend(loc='best')
plt.grid()
plt.savefig('Clausius-Claperyon.png')


"""Antoine"""
def Antoine(t):
    return 1000*exp(16.573 - (3988.842)/(t-39.47))

def lineal(t):
    return plab * exp(np.polyval(np.polyfit(1/temp,np.log(P_v/plab),1),1/t))
t =273.15 + np.linspace(50,65,100)

plt.figure(figsize=(9,6),dpi=250)
plt.errorbar(temp,P_v,yerr=errpv, fmt='o',label='Dades experimentals',
ms=4,marker='x',elinewidth = 1, capsize = 4, capthick = 1,)
plt.plot(t,Antoine(t),"g",label = "Aproximacio de Antoine")
plt.plot(t,lineal(t),"b", label = "$P_v = P_0 e^{(-5800 \\pm 200) T^{-1} + (15,3 \\pm 0,6)}$")
plt.title("Pressio de vapor en funcio de la temperatura", fontsize = 15)
plt.xlabel('$T$ $(K)$', fontsize = 15)
plt.ylabel("$ P_v$ $(Pa)$", fontsize = 15)
plt.ticklabel_format(style = "sci", scilimits=(0,0), useMathText=True)
plt.legend(loc='best')
plt.savefig('antoine.png')
plt.grid()


"Datos"

plt.figure()
plt.title("Comparativa d'entalpies de vaporitzacio", fontsize = 14)
plt.ylabel("$\\Delta \\overline{H}_v$ (J/mol)", fontsize = 14)
plt.errorbar(1, -48200, 1700,fmt = 'x', ms=4, ecolor='r', elinewidth = 1,
capsize = 4, capthick = 1, label = "Experimental")
plt.plot(1, -42767, 'bo', ms=4, label = "Antoine")
plt.plot(1, -40656, 'go', ms=4, label = "Lliteratura")
plt.ticklabel_format(style = "sci", scilimits=(0,0), useMathText=True)
plt.legend(fontsize = 14,shadow = True)
plt.xticks([1])
plt.savefig('entalpies.png')

plt.figure()
plt.title("Comparativa de temperatures d'ebullicio", fontsize = 14)
plt.ylabel("$T_{eb}^0 (K)$", fontsize = 14)
plt.errorbar(1, -380, 20,fmt = 'x', ms=4, ecolor='r', elinewidth = 1,
capsize = 4, capthick = 1, label = "Experimental")
plt.plot(1, -372.76, 'bo', ms=4, label = "Antoine")
plt.plot(1, -373.15, 'go', ms=4, label = "Lliteratura")
plt.ticklabel_format(style = "sci", scilimits=(0,0), useMathText=True)
plt.legend(fontsize = 14,shadow = True)
plt.xticks([1])
plt.savefig('temperatures.png')

plt.show()




