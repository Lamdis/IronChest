package fr.lamdis.ironchest.pages;

import org.bukkit.inventory.Inventory;

public class Pages {
	
	public static int getActualPage(Inventory inv) {
		
	}
	
	public static int getStartSlot(int page) {
		return (page - 1) * 54;
	}
	
}
