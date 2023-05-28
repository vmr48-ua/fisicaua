import numpy as np
import matplotlib.pyplot as plt
from numpy import pi,log,cos,sin
from matplotlib.patches import Circle

############################################################################################
#                                                                                          #
#                   ELECTROMAGNETISMO I -- PRÁCTICA 1: CAMPOS ELÉCTRICOS                   #
#                       Víctor Mira Ramírez y Lucas Peydró Ferrando                        #
#                                                                                          #
############################################################################################

# En esta práctica se calcularán los potenciales y campos eléctricos debidos a
# distribuciones de cargas puntuales y distribuciones contínuas. Usaremos dos métodos:
# calculando el campo eléctrico como la superposición de los campos eléctricos debidos a las
# cargas puntuales y resolviendo de forma numérica la ecuación de Poisson.

############################################################################################
#          1 - REPRESENTACIÓN DE UNA FUNCIÓN DE DOS VARIABLES EN DOS DIMENSIONES           #
############################################################################################

'''
Primeramente, definimos un área cuadrada de lado L, tomando L0 como el punto más abajo a la
izquierda. Dentro de este área crearemos una matriz con puntos equidespaciados en función de
h. Usaremos h como una variable para la resolución, ya que variando el denominador de la
misma,variamos el número de subintervalos que tendrá nuestra área. Para variar dicho
denominador, utilizamos la variable N. N describe el número de puntos en un eje y por tanto,
incluye los extremos del eje, por lo que debemos restarle uno para obtener el número de
subintervalos que deseamos para calcular h.
'''

L  = 4.     # Es importante el punto para que sea un número en coma flotante y no un entero
L0 = -L/2   # Colocando L0 aquí, estamos centrando el área en el origen de coordenadas (0,0)
N  = 36
h  = L/(N-1)

'''
Ahora definiremos dos vectores x,y que generarán nuestros ejes. Como ya hemos dicho, estos
vectores serán de tamaño N, que es la variable que simboliza este tamaño.

También daremos uso a la funicón 'meshgrid' de 'numpy' para crear un espacio vectorial con
los ejes que acabamos de crear. Gracias a esta función podemos generar un plano
bidimensional sobre el cual podremos representar funciones vectoriales.
'''

x = np.linspace(L0,L0+L,N)
y = np.linspace(L0,L0+L,N)
X,Y = np.meshgrid(x,y)

#       1.1 - Representación en 2D

'''
Para la representación de dichas funciones, creamos una matriz M de tamaño NxN para que
quepadentro de nuestro plano. Podemos modificar qué función representamos jugando con X,Y.
'''

M=np.zeros((N,N))
M=X+4*(Y)                                                      # Función de ejemplo z = x+4y

'''
Ahora, pasamos a representar lo que tenemos. Primeramente definimos dos variables, colorMap
y colorinterpolation, que nos servirá para modificar los colores de nuestra representación.
'''

colorinterpolation = 50
colorMap = plt.cm.hsv

'''
Usaremos contourf para realizar la representación en sí, pasándole o bien x,y como vectores,
o bien X,Y como meshgrid indistintamente. A contourf le pasaremos las dos variables que
acabamos de crear para definir los colores.
'''

plt.figure(figsize=(20,10))

plt.subplot(121)
plt.contourf(X, Y, M, colorinterpolation, cmap=colorMap)
plt.axis('square')
plt.title("M (meshgrid)")
plt.xlabel('$x$')
plt.ylabel('$y$')
plt.colorbar()

plt.subplot(122)
plt.contourf(x, y, M, colorinterpolation, cmap=colorMap)
plt.axis('square')
plt.title("M (vectores)")
plt.xlabel('$x$')
plt.ylabel('$y$')
plt.colorbar()

plt.show()



#       1.2 - Proyección desde un eje

'''
Sin embargo, si buscamos crear una representación sólo del perfil de M en funcion x para
sólo un valor dado de y. O análogamente, un perfil de M en función de y para sólo un valor
dado de x.
'''

plt.figure(figsize=(20,10))

plt.subplot(121)
plt.plot(x,np.transpose(M),color='k')
plt.title("M\ vista\ desde\ el\ plano\ x")
plt.xlabel('$x$')

plt.subplot(122)
plt.plot(y,M,color='k')
plt.title("M\ vista\ desde\ el\ plano\ y")
plt.xlabel('$y$')

plt.show()

#       1.3 - Representación de un campo vectorial

#       1.4 - Automatización con funciones

'''
Bien podemos entonces crear una función que dada la matriz M y los vectores, imprima la
representación en 2D automáticamente.
'''

