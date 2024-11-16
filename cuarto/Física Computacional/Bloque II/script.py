import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import odeint, solve_ivp
from math import sqrt

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

    # Simular trayectorias
    def simular(metodo, plot=True):
        x, y, vx, vy = x0, y0, vx0, vy0
        ax, ay = aceleración([x, y])
        x_array, y_array, r_array = [],[],[]
        T_array, U_array, E_array = [],[],[]

        for _ in t_array:
            T = 0.5 * m * (vx**2 + vy**2)
            U = -GM * m / sqrt(x**2 + y**2)
            T_array.append(T)
            U_array.append(U)
            E_array.append(T+U)
            
            x_array.append(x)
            y_array.append(y)
            r_array.append(sqrt(x**2 + y**2))
            
            if metodo == 'euler':
                ax, ay = aceleración([x, y])
                x += vx * dt
                y += vy * dt
                vx += ax * dt
                vy += ay * dt

            elif metodo == 'verlet':
                x_nuevo = x + vx * dt + 0.5 * ax * dt**2
                y_nuevo = y + vy * dt + 0.5 * ay * dt**2
                ax_nueva, ay_nueva = aceleración([x_nuevo, y_nuevo])
                vx += 0.5 * (ax + ax_nueva) * dt
                vy += 0.5 * (ay + ay_nueva) * dt
                x, y = x_nuevo, y_nuevo
                ax, ay = ax_nueva, ay_nueva

            elif metodo == 'rk2':
                ax, ay = aceleración([x, y])
                k1x = vx * dt
                k1y = vy * dt
                k1vx = ax * dt
                k1vy = ay * dt
                
                k2x = (vx + 0.5 * k1vx) * dt
                k2y = (vy + 0.5 * k1vy) * dt
                ax2, ay2 = aceleración([x + 0.5 * k1x, y + 0.5 * k1y])
                k2vx = ax2 * dt
                k2vy = ay2 * dt

                x += k2x
                y += k2y
                vx += k2vx
                vy += k2vy

            elif metodo == 'rk4':
                ax, ay = aceleración([x, y])
                
                k1x = vx * dt
                k1y = vy * dt
                k1vx = ax * dt
                k1vy = ay * dt
                
                k2x = (vx + 0.5 * k1vx) * dt
                k2y = (vy + 0.5 * k1vy) * dt
                ax2, ay2 = aceleración([x + 0.5 * k1x, y + 0.5 * k1y])
                k2vx = ax2 * dt
                k2vy = ay2 * dt
                
                k3x = (vx + 0.5 * k2vx) * dt
                k3y = (vy + 0.5 * k2vy) * dt
                ax3, ay3 = aceleración([x + 0.5 * k2x, y + 0.5 * k2y])
                k3vx = ax3 * dt
                k3vy = ay3 * dt
                
                k4x = (vx + k3vx) * dt
                k4y = (vy + k3vy) * dt
                ax4, ay4 = aceleración([x + k3x, y + k3y])
                k4vx = ax4 * dt
                k4vy = ay4 * dt
                
                x += (k1x + 2 * k2x + 2 * k3x + k4x) / 6
                y += (k1y + 2 * k2y + 2 * k3y + k4y) / 6
                vx += (k1vx + 2 * k2vx + 2 * k3vx + k4vx) / 6
                vy += (k1vy + 2 * k2vy + 2 * k3vy + k4vy) / 6

        if plot:
            fig, axs = plt.subplots(2, 3, figsize=(11, 8))
            plt.suptitle(f'Método {metodo}',fontsize=20,weight='bold')
            plt.tight_layout(pad=2.)
            fig.subplots_adjust(hspace=0.3, wspace=-.1)

            # órbita
            ax_orbita = fig.add_subplot(1, 2, 1)
            ax_orbita.set_aspect('equal')
            ax_orbita.plot(x_array, y_array, label='Órbita Terrestre', c='blue')
            ax_orbita.set_xlabel("x (m)")
            ax_orbita.set_ylabel("y (m)")
            ax_orbita.set_title(f"Trayectoria de la Tierra alrededor del Sol")
            ax_orbita.legend(loc=(0.35, 0.7))
            ax_orbita.grid()

            plt.show()
        
        return E_array

    # Simular utilizando odeint
    def simular_odeint():
        condiciones_iniciales = [x0, y0, vx0, vy0]
        sol = odeint(derivadas, condiciones_iniciales, t_array)
        return sol[:, 0], sol[:, 1]

    # Simular utilizando solve_ivp
    def simular_solve_ivp():
        condiciones_iniciales = [x0, y0, vx0, vy0]
        sol = solve_ivp(lambda t, r: derivadas(r, t), (0, t_max), condiciones_iniciales, t_eval=t_array)
        return sol.y[0], sol.y[1]

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
    t_max = 5*365*24*3600 # 5 años en segundos
    dt = 3600.            # 1 hora en segundos
    t_array = np.arange(0, t_max, dt)

    x_odeint, y_odeint = simular_odeint()
    x_solve_ivp, y_solve_ivp = simular_solve_ivp()

    plt.figure(figsize=(10, 5))
    plt.plot(x_odeint, y_odeint, label="odeint")
    plt.plot(x_solve_ivp, y_solve_ivp, label="solve_ivp")
    plt.xlabel("x (m)")
    plt.ylabel("y (m)")
    plt.title("Comparación de trayectorias con odeint, solve_ivp y RK4")
    plt.legend()
    plt.grid()
    plt.show()

ejercicioII()