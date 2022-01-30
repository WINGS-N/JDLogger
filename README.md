# JDLogger

**JDLogger is a Minecraft plugin that allows you to rollback a player's inventory if he died.**

Supports **Minecraft v. 1.13.x - 1.18.x**

In addition, **it can tell you a lot of information about the death of a player, for example, the coordinates, the cause, the TPS of the server and the ping of the player at that moment, so that you can know for sure if he is lying, saying that he died from server lags or ping.**

This plugin can also **protect players from death at a low TPS value and a high ping value**, which you can define in `config.yml`. When the TPS is lower, or the ping is higher than the set value, the player will not receive damage, instead he will see a message in the chat that there are problems with the connection or the server. You can also define these messages in `config.yml`.

JDLogger has a **built-in Auto Updater**, so you don't have to worry about updates. They will be installed automatically when the server is started and shut down.

**DO NOT RENAME JAR FILES! It should only be called JDLogger.jar for plugin and JDLogger_WinUPD.jar for Windows Updater.**
