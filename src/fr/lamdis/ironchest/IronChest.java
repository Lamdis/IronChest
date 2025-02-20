package fr.lamdis.ironchest;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.lamdis.ironchest.commands.IronChestCommand;
import fr.lamdis.ironchest.events.BreakListerner;
import fr.lamdis.ironchest.events.InventoryListerner;
import fr.lamdis.ironchest.events.IronChestListerner;
import fr.lamdis.ironchest.inventory.manager.IronChestManager;
import fr.lamdis.ironchest.recipes.IronChestRecipes;

public class IronChest extends JavaPlugin {
	
	public static IronChest plugin;
	
	public static FileConfiguration CONFIG;
	
	public static FileConfiguration CONFIG_CRAFT;
	
	public static Server SERVER;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		SERVER = plugin.getServer();
		
		File craftFile = new File(getDataFolder(), "crafts.yml");
		
		if(!craftFile.exists()) { saveResource("crafts.yml", true); }
		
		CONFIG = plugin.getConfig();
		CONFIG_CRAFT = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "crafts.yml"));
		
        if (!IronChest.plugin.getDataFolder().exists()) {
        	IronChest.plugin.getDataFolder().mkdirs();
        }
		IronChestManager.loadData();
        
        getCommand("ironchest").setExecutor(new IronChestCommand());
		
		getServer().getPluginManager().registerEvents(new InventoryListerner(), this);
		getServer().getPluginManager().registerEvents(new IronChestListerner(), this);
		getServer().getPluginManager().registerEvents(new BreakListerner(), this);
		
		IronChestRecipes.registerRecipes();
		
		getLogger().info("Plugin enabled");
	}

	@Override
	public void onDisable() {
		IronChestManager.saveData();
		getLogger().info("Plugin disabled");
	}
	
	
}
