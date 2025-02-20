package fr.lamdis.ironchest.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import fr.lamdis.ironchest.inventory.manager.ActiveChestManager;
import fr.lamdis.ironchest.inventory.manager.InventoryStorageManager;
import fr.lamdis.ironchest.inventory.manager.IronChestManager;
import fr.lamdis.ironchest.items.DiamondChestItem;
import fr.lamdis.ironchest.items.IronChestItem;

public class BreakListerner implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        
        // Vérifier si c'est un Iron Chest
        if (!isIronChest(block)) return;
        
        // Empêcher la chute automatique des items
        event.setDropItems(false);

        // Détruire le coffre et gérer les loots
        destroyIronChest(block);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Iterator<Block> iterator = event.blockList().iterator();
        
        while (iterator.hasNext()) {
            Block block = iterator.next();
            
            if (isIronChest(block)) {
                destroyIronChest(block);
                iterator.remove(); // Empêche le drop naturel du bloc
            }
        }
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        Block block = event.getToBlock();
        
        if (isIronChest(block)) {
        	event.setCancelled(true);
        }
    }

    /**
     * Vérifie si un bloc est un Iron Chest.
     * @param block Le bloc à vérifier.
     * @return true si c'est un Iron Chest, sinon false.
     */
    private boolean isIronChest(Block block) {
    	if(block.getType() == Material.PLAYER_HEAD || block.getType() == Material.PLAYER_HEAD) {
    		if(IronChestManager.isIronChest(block.getLocation())) {
    	        return true;
    		}
    	}
        return false;
    }

    /**
     * Détruit un Iron Chest, drop les items et nettoie les données.
     * @param block Le bloc correspondant au coffre.
     */
    private void destroyIronChest(Block block) {
        int maxPages = InventoryStorageManager.getMaxPages(block.getLocation());

        List<ItemStack> droppedItems = new ArrayList<>();
        
        // Récupérer et vider toutes les pages du coffre
        for (int i = 1; i <= maxPages; i++) {
            Inventory inv = ActiveChestManager.getActiveChest(block.getLocation(), i);
            
            if (inv != null) {
                // Fermer l'inventaire pour tous les joueurs
                for (HumanEntity viewer : new ArrayList<>(inv.getViewers())) {
                    viewer.closeInventory();
                }

                // Ajouter les items au loot
                for (int slot = 0; slot < inv.getSize(); slot++) {
                    ItemStack item = inv.getItem(slot);
                    if (item != null && slot <= 44) {
                        droppedItems.add(item);
                    }
                }
            }
        }

        // Drop tous les items récupérés
        for (ItemStack item : droppedItems) {
            block.getWorld().dropItemNaturally(block.getLocation(), item);
        }

        // Drop l'Iron Chest lui-même
        if(maxPages == 2)  {
            IronChestItem ironChestItem = new IronChestItem(1);
            block.getWorld().dropItemNaturally(block.getLocation(), ironChestItem.getItemStack());
        } else if(maxPages == 3) {
			DiamondChestItem diamondChestItem = new DiamondChestItem(1);
			block.getWorld().dropItemNaturally(block.getLocation(), diamondChestItem.getItemStack());
        }

        // Supprimer les références à ce coffre
        for (int i = 1; i <= maxPages; i++) {
            ActiveChestManager.removeIfEmpty(block.getLocation(), i);
        }
        IronChestManager.removeIronChest(block.getLocation());
        InventoryStorageManager.clearChest(block.getLocation());

        // Détruire le bloc
        block.setType(Material.AIR);
    }
}
