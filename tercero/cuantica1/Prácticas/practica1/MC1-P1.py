import numpy as np
import scipy.linalg as spy
import matplotlib.pyplot as plt
import warnings

warnings.filterwarnings("ignore")

"""
#######################################################################
#                                                                     #
#                PRÁCTICA 1 MCI: POTENCIALES EN PYTHON                #
#                         Víctor Mira Ramírez                         #
#                            DNI 74528754Z                            #
#                                                                     #
#######################################################################

Práctica realizada durante el curso 23-24 para la asignatura de 
Mecánica Cuántica I de la Universidad de Alicante. Clases de prácticas 
impartidas por J. Fernández-Rossier y Mar Ferri Cortés.

Es importante para el funcionamiento de este programa cambiar el nombre
'PRAC12023' de la línea siguiente por el nombre que tenga el archivo 
.py con las funciones dadas en clase.
"""
from PRAC12023 import *

def redondea_primeros2_nonzero(num):
    num_str = str(num)
    iComa = num_str.find('.')
    cuantosCeros=0
    for digito in num_str:
        if digito == '0':
            cuantosCeros+=1
        else:
            return round(num,cuantosCeros+2)

"""
#######################################################################
#                        Problema 1 (Modelo 1)                        #
#######################################################################
Haz un programa de python que llame a los que has usado en las 
prácticas y que resuelva las siguientes tareas:
"""

k=-10 # meV / Å
m= 1  # masa del electrón

xmax=50  # rango razonable (20-100)
N=150    # rango razonable (20-150)
# Si ponemos N pequeño, lo adecuado es poner xmax pequeño también 
# para no bajar mucho la resolución y no colgar el programa

precisionGlobal = redondea_primeros2_nonzero(2/(xmax+1.2*N))
# Se puede poner la precision manualmente también para mayor rapidez
# precisionGlobal = 0.1
# print('Precisión = {} unidades'.format(precisionGlobal))

dni = 754

"""
Apartado A 

Encuentre el valor de la diferencia de energı́a, ∆ ≡ E1 − E0, 
para p = 0. (Observa que esta canditad dependerá ligeramente 
de los valores de N y xmax elegidos para los cálculos).
"""

def DeltaRespectoP(p):
    def potencial(x,k):
        return (p/2)*(x**2)+k*x
    hamiltoniano = ham(xmax,N,potencial,k,m)
    nivel,_=spy.eigh(hamiltoniano)
    return round(np.abs(nivel[1]-nivel[0])
                ,len(str(precisionGlobal).split(".")[-1]))


print('Delta0 =    ', DeltaRespectoP(0),' meV',sep='')

"""
Apartado B

Encuentra el valor del parámetro p en el potencial V (x) que 
te corresponde, tal que el cambio de diferencia de energı́a entre
el estado fundamental E0 y el primer excitado es E1 con respecto
al obtenido en el apartado anterior, ∆(p) − ∆(0), sea igual tres
últimas cifras de tu DNI en meV. Por ejemplo, DNI terminado en 
437, ∆(p) − ∆(0) = 437meV .
"""

def buscaP(dni,precision):
    x,y,last_y=0,0,-1
    while int(np.round(DeltaRespectoP(x)))<=dni:
        x+=precision
        y= (int(np.round(DeltaRespectoP(x)))*100)/dni
        if y < 95:
            x=1.05*x
        if y != last_y:
            print('Buscando la constante P ',int(y),'%',end='\r',sep='')
            last_y = y
    return x

pDni = round(buscaP(dni,precisionGlobal)
            ,len(str(precisionGlobal).split(".")[-1]))
print('                            ',end='\r')
print('P =         {} meV/A²'.format(pDni))

"""
    Apartado C

    (FIGURA 1) Dibuja la curva ∆(p) − ∆(0) como función del 
    parámetro variable de tu potencial, y haz visibile la solución
    del problema en dicho punto.
"""

print('Calculando las figuras 1 y 2 ...',end='\r')
plt.figure(figsize=(14,10))
plt.title('FIGURA 1: ∆(p) − ∆(0) en función de P',fontweight='bold',fontsize=20)
plt.ylabel('∆(p) − ∆(0) [meV]')
plt.xlabel('P [meV]')
plt.annotate('∆(p) − ∆(0) ={}'.format(dni),(1,dni+10))
plt.axvline(pDni,color='mediumblue',linestyle='--',alpha=0.5)
plt.annotate('P={}'.format(pDni),(pDni+1,100))
plt.scatter(pDni,DeltaRespectoP(pDni),marker='x',color='r')

DeltaRespectoP = np.vectorize(DeltaRespectoP)
plist = np.linspace(0,100,1000)

plt.plot(plist,DeltaRespectoP(plist))
plt.plot(plist,len(plist)*[DeltaRespectoP(pDni)],linestyle=':',color='k',alpha=0.5)

