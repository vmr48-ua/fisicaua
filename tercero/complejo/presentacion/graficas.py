import matplotlib.pyplot as plt
import numpy as np

# Datos de la ecuación cuadrática: x^2 + 6x = 7
a = 1
b = 6
c = -7

# Crear una figura y ejes
fig, ax = plt.subplots()

# Definir los valores de x
x = np.linspace(-4, 2, 100)

# Ecuación de la parábola
y = a * x**2 + b * x + c

# Ecuación del rectángulo
rect_x = np.array([-3, -3, 1, 1, -3])
rect_y = np.array([0, 6, 6, 0, 0])

# Dibujar la parábola
ax.plot(x, y, label='y = x^2 + 6x - 7')

# Dibujar el rectángulo
ax.plot(rect_x, rect_y, linestyle='--', label='Rectángulo')

# Rellenar el área del cuadrado
ax.fill_between(x, y, 6, where=(y >= 0), interpolate=True, alpha=0.2)

# Etiquetas de ejes y título
ax.set_xlabel('x')
ax.set_ylabel('y')
ax.set_title('Completando el Cuadrado para Resolver la Ecuación Cuadrática')

# Mostrar leyenda
ax.legend()

# Mostrar la gráfica
plt.grid(True)
plt.show()





# Gráfica 1: Cuadrado unitario
# Dibujar el cuadrado unitario
ax.add_patch(plt.Rectangle((0, 0), 1, 1, fill=False, color='blue', linewidth=2))
ax.text(0.5, 0.5, '1', fontsize=20, ha='center', va='center', color='blue')

# Estilizar el aspecto de la gráfica
ax.set_xlim(0, 1.5)
ax.set_ylim(0, 1.5)
ax.axis('off')

# Añadir un título
ax.set_title('Cuadrado Unitario', fontsize=16)

# Mostrar la gráfica
plt.grid(False)
plt.show()

# Gráfica 2: Números Imaginarios en el Plano Complejo
fig, ax = plt.subplots()
ax.axhline(0, color='black', linewidth=0.5)
ax.axvline(0, color='black', linewidth=0.5)
ax.quiver(0, 0, 1, 1, angles='xy', scale_units='xy', scale=1, color='b')
ax.text(1, 1, '1 + i', fontsize=12, ha='right', va='bottom')
ax.set_xlim(-1, 2)
ax.set_ylim(-1, 2)
ax.set_aspect('equal', adjustable='box')
ax.set_title('Números Imaginarios en el Plano Complejo')
plt.show()

# Gráfica 3: Completando el Cuadrado para x^2 + 6x + 9
fig, ax = plt.subplots()
x = np.linspace(-1, 3, 400)
y = x**2 + 6*x + 9
ax.plot(x, y, label='y = x^2 + 6x + 9')
ax.fill_between(x, y, 9, where=(y >= 0), interpolate=True, alpha=0.2)
ax.annotate('Completa el Cuadrado', xy=(1.5, 6.5), fontsize=12, color='r')
ax.set_xlabel('x')
ax.set_ylabel('y')
ax.legend()
ax.grid(True)
ax.set_title('Completando el Cuadrado: y = x^2 + 6x + 9')
plt.show()
