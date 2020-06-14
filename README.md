# TeamSpeak-AllDice
Is a TeamSpeak-Text-Bot which enables every user on a TeamSpeak-Server to roll dices which are evaluated depending on the used inputcommand.
Currently AllDice only supports german as a output language, different output languages may be added in a future updates or on request.

#Which settings are supported?
AllDice supports many different commands for different settings.
These include several Savage-World, DSA and several different test throw's.
Commands for settings like Fu and Fate will also be added in future updates.

#How do i install TeamSpeak-AllDice?
First of all you have to know that AllDice is a QueryClient and needs a server or something similar which supports Java (eg. a Raspberry Pi) to run on.
To install AllDice you have to download the Jar file and put it in the directory where it should be run later.
Then you start the Jar file for the first time (eg. with the command "java -jar TeamSpeak3-AllDice-Query.jar).
After that, you will see that AllDice has created a few .json files, most importantly you have to edit the settings.json file to let AllDice know on which server he should connect to and which credentials he should use.
If everything has been configured correctly, you should be able to run AllDice with the same command as before.

#How do i use TeamSpeak-AllDice?
After the successful setup and start of AllDice, you should be able to see a QueryClient in the Channel you named in the settings.json file. 
(Assuming you have turned on "Show ServerQuery Clients" in your TeamSpeak Settings, but even if you havent turned that option on, you should still be able to interact with AllDice.)
After joining the given channel, you can use the command "!help" to get a helppage with all supported commands.
Most of the other commands require you assign AllDice to yourself, you can do that by typing "!follow", after which AllDice will follow you in every channel you move into.
When you are done using AllDice, you can simply type "!leave" or just leave the TeamSpeak Server yourself.
