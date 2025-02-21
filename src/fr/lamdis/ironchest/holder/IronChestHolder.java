package fr.lamdis.ironchest.holder;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class IronChestHolder implements InventoryHolder {
    private final Location chestLocation;
    private int currentPage;

    public IronChestHolder(Location location) {
        this.chestLocation = location;
        this.currentPage = 1;
    }

    public Location getChestLocation() {
        return chestLocation;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
