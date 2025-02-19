package fr.lamdis.ironchest;

import org.bukkit.plugin.java.JavaPlugin;

import fr.lamdis.ironchest.commands.IronChestCommand;
import fr.lamdis.ironchest.events.InventoryListerner;
import fr.lamdis.ironchest.events.IronChestListerner;
import fr.lamdis.ironchest.inventory.manager.IronChestManager;

public class IronChest extends JavaPlugin {
	
	public static IronChest plugin;

	@Override
	public void onEnable() {
		plugin = this;
		
        if (!IronChest.plugin.getDataFolder().exists()) {
        	IronChest.plugin.getDataFolder().mkdirs();
        }
		IronChestManager.loadData();
        
        getCommand("ironchest").setExecutor(new IronChestCommand());
		
		getServer().getPluginManager().registerEvents(new InventoryListerner(), this);
		getServer().getPluginManager().registerEvents(new IronChestListerner(), this);
		
		getLogger().info("Plugin enabled");
	}

	@Override
	public void onDisable() {
		IronChestManager.saveData();
		getLogger().info("Plugin disabled");
	}
	
	
}
