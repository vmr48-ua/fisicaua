import numpy as np
import numpy.linalg as la
from numpy import pi, exp
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from scipy import sparse

'''
EJERCICIO FÍSICA COMPUTACIONAL - BLOQUE I (Ejercicio 6)
INTRODUCCIÓN A LA MODELIZACIÓN EN FÍSICA

Víctor Mira Ramírez
vmr48@alu.ua.es
'''

def ejercicioVI() -> None:
    #####################################################################################
    #                                     OPCIÓN A)                                     #
    ##################################################################################### 
    global Laplaciano, dt, x, cte, Nx, Nt, L
    
    ##############
    # CONSTANTES #
    ##############
    # Espacio
    L = 1.
    Nx = 120 #120
    dx = L / Nx
    x = np.linspace(0, L, Nx)
    
    # Tiempo
    tmax = 1.
    Nt = int(3e4) # 3e4
    dt = tmax / Nt
    t = np.linspace(0, tmax, Nt)
    
    # Otros
    cte = 1j * dt / (2 * dx**2)
    if dt > dx**2 / 2:
        raise Exception('Condición de estabilidad no satisfecha')
    
    # Laplaciano
    d = np.array([-2 for _ in range(Nx)])
    u = np.array([1 for _ in range(Nx - 1)])
    o = np.array(u.copy())
    Laplaciano = sparse.diags([d,u,o], [0,-1,1], shape=(len(x), len(x))).toarray()
    
    ###################
    # FUNCIÓN INICIAL #
    ###################
    def psi_0(x):
        sigma = 1e-2
        psi = exp(-(x-(1/2)*L)**2/(2*sigma)) #*exp(5j*(x-L/2)/(2*sigma)) # Término con momento
        norm = np.sqrt(np.trapz(np.abs(psi)**2, x))
        return psi / norm   # para que se cumpla la normalización en psi0

    ##########################
    # FUNCIÓN SHRODINGER C-N #
    ##########################
    def schrodinger(V, index, total):
        A = np.eye(len(x)) + cte * Laplaciano
        B = np.eye(len(x)) - cte * Laplaciano
        
        for i in range(len(x)):
            A[i, i] += 1j * dt * V[i] * 0.5
            B[i, i] -= 1j * dt * V[i] * 0.5
        
        psi = np.zeros((Nx, Nt), dtype=complex)
        psi[:, 0] = psi_0(x) # función inicial
        
        psi[0, 0] = 0 # Dirichlet
        psi[-1, 0] = 0
        
        # alpha = 0.99
        A_inv = la.inv(A)
        for i in range(len(t) - 1):
            print(f'Progreso en el cálculo para el Potencial [{index}/{total}]: {int(i / (len(t)-1) * 100)}%', end='\r')
            psi[0, i+1], psi[-1, i+1] = 0, 0 # Contorno Dirichlet
            # psi[0, i+1] *= alpha
            # psi[-1, i+1] *= alpha
            psi[:, i+1] = np.dot(A_inv, np.dot(B, psi[:, i]))

        norm = np.array([np.sqrt(np.trapz(np.abs(psi[:,i])**2, x)) for i in range(0, Nt, 30)])
        esperado_pos = np.array([np.trapz(x * np.abs(psi[:,i])**2, x) for i in range(0, Nt, 5)])
        
        return psi, norm, esperado_pos
    
    ###############
    # POTENCIALES #
    ###############
    potenciales, p_name = [], []
    
    # Armónico
    omega = 100.
    m = 1.
    V1 = 1/2*m*omega**2* (x - L/2)**2
    potenciales.append(V1)
    p_name.append('Armónico')
    
    # Pozo Infinito
    V2 = np.array([1e6 if x[i] <= 0 or x[i] >= L else 0 for i in range(Nx)])
    potenciales.append(V2)
    p_name.append('Pozo Infinito')
    
    # Delta Dirac    
    delta_max = 1e6
    centro = int(Nx/4)
    ancho = 0
    V3 = np.zeros(Nx)
    if ancho != 0:
        V3[centro-ancho:centro+ancho] = delta_max
    else:
        V3[centro] = delta_max
    potenciales.append(V3)
    p_name.append('Delta de Dirac')
    
    # # Pozo Dirac    
    # delta_max = -1e6
    # centro = int(Nx/4)
    # ancho = 0
    # V4 = np.zeros(Nx)
    # if ancho != 0:
    #     V4[centro-ancho:centro+ancho] = delta_max
    # else:
    #     V4[centro] = delta_max
    # potenciales.append(V4)
    # p_name.append('Pozo de Dirac')
    
    # #Barrera de potencial
    # V5 = np.array([1e3 if L/7 < x[i] < L/4 else 0 for i in range(Nx)])
    # potenciales.append(V5)
    # p_name.append('Barrera')
    
    # #Gaussiana
    # V6 = np.array([20*np.exp(-(x[i]-L/2)**2/0.01) for i in range(Nx)])
    # potenciales.append(V6)
    # p_name.append('Gaussiano')
    
    # #Escalón de potencial
    # V7 = np.array([40 if x[i] < L/5 else 0 for i in range(Nx)])
    # potenciales.append(V7)
    # p_name.append('Escalón')

    resultados = [schrodinger(V, i+1, len(potenciales)) for i, V in enumerate(potenciales)]
    print('\nCalculando gráficas...')
    ####################################
    # FUNCIÓN PARA GENERAR ANIMACIONES #
    ####################################
    def generar_animaciones(resultados):
        fig, axes = plt.subplots(len(resultados), 4, figsize=(20, 4*len(resultados)), tight_layout=True)
        if len(resultados) == 1:
            axes = [axes]  # problemas caso un solo potencial
        
        lines_real, lines_comp, lines_abs, imshows, time_texts = [], [], [], [], []
        psi_max = np.max([np.abs(psi)**2 for psi,_,_ in resultados])
        
        for idx, (psi, norm, esperado_pos) in enumerate(resultados):            
            # Animación de la densidad de probabilidad            
            ax_evolution = axes[idx, 0].twinx()
            ax_evolution.set_xlim(0, L)
            ax_evolution.set_ylim(-1.1 * psi_max, 1.1 * psi_max)
            ax_evolution.set_ylabel('$\\left|\\psi(x,t)\\right|^2$', color='blue')
            line_abs, = ax_evolution.plot([], [], color='blue', label='$\\left|\\psi(x,t)\\right|^2$')
            lines_abs.append(line_abs)
            axes[idx, 0].set_title(f'Potencial {p_name[idx]}: Evolución de $\\psi(x,t)$')
            axes[idx, 0].set_xlabel('Posición (x)')
            axes[idx, 0].set_ylabel('$\\psi(x,t)$ ($m^{-1}$)', color='black')
            axes[idx, 0].set_ylim(-1.1 * psi_max, 1.1* psi_max)
            time_text = axes[idx, 0].text(0.1, 0.8, '', transform=axes[idx, 0].transAxes, fontsize=10)
            time_texts.append(time_text)

            # Animación de las partes real y compleja de la función de onda
            line_real, = axes[idx, 0].plot([], [], color='green', label='Re($\\psi(x,t)$)', linestyle='--', alpha=0.5)
            line_comp, = axes[idx, 0].plot([], [], color='red',   label='Im($\\psi(x,t)$)', linestyle='--', alpha=0.5)
            lines_real.append(line_real)
            lines_comp.append(line_comp)
            axes[idx, 0].legend(loc='lower left',mode = "expand", ncol = 3)
            
            # Potencial
            axes[idx, 1].plot(x, potenciales[idx], color='black')
            axes[idx, 1].set_xlim(0, L)
            axes[idx, 1].set_ylim(-1.1*np.abs(np.min(potenciales[idx])), 1.1*np.max(potenciales[idx]))
            axes[idx, 1].set_xlim(0-0.05, L+0.05)
            axes[idx, 1].set_title(f'Potencial {p_name[idx]}')
            axes[idx, 1].set_xlabel('Posición (x)')
            axes[idx, 1].set_ylabel('V(x)')
            
            # Imshow del espacio-tiempo
            imshow = axes[idx, 2].imshow(np.abs(psi[:, ::30].T)**2, aspect='auto', extent=[0, L, 0, tmax], cmap='inferno')
            imshows.append(imshow)
            axes[idx, 2].set_title(f'Potencial {p_name[idx]}: Espacio-Tiempo')
            axes[idx, 2].set_xlabel('Posición (x)')
            axes[idx, 2].set_ylabel('Tiempo (t)')
            axes[idx, 2].set_ylim(0, 0.2)
            fig.colorbar(imshow, ax=axes[idx, 2])

            # Norma y Valor Esperado de x
            ax_norm = axes[idx, 3].twinx()
            axes[idx, 3].set_ylim(0, 1.05) # norma
            axes[idx, 3].plot(np.linspace(0, tmax, len(norm)), norm, color='blue', label='Norma $\\|\\psi(x,t)\\|$', linestyle='dotted')
            ax_norm.set_ylim(0, L) # <x>
            ax_norm.plot(np.linspace(0, tmax, len(esperado_pos)), esperado_pos, color='green', label='$\\langle x(t) \\rangle$', linestyle='solid')
            axes[idx, 3].set_title(f'Potencial {p_name[idx]}: Norma y Valor Esperado $\\langle x(t) \\rangle$')
            axes[idx, 3].set_xlabel('Tiempo (t)')
            axes[idx, 3].set_ylabel('Norma $\\|\\psi(x,t)\\|$', color='blue')
            ax_norm.set_ylabel('$\\langle x(t) \\rangle$', color='green')
            axes[idx, 3].legend(loc='lower left')
            ax_norm.legend(loc='lower right')

        def init():
            for line_real, line_abs, line_comp in zip(lines_real, lines_abs, lines_comp): 
                line_real.set_data([], [])
                line_comp.set_data([], [])
                line_abs.set_data([], [])
            for time_text in time_texts:
                time_text.set_text('')
            return lines_real + lines_comp + lines_abs + time_texts

        def animate(i):
            for idx, (psi, _, _) in enumerate(resultados):
                lines_real[idx].set_data(x, np.real(psi[:, i]))
                lines_comp[idx].set_data(x, np.imag(psi[:, i]))
                lines_abs[idx].set_data(x, np.abs(psi[:, i])**2)
                time_texts[idx].set_text(f't = {i*dt:.2f}s')
            return lines_real + lines_comp + lines_abs + time_texts
        
        anim = FuncAnimation(fig, animate, init_func=init, frames=range(0,Nt,10), interval=1, blit=True)
        plt.show()

    ##############################################
    # FUNCIÓN PARA GENERAR GRÁFICAS POR SEPARADO # (Se usó para el informe)
    ##############################################
    def generar_animaciones_por_separado(resultados, frame=0):
        psi_max = np.max([np.abs(psi)**2 for psi, _, _ in resultados])

        for idx, (psi, norm, esperado_pos) in enumerate(resultados):
            # Gráfica de la evolución de la densidad de probabilidad
            fig, ax = plt.subplots()
            ax2 = ax.twinx()
            ax2.set_xlim(0, L)
            ax2.set_ylim(-1.1 * psi_max, 1.1 * psi_max)
            ax2.set_ylabel('$\\left|\\psi(x,t)\\right|^2$', color='blue')
            ax2.plot(x, np.abs(psi[:, frame])**2, color='blue', label='$\\left|\\psi(x,t)\\right|^2$')
            ax.set_title(f'Potencial {p_name[idx]}: Evolución de $\\psi(x,t)$ (frame {frame})')
            ax.set_xlabel('Posición (x)')
            ax.set_ylabel('$\\psi(x,t)$ ($m^{-1}$)', color='black')
            ax.set_ylim(-1.1 * psi_max, 1.1 * psi_max)
            ax.plot(x, np.real(psi[:, frame]), color='green', label='Re($\\psi(x,t)$)', linestyle='--', alpha=0.5)
            ax.plot(x, np.imag(psi[:, frame]), color='red', label='Im($\\psi(x,t)$)', linestyle='--', alpha=0.5)
            ax.legend(loc='lower left', mode="expand", ncol=3)
            plt.show()

            # Gráfica del potencial
            fig, ax = plt.subplots()
            ax.plot(x, potenciales[idx], color='black')
            ax.set_xlim(0, L)
            ax.set_ylim(-1.1 * np.abs(np.min(potenciales[idx])), 1.1 * np.max(potenciales[idx]))
            ax.set_title(f'Potencial {p_name[idx]}')
            ax.set_xlabel('Posición (x)')
            ax.set_ylabel('V(x)')
            plt.show()

            # Imshow del espacio-tiempo
            fig, ax = plt.subplots()
            imshow = ax.imshow(np.abs(psi[:, ::30].T)**2, aspect='auto', extent=[0, L, 0, tmax], cmap='inferno')
            ax.set_title(f'Potencial {p_name[idx]}: Espacio-Tiempo')
            ax.set_xlabel('Posición (x)')
            ax.set_ylabel('Tiempo (t)')
            ax.set_ylim(0, 0.2)
            fig.colorbar(imshow, ax=ax)
            plt.show()

            # Gráfica de la norma y el valor esperado de x
            fig, ax = plt.subplots()
            ax2 = ax.twinx()
            ax.set_ylim(0, 1.05)  # Norma
            ax.plot(np.linspace(0, tmax, len(norm)), norm, color='blue', label='Norma $\\|\\psi(x,t)\\|$', linestyle='dotted')
            ax2.set_ylim(0, L)  # <x>
            ax2.plot(np.linspace(0, tmax, len(esperado_pos)), esperado_pos, color='green', label='$\\langle x(t) \\rangle$', linestyle='solid')
            ax.set_title(f'Potencial {p_name[idx]}: Norma y Valor Esperado $\\langle x(t) \\rangle$')
            ax.set_xlabel('Tiempo (t)')
            ax.set_ylabel('Norma $\\|\\psi(x,t)\\|$', color='blue')
            ax2.set_ylabel('$\\langle x(t) \\rangle$', color='green')
            ax.legend(loc='lower left')
            ax2.legend(loc='lower right')
            plt.show()

    generar_animaciones(resultados)
    #generar_animaciones_por_separado(resultados, 1000) # Se usó para el informe

