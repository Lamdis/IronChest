package fr.lamdis.ironchest.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import fr.lamdis.ironchest.holder.IronChestHolder;
import fr.lamdis.ironchest.inventory.Pages;
import fr.lamdis.ironchest.inventory.manager.InventoryStorageManager;

public class InventoryListerner implements Listener {
    
    // Sauvegarde l'inventaire à chaque clic pour synchroniser l'état entre les joueurs
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof IronChestHolder) {
            IronChestHolder holder = (IronChestHolder) event.getInventory().getHolder();
        	if(event.getSlot() >= 45 && event.getSlot() <= 53) {
        		event.setCancelled(true);
        		if(event.getWhoClicked() instanceof Player) {
        			Player player = (Player) event.getWhoClicked();
                	if(event.getSlot() >= 46 && event.getSlot() <= 48) {
                		Pages.backPage(player, event.getInventory());
                	} else if(event.getSlot() >= 50 && event.getSlot() <= 52) {
                		Pages.nextPage(player ,event.getInventory());
                	}
        		}
        	} else {
                InventoryStorageManager.saveChest(holder.getChestLocation(), event.getInventory().getContents(), Pages.getActualPage(event.getInventory()));
        	}
        }
    }
    
    // Sauvegarde également lors d'un drag sur l'inventaire
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof IronChestHolder) {
            IronChestHolder holder = (IronChestHolder) event.getInventory().getHolder();
            InventoryStorageManager.saveChest(holder.getChestLocation(), event.getInventory().getContents(), Pages.getActualPage(event.getInventory()));
        }
    }
	
}
