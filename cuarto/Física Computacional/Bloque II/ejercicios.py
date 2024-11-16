import numpy as np
import matplotlib.pyplot as plt
import time
from numpy import sqrt, sin, cos
from scipy.integrate import odeint, solve_ivp

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
    # Calcular aceleración
    def aceleración(r):
        x, y = r
        R = sqrt(x**2 + y**2)
        ax = -GM * x / R**3
        ay = -GM * y / R**3
        return ax, ay

    # Función de derivadas para odeint y solve_ivp
    def derivadas(r, t, *args):
        x, y, vx, vy = r
        R = sqrt(x**2 + y**2)
        ax = -GM * x / R**3
        ay = -GM * y / R**3
        return [vx, vy, ax, ay]
    
    def simular_odeint():
        condiciones_iniciales = [x0, y0, vx0, vy0]
        sol = odeint(derivadas, condiciones_iniciales, t_array)
        x_odeint, y_odeint, vx_odeint, vy_odeint = sol[:, 0], sol[:, 1], sol[:, 2], sol[:, 3]
        energia_odeint = 0.5 * m * (vx_odeint**2 + vy_odeint**2) - GM * m / np.sqrt(x_odeint**2 + y_odeint**2)
        return x_odeint, y_odeint, energia_odeint

    def simular_solve_ivp():
        condiciones_iniciales = [x0, y0, vx0, vy0]
        sol = solve_ivp(lambda t, r: derivadas(r, t), (0, t_max), condiciones_iniciales, t_eval=t_array)
        return sol.y[0], sol.y[1]

    def simular(metodo, plot=True):
        x, y, vx, vy = x0, y0, vx0, vy0
        ax, ay = aceleración([x, y])
        x_array, y_array, r_array = [],[],[]
        T_array, U_array, E_array = [],[],[]

        for _ in t_array:
            T = 0.5*m*(vx**2 + vy**2)
            U = -GM*m/sqrt(x**2 + y**2)
            T_array.append(T)
            U_array.append(U)
            E_array.append(T+U)
            
            x_array.append(x)
            y_array.append(y)
            r_array.append(sqrt(x**2 + y**2))
            
            if metodo == 'euler':
                ax, ay = aceleración([x, y])
                x  += vx*dt
                y  += vy*dt
                vx += ax*dt
                vy += ay*dt

            elif metodo == 'verlet':
                x_nuevo = x + vx*dt + 0.5*ax*dt**2
                y_nuevo = y + vy*dt + 0.5*ay*dt**2
                ax_nueva, ay_nueva = aceleración([x_nuevo, y_nuevo])
                vx += 0.5*(ax + ax_nueva)*dt
                vy += 0.5*(ay + ay_nueva)*dt
                x, y   = x_nuevo,  y_nuevo
                ax, ay = ax_nueva, ay_nueva

            elif metodo == 'rk2':
                ax, ay = aceleración([x, y])
                k1x  = vx*dt
                k1y  = vy*dt
                k1vx = ax*dt
                k1vy = ay*dt
                
                k2x  = (vx + 0.5*k1vx)*dt
                k2y  = (vy + 0.5*k1vy)*dt
                ax2, ay2 = aceleración([x + 0.5*k1x, y + 0.5*k1y])
                k2vx = ax2*dt
                k2vy = ay2*dt

                x  += k2x
                y  += k2y
                vx += k2vx
                vy += k2vy

            elif metodo == 'rk4':
                ax, ay = aceleración([x, y])
                
                # Paso 1
                k1x =  vx*dt
                k1y =  vy*dt
                k1vx = ax*dt
                k1vy = ay*dt
                
                # Paso 2
                k2x  = (vx + 0.5*k1vx)*dt
                k2y  = (vy + 0.5*k1vy)*dt
                ax2, ay2 = aceleración([x + 0.5*k1x, y + 0.5*k1y])
                k2vx = ax2*dt
                k2vy = ay2*dt
                
                # Paso 3
                k3x  = (vx + 0.5*k2vx)*dt
                k3y  = (vy + 0.5*k2vy)*dt
                ax3, ay3 = aceleración([x + 0.5*k2x, y + 0.5*k2y])
                k3vx = ax3*dt
                k3vy = ay3*dt
                
                # Paso 4
                k4x = (vx + k3vx)*dt
                k4y = (vy + k3vy)*dt
                ax4, ay4 = aceleración([x + k3x, y + k3y])
                k4vx = ax4*dt
                k4vy = ay4*dt
                
                x  += (k1x  + 2*k2x  + 2*k3x  + k4x) /6
                y  += (k1y  + 2*k2y  + 2*k3y  + k4y) /6
                vx += (k1vx + 2*k2vx + 2*k3vx + k4vx)/6
                vy += (k1vy + 2*k2vy + 2*k3vy + k4vy)/6
        
        # Gráficas
        if plot:
            fig, axs = plt.subplots(2, 3, figsize=(11, 8))
            plt.suptitle(f'Método {metodo}',fontsize=20,weight='bold')
            plt.tight_layout(pad=2.)
            fig.subplots_adjust(hspace=0.3, wspace=-.1)

            # órbita
            h, k = np.mean(x_array), np.mean(y_array)  # centro de la elipse
            theta = np.linspace(0, 2 * np.pi, 100)

            ax_orbita = fig.add_subplot(1, 2, 1)
            ax_orbita.set_aspect('equal')
            ax_orbita.plot(x_array, y_array, label='Órbita Terrestre', c='blue')
            ax_orbita.plot(x_array[0] * np.cos(theta), x_array[0] * np.sin(theta), label='Círculo de radio = perihelio', c='orange')
            ax_orbita.scatter(0, 0, marker='x', c='orange', label='Centro del círculo')
            ax_orbita.scatter(h, k, marker='x', c='blue', label='Centro de la elipse')
            ax_orbita.set_xlabel("x (m)")
            ax_orbita.set_ylabel("y (m)")
            ax_orbita.set_title(f"Trayectoria de la Tierra alrededor del Sol")
            ax_orbita.legend(loc=(0.28,0.65))
            ax_orbita.grid()

            # trayectoria
            axs[0, 2].plot(t_array, r_array, label="Radio orbital")
            axs[0, 2].set_xlabel("Tiempo (s)")
            axs[0, 2].set_ylabel("Radio (m)")
            axs[0, 2].set_title("Radio en función del tiempo")
            axs[0, 2].legend(loc='upper right')
            axs[0, 2].grid()

            # energías
            axs[1, 2].plot(t_array, T_array, label="Energía cinética")
            axs[1, 2].plot(t_array, U_array, label="Energía potencial")
            axs[1, 2].plot(t_array, E_array, label="Energía total")
            axs[1, 2].set_xlabel("Tiempo (s)")
            axs[1, 2].set_ylabel("Energía (J)")
            axs[1, 2].set_title("Energía en función del tiempo")
            axs[1, 2].legend(loc='best')
            axs[1, 2].grid()

            axs[0, 0].remove()
            axs[0, 1].remove()
            axs[1, 0].remove()
            axs[1, 1].remove()

            plt.show()
        
        return E_array

    # Constantes
    G = 6.67384e-11 # m^3 kg^-1 s^-2
    M = 1.9891e30   # kg (sol)
    m = 5.97219e24  # kg (tierra)
    GM = G*M

    # Condiciones Iniciales
    x0 = 1.4719e11  # m (perihelio)
    y0 = 0          # m
    vx0 = 0         # m/s
    vy0 = 3.0287e4  # m/s (tangencial)

    # Tiempo de simulación
    t_max = 5*365*24*3600 # 5y en s
    dt = 3600.            # 1h en s
    t_array = np.arange(0, t_max, dt)

    # Métodos implementados
    simular('euler')
    simular('verlet')
    simular('rk2')
    simular('rk4')
    
    # Métodos de librerías
    x_odeint, y_odeint, _ = simular_odeint()
    x_solve_ivp, y_solve_ivp = simular_solve_ivp()
    theta = np.linspace(0, 2 * np.pi, 100)
    
    fig, ax_orbita = plt.subplots(figsize=(8,8))
    ax_orbita.set_aspect('equal')
    ax_orbita.plot(x_odeint, y_odeint, label='Trayectoria con Odeint', c='blue')
    ax_orbita.plot(x_solve_ivp, y_solve_ivp, label='Trayectoria con SolveIvp', c='red')
    
    ax_orbita.scatter(0, 0, marker='x', c='orange', label='Centro del círculo')
    ax_orbita.set_xlabel("x (m)")
    ax_orbita.set_ylabel("y (m)")
    ax_orbita.set_title(f"Trayectoria de la Tierra alrededor del Sol con Odeint y SolveIVP")
    ax_orbita.legend(loc=(0.28,0.65))
    ax_orbita.grid()
    plt.show()
    
    # Vemos que solveivp es nefasto, solo voy a comparar la energía con odeint
    
    ##########################
    # COMPARATIVA ENERGÉTICA #
    ##########################
    # Tiempo de simulación para la comparativa energética
    t_max = 50*365*24*3600 # 50y en s
    dt = 3600*12.          # 12h en s
    t_array = np.arange(0, t_max, dt)

    energia_euler =  simular('euler',  plot=False)
    energia_verlet = simular('verlet', plot=False)
    energia_rk2 =    simular('rk2',    plot=False)
    energia_rk4 =    simular('rk4',    plot=False)
    _, _, energia_odeint = simular_odeint()

    fig, axs = plt.subplots(1, 2, figsize=(14, 8))
    plt.subplots_adjust(wspace=0.5)
    plt.suptitle('Conservación de la energía',fontsize=20,weight='bold')

    axs[0].plot(t_array/(365*24*3600), energia_euler,  label='Euler',          c='#d62728')
    axs[0].plot(t_array/(365*24*3600), energia_verlet, label='Verlet',         c='#1f77b4')
    axs[0].plot(t_array/(365*24*3600), energia_rk2,    label='Runge-Kutta II', c='#ff7f0e')
    axs[0].plot(t_array/(365*24*3600), energia_rk4,    label='Runge-Kutta IV', c='#2ca02c')
    axs[0].plot(t_array/(365*24*3600), energia_odeint, label='Odeint',         c='#9467bd')
    axs[0].set_xlabel('Tiempo (años)')
    axs[0].set_ylabel('Energía total (J)')
    axs[0].set_title('Comparación con el método de Euler')
    axs[0].legend()
    axs[0].grid(True)

    axs[1].plot(t_array/(365*24*3600), energia_verlet, label='Verlet',         c='#1f77b4')
    axs[1].plot(t_array/(365*24*3600), energia_rk2,    label='Runge-Kutta II', c='#ff7f0e')
    axs[1].plot(t_array/(365*24*3600), energia_rk4,    label='Runge-Kutta IV', c='#2ca02c')
    axs[1].plot(t_array/(365*24*3600), energia_odeint, label='Odeint',         c='#9467bd')
    axs[1].set_xlabel('Tiempo (años)')
    axs[1].set_ylabel('Energía total (J)')
    axs[1].set_title('Conservación de la energía en métodos más precisos')
    axs[1].legend()
    axs[1].grid(True)

    plt.show()

    """
    El método de Euler, de primer orden, es inexacto para simulaciones
    con largo plazo temporal, por la acumulación de errores que llevan
    a una ganancia de energía. Esto hace que la trayectoria se desvíe
    significativamente, provocando que la órbita se vuelva espiral,
    alejándose del Sol poco a poco.

    El método de Verlet, es una mejora en lo que a la conservación de
    la energía se refiere. Conserva la energía en un sentido medio
    (la energía media al principio y al final es la misma aunque tenga
    pequeñas oscilaciones). Aunque la energía fluctúa, lo hace de forma
    acotada y sin desviarse cumulativamente como lo hace Euler.

    El método de Runge-Kutta de segundo orden (RK2), es una mejora
    respecto al de Euler, pero sigue teniendo problemas cuando se amplía
    el rango temporal, ya que acumula errores en la energía total aunque
    sea a una razón menor que la que acumula Euler. Es casi igual de
    simple que el método de Euler ganando en algo de precisión, a la vez
    que mantiene el balance entre costo computacional y exactitud.

    El método de Runge-Kutta de cuarto orden (RK4), mejora mucho en lo
    que a la conservación de la energía se refiere. Al tener la trayectoria
    con un error de orden o(dt^4), las fluctuaciones en la energía total
    son mucho menores comparadas con los otros métodos. Esto se debe a que
    considera los efectos de la aceleración en diferentes puntos de un 
    intervalo de temporal, promediando el efecto para obtener una mejor
    predicción de la posición y velocidad futuras.

    En conclusión, RKIV es el método más adecuado para la simulación de
    órbitas en lo que a la conservación de la energía se refiere. Odeint 
    se le acerca aunque es muy extraño cómo oscila su cálculo de la energía.
    """


def main() -> None:
    # ejercicioI()    
    # comparar(f'/home/victor/fisicaua/cuarto/posiciones_diamante.txt', f'/home/victor/fisicaua/cuarto/posiciones_mejor_diamante.txt', "/home/victor/fisicaua/cuarto/differences.txt")
    ejercicioII()

if __name__ == '__main__':
    main()