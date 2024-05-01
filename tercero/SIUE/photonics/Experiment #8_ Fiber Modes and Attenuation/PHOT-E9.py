import numpy as np
import matplotlib.pyplot as plt
import atexit

@atexit.register
def exit_guard() -> None:
    plt.show()

def redondea(x:float) -> None:
    raise NotImplementedError()

def main() -> None:
    Intensity_out = 158.5  #microW
    Intensity_in = 480 #microWatt
    L = 500 #m
    savings = np.array([1,2,3,2,0.6,0.6,0.55,0.31,0.605,0.525,0.479,0.5,0.55])
    for cut in savings:
        L -= cut
    print(L) #m
    
if __name__ == '__main__':
    main()