

#Importamos las librerías necesarias
import matplotlib.pyplot as plt
import numpy as np
from matplotlib import animation

#Intento de representar los spines con flechas
def plot_spins(M):
    plt.figure()
    colors = {1: "red", -1: "blue"}
    for i in range(0,Z+2):
        for j in range(0,Z+2):
            plt.quiver(i, j, 0, M[i][j], pivot="middle", color=colors[M[i][j]])
    plt.xticks(range(-1,Z+1))
    plt.yticks(range(-1,Z+1))
    plt.gca().set_aspect("equal")
    plt.grid()
    plt.show
    
#Variamos el valor de un spin aleatorio
def SpinChange(M):
    A = np.copy(M)
    x=np.random.randint(1,Z+1)
    y=np.random.randint(1,Z+1)
    A[x][y]=A[x][y]*(-1)
    return A
    
#Definimos la función probabilidad de cambiar de estado
def ProbabilityChange(dE):
    return 1/(1+np.exp(beta*dE))

#Sorteamos con igual propiedad el valor de los spines
np.random.choice([-1,1], p=(0.5,0.5))

#Definimos la matriz (red de spines 2D) y las C.C.
Z=30
M=np.array([[np.random.choice([-1,1], p=(0.5,0.5)) for j in range (Z+2)] for i in range(Z+2)])
M[0]=M[Z]
M[Z+1]=M[1]
M[:,0]=M[:,Z]
M[:,Z+1]=M[:,1]

#Graficamos
#plot_spins(M)
#plt.imshow(M)


#Magnetiación total
mt = np.sum(M[1:Z,1:Z])

#Definimos la matriz energía
E=np.zeros((Z+2,Z+2))

def E (M):
    E=np.zeros((Z+2,Z+2))
    for i in range(1,Z+1):
        for j in range (1,Z+1):
            E[i][j]=-0.5*M[i][j]*(M[i+1,j]+M[i-1,j]+M[i,j+1]+M[i,j-1])
    return E

En=E(M)

#Graficamos
#plt.imshow(En)

#Energía total
et=np.sum(En[1:Z+1,1:Z+1])


beta=0.01
K = 1
H=0


ENERGIA=[et]
MAGN=[mt]

#Creamos bucle
    
MLIST = [M]
iterac=0
for k in range(0,5000):
    NM=SpinChange(M)
    nmt = np.sum(NM[0:Z+1,0:Z+1])
    NEn = E(NM)
    net = np.sum(NEn[1:Z+1,1:Z+1])
    dE = net-et
    pnew = ProbabilityChange(dE)
    
    if np.random.choice([True,False],p=(pnew,1-pnew)):
        ENERGIA += [net]
        MAGN += [nmt]
        M = NM
        iterac+=1
        et = net
        if k/2 == k//2:
            MLIST.append(M)
            
x= np.linspace(0,iterac, iterac+1)
plt.plot(x,ENERGIA, label='Energia total ([J])')
plt.plot(x,MAGN, label='Magnetización total')
#temperaturas bajas
plt.ylim([min(ENERGIA)-50,max(MAGN)+50])
#temperaturas altas
#plt.ylim([-500,500])

plt.xlabel('Número de iteraciones que cambian el sistema')
plt.legend()
plt.show()


#Animación
fig = plt.figure()
im = plt.imshow(MLIST[0], aspect="auto")

def init():
    im.set_data(np.zeros(Z+2,Z+2))
    
def updatefig(i):
    im.set_data(MLIST[i])
    return im
    
anim = animation.FuncAnimation(fig, updatefig, frames=len(MLIST),
                               interval=10,repeat_delay = 1000)

plt.show()

plt.figure()
plt.imshow(M)
plt.show()


### Dispersiones y calor especifico

def energia(M):
    E = 0
    for i in range(1,Z+1):
        for j in range(1,Z+1):
            E += -J/2 * M[i,j]*(M[i-1,j] + M[i+1,j] + M[i,j-1] + M[i,j+1]) - M[i,j]*H
    return E

# Parámetros iniciales

n = 4000
H = 0
cont = 0
J=1

# Temperaturas y betas
Temp = np.logspace(-3, 3, 3)

Betas = np.zeros(len(Temp))
# Calculo de betas
for i in range(len(Temp)):
    Betas[i] = 1/(Temp[i]*K)

# Inicializamos listas de las variables a calcular
Cvtodas = np.zeros(len(Betas))

Disp = np.zeros(len(Betas))


## Simulamos el método variando las temperaturas
for beta in Betas:

    sp = np.zeros((Z+2, Z+2))
    
    for i in range(1,Z+1):
        for j in range(1,Z+1):
            sp[i,j] = np.random.choice(np.array([1,-1]))
            
    sp[:,0] = sp[:,-2]
    sp[0,:] = sp[-2,:]
    sp[:,-1] = sp[:,1]
    sp[-1,:] = sp[1,:]
    
    s_t = np.zeros((Z,Z,n))
    E_t = np.zeros(n)
    M_t = np.zeros(n)
    
    ## Aplicamos el metodo de montecarlo
    
    for i in range(n):        
        s = sp[1:-1, 1:-1]        
        s_t[:,:,i] = s        
        M_t[i] = np.sum(s)        
        E_0 = energia(sp)        
        E_t[i] = E_0        
        fil = int(np.random.choice(np.linspace(1, Z+1, Z+1)))
        col = int(np.random.choice(np.linspace(1, Z+1, Z+1)))        
        sp[fil, col] = -1 * sp[fil,col]        
        dE = energia(sp) - E_0      
        s = sp[1:-1, 1:-1] 
        pN = 1/(1 + np.exp(beta*dE))        
        change = np.random.choice(np.array([True,False]), p = np.array([1-pN, pN]))        
        if change:
            sp[fil, col] = -1 * sp[fil,col]        
        sp[:,0] = sp[:,-2]
        sp[0,:] = sp[-2,:]
        sp[:,-1] = sp[:,1]
        sp[-1,:] = sp[1,:]        
        s = sp[1:-1, 1:-1]        
        
    
    ## Almacenamos las variables
    # Energía de cada partícula
    Emat = np.zeros((Z+2, Z+2))
    for i in range(1,Z+1):
        for j in range(1,Z+1):
            Emat[i,j] = -1/2 * sp[i,j] * (sp[i-1,j] + sp[i+1,j] + sp[i,j-1] + sp[i,j+1]) - sp[i,j]*H
    
    Epart = Emat.flatten(order='C')
    
    # Dispersión
    media = np.sum(Epart)/Z**2
    media2 = np.sum(Epart**2)/Z**2
    
    Dispersion = np.sqrt(media2 - media**2)
    Disp[cont] = Dispersion
    
    # Calor específico
    Cv = (Dispersion*beta)**2*K
    Cvtodas[cont] = Cv
    
    cont += 1
    
##### REPRESENTACIONES GRÁFICAS #####

plt.figure()
plt.title('Calor específico en función de la temperatura')
plt.plot(Temp, Cvtodas, '.')
plt.xscale('log')
plt.xlabel('Temperatura (K)')
plt.ylabel('$ C_v $')
plt.show()


plt.figure()
plt.title('Dispersión de la energía en función de la temperatura')
plt.plot(Temp, Disp, '.')
plt.xscale('log')
plt.xlabel('Temperatura (K)')
plt.ylabel('Dispersión ([J])')
plt.show()


    



    