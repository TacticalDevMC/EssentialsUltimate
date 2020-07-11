package essentialsapi.utils.menu.interfaces;

import essentialsapi.utils.menu.interfaces.MenuClick;
import essentialsapi.utils.menu.interfaces.MenuUpdate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.Map;
import java.util.Set;

public interface Menu extends InventoryHolder {
    Inventory getInventory();

    Map<ItemStack, MenuClick> getClickableItems();

    void addMenuClick(ItemStack var1, MenuClick var2, int var3);

    void setItem(ItemStack var1, int var2);

    Set<String> getIgnoredClose();

    MenuUpdate getUpdateHandler();

    void setUpdateHandler(MenuUpdate menuUpdate);

    void update();

    void setAutoUpdate();

    void openMenu(Player player);

    void openMenu(Player player, boolean bool);

    int firstEmpty(int[] var1);

    Consumer<InventoryClickEvent> getClickHandler();

    void setClickHandler(Consumer<InventoryClickEvent> inventoryCloseEventConsumer);

    Consumer<InventoryCloseEvent> getCloseHandler();

    void setCloseHandler(Consumer<InventoryCloseEvent> inventoryCloseEventConsumer);
}