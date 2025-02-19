package fr.lamdis.ironchest.items;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import fr.lamdis.ironchest.IronChest;

public class IronChestItem {
	
	private final ItemStack itemStack;
	private final IronChest plugin;

    public IronChestItem(IronChest plugin, int quantity) {
        this.plugin = plugin;
        this.itemStack = get(quantity);
    }
	
	private ItemStack get(int amount) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        if (skullMeta != null) {
            // Définir le nom custom
            skullMeta.setDisplayName(ChatColor.GOLD + "Iron Chest");

            // On ajoute une métadonnée custom pour identifier l'item
            NamespacedKey key = new NamespacedKey(plugin, "iron_chest");
            // Ici on stocke par exemple un byte indiquant que c'est un Iron Chest
            skullMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            

            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "ironchest");

            // Obtenez les textures du profil
            PlayerTextures textures = profile.getTextures();

            try {
                // Définissez la nouvelle URL de la peau
                @SuppressWarnings("deprecation")
				URL skinUrl = new URL("http://textures.minecraft.net/texture/f7aadff9ddc546fdcec6ed5919cc39dfa8d0c07ff4bc613a19f2e6d7f2593");
                textures.setSkin(skinUrl);

                // Appliquez les modifications au profil du joueur
                profile.setTextures(textures);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            
            skullMeta.setOwnerProfile(profile);
            
            // Vous pouvez aussi ajouter la texture custom via une méthode basée sur GameProfile

            item.setItemMeta(skullMeta);
        }
		return item;
	}
	
	public ItemStack getItemStack() {
        return this.itemStack;
    }
	
}
