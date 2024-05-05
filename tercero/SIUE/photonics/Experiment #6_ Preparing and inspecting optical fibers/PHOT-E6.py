import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as scis
from statistics import linear_regression
from scipy.integrate import quad
import math as mth
import atexit

@atexit.register
def exit_guard() -> None:
    plt.show()

def main() -> None:
    data = np.array([1.5,2.5,4,5,6.5])

    y = 4*632.8*10**(-9)*(157.5*1/data)
    x = [1,2,3,4,5]

    plt.scatter(x,y,marker='x')
    plt.axhline(np.average(y))
    plt.xlabel('m')
    plt.ylabel('D(m)')
    print(np.average(y))

if __name__ == '__main__':
    main()