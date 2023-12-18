import bluetooth
import subprocess
import json

def discover_device():
	output = subprocess.check_output(["bluetoothctl", "devices"], universal_newlines = True)
	lines = output.split("\n")
	devices = [line.split(" ") for line in lines if len(line) > 0]
	return devices
	


def send_data(device_address, service_name, service_uuid):
	port = 1
	
	service_matches = bluetooth.find_service(name=service_name, uuid = service_uuid, address=device_address)
	print(service_matches)
	first_match = service_matches[0]
	port = first_match["port"]
	
	
	sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
	sock.connect((device_address, port))
	
	json_path = "/home/christianaltrichter/pi-rfid/roomstatus.json"
	
	with open(json_path, 'r') as file:
		data = json.load(file)
	
	sock.send_data(data)


devices = discover_device()

print("Available Devices")

uuid = "00001101-0000-1000-8000-00805F9B34FB"
name = "Bluetooth2.app"
out_pair = devices[0][1]
#name = " ".join(devices[0][2:])
print("name",out_pair)

send_data(out_pair, name, uuid)



	
	
