import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as scis
from statistics import linear_regression
from scipy.integrate import quad
import math as mth

wavelength = 632.8e-9

##########
# TASK 1 #
##########
diameter = np.array([17.5,  16.5,  15.5,  14.5,  13.5,  12.5,  11.5,  10.5,  9.5,   8.5,   7.5,   6.5,   5.5,   4.5,   3.5,   2.5,   1.5]) #mm
power =    np.array([4.036, 4.019, 3.997, 3.948, 3.843, 3.721, 3.561, 3.367, 3.103, 2.757, 2.395, 2.042, 1.570, 1.162, 0.780, 0.468, 0.221]) #mW

diameter = 10**(-3)*diameter
power = 10**(-3)*power

def log(x):
    return np.log(x)
log=np.vectorize(log)

# x=np.linspace(0,diameter[0]/2,1000)
linearization=np.sqrt(-0.5*log(1-power/(power[0]+3e-5)))

# plt.figure(figsize=(10,8))
# plt.title('Linearization')
# plt.xlabel('$\dfrac{1}{\omega}x$')
# plt.ylabel('$\sqrt{-\dfrac{1}{2}\log(1-P/P_0)}$')
# plt.scatter(diameter/2,linearization,marker='x',label='linearized data')
# P = np.polyfit(diameter/2,linearization,1)
# plt.plot(x,np.polyval(P,x),c='r',label='$y=178.55x \ \ R^2=0.99879$')
# plt.legend(loc='best')

result = scis.linregress(diameter/2,linearization)
print(result.slope, (result.rvalue)**2)
w = 1/result.slope
print("w=",round(w,5))

# def power_trans(a):  
#     return power[0]*(1-np.exp(-2*(a/w)**2)) 

# x = np.linspace(0,10e-3)

# plt.figure(figsize=(10,8))
# plt.title('Radii vs Power')
# plt.xlabel('Radii ($m$)')
# plt.ylabel('Power ($W$)')
# plt.scatter(diameter/2,power,marker='x',label='data')
# plt.plot(x,power_trans(x),c='r',label='$P_0\left[1-\exp\left(-2\dfrac{a^2}{\omega^2}\\right)\\right]$')
# plt.legend(loc='best')


##########
# TASK 2 #
##########
# Iris set at minimum diameter

# distance = np.array([0.,    1.,    2.,     3.,    4.,    5.,    6.,    7.,    8.,    9.,    10.,   11.,   12.,   13.,   14.,  15.,  16.]) #mm
# power =    np.array([2.215, 4.760, 11.720, 22.96, 39.80, 58.35, 74.27, 87.90, 90.24, 75.52, 58.48, 38.49, 25.50, 15.72, 8.35, 3.90, 1.75]) #mW

# distance = 10**(-3)*distance
# distance = [point-7.55e-3 for point in distance]
# power = 10**(-3)*power

# def intensity(a):
#     return np.max(power)*np.exp(-2*((a)/w)**2)

# plt.figure(figsize=(10,8))
# plt.title('Power vs Distance')
# plt.xlabel('Distance $(m)$')
# plt.ylabel('Power $(W)$')
# Gauss_beamR = np.max(power)/np.e**2
# print(Gauss_beamR)
# plt.scatter(distance,power,marker='x',label='data')
# plt.axhline(Gauss_beamR,linestyle='--',c='k',alpha=0.7,label='$e^{-2}\cdot \max$(power)')
# plt.axvline(-0.005483,ymin=0.,ymax=0.16,linestyle=':',c='k',alpha=0.5,label='Estimated width without fitting')
# plt.axvline(0.005831,ymin=0.,ymax=0.16,linestyle=':',c='k',alpha=0.5)
# plt.annotate('$2\omega\\Rightarrow\omega=0.00566m$', xy=(0.005831, 0.01), xytext=(-0.0015, 0.009),arrowprops=dict(arrowstyle="->",color='k'))
# plt.annotate('$2\omega\\Rightarrow\omega=0.00566m$', xy=(-0.005483, 0.01), xytext=(-0.0015, 0.009),arrowprops=dict(arrowstyle="->",color='k'))

# x = np.linspace(-9e-3,9e-3,1000)
# plt.plot(x,intensity(x),c='orange',label='Gaussian fit')
# plt.axvline(-0.0056,ymin=0.,ymax=0.16,linestyle='--',c='r',alpha=0.8,label='Beam width accounting fitting')
# plt.axvline(0.0056,ymin=0.,ymax=0.16,linestyle='--',c='r',alpha=0.8)
# plt.annotate('$2\omega\\Rightarrow\omega=0.0056m$', xy=(0.0056, 0.004), xytext=(-0.0015, 0.003),arrowprops=dict(arrowstyle="->",color='r'))
# plt.annotate('$2\omega\\Rightarrow\omega=0.0056m$', xy=(-0.0056, 0.004), xytext=(-0.0015, 0.003),arrowprops=dict(arrowstyle="->",color='r'))

# plt.legend(loc='best')

##########
# TASK 4 #
##########

# distance razor-lens 5.66cm

##########
# TASK 3 #
##########

# plt.figure(figsize=(10,8))
# plt.title('Beam radius vs Distance')
# plt.xlabel('Distance (m)')
# plt.ylabel('Radii (m)')

# P_0 = 4.06 #mW      # we check the diameter for intensity =4.06*(1-np.exp(-2))=3.51 mW
# iris_diameter = 0.425 #cm
# distance_0 = 23. #cm
# d_0_i = 23./2.54 #inch

