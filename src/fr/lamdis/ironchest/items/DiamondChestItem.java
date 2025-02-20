package fr.lamdis.ironchest.items;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import fr.lamdis.ironchest.IronChest;

public class DiamondChestItem {
	
	private final ItemStack itemStack;
	private final IronChest plugin;

    public DiamondChestItem(int quantity) {
        this.plugin = IronChest.plugin;
        this.itemStack = get(quantity);
    }
	
	private ItemStack get(int amount) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        if (skullMeta != null) {
            // Définir le nom custom
            skullMeta.setDisplayName("§rDiamond Chest");

            // On ajoute une métadonnée custom pour identifier l'item
            NamespacedKey key = new NamespacedKey(plugin, "diamond_chest");
            // Ici on stocke par exemple un byte indiquant que c'est un Iron Chest
            skullMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            

            UUID uuid = new UUID(0, 0);
            PlayerProfile profile = Bukkit.createPlayerProfile(uuid, "diamondchest");

            // Obtenez les textures du profil
            PlayerTextures textures = profile.getTextures();

            try {
                // Définissez la nouvelle URL de la peau
				URL skinUrl = new URL("http://textures.minecraft.net/texture/f7bf02390d3f3f4cce4bfedc3c190484138a3174d8541a8fd912ebb2147fcc0e");
                textures.setSkin(skinUrl);

                // Appliquez les modifications au profil du joueur
                profile.setTextures(textures);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            
            skullMeta.setOwnerProfile(profile);

            item.setItemMeta(skullMeta);
        }
		return item;
	}
	
	public ItemStack getItemStack() {
        return this.itemStack;
    }

}
