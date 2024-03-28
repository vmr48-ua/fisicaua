import numpy as np
import scipy.stats as scis
import matplotlib.pyplot as plt
from numpy import cos, sqrt, sin
from statistics import linear_regression

############################
#          TASK 3          #
############################

task3_1 = [2.146, 2.11, 2.064, 1.983, 1.907,1.798,1.584,1.39,1.197,1.049,0.8701,0.6934,0.5331,0.3819,0.256,0.1408,0.06484,0.01879, 0.000149]
task3_2 = [0.2845,7.796,41.11,90.29,155.8,224.9,292.8,344.1,382.9,403.6,392.2,361.4,302.5,261.6,169,105,47.01,14.16,0.2705]
x = np.linspace(-10,100,1000)
I_0 = task3_1[0]

# Task 3.1
plt.figure()
plt.title('Light intensity after passing through \n two polarizers at $0^\circ$ to $90^\circ$')

plt.xlim((-5,95))
plt.ylim((-0.2,2.5))
plt.xlabel('$\\theta$ (deg)')
plt.ylabel('I ($\mu W$)')

theta = np.arange(0,95,5)
plt.scatter(theta, task3_1,c='b',marker='x',label='Measured intensity')

def mallus(angle):
    angle = (np.pi/180)*angle
    return I_0*np.cos(angle)**2
x31 = np.linspace(0,90,1000)
plt.plot(x31,mallus(x31),c='orange',label='Mallus Law')

plt.legend(loc='best')

plt.figure()
plt.title('Intensity as a function of $\cos^2(\\theta)$')

x31b = np.cos(x31*(np.pi/180))**2
P = np.polyfit(np.cos(theta*(np.pi/180))**2,task3_1,1)
result = scis.linregress(np.cos(theta*(np.pi/180))**2,task3_1)
# result has result.slope, (result.rvalue)**2

plt.scatter(np.cos(theta*(np.pi/180))**2,task3_1,c='r',marker='x',label='Measured intensity')
plt.plot(x31b,np.polyval(P,x31b),label='$y=2.135x$\n $R^2=0.99929$')

plt.legend(loc='best')

# Task 3.2

plt.figure()
plt.title('Light intensity after passing cross polarizers \n with a polarizer in between at $0^\circ$ to $90^\circ$')

plt.xlim((-2,92))
plt.ylim((-2,450))
plt.xlabel('$\\theta$ (deg)')
plt.ylabel('I ($\mu W$)')

theta = np.arange(0,95,5)
plt.scatter(theta, task3_2,c='r',marker='x',label='Measured intensity')

x32 = np.linspace(0,90,1000)
plt.plot(x32,350*mallus(x32)*mallus(90-x32),label='Mallus fitting')

plt.legend(loc='best')

plt.figure()
plt.title('Intensity as a function of $\sin^2(\\theta)\cos^2(\\theta)$')

x32b = np.cos(x32*(np.pi/180))**2*np.sin(x32*(np.pi/180))**2
P = np.polyfit(np.cos(theta*(np.pi/180))**2*np.sin(theta*(np.pi/180))**2,task3_2,1)
result1 = scis.linregress(np.cos(theta*(np.pi/180))**2*np.sin(theta*(np.pi/180))**2,task3_2)
# result1 has result.slope, (result.rvalue)**2

plt.scatter(np.cos(theta*(np.pi/180))**2*np.sin(theta*(np.pi/180))**2,task3_2,c='r',marker='x',label='Measured intensity')
plt.plot(x32b,np.polyval(P,x32b),label='$y=1609.35x$\n $R^2=0.99666$')
# print(result1.slope, result1.rvalue**2)

plt.legend(loc='best')

plt.show()
############################
#          TASK 4          #
############################

task4p = np.array([41,40.71,37.51,35.83,31.8,27.43,21.81,15.6,9.62,3.846,0.291,1.7,14.21,43.35,112.6,248.5])
task4s = np.array([104.7,108.2,112.9,123.2,134.8,151.7,174.7,202,248.8,299.9,374.7,454.7,611.7,835.5,1094,1458])

n = 1.5

def Rs(inc):
    angle = (np.pi/180)*inc
    num = cos(angle)-n*sqrt(1-(sin(angle)/n)**2)
    den = cos(angle)+n*sqrt(1-(sin(angle)/n)**2)
    return abs(num/den)**2