def representa2d(x,y,M,colorinterpolation=50,cmap=plt.cm.hsv,title=''):
    plt.figure(figsize=(10,10))
    plt.contourf(x, y, M, colorinterpolation, cmap=cmap)
    plt.axis('square')
    plt.title(title)
    plt.xlabel('$x$')
    plt.ylabel('$y$')
    plt.colorbar()
    plt.show()

'''
De igual manera, podemos crear una función que genere la proyección automáticamente.
'''

def representa_perfil(x,y,M,title='',ztitle='$rho$'):
    plt.figure(figsize=(20,10))
    plt.subplot(121)
    plt.plot(x,np.transpose(M),color='k')
    plt.title(title + "$\ vista\ desde\ el\ plano\ x$")
    plt.xlabel('$x$')
    plt.ylabel(ztitle)

    plt.subplot(122)
    plt.plot(y,M,color='k')
    plt.title(title + "$\ vista\ desde\ el\ plano\ y$")
    plt.xlabel('$y$')
    plt.ylabel(ztitle)

    plt.show()

'''
Por último, definiremos la función que representará un campo vectorial automáticamente.
'''

def representa_cv(x,y,ex,ey,cmap=plt.cm.inferno,density=2,title='',L=2):
    fig, ax = plt.subplots(figsize=(10,10))
    color = 2*log(abs(np.hypot(ex, ey)))
    plt.streamplot(x, y, ex, ey, color=color, linewidth=1, cmap=cmap, density=density,
                   arrowstyle='->', arrowsize=1.5)

    color_carga = {True: '#aa0000', False: '#0000aa'}
    for q, pos in cargas:
        plt.Axes.add_patch(ax,Circle(pos, 0.02*L, color=color_carga[q>0]))
    plt.title(title)
    plt.xlabel('$x$')
    plt.ylabel('$y$')
    plt.axis('square')
    plt.xlim((L0,L0+L))
    plt.ylim((L0,L0+L))

    plt.show()

############################################################################################
#       2 - REPRESENTACIÓN DEL CAMPO Y EL POTENCIAL ELÉCTRICO DEBIDO A UN DIPOLO           #
############################################################################################

#       2.1 - Densidad de carga eléctrica

'''
Ahora, vamos a definir una función que asigne los valores de las cargas en las posiciones
correspondientes de la matriz paralas posiciones espaciales deseadas. Esto será útil para
simular más tarde distribuciones de carga más complicadas.
'''

def Pos(r):
	i = int((r[0]-L0)/h)
	j = int((r[1]-L0)/h)
	return (i,j)

'''
Con el objetivo de crear el dipolo, generamos un vector con las cargas, donde cada carga es
representada con una tupla, donde el primer elemento representa la carga en culombios, y el
segundo elemento representa la posición en el plano.

Gracias a la funicón que hemos generado podemos traducir una tupla con la posición en
coordenadas cartesianas a la posición en nuestra matriz M, ya que conocemos la resolución h
de la misma. Limpiamos la matriz M y colocamos las cargas en ella con un bucle for.
'''

cargas = [(3.,(-0.5,0)), (-3.,(0.5,0))] # Otra vez, es importante que sean floats

M = np.zeros((N,N))
for carga in cargas:
	M[Pos(carga[1])] = carga[0]

'''
Utilizamos la función que hemos creado previamente para representar la densidad de carga.
Destacar que hemos de transponer M para su correcta representación.
'''
representa2d(x, y, np.transpose(M), cmap=plt.cm.coolwarm, title='$Densidad\ de\ carga$')

#       2.2 - Potencial eléctrico

def V(q,r0,x,y):
    den=np.hypot(x-r0[0],y-r0[1])
    return q/den

for carga in cargas:
    v0=V(carga[0],carga[1],X,Y)
    M += v0

representa_perfil(x, y, M, title='$Potencial\ Eléctrico$', ztitle='$N/C$')

#       2.2 - Campo eléctrico

def E(q, r0, x, y):
    den = ((np.hypot(x-r0[0], y-r0[1]))**3) / 4*pi
    return q*(x -r0[0])/den, q*(y -r0[1])/den

'''
Definimos una función para representar el campo eléctrico generado por una distribución de
carga dada.
'''

def representaE(cargas,tipo=''):
    Ex, Ey = np.zeros((N, N)), np.zeros((N, N))
    for carga in cargas:
        ex, ey = E(*carga, x=X, y=Y)
        Ex += ex
        Ey += ey

    z = np.hypot(Ex,Ey)
    representa_perfil(x, y, np.transpose(z), title='$Campo\ Eléctrico$'+tipo ,ztitle='$E\ (N/C)$')
    representa2d(     x, y, np.transpose(z), title='$Campo\ Eléctrico$'+tipo)
    representa_cv(    x, y, Ex, Ey,          title='$Campo\ Eléctrico$'+tipo, L=L)

representaE(cargas,'$\ Dipolo$')

