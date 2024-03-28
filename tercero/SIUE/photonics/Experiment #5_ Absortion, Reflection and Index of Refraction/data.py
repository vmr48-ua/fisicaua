import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as scis
from statistics import linear_regression
from scipy.integrate import quad
import math as mth

# lasers: 405, 450, 532

##################
# TASK 1 - 1% Pr # .465cm thick
##################

# zeroed with spotlight

# 532 nm 
# A
# 4 datapoints at different atenuations
before_greenPr = np.array([4.141,3.176,1.939,1.091, 0.4834]) #mW before sample
after_greenPr =  np.array([3.874,2.877,1.781,0.9804,0.4492]) #mW after sample

plt.figure()
plt.title('532 nm Presdimium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_greenPr,after_greenPr,marker='x')
P = np.polyfit(before_greenPr,after_greenPr,1)
plt.plot(x,np.polyval(P,x),c='r')

# B did it at 5º and when put at 0.2º we got the same result, same result at 6º
pow_greenPR = np.array([4.202, 2.977, 2.111,  0.977,  0.5171]) #mW
deg_greenPR = np.array([0.1582,0.1114,0.07865,0.03659,0.01952]) #mW

#################
# TASK 1 - 1% Pr # .465cm thick
##################

# zeroed with spotlight

# 450 nm 
# A
# 4 datapoints at different atenuations
before_bluePr = np.array([4.037,2.822,2.088, 1.077, 0.5127]) #mW before sample
after_bluePr =  np.array([1.587,1.107,0.8091,0.4128,0.2019]) #mW after sample

plt.figure()
plt.title('450 nm Presdimium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_bluePr,after_bluePr,marker='x')
P = np.polyfit(before_bluePr,after_bluePr,1)
plt.plot(x,np.polyval(P,x),c='r')

# B
pow_bluePR = np.array([0.5094, 1.010,  1.903,  2.975, 3.950]) #mW
deg_bluePR = np.array([0.01746,0.03606,0.06851,0.1076,0.1423]) #mW

##################
# TASK 1 - 1% Pr # .465cm thick
##################

# zeroed with spotlight

# 405 nm 
# A
# 4 datapoints at different atenuations
before_purplePr = np.array([0.5417,1.012, 1.483,1.995,2.665]) #mW before sample
after_purplePr =  np.array([0.4767,0.8641,1.247,1.727,2.245]) #mW after sample

plt.figure()
plt.title('405 nm Presdimium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_purplePr,after_purplePr,marker='x')
P = np.polyfit(before_purplePr,after_purplePr,1)
plt.plot(x,np.polyval(P,x),c='r')

# B
pow_purplePR = np.array([0.5630,1.423,  1.000,  2.060,  2.657, ]) #mW
deg_purplePR = np.array([0.01911,0.05261,0.03572,0.07755,0.1014,  ]) #mW

##################
# TASK 1 - 2% Eu # 0.330 cm thick
##################

# 532 nm 
# A
# 4 datapoints at different atenuations
before_greenEU = np.array([0.5134,0.9346,2.175,3.112,4.075]) #mW before sample
after_greenEU =  np.array([0.3857,0.6984,1.637,2.331,3.056]) #mW after sample

plt.figure()
plt.title('532 nm Europium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_greenEU,after_greenEU,marker='x')
P = np.polyfit(before_greenEU,after_greenEU,1)
plt.plot(x,np.polyval(P,x),c='r')

# B at 3.4º
pow_greenEU = np.array([4.088, 2.967, 2.090, 1.193,  0.5361]) #mW
deg_greenEU = np.array([0.2884,0.2080,0.1459,0.08225,0.03720]) #mW

##################
# TASK 1 - 2% Eu # 0.330 cm thick
##################

# 450 nm 
# A
# 4 datapoints at different atenuations
before_blueEU = np.array([4.181,3.135,2.012,1.059, 0.5395]) #mW before sample
after_blueEU =  np.array([2.777,2.080,1.340,0.7027,0.3577]) #mW after sample

plt.figure()
plt.title('450 nm Europium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_blueEU,after_blueEU,marker='x')
S = np.polyfit(before_blueEU,after_blueEU,1)
plt.plot(x,np.polyval(S,x),c='r')

# B at 5.0º
pow_blueEU = np.array([0.5372,1.009,  1.939, 2.848, 4.009]) #mW
deg_buleEU = np.array([0.2992,0.05495,0.1105,0.1621,0.2351]) #mW

##################
# TASK 1 - 2% EU # 0.330cm thick
##################

# zeroed with spotlight

# 405 nm 
# A
# 4 datapoints at different atenuations
before_purpleEU = np.array([0.5500,0.9736,1.520, 2.139, 2.795]) #mW before sample
after_purpleEU =  np.array([0.2258,0.4350,0.6494,0.9570,1.218]) #mW after sample

plt.figure()
plt.title('405 nm Europium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_purpleEU,after_purpleEU,marker='x')
P = np.polyfit(before_purpleEU,after_purpleEU,1)
plt.plot(x,np.polyval(P,x),c='r')

# B
pow_purpleEU = np.array([2.836, 2.040, 1.508, 1.021,  0.5243]) #mW
deg_purpleEU = np.array([0.2061,0.1448,0.1105,0.07550,0.03687]) #mW

plt.show()