def ejercicioVI_parteB() -> None:
    #####################################################################################
    #                                     OPCIÓN B)                                     #
    #####################################################################################
    L = 2.
    T = 40.  # t_max
    Nx = 100
    Nt = 3000
    dx = L/Nx
    dt = T/Nt

    c = 1.
    k_rho = 0.1
    a = -2.0

    x = np.linspace(0, L, Nx)
    t = np.linspace(0, T, Nt)

    modos = [1, 2, 3, 4]
    u = np.zeros((len(modos), Nx, Nt))  # modo, espacio y tiempo

    for idx, n in enumerate(modos):
        u[idx, :, 0] = np.sin(n*pi*x/L)  # cada modo con sin(n*pi*0/L)

    # Contorno
    u[:, 0, :] = 0 # extremos fijos
    u[:, -1, :] = 0

    for idx in range(len(modos)):
        u[idx, :, 1] = u[idx, :, 0]  # du/dt(0) = 0

    for j in range(1, Nt-1):
        for idx in range(len(modos)):           
            u[idx,1:-1,j+1] = (2*u[idx,1:-1,j] - u[idx,1:-1,j-1] + (c**2*dt**2/dx**2) * (u[idx,2:,j] 
                            - 2*u[idx,1:-1,j] + u[idx,:-2,j]) - (2*k_rho*dt * u[idx,1:-1,j] 
                            + a*dt**2*u[idx,1:-1,j]))

    # Crank-Nicholson (no consigo que funcione)
    # cte = (c*dt/dx)**2 * 2

    # d = np.array([1 - 2*cte for _ in range(Nx)])
    # ud = np.array([cte for _ in range(Nx - 1)])
    # o = np.array(ud.copy())

    # A = sparse.diags([d,ud,o],[0,-1,1]).toarray()
    # B = sparse.diags([2-d,ud,o],[0,-1,1]).toarray()

    # A_inv = la.inv(A)
    # # A u^{j+1} = B u^j
    # for j in range(1, Nt - 1):
    #     for idx in range(len(modos)):
    #         b = np.dot(B, u[idx, :, j]) - (2*k_rho*dt - a*dt**2)*u[idx, :, j]
    #         b[0],b[-1] = 0,0
    #         u[idx, :, j+1] = np.dot(A_inv, b)

    #         u[idx,0,j+1],u[idx,-1,j+1] = 0,0

    fig, ax = plt.subplots()
    ax.set_xlim(-0.05, L+0.05)
    ax.set_ylim(-1.05, 1.05)

    lines = []
    for idx, n in enumerate(modos):
        line, = ax.plot(x, u[idx, :, 0], label='Modo {}'.format(n))
        lines.append(line)
    time_text = ax.text(0.85, 0.65, '', transform=ax.transAxes, fontsize=10)

    def animate(j):
        for idx, line in enumerate(lines):
            line.set_ydata(u[idx, :, j])
        time_text.set_text('t = {}s'.format(np.round(j*dt, 2)))
        return *lines, time_text

    anim = FuncAnimation(fig, animate, frames=range(0,Nt,1), interval=20, blit=True)
    ax.legend(loc="upper right")
    plt.show()

# Main
def main() -> None:
    ejercicioVI()
    #ejercicioVI_parteB()

if __name__ == '__main__':
    main()