package fr.lamdis.ironchest.holder;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class IronChestHolder implements InventoryHolder {
    private final Location chestLocation;

    public IronChestHolder(Location location) {
        this.chestLocation = location;
    }

    public Location getChestLocation() {
        return chestLocation;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
