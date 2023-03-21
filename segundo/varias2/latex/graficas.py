import matplotlib.pyplot as plt
from numpy import arctan
import numpy as np

def f1(x):
    return arctan(x-3)+4

x = np.linspace(1,6)
plt.figure(figsize=(10,6))
plt.plot(x,f1(x),c='k')
plt.scatter([1,6],[f1(1),f1(6)],c='k')
plt.annotate('A',[1,f1(1)+0.2],fontsize=18)
plt.annotate('B',[6,f1(6)+0.2],fontsize=18)
plt.vlines(x=1, ymin = 0, ymax= f1(1), color='k', linestyle='--')
plt.vlines(x=6, ymin = 0, ymax= f1(6), color='k', linestyle='--')
plt.annotate("", [2.5,f1(2.5)], xytext=(2.49, f1(2.49)),arrowprops=dict(arrowstyle="->"),fontsize=30)
plt.annotate("", [4.5,f1(4.5)], xytext=(4.49, f1(4.49)),arrowprops=dict(arrowstyle="->"),fontsize=30)
plt.xticks([1, 6], ['a', 'b'])
plt.yticks([],[])
plt.axis('off')
plt.xlim(0,7)
plt.ylim(2,6)
plt.savefig('latex/integraldelinea.png')