import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
import json
import os
import time 

# Reads from an NFC Card
def read():
	reader = SimpleMFRC522()
	print("please provide keycard reader")
	try:
		id, text = reader.read()
		#print(id)
		print(text)
	finally:
		GPIO.cleanup()

# Writes to an NFC Card
def write():
	writer = SimpleMFRC522()
	
	try:
		text = input("new data:")
		print("No place your tag to writer")
		writer.write(text)
		print("written")
		
	finally:
		GPIO.cleanup()
		

# Reads and or initialises a json file
def read_json(file_path):
	try:
		with open(file_path, 'r') as file:
			data = json.load(file)
			return data
	except FileNotFoundError:
		print("File not found")
		#with open(file_path, 'w') as file:
		data = {"Room 6" : 1}
		#	json.dump(data, file)
		#	print("written to file")
		#return {}
	except json.JSONDecodeError as e:
		print("Error decoding", e)		
		return {}

# Checks the current room status and updates it to internal logic
def check_room(data):
	new_data = {}
	current_room = "Room 6"
	for key, value in data.items():
		if key == "Room 6":
			print("current room detected", current_room)
			new_value = (value + 1)%4
			new_data[key] = new_value
		else:
			new_data[key] = value
	return new_data


# Writes the updated json file to storage
def write_json(file_path, data):
	with open(file_path, 'w') as file:
		json.dump(data, file)
		print("written to file")


if __name__ == "__main__":
	
	while(True):
		print(50*"-")
		read()
		print(50*"-")		
		data = read_json("/home/christianaltrichter/roomstatus.json")
		print("CURRENT OCCUPANCY OF ROOMS IN DATABASE")
		print(data)
		print(50*"-")
		new_data = check_room(data)
		print("ACTIVITY RECORDED, OCCUPANCY OF CURRENT ROOM (ROOM 6) CHANGED")
		print(new_data)
		print(50*"-")
		write_json("/home/christianaltrichter/roomstatus.json", new_data)
		print(50*"-")
		data2 = read_json("/home/christianaltrichter/roomstatus.json")
		print("RESULTS SAVED TO DATABASE AND READ AGAIN")
		print(data2)
		print(50*"-")
		time.sleep(5)
