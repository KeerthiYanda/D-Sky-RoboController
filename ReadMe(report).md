# D-SKY-Robo-Controller
v1

D-SKY-Robo-Controller
-------------------------

We have developed an android app where any user can control the robot(romo) motions. The user can control it using the text commands or speech commands.

How it is used in our project:
-------------------------------

Our project is University Guider, in which the robot stops at each campus location and speaks about the location. If the instructor at the respect location wants to show some specific locations or content he/she can control the room by speech instructions. So we used Google STT API to convert input speech to text commands. 

API URL:
---------
http://translate.google.com/translate_stt

Instructions:
-------------------------
1)	Run the android application using android studio and ios application using XCode. 
2)	Enter the ip address and port number of the ihone in which ios app is running.
3)	Click on the connect button to establish socket connection. 
4)	Once the connection is established, enter the text command in the provided text box which you want to be done by romo.
5)	For controlling using speech, click on "mike" icon and hold. 
6)	Speech the commands that romo needs to follow. 
7)	These commands from android app are transferred to iPhone using socket connection. 
8)	once it reached ios application romo will perform the corresponding action.
