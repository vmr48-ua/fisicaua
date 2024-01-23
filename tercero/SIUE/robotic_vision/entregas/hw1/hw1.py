import cv2 as cv
import numpy as np
import matplotlib.pyplot as plt

path=r'/home/victor/fisicaua/tercero/SIUE/robotic_vision/entregas/hw1/church.jpg'

img = cv.imread(path, cv.IMREAD_COLOR)
# to use with matplotlib we need to transform BGR to RGB
RGB_img = cv.cvtColor(img, cv.COLOR_BGR2RGB) 

plt.figure()
plt.imshow(img)

plt.figure()
plt.imshow(RGB_img)

plt.show()