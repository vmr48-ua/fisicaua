
data = str(('0.00000', '0.00000'))

def decode(packet):
    output = packet.split(',')
    print(output)
    x = output[0].split("'")[1]
    y = output[1].split("'")[0]
    return float(output[0]),float(output[1])

print(decode(data))