package com.gmail.leal.mendo.QTools;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.plugin.java.JavaPlugin;

public class QTools extends JavaPlugin implements Listener{
	
	@Override
    public void onEnable() {
        // TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("Launching QTools...");
		Bukkit.getPluginManager().registerEvents(new Listeners(this),  this);
		this.saveDefaultConfig(); // Create config file if it doesn't exist already
		reloadConfig();
		
		getCommand("soulenchant").setTabCompleter(new TabCompletion());
		
		// in case of /reload used and storage about players in a hashmap or PlayerJoinEvent
		//for (Player player : Bukkit.getServer().getOnlinePlayers()) {
		//    playerList.put(player.getName(), playerData(player));
		//}
    }
    
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("Disabling QTools...");
    }
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	// the /souls command that shows a player's tiago souls
    	if(cmd.getName().equalsIgnoreCase("souls"))  {
    		if(sender.hasPermission("quickbarplugin.souls") && sender instanceof Player)  {
    			Player player = Bukkit.getPlayer(sender.getName());
    			if(args.length == 0)  {
        			if(player != null)  {
        				sender.sendMessage("§5Tiago Soul Balance: " + SoulEnchantments.getSouls(player, this.getConfig()));
        			}
        			else  {
        				sender.sendMessage("§4Invalid command use, you are not a player.");
        			}
        			return true;
        		}
        		else if(args.length == 3 && args[0].equalsIgnoreCase("give"))  {
        			Player receiver = Bukkit.getPlayer(args[1]);
        			try  {
        				int amount = Integer.parseInt(args[2]);
        				return SoulEnchantments.soulTransfer(amount, player, receiver, args[1], this);
        			}
        			catch(NumberFormatException nfe)  {
    					sender.sendMessage("§4" + args[2] + " is not a valid amount");
    					return true;
    				}
        		}
        		else   {
        			return false;
        		}
        	}
    		else  {
    			sender.sendMessage("§4You don't have permission to manipulate tiago souls");
    			return true;
    		}
    	}
    	else if(cmd.getName().equalsIgnoreCase("deathstop"))  {
    		if(!sender.hasPermission("quickbarplugin.deathstop"))  {
    			sender.sendMessage("§4You don't have permission to view this leaderboard");
    		}
    		else  {
				List<String> leaderboard = Trackers.getLeaderboard("deaths", "Deaths", this.getConfig());
				for(String message : leaderboard)  {
					sender.sendMessage("§5" + message);
				}
			}
			return true;
    	}
    	
    	else if(cmd.getName().equalsIgnoreCase("applestop"))  {
    		if(!sender.hasPermission("quickbarplugin.applestop"))  {
    			sender.sendMessage("§4You don't have permission to view this leaderboard");
    			return true;
    		}
    		else  {
    			List<String> leaderboard = Trackers.getLeaderboard("applesEaten", "Apples Eaten", this.getConfig());
    			for(String message : leaderboard)  {
    				sender.sendMessage("§5" + message);
    			}
    			return true;
    		}
    	}
    	
    	// The /janita command that shows a player's death count
    	else if(cmd.getName().equalsIgnoreCase("deaths"))  {
    		
    		// The /janita command that returns a player's amount of deaths
    		if(sender.hasPermission("quickbarplugin.deaths"))  {
    			if(args.length != 1)  {
        			return false;
        		}
        		else {
        			if(Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore() && GeneralUtil.isInConfig(args[0], "deaths.", this.getConfig()))  {
        				sender.sendMessage("§d" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1) + " has died " + this.getConfig().getString("deaths." + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()) + " time(s)");
        			}
        			else  {
        				sender.sendMessage("§4Couldn't find player or player has never died");
        			}
        			return true;
        		}
    		}
    		else  {
    			sender.sendMessage("§4You don't have permission to use the /janita command");
    			return true;
    		}
    	}
    	
    	// The /janita2 command that shows a player's amount of apples eaten
    	else if(cmd.getName().equalsIgnoreCase("apples"))  {
    		if(!sender.hasPermission("quickbarplugin.apples"))  {
    			sender.sendMessage("§4You do not have permission for this");
    			return true;
    		}
    		if(args.length != 1)  {
    			sender.sendMessage("§4Invalid amount of arguments");
    			return false;
    		}
    		if(!(sender instanceof Player))  {
    			sender.sendMessage("§4Only players can use this command");
    			return true;
    		}
    		OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
    		if(p.hasPlayedBefore() && this.getConfig().isSet("applesEaten." + p.getUniqueId().toString()))  {
				sender.sendMessage("§d" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1) + " has eaten " + this.getConfig().getString("applesEaten." + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()) + " apple(s)");
				return true;
    		}
			else  {
				sender.sendMessage("§4Couldn't find player or player has never eaten an apple");
				return true;
			}
    		
    	}
    	
    	// The /tiago command that just returns gay in pink
    	else if(cmd.getName().equalsIgnoreCase("tiago"))  {
    		if(sender.hasPermission("quickbarplugin.tiago"))  {
    			// display message "gay"
    			sender.sendMessage("§dgay");
    		}
    		else  {
    			// inform the user that they don't have the permission to use this command
    			sender.sendMessage("§4You don't have permission to use this command.");
    		}
    		return true;
    	}
    	
    	// The /lucas command that just returns "-> is useless" in pink
    	else if(cmd.getName().equalsIgnoreCase("lucas"))  {
    		if(sender.hasPermission("quickbarplugin.lucas"))  {
    			// display message "gay"
    			sender.sendMessage("§d-> is useless");
    		}
    		else  {
    			// inform the user that they don't have the permission to use this command
    			sender.sendMessage("§4You don't have permission to use this command.");
    		}
    		return true;
    	} 
    	
    	// The /emma command to give the quickbar switching tool
    	else if(cmd.getName().equalsIgnoreCase("qbs"))  {
    		if(sender.hasPermission("quickbarplugin.qbs") && sender instanceof Player)  {
    			Player player = (Player) sender;
    			// give the quickbar switching tool
    			PlayerInventory inventory = player.getInventory();
    			inventory.addItem(QuickbarSwitcher.getQbs());
    			return true;
    		}
    		else  {
    			sender.sendMessage("§4You are not allowed to do this.");
    			return true;
    		}
    	}
    	
    	// The /emma2 command to convert xp to xp bottles
    	else if(cmd.getName().equalsIgnoreCase("bottles"))  {
    		if(sender.hasPermission("quickbarplugin.bottles") && sender instanceof Player)  {
    			
    			// Check if the command has enough arguments
    			if(args.length != 1)  {
    				return false;
    			}
    			else  {
    				if(GeneralUtil.isInteger(args[0]))  {
    					int amount = Integer.parseInt(args[0]);
    					XPUtil.convertToBottles((Player) sender, amount);
    					return true;
    				}
    				else  {
    					sender.sendMessage("§4Invalid amount of xp bottles");
    					return true;
    				}
    			}
    			
    		}
    		else  {
    			sender.sendMessage("§4You are not allowed to do this.");
    			return true;
    		}
    	}
    	
    	else if(cmd.getName().equalsIgnoreCase("soulenchant"))  {
    		if(sender.hasPermission("quickbarplugin.soulenchant") && sender instanceof Player)  {
    			if(args.length != 1)  {
    				sender.sendMessage("§4Invalid arguments");
    				return false;
    			}
    			else  {
    				return SoulEnchantments.soulEnchant(args[0], (Player) sender, this);
    			}
    		}
    		else  {
    			sender.sendMessage("§4You are not allowed to do this.");
    			return true;
    		}
    	}
    	
    	else if(cmd.getName().equalsIgnoreCase("extract"))  {
    		if(!sender.hasPermission("quickbarplugin.extract") || !(sender instanceof Player))  {
    			sender.sendMessage("§4You are not allowed to do this.");
    			return true;
    		}
    		
    		if(args.length != 1)  {
    			sender.sendMessage("§4Invalid arguments");
				return false;
    		}
    		Enchantment enchantment = null;
    		try  {
    			enchantment = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(args[0]));
    		} catch(IllegalArgumentException e)  {
    			sender.sendMessage("§4Invalid enchantment");
    			return true;
    		}
    		
    		if(enchantment == null)  {
    			sender.sendMessage("§4Invalid enchantment");
				return true;
    		}
    		
    		Player player = (Player) sender;
    		ItemStack item = player.getInventory().getItemInMainHand();
    		EnchantmentExtraction.extract(player, item, enchantment, 2000);
    		return true;
    	}
    	
    	// If this has happened the function will return true. 
        // If this hasn't happened the value of false will be returned.
    	return false; 
    }
}


