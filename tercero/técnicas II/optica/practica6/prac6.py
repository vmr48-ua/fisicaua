import numpy as np
import scipy.stats as scis
import matplotlib.pyplot as plt
from numpy import cos, sqrt, sin, pi, arcsin
from statistics import linear_regression

alpha = np.array([0,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90])
# Intensidad con los polarizadores y el retardador (Watts)
I_exp = np.array([1.061e-3, 39.4e-3, 146.3e-3, 0.303, 0.496, 0.698, 0.896, 1.052, 1.160, 1.191, 1.151, 1.047, 0.885, 0.682, 0.479, 0.280, 129.6e-3, 32.7e-3, 1.328e-3])*1e-3

# Parte 3
I_p1 = 4.75e-3 # Watts

# Parte 4
I_i  = 4.75e-3 # Watts
I_t  = 4.04e-3 # Watts
T_r  = I_t/I_i

# Parte 5
I_t90 = 81.3e-6  # Watts
I_i90 = 127.8e-6 # Watts
T_p2  = I_t90/I_i90

# Parte 6
I_norm = I_exp/(I_p1*T_r*T_p2)

plt.figure()
x = np.linspace(0,1,1000)

xo = sin(2*alpha*2*pi/360)**2
yo = I_norm

P = np.polyfit(xo,yo,1)
result = scis.linregress(xo,yo)
plt.plot(x,np.polyval(P,x), 
        label=f'$m=${np.round(result.slope,3)}$\\pm${np.round(result.stderr,3)}\n$R^2=${np.round(result.rvalue**2,5)}')


plt.scatter(xo,yo, marker='x')
plt.ylabel('$I_\\text{normalizada}$')
plt.xlabel('$\\sin^2{2\\alpha}$')
plt.grid()
plt.legend(loc='upper left')

# Parte 7
# Determinación de delta
delta = 2*arcsin(sqrt(result.slope))*360/(2*pi)
error_delta = 2*arcsin(sqrt(result.stderr))*360/(2*pi)

print(f'δ​​={round(delta)}±{round(error_delta)}')

#################################################
# PARTE 2                                       #
#################################################

# Prueba A
angulos = np.array([0, 10,    20,    30,    40,    50,    60,    70,    80,    90,    100,   110,   120,   130,   140,   150,   160,   170,   180]) # deg
I_a = np.array([0.692, 0.703, 0.712, 0.718, 0.720, 0.722, 0.720, 0.715, 0.705, 0.698, 0.691, 0.686, 0.683, 0.684, 0.687, 0.696, 0.706, 0.717, 0.728])*1e-3 # Watts

# Prueba B
angulos = np.array([0, 10,    20,    30,    40,    50,    60,    70,    80,    90,    100,   110,   120,   130,   140,   150,   160,   170,   180]) # deg
I_b = np.array([1.475, 1.456, 1.378, 1.251, 1.091, 0.915, 0.757, 0.594, 0.502, 0.469, 0.496, 0.579, 0.715, 0.880, 1.057, 1.227, 1.365, 1.457, 1.484])*1e-3 # 

plt.figure()
plt.scatter(angulos,I_a, marker='x')
plt.scatter(angulos,I_b, marker='x')
plt.xlabel('Ángulo (°)')
plt.ylabel('Intensidad (W)')
plt.show()