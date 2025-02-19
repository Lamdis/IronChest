package fr.lamdis.ironchest.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.lamdis.ironchest.IronChest;
import fr.lamdis.ironchest.items.IronChestItem;

public class IronChestCommand implements CommandExecutor {

	private final IronChest plugin;

    public IronChestCommand(IronChest plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Vérifier que l'émetteur est un joueur
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seul un joueur peut utiliser cette commande.");
            return true;
        }
        Player player = (Player) sender;
        
        // Création de l'item Iron Chest ( tête de joueur )
        IronChestItem ironChestItem = new IronChestItem(plugin, 1);
        ItemStack ironChest = ironChestItem.getItemStack();
        
        // Donner l'item au joueur
        player.getInventory().addItem(ironChest);
        player.sendMessage(ChatColor.GREEN + "Vous avez reçu un Iron Chest !");
        return true;
    }

}
