import numpy as np 
import matplotlib.pyplot as plt


############################
#          TASK 3          #
############################

task3_1 = [(0,938.5),(6,957.4),(12,954.2),(18,932.2),(24,889.9),(30,831.3),(36,754.2),(42,657.4),(48,572.0),(54,475.0),(60,372.2),(66,244.7),(72,167.0),(78,95.48),(84,46.92),(90,14.2)]
task3_2 = [(0,0.5004),(6,9.510),(12,31.38),(18,61.00),(24,91.82),(30,117.7),(36,135.9),(42,137.1),(48,127.6),(54,106.7),(60,75.29),(66,44.71),(72,17.26),(78,2.866),(84,2.01),(90,14.45)]
x = np.linspace(-10,100,1000)
I_0 = task3_1[0][1]

# Task 3.1
plt.figure()
plt.title('Light intensity after passing through \n two polarizers at $0^\circ$ to $90^\circ$')

plt.xlim((-2,92))
plt.ylim((-20,1000))
plt.xlabel('$\\theta$ (deg)')
plt.ylabel('I ($\mu W$)')

theta = np.array([point[0] for point in task3_1])
plt.scatter(theta, [point[1] for point in task3_1],c='b',marker='x',label='Measured intensity')

def mallus(angle):
    angle = (np.pi/180)*angle
    return I_0*np.cos(angle)**2
x31 = np.linspace(0,90,1000)
plt.plot(x31,mallus(x31),c='orange',label='Mallus Law')

plt.legend(loc='best')


# Task 3.2

plt.figure()
plt.title('Light intensity after passing cross polarizers \n with a polarizer in between at $0^\circ$ to $90^\circ$')

plt.xlim((-2,92))
plt.ylim((-2,150))
plt.xlabel('$\\theta$ (deg)')
plt.ylabel('I ($\mu W$)')

theta = np.array([point[0] for point in task3_2])
plt.scatter(theta, [point[1] for point in task3_2],c='r',marker='x',label='Measured intensity')

x32 = np.linspace(0,90,1000)
plt.plot(x32,140*np.sin((np.pi/180)*x32*2)**2)

plt.show()