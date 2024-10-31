import numpy as np
import matplotlib.pyplot as plt
import time

def ejercicioI() -> None:
    def generar_red_cristalina(tipo, nx, ny, nz):
        """
        Genera una red cristalina del tipo seleccionado:
            - 'cúbica simple'
            - 'fcc'
            - 'diamante'
            - 'bcc'
        """
        posiciones = []

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

        # Desplazamiento para centrar en el origen
        centro = np.array([nx - 1, ny - 1, nz - 1]) / 2

        for i in range(nx):
            for j in range(ny):
                for k in range(nz):
                    for dx, dy, dz in desplazamientos:
                        posiciones.append(np.array([dx,dy,dz]) + np.array([i,j,k]) - centro)

        return np.array(posiciones)
    
    def generar_red_cristalina_sin_for(tipo, nx, ny, nz):
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
        i,j,k = np.meshgrid(np.arange(nx), np.arange(ny), np.arange(nz), indexing='ij')
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
    
    t1 = time.time()
    posiciones = generar_red_cristalina(tipo_red, nx, ny, nz)  
    t2 = time.time()
    
    t3 = time.time()
    posiciones_mejor = generar_red_cristalina_sin_for(tipo_red, nx, ny, nz)
    t4 = time.time()
    
    print(t2-t1)
    print(t4-t3)
    
    """ 
    El tiempo de ejecución de la función sin bucles for es mucho menor 
    que el tiempo de ejecución de la función con bucles for:
    
    Longitud en x (nº ptos): 100
    Longitud en y (nº ptos): 100
    Longitud en z (nº ptos): 100
    Tipo de red (cúbica simple, fcc, diamante, bcc): diamante
    24.156051635742188 <- segundos de ejecución con bucles for
    0.3499596118927002 <- segundos de ejecución sin bucles for
    """    
    guardar_posiciones(posiciones, f'/home/victor/fisicaua/cuarto/posiciones_{tipo_red}.txt')
    guardar_posiciones(posiciones_mejor, f'/home/victor/fisicaua/cuarto/posiciones_mejor_{tipo_red}.txt')

    mostrar_red_3d(posiciones, tipo_red) 
    mostrar_red_3d(posiciones_mejor, tipo_red)

def main() -> None:
    ejercicioI()
    def compare_files(file1, file2, output_file):
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

        print(f"Differences have been saved to {output_file}")
    # compare_files(f'/home/victor/fisicaua/cuarto/posiciones_diamante.txt', f'/home/victor/fisicaua/cuarto/posiciones_mejor_diamante.txt', "/home/victor/fisicaua/cuarto/differences.txt")

if __name__ == '__main__':
    main()