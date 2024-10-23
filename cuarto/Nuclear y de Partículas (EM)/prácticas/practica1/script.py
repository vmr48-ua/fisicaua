import matplotlib.pyplot as plt

# Example binding energies for illustration
B_U238 = 1798.9  # Binding energy of Uranium-238
B_Kr92 = 775.98  # Binding energy of Krypton-92
B_Ba141 = 1163.47  # Binding energy of Barium-141

# Labels and binding energies
labels = ['Uranium-238', 'Krypton-92', 'Barium-141', 'Fission Products\n(Kr-92 + Ba-141)']
binding_energies = [B_U238, B_Kr92, B_Ba141, B_Kr92 + B_Ba141]

# Define the colors for Krypton and Barium
colors = ['blue', 'green', 'orange', 'none']  # Uranium, Krypton, Barium, and fission product bar (initially 'none')

plt.figure(figsize=(10, 5))

# Plot the bars for Uranium-238, Krypton-92, and Barium-141
plt.bar(labels[:3], binding_energies[:3], color=colors[:3])

# Plot Krypton and Barium's contributions in the "Fission Products" bar
plt.bar(labels[3], B_Kr92, color='green', label='Krypton-92')
plt.bar(labels[3], B_Ba141, bottom=B_Kr92, color='orange', label='Barium-141')

# Adding labels and title
plt.ylabel('Binding Energy (MeV)')
plt.title('Comparison of Binding Energies\nfor the Fission of Uranium-238')

# Displaying the plot
plt.legend()
plt.show()