def Rp(inc):
    angle = (np.pi/180)*inc
    num = sqrt(1-(sin(angle)/n)**2)-n*cos(angle)
    den = sqrt(1-(sin(angle)/n)**2)+n*cos(angle)
    return abs(num/den)**2


theta = np.arange(5,85,5)
x4 = np.linspace(5,85,1000)
I_0p = 1227
I_0s = 2882

# greatestR=0
# n=1
# while(n<=2):
#     resultP = scis.linregress(Rp(theta),task4p/I_0p)
#     if resultP.rvalue**2 >= greatestR**2:
#         greatestR = resultP.rvalue
#         finalN = n
#     n+=0.01
# print(finalN)

# greatestR=0
# n=1
# while(n<=2):
#     resultS = scis.linregress(Rs(theta),task4s/I_0s)
#     if resultS.rvalue**2 >= greatestR**2:
#         greatestR = resultS.rvalue
#         finalN2 = n
#     n+=0.01
# print(finalN2)

#the best n possible is n=1.5, it maximizes the R^2 value

###
plt.figure()
plt.title('Fresnel Reflection')
plt.xlim(-2,82)
plt.ylim(-0.01,0.52)
plt.axhline(0,linestyle='--',c='k',alpha=0.5)

plt.xlabel('Angle of incidence (deg)')
plt.ylabel('Intensity ($I$/$I_0$)')
plt.scatter(theta,task4p/I_0p,c='b',label='p-polarization',marker='x')
plt.scatter(theta,task4s/I_0s,c='r',label='s-polarization',marker='x')

plt.plot(x4,Rp(x4),label='p-fit',c='b')
plt.plot(x4,Rs(x4),label='s-fit',c='r')

plt.legend(loc='best')

###
plt.figure()
plt.title('Fresnel Reflection as a funciton of Rs')
plt.ylabel('Intensity ($I$/$I_0$)')
plt.xlabel('$R_s(\\theta)$')
plt.ylim(0,0.51)
plt.xlim(0,0.61)

P = np.polyfit(Rs(theta),task4s/I_0s,1)

x4s = Rs(x4)
plt.scatter(Rs(theta),task4s/I_0s,label='Data',marker='x',c='r')
plt.plot(x4s,np.polyval(P,x4s),label='y=0.9414x\n$R^2=0.99976$',c='r')

plt.legend(loc='best')
resultS = scis.linregress(Rs(theta),task4s/I_0s)
print(linear_regression(Rs(theta),task4s/I_0s,proportional=True),resultS.rvalue)

###
plt.figure()
plt.title('Fresnel Reflection as a funciton of Rp')
plt.ylabel('Intensity ($I$/$I_0$)')
plt.xlabel('$R_p(\\theta)$')
plt.ylim(0,0.25)
plt.xlim(0,0.25)

P = np.polyfit(Rp(theta),task4p/I_0p,1)

x4p = Rp(x4)
plt.scatter(Rp(theta),task4p/I_0p,label='Data',marker='x',c='b')
plt.plot(x4p,np.polyval(P,x4p),label='y=0.8561x\n$R^2=0.99995$',c='b')

plt.legend(loc='best')
resultP = scis.linregress(Rp(theta),task4p/I_0p)
print(linear_regression(Rp(theta),task4p/I_0p,proportional=True),resultP.rvalue)

plt.show()

############################
#          TASK 9          #
############################

task9 = np.array([8.5,12.5,16,19,21])
concentration = np.arange(0.10,0.35,0.05)

plt.figure()
plt.title('Angle of rotation over sugar concentration')
plt.xlabel('concentration ($g$/$ml$)')
plt.ylabel('angle (deg)')

plt.scatter(concentration,task9,c='r',marker='x',label='Data points')
plt.plot(concentration,np.polyval([75.444,0],concentration),label='$y=75.444x$\n$R^2=0.9955$')
plt.scatter(np.polyval([1/75.444,0],8.5),8.5,label='Unknown Sample (0.106 $g$/$ml$)')
plt.scatter(0.125,np.polyval([75.444,0],0.125),label='Expected Unknown Sample value')

plt.legend(loc='best')
plt.show()