#       2.3 - Campo eléctrico en otras distribuciones

'''
En esta sección, vamos a probar diferentes distribuciones de carga a la del dipolo y a
observar su representación.
'''

'''
Distribución de carga cuadrada
'''
cargas.clear()
cargas = [(3.,(-0.5,-0.5)), (-3.,(0.5,-0.5)), (-3.,(-0.5,0.5)), (3.,(0.5,0.5))]

M = np.zeros((N,N))
for carga in cargas:
	M[Pos(carga[1])] = carga[0]
representa2d(x, y, np.transpose(M), cmap=plt.cm.coolwarm, title='$Densidad\ de\ carga\ cuadrada$')

'''
Distribución de carga lineal (finita):
Como conocemos la resolución, basta colocar una carga en cada nodo de nuestra malla
para simular una distribución lineal. La carga partido h de cada una de estas cargas que
vamos a colocar será la densidad de carga lineal de la distribución.
'''

cargas.clear()
q = 3.
c = 0                   # Constante que usaremos como eje en el que colocar la distribución
for i in range(N):
    cargas.append((q,(L0+i*h,c)))
representaE(cargas,'$\ distribución\ lineal\ finita$')

M = np.zeros((N,N))
for carga in cargas:
	M[Pos(carga[1])] = carga[0]
representa2d(x, y, np.transpose(M), cmap=plt.cm.coolwarm, title='$Densidad\ de\ carga\ lineal$')

'''
Distribución de carga lineal (infinita):
De igual forma crearemos una distribución "infinita" generando un número  mayor de cargas,
pero mostrando en el gráfico una sección.
'''

cargas.clear()
q = 3.
c = 0                   # Constante que usaremos como eje en el que colocar la distribución
zoom = 20         # Constante que usaremos para determinar cuantas cargas extra colocaremos
for i in range(zoom*N):
    cargas.append((q,(zoom*L0+i*h,c)))
representaE(cargas,'$\ distribución\ lineal\ infinita$')

'''
Distribución de carga circular:
Primeramente bajamos la resolución bastante para ayudar al cómputo de esta distribución.
Posteriormente, parametrizamos una circunferencia de cargas con const = rcosx + rsinx ý 
variamos el radio n veces (10) hasta generar un disco de carga de 0.1*n de radio.
'''

cargas.clear()
N = 36
h  = L/(N-1)
x = np.linspace(L0,L0+L,N)
y = np.linspace(L0,L0+L,N)
X,Y = np.meshgrid(x,y)

q = 3.                                                             # coulombs de cada carga
n = 10000                                                          # número de circunferencias
theta = np.linspace(0,2*pi)
cx, cy = [], []

for i in range(n):
    R = i*0.0001
    for j in range(len(theta)):
        cx.append(R*cos(theta[j]))
        cy.append(R*sin(theta[j]))

for i in range(len(cx)):
        cargas.append((q,(cx[i],cy[i])))

M = np.zeros((N,N))
for carga in cargas:
	M[Pos(carga[1])] = carga[0]
representa2d(x, y, np.transpose(M), cmap=plt.cm.coolwarm, title='$Densidad\ de\ carga\ circular$')

############################################################################################
#          3 - CAMPO ELÉCTRICO A PARTIR DE LA SOLUCIÓN DE LA ECUACIÓN DE POISSON           #
############################################################################################

cargas.clear()
cargas = [(3.,(-0.5,0)), (-3.,(0.5,0))] 

'''
Para resolver la ecuación de Poisson, requeriremos del cálclulo del laplaciano del potencial,
para el cual necesitaremos tener bien definido V y rho.
'''
rho = np.zeros((N,N))
for carga in cargas:
	rho[Pos(carga[1])]=carga[0]

'''
Ahora, definiremos un épsilon que usaremos como tolerancia, comparando el valor de la matriz
anterior con el actual, hasta que la diferencia entre ambas sea menor a nuestro épsilon
'''

V0 = np.zeros((N,N))
v  = np.zeros((N,N))
M  = np.zeros((N,N))
for carga in cargas:
    v0=V(carga[0],carga[1],X,Y)
    v += v0
eps = 10e-8
err = 10

while(err > eps):
    V0 = np.copy(v)
    for i in range(1,N-1):
        for j in range(1,N-1):
            v[i,j] = (v[i+1,j]+v[i-1,j]+v[i,j+1]+v[i,j-1]+rho[i,j]*h**2)/4
            err += v[i,j]-V0[i,j]
    err = err/N**2

for carga in cargas:
    v0 = V(carga[0],carga[1],X,Y)
    M += v0

representa2d(X, Y, v, title='$Poisson$',  cmap=plt.cm.coolwarm)
representa2d(X, Y, M, title='$Potencial$',cmap=plt.cm.coolwarm)