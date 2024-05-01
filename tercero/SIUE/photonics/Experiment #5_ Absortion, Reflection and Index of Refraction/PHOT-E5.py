import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as scis
from statistics import linear_regression
from scipy.integrate import quad
import math as mth

# lasers: 405, 450, 532
n_pr = 1.9508
n_eu = 0.62519
n_ai = 1.0003

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
plt.title('532 nm Praseodymium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_greenPr,after_greenPr,marker='x')
P = np.polyfit(before_greenPr,after_greenPr,1)
result = scis.linregress(before_greenPr,after_greenPr)
plt.plot(x,np.polyval(P,x),c='r',label='y={}x{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))
plt.legend(loc='best')
plt.xlabel('$P_\\text{in}$ (mW)')
plt.ylabel('$P_\\text{out}$ (mW)')

# B did it at 5º and when put at 0.2º we got the same result, same result at 6º
pow_greenPR = np.array([4.202, 2.977, 2.111,  0.977,  0.5171]) #mW
deg_greenPR = np.array([0.1582,0.1114,0.07865,0.03659,0.01952])

plt.figure()
result = scis.linregress(pow_greenPR, deg_greenPR)
plt.scatter(pow_greenPR,deg_greenPR, color='green',marker='x')
P = np.polyfit(pow_greenPR,deg_greenPR,1)
x = np.linspace(0,4.3,1000)
plt.plot(x,np.polyval(P,x),label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))

# Setting the title and labels
plt.title('Reflection Coefficient at 5 degrees 532 nm Praseodymium')
plt.xlabel('$P_0$ (mW)')
plt.ylabel('$P_f$ (mW)')
plt.legend(loc='best')

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
plt.title('450 nm Praseodymium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_bluePr,after_bluePr,marker='x')
P = np.polyfit(before_bluePr,after_bluePr,1)
result = scis.linregress(before_bluePr,after_bluePr)
plt.plot(x,np.polyval(P,x),c='r',label='y={}x{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))
plt.legend(loc='best')
plt.xlabel('$P_\\text{in}$ (mW)')
plt.ylabel('$P_\\text{out}$ (mW)')

# B 
deg_bluePR = np.array([0.5094, 1.010,  1.903,  2.975, 3.950]) #mW
pow_bluePR = np.array([0.01746,0.03606,0.06851,0.1076,0.1423]) #mW

plt.figure()
result = scis.linregress(deg_bluePR, pow_bluePR)
plt.scatter(deg_bluePR, pow_bluePR, color='green',marker='x')
P = np.polyfit(deg_bluePR, pow_bluePR,1)
x = np.linspace(0,4,1000)
plt.plot(x,np.polyval(P,x),label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))

# Setting the title and labels
plt.title('Reflection Coefficient at 5 degrees 450 nm Praseodymium')
plt.xlabel('$P_0$ (mW)')
plt.ylabel('$P_f$ (mW)')
plt.legend(loc='best')


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
plt.title('405 nm Praseodymium')
x = np.linspace(0.3,5,1000)
plt.scatter(before_purplePr,after_purplePr,marker='x')
P = np.polyfit(before_purplePr,after_purplePr,1)
result = scis.linregress(before_purplePr,after_purplePr)
plt.plot(x,np.polyval(P,x),c='r',label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))
plt.legend(loc='best')
plt.xlabel('$P_\\text{in}$ (mW)')
plt.ylabel('$P_\\text{out}$ (mW)')

# B 
deg_purplePR = np.array([0.5630,1.423,  1.000,  2.060,  2.657, ]) #mW
pow_purplePR = np.array([0.01911,0.05261,0.03572,0.07755,0.1014,  ]) #mW

plt.figure()
result = scis.linregress(deg_purplePR, pow_purplePR)
plt.scatter(deg_purplePR, pow_purplePR, color='green',marker='x')
P = np.polyfit(deg_purplePR, pow_purplePR,1)
x = np.linspace(0,2.7,1000)
plt.plot(x,np.polyval(P,x),label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))

