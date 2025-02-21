package fr.lamdis.ironchest.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandTabCompleter implements TabCompleter {

	@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        
        suggestions.addAll(Arrays.asList("give"));

        if (args.length == 1) {
            suggestions.addAll(Arrays.asList("give", "reload"));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                suggestions.clear();
                suggestions.addAll(Arrays.asList("ironchest", "diamondchest"));
            }
		} else {
			suggestions.clear();
		}
        return suggestions;
    }

}
