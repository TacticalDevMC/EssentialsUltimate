package essentialsapi.utils.menu;


import essentialsapi.utils.menu.interfaces.Menu;
import essentialsapi.utils.menu.interfaces.MenuClick;
import essentialsapi.utils.menu.interfaces.MenuUpdate;
import essentialsapi.utils.menu.item.InventoryCheck;
import nl.tacticaldev.essentials.Essentials;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTMenu extends MenuHolder implements Menu {

    private final Set<String> ignoredClose = new HashSet<String>();
    private final Map<ItemStack, MenuClick> clickableItems = new ConcurrentHashMap<ItemStack, MenuClick>();

    private Consumer<InventoryClickEvent> clickHandler;
    private Consumer<InventoryCloseEvent> closeHandler;

    private MenuUpdate updateHandler;

    public final int[] slots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public AbstractTMenu(String name, int lines) {

        super(name, lines);
    }

    public AbstractTMenu(String name, InventoryType inventoryType) {

        super(name, inventoryType);
    }

    @Override
    public void openMenu(Player player) {

        openMenu(player, false);
    }

    @Override
    public void openMenu(final Player player, boolean ignoreCloseHandler) {

        update();

        if (ignoreCloseHandler) {
            getIgnoredClose().add(player.getName());
        }
        InventoryCheck.saveInventory(player, getInventory());
        new BukkitRunnable() {

            @Override
            public void run() {
                player.openInventory(getInventory());
            }
        }.runTaskLater(Essentials.getInstance(), 1);
    }

    @Override
    public int firstEmpty(int[] slots) {

        if (getInventory() == null) {
            return -1;
        }

        for (int s : slots) {
            if (getInventory().getItem(s) == null || getInventory().getItem(s).getType() == Material.AIR) {
                return s;
            }
        }

        return -1;
    }

    @Override
    public void setAutoUpdate() {

        MenuTask.getAutoUpdateInventories().add((Menu) this);
    }

    public Set<String> getIgnoredClose() {
        return ignoredClose;
    }

    public Map<ItemStack, MenuClick> getClickableItems() {
        return clickableItems;
    }

    public Consumer<InventoryClickEvent> getClickHandler() {
        return clickHandler;
    }

    public void setClickHandler(Consumer<InventoryClickEvent> clickHandler) {
        this.clickHandler = clickHandler;
    }

    public Consumer<InventoryCloseEvent> getCloseHandler() {
        return closeHandler;
    }

    public void setCloseHandler(Consumer<InventoryCloseEvent> closeHandler) {
        this.closeHandler = closeHandler;
    }

    public MenuUpdate getUpdateHandler() {
        return updateHandler;
    }

    public void setUpdateHandler(MenuUpdate updateHandler) {
        this.updateHandler = updateHandler;
    }
}