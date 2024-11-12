import numpy as np
import matplotlib.pyplot as plt
import time
from numpy import sqrt, sin, cos

'''
EJERCICIOS FÍSICA COMPUTACIONAL - BLOQUE II
INTRODUCCIÓN A LA MODELIZACIÓN EN FÍSICA

Víctor Mira Ramírez
74528754Z
vmr48@alu.ua.es
'''
# Funciones Auxiliares
def comparar(file1, file2, output_file):
    with open(file1, 'r') as f1, open(file2, 'r') as f2, open(output_file, 'w') as out:
        f1_lines = f1.readlines()
        f2_lines = f2.readlines()

        max_len = max(len(f1_lines), len(f2_lines))

        for i in range(max_len):
            line1 = f1_lines[i].strip() if i < len(f1_lines) else ""
            line2 = f2_lines[i].strip() if i < len(f2_lines) else ""

            if line1 != line2:
                out.write(f"Line {i + 1}:\n")
                out.write(f"- {file1}: {line1}\n")
                out.write(f"- {file2}: {line2}\n\n")
    file1.close()
    file2.close()
    output_file.close()

    print(f"Differences have been saved to {output_file}")

# Ejercicios
def ejercicioI() -> None:
    def generar_red_cristalina(tipo, nx, ny, nz):
        """
        Genera una red cristalina del tipo seleccionado:
            - 'cúbica simple'
            - 'fcc'
            - 'diamante'
            - 'bcc'
        """
        if tipo == 'cúbica simple':
            desplazamientos = [(0, 0, 0)]
        elif tipo == 'fcc':
            desplazamientos = [(0, 0, 0), (0.5, 0.5, 0), (0.5, 0, 0.5), (0, 0.5, 0.5)]
        elif tipo == 'diamante':
            desplazamientos =  [(0, 0, 0), (0.5, 0.5, 0.0), (0.5, 0, 0.5), (0, 0.5, 0.5),
                                (0.25, 0.25, 0.25), (0.75, 0.75, 0.25), (0.75, 0.25, 0.75), 
                                (0.25, 0.75, 0.75)]
        elif tipo == 'bcc':
            desplazamientos = [(0, 0, 0), (0.5, 0.5, 0.5)]
        else:
            raise Exception("Tipo de red no soportado. Use 'cúbica simple', 'f.c.c.', 'diamante', 'bcc' o 'sal'")

        centro = np.array([nx - 1, ny - 1, nz - 1]) / 2
        posiciones = []

        for i in range(nx):
            for j in range(ny):
                for k in range(nz):
                    for dx, dy, dz in desplazamientos:
                        posiciones.append(np.array([dx,dy,dz]) + np.array([i,j,k]) - centro)
        return np.array(posiciones)
    
    def generar_red_cristalina_sin_for(tipo, nx, ny, nz):
        """
        Genera una red cristalina del tipo seleccionado:
            - 'cúbica simple'
            - 'fcc'
            - 'diamante'
            - 'bcc'
        """
        if tipo == 'cúbica simple':
            desplazamientos = [(0, 0, 0)]
        elif tipo == 'fcc':
            desplazamientos = [(0, 0, 0), (0.5, 0.5, 0), (0.5, 0, 0.5), (0, 0.5, 0.5)]
        elif tipo == 'diamante':
            desplazamientos =  [(0, 0, 0), (0.5, 0.5, 0.0), (0.5, 0, 0.5), (0, 0.5, 0.5),
                                (0.25, 0.25, 0.25), (0.75, 0.75, 0.25), (0.75, 0.25, 0.75), 
                                (0.25, 0.75, 0.75)]
        elif tipo == 'bcc':
            desplazamientos = [(0, 0, 0), (0.5, 0.5, 0.5)]
        else:
            raise Exception("Tipo de red no soportado. Use 'cúbica simple', 'f.c.c.', 'diamante', 'bcc' o 'sal'")

        centro = np.array([nx-1, ny-1, nz-1])*0.5
        i,j,k = np.meshgrid(np.arange(nx), np.arange(ny), np.arange(nz), indexing='ij') # el indexing es para que tenga el mismo orden que en el método con bucles
        posiciones_base = np.stack([i,j,k], axis=3).reshape(nx*ny*nz,1,3) # nx*ny*nz ó -1 (pegamos el meshgrid) 
        posiciones = posiciones_base + desplazamientos - centro
        # posiciones = posiciones_base[:, None, :] + desplazamientos - centro
        
        # posiciones = []
        # for desplazamiento in desplazamientos:
        #     posiciones.extend(posiciones_base + desplazamiento - centro)

        return np.array(posiciones.reshape(len(desplazamientos)*nx*ny*nz,3)) # len(desplazamientos)*nx*ny*nz o -1
    
    def guardar_posiciones(posiciones, nombre_archivo='posiciones_red.txt'):
        """
        Guarda las posiciones de la red en un archivo de texto.
        """
        with open(nombre_archivo, 'w') as f:
            f.write(f'{len(posiciones)}\n')
            for i, (x, y, z) in enumerate(posiciones):
                f.write(f'{i}: {x:.4f} {y:.4f} {z:.4f}\n')

        print(f'Posiciones guardadas en {nombre_archivo}')
        f.close()
    
    def mostrar_red_3d(posiciones, tipo_red):
        """
        Muestra la red cristalina en 3D.
        """
        fig = plt.figure()
        ax = fig.add_subplot(111, projection='3d')
        ax.scatter(*posiciones.T, c='b', marker='.') # posiciones[:, 0], posiciones[:, 1], posiciones[:, 2]
        ax.set_xlabel('X')
        ax.set_ylabel('Y')
        ax.set_zlabel('Z')
        ax.set_title('Red Cristalina: ' + tipo_red)
        plt.show()

    nx = int(input('Longitud en x (nº ptos): '))
    ny = int(input('Longitud en y (nº ptos): '))
    nz = int(input('Longitud en z (nº ptos): '))
    tipo_red = str(input('Tipo de red (cúbica simple, fcc, diamante, bcc): '))
    
    t1 = time.perf_counter()
    posiciones = generar_red_cristalina(tipo_red, nx, ny, nz)  
    t2 = time.perf_counter()
    
    t3 = time.perf_counter()
    posiciones_mejor = generar_red_cristalina_sin_for(tipo_red, nx, ny, nz)
    t4 = time.perf_counter()
    
    print(t2-t1)
    print(t4-t3)
    
    """ 
    El tiempo de ejecución de la función sin bucles for es mucho menor 
    que el tiempo de ejecución de la función con bucles for:
    
    Longitud en x (nº ptos): 100
    Longitud en y (nº ptos): 100
    Longitud en z (nº ptos): 100
    Tipo de red (cúbica simple, fcc, diamante, bcc): diamante
    23.689733266830444  <- segundos de ejecución con bucles for
    0.34017109870910645 <- segundos de ejecución sin bucles for
    """    
    guardar_posiciones(posiciones, f'/home/victor/fisicaua/cuarto/posiciones_{tipo_red}.txt')
    guardar_posiciones(posiciones_mejor, f'/home/victor/fisicaua/cuarto/posiciones_mejor_{tipo_red}.txt')

    mostrar_red_3d(posiciones, tipo_red) 
    mostrar_red_3d(posiciones_mejor, tipo_red)

