from math import pi,sqrt
def cerror(err):
    cont = 0
    if err >= 1:
        while 10*(err-int(err)) == 0:
            err /= 10
            cont -= 1
        cont += 1
    else:
        while int(err) == 0:
            err *= 10
            cont += 1
        if int(err) == 1 and int((err-1)*10) != 0:
            cont += 1
    return cont

def normal_round(num, ndigits=0):
    if ndigits == 0:
        return int(num + 0.5)
    else:
        digit_value = 10 ** ndigits
        return int(num * digit_value + 0.5) / digit_value

def cifra(a):
    b = a
    cont = 0
    if a < 1:
        while int(a) == 0:
            a *= 10
            cont += 1
        if int(a) == 1 and int((a-int(a))*10) < 5:
            cont+=1   
        return normal_round(b,cont)     
    else:
        if a >= 100:
            a = int(a)
            while a > 1:
                a /= 10
                cont -= 1
            cont += 1
            if int(a*10) == 1 and int(((a*10)-int(a*10))*10) < 5:
                cont += 1
            return int(normal_round(b,cont))
        elif a >= 10:
            if (int(a)-int(10*(a/10-int(a/10))))/10 == 1 and int(a-10) < 4:
                cont = 0
            else:
                cont = -1
            return normal_round(b,cont)
        else:
            if int(a) - 1 == 0 and int((a-int(a))*10) < 5:
                cont = 1
            else:
                cont = 0
            return normal_round(b,cont)

def periodo():
    filas = int(input('Introduce el número de filas: '))
    print('Introduce una columna de excel con los datos:')
    result = ''
    for i in range(filas):
        result += input()+' '

    datos = list(map(float,result.replace(',','.').split()))
    error = float(input('Introduce el error de la medida (ajustado): '))
    sum,media = 0,0

    for i in range(len(datos)):
        media += datos[i]
    b = normal_round(media/len(datos),cerror(error))

    for i in range(len(datos)):
        sum += ((datos[i] - b)**2)/(len(datos)-1)

    ans = (sum)**(0.5)
    var = normal_round(ans,cerror(error))
    ErEsta = cifra(var/(len(datos))**0.5)
    #print('Varianza', var)
    #print('Media', b)
    #print('Error estadístico', ErEsta)
    ErInte = float(input('Introduce el error de interacción (ajustado): '))
    ErInst = float(input('Introduce el error del instrumento (ajustado): '))
    ErTodo = ((ErEsta**(2))+(((ErInte/len(datos))**(2)+(ErInst/len(datos))**(2))**(0.5))**2)**(0.5)
    print('Valor final (Periodo): {} \xB1 {}'\
        .format(f"{normal_round(b,cerror(cifra(ErTodo))):.{cerror(cifra(ErTodo))}f}",cifra(ErTodo)),'s')

    return[f"{normal_round(b,cerror(cifra(ErTodo))):.{cerror(cifra(ErTodo))}f}" , cifra(ErTodo)]

ans = periodo()
T = float(ans[0])
errT = float(ans[1])

L =    float(input('Introduce la longitud (m): '))
errL = float(input('Introduce el error: '))

gravedad = L/((T/(2*pi))**(2))
error = sqrt((((4*(pi**(2)))/(T**(2)))**(2))*((errL)**2)+(((-8*(pi**(2))*L)/(T**(3)))**2)*((errT)**2))

print("La aceleración gravitatoria calculada será de",\
    f"{normal_round(gravedad,cerror(cifra(error))):.{cerror(cifra(error))}f}",'\xB1',cifra(error), "m/s\xb2")