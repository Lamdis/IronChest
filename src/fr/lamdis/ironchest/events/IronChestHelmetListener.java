package fr.lamdis.ironchest.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class IronChestHelmetListener implements Listener {
	
    public static final String IRONCHEST_KEY = "iron_chest";

    // This method checks if the given item is an Iron Chest based on its PersistentDataContainer.
    private boolean isIronChestItem(ItemStack item) {
        if (item != null && item.getType() == Material.PLAYER_HEAD) {
            if (item.hasItemMeta() && item.getItemMeta() instanceof SkullMeta) {
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                if (meta.hasDisplayName() && meta.getDisplayName().contains("Iron Chest")) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if the clicked slot is an armor slot (helmet).
        // Additionally, prevent shift-click equipping from the main inventory into the armor slot.
    	if(event.getCurrentItem().getType() == Material.PLAYER_HEAD && event.isShiftClick()) {
			if (isIronChestItem(event.getCursor())) {
				event.setCancelled(true);
            }
    	} else if(event.getCursor().getType() == Material.PLAYER_HEAD && event.getSlot() == 39) {
			if (isIronChestItem(event.getCursor())) {
				event.setCancelled(true);
            }
    	}
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
    	if(event.getRawSlots().contains(39)) {
    		ItemStack dragged = event.getOldCursor();
            if (isIronChestItem(dragged)) {
                event.setCancelled(true);
                if (event.getWhoClicked() instanceof Player) {
                	event.getWhoClicked().sendMessage("§cVous ne pouvez pas mettre un Iron Chest sur votre tête !");
                }
            }
    	}
    }
}



