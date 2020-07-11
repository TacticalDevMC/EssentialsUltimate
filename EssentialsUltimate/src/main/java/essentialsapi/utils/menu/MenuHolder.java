package essentialsapi.utils.menu;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {

	private transient Inventory inventory;
	private String invName;
	private int lines;
	private InventoryType inventoryType;

	public MenuHolder(String name) {

		this.invName = name;
		if (this.inventoryType == null) {
			this.inventory = Bukkit.createInventory(this, lines * 9, StringUtils.abbreviate(invName, 32));
		} else {
			this.inventory = Bukkit.createInventory(this, inventoryType, StringUtils.abbreviate(invName, 32));
		}
	}

	public MenuHolder(String name, int lines) {

		this.invName = name;
		this.lines = lines;
		if (this.inventoryType == null) {
			this.inventory = Bukkit.createInventory(this, lines * 9, StringUtils.abbreviate(invName, 32));
		} else {
			this.inventory = Bukkit.createInventory(this, inventoryType, StringUtils.abbreviate(invName, 32));
		}
	}

	public MenuHolder(String name, InventoryType inventoryType) {

		this.invName = name;
		this.inventoryType = inventoryType;
		if (this.inventoryType == null) {
			this.inventory = Bukkit.createInventory(this, lines * 9, StringUtils.abbreviate(invName, 32));
		} else {
			this.inventory = Bukkit.createInventory(this, inventoryType, StringUtils.abbreviate(invName, 32));
		}
	}

	@Override
	public Inventory getInventory() {

		return this.inventory;
	}

	public String getInvName() {
		return invName;
	}

	public void setInvName(String invName) {
		this.invName = invName;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public InventoryType getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(InventoryType inventoryType) {
		this.inventoryType = inventoryType;
	}
}