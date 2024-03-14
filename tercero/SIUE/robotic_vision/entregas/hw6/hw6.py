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
(Corner detector, adapted from textbook problem 13.6) Use the 
'building2-1.png' provided by the toolbox, test the performance of the 
Harris, Noble and Shi-Tomasi corner detectors. For each detectors, only 
return 200 strongest corners, zoom in to the same location and compare 
the difference of those three detectors.

For Python user Noble Corner detector is optional, you can see the 
    - Shi-Tomasi Corner Detection Method:
        https://www.geeksforgeeks.org/python-corner-detection-with-shi-tomasi-corner-detection-method-using-opencv/
    - The Harris Corner Detection method:
        https://www.geeksforgeeks.org/python-corner-detection-with-harris-corner-detection-method-using-opencv/
"""
path=r'/home/victor/fisicaua/tercero/SIUE/robotic_vision/entregas/hw6/building2-1.png'

img = cv2.imread(path, cv2.IMREAD_COLOR)
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

# SHI-TOMASI
shi_tomasi = img.copy()
corners = np.int0(cv2.goodFeaturesToTrack(gray.copy(), 500, 0.01, 10))
  
for i in corners: 
    x, y = i.ravel() 
    cv2.circle(shi_tomasi, (x, y), 3, (255, 0, 0), -1) 

# HARRIS
harris = img.copy()
corners = np.int0(cv2.goodFeaturesToTrack(gray.copy(), 500, 0.01, 10, useHarrisDetector=True))
  
for i in corners: 
    x, y = i.ravel() 
    cv2.circle(harris, (x, y), 3, (255, 0, 0), -1) 

plt.figure()
plt.imshow(shi_tomasi)

plt.figure()
plt.imshow(harris)

            ##############
            # EXERCISE 2 #
            ##############

"""
(Hough transform, adapted from textbook problem 13.5) Write code that 
capture one image from your computer's camera (refer to section 12.1.2), 
finding the two dominant lines and overlaying them on the image. 

Python code can be found:
    https://www.geeksforgeeks.org/line-detection-python-opencv-houghline-method/
"""

plt.show()