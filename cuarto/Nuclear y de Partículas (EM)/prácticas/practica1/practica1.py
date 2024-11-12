import numpy as np
from numpy import sqrt
import matplotlib.pyplot as plt

'''
FÍSICA NUCLEAR Y DE PARTÍCULAS - PRÁCTICA I

Víctor Mira Ramírez
74528754Z
vmr48@alu.ua.es
'''

def SemiEmpirical_MassFormula(Z,A) -> float:
    '''
    Given Z and A, returns Binding Energy in MeV
    '''
    a_v = 15.8
    a_s = 18.3
    a_c = 0.714
    a_a = 23.2
    if A % 2 == 0:
        if Z % 2 == 0 and (A - Z) % 2 == 0:  # Z, N even
            a_p = 11.2 / sqrt(A)
        elif Z % 2 == 1 and (A - Z) % 2 == 1:  # Z, N odd
            a_p = -11.2 / sqrt(A)
        else:
            a_p = 0
    else:
        a_p = 0
        
    B =  (a_v*A
        - a_s*A**(2/3)
        - a_c*Z**2/A**(1/3)
        - a_a*(A-2*Z)**2/A 
        + a_p)
    
    return B

def partI()  -> None:
    Z_Uranium = 92
    A_U235 = 235
    A_U238 = 238
    
    Ba_U235 = np.round(SemiEmpirical_MassFormula(Z_Uranium,A_U235)/A_U235,3)
    Ba_U238 = np.round(SemiEmpirical_MassFormula(Z_Uranium,A_U238)/A_U238,3)
    
    print('Binding erergy per nucleon of Uranium-235:', Ba_U235, 'MeV')
    print('Binding erergy per nucleon of Uranium-238:', Ba_U238, 'MeV')
    
    '''
    The higher the binding energy per nucleon, the more stable the nucleus.
    According to the results, U235 has a slightly higher binding energy per nucleon 
    than U238, indicating that U235 is slightly more stable based on this criterion.
    '''
    
    SEMF_vectorized = np.vectorize(SemiEmpirical_MassFormula)
    plt.subplot(1,2,1)
    A_range = np.arange(218,242)
    
    # Literature
    literature = np.array([(218,7.6407980275229),(220,7.6395274772727),(222,7.6377162072072),
                           (219,7.6366875342466),(221,7.6345700904977),(223,7.6327566636771),
                           (224,7.6352669866071),(225,7.6297764755556),(226,7.6319142566372),
                           (227,7.62646269163),  (228,7.6209764565217),(229,7.6207637860262),
                           (231,7.6134022640693),(232,7.6119347931034),(233,7.6039839957082),
                           (234,7.6007367008547),(235,7.590937187234), (236,7.5865044025424),
                           (237,7.5761219156118),(238,7.5701458067227),(239,7.5585809790795),
                           (240,7.5517985041667),(241,7.5394813900415),(242,7.5316898553719)])
    plt.scatter(literature[:,0], literature[:,1], marker='+', c='k', label='Literature', alpha=0.4)
    plt.scatter(235, 7.590937187234, label='Literature B/A (U235): 7.591 MeV', marker='+', c='red')
    plt.axhline(7.590937187234,c='red',linestyle='--', alpha=0.25)
    plt.scatter(238, 7.5701458067227, label='Literature B/A (U238): 7.570 MeV', marker='+', c='blue')
    plt.axhline(7.5701458067227,c='blue',linestyle='--', alpha=0.25)
    
    # SEMF
    plt.scatter(A_range, SEMF_vectorized(Z_Uranium,A_range)/A_range, label='SEMF', marker='x', c='k', alpha=0.4)
    plt.scatter(235, SemiEmpirical_MassFormula(Z_Uranium,A_U235)/A_U235, 
                label='B/A (U235): {} MeV'.format(Ba_U235), marker='x', c='red')
    plt.axhline(SemiEmpirical_MassFormula(Z_Uranium,A_U235)/A_U235,c='red',linestyle='--')
    plt.scatter(238, SemiEmpirical_MassFormula(Z_Uranium,A_U238)/A_U238,
                label='B/A (U238): {} MeV'.format(Ba_U238), marker='x', c='blue')
    plt.axhline(SemiEmpirical_MassFormula(Z_Uranium,A_U238)/A_U238,c='blue',linestyle='--')

    
    plt.xlabel('Massic Number A')
    plt.ylabel('Binding Energy per nucleon (MeV)')
    plt.title('Uranium Binding Energy per nucleon as a function of A')
    plt.legend()
    plt.grid()

