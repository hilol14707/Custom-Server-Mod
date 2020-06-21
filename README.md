# Custom Server Mod
##### A Forge 1.12.2 Minecraft Mod

This mod is only required on the Server. The client does not need this mod if it is on the Server.

# Features

## Commands
### coords
Description: Gets the current x, y, z and dimension the specified player is

Usage: ```/coords <player>```
### tpdim
Description: Teleports specified player to the coordinate in specified dimension

Usage: ```/tpdim <player> <x> <y> <z> <dimension number>```

Number|Dimension
---|---
-1|Nether
0|Overworld
1|End

## Logging
Logs the following stuff to a log file.

The log file is in the Minecraft `logs/csm` folder
- Commands
	- /coords
	- /tpdim
	- /gamemode
	- /give
	- /say
	- /tp
- Chat messages
- Player Advancements
- Player Death
- Player Join/Leave

# Installation

Go to releases and download the latest version then put that into your mods folder.
[(Requires Minecraft Forge for 1.12.2)](https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.12.2.html)