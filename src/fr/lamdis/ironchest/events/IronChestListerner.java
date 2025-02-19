package fr.lamdis.ironchest.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import fr.lamdis.ironchest.IronChest;
import fr.lamdis.ironchest.holder.IronChestHolder;
import fr.lamdis.ironchest.manager.ActiveChestManager;
import fr.lamdis.ironchest.manager.InventoryStorageManager;
import fr.lamdis.ironchest.manager.IronChestManager;

public class IronChestListerner implements Listener {
	
	private final IronChest plugin;
    private final IronChestManager manager;
    private final InventoryStorageManager storageManager;

    public IronChestListerner(IronChest plugin, IronChestManager manager, InventoryStorageManager storageManager) {
        this.plugin = plugin;
        this.manager = manager;
        this.storageManager = storageManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        // Vérifier que le bloc placé est une tête de joueur
        if (block.getType() == Material.PLAYER_HEAD) {
            // Vérifier que l'item en main possède notre métadonnée définie dans l'ItemMeta
            if (event.getItemInHand().hasItemMeta() && event.getItemInHand().getItemMeta() instanceof SkullMeta) {
                SkullMeta meta = (SkullMeta) event.getItemInHand().getItemMeta();
                if (meta.hasDisplayName() && meta.getDisplayName().contains("Iron Chest")) {
                    // Enregistrer la position du bloc comme étant un Iron Chest
                    manager.addIronChest(block.getLocation());
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if(event.getAction() == Action.LEFT_CLICK_BLOCK) return; // AJOUT PAR MOI
    	if(event.getPlayer().isSneaking()) return; // AJOUT PAR MOI
        if (event.getClickedBlock() == null) return;
        Block block = event.getClickedBlock();
        if (block.getType() != Material.PLAYER_HEAD) return;
        Location loc = block.getLocation();
        if (manager.isIronChest(loc)) {
            // Récupérer l'inventaire actif pour la position
            Inventory inv = ActiveChestManager.getActiveChest(loc, storageManager);
            event.getPlayer().openInventory(inv);
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof IronChestHolder) {
        	IronChestHolder holder = (IronChestHolder) event.getInventory().getHolder();
            // Sauvegarder le contenu lors de la fermeture
            storageManager.saveChest(holder.getChestLocation(), event.getInventory().getContents());
            // Retirer le coffre actif s'il n'est plus consulté
            ActiveChestManager.removeIfEmpty(holder.getChestLocation());
        }
    }
}
