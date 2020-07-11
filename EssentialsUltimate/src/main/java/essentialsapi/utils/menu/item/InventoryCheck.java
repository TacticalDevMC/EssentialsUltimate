package essentialsapi.utils.menu.item;

import essentialsapi.utils.essentialsutils.VersionUtil;
import nl.tacticaldev.essentials.Essentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.event.EventPriority.HIGHEST;

public class InventoryCheck implements Listener {

    private static final ArrayList<UUID> insideInventory = new ArrayList<UUID>();

    public static Inventory saveInventory(Player player, Inventory inventory) {

        insideInventory.add(player.getUniqueId());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null) {
                ItemStack clone = ItemTagHandler.addString(inventory.getItem(i), "secured", "secured");
                inventory.setItem(i, clone);
            }
        }
        return inventory;
    }

    @EventHandler(priority = HIGHEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        if (e.getPlayer() instanceof Player) {
            final Player p = ((Player) e.getPlayer());
            if (insideInventory.contains(p.getUniqueId())) {
                insideInventory.remove(p.getUniqueId());

                new BukkitRunnable() {

                    @SuppressWarnings("deprecation")
                    @Override
                    public void run() {

                        for (int i = 0; i < p.getInventory().getSize(); i++) {
                            if (p.getInventory().getItem(i) != null) {
                                ItemStack itemStack = p.getInventory().getItem(i);
                                if (itemStack == null) continue;
                                if (ItemTagHandler.hasString(itemStack, "secured")) {
                                    p.getInventory().removeItem(itemStack);
                                }
                            }
                        }
                        p.updateInventory();
                    }
                }.runTaskLater(Essentials.getInstance(), 15);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {

        if (ItemTagHandler.hasString(e.getItemDrop().getItemStack(), "secured")) e.getItemDrop().remove();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!insideInventory.contains(e.getWhoClicked().getUniqueId()) && e.getCurrentItem() != null && ItemTagHandler.hasString(e.getCurrentItem(), "secured")) {
            e.getInventory().remove(e.getCurrentItem());
        }
    }

}