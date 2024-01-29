# QTools
Minecraft Spigot QTools Plugin
For spigot 1.20.4

Commands:
- /qbs - Gives the player a special tool that lets you switch your quickbar with the first line of your inventory by right-clicking
- /bottles \<amount\> - Converts your xp to a certain amount of experience bottles, experience bottles
- /deaths \<playername\> - Shows how many time a player has died since installation of the plugin
- /apples \<playername\> - Shows how many apples a player has eaten since installation of the plugin
- /tiago - Displays useful information about tiago
- /lucas - Displays useful information about lucas
- /souls - Shows a player's balance of Souls
- /souls give \[playername\] \[amount\] - Give the specified amount of souls to another player (this will deduct them from your balance, obviously)
- /soulenchant \<enchantment\> - Enchants the item the player is holding with a soulenchantment, see available enchantments below
- /deathstop - Shows the leaderboard of the top 10 players with most deaths
- /applestop - Shows the leaderboard of the top 10 players with most apples eaten
- /extract \<enchantment\> - Extracts an enchantment from the item in the player's hand by giving it to them in the form of an enchanted book and removing it from the item, this costs 2000 xp and does not work for soul enchantments

Other Functionality:
- Fortune on hoes also increases drops from harvesting wheat, carrots, potatoes and beetroot with the hoe

Soul Enchantments:
- Magnetism: usable on all tools and weapons, when you destroy blocks or kill mobs/players the loot drops and xp drops get absorbed right into your inventory/xp bar
- Indestructibility: usable on diamond and netherite swords, makes the item indestructible/unbreakable
- Harvesting: usable on all tools and weapons, will drop double xp from any entity you kill or any block you break
- Vampirism: usable on swords, bows and axes, will heal the attacker for 10% of damage dealt, bows will only heal for 5%
- Swiftness: usable on all boots, will make the player faster when equipped with it
- Looting: usable on bows, this is just a way to put the looting (only 3) enchantment on bows which is not possible in vanilla minecraft
- Thunderlord: usable on all weapons and bows, gives a 10 % chance to summon lightning doing 25% of the original hit's damage extra
- Absorption: usable on diamond or netherite armor and shields, gives the wearer a 20% health boost (aka 2 extra hearts)
- Reflection: usable on shields, will reflect 10% of potential damage taken
- Felling: usable on axes, will take down all log/stem blocks above the one the axe broke (cut down trees in one hit). Works together with magnetism.

Soul Enchantments Costs (+lvl equivalent):
- Magnetism: 1 Soul and 3000 xp (lvl 40)
- Indestructibility: 1 Soul and 3500 xp (lvl 42)
- Harvesting: 1 Soul and 4000 xp (lvl 44)
- Vampirism: 1 Soul and 5000 xp (lvl 48)
- Swiftness: 1 Soul and 5000 xp (lvl 48)
- Looting: 0 Souls and 3000 xp (lvl 40)
- Thunderlord: 1 Soul and 4500 xp (lvl 46)
- Absorption: 1 Soul and 5000 xp (lvl 48)
- Reflection: 1 Soul and 5000 xp (lvl 48)
- Felling: 1 Soul and 5000 xp (lvl 48)

Permission Nodes:
- quickbarplugin.tiago: Grants access to the /tiago command
- quickbarplugin.qbs: Grants access to the /emma command and the quickbar switching tool
- quickbarplugin.bottles: Grants access to the /emma2 command to convert xp into xp bottles
- quickbarplugin.deaths: Grants access to the /janita command
- quickbarplugin.apples: Grants access to the /janita2 command
- quickbarplugin.lucas: Grants access to the /lucas command
- quickbarplugin.souls: Grants access to the /souls command and the manipulating of souls
- quickbarplugin.soulenchant: Grants access to the /soulenchant command
- quickbarplugin.deathstop: Grants access to the /janitatop command
- quickbarplugin.applestop: Grants access to the /janita2top command
- quickbarplugin.extract: Grants access to the /extract command
