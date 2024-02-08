import cv2 as cv2
import numpy as np
import matplotlib.pyplot as plt

"""
Robotic Vision
Homework Set II

Víctor Mira Ramírez
800803577
vmirara@siue.edu
"""

            ##############
            # EXERCISE 1 #
            ##############

"""
(Perspective Rectification) Warp the basketball court from this image 
to a new image so that it appears as if the new image was taken from
directly above. 
"""
path=r'/home/victor/fisicaua/tercero/SIUE/robotic_vision/entregas/hw4/basketball_court.jpg'

img = cv2.imread(path, cv2.IMREAD_COLOR)
RGB_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB) # translate to rgb

pts1 = np.float32([[210, 41], [343, 62], [239, 237], [19, 164]]) # clockwise starting from NW
pts2 = np.float32([[100, 100], [300, 100], [300, 475], [100, 475]])
    
# Apply Perspective Transform Algorithm
matrix = cv2.getPerspectiveTransform(pts1, pts2)
result = cv2.warpPerspective(RGB_img, matrix, (400, 575))
# added 100px size around the court so that the image is narrower
# the proportion of a basketball court is approximately 1.87

plt.figure()
plt.imshow(result)

plt.figure()
plt.imshow(RGB_img)

plt.show()

            ##############
            # EXERCISE 2 #
            ##############

"""
Repeat the homogeneous camera calibration exercise of Sect. 11.2.1. 
Investigate the effect of the number of calibration points, and noise
on the calibration residual.

This part is a repeat of MATLAB code in textbook, there is no easy
replacement for Python, I would recommend you use MATLAB for this
problem
"""
