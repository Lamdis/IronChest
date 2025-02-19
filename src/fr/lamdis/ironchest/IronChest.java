package fr.lamdis.ironchest;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import fr.lamdis.ironchest.commands.IronChestCommand;
import fr.lamdis.ironchest.events.IronChestBreakListener;
import fr.lamdis.ironchest.events.IronChestInventoryUpdateListener;
import fr.lamdis.ironchest.events.IronChestListerner;
import fr.lamdis.ironchest.manager.InventoryStorageManager;
import fr.lamdis.ironchest.manager.IronChestManager;

public class IronChest extends JavaPlugin {
	
	private IronChestManager manager;
    private InventoryStorageManager storageManager;
    
    //public PlayerProfile profile = getServer().createPlayerProfile(UUID.randomUUID(), "IronChest");
	
	@Override
	public void onEnable() {
		manager = new IronChestManager(this);
        storageManager = new InventoryStorageManager(this);
        //profile.setTextures(new IronChestTexture());
        
        getCommand("ironchest").setExecutor(new IronChestCommand(this));
		getServer().getPluginManager().registerEvents(new IronChestListerner(this, manager, storageManager), this);
		getServer().getPluginManager().registerEvents(new IronChestInventoryUpdateListener(storageManager), this);
		getServer().getPluginManager().registerEvents(new IronChestBreakListener(this, manager, storageManager), this);
		//getServer().getPluginManager().registerEvents(new IronChestHelmetListener(), this);			NON FONCTIONNELLE
		
		System.out.println("Iron Chest is a successful start !");
	}
	
	@Override
	public void onDisable() {
		manager.saveData();
		System.out.println("Iron Chest is to stop !");
	}

	public IronChestManager getManager() {
        return manager;
    }
	
	public InventoryStorageManager getStorageManager() {
        return storageManager;
    }
	
	public boolean isIronChest(ItemStack item) {
		if (item == null || item.getType() != Material.PLAYER_HEAD) {
			return false;
		}
		if (!item.hasItemMeta()) {
			return false;
		}
		if (item.getItemMeta() instanceof SkullMeta) {
			SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            NamespacedKey key = new NamespacedKey(this, "iron_chest");
			return skullMeta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
		}
		return false;
	}
	
}
