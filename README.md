# TeamSpeak-AllDice
Is a TeamSpeak-Text-Bot which enables every user on a TeamSpeak-Server to roll dices which are evaluated depending on the used inputcommand.
Currently AllDice only supports german as a output language, different output languages may be added in a future updates or on request.
(Notice that AllDice isnt a single bot, its more like a bot network which supports up to five independent bots on one server which are crated automatically after assigning one of the bots to yourself - more on that later)

# Which P&P systems are supported?
AllDice supports many different commands for different settings.
| Setting | Status |
| --- | --- |
| Savage Worlds | supported |
| DSA | Supported |
| Cthuhlu | Supported |
| Fate | future release |
| Fu | future release |

And many other custom commands.

# How do I install TeamSpeak-AllDice?
First of all you have to know that AllDice is a QueryClient and needs a server or something similar which supports Java (eg. a Raspberry Pi) to run on.
To install AllDice you have to download the .jar file and put it in the directory where it should run later.
Then you start the downloaded .jar file for the first time (eg. with the command "java -jar TeamSpeak3-AllDice-Query.jar).
After that, you will see that AllDice has created a few .json files which you have to edit, most importantly you have to edit the settings.json file to let AllDice know on which server he should connect to and which credentials he should use.
If everything has been configured correctly, you should be able to run AllDice with the same command you used before.

# How do I use TeamSpeak-AllDice?
After the successful setup and start of AllDice, you should be able to see a QueryClient in the Channel you named in the settings.json file. 
(Assuming you have turned on "Show ServerQuery Clients" in your TeamSpeak Settings, but even if you havent turned that option on, you should still be able to interact with AllDice.)
After joining the given channel, you can use the command "!help" to get a helppage with all supported commands.
You will notice that AllDice doesnt respond to most of the other commands, that because you have to assign AllDice to yourself, you can do that by typing "!follow", after which AllDice should respond to all commands and also follow you around to other channels you may move to.
When you are done using AllDice, you can simply type "!leave" or just leave the TeamSpeak Server yourself.
