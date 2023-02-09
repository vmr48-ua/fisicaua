# -*- coding: utf-8 -*-
""" 
* MÉTODOS NUMÉRICOS Y COMPUTACIÓN * 2022/2023 * GRADO EN FÍSICA *
  @JulioMulero @JoseVicente 
  PRÁCTICA 1                                                      """


# En primer lugar, se cargan las librerías necesarias.

import numpy as np

''' Numpy es una extensión de Python que le dota de la capacidad matemática 
necesaria para operar con cualquier dato numérico (vectores, matrices, arrays).
Incorpora operaciones básicas como la suma o la multiplicación, y otras 
mucho más complejas como la transformada de Fourier o el álgebra lineal.  '''

import scipy as sci

''' Scipy es una librería de herramientas numéricas para Python que se 
distribuye libremente. El módulo scipy confiere al lenguaje general Python 
gran capacidad de cálculo numérico. 
Contiene módulos para optimización, álgebra lineal, integración, interpolación,
 funciones especiales, resolución de ecuaciones diferenciales ordinarias, FFT, 
procesamiento de señales y de imágen, y otros muchos aspectos. '''

import matplotlib.pyplot as plt

''' Matplotlib es el módulo de dibujo de gráficas 2D y 3D que vamos a utilizar,
aunque no es el único existente. Matplotlib tiene multitud de librerías de las 
cuales nosotros, por semejanza a Matlab, utilizaremos pyplot. '''

from mpl_toolkits.mplot3d import Axes3D

''' mpl_toolkits es otro módulo cuya librería mplot3d contiene la función 
    Axes3D que usaremos para hacer representaciones gráficas en 3D.  '''

###################################################

'''
INTRODUCCIÓN 

Un método numérico es un procedimiento mediante el cual se obtiene, casi
siempre de manera aproximada, la solución de ciertos problemas realizando
cálculos puramente aritméticos y lógicos y usando generalmente un ordenador.
Un tal procedimiento consiste de una lista finita de instrucciones precisas
que especifican una secuencia de operaciones algebraicas y lógicas (algoritmo),
que producen o bien una aproximación de la solución del problema (solución
numérica) o bien un mensaje. La eficiencia en el cálculo de dicha aproximación
depende, en parte, de la facilidad de implementación del algoritmo y de las
características especiales y limitaciones de los instrumentos de cálculo (los
computadores). En general, al emplear estos instrumentos de cálculo se
introducen errores llamados de redondeo. 
'''


# OPERACIONES BÁSICAS

2+3
2-3
2*3

2**3
37 + 45
12345678 * 234895764
9876 ** 31
-25.3 + 12.1

# En algunas versiones el siguiente valor no vale 0.5:
1/2
2/3
# Hay que poner:
1./2
1/2.
2./3
type(2/3)
type(2)

1e3
79.8**2
79.8**20
79.8**200

# Coma flotante: 
1.0 - 0.9 - 0.1

# Para declarar variables algunos lenguajes utilizan la disciplina estática2. Por ejemplo, en Java, tenemos
# que explícitamente definir el tipo de la variable: int a,b,c, String myName, etc. Otros lenguajes, como
# Python, utilizan la disciplina dinámica para declarar variables; en otras palabras, las variables asumen
# automáticamente el tipo del valor que se les asigna:

a = 8
a
type(a)
a = 3.14
type(a)
a = 3.14+2j
type(a)
np.real(a)

type(True)

# Trabajar sólo con números (int, long, float) es algo limitado.  Necesitamos trabajar con conjuntos de
# valores. En la distribución básica de Python coexisten listas y tuplas.

# Las tuplas en Python son una secuencia de valores que pueden 
# ser de cualquier tipo, cadenas, enteros, floats, etc. Son objetos inmutables.

tupla1 = ("Python", True, "Python", "R", 5)
tupla2 = "Python", True, "Python", "R", 5

tupla1 == tupla2

# Extraer elementos:
    
tupla1[0]
tupla1[0:2]

# ¿Cambiar el valor de los elementos?

tupla1[0] = "dos"

# Recorrer una tupla:

for elem in tupla1:
    print(elem)

# Funciones sobre tuplas:
    
tupla1.count(True)
tupla1.count("Python")

tupla1.index(True)
tupla1.index("Python")
tupla1.index("Latex")

