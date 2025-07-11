package com.gmail.leal.mendo.QTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class GeneralUtil {
	
	public static final List<Material> hoeFortuneAffectedBlocks = new ArrayList<Material>(Arrays.asList(new Material[] {Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOTS}));
	public static final List<Material> woodTypes = new ArrayList<Material>(Arrays.asList(new Material[] {Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.MANGROVE_LOG, Material.CHERRY_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM}));
	
	private static final List<Material> hoes = new ArrayList<Material>(Arrays.asList(new Material[] {Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE}));
	
	
	public static void addLore(ItemStack item, String newLore)  {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null)  {
			List<String> newList = new ArrayList<String>();
			newList.add(newLore);
			meta.setLore(newList);
		}
		else  {
			lore.add(newLore);
		}
		item.setItemMeta(meta);
	}
	
	public static boolean hasLore(ItemStack item, String lore)  {
		ItemMeta meta = item.getItemMeta();
		List<String> itemLore = meta.getLore();
		if(itemLore == null) return false;
		for(String s : itemLore)  {
			if(lore.equalsIgnoreCase(s))  {
				return true;
			}
		}
		return false;
	}
	
	public static void clearLore(ItemStack item)  {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(new ArrayList<String>());
		item.setItemMeta(meta);
	}
	
	public static void breakBlockNormally(Block block, World world)  {
		List<ItemStack> drops = (List<ItemStack>) block.getDrops();
		block.setType(Material.AIR);
		Location location = block.getLocation();
		for(ItemStack is : drops)  {
			world.dropItem(location, is);
		}
	}
	
	public static void breakBlockWithMagnetism(Block block, World world, Player player)  {
		List<ItemStack> drops = (List<ItemStack>) block.getDrops();
		block.setType(Material.AIR);
		for(ItemStack is : drops)  {
			giveItem(player, is);
		}
	}
	
	public static boolean isHoldingHoe(Player player)  {
		ItemStack hand = player.getInventory().getItemInMainHand();
		Material type = hand.getType();
		return hoes.contains(type);
	}
	
	public static int randomInRange(int min, int max)  {
		Random rand = new Random();
		return rand.nextInt(max) + 1;
	}
	
	public static boolean takeAmountFromInventory(Player player, ItemStack item, int amount)  {
		int count = 0;
		PlayerInventory inventory = player.getInventory();
		for(int i = 0; i < inventory.getSize(); i++)  {
			ItemStack current = inventory.getItem(i);
			if(current != null && current.getType() != null && current.getType().equals(item.getType()))  {
				int left = amount - count;
				if(current.getAmount() > left)  {
					count += amount;
					current.setAmount(current.getAmount() - left);
					break;
				}
				else if(current.getAmount() == left)  {
					count += amount;
					inventory.setItem(i, new ItemStack(Material.AIR));
					break;
				}
				else {
					count += current.getAmount();
					inventory.setItem(i, new ItemStack(Material.AIR));
				}
			}
		}
		
		if(count < amount)  {
			// Give items back since it failed
			giveItem(player, new ItemStack(item.getType(), count));
			return false;
		}
		return true;
	}
	
	public static void takeItemFromMainHand(Player player)  {
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item.getAmount() > 1)  {
			item.setAmount(item.getAmount() - 1);
		}
		else  {
			player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
		}
	}
	
	/**
	 * Checks if a plugin is enabled
	 * @param pluginName The name of the plugin to check for
	 * @return True if the plugin is enabled, false otherwise
	 */
	public static boolean isPluginEnabled(String pluginName)  {
		return Bukkit.getPluginManager().getPlugin(pluginName) != null;
	}
	
	/**
	 * Removes item drops of the given type in the chunk at the given location after the given delay
	 * @param type The type of the item drops to remove, null to remove all item drops
	 * @param delay The delay in ticks after which to remove the items
	 * @param loc The location in the chunk in which to remove the items
	 * @param plugin The plugin running this task
	 */
	public static void removeItemsInChunkAfterDelay(final Material type, long delay, final Location loc, Plugin plugin)  {
		new BukkitRunnable()  {
			
			@Override
			public void run()  {
				Chunk chunk = loc.getChunk();
				Entity[] entities = chunk.getEntities();
				for(Entity ent : entities)  {
					if(ent instanceof Item)  {
						Item item = (Item) ent;
						if(type == null || item.getItemStack().getType() == type)  {
							item.remove();
						}
					}
				}
			}
		}.runTaskLaterAsynchronously(plugin, delay);
	}
	
	public static void giveItem(Player p, ItemStack item)  {
    	Inventory inv = p.getInventory();
    	if(inv.firstEmpty() == -1)  {
    		p.getWorld().dropItem(p.getLocation(), item);
    	}
    	else  {
    		inv.addItem(item);
    	}
    }
	
	public static void swapInStringList(String[] list, int i, int j)  {
		String temp = list[j];
		list[j] = list[i];
		list[i] = temp;
	}
	
	public static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	/**
     * Checks if the player is in the config file for the certain property
     * @param player The player
     * @param pathPrefix the prefix to the path of the property including the '.', e.g. "deaths." or "applesEaten."
     * @return true if the player has a value set for this property in the config file, false otherwise
     */
    public static boolean isInConfig(String player, String pathPrefix, FileConfiguration config)  {
    	if(config.isSet(pathPrefix + Bukkit.getOfflinePlayer(player).getUniqueId()))  {
    		return true;
    	}
    	else  {
    		return false;
    	}
    }
    
    /**
     * Gets the equipment slot for a certain item or null if the item is not armor or a shield
     * @param item The item to find an equipment slot for
     * @return The equipment slot that the given item corresponds to
     */
    public static EquipmentSlot getEquipmentSlot(ItemStack item)  {
    	Material t = item.getType();
    	if(t.equals(Material.SHIELD))  {
    		return EquipmentSlot.OFF_HAND;
    	}
    	else if(t.equals(Material.LEATHER_HELMET) || t.equals(Material.IRON_HELMET) || t.equals(Material.CHAINMAIL_HELMET) || t.equals(Material.GOLDEN_HELMET) || t.equals(Material.DIAMOND_HELMET) || t.equals(Material.NETHERITE_HELMET))  {
    		return EquipmentSlot.HEAD;
    	}
    	else if(t.equals(Material.LEATHER_CHESTPLATE) || t.equals(Material.IRON_CHESTPLATE) || t.equals(Material.CHAINMAIL_CHESTPLATE) || t.equals(Material.GOLDEN_CHESTPLATE) || t.equals(Material.DIAMOND_CHESTPLATE) || t.equals(Material.NETHERITE_CHESTPLATE))  {
    		return EquipmentSlot.CHEST;
    	}
    	else if(t.equals(Material.LEATHER_LEGGINGS) || t.equals(Material.IRON_LEGGINGS) || t.equals(Material.CHAINMAIL_LEGGINGS) || t.equals(Material.GOLDEN_LEGGINGS) || t.equals(Material.DIAMOND_LEGGINGS) || t.equals(Material.NETHERITE_LEGGINGS))  {
    		return EquipmentSlot.LEGS;
    	}
    	else if(t.equals(Material.LEATHER_BOOTS) || t.equals(Material.IRON_BOOTS) || t.equals(Material.CHAINMAIL_BOOTS) || t.equals(Material.GOLDEN_BOOTS) || t.equals(Material.DIAMOND_BOOTS) || t.equals(Material.NETHERITE_BOOTS))  {
    		return EquipmentSlot.FEET;
    	}
    	else  {
    		return null;
    	}
    }
    
    /**
     * Apply default attributes to an item (only if it has no soul enchantments already), works for diamond and netherite armor items (this is useful because when using addAttributeModifier it removes vanilla attributes from the item)
     * @param item the item to apply the default attributes to
     */
    public static void applyDefaultAttributes(ItemStack item)  {
    	List<String> soulEnchantments = SoulEnchantments.getSoulEnchantmentsWithAttributes();
    	for(String enchantment : soulEnchantments)  {
    		if(SoulEnchantments.hasCustomEnchant(item, enchantment))  {
    			return;
    		}
    	}
    	
    	Material type = item.getType();
    	ItemMeta meta = item.getItemMeta();
    	if(type.equals(Material.DIAMOND_HELMET) || type.equals(Material.DIAMOND_CHESTPLATE) || type.equals(Material.DIAMOND_LEGGINGS) || type.equals(Material.DIAMOND_BOOTS))
    		meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "Base Armor Toughness", 2.0, AttributeModifier.Operation.ADD_NUMBER, getEquipmentSlot(item)));
    	else if(type.equals(Material.NETHERITE_HELMET) || type.equals(Material.NETHERITE_CHESTPLATE) || type.equals(Material.NETHERITE_LEGGINGS) || type.equals(Material.NETHERITE_BOOTS))  {
    		meta.addAttributeModifier(Attribute.ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "Base Armor Toughness", 3.0, AttributeModifier.Operation.ADD_NUMBER, getEquipmentSlot(item)));
    		meta.addAttributeModifier(Attribute.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "Base Knockback Resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, getEquipmentSlot(item)));
    	}
    	switch(type)  {
    	case DIAMOND_HELMET:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
    		break;
    	case DIAMOND_CHESTPLATE:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
    		break;
    	case DIAMOND_LEGGINGS:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
    		break;
    	case DIAMOND_BOOTS:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
    		break;
    	case NETHERITE_HELMET:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
    		break;
    	case NETHERITE_CHESTPLATE:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 8.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
    		break;
    	case NETHERITE_LEGGINGS:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
    		break;
    	case NETHERITE_BOOTS:
    		meta.addAttributeModifier(Attribute.ARMOR, new AttributeModifier(UUID.randomUUID(), "Base Armor", 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
    		break;
    	default:
    		break;
    	}
    	item.setItemMeta(meta);
    }
    
    public static void changeItemName(ItemStack item, String newName)  {
    	ItemMeta meta = item.getItemMeta();
    	meta.setDisplayName(newName);
    	item.setItemMeta(meta);
    }
}
