import numpy as np
import matplotlib.pyplot as plt

"""
En este experimento, la variable que correspondería al potencial
químico en el sistema de esferas vibrantes puede interpretarse
como la altura en la que la densidad de esferas es aproximadamente
la mitad de su valor máximo.

En Fermi-Dirac, el potencial químico se asocia con el nivel de
energía en el que la probabilidad de ocupación de un estado
es 1/2 a una temperatura dada. Análogamente en nuestro sistema
la "altura de media ocupación" puede verse como el nivel donde
las esferas están en equilibrio entre la tendencia de ocupar
posiciones más bajas (debido a la gravedad)y la tendencia a
distribuirse en mayores alturas (debido a la vibración, que
simula un efecto térmico).
"""

# Sinusoidal, 10Hz, 30s, 0.8A
altura = np.array([0,  1,   2,   3,   4,   5,   6,   7,   8,   9,  10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,   25])*1e-3 #mm
n1 = np.array([    54, 69, 128, 160, 184, 211, 263, 305, 357, 412, 521, 523, 592, 614, 652, 704, 736, 754, 754, 795, 809, 778, 709, 714, 717, 690])[::-1]
n2 = np.array([    35, 40,  81, 114, 139, 165, 230, 318, 297, 334, 418, 501, 525, 602, 624, 650, 720, 728, 722, 754, 804, 790, 772, 746, 749, 710])[::-1]
n3 = np.array([    28, 50,  70,  85,  91, 176, 208, 235, 304, 350, 391, 465, 488, 561, 568, 646, 709, 746, 742, 758, 774, 782, 737, 695, 709, 721])[::-1]
n = (n1+n2+n3)/3

plt.figure()
plt.scatter(altura, n1, marker='x')
plt.scatter(altura, n2, marker='x', alpha=0.5)
plt.scatter(altura, n3, marker='x', alpha=0.5)
plt.plot(altura, n)
plt.show()

# Cuadrada, 10Hz, 20s, 0.8A
altura = np.array([0,  1,  2,   3,   4,   5,   6,   7,   8,   9,   10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,  25])*1e-3 #mm
n = np.array([      ])

# Triangular, 10Hz, 20s, 0.8A
altura = np.array([0,  1,  2,   3,   4,   5,   6,   7,   8,   9,   10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,  25])*1e-3 #mm
n = np.array([      ])

# Sinusoidal, 20Hz, 20s, 0.8A
altura = np.array([0,  1,  2,   3,   4,   5,   6,   7,   8,   9,   10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,  25])*1e-3 #mm
n = np.array([     ])
# plt.figure()
# plt.scatter(altura,n,marker='x')
# plt.show()

# Sinusoidal, 40Hz, 20s, 0.8A
altura = np.array([ 0,  1,  2,   3,   4,   5,   6,   7,   8,   9,   10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,  25])*1e-3 #mm
n = np.array([     54, 92, 115, 175, 194, 226, 254, 274, 322, 331, 354, 391, 400, 407, 430, 455, 460, 488, 503, 502, 470, 446, 434, 450, 426, 458])[::-1]
plt.figure()
plt.scatter(altura,n,marker='x')
plt.show()

# Sinusoidal, 40Hz, 20s, 0.6A
altura = np.array([ 0, 1,  2,  3,  4,   5,  6,  7,   8,   9,  10,  11,  12,  13,  14,  15,  16,  17,  18,  19,  20,  21,  22,  23,  24,   25])*1e-3 #mm
n = np.array([      5, 24, 32, 35, 54, 74, 96, 144, 144, 207, 235, 279, 293, 328, 349, 352, 401, 408, 401, 417, 396, 398, 388, 405, 403, 409])[::-1]
plt.figure()
plt.scatter(altura,n,marker='x')
plt.show()