# Operaciones con tuplas:
    
tupla3 = 'uno', 'dos', 'tres', 'cuatro', 'cinco'

tupla1+tupla3
2*tupla1


#Las listas en Python son una secuencia de valores que pueden 
#ser de cualquier tipo, cadenas, enteros, floats, etc. Son objetos mutables.

u = [1,2,3]
v = [0,1,2]
lista1 = ['uno', 'dos', 'tres', 'cuatro', 'cinco']
lista2 = ['uno', 20 , 5.5 , [10, 15], 'cinco']

# Extraer elementos:
    
lista1[2]
lista1[0:3]
lista1[-1]

# Cambiar el valor de los elementos:
    
tercero = lista1[2]
print(tercero)
lista1[2] = 'tercero'

# Recorrer una lista:
    
for elem in lista1:
    print(elem)
    
lista3 = [1, 2, 3, 4, 5]
for i in range(len(lista3)):
    lista3[i]+=5

print(lista3)

# Funciones sobre listas:

lista1.count("uno")

lista1.index("dos")

lista3 = [1, 2, 3, 4, 5]
lista3.insert(1,'Hola')
print(lista3)

lista1 = ['uno', 'dos', 'tres', 'cuatro', 'cinco']
lista1.append("uno más")
print(lista1)

lista4 = ['cde', 'fgh', 'abc', 'klm', 'opq']
lista4.sort()

lista5 = [3, 5, 2, 4, 1]
lista5.sort()

lista3 = [1, 2, 3, 4, 5]
lista3.reverse()
print(lista3)

lista1 = ['uno', 'dos', 'tres', 'cuatro', 'cinco']
lista1.index('tres')

lista1 = ['uno', 'dos', 'tres', 'cuatro', 'cinco']
lista1_modificada = lista1.pop(2)
print(lista1)
print(lista1_modificada)

lista1 = ['uno', 'dos', 'tres', 'cuatro', 'cinco']
lista1_modificada = lista1.pop()
print(lista1)
print(lista1_modificada)


lista6 = [5, 3, 2, 4, 1]
print(len(lista6))
print(min(lista6))
print(max(lista6))
print(sum(lista6))

# Operaciones con listas:
    
u+v
2*u
 

# Sin embargo, ni las listas ni las tuplas son adecuadas para trabajar con vectores de números
# conservando las operaciones de Rn. Los arrays son objetos propios de la librería
# numpy. Existen varias funciones que sirven para definir arrays (por ejemplo, arange o linspace).

x = np.array([1, 2, 3])
y = np.array([-1, 0, 7])
x[0:2]
x + y
x * y
z = np.linspace(0,1)
len(z)
x+z

round(0.8564)
round(0.8564,1)
round(0.8534,2)

np.sin(z)

round(z)

Round=np.vectorize(lambda x: round(x))
Round(z)

np.arange(7)
np.arange(7.5)
x=np.linspace(0,1)
len(x)

# Extraer elementos:

A = np.array([[1, 2, 3],[-1, 0, 7],[0, -2, 3]])

A[0,0] #Python empieza a contar desde 0 hasta n-1.
A[1,:]
A[1]
A[:,2]
A[:,[0,2]]
A[:,:2]

# Los arrays sirven también para trabajar con polinomios:

P1=np.array([3,-2,1]) # En potencias decrecientes (3x**2-2x+1).
Q1=np.array([2,1]) # En potencias decrecientes (2x+1).

P = np.poly1d(P1) 
print(P)

np.polyadd(P1,Q1) # En potencias decrecientes (3x**2+2).

np.polysub(P1,Q1) # En potencias decrecientes (3x**2-4x).
np.polymul(P1,Q1) # En potencias decrecientes (6x**3-x**2+1).
np.polydiv(P1,Q1) # En potencias decrecientes cociente (1.5x-1.75) y resto (2.75).

2*P1 # Producto por un escalar. 

P1+2 # Ojo para la suma no funciona. Deberíamos sumar el array que representa 
     # al polinomio igual a 2.
np.polyadd(P1,np.array([2]))

np.roots(P1) # Raíces
np.roots(Q1) # Raíces

np.polyval(P1,0)

x = np.linspace(-1,1,100)
np.polyval(P1,x) # Evaluación de un polinomio

# Además, dado un conjunto de raíces, se puede construir el polinomio
# que tiene dichas raíces. Tenemos que usar polynomial (npol).