def partII() -> None:
    Z_Uranium = 92
    Z_Krypton = 36
    Z_Barium = 56
    
    A_U238 = 238
    A_Kr92 = 92
    A_Ba141 = 141
    
    # TASK I
    B_U238 = np.round(SemiEmpirical_MassFormula(Z_Uranium,A_U238),3)
    B_Kr92 = np.round(SemiEmpirical_MassFormula(Z_Krypton,A_Kr92),3)
    B_Ba141 = np.round(SemiEmpirical_MassFormula(Z_Barium,A_Ba141),3)
    
    print('Binding erergy of Uranium-238:', B_U238,  'MeV')
    print('Binding erergy of Krypton-92: ', B_Kr92,  'MeV')
    print('Binding erergy of Barium-141: ', B_Ba141, 'MeV')

    # TASK II
    print()
    '''
    To ascertain wether the fission process is energeticaly favourable, we will
    check if the process is spontaneous. To do so we calculate the binding energy 
    of the fission products (Kr92 and Ba141) and the binding energy of the fission
    fuel (U238). If the binding energy of the products is greater that the binding 
    energy of the fuel, the reaction is energetically favourable.
    '''
    
    if B_U238 < (B_Kr92 + B_Ba141):
        print('The fission process of U238 is energetically favourable')
    else:
        print('The fission process of U238 is not energetically favourable')
    
    # TASK III
        # a)
    print('The energy released whet U238 is fissioned is: ', np.round((B_Kr92+B_Ba141) - B_U238,3), 'MeV')
    print('The percentage of energy released from the original mass is: ',np.round(((B_Kr92+B_Ba141) - B_U238)*100/B_U238,2), '%')
        # b)
    '''
    Heavier nuclii such as U238 tend to undergo fission because of their binding energy
    per nucleon. If we recall the plot with Z on the x-axis and B/A on the y-axis, we 
    saw that binding energy per nucleon has a peak around mid-sized nuclii such as Fe,
    and then it starts to decay as Z agumented. 
    
    This means that when heavy nuclii divide into midzsized ones, the greater binding energy
    per nucleon of the products results in total binding energy being greater on them than on 
    the original binding energy of the fuel. Consequently fission impiles a release of energy.
    
    This release of energy makes the process energetically favourable as we discussed in the
    second task, and is why heavy nuclii are prone to undergo fission spontaneously.
    
    Moreover, the bigger the nuclii the more protons it will contain, and Coulomb repulsion
    grows faster than the strong force can keep up with making this nuclii further unstable.
    '''
    
    # Task IV
    labels = ['Uranium238', 'Krypton92', 'Barium141', 'Fission Products\n(Kr92 + Ba141)']
    binding_energies = [B_U238, B_Kr92, B_Ba141, B_Kr92+B_Ba141]

    plt.subplot(1,2,2)
    plt.bar(labels[:3], binding_energies[:3], color=['lime', 'lemonchiffon', 'powderblue']) # The first three are coloured normally
    plt.bar(labels[3], B_Kr92, color='lemonchiffon') # The products are plotted one at a time so that they stack
    plt.bar(labels[3], B_Ba141, bottom=B_Kr92, color='powderblue')
    
    plt.ylabel('Binding Energy (MeV)')
    plt.title('Comparison of Binding Energies\n for the fission of U238')    

def main() -> None:
    plt.figure(figsize=(18,9))
    
    partI()
    partII()
    
    # Final Question
    '''
    In nuclear reactors, this energy is released in a controlled manner to heat water, 
    produce steam which move turbines which then generate electricity. The fission process
    keeps going thanks to neutrons emmited from a fission event that start new fissions 
    on other fuel atoms. 

    In atomic bombs, the process is the same but the controlled part is lost.
    Uncontrolled chain reactions release a large amount of energy in a short time, provoking
    an explosion.

    Although only 8% of the binding energy gets released, the process emits such high
    quantities of energy because of the trillions of nuclii that undergo fission in both 
    fission nuclear energy generation plants and fission nuclear explosion bombs.
    '''
    
    plt.show()

if __name__ == '__main__':
    main() 