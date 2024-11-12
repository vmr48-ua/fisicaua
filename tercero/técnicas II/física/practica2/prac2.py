import numpy as np 
import matplotlib.pyplot as plt
from numpy import arcsin, tan, arctan, pi, sinc, sin, cos
from scipy.optimize import fsolve
from statistics import linear_regression
import scipy.stats as scis

HeNe      = 632.8e-9  #m
apertura  = 4e-5  #m
distancia = 1.875 #m

regla = np.arange(-74,74,4)
fotoR_ida1 = np.array(   [np.inf,np.inf,np.inf,11.92,6.63, 4.91,4.84,5.73,12.5, np.inf,np.inf,6.82,1.88,0.84, 0.53, 0.355,0.247,0.209,0.167,0.199,0.250,0.276,0.505,1.065,2.17,5.71,np.inf,np.inf,np.inf,15.46,10.37,9.38,9.79,13.71,np.inf,np.inf,np.inf]) # mega ohm
fotoR_ida2 = np.array(   [np.inf,np.inf,np.inf,12.42,6.81, 5.15,4.69,5.73,12.56,np.inf,np.inf,4.88,1.59,0.775,0.451,0.318,0.255,0.206,0.165,0.228,0.272,0.350,0.515,0.913,2.15,5.65,np.inf,np.inf,np.inf,15.46,8.3,  7.02,9.33,13.33,np.inf,np.inf,np.inf])

fotoR_vuelta1 = np.array([np.inf,np.inf,np.inf,17.2, 11.08,9.89,9.21,12.29,np.inf,np.inf,np.inf,6.21,1.64,1.12, 0.524,0.387,0.323,0.263,0.163,0.229,0.278,0.367,0.544,0.919,1.833,7.01, np.inf,np.inf,11.30,6.47,5.22,5.62,7.99, 15.07, np.inf,np.inf,np.inf])
fotoR_vuelta2 = np.array([np.inf,np.inf,np.inf,14.85,7.56, 6.46,6.3, 9.41, np.inf,np.inf,np.inf,4.42,1.65,0.771,0.485,0.298,0.262,0.211,0.213,0.228,0.281,0.382,0.614,1.163,3.37, 15.12,np.inf,np.inf,7.56, 5.28,4.92,6.17,10.34,np.inf,np.inf,np.inf,np.inf])

I0    = 1/((0.167+0.165+0.163+0.211)*0.25)
I_ida1 = 1/fotoR_ida1
I_ida2 = 1/fotoR_ida2
I_vuelta1 = 1/fotoR_vuelta1
I_vuelta2 = 1/fotoR_vuelta2
Intsd = (I_ida1+I_ida2+I_vuelta1[::-1]+I_vuelta2[::-1])/4

def intensidad_teorica(theta):
    beta = (pi*apertura/HeNe*np.sin(theta))
    return I0 * (sinc(2*pi/360 * beta))**2

def derivada_intensidad(theta):
    beta = (pi * apertura / HeNe * np.sin(theta))
    dbeta_dtheta = (pi * apertura / HeNe * cos(theta))
    return 2 * I0 * sinc(2 * pi / 360 * beta) * (cos(2 * pi / 360 * beta) * dbeta_dtheta) * (-2 * pi / 360)

ang   = arctan(2*pi*regla/(distancia*360))
theta0 = np.linspace(-0.7, 0.7, 1000)
plt.figure()
plt.plot(ang, Intsd, 'x', label='Intensidad vs angulo')
plt.plot(theta0, intensidad_teorica(theta0), label='Intensidad vs angulo teórico')


theta_minimos_candidatos = fsolve(derivada_intensidad, np.linspace(-0.6, 0.6, 4))
minimos = []
for theta in theta_minimos_candidatos:
    idx = np.argmin(np.abs(theta0 - theta))
    if 0 < idx < len(theta0) - 1:
        if intensidad_teorica(theta0[idx]) < intensidad_teorica(theta0[idx - 1]) and intensidad_teorica(theta0[idx]) < intensidad_teorica(theta0[idx + 1]):
            minimos.append(theta)

for theta in minimos:
    plt.axvline(x=theta, color='r', linestyle='--')

plt.grid()
plt.legend(loc='upper right')

plt.figure()
P = np.polyfit(intensidad_teorica(ang),Intsd,1)
ang1 = intensidad_teorica(ang)
plt.scatter(intensidad_teorica(ang1),Intsd,label='Data',marker='x',c='b')
plt.plot(ang1,np.polyval(P,ang1))
result = scis.linregress(intensidad_teorica(ang1),Intsd)
print(linear_regression(intensidad_teorica(ang1),Intsd,proportional=True),result.rvalue)

plt.show()