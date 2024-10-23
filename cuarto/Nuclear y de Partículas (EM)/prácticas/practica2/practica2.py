import numpy as np
from numpy import log, exp
import matplotlib.pyplot as plt
from decimal import Decimal
from scipy.integrate import odeint
from matplotlib.animation import FuncAnimation

'''
FÍSICA NUCLEAR Y DE PARTÍCULAS - PRÁCTICA II

Víctor Mira Ramírez
74528754Z
vmr48@alu.ua.es
'''

# Auxiliary Functions
def landa(a):
    return log(2)/a

def convertir_a_segundos(t12, unidad):
    if unidad == 'y':    # Años a segundos
        t12 *= 365.25 * 24 * 3600
    elif unidad == 'd':  # Días a segundos
        t12 *= 24 * 3600
    elif unidad == 'h':  # Horas a segundos
        t12 *= 3600
    return t12

def Decay(N0,T12,t):
    λ = landa(T12)
    return N0 * exp(-λ*t)

def Activity(T12,N):
    λ = log(2)/T12
    return λ * N

# Tasks
def task1(N0, T12_U235, T12_U238):
    t = 1e9 # years
    
    print('Given a sample of Uranium isotopes of N0 = {} nuclei:'.format('%.E' % Decimal(N0)))
    print('Remaining quantity of Uranium-235 after {} years: {} nuclei'.format('%.E' % Decimal(t),'%.2E' % Decimal(Decay(N0,T12_U235,t))))
    print('Remaining quantity of Uranium-238 after {} years: {} nuclei'.format('%.E' % Decimal(t),'%.2E' % Decimal(Decay(N0,T12_U238,t))))
    
    return None

def task2(N0, T12_U235, T12_U238):   
    tmax = 10 # 1e10
    Nt = 100
    t_log = np.logspace(0,tmax,Nt)
    t = np.linspace(0,10**tmax,Nt)
    
    decayU238_log = np.array(Decay(N0,T12_U238,t_log))
    decayU235_log = np.array(Decay(N0,T12_U235,t_log))
    decayU238     = np.array(Decay(N0,T12_U238,t))
    decayU235     = np.array(Decay(N0,T12_U235,t))
    
    plt.figure(figsize=(10,5),tight_layout=True)
    plt.suptitle('Uranium Isotopes Decay Curves\nfor $N_0=10^6$ nuclei')
    
    plt.subplot(1,2,1)
    plt.title('Logaritmic')
    plt.plot(t_log,decayU238_log, label='$U^{238}$')
    plt.plot(t_log,decayU235_log, label='$U^{235}$')
    plt.yscale("log")
    plt.xlabel('Years')
    plt.ylabel('Remaining Nuclei')
    plt.grid()
    plt.legend(loc='upper right')
    
    plt.subplot(1,2,2)
    plt.title('Exponential')
    plt.plot(t,decayU238, label='$U^{238}$')
    plt.plot(t,decayU235, label='$U^{235}$')
    plt.xlabel('Years')
    plt.ylabel('Remaining Nuclei')
    plt.grid()
    plt.legend(loc='upper right')
    return None

def task3(N0, T12_U235, T12_U238):
    tmax = 10 # 1e10
    Nt = 100
    t = np.linspace(0,10**tmax,Nt)
    t_log = np.logspace(0,tmax,Nt)
    
    activityU235 = Activity(T12_U235, Decay(N0, T12_U235, t))*365*24*60*60/1e3
    activityU238 = Activity(T12_U238, Decay(N0, T12_U238, t))*365*24*60*60/1e3
    activityU235_log = Activity(T12_U235, Decay(N0, T12_U235, t_log))*365*24*60*60
    activityU238_log = Activity(T12_U238, Decay(N0, T12_U238, t_log))*365*24*60*60
    
    
    plt.figure(figsize=(10,5),tight_layout=True)
    plt.suptitle('Uranium Activity Curves\nfor $N_0=10^6$ nuclei')

    plt.subplot(1,2,1)
    plt.title('Logaritmic')
    plt.plot(t_log,activityU238_log, label='$U^{238}$')
    plt.plot(t_log,activityU235_log, label='$U^{235}$')
    plt.xlabel('Years')
    plt.ylabel('Activity ($Bq$)')
    plt.yscale("log")
    plt.grid()
    plt.legend(loc='upper right')
    
    plt.subplot(1,2,2)
    plt.title('Exponential')
    plt.plot(t,activityU238, label='$U^{238}$')
    plt.plot(t,activityU235, label='$U^{235}$')
    plt.xlabel('Years')
    plt.ylabel('Activity ($kBq$)')
    plt.grid()
    plt.legend(loc='upper right')
    return None

