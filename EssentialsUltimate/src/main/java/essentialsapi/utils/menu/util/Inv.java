package essentialsapi.utils.menu.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Inv {

	public static int getPlayersItemAmount(Player player, ItemStack itemStack) {

		int amount = 0;

		for (int i = 0; i < player.getInventory().getSize(); i++) {
			ItemStack itemStack1 = player.getInventory().getItem(i);

			if (!compareItemStacks(itemStack1, itemStack, false)) {
				continue;
			}

			amount += itemStack1.getAmount();
		}
		return amount;
	}

	@SuppressWarnings("deprecation")
	public static void removeItemFromPlayer(Player player, ItemStack itemStack, int amount) {

		int left = amount;

		for (int i = 0; i < player.getInventory().getSize(); i++) {
			ItemStack itemStack1 = player.getInventory().getItem(i);

			if (!compareItemStacks(itemStack1, itemStack, false)) {
				continue;
			}

			if (left >= itemStack1.getAmount()) {
				player.getInventory().clear(i);
				left -= itemStack1.getAmount();
			} else if (left > 0) {
				itemStack1.setAmount(itemStack1.getAmount() - left);
				left = 0;
			} else {
				break;
			}
		}

		player.updateInventory();
	}

	public static boolean compareItemStacks(ItemStack item1, ItemStack item2) {

		return compareItemStacks(item1, item2, true);
	}

	public static boolean compareItemStacks(ItemStack item1, ItemStack item2, boolean compareAmount) {

		if (item1 == null || item2 == null) {
			return false;
		}

		if (item1.getType() != item2.getType()) {
			return false;
		}

		if (compareAmount) {
			if (item1.getAmount() != item2.getAmount()) {
				return false;
			}
		}

		if (item1.getType().equals(Material.POTION) && item2.getType().equals(Material.POTION) || item1.getDurability() == item2.getDurability()) {

			ItemMeta meta1 = item1.getItemMeta();
			ItemMeta meta2 = item2.getItemMeta();

			if (meta1 instanceof LeatherArmorMeta && meta2 instanceof LeatherArmorMeta && !((LeatherArmorMeta) meta1).getColor().equals(((LeatherArmorMeta) meta2).getColor())) {
				return false;
			}

			if (!meta1.hasDisplayName() || !meta2.hasDisplayName() || !meta1.getDisplayName().equals(meta2.getDisplayName())) {
				if (meta1.hasDisplayName() && meta2.hasDisplayName()) {
					return false;
				}
			}

			if (!meta1.hasLore() || !meta2.hasLore() || !meta1.getLore().equals(meta2.getLore())) {
				if (meta1.hasLore() && meta2.hasLore()) {
					return false;
				}
			}

			return item1.getEnchantments().equals(item2.getEnchantments());
		}

		return false;
	}
}
