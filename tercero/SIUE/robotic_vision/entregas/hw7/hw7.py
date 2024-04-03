import cv2 as cv2
import numpy as np
from numpy import pi,sin,cos
import matplotlib.pyplot as plt
import open3d as o3d

"""
Robotic Vision
Homework Set VII

Víctor Mira Ramírez
800803577
vmirara@siue.edu
"""

pathL = r'C:\Users\victor\Documents\GitHub\fisicaua\tercero\SIUE\robotic_vision\entregas\hw7\rocks2-l.png'
pathR = r'C:\Users\victor\Documents\GitHub\fisicaua\tercero\SIUE\robotic_vision\entregas\hw7\rocks2-r.png'

rockL = cv2.imread(pathL, cv2.IMREAD_GRAYSCALE)
rockR = cv2.imread(pathR, cv2.IMREAD_GRAYSCALE)
color = cv2.imread(pathL, cv2.IMREAD_COLOR)
h, w = color.shape[:2]

stereo = cv2.StereoBM_create(numDisparities=256, blockSize=15)
disparity = stereo.compute(rockL, rockR)
disparity = cv2.normalize(disparity, None, alpha=0, beta=255, norm_type=cv2.NORM_MINMAX, dtype=cv2.CV_8U)

max_disp = np.max(disparity)
disp8 = np.uint8(disparity / max_disp * 255)

plt.figure()
plt.imshow(disparity,'gray')

def draw3d() -> None:
    pcd = o3d.geometry.PointCloud()
    pc_points = np.array([], np.float32)
    pc_color  = np.array([], np.float32)
    depth = 255 - disp8
    f = 50

    for v in range(h):
        for u in range(w):
            if(disp8[v][u] > 0):
                # pc_points
                x = (u - w/2) * depth[v][u] / f
                y = (v - h/2) * depth[v][u] / f
                z = depth[v][u]
                pc_points = np.append(pc_points, np.array(np.float32(([x, y, z]))))
                pc_points = np.reshape(pc_points, (-1, 3))

                # pc_colors
                pc_color = np.append(pc_color, np.array(np.float32(color[v][u] / 255)))
                pc_color = np.reshape(pc_color, (-1, 3))
                print(v,' of ',h)

    pcd.points = o3d.utility.Vector3dVector(pc_points)
    pcd.colors = o3d.utility.Vector3dVector(pc_color)
    o3d.visualization.draw_geometries([pcd])

draw3d()
plt.show()