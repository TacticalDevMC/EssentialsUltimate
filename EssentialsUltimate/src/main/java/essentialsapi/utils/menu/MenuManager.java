package essentialsapi.utils.menu;
// Plugin created by TacticalDevelopment
// Project Name: EssentialsUltimate
// Class created on: 22:07 (20/11/2019)
// Time: 22:07

import nl.tacticaldev.essentials.Essentials;
import essentialsapi.utils.menu.interfaces.Menu;
import essentialsapi.utils.menu.interfaces.MenuClick;
import essentialsapi.utils.menu.util.Inv;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.Map;

public class MenuManager implements Listener {

    @EventHandler
    public void onClickMenu(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player member = ((Player) e.getWhoClicked());
        if (member == null) return;

        if (!(e.getInventory().getHolder() instanceof Menu)) return;

        e.setCancelled(true);

        Menu menu = (Menu) e.getInventory().getHolder();

        if (e.getCurrentItem() != null) {
            for (Map.Entry<ItemStack, MenuClick> a : menu.getClickableItems().entrySet()) {
                if (a != null) {
                    if (a.getValue() != null) {
                        if (Inv.compareItemStacks(e.getCurrentItem(), a.getKey())) {
                            MenuClick click = a.getValue();
                            click.onItemClick(member, e.getClick());
                            return;
                        }
                    }
                }
            }
        }

        Consumer<InventoryClickEvent> consumer = menu.getClickHandler();
        if (consumer != null) {
            consumer.accept(e);
        }
    }

    @EventHandler
    public void onCloseMenu(final InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();

        if (!(e.getInventory().getHolder() instanceof Menu)) return;

        Menu menu = (Menu) e.getInventory().getHolder();

        final Consumer<InventoryCloseEvent> consumer = menu.getCloseHandler();
        if (consumer != null) {
            if (menu.getIgnoredClose().contains(p.getName())) {
                menu.getIgnoredClose().remove(p.getName());
            } else {
                Bukkit.getScheduler().runTaskLater(Essentials.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        consumer.accept(e);
                    }
                }, 1);
            }
        }

        MenuTask.getAutoUpdateInventories().remove(menu);
    }
}