"""
    Apartado D

    (FIGURA 2) Dibuja las funciones de onda de los estados para 
    el valor p obtenido.
"""

plt.figure(figsize=(14,10))
plt.title('FIGURA 2: Funciones de onda 1 y 2 para P={} meV'.format(pDni)
         ,fontweight='bold',fontsize=20)

def potencial(x,k):
        return (pDni/2)*(x**2)+k*x
H = ham(xmax,N,potencial,k,m)
plotstates(H,2,xmax,N,potencial,k)
print('                                      ',end='\r')

"""
#######################################################################
#                        Problema 2 (Modelo 2)                        #
#######################################################################
Doble barrera, de altura V0 = 400meV , anchura W = 30Å, separadas por 
d = 30Å (mira la figura).
"""

V0= 400
W = 30
d = 30

par = (-(d/2+W),-d/2,d/2,-(d/2+W),V0,V0)

"""
    Apartado A

    Determina la energı́a del electrón para que la probabilidad de 
    transmisión T sea igual a las 3 últimas cifras de tu DNI, divididas 
    por 1000 (Por ejemplo, para DNI = 63290281 T = 0.281). Nota que 
    puede haber más de una energı́a que satisface la condición requerida.
"""

Puntos=1000

def potencial2(x,par):
    return doublebarrier(x,par)

def buscaE(T,precision):
    x,xl,aux,result=[],[],[],[]
    last_y = -1
    # El siguiente bucle puede dar más de un valor de E para un mismo 
    # nodo que cumpla la condición de tolerancia
    print('Buscando las energías: ',0,'%',end='\r',sep='')
    longitud = len(np.arange(0,Puntos+precision,precision))
    for i in np.arange(0,Puntos+precision,precision):
        y = int((i*100)/(Puntos+precision))
        if y != last_y:
            print('Buscando las energías: ',y,'%',end='\r',sep='')
            last_y = y
        if np.abs(np.round(trans(xmax,N,potencial2,par,m,i),3)-T) < 0.1*precision:
            x.append(round(i,len(str(precision).split(".")[-1])))
    print('                           ',end='\r')

    # Hacemos una lista xl donde ponemos los valores próximos entre si
    i=0
    while i < (len(x)-1):
        aux.append(x[i])
        while i < (len(x)-1) and abs(x[i]-x[i+1]) < 2*precision:
            aux.append(x[i+1])
            i+=1
        xl.append(aux.copy())
        aux.clear()
        i+=1
    
    # Evaluamos todos los valores próximos y nos quedamos con el más
    # cercano al valor del dni
    for lista in xl:
        final = lista[0]
        for i in lista:
            valor = np.abs(trans(xmax,N,potencial2,par,m,final)-T)
            if np.abs(trans(xmax,N,potencial2,par,m,i)-T) < valor:
                final = i
        result.append(final)
    return result

# En función de la precisión el programa tarda más o menos tiempo
# Si vemos que falta algún valor en la gráfica, basta poner una
# precisión más fina para que aparezca (por ejemplo para xmax=50
# se necesita que la precisión sea al menos 0.01)

eDni = buscaE(dni/1000,precisionGlobal) 
for i in range(len(eDni)):
    Ei = round(eDni[i], len(str(precisionGlobal).split(".")[-1]))
    print('E_{} =       '.format(i), Ei,' meV',sep='')

"""
    Apartado B

    (FIGURA 3) Grafica la curva T (E) correspondiente en la que se 
    vea la solucián del apartado anterior.
"""

emin=0
emax=1000
dE=0.5
colorlist=['mediumblue','red','green','brown','orange','blueviolet']
colorlist0=['b','g','r','c','m','y','k']
colorlist=colorlist+colorlist0+colorlist

elist = np.linspace(0,int(Puntos))

plt.figure(figsize=(14,10))
plt.title('FIGURA 3: T(E)',fontweight='bold',fontsize=20)

plt.plot(elist,len(elist)*[dni/1000],color='k',linestyle=':',alpha=0.5)

for i in range(len(eDni)):
    scatterPointsX = eDni[i]
    scatterPointsY = (trans(xmax,N,potencial2,par,m,eDni[i]))
    plt.axvline(eDni[i],color=colorlist[i],linestyle='--',alpha=0.5
                ,label=str('E_{}= '.format(i)+str(round(eDni[i]
                ,len(str(precisionGlobal).split(".")[-1])))))
    plt.scatter(scatterPointsX,scatterPointsY,marker='x'
                ,color=colorlist[i])

plt.annotate('T={}'.format(dni/1000),(10,dni/1000+0.05))
plt.legend(loc='lower right')
plotT(xmax,N,m,emin,emax,dE,potencial2,par)