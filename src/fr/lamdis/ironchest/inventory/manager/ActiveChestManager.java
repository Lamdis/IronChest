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
    private static final Map<String, Inventory> activeChests = new HashMap<>();

    /**
     * Récupère l'inventaire actif d'un Iron Chest à une page spécifique.
     * @param loc La location du coffre.
     * @param page La page demandée.
     * @return L'inventaire de la page du coffre.
     */
    public static Inventory getActiveChest(Location loc, int page) {
        String key = getChestKey(loc, page);

        // Vérifie si l'inventaire de cette page est déjà chargé
        if (activeChests.containsKey(key)) {
            return activeChests.get(key);
        }

        // Création d'un nouvel inventaire pour la page demandée
        String title = "Chest (Page " + page + ")";
		if (InventoryStorageManager.getMaxPages(loc) == 3) {
			title = "Diamond " + title;
        } else if(InventoryStorageManager.getMaxPages(loc) == 2) {
			title = "Iron " + title;
        }
        Inventory inv = Bukkit.createInventory(new IronChestHolder(loc), 54, ChatColor.DARK_GRAY + title);
        ItemStack[] storedItems = InventoryStorageManager.loadChest(loc, page);

        if (storedItems != null && storedItems.length > 0) {
            inv.setContents(storedItems);
        }

        // Ajout du nouvel inventaire à la map avec une clé unique
        activeChests.put(key, inv);
        return inv;
    }

    /**
     * Supprime un coffre actif de la mémoire si personne ne l'utilise.
     * @param loc La location du coffre.
     * @param page La page à vérifier.
     */
    public static void removeIfEmpty(Location loc, int page) {
        String key = getChestKey(loc, page);

        if (activeChests.containsKey(key)) {
            Inventory inv = activeChests.get(key);
            
            // Vérifie si l'inventaire n'a plus de spectateurs
            if (inv.getViewers().isEmpty()) {
                activeChests.remove(key);
            }
        }
    }

    /**
     * Génère une clé unique pour chaque page d'un coffre.
     * @param loc La position du coffre.
     * @param page La page actuelle.
     * @return Une clé unique sous forme de String.
     */
    private static String getChestKey(Location loc, int page) {
        return loc.getWorld().getName() + ":" + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + ":" + page;
    }
}