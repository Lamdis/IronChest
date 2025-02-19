package fr.lamdis.ironchest.events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.IronChest;
import fr.lamdis.ironchest.inventory.manager.ActiveChestManager;
import fr.lamdis.ironchest.inventory.manager.InventoryStorageManager;
import fr.lamdis.ironchest.inventory.manager.IronChestManager;
import fr.lamdis.ironchest.items.IronChestItems;

public class IronChestBreakListener implements Listener {

    public IronChestBreakListener(IronChest plugin, IronChestManager manager, InventoryStorageManager storageManager) {
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        
        // Vérifier que le bloc est un PLAYER_HEAD et qu'il correspond à un Iron Chest enregistré.
        if (block.getType() != Material.PLAYER_HEAD) {
            return;
        }
        if (!IronChestManager.isIronChest(block.getLocation())) {
            return;
        }
        
        // Empêcher la chute automatique des items.
        event.setDropItems(false);
        
        
		for (int i = 1; i < InventoryStorageManager.getMaxPages(event.getBlock().getLocation()) + 1; i++) {
	        // Récupérer l'inventaire actif du Iron Chest afin d'obtenir le contenu en direct.
	        Inventory inv = ActiveChestManager.getActiveChest(block.getLocation(), i);
	        // Fermer l'inventaire pour tous les joueurs qui le consultent.
	        for (HumanEntity viewer : new ArrayList<>(inv.getViewers())) {
	            viewer.closeInventory();
	        }
	        // Faire tomber tous les items stockés dans le coffre.
	        for (ItemStack item : inv.getContents()) {
	            if (item != null) {
	                block.getWorld().dropItemNaturally(block.getLocation(), item);
	            }
	        }
		}
        
        // Créer l'item Iron Chest lui-même pour le drop.
        IronChestItems ironChestItem = new IronChestItems(1);
        ItemStack chestItem = ironChestItem.getItemStack();
        block.getWorld().dropItemNaturally(block.getLocation(), chestItem);
        
        // Supprimer le coffre actif et retirer son enregistrement.
        ActiveChestManager.removeIfEmpty(block.getLocation());
        IronChestManager.removeIronChest(block.getLocation());
        
        // Vider les données sauvegardées pour éviter la duplication lors d'une ré-implantation.
        InventoryStorageManager.clearChest(block.getLocation());
        
        // Remplacer le bloc cassé par de l'air.
        block.setType(Material.AIR);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        // Parcourir tous les blocs affectés par l'explosion.
        for (Block block : event.blockList()) {
            // Vérifier si c'est un Iron Chest.
            if (block.getType() == Material.PLAYER_HEAD && IronChestManager.isIronChest(block.getLocation())) {
        		for (int i = 1; i < InventoryStorageManager.getMaxPages(block.getLocation()) + 1; i++) {
                    // Récupérer l'inventaire "live" du Iron Chest.
                    Inventory inv = ActiveChestManager.getActiveChest(block.getLocation(), i);
                    
                    // Fermer l'inventaire pour tous les joueurs qui le consultent.
                    for (HumanEntity viewer : new ArrayList<>(inv.getViewers())) {
                        viewer.closeInventory();
                    }
                    
                    // Faire tomber tous les items stockés dans le coffre.
                    for (ItemStack item : inv.getContents()) {
                        if (item != null) {
                            block.getWorld().dropItemNaturally(block.getLocation(), item);
                        }
                    }
        		}
                
                // Créer l'item Iron Chest pour le drop.
                IronChestItems ironChestItem = new IronChestItems(1);
                ItemStack chestItem = ironChestItem.getItemStack();
                block.getWorld().dropItemNaturally(block.getLocation(), chestItem);
                
                // Supprimer le coffre actif et retirer son enregistrement.
                ActiveChestManager.removeIfEmpty(block.getLocation());
                IronChestManager.removeIronChest(block.getLocation());
                InventoryStorageManager.clearChest(block.getLocation());
                
                // Remplacer le bloc détruit par de l'air.
                block.setType(Material.AIR);
            }
        }
    }
    
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        Block block = event.getToBlock();
        // Check if the block being flowed into is a PLAYER_HEAD and is registered as an Iron Chest.
        if (block.getType() == Material.PLAYER_HEAD && IronChestManager.isIronChest(block.getLocation())) {
    		for (int i = 1; i < InventoryStorageManager.getMaxPages(block.getLocation()) + 1; i++) {
                // Récupérer l'inventaire "live" du Iron Chest.
                Inventory inv = ActiveChestManager.getActiveChest(block.getLocation(), i);
                
                // Fermer l'inventaire pour tous les joueurs qui le consultent.
                for (HumanEntity viewer : new ArrayList<>(inv.getViewers())) {
                    viewer.closeInventory();
                }
                
                // Faire tomber tous les items stockés dans le coffre.
                for (ItemStack item : inv.getContents()) {
                    if (item != null) {
                        block.getWorld().dropItemNaturally(block.getLocation(), item);
                    }
                }
    		}
            
            // Créer l'item Iron Chest pour le drop.
            IronChestItems ironChestItem = new IronChestItems(1);
            ItemStack chestItem = ironChestItem.getItemStack();
            block.getWorld().dropItemNaturally(block.getLocation(), chestItem);
            
            // Supprimer le coffre actif et retirer son enregistrement.
            ActiveChestManager.removeIfEmpty(block.getLocation());
            IronChestManager.removeIronChest(block.getLocation());
            InventoryStorageManager.clearChest(block.getLocation());
            
            // Remplacer le bloc détruit par de l'air.
            block.setType(Material.AIR);
        }
    }
}