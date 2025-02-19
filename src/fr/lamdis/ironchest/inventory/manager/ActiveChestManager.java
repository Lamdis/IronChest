package fr.lamdis.ironchest.inventory.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.holder.IronChestHolder;

public class ActiveChestManager {
	private static final Map<Location, Map<Integer, Inventory>> activeChests = new HashMap<>();

    public static Inventory getActiveChest(Location loc, int page) {
        // Recherche d'un coffre actif pour la position (en comparant les positions de blocs)
        for (Location key : activeChests.keySet()) {
            if (areLocationsEqual(key, loc)) {
            	Map<Integer, Inventory> pages = activeChests.get(key);
                if (pages.containsKey(page)) {
                    return pages.get(page);
                }
            }
        }
        // Aucun coffre actif trouvé : création d'un inventaire live avec le contenu sauvegardé
        Inventory inv = Bukkit.createInventory(new IronChestHolder(loc), 54, ChatColor.DARK_GRAY + "Iron Chest");
        ItemStack[] storedItems = InventoryStorageManager.loadChest(loc, page);
        if (storedItems != null && storedItems.length > 0) {
            inv.setContents(storedItems);
        }
        activeChests.computeIfAbsent(loc, k -> new HashMap<>()).put(page, inv);
        return inv;
    }

    public static void removeIfEmpty(Location loc) {
        // Si plus aucun joueur ne consulte cet inventaire, on le supprime de la map
        for (Location key : activeChests.keySet()) {
            if (areLocationsEqual(key, loc)) {
            	Map<Integer, Inventory> pages = activeChests.get(key);
                pages.entrySet().removeIf(entry -> entry.getValue().getViewers().isEmpty());
                if (pages.isEmpty()) {
                    activeChests.remove(key);
                }
                break;
            }
        }
    }

    private static boolean areLocationsEqual(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) return false;
        return loc1.getWorld().equals(loc2.getWorld())
            && loc1.getBlockX() == loc2.getBlockX()
            && loc1.getBlockY() == loc2.getBlockY()
            && loc1.getBlockZ() == loc2.getBlockZ();
    }
}