# Setting the title and labels
plt.title('Reflection Coefficient at 5 degrees 405 nm Praseodymium')
plt.xlabel('$P_0$ (mW)')
plt.ylabel('$P_f$ (mW)')
plt.legend(loc='best')

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
result = scis.linregress(before_greenEU,after_greenEU)
plt.plot(x,np.polyval(P,x),c='r',label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))
plt.legend(loc='best')
plt.xlabel('$P_\\text{in}$ (mW)')
plt.ylabel('$P_\\text{out}$ (mW)')

# B 
deg_greenEU = np.array([4.088, 2.967, 2.090, 1.193,  0.5361]) #mW
pow_greenEU = np.array([0.2884,0.2080,0.1459,0.08225,0.03720]) #mW

plt.figure()
result = scis.linregress(deg_greenEU, pow_greenEU)
plt.scatter(deg_greenEU, pow_greenEU, color='green',marker='x')
P = np.polyfit(deg_greenEU, pow_greenEU,1)
x = np.linspace(0,4.1,1000)
plt.plot(x,np.polyval(P,x),label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))

# Setting the title and labels
plt.title('Reflection Coefficient at 5 degrees 532 nm Europium')
plt.xlabel('$P_0$ (mW)')
plt.ylabel('$P_f$ (mW)')
plt.legend(loc='best')

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
x = np.linspace(0,4.2,1000)
plt.scatter(before_blueEU,after_blueEU,marker='x')
S = np.polyfit(before_blueEU,after_blueEU,1)
result = scis.linregress(before_blueEU,after_blueEU)
plt.plot(x,np.polyval(S,x),c='r',label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))
plt.legend(loc='best')
plt.xlabel('$P_\\text{in}$ (mW)')
plt.ylabel('$P_\\text{out}$ (mW)')

# B 
deg_blueEU = np.array([0.5372,1.009,  1.939, 2.848, 4.009]) #mW
pow_blueEU = np.array([0.02992,0.05495,0.1105,0.1621,0.2351]) #mW

plt.figure()
result = scis.linregress(deg_blueEU, pow_blueEU)
plt.scatter(deg_blueEU, pow_blueEU, color='green',marker='x')
P = np.polyfit(deg_blueEU, pow_blueEU,1)
x = np.linspace(0,4.1,1000)
plt.plot(x,np.polyval(P,x),label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))

# Setting the title and labels
plt.title('Reflection Coefficient at 5 degrees 450 nm Europium')
plt.xlabel('$P_0$ (mW)')
plt.ylabel('$P_f$ (mW)')
plt.legend(loc='best')

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
result = scis.linregress(before_purpleEU,after_purpleEU)
plt.plot(x,np.polyval(P,x),c='r',label='y={}x{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))
plt.legend(loc='best')
plt.xlabel('$P_\\text{in}$ (mW)')
plt.ylabel('$P_\\text{out}$ (mW)')

# B
deg_purpleEU = np.array([2.836, 2.040, 1.508, 1.021,  0.5243]) #mW
pow_purpleEU = np.array([0.2061,0.1448,0.1105,0.07550,0.03687]) #mW

plt.figure()
result = scis.linregress(deg_purpleEU, pow_purpleEU)
plt.scatter(deg_purpleEU, pow_purpleEU, color='green',marker='x')
P = np.polyfit(deg_purpleEU, pow_purpleEU,1)
x = np.linspace(0,2.9,1000)
plt.plot(x,np.polyval(P,x),label='y={}x+{}    R²={}'.format(np.round(result.slope,4),np.round(result.intercept,2),np.round(result.rvalue**2,6)))

# Setting the title and labels
plt.title('Reflection Coefficient at 5 degrees 405 nm Europium')
plt.xlabel('$P_0$ (mW)')
plt.ylabel('$P_f$ (mW)')
plt.legend(loc='best')

plt.show()