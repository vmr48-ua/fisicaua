import matplotlib.pyplot as plt
import numpy as np

# Define the function
def f(x):
    return x

# Generate x values
x = np.linspace(-3, 7, 1000)

# Generate y values
y = f(x % 10 - 5)  # Shift the function to be centered around x=0

# Create the plot
plt.figure(figsize=(8, 6))
plt.plot(x, y, label='f(x) = x')
plt.title('Plot of the function f(x) = x on interval [-3, 7] with repeat distance 10')
plt.xlabel('x')
plt.ylabel('f(x)')
plt.grid(True)
plt.legend()
plt.show()