# distance = np.arange(d_0_i,d_0_i+9,1)#inch relative to the lens frame increments of 1 inch
# distance = distance * 2.54 # to cm
# distance = distance - 5.2 + 0.7 # - distance from frame to razorblade + distance from irisframe to iris

# iris_diam = np.array([0.425, 0.5, 0.525, 0.585, 0.6, 0.67, 0.701, 0.75, 0.805, 0.855]) # cm
# radii = (iris_diam/2)*10**(-2)
# distance = distance*10**(-2)

# print(distance)
# print(radii)

# P = np.polyfit(distance,radii,1)
# plt.plot(distance,np.polyval(P,distance),c='r',label='$y=0.0009x\ \ \ R^2=0.99323$')
# plt.scatter(distance,radii,marker='x',label='data')
# plt.legend(loc='best')

# result = scis.linregress(distance,radii)
# print(result.slope, (result.rvalue)**2)
# theta = wavelength / (np.pi * result.slope) # 0.0002368321704047417


##########
# TASK 5 #
##########
# 80% 15.5
# 50% 16.3
# 20% 17.1
x_0 = 16.3e-5
w = 1.35e-5

plt.figure(figsize=(10,8))
P_0 = 5120*10**(-3) #mW 
x = np.array([1.12947368e-04, 1.20421053e-04, 1.27894737e-04, 1.35368421e-04, 1.42842105e-04, 1.50315789e-04, 1.57789474e-04, 1.65263158e-04, 1.72736842e-04, 1.80210526e-04, 1.87684211e-04, 1.95157895e-04, 2.02631579e-04, 2.10105263e-04, 2.17578947e-04, 2.25052632e-04, 2.32526316e-04, 2.40000000e-04])
y = np.array([9.54, 10.51, 11.52, 11.84, 13.54, 15.20, 21.30, 58.5, 282.0, 915, 2015, 3820, 4683, 4999, 5100, 5115, 5126, 5127])[::-1] #microW
y = y*10**(-3)

def dy_cinco_discreto(x,y):
    h = abs(x[4]-x[0])/5
    return (y[0]-8.*y[1]+8.*y[3]-y[4])/(12.*h)

dy = []
for i in range(len(x)-6):
    dy.append(dy_cinco_discreto(x[i:i+5],y[i:i+5]))
dy = dy/(0.25*np.max(dy))


def P_normalized(u):
    # def erf(s):
    #     return mth.erf(s)
    def erf(s):
        return 2/(np.sqrt(np.pi))*quad(np.vectorize(lambda t: np.exp(-t**2)),0,s)[0]
    erf = np.vectorize(erf)
    
    return 0.5*(1-erf((u-x_0)/w)) #0.0000135

def P_normalized_func(u):
    a_1 = -1.5954086
    a_3 = -7.3638857e-2
    a_5 =  6.4121343e-4
    s = np.sqrt(2)*(u-x_0)/w
    return 1/(1+np.exp(-(a_1*s + a_3*s**3 + a_5*s**5)))

x = np.delete(x,-1)
x = np.delete(x,-1)
x = np.delete(x,-1)

y = np.delete(y,-1)
y = np.delete(y,-1)
y = np.delete(y,-1)

xo = np.linspace(x[0],x[-1],1000)

plt.scatter(x,y,marker='x',label='data')
plt.scatter([15.5e-5,16.3e-5,17.1e-5],[0.8*P_0,0.5*P_0,0.2*P_0],c='r',label='20,50,80% total power')

plt.plot(xo,P_0*P_normalized(xo),label='data fit: erf-approximation')
plt.plot(xo,P_0*P_normalized_func(xo),label='data fit: formula-approximation')

plt.axhline(0.8*P_0,linestyle='--',alpha=0.5)
plt.axhline(0.5*P_0,linestyle='--',alpha=0.5)
plt.axhline(0.2*P_0,linestyle='--',alpha=0.5)

plt.axvline(15.5e-5,linestyle='--',alpha=0.5)
plt.axvline(16.3e-5,linestyle='--',alpha=0.5)
plt.axvline(17.1e-5,linestyle='--',alpha=0.5)

plt.legend(loc='best')

plt.figure(figsize=(10,8))
def deriv_fit(u):
    return 1/(w*np.sqrt(2))*(np.exp(-(((u-x_0)/w))**2))
plt.scatter(x[2:14],dy,marker='x',label='data derivative')
plt.plot(xo,deriv_fit(xo),label='data derivative fit')
plt.legend(loc='best')


###
#    TASK 6
###

# R = 3.85 #cm

# DeltaV = 2.36875 #V
# V80 = 0.8*DeltaV
# V20 = 0.2*DeltaV

# x_0 = -132e-6 #s (us)
# x_1 = -92e-6 # s when V20
# x_2 = 148e-6 # s

# DeltaX20 = abs(x_0 - x_1)
# DeltaX80 = abs(x_0 - x_2)

# print(V80,V20)
# print(abs(x_1-x_2))
plt.show()

###
#    TASK 7
###

# DeltaZ = 0.0254
# w_1 = 0.003750
# w_2 = 0.004025

# num = wavelength * DeltaZ**2 * (w_1**2 +w_2**2 + 2*np.sqrt(w_1**2*w_2**2-((wavelength*DeltaZ)/np.pi)**2))
# den = np.pi * (w_1**4 + w_2**4 - 2*w_1**2*w_2**2 + 4*((wavelength*DeltaZ)/np.pi)**2)

# z_0 = num/den
# w_0 = np.sqrt(z_0*wavelength/np.pi)

# print(z_0,w_0)