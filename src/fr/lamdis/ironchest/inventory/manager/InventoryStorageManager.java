package fr.lamdis.ironchest.inventory.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.IronChest;
import fr.lamdis.ironchest.items.NewItem;

public class InventoryStorageManager {
    private static File file = new File(IronChest.plugin.getDataFolder(), "chest_storage.yml");
    private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    private static String getKey(Location loc) {
        return loc.getWorld().getName() + "_" + loc.getBlockX() + "_" + loc.getBlockY() + "_" + loc.getBlockZ();
    }
	
	public static int getMaxPages(Location loc) {
		String key = getKey(loc);
		return config.getInt(key + ".max-pages");
	}
	
	public static void setMaxPages(Location loc, int pages) {
		String key = getKey(loc);
		config.set(key + ".max-pages", pages);
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Erreur lors de la sauvegarde du chest storage", e);
        }
	}

    public static ItemStack[] loadChest(Location loc, int page) {
        String key = getKey(loc);
        // On récupère la liste sauvegardée
        List<?> itemsList = config.getList(key + ".contents-" + page);
        // Initialise un tableau de 54 emplacements par défaut
        ItemStack[] items = new ItemStack[54];
        if (itemsList != null) {
            int i = 0;
            for (Object obj : itemsList) {
                if (i >= items.length) break;
				if (i >= 45 && i <= 53) {
					if(i == 45 || i == 53) {
						items[i] = NewItem.simpleItem(Material.BLACK_STAINED_GLASS_PANE, " ");
					} else if (i >= 46 && i <= 48) {
						if(page <= 1) {
							items[i] = NewItem.simpleItem(Material.RED_STAINED_GLASS_PANE, " ");
						} else {
							items[i] = NewItem.simpleItem(Material.LIME_STAINED_GLASS_PANE, "§aPage précédente");
						}
					} else if (i >= 50 && i <= 52) {
						if (getMaxPages(loc) == page) {
							items[i] = NewItem.simpleItem(Material.RED_STAINED_GLASS_PANE, " ");
						} else {
							items[i] = NewItem.simpleItem(Material.LIME_STAINED_GLASS_PANE, "§aPage suivante");
						}
						
					} else if (i == 49) {
						items[i] = NewItem.cmdItem(Material.BLACK_STAINED_GLASS_PANE, "Page " + page, page);
					}
				} else {
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
				}
                i++;
            }
        }
        return items;
    }

    public static void saveChest(Location loc, ItemStack[] items, int page) {
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
        config.set(key + ".contents-" + page, list);
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Erreur lors de la sauvegarde du chest storage", e);
        }
    }
    
    public static void clearChest(Location loc) {
        String key = getKey(loc);
		for (int i = 0; i < getMaxPages(loc); i++) {
			config.set(key + ".contents-" + i, null);
		}
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Erreur lors de la suppression du chest storage pour le coffre " + key, e);
        }
    }
}
