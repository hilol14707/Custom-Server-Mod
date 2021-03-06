# Custom Server Mod
##### A Forge 1.16.1 Minecraft Mod

This mod is only required on the server. The client does not need this mod if it is on the server.

Note: This was coded using `Forge version 32.0.106`

# Features

## Commands
### /coords
Description: Gets the current x, y, z and dimension the specified player is

Usage: ```/coords <player>```

### /tpa
Description: Asks another player to teleport to them.

Usage: ```/tpa <player>```

### /tpaaccept
Description: Accepts a player's tpa request if they sent one.

The requestor will be teleported to the acceptor.

Usage: ```/tpaaccept <player>```

*(NOTE: the acceptor has 2 minutes to accept the tpa request before it expires)*

### /tpdim
can use `/execute in <namespace:dimension_name> run tp @s <x> <y> <z>`

## Logging
Logs the following stuff to a log file.

The log file is in the Minecraft `logs/csm` folder
- Commands
	- /gamemode
	- /give
	- /say
	- /tp
	- use the config file to add more
- Chat messages
- Player Advancements
- Player Death
- Player Join/Leave

# Installation
Go to releases and download the latest version then put that into your mods folder.
[(Requires Minecraft Forge for 1.16.1)](https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.16.1.html)

# Build
To build this file yourself clone this repository and open a command prompt in this folder.

Run the command 
```batch
 gradlew build
```