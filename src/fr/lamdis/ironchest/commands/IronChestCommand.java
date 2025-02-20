package fr.lamdis.ironchest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.items.DiamondChestItem;
import fr.lamdis.ironchest.items.IronChestItem;

public class IronChestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        // Vérifier que l'émetteur est un joueur
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seul un joueur peut utiliser cette commande.");
            return true;
        }
        Player player = (Player) sender;
        
		if (args[0].equalsIgnoreCase("diamond")) {
	        // Création de l'item Iron Chest ( tête de joueur )
	        DiamondChestItem ironChestItem = new DiamondChestItem(1);
	        ItemStack ironChest = ironChestItem.getItemStack();
	        
	        // Donner l'item au joueur
	        player.getInventory().addItem(ironChest);
	        player.sendMessage(ChatColor.GREEN + "Vous avez reçu un Diamand Chest !");
		} else {
	        // Création de l'item Iron Chest ( tête de joueur )
	        IronChestItem ironChestItem = new IronChestItem(1);
	        ItemStack ironChest = ironChestItem.getItemStack();
	        
	        // Donner l'item au joueur
	        player.getInventory().addItem(ironChest);
	        player.sendMessage(ChatColor.GREEN + "Vous avez reçu un Iron Chest !");
		}
        return true;
	}

}