def ejercicioII() -> None:
    def aceleración(r):
        x, y = r
        R = sqrt(x**2 + y**2)
        ax = -GM*x / R**3
        ay = -GM*y / R**3
        return ax, ay
    
    # Constantes
    G = 6.67384e-11 # m^3 kg^-1 s^-2
    M = 1.9891e30   # kg (sol)
    m = 5.97219e24  # kg (tierra)
    GM = G*M

    # Condiciones Iniciales
    x = 1.4719e11  # m (perihelio)
    y = 0          # m
    vx = 0         # m/s
    vy = 3.0287e4  # m/s (tangencial)
    ax, ay = aceleración([x, y]) # m/s^2

    # Tiempo de simulación
    t_max = 5*365*24*3600 # 5y en s
    dt = 3600.            # 1h en s
    t_array = np.arange(0, t_max, dt)
    
    x_array, y_array, r_array = [],[],[]
    T_array, U_array, E_array = [],[],[]
    
    metodo = input('Introduce el método (Verlet (\'v\'), Euler (\'e\'), Runge Kutta II (\'rk2\')): ')
    t = 0
    while t < t_max:
        x_array.append(x)
        y_array.append(y)
        r_array.append(sqrt(x**2 + y**2))
        
        T = 0.5*m*(vx**2 + vy**2)
        U = -GM*m / sqrt(x**2 + y**2)
        T_array.append(T)
        U_array.append(U)
        E_array.append(T+U)
        
        if metodo == 'v': # Verlet
            x = x + vx*dt + 0.5*ax*dt**2
            y = y + vy*dt + 0.5*ay*dt**2
            ax_nueva, ay_nueva = aceleración([x, y])
            vx += 0.5 * (ax+ax_nueva)*dt
            vy += 0.5 * (ay+ay_nueva)*dt
            ax, ay = ax_nueva, ay_nueva
        elif metodo == 'e': # Euler
            ax, ay = aceleración([x, y])
            x = x + vx*dt
            y = y + vy*dt
            vx = vx + ax*dt
            vy = vy + ay*dt
        elif metodo == 'rk2': # Runge Kutta 2
            # paso 1
            ax, ay = aceleración([x, y])
            x_mid = x + 0.5*vx*dt
            y_mid = y + 0.5*vy*dt
            vx_mid = vx + 0.5*ax*dt
            vy_mid = vy + 0.5*ay*dt
            # paso 2
            ax_mid, ay_mid = aceleración([x_mid, y_mid])
            x = x + vx_mid*dt
            y = y + vy_mid*dt
            vx = vx + ax_mid*dt
            vy = vy + ay_mid*dt
        else:
            raise Exception("Usa 'Verlet', 'Euler', o 'RK2'")
        t += dt
    
    h, k = np.mean(x_array), np.mean(y_array) # Centro
    theta = np.linspace(0, 2*np.pi, 100)

    plt.figure(figsize=(10,10))
    plt.axes().set_aspect('equal')
    plt.plot(x_array, y_array, label='Órbita Terrestre', c='blue')
    plt.plot(x_array[0]*cos(theta), x_array[0]*sin(theta), label='Círculo de radio = perihelio', c='orange')
    plt.scatter(0,0,marker='x',c='orange',label='Centro del círculo')
    plt.scatter(h,k,marker='x',c='blue',label='Centro de la elipse')
    plt.xlabel("x (m)")
    plt.ylabel("y (m)")
    plt.title("Trayectoria de la Tierra alrededor del Sol")
    plt.legend(loc=(0.35,0.7))
    plt.grid()
    
    plt.figure()
    plt.plot(t_array, r_array, label="Radio orbital")
    plt.xlabel("Tiempo (s)")
    plt.ylabel("Radio (m)")
    plt.title("Radio en función del tiempo")
    plt.legend(loc='upper right')
    plt.grid()
    
    plt.figure()
    plt.plot(t_array, T_array, label="Energía cinética")
    plt.plot(t_array, U_array, label="Energía potencial")
    plt.plot(t_array, E_array, label="Energía total")
    plt.xlabel("Tiempo (s)")
    plt.ylabel("Energía (J)")
    plt.title("Energía en función del tiempo")
    plt.legend(loc=(0.62,0.72))
    plt.grid()
    
    plt.show()
    
    return None


def main() -> None:
    # ejercicioI()    
    # comparar(f'/home/victor/fisicaua/cuarto/posiciones_diamante.txt', f'/home/victor/fisicaua/cuarto/posiciones_mejor_diamante.txt', "/home/victor/fisicaua/cuarto/differences.txt")
    ejercicioII()

if __name__ == '__main__':
    main()