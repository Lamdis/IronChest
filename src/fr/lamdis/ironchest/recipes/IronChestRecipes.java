package fr.lamdis.ironchest.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import fr.lamdis.ironchest.IronChest;
import fr.lamdis.ironchest.items.DiamondChestItem;
import fr.lamdis.ironchest.items.IronChestItem;

public class IronChestRecipes {
	
	public static void registerRecipes() {
		if(IronChest.CONFIG_CRAFT.getBoolean("ironchest.active", true)) {
			IronChestItem ironChestItems = new IronChestItem(1);
			createRecipe(ironChestItems.getItemStack(), "ironchest.table", "ironchest.ingredients", "ironchest");
		}
		if(IronChest.CONFIG_CRAFT.getBoolean("diamondchest.active", true)) {
			DiamondChestItem diamondChestItems = new DiamondChestItem(1);
			createRecipe(diamondChestItems.getItemStack(), "diamondchest.table", "diamondchest.ingredients", "diamondchest");
		}
	}
	
	private static void createRecipe(ItemStack it, String t, String i, String id) {
		List<String> table = IronChest.CONFIG_CRAFT.getStringList(t);
		Map<Character, Material> ingre = new HashMap<>();
		ConfigurationSection ingredients = IronChest.CONFIG_CRAFT.getConfigurationSection(i);
		for (String key : ingredients.getKeys(false)) {
			  Material material = Material.valueOf(ingredients.getString(key));
			  ingre.put(key.charAt(0), material);
		}
		NamespacedKey key = new NamespacedKey(IronChest.plugin, id);
		ShapedRecipe recipe = new ShapedRecipe(key, it);
		recipe.shape(table.toArray(new String[0]));
		for (Map.Entry<Character, Material> entry : ingre.entrySet()) {
			recipe.setIngredient(entry.getKey(), entry.getValue());
		}
		IronChest.SERVER.addRecipe(recipe);
	}
	
}
