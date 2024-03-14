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
(Diadic Operation) Follow the instruction in section 12.4. Take a photo 
of either yourself, your friend, family member, or your pets. Then 
superimpose the subject (the subject only, without the background) to one
of your favorite movie posters (you can also use the poster.jpg attached).
Try to “photoshop” subject and cover up only the face one of the main 
characters.

Hint: if you don't own a green screen background, pick a background with
color distinct from the subject, covert the picture to an appropriate
color space, then threshold.
"""
path=r'/home/victor/fisicaua/tercero/SIUE/robotic_vision/entregas/hw5/victor.jpeg'
path2 = r'/home/victor/fisicaua/tercero/SIUE/robotic_vision/entregas/hw5/poster.jpg'

# READ victor
img_victor = cv2.imread(path, cv2.IMREAD_COLOR)
victor = cv2.cvtColor(img_victor, cv2.COLOR_BGR2RGB)

# READ poster
img_poster = cv2.imread(path2, cv2.IMREAD_COLOR)
poster = cv2.cvtColor(img_poster, cv2.COLOR_BGR2RGB) 

# img size
h, w = img_victor.shape[:2]   

# HSV MASKING victor
HSV_victor = cv2.cvtColor(img_victor, cv2.COLOR_BGR2HSV)
mask5 = cv2.inRange(HSV_victor, (0,0,10), (3,200,255))
mask0 = cv2.inRange(HSV_victor, (4,0,50), (4,220,255))
mask1 = cv2.inRange(HSV_victor, (5,0,10), (10,200,255))
mask2 = cv2.inRange(HSV_victor, (11,21,0), (110,200,255))
mask3 = cv2.inRange(HSV_victor, (111,0,0), (210,200,255))
mask4 = cv2.inRange(HSV_victor, (0,0,0), (0,0,8))
mask = mask0 + mask1 + mask2 + mask3 + mask4 + mask5
masked_victor = cv2.bitwise_and(HSV_victor, HSV_victor, mask = mask)


# RESIZING victor
resized_victor = cv2.resize(masked_victor, (int(w/8), int(h/8)),interpolation=cv2.INTER_AREA) 
resized_mask = cv2.resize(mask, (int(w/8), int(h/8))) 
# TRANSLATING victor
translation_matrix = np.float32([[1,0,390], [0,1,890]])   
translated_victor = cv2.warpAffine(resized_victor, translation_matrix, (w, h)) 
translated_mask = cv2.warpAffine(resized_mask, translation_matrix, (w, h)) 
# ROTATING victor
rotation_matrix = cv2.getRotationMatrix2D((w/2,h/2), -90, 1)
rotated_victor = cv2.warpAffine(translated_victor, rotation_matrix, (w, h))
rotated_mask = cv2.warpAffine(translated_mask, rotation_matrix, (w, h))

# INVERSE MASKING poster
HSV_poster = cv2.cvtColor(img_poster, cv2.COLOR_BGR2HSV)
inv_mask = cv2.bitwise_not(rotated_mask)
masked_poster = cv2.bitwise_and(HSV_poster, HSV_poster, mask = inv_mask)

# ADDING MASKS
result = cv2.addWeighted(rotated_victor, 1, masked_poster, 1, 0)
result = cv2.cvtColor(result,cv2.COLOR_HSV2RGB)

plt.figure()
plt.imshow(result)

plt.figure()
plt.imshow(mask)

plt.figure()
plt.imshow(poster)

plt.figure()
plt.imshow(victor)

# plt.show()

            ##############
            # EXERCISE 2 #
            ##############

"""
See the Lena image below(lena_noisy.png is attached), how would you 
design a gaussian filter with an appropriate kernel size and standard 
deviation that can reduce the noise without overly blur the image?
"""

path=r'/home/victor/fisicaua/tercero/SIUE/robotic_vision/entregas/hw5/lena_noisy.png'

img = cv2.imread(path, cv2.IMREAD_GRAYSCALE)
gray = cv2.cvtColor(img,cv2.COLOR_GRAY2RGB) # gray image with vectorsize = 3

gauss = cv2.GaussianBlur(gray,ksize=(7,7),sigmaX=1.5,sigmaY=1.5)

plt.figure()
plt.tight_layout()
plt.imshow(gauss)

plt.figure()
plt.tight_layout()
plt.imshow(gray)

plt.show()

