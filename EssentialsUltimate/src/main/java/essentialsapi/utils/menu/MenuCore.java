package essentialsapi.utils.menu;
// Plugin created by TacticalDevelopment
// Project Name: EssentialsUltimate
// Class created on: 22:01 (20/11/2019)
// Time: 22:01


import essentialsapi.utils.builders.ItemBuilder;
import essentialsapi.utils.menu.interfaces.MenuClick;
import essentialsapi.utils.menu.interfaces.MenuUpdate;
import essentialsapi.utils.menu.item.InventoryCheck;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class MenuCore extends AbstractTMenu {

    private final int[] corners = new int[]{0, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53};

    public MenuCore(String name, int lines) {
        super(name, lines);
    }

    public MenuCore(String name, InventoryType inventoryType) {
        super(name, inventoryType);
    }

    public void clear() {
        getInventory().clear();
        getClickableItems().clear();
    }

    @Override
    public void update() {
        MenuUpdate menuUpdate = getUpdateHandler();

        if (menuUpdate != null) {
            clear();
            menuUpdate.onUpdate();
            for (HumanEntity e : getInventory().getViewers()) {
                if (e instanceof Player) InventoryCheck.saveInventory((Player) e, getInventory());
            }
        }
    }

    public void addItem(List<ItemStack> items) {
        items.forEach(new Consumer<ItemStack>() {
            @Override
            public void accept(ItemStack i) {
                MenuCore.this.getInventory().addItem(i);
            }
        });
    }

    @Override
    public void setItem(ItemStack stack, int slot) {
        addMenuClick(stack, null, slot);
    }

    @Override
    public void addMenuClick(ItemStack stack, MenuClick click, int slot) {
        if (click != null) {
            getClickableItems().put(stack, click);
            getInventory().setItem(slot, stack);
        }
    }

    public ItemStack getItem(int slot) {
        return getInventory().getItem(slot);
    }

    public void placePlanes(MenuCore menu) {
        int done = 0;

        boolean light = false;

        for (int c : corners) {
            light = !light;
            if (done >= 2) {
                light = !light;
                done = 0;
            }

            if (c < menu.getInventory().getSize()) {
                menu.addMenuClick(new ItemBuilder(Material.LEGACY_STAINED_GLASS_PANE, 1, (byte) (light ? 4 : 1)).setName(" ").toItemStack(), null, c);
                done++;
            }
        }
    }

}
