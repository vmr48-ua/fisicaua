import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as scis
from statistics import linear_regression
from scipy.integrate import quad
import math as mth

distance = 25 # microns delta

##########
# TASK 1 #
##########
wavelength = 632.8e-9
fringes = np.array([78, 79, 78, 74]) # n of fringes
av_fringes = np.average(fringes)
# convering_factor = 2*distance/wavelength
# print(convering_factor) # should be close to 1 micron

##########
# TASK 2 #
##########
#  GREEN  #
wavelength = 532e-9 # green
fringes = np.array([94, 96, 78, 90, 91])
av_fringes = np.average(fringes)

#  RED  #
wavelength = 635e-9
fringes = np.array([92, 80, 70, 89, 71])
av_fringes = np.average(fringes)

##########
# TASK 3 #
##########
deg = 10.0*np.pi/180
thickness = 0.600e-2 # cm
wavelength = 632.8e-9

fringes = np.array([115, 113, 110, 97, 87])
av_fringes = np.average(fringes)

num = (2*thickness - av_fringes*wavelength)*(1-np.cos(deg))
den = 2*thickness*(1-np.cos(deg))-av_fringes*wavelength
n = num/den
print(n)


##########
# TASK 5 #
##########

data = np.array([246,533,822])*1e-6 #minimums