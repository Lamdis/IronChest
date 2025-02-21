package fr.lamdis.ironchest.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.IronChest;
import fr.lamdis.ironchest.inventory.manager.IronChestManager;
import fr.lamdis.ironchest.items.DiamondChestItem;
import fr.lamdis.ironchest.items.IronChestItem;
import fr.lamdis.ironchest.recipes.IronChestRecipes;

public class IronChestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ironchest")){
	        if (sender instanceof Player) {
		        Player player = (Player) sender;
		        
				if (args[0].equalsIgnoreCase("give")) {
					if(args[1].equalsIgnoreCase("diamondchest")) {
				        DiamondChestItem diamondChestItem = new DiamondChestItem(1);
				        ItemStack diamondChest = diamondChestItem.getItemStack();
				        
				        player.getInventory().addItem(diamondChest);
					} else if (args[1].equalsIgnoreCase("ironchest")) {
						IronChestItem ironChestItem = new IronChestItem(1);
						ItemStack ironChest = ironChestItem.getItemStack();

						player.getInventory().addItem(ironChest);
					}
				} else if(args[0].equalsIgnoreCase("reload")) {
					IronChestManager.saveData();
					IronChestManager.loadData();
					IronChest.plugin.reloadConfig();
					IronChest.CONFIG = IronChest.plugin.getConfig();
					IronChest.CONFIG_CRAFT = YamlConfiguration.loadConfiguration(new File(IronChest.plugin.getDataFolder(), "crafts.yml"));
					IronChest.SERVER.resetRecipes();
					IronChestRecipes.registerRecipes();
					System.out.println("IronChest : Config reloaded !");
					if(sender instanceof Player) {
						sender.sendMessage("§aConfig reloaded !");
					}
				}
	        }
		}
        return false;
	}

}
