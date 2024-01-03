#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Dec  7 16:27:42 2022

@author: Laura
"""

#Definiendo gravedad
def tiro(z,t,par):
    z1,z2,z3,z4=z  
    dzdt=[z3,z4, 
          -gr*mati*z1/np.sqrt(z1**2+z2**2)**3,
          -gr*mati*z2/np.sqrt(z1**2+z2**2)**3]
    return dzdt

#Llamada a odeint que resuelve las ecuaciones
