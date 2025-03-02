package fr.lamdis.ironchest.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.holder.IronChestHolder;
import fr.lamdis.ironchest.inventory.manager.ActiveChestManager;

public class Pages {
	
	public static int getActualPage(Inventory inv) {
		ItemStack item = inv.getItem(49);
		if(item != null && item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {
			return item.getItemMeta().getCustomModelData();
		}
		return 1;
	}
	
	public static void nextPage(Player player, Inventory inv) {
		if(canNext(inv)) {
			if(inv.getHolder() instanceof IronChestHolder) {
	        	IronChestHolder holder = (IronChestHolder) inv.getHolder();
	        	
				final int actualPage = getActualPage(inv);
				
				InventoryCloseEvent event = new InventoryCloseEvent(player.getOpenInventory());
				Bukkit.getPluginManager().callEvent(event);
	            
	            Inventory inventory = ActiveChestManager.getActiveChest(holder.getChestLocation(), (actualPage + 1));
	            
	            player.openInventory(inventory);
	            player.updateInventory();
			}
		}
	}
	
	public static void backPage(Player player, Inventory inv) {
		if(canBack(inv)) {
			if(inv.getHolder() instanceof IronChestHolder) {
	        	IronChestHolder holder = (IronChestHolder) inv.getHolder();
	        	
				final int actualPage = getActualPage(inv);
	        	
				InventoryCloseEvent event = new InventoryCloseEvent(player.getOpenInventory());
				Bukkit.getPluginManager().callEvent(event);
	            
	            Inventory inventory = ActiveChestManager.getActiveChest(holder.getChestLocation(), (actualPage - 1));
	            
	            player.openInventory(inventory);
	            player.updateInventory();
			}
		}
	}
	
	public static boolean canNext(Inventory inv) {
		if (inv.getItem(51) != null && inv.getItem(51).getType() == Material.LIME_STAINED_GLASS_PANE){
			return true;
		}
		return false;
	}
	
	public static boolean canBack(Inventory inv) {
		if (inv.getItem(47) != null && inv.getItem(47).getType() == Material.LIME_STAINED_GLASS_PANE){
			return true;
		}
		return false;
	}
	
}
