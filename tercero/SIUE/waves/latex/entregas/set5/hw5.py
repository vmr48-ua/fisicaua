import numpy as np
import matplotlib.pyplot as plt

# Constants
L = 1.0  # Length of the string
T1 = 2 * L  # Period of the lowest mode (fundamental frequency)

# Calculate angular frequencies
omega1 = 2 * np.pi / T1
omega2 = 2 * omega1
omega3 = 3 * omega1

# Position along the string (0 to L)
z_values = np.linspace(0, L, 1000)

# Calculate superposition at different times
times = [0, T1 / 4, T1 / 2, 3 * T1 / 4, 2 * T1]
superposition_values = []
for t in times:
    superposition = (
        np.sin(np.pi * z_values) * np.cos(omega1 * t)
        + np.sin(2 * np.pi * z_values) * np.cos(omega2 * t)
        + (1 / 3) * np.sin(3 * np.pi * z_values) * np.cos(omega3 * t)
    )
    superposition_values.append(superposition)

# Plot the superposition at different times
plt.figure()
plt.plot(z_values, superposition_values[0], label=f"t = {times[0]:.2f} s")
plt.xlabel("Position along the string (z)")
plt.ylabel("Amplitude")
plt.legend()
plt.grid(True)

fig, axs = plt.subplots(2, 2)
axs[0, 0].plot(z_values, superposition_values[1], label=f"t = T/4 = {times[1]:.2f} s")
axs[0, 1].plot(z_values, superposition_values[2], label=f"t = T/2 = {times[2]:.2f} s")
axs[1, 0].plot(z_values, superposition_values[3], label=f"t = 3T/4 = {times[3]:.2f} s")
axs[1, 1].plot(z_values, superposition_values[4], label=f"t = 2T = {times[4]:.2f} s")
for ax in axs.flat:
    ax.set(xlabel='Position along the string (z)', ylabel='Amplitude')
    ax.grid(True)
    ax.legend()
plt.show()

import numpy as np
import matplotlib.pyplot as plt

# Length of the string
L = 1.0  

# X coordinates representing points along the string
x = np.linspace(0, L, 1000)

# First normal mode shape (fundamental frequency)
y1 = np.sin(np.pi * x / L)

# Second normal mode shape (first overtone)
y2 = np.sin(2 * np.pi * x / L)

plt.figure(figsize=(10, 5))

# Plotting the first normal mode shape
plt.subplot(2, 1, 1)
plt.plot(x, y1)
plt.axhline(0, color='black', linewidth=0.5)  
plt.title('First Normal Mode')
plt.ylabel('Displacement')

# Plotting the second normal mode shape
plt.subplot(2, 1, 2)
plt.plot(x, y2)
plt.axhline(0, color='black', linewidth=0.5)  
plt.title('Second Normal Mode')
plt.xlabel('Position along String')
plt.ylabel('Displacement')

# Display plots
plt.tight_layout()
plt.show()