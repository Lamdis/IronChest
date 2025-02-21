package fr.lamdis.ironchest.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import fr.lamdis.ironchest.IronChest;

public class IronChestHelmetListener implements Listener {
	
    public static final String IRONCHEST_KEY = "iron_chest";

    // This method checks if the given item is an Iron Chest based on its PersistentDataContainer.
    private boolean isIronChestItem(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta() instanceof SkullMeta) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            NamespacedKey diamondKey = new NamespacedKey(IronChest.plugin, "diamond_chest");
            NamespacedKey ironKey = new NamespacedKey(IronChest.plugin, "iron_chest");
            if(meta.getPersistentDataContainer().get(diamondKey, PersistentDataType.BYTE) != null) {
				return true;
			} else if (meta.getPersistentDataContainer().get(ironKey, PersistentDataType.BYTE) != null) {
				return true;
			}
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    	if(event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD && event.isShiftClick()) {
			if (isIronChestItem(event.getCurrentItem())) {
				event.setCancelled(true);
            }
    	} else if(event.getCurrentItem() != null && event.getCursor().getType() == Material.PLAYER_HEAD && event.getSlot() == 39) {
			if (isIronChestItem(event.getCursor())) {
				event.setCancelled(true);
            }
    	}
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
		if (event.getOldCursor().getType() == Material.PLAYER_HEAD) {
			ItemStack dragged = event.getOldCursor();
			if (isIronChestItem(dragged)) {
				event.setCancelled(true);
			}
		}
    }
    
}



