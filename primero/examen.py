from sys import argv
import matplotlib.pyplot as plt
from scipy.integrate import odeint
import numpy as np

def borraEspacios(doc):
    doc2,cadena2 = [],[]
    for fila in doc:
        cadena = fila.split(" ")
        cadena2.clear()
        for i in range(len(cadena)):
            if cadena[i] != '':
                cadena2.append(cadena[i])
        doc2.append(cadena2.copy())
    return doc2

def borraFilaVacia(doc):
    doc2 = []
    for fila in doc:
        if fila != []:
            doc2.append(fila)
    return doc2

def leeDatos(nombre):
    u1,u2,u3 = [],[],[]
    entrada = open(nombre,"r")
    doc = entrada.read().splitlines()
    doc = borraEspacios(doc)
    doc = borraFilaVacia(doc)

    if len(doc) < 4:
        print('Error: archivo incorrecto')
        return ([None],[None],[None])
    else:
        for i in range(len(doc)):
            u1.append(int(doc[i][0]))
            u2.append(float(doc[i][1]))
            u3.append(float(doc[i][2]))
    return (u1,u2,u3)

def minimo(v):
    minimo = v[0]
    for elemento in v:
        if elemento <= minimo:
            minimo = elemento
    return minimo

def sumaDobleMinimo(v):
    v2,m = [],minimo(v)
    for elemento in v:
        v2.append(elemento + 2*m)
    return v2

def es_primo(n): 
	if n>1: 
		divs=[k for k in range(2,n) if n%k==0] 
		return len(divs)==0 
	else: 
		return False

def nprimo(n): 
	primes=[p for p in range(n*n+2) if es_primo(p)] 
	return primes[n-1]

def primoI(v):
    v2,n = [],0
    while n < len(v):
        if v[n] > 0:
            v2.append(nprimo(v[n])) 
        else:
            v2.append(0)
        n+=1
    return v2

def media(v,i):
    v2 = v.copy()
    v2.pop(i)
    ans = 0
    for elemento in v2:
        ans += elemento
    return ans/len(v2)

def mediaSinI(v):
    v2 = []
    for i in range(len(v)):
        v2.append(media(v,i))
    return v2

def imprimir(v):
    for i in range(len(v)-1):
        print(v[i],end=' ')
    print(v[len(v)-1])

datos = leeDatos("datos.dat")
if datos !=([None],[None],[None]):
    if len(argv) == 1:
        print('suma doble minimo: ',end ='')
        imprimir(sumaDobleMinimo(datos[0]))
        print('i-esimo primo: ',end='')
        imprimir(primoI(datos[0]))
        print('media sin i: ',end='')
        imprimir(mediaSinI(datos[0]))
    elif len(argv) == 2:
        salida = open(argv[1],"w")

        salida.write('suma doble minimo: ')
        for i in range(len(datos[0])-1):
            salida.write(str(sumaDobleMinimo(datos[0])[i])+' ')
        salida.write(str(sumaDobleMinimo(datos[0])[len(datos[0])-1])+'\n')

        salida.write('i-esimo primo: ')
        for i in range(len(datos[0])-1):
            salida.write(str(primoI(datos[0])[i])+' ')
        salida.write(str(primoI(datos[0])[len(datos[0])-1])+'\n')

        salida.write('media sin i: ')
        for i in range(len(datos[0])-1):
            salida.write(str(mediaSinI(datos[0])[i])+' ')
        salida.write(str(mediaSinI(datos[0])[len(datos[0])-1]))        
        salida.close()
    else:
        print('ERROR: número de argumentos no válido')

x,y = [],[]
for i in range(len(datos[1])):
    x.append(datos[1][i])
    y.append(datos[2][i])

plt.scatter(x,y)
def f(y,x):
    return (x*x-1)/(y*y)
y0 = 8
t = np.linspace(1,6,100)
y = odeint(f,y0,t)
plt.plot(t,y)

plt.savefig('grafica.png')