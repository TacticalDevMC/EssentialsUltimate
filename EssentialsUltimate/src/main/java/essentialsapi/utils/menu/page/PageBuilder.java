package essentialsapi.utils.menu.page;

import essentialsapi.utils.builders.ItemBuilder;
import essentialsapi.utils.menu.MenuCore;
import essentialsapi.utils.menu.interfaces.MenuClick;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public abstract class PageBuilder {

    public boolean usingMenu = false;

    private MenuCore menu;
    private int maxPages = 0;
    private int[] middleSlots = new int[]{0, 0, 0, 13, 13, 22, 22, 22};

    private final List<Integer> placedSlots = Arrays
            .asList((9 + 1), (9 + 2), (9 + 3), (9 + 4), (9 + 5), (9 + 6), (9 + 7), (9 * 2 + 1), (9 * 2 + 2), (9 * 2 + 3), (9 * 2 + 4), (9 * 2 + 5), (9 * 2 + 6), (9 * 2 + 7), (9 * 3 + 1), (9 * 3 + 2), (9 * 3 + 3), (9 * 3 + 4), (9 * 3 + 5), (9 * 3 + 6), (9 * 3 + 7), (9 * 4 + 1), (9 * 4 + 2), (9 * 4 + 3), (9 * 4 + 4), (9 * 4 + 5), (9 * 4 + 6),
                    (9 * 4 + 7));

    public PageBuilder(boolean usingMenu) {

        this.usingMenu = usingMenu;
    }

    public abstract String getMenuName();

    public abstract List<PageItem> getItems();

    public abstract int getMenuSize();

    public abstract ItemStack getEmptyItem();

    public abstract PageItem getBackItem();

    public PageBuilder apply() {

        this.maxPages = (int) Math.ceil(getItems().size() / 29.0);
        if (maxPages <= 0) {
            maxPages = 1;
        }
        return this;
    }

    public void openMenu(final Player p, final int page) {

        this.menu = new MenuCore(ChatColor.translateAlternateColorCodes('&', getMenuName() + " " + (maxPages > 1 ? "(Page " + (page + 1) + "/" + maxPages + ")" : "")), getMenuSize());

        if (page != 0) {
            menu.addMenuClick(new ItemBuilder(Material.ARROW).setName("&a◀ &a&lPrevious Page").toItemStack(), new MenuClick() {

                @Override
                public void onItemClick(Player player, ClickType type) {

                    openMenu(player, (page - 1));
                }
            }, 46);
        }

        if ((page + 1) != maxPages) {
            menu.addMenuClick(new ItemBuilder(Material.ARROW).setName("&a&lNext Page &a▶").toItemStack(), new MenuClick() {

                @Override
                public void onItemClick(Player player, ClickType type) {

                    openMenu(player, (page + 1));
                }
            }, 52);
        }

        if (getBackItem() != null)
            menu.addMenuClick(getBackItem().getItemStack(), getBackItem().getMenuClick(), (menu.getInventory().getSize() - 5));

        if (getItems().isEmpty()) {
            menu.addMenuClick(getEmptyItem(), new MenuClick() {
                @Override
                public void onItemClick(Player player, ClickType type) {

                }
            }, middleSlots[getMenuSize()]);
        } else {
            int totalItems = 29;
            for (int i = page * totalItems; i < (page * totalItems) + (totalItems); i++) {
                if (i >= getItems().size()) continue;
                PageItem item = getItems().get(i);
                if (item != null) {
                    if (firstEmpty() != -1) {
                        menu.addMenuClick(item.getItemStack(), item.getMenuClick(), firstEmpty());
                    }
                }
            }
        }
        menu.openMenu(p);
    }

    private int firstEmpty() {

        for (int i : placedSlots) {
            if (menu.getInventory().getItem(i) == null) {
                return i;
            }
        }
        return -1;
    }

    public static class PageItem {

        protected ItemStack itemStack;
        protected MenuClick menuClick;

        public ItemStack getItemStack() {
            return itemStack;
        }

        public MenuClick getMenuClick() {
            return menuClick;
        }
    }

    public boolean isUsingMenu() {
        if (usingMenu) {
            return true;
        } else {
            return false;
        }
    }
}