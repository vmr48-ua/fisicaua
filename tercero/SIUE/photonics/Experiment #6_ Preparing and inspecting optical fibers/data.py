import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as scis
from statistics import linear_regression
from scipy.integrate import quad
import math as mth
import atexit

@atexit.register
def exit_guard() -> None:
    ...

def plot(x,y,title='',figure=True,figsize=(10,8),grid=False) -> None:
    if figure:
        plt.figure(figsize)
    if grid:
        plt.grid()
    plt.title(title)
    plt.plot(x,y)

def main() -> None:
    data = np.array([1.5,2.5,4,5,6.5])

    y = 4*632.8*10**(-9)*(157.5*1/data)
    x = [1,2,3,4,5]

    plt.scatter(x,y,marker='x')
    plt.axhline(np.average(y),)
    plt.show()

if __name__ == '__main__':
    main()