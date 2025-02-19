package fr.lamdis.ironchest.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.IronChest;

public class InventoryStorageManager {
    private final File file;
    private final YamlConfiguration config;

    public InventoryStorageManager(IronChest plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        file = new File(plugin.getDataFolder(), "chest_storage.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    private String getKey(Location loc) {
        return loc.getWorld().getName() + "_" + loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();
    }

    public ItemStack[] loadChest(Location loc) {
        String key = getKey(loc);
        // On récupère la liste sauvegardée
        List<?> itemsList = config.getList(key + ".contents");
        // Initialise un tableau de 54 emplacements par défaut
        ItemStack[] items = new ItemStack[54];
        if (itemsList != null) {
            int i = 0;
            for (Object obj : itemsList) {
                if (i >= items.length) break;
                if (obj instanceof ItemStack) {
                    items[i] = (ItemStack) obj;
                } else if (obj instanceof Map) {
                    try {
                        // Tenter de désérialiser l'objet à partir de la Map
                        @SuppressWarnings("unchecked")
                        Map<String, Object> map = (Map<String, Object>) obj;
                        items[i] = ItemStack.deserialize(map);
                    } catch (Exception e) {
                        Bukkit.getLogger().log(Level.SEVERE, "Erreur lors de la désérialisation d'un item du coffre à la position " + key, e);
                        items[i] = null;
                    }
                } else {
                    // Si l'objet n'est pas du type attendu, on laisse null
                    items[i] = null;
                }
                i++;
            }
        }
        return items;
    }

    public void saveChest(Location loc, ItemStack[] items) {
    	String key = getKey(loc);
        List<Map<String, Object>> list = new ArrayList<>();
        // On parcourt les 54 slots et on ajoute la Map sérialisée ou null.
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item != null) {
                list.add(item.serialize());
            } else {
                list.add(null);
            }
        }
        config.set(key + ".contents", list);
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Erreur lors de la sauvegarde du chest storage", e);
        }
    }
    
    public void clearChest(Location loc) {
        String key = getKey(loc);
        config.set(key + ".contents", null);
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Erreur lors de la suppression du chest storage pour le coffre " + key, e);
        }
    }
}
