import socket # networking lib
import jetbot
import atexit

@atexit.register
def close_socket(connection) -> None:
    try:
        connection.shutdown(socket.SHUT_RDWR)
        connection.close()
    except:
        pass
    try:
        robot.left_motor.value = 0
        robot.right_motor.value = 0
    except:
        pass
    
def read(connections, packet_size: int = 22) -> bytes:
    for i in reversed(range(len(connections))):
        try:
            data, sender = connections[i][0].recvfrom(packet_size)
            return data
        except (BlockingIOError, socket.timeout, OSError):
            pass
        except (ConnectionResetError, ConnectionAbortedError):
            close_socket(connections[i][0])
            connections.pop(i)
    return b''  # return empty if no data found
            
def decode(packet: bytes) -> (float,float):
    output = packet.decode()
    output = output.split(',')
    x = output[0].split('(')[1]
    y = output[1].split(')')[0]
    x = x.split("'")[1]
    y = y.split("'")[1]
    return float(x),float(y)

def main() -> None:
    robot = jetbot.Robot()
    HOST = '192.168.28.187'
    PORT = '8080'
    connections = []
    running: bool = True

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setblocking(0)
    server.bind((HOST, int(PORT))) # declare server
    server.listen(1) # max connection streams
    print('Server Started')

    while running:
        try:
            con, addr = server.accept()
            connections.append((con, addr))
        except BlockingIOError:
            pass

        data: bytes = read(connections)
        if data != b'':
            if 'exit' in data.decode():
                running = False
                break
            if 'camera' in data.decode():
                #take a pic
                break
            motorL, motorR = decode(data)
            robot.left_motor.value = motorL
            robot.right_motor.value = motorR

    # Close the sockets
    for i in reversed(range(len(connections))):
        close_socket(connections[i][0])
        connections.pop(i)
    close_socket(server)

if __name__ == '__main__':
    main()