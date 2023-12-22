# Housekeeper

## Description

This app aims to improve the efficiency of housekeeping employees while at the same time to maintain the health quality of the employees. 
Moreover, there is very little statistical data driven decision making processes when it comes to hotel operations. 

## Installation
`git clone https://github.com/JuryAndrea/Project_Housekeeper.git`

## Usage

PLEASE NOTE THAT THE PROJECT IS NOT RUNNABLE WITHOUT THE RASPBERRY PI DEVICE AND OUR NFC TAG(s).

In additon, please make sure for the SSH connection between Android phone and RaspberryPi to work, they must be conencted to the same WIFI network. 

To run the project succesfully, please clone the project repository. 

Then launch on the raspberry pi the file `housekeeper.py`. Here, please ensure that the wiring of the RaspberryPi with the breadboard connecting to the NFC reader is properly set up. 

Furthermore, once the repository has been cloned, pleas ensure that a phone with API 33 and Android Version 12 or higher. 

  1. First, build and laucnh the app on the device. 
  2. Then, use the NFC tag provided by the team to log in into the application. 
  3. Once launched, the stepper app is automatically activated.
  4. The entire interface is subsequently free to use.

PLEASE, FOR DEMO REFER TO THE VIDEO FOLDER.

## Features

- LoginActivity: Use NFC tag to login into the app. 
- HomeFragement: Depicts in a progress bar the steps for a given employee inside and oustide the rooms.  
- RoomsFragment: Shows all the rooms assigned to an invidiual employee with the repective status.  
- CameraFragment: Enables report damage of furniture inside the room through the use of camera. 
- ReportFragement: Shows in a bar chart the comparison by hours between steps inside and outside of rooms. 
- ProfileFragment: Shows the pie chart of the overall progress of an employee. 
- Logout button: Logs the employee out. 

## Changed
I fixed the bug in HomeFragment that did not allow the live demo. The problem was concern a wrong setting of the layout. 
Moreover, since I did not have the Rasberry Pi and the NFC sensor, I hard-coded the json file with the status of the rooms for testing purpose.
