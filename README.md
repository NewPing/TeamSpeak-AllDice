# TeamSpeak-AllDice
Is a TeamSpeak text bot which enables every user on a TeamSpeak server to roll dices which are evaluated depending on the used inputcommand.
Currently AllDice only supports german as a output language, different output languages may be added in a future update or on request.
(Notice: AllDice isnt a single bot, its more like a bot network - more on that later)

# Which P&P systems are supported?
AllDice supports many different commands for different settings.
| Setting | Status |
| --- | --- |
| Savage Worlds | Supported |
| DSA | Supported |
| Cthuhlu | Supported |
| Fate | Future release |
| Fu | Future release |

And many other custom commands.

# How do I install TeamSpeak-AllDice?
First of all you have to know that AllDice is a QueryClient and needs a server or something similar which supports Java (eg. a Raspberry Pi) to run on.
To install AllDice you have to download the .jar file and put it in the directory where it should run later.
Then you start the downloaded .jar file for the first time (eg. with the command "java -jar TeamSpeak3-AllDice-Query.jar).
After that, you will see that AllDice has created a few .json files which you have to edit, most importantly you have to edit the settings.json file to let AllDice know on which server it should connect to and which credentials it should use.
If everything has been configured correctly, you should be able to run AllDice with the same command you used before.

# How do I use TeamSpeak-AllDice?
After the successful setup and start of AllDice, you should be able to see a QueryClient in the channel you named in the settings.json file. 
(Assuming you have turned on "Show ServerQuery Clients" in your TeamSpeak settings, but even if you havent turned that option on, you should still be able to interact with AllDice.)
After joining the given channel, you can use the command "!help" to get a helppage with all supported commands.
You will notice that AllDice doesnt respond to most of the other commands, that because you first have to assign AllDice to yourself. You can assign AllDice to yourself by typing "!follow", after which AllDice should respond to all commands and also follow you around to other channels you may move to.
When you are done using AllDice, you can simply type "!leave" or just leave the TeamSpeak Server yourself.

# Further technical details
TeamSpeak-AllDice is not a single TeamSpeak query client, its more like a bot network.
After assigning AllDice to yourself - another bot instance will be created, joins the server and moves into the specified channel in the settings.json.
This garanties that multiple P&P sessions can be held at a time, each with its own bot.
Sadly there is a limit how many sessions of queryclients with the same identity can be active at the same time, this limits the number of bots which can be used on a TeamSpeak server (experience may vary from servers to server, i experienced a limit of five bots).
If the limit is reached, the server may ban the bot for several minutes and you it can happen that you then have to restart AllDice (a option to set a session limit in the setttings.json may be added in a future update).
