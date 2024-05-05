import numpy as np
import matplotlib.pyplot as plt
import atexit

@atexit.register
def exit_guard() -> None:
    plt.show()

def redondea(x:float) -> None:
    raise NotImplementedError()

def main() -> None:
    plt.figure()
    deg = np.array([314,    315,   316,   317,   318,   319,   320,   321,   322,   323,   324,   325,   326,   327,   328,   329,  330,  331,  332,  333,   334,   335, 
                    313,    312,   311,   310,   309,   308,   307,   306,   305,   304,   303,   302,   301,   300,   299,   298,  297,  296,  295,  294,   293,   292]) #deg
    data = np.array([53.10, 52.90, 52.3, 51.00, 49.66, 48.3, 46.35, 43.50, 41.05, 37.70, 34.13, 31.02, 26.41, 21.69, 15.03, 7.58, 3.04, 1.18, 0.61, 0.421, 0.308, 0.251,
                     52.48, 52.34, 51.32, 50.04, 48.27, 45.90, 43.41, 40.52, 38.04, 34.96, 31.00, 26.60, 20.75, 13.26, 7.58,  2.84, 1.18, 0.95, 0.84, 0.76,  0.67,  0.58]) #microW
    plt.scatter(deg,data,marker='x')

    sin_theta = np.sin(np.deg2rad(360 - deg))

    # Plot data on a semi-log scale
    plt.figure(figsize=(10,6))
    plt.semilogy(sin_theta, data, 'x')
    plt.grid(True, which="both", ls="--")
    plt.title('Power vs. Sine of Acceptance Angle')
    plt.xlabel('Sine of Acceptance Angle')
    plt.ylabel('Power (microW)')

    # Calculate the full width at 5% of the maximum power
    max_power = np.max(data)
    print(max_power)
    five_percent_power = 0.05 * max_power
    plt.axhline(five_percent_power)
    plt.axvline(0.498,linestyle='--')
    plt.axvline(0.883,linestyle='--')

    print(f'Numerical Aperture (NA) = {(0.883-0.498)/2}')

    plt.figure()
    part2 = np.array([7.6, 9.6, 11.1, 13.2]) -0.41#-0.41cm
    diameter = np.array([2.0, 3.0, 4.0, 5.0]) #cm
    plt.scatter(2*part2,diameter/2)
    L =  np.sin(0.3)/2.5 
    print(L)
    ...
    

if __name__ == '__main__':
    main()