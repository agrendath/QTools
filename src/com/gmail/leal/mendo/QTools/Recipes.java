package com.gmail.leal.mendo.QTools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Recipes {
	
	
	/**
	 * Create the recipe for the villager container item
	 * @return The ShapedRecipe item representing the recipe for the villager container item
	 */
	public static ShapedRecipe villagerContainer(Plugin plugin)  {
		// Get an enderchest as the icon for the villager container item
		ItemStack chest = new ItemStack(Material.ENDER_CHEST);
		
		// Change the name of the enderchest
		GeneralUtil.changeItemName(chest, Constant.VILLAGER_CONTAINER_NAME);
		
		// Add a lore line
		GeneralUtil.addLore(chest, Constant.VILLAGER_CONTAINER_EMPTY_LORE);
		
		// Create the recipe object that will contain the recipe
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, Constant.VILLAGER_CONTAINER_KEY), chest);
		
		// Set the shape of the recipe
		recipe.shape(" e ", " h ", " c ");
		
		// Set the ingredients of the recipe
		recipe.setIngredient('e', Material.EMERALD_BLOCK);
		recipe.setIngredient('h', Material.HOPPER);
		recipe.setIngredient('c', Material.CHEST);
		
		// Finally return the shaped recipe
		return recipe;
	}
	
	public static boolean isEmptyVillagerContainer(ItemStack item)  {
		if(!item.getItemMeta().getDisplayName().equals(Constant.VILLAGER_CONTAINER_NAME)) return false;
		if(!GeneralUtil.hasLore(item, Constant.VILLAGER_CONTAINER_EMPTY_LORE)) return false;
		return true;
	}
	
	public static boolean isFullVillagerContainer(ItemStack item)  {
		if(!item.getItemMeta().getDisplayName().equals(Constant.VILLAGER_CONTAINER_NAME)) return false;
		if(!GeneralUtil.hasLore(item, Constant.VILLAGER_CONTAINER_FULL_LORE)) return false;
		return true;
	}

}
