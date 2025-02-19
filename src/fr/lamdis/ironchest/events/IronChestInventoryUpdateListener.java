package fr.lamdis.ironchest.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import fr.lamdis.ironchest.holder.IronChestHolder;
import fr.lamdis.ironchest.manager.InventoryStorageManager;

public class IronChestInventoryUpdateListener implements Listener {
    private final InventoryStorageManager storageManager;
    
    public IronChestInventoryUpdateListener(InventoryStorageManager storageManager) {
        this.storageManager = storageManager;
    }
    
    // Sauvegarde l'inventaire à chaque clic pour synchroniser l'état entre les joueurs
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof IronChestHolder) {
            IronChestHolder holder = (IronChestHolder) event.getInventory().getHolder();
            storageManager.saveChest(holder.getChestLocation(), event.getInventory().getContents());
        }
    }
    
    // Sauvegarde également lors d'un drag sur l'inventaire
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof IronChestHolder) {
            IronChestHolder holder = (IronChestHolder) event.getInventory().getHolder();
            storageManager.saveChest(holder.getChestLocation(), event.getInventory().getContents());
        }
    }
    
    @EventHandler
    public void onInventoryClick2(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof IronChestHolder) {
        	if(event.getSlot() >= 45 && event.getSlot() <= 53) {
        		event.setCancelled(true);
            	if(event.getSlot() >= 46 && event.getSlot() <= 48) {
            		
            	} else if(event.getSlot() >= 50 && event.getSlot() <= 52) {
            		
            	}
        	}
        }
    }
}
