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
    factor = {'y':365*24*3600, 'd':24*3600, 'h':3600, 's':1}
    return t12 * factor[unidad]

def Decay(N0,T12,t):
    λ = landa(T12)
    return N0 * exp(-λ*t)

def Activity(T12,N):
    return landa(T12) * N


# Tasks
def task1(N0, T12_U235, T12_U238):
    t = 1e10 # years
    
    print('Given a sample of Uranium isotopes of N0 = {} nuclei:'.format('%.E' % Decimal(N0)))
    print('  Remaining quantity of Uranium-235 after {} years: {} nuclei'.format('%.E' % Decimal(t),'%.3E' % Decimal(Decay(N0,T12_U235,t))))
    print('  Remaining quantity of Uranium-238 after {} years: {} nuclei'.format('%.E' % Decimal(t),'%.3E' % Decimal(Decay(N0,T12_U238,t))))
    
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
    
    plt.figure(figsize=(10,5), tight_layout=True)
    plt.suptitle('Uranium Isotopes Decay Curves for $N_0=10^6$ nuclei')
    
    plt.subplot(1,2,1)
    plt.title('Logaritmic')
    plt.plot(t_log,decayU238_log, alpha=0.5, linestyle='--', color='#01C0CC')
    plt.plot(t_log,decayU235_log, alpha=0.5, linestyle='--', color='#FF7F0E')
    plt.scatter(t_log,decayU238_log, label='$U^{238}$', marker='x', color='#01C0CC')
    plt.scatter(t_log,decayU235_log, label='$U^{235}$', marker='x', color='#FF7F0E')
    plt.yscale("log")
    plt.xlabel('Years')
    plt.ylabel('Remaining Nuclei')
    plt.grid()
    plt.legend(loc='upper right')
    
    ax = plt.subplot(1,2,2)
    plt.title('Exponential')
    ax.yaxis.tick_right()
    ax.yaxis.set_ticks_position('right')
    ax.yaxis.set_label_position("right")
    plt.plot(t,decayU238, alpha=0.5, linestyle='--', color='#01C0CC')
    plt.plot(t,decayU235, alpha=0.5, linestyle='--', color='#FF7F0E')
    plt.scatter(t,decayU238, label='$U^{238}$', marker='x', color='#01C0CC')
    plt.scatter(t,decayU235, label='$U^{235}$', marker='x', color='#FF7F0E')
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
    
    activityU235 = Activity(T12_U235, Decay(N0, T12_U235, t))*365*24*60*60/1e3 # kiloBecquerels
    activityU238 = Activity(T12_U238, Decay(N0, T12_U238, t))*365*24*60*60/1e3
    activityU235_log = Activity(T12_U235, Decay(N0, T12_U235, t_log))*365*24*60*60
    activityU238_log = Activity(T12_U238, Decay(N0, T12_U238, t_log))*365*24*60*60
    
    
    plt.figure(figsize=(10,5),tight_layout=True)
    plt.suptitle('Uranium Activity Curves for $N_0=10^6$ nuclei')

    plt.subplot(1,2,1)
    plt.title('Logaritmic')
    plt.plot(t_log,activityU238_log, linestyle='--', alpha=0.5, color = '#01C0CC')
    plt.plot(t_log,activityU235_log, linestyle='--', alpha=0.5, color = '#FF7F0E')
    plt.scatter(t_log,activityU238_log, label='$U^{238}$', color = '#01C0CC', marker='x')
    plt.scatter(t_log,activityU235_log, label='$U^{235}$', color = '#FF7F0E', marker='x')
    plt.xlabel('Years')
    plt.ylabel('Activity ($Bq$)')
    plt.yscale("log")
    plt.grid()
    plt.legend(loc='upper right')
    
    ax = plt.subplot(1,2,2)
    plt.title('Exponential')
    ax.yaxis.tick_right()
    ax.yaxis.set_ticks_position('right')
    ax.yaxis.set_label_position("right")
    plt.plot(t,activityU238, linestyle='--', alpha=0.5, color = '#01C0CC')
    plt.plot(t,activityU235, linestyle='--', alpha=0.5, color = '#FF7F0E')
    plt.scatter(t,activityU238, label='$U^{238}$', color = '#01C0CC', marker='x')
    plt.scatter(t,activityU235, label='$U^{235}$', color = '#FF7F0E', marker='x')
    plt.xlabel('Years')
    plt.ylabel('Activity ($kBq$)')
    plt.grid()
    plt.legend(loc='upper right')
    return None

def task4(N0, isotopos):
    isotopos_seg = [(name, convertir_a_segundos(T12, unidad)) for name, T12, unidad in isotopos]
    
    t_max = convertir_a_segundos(1e10, 'y')
    t_points = 1000
    t = np.linspace(0, t_max, t_points)
    
    decays = {}
    activities = {}
    
    for i, (name, T12) in enumerate(isotopos_seg):
        if name == 'Uranium 238':
            daughter_name = 'Torium 234'
        elif name == 'Uranium 235':
            daughter_name = 'Torium 231'
        else:
            continue
        
        N_parent = Decay(N0, T12, t)
        N_daughter = N0 - N_parent
        A_parent = Activity(T12, N_parent)
        A_daughter = Activity(T12, N_daughter)
        
        decays[name] = N_parent
        decays[daughter_name] = N_daughter
        activities[name] = A_parent
        activities[daughter_name] = A_daughter
    
    plt.figure(figsize=(10, 5))
    plt.subplot(1, 2, 1)
    for name, N in decays.items():
        plt.plot(t / convertir_a_segundos(1, 'y'), N, label=name)
    plt.xlabel('Time (years)')
    plt.ylabel('Number of Nuclei')
    plt.title('Number of Nuclei Over Time')
    plt.legend()
    plt.grid()
    
    plt.subplot(1, 2, 2)
    for name, A in activities.items():
        plt.plot(t / convertir_a_segundos(1, 'y'), A, label=name)
    plt.xlabel('Time (years)')
    plt.ylabel('Activity (Bq)')
    plt.title('Activity Over Time')
    plt.legend()
    plt.grid()
    
    plt.tight_layout()
    plt.show()

def task5(N0, T):
    percentages = [0.9, 0.5, 0.1]
    unidades = {'y':'years', 'h':'hours', 'd':'days', 's':'seconds'}
    
    for nombre, T12, unidad in T:
        print(f"\nDecay of {nombre}")
        for p in percentages:
            t, N_new = 1, N0*p + 1 # +1 para que entre en el while
            while N_new > N0*p:
                N_new = Decay(N0, T12, t)
                t *= 1.00006 # de esta forma sea la unidad que sea el rango es razonable
            print(f"  to {p*100}%: {Decimal(t):.3E} {unidades[unidad]}")

def main() -> None:  
    N0 = 1e6                    # nuclii
    
    T12_U238 = 4.468e9          # years
    T12_U235 = 703.8e6
    T12_Th231 = 25.52           # horas
    T12_Th234 = 24.1            # dias
    
    isotopos = [('Uranium 238', T12_U238, 'y'), ('Torium 234', T12_Th234, 'd'),
                ('Uranium 235', T12_U235, 'y'), ('Torium 231', T12_Th231, 'h')]

    task1(N0, T12_U235, T12_U238)
    task2(N0, T12_U235, T12_U238)
    task3(N0, T12_U235, T12_U238)
    task4(N0, isotopos)
    task5(N0, isotopos)

    plt.show()

if __name__ == '__main__':
    main()