import matplotlib.pyplot as plt
import roboticstoolbox.tools.trajectory as rbt

"""
Robotic Vision
Homework Set II

Víctor Mira Ramírez
800803577
vmirara@siue.edu
"""


            ##############
            # EXERCISE 8 #
            ##############

"""
For a lspb trajectory from 0 to 1 in 50 steps explore the effects of specifying the velocity
for the constant velocity segment. What are the minimum and maximum bounds possible?
"""

N  = 50
S0 = 0
SF = 1

# If I use the package named method, it is said to be deprecated and uses trapezoidal
print(rbt.lspb(S0,SF,N)) # We can see how it is "created by trapezoidal"


# The limits of velocity are actually written in the method: 
# (abs(qf - q0) / tf) < abs(V) < (2 * abs(qf - q0) / tf)
# Which translates to |1-0|/50 < V < 2*|1-0|/50 or 0.02 < V < 0.04

V = 0.03

trajectory = rbt.lspb(S0,SF,N,V)
rbt.Trajectory.plot(trajectory)

plt.show()

            ###############
            # EXERCISE 10 #
            ###############

"""
Use animate to compare rotational interpolation using quaternions, Euler angles
and roll-pitch-yaw angles. Hint: use the quaternion interp method and mtraj.
"""

import spatialmath as sm

r0 = sm.UnitQuaternion.RPY(0,0,0,unit='deg')

# Trajectory using Quaternions
rotation_quat = sm.UnitQuaternion.RPY(20,10,60,unit='deg')

q_trajectory = r0.interp(rotation_quat,N,shortest=True)
q_trajectory.animate()
plt.show()

# Trajectory using Roll Pitch Yaw
rotation_rpy = sm.pose3d.SO3.RPY(20,10,60,unit='deg')

rpy_trajectory = rbt.mtraj(rbt.trapezoidal,r0.rpy(),rotation_rpy.rpy(),N)
pose_rpy = sm.pose3d.SO3.RPY(rpy_trajectory.q)
pose_rpy.animate()
plt.show()

# Trajectory using Euler Angles
rotation_euler = rotation_rpy.eul()

eul_trajectory = rbt.mtraj(rbt.trapezoidal,r0.eul(),rotation_euler,N)
pose_euler = sm.pose3d.SO3.Eul(eul_trajectory.q)
pose_euler.animate()
plt.show()