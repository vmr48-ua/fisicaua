import numpy as np
import matplotlib.pyplot as plt
import atexit
import scipy.stats as scis
import numba
#@numba.njit

@atexit.register
def exit_guard() -> None:
    plt.show()

def redondea(x:float) -> None:
    raise NotImplementedError()

def main() -> None: 
    # PART 1
    intensity = np.array([225, 241, 251,  264, 279, 301, 319, 340, 359, 383, 410, 442, 462, 474, 494, 517, 541, 560, 585, 610, 642, 674, 516, 745, 799, 833,  890, 942, 1009, 1086, 1188, 1350, 1545, 1606, 2251, 2911, 3890, 5335, 7764, 12640, 25040, 61320, 83510, 75380, 33400, 11510, 6340,  4534, 3350]) #nanoW
    distance = np.array([  29,  28,  27,   26,  25,  24,  23,  22,  21,  20,  19,  18, 17.5, 17,16.5,  16,15.5,  15,14.5,  14,13.5,  13,12.5,  12,11.5,  11,10.5,   10,  9.5,    9,  8.5,    8,  7.5,    7,  6.5,    6,  5.5,    5,  4.5,     4,   3.5,     3,   2.8,   2.6,   2.4,   2.3,  2.2,   2.1,    2]) #cm
    distance = distance -2
    plt.figure()
    plt.xlabel('Distance ($cm$)')
    plt.ylabel('Intensity ($nW$)')
    plt.scatter(distance,intensity,marker='x')
    plt.grid()

    plt.figure()
    plt.xlabel('Distance Inversely Squared ($cm^{-2}$)')
    plt.ylabel('Intensity ($nW$)')
    plt.scatter(1/(distance[:48]**2),intensity[:48],marker='x')
    plt.grid()
    
    plt.figure()
    plt.xlabel('Distance Inversely Squared ($cm^{-2}$)')
    plt.ylabel('Intensity ($nW$)')
    P = np.polyfit(1/(distance[:43]**2),intensity[:43],1)
    Q = np.polyfit(1/distance[42:46]**2,intensity[42:46],1)
    xp = np.linspace(0,1.5,1000)
    xq = np.linspace(1.5,12,1000)
    plt.plot(xp,np.polyval(P,xp),c='k')
    plt.plot(xq,np.polyval(Q,xq),c='r')
    plt.scatter(1/(distance[:48]**2),intensity[:48],marker='x')
    plt.grid()
    
    def plot_cones(distance):
        # Define the angles for the incident and reflected light cones
        incident_angle = np.linspace(-np.pi/4, np.pi/4, 100)
        reflected_angle = np.linspace(np.pi/4, 3*np.pi/4, 100)

        # Define the radii for the incident and reflected light cones
        incident_radius = distance * np.abs(np.tan(incident_angle))
        reflected_radius = distance * np.abs(np.tan(reflected_angle))

        # Plot the incident light cone
        plt.plot(incident_radius*np.cos(incident_angle), incident_radius*np.sin(incident_angle), 'r')
        plt.fill_between(incident_radius*np.cos(incident_angle), 0, incident_radius*np.sin(incident_angle), color='r', alpha=0.1)

        # Plot the reflected light cone
        plt.plot(reflected_radius*np.cos(reflected_angle), reflected_radius*np.sin(reflected_angle), 'b')
        plt.fill_between(reflected_radius*np.cos(reflected_angle), 0, reflected_radius*np.sin(reflected_angle), color='b', alpha=0.1)

        # Set the plot limits and labels
        plt.xlim(-distance, distance)
        plt.ylim(0, 2*distance)
        plt.xlabel('Horizontal Distance')
        plt.ylabel('Vertical Distance')
        plt.title(f'Light Cones for Distance = {distance}')
        plt.show()

    """plot_cones(1)
    plot_cones(2)
    plot_cones(3)"""


    # PART 2
    # maxI = 2.49 #mW
    I = np.array([2.42, 2.16, 1.76, 1.32, 1.16, 0.791, 0.331, 0.220,  0.055]) #mW
    knob = np.array([12.5, 25, 37.5,   50, 62.5,    75,  87.5,   100, 112.5]) #micro m

    plt.figure()
    plt.xlabel('Spikes movement ($\mu m$)')
    plt.ylabel('Intensity ($mW$)')
    plt.scatter(knob,I,marker='x')
    plt.grid()
    P = np.polyfit(knob,I,1)
    result = scis.linregress(knob,I)
    xo = np.linspace(0,120,1000)
    plt.plot(xo,np.polyval(P,xo),label='-0.02489x + 2.69 R²={}'.format(np.round(result.rvalue**2,4)))
    plt.legend(loc='best')    

    """ 
    m = 0.02489
    def f(p):
        return maxI*(1-m*p)
    plt.plot(knob,f(knob))
    """


if __name__ == '__main__':
    main()