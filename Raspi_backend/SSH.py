import paramiko

host = ""
username = ""
password = ""

try:
	client = paramiko.SSHClient()
	client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
	
	client.connect(host, username=username, password=password)
	
	command = "ls"
	stdin, stdout, stderr = client.exec_command(command)
	
	print(stdout.read().decode())

finally:
	client.close()
