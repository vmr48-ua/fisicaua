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


print(leeDatos('prueba.txt'))