import numpy.polynomial.polynomial as npol

raices = np.array([0,1])
npol.polyfromroots(raices) # El polinomio es x**2-x, atención al orden de los coeficientes. 
npol.polyfromroots(raices)[::-1]

np.poly1d(raices,r=True).c # Otra forma


# Matrices:

A = np.array([[1, 2, 3],[-1, 0, 7],[0, -2, 3]])

A.T

np.linalg.det(A)
np.linalg.matrix_rank(A)
np.linalg.inv(A)
np.linalg.eig(A)

# Producto de vectores y matrices:

a2 = np.array([0,1,2])
b2 = np.array([-1,0,6])

a2*b2
np.dot(a2,b2)
A*a2
np.dot(A,a2)

# Sistemas de ecuaciones:

A = np.array([[1, 2, 3],[-1, 0, 7],[0, -2, 3]])
b = np.array([1, 0, 3])

np.linalg.solve(A, b)

# Números aleatorios:

np.random.rand(3)

np.random.seed(0)
np.random.rand(3)

np.random.rand(3,1)

B=np.random.randint(-1,3,[2,3])
B2=np.random.uniform(10,15,[2,2])
print(B)

# Usando arrays podemos elaborar gráficas:
    
x = np.linspace(-2,2)

plt.plot(x, x**2 - 1)

plt.grid()
plt.plot(x, np.sin(x))
plt.plot(x, np.cos(x))

x = np.linspace(-np.pi, np.pi)
plt.plot(x, np.sin(x), label='seno')
plt.plot(x, np.cos(x), label='coseno')
plt.grid()
plt.xlabel('Tiempo [s]')
plt.ylabel('Amplitud [V]')
plt.title('Funciones trigonometricas')
plt.legend(loc=2)

# Funciones y gráficas:

def mifuncion(x):
    return x**2

mifuncion(2)
mifuncion(a2)

x0=np.linspace(-2,2,100)
y0=mifuncion(x0)
plt.plot(x0,y0)


plt.figure(figsize=(5,2))
plt.subplot(1,2,1)
plt.plot(x0,y0,'--',color='red')
plt.title('Mi primera subgrafica')
plt.xlabel('Eje de abcisas')
plt.ylabel('Eje de ordenadas')
plt.subplot(1,2,2)
plt.plot(x0,-y0,'-',color='blue')
plt.title('Mi segunda subgrafica')
plt.xlabel('Eje de abcisas')
plt.ylabel('Eje de ordenadas')
plt.show()

plt.figure()
plt.plot(x0,2*x0,'--',color='red')
plt.plot(x0,y0,'--',color='red')
plt.fill_between(x0,2*x0,y0,color='red',alpha=0.5)
plt.show()

# Gráficas 3D:

fig=plt.figure()
ax=Axes3D(fig)
x = np.linspace(-1,1,10);
y = np.linspace(-1,1,10);
X,Y =np.meshgrid(x, y);
Z=X**2 +Y**2
ax.plot_surface(X, Y, Z)
plt.show()

# Integración:

import scipy.integrate

aprox=sci.integrate.quad(mifuncion,0,1)


# Bucles:

edad=16
if edad < 18:
    print("Es usted menor de edad")
else:
    print("Es usted mayor de edad")
print("¡Hasta la próxima!")

range(1,2)
    
for i in range(24):
    print(i)
    
for i in range(1,3):
    print(i)
    
suma=0
for i in range(1,21):
    suma=suma+i
print(suma)

print((20*21)/2)


suma=0
i=1
while suma<=200:
    suma=suma+i
    i=i+1
print(suma)

# Dado un N calcule el sumatorio X=sum_{k=1}^{N}1/k:

N = 10
range(1,N)
[k for k in range(1,N)]
[1.0/k for k in range(1,N)]
sum([1.0/k for k in range(1,N)])
N = 100
sum([1.0/k for k in range(1,N)])

N = 10
np.arange(0.5, N)
1/np.arange(1.0, N)
sum(1/np.arange(1.0, N))
N = 100
sum(1/np.arange(1.0, N))


eps = 1.0
while 1.0 != 1.0 + eps:
    print('...',eps)
    eps = eps/2.0
print('final eps:', eps)

1.0 + eps
(1.0 + eps) == 1.0


