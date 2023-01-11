from numpy import exp,linspace,inf
import matplotlib.pyplot as plt
from scipy.integrate import quad

#Creo la función
def f(t,x):
    return (1/t) * exp(-t)

#Escribo en un archivo el resultado que quad me da para la función
def escribir():
    file = open("datos.txt","w")

    #Creo un espacio en el límite estipulado con 100 puntos
    a = linspace(0.1,2,100)
    #resultados contendrá 100 resultados de la integral en el rango estipulado
    resultados = []
    for x in a:
        resultados.append(quad(f,x,inf,args=x)[0])
    print(quad(f,0.1,inf,args=x))

    #Escribo todos los resultados en el archivo en el formato estipulado
    for i in range(len(resultados)-1):
        file.write(str(a[i]) + ' ' + str(resultados[i]) + '\n')
    file.write(str(a[99]) + ' ' + str(resultados[99]))

    file.close()

#Grafica los resultados que lee de un archivo de datos
def graficar():
    archivo = open("datos.txt","r")
    y = []
    x = []
    grafica = []
    a = linspace(0.1,2,100)
    
    #Separo el texto de los datos y me guardo los datos en una lista
    for fila in archivo:
        fila = [str(x) for x in fila.split()]
        x.append(float(fila[0]))
        y.append(float(fila[1]))

    #Grafico los datos y los guardo en la foto correspondiente
    
    plt.plot(x,y)
    plt.savefig("ej3.png")
    archivo.close()

escribir()
graficar()

#https://www.wolframalpha.com/input?i2d=true&i=-Ei%5C%2840%29-x%5C%2841%29+from+0.1+to+2