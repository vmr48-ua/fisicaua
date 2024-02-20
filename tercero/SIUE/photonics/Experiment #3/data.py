import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as scis
from statistics import linear_regression

##########
# TASK 1 #
##########

diameter = np.array([17.5,  16.5,  15.5,  14.5,  13.5,  12.5,  11.5,  10.5,  9.5,   8.5,   7.5,   6.5,   5.5,   4.5,   3.5,   2.5,   1.5]) #mm
power =    np.array([4.036, 4.019, 3.997, 3.948, 3.843, 3.721, 3.561, 3.367, 3.103, 2.757, 2.395, 2.042, 1.570, 1.162, 0.780, 0.468, 0.221]) #mW

P_0 = 4.05
w = 5.5

def power_trans(a): # Assuming 11 as the beam diameter (w=5.5)
    return P_0*(1-np.exp(-2*(a/w)**2)) #np.sqrt(-0.5*np.log(1-P/P0)) = aw

x = np.linspace(0,10)

plt.figure()
plt.title('Radii vs Power')
plt.xlabel('Radii ($mm$)')
plt.ylabel('Power ($mW$)')
plt.scatter(diameter/2,power,marker='x')
plt.plot(x,power_trans(x))

# plt.figure()
# plt.title('Radii vs Power NORMALIZED')
# plt.xlabel('Diameter ($mm$)')
# plt.ylabel('Power Normalized')
# plt.scatter(diameter/2,power/P_0,marker='x')
# plt.axhline(0.86)

def log(x):
    return np.log(x)
log=np.vectorize(log)

x=np.linspace(0,20,1000)

plt.figure()
plt.title('Radii vs Power LINEARIZED')
plt.xlabel('Radii')
plt.scatter(diameter,np.sqrt(-0.5*log(1-power/(P_0))),marker='x')
P = np.polyfit(diameter,np.sqrt(-0.5*log(1-power/(P_0))),1)
plt.plot(x,np.polyval(P,x),c='r')
result = scis.linregress(diameter,np.sqrt(-0.5*log(1-power/P_0)))
print(result.slope, (result.rvalue)**2)

##########
# TASK 2 #
##########
# Iris set at minimum diameter

distance = np.array([0.,    1.,    2.,     3.,    4.,    5.,    6.,    7.,    8.,    9.,    10.,   11.,   12.,   13.,   14.,  15.,  16.]) #mm
power =    np.array([2.215, 4.760, 11.720, 22.96, 39.80, 58.35, 74.27, 87.90, 90.24, 75.52, 58.48, 38.49, 25.50, 15.72, 8.35, 3.90, 1.75])

def intensity(a):
    return 94.*np.exp(-2*((a-8.)/5.5)**2)

plt.figure()
plt.title('Distance vs Power')
plt.scatter(distance,power,marker='x')
x = np.linspace(0,16,1000)
plt.plot(x,intensity(x))

plt.show()