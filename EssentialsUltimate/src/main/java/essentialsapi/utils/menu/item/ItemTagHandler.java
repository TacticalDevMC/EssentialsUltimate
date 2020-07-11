package essentialsapi.utils.menu.item;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemTagHandler {

	private static NBTTagCompound getTag(ItemStack item) {

		net.minecraft.server.v1_12_R1.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag;
		if (itemNms.hasTag()) {
			tag = itemNms.getTag();
		} else {
			tag = new NBTTagCompound();
		}

		return tag;
	}

	private static ItemStack setTag(ItemStack item, NBTTagCompound tag) {

		net.minecraft.server.v1_12_R1.ItemStack itemNms = CraftItemStack.asNMSCopy(item);
		itemNms.setTag(tag);
		return CraftItemStack.asBukkitCopy(itemNms);
	}

	public static ItemStack addString(ItemStack item, String name, String value) {

		NBTTagCompound tag = getTag(item);
		tag.setString(name, value);
		return setTag(item, tag);
	}

	public static boolean hasString(ItemStack item, String name) {

		NBTTagCompound tag = getTag(item);
		return tag.hasKey(name);
	}

	public static String getString(ItemStack item, String name) {

		NBTTagCompound tag = getTag(item);
		return tag.getString(name);
	}

	public static ItemStack removeString(ItemStack itemStack, String name) {

		NBTTagCompound tag = getTag(itemStack);
		tag.remove(name);
		return setTag(itemStack, tag);
	}
}