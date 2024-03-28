import socket
import pygame
import numpy as np
import time,errno,atexit

"""
Robotic Vision ME462 SIUE
SLAM PROJECT

GROUP 2:
  - Víctor Mira Ramírez   800803577   vmirara@siue.edu
  - 
  - 

"""
#####################################
#           EXIT GUARD              #
#####################################
@atexit.register
def end_connection() -> None:
    send(0,0)
    client_socket.send(command='exit')
    client_socket.close()

#####################################
#           NETWORKING              #
#####################################
def adapt_packet_size(packet:str) -> str:
    try:
        packet = str(np.round(float(packet),4))
        while len(packet) < 7: # 1 for +- sign, 2 for the int and dot and 4 for the floating point
            packet += '0'
    except:
        if 'exit' or 'camera' in packet:
            while len(packet) < 22:
                packet += ' '
            packet = packet.encode()
    return packet

def send(L:float=0,R:float=0,command=None) -> None: # void method sending data tuple to server
    if command == None:
        L,R = adapt_packet_size(L), adapt_packet_size(R)  
        data = repr((L,R))
    else:
        data = adapt_packet_size(command)

    try: # try sending data without network pipe jamming
        client_socket.send(data.encode())
        print(data.decode())
    except IOError as e:
        if e.errno == errno.EPIPE:
            pass

def main() -> None:
    #####################################
    #           CONTROLLER              #
    #####################################

    # initialize controller lib
    pygame.init()
    pygame.joystick.init()

    # check there's a controller 
    num_joysticks = pygame.joystick.get_count()

    if num_joysticks > 0:
        controller = pygame.joystick.Joystick(0)
        controller.init()
        print("Controller connected:", controller.get_name())
        running = True
    else:
        print("No controller detected.")
        running = False


    ########################################
    #             INIT VARABLES            #
    ########################################

    # Local ip of the server
    HOST = '192.168.28.187'  
    PORT = '8080'

    # start connection inside a try block so as to debug in case the server is down
    try:
        client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client_socket.connect((HOST, int(PORT)))
    except:
        print('Server Connection was unsuccessful.')
        pass
    ax0,ax1,motorL,motorR = 0,0,0,0
    steer_lock,stop = False, False

    ########################################
    #           MAIN LOGIC LOOP            #
    ########################################
    while(running):
        # If button 0 is pressed, the program exits
        events = pygame.event.get()
        for event in events:
            if event.type == pygame.JOYBUTTONDOWN and (event.button == 9 or event.button == 10):
                running = False #server stop
            if event.type == pygame.JOYBUTTONDOWN and event.button == 3:
                stop = True
            if event.type == pygame.JOYBUTTONDOWN and event.button == 4:
                stop = False
            if event.type == pygame.JOYBUTTONDOWN and event.button == 0: #trigger
                send(command='camera')
            if event.type == pygame.JOYBUTTONDOWN and event.button == 2:
                steer_lock = True
            if event.type == pygame.JOYBUTTONUP and event.button == 2:
                steer_lock = False
                
        if running and not(stop):
            ax0 = controller.get_axis(0)  #i
            ax1 = -controller.get_axis(1) #j
            ax2 = controller.get_axis(2) #power

            power = (controller.get_axis(2)+1)/2 #global power to the motors, can be attached to ax2
            turn_power = 1-power + 0.3 #global turning power (max recommended 0.5)
            time.sleep(0.01) #necessary to avoid network jamming (could be finetuned)
    
            # ACTUAL LOGIC
            if not(steer_lock):
                motorL,motorR = 0,0
            if np.abs(ax1) > 0.05 and not(steer_lock):
                motorL = power*ax1
                motorR = power*ax1
                if ax1 > 0:
                    if np.abs(ax0) > 0.05 and not(steer_lock):
                        motorL += turn_power*ax0
                        motorR -= turn_power*ax0
                else: 
                    if np.abs(ax0) > 0.05 and not(steer_lock):
                        motorL -= turn_power*ax0
                        motorR += turn_power*ax0
            send(motorL,motorR) # send motor info  

    # END CONNECTION
    end_connection()

if __name__ == '__main__':
    main()