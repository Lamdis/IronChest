package fr.lamdis.ironchest.inventory.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.lamdis.ironchest.IronChest;

public class IronChestManager {
	
	private static Set<Location> ironChests = new HashSet<>();
    private static File file = new File(IronChest.plugin.getDataFolder(), "ironchests.yml");;
    private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    
    public static void addIronChest(Location loc) {
        ironChests.add(loc);
        InventoryStorageManager.setMaxPages(loc, 2);
    }
    
    public static boolean isIronChest(Location loc) {
        // Comparaison basée sur la position bloc (entier)
        for (Location chestLoc : ironChests) {
            if (chestLoc.getWorld().equals(loc.getWorld())
                && chestLoc.getBlockX() == loc.getBlockX()
                && chestLoc.getBlockY() == loc.getBlockY()
                && chestLoc.getBlockZ() == loc.getBlockZ()) {
                return true;
            }
        }
        return false;
    }
    
    public static void removeIronChest(Location loc) {
        ironChests.removeIf(chestLoc -> chestLoc.getWorld().equals(loc.getWorld())
            && chestLoc.getBlockX() == loc.getBlockX()
            && chestLoc.getBlockY() == loc.getBlockY()
            && chestLoc.getBlockZ() == loc.getBlockZ());
    }
    
    public static void loadData() {
        ironChests.clear();
        List<String> chestList = config.getStringList("chests");
        for (String s : chestList) {
            String[] parts = s.split(",");
            if (parts.length == 4) {
                World world = Bukkit.getWorld(parts[0]);
                if (world == null) continue;
                try {
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    Location loc = new Location(world, x, y, z);
                    ironChests.add(loc);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void saveData() {
        List<String> chestList = new ArrayList<>();
        for (Location loc : ironChests) {
            String s = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
            chestList.add(s);
        }
        config.set("chests", chestList);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