def task4(N0, T):
    '''
    N0: int, nº de núcleos de uranio inicial
    T: [float], [(str, T12U, str), (str, T12Th, str), 
                (str, T12U, str), (str, T12Th, str)]
    '''
    lamda = [landa(convertir_a_segundos(isotopo[1], isotopo[2])) for isotopo in T]

    def sistema238(N, t):
        N_U238, N_Th234 = N
        dN_U238_dt = -lamda[0] * N_U238
        dN_Th234_dt = lamda[0] * N_U238 - lamda[1] * N_Th234
        return [dN_U238_dt, dN_Th234_dt]

    def sistema235(N, t):
        N_U235, N_Th231 = N
        dN_U235_dt = -lamda[2] * N_U235
        dN_Th231_dt = lamda[2] * N_U235 - lamda[3] * N_Th231
        return [dN_U235_dt, dN_Th231_dt]

    t1 = np.linspace(0, 1.3e18, 100)
    t2 = np.linspace(0, 1.3e17, 100)

    solucion238 = odeint(sistema238, [N0, 0], t1) # U238 -> Th234
    solucion235 = odeint(sistema235, [N0, 0], t2) # U235 -> Th231

    fig1, ax1 = plt.subplots(figsize=(10, 8))
    line_U238, = ax1.plot([], [], label='N_U238', lw=2)
    line_Th234, = ax1.plot([], [], label='N_Th234', lw=2)

    ax1.set_xlim(0, t1[-1])
    ax1.set_ylim(0, N0)
    ax1.set_xlabel('Tiempo (s)')
    ax1.set_ylabel('Número de núcleos')
    ax1.legend(loc='upper right')
    ax1.grid()

    def init1():
        line_U238.set_data([], [])
        line_Th234.set_data([], [])
        return line_U238, line_Th234

    def animate1(i):
        line_U238.set_data(t1[:i], solucion238[:i, 0])
        line_Th234.set_data(t1[:i], solucion238[:i, 1])
        return line_U238, line_Th234

    anim1 = FuncAnimation(fig1, animate1, init_func=init1, frames=len(t1), interval=50, blit=True)
    plt.show()
    
    fig2, ax2 = plt.subplots(figsize=(10, 8))
    line_U235, = ax2.plot([], [], label='N_U235', lw=2)
    line_Th231, = ax2.plot([], [], label='N_Th231', lw=2)

    ax2.set_xlim(0, t2[-1])
    ax2.set_ylim(0, N0)
    ax2.set_xlabel('Tiempo (s)')
    ax2.set_ylabel('Número de núcleos')
    ax2.legend(loc='upper right')
    ax2.grid()
    
    def init2():
        line_U235.set_data([], [])
        line_Th231.set_data([], [])
        return line_U235, line_Th231

    def animate2(i):
        line_U235.set_data(t2[:i], solucion235[:i, 0])
        line_Th231.set_data(t2[:i], solucion235[:i, 1])
        return line_U235, line_Th231

    anim2 = FuncAnimation(fig2, animate2, init_func=init2, frames=len(t2), interval=50, blit=True)
    plt.show()

    return None

def task5(N0, T):
    percentages = [0.9, 0.5, 0.1]
    T12_isotopes = T.copy()
    
    for T12_isotope in T12_isotopes:
        print()
        for p in percentages:
            t = 1
            N_new = N0 * p + 1
            while N_new > N0 * p:
                N_new = Decay(N0, T12_isotope[1], t)
                t *= 1.00006
            
            isotope_name = T12_isotope[0]
            unit = T12_isotope[2]
            
            if unit == 'y':  # Años
                print('Decaimiento del {} al {}%: {} años'.format(
                    isotope_name, p * 100, '%.2E' % Decimal(t)))
            elif unit == 'h':  # Horas
                print('Decaimiento del {} al {}%: {} horas'.format(
                    isotope_name, p * 100, '%.2E' % Decimal(t)))
            elif unit == 'd':  # Días
                print('Decaimiento del {} al {}%: {} días'.format(
                    isotope_name, p * 100, '%.2E' % Decimal(t)))

def main() -> None:  
    N0 = 1e6                    # nuclii
    
    T12_U238 = 4.468e9          # years
    T12_U235 = 703.8e6
    T12_Th231 = 25.52           # horas
    T12_Th234 = 24.1            # dias
    
    isotopos = [('Uranio 238', T12_U238, 'y'), ('Torio 234', T12_Th234, 'd'),
                ('Uranio 235', T12_U235, 'y'), ('Torio 231', T12_Th231, 'h')]

    #task1(N0, T12_U235, T12_U238)
    #task2(N0, T12_U235, T12_U238)
    #task3(N0, T12_U235, T12_U238)
    task4(N0, isotopos)
    #task5(N0, isotopos)

    plt.show()

if __name__ == '__main__':
    main()