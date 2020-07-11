package essentialsapi.utils.menu.confirm;

import essentialsapi.utils.builders.ItemBuilder;
import essentialsapi.utils.menu.MenuCore;
import essentialsapi.utils.menu.interfaces.MenuClick;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public abstract class ConfirmBuilder {

    public abstract ConfirmMiddleItem getMiddleItem();

    public abstract ConfirmItem getConfirm();

    public abstract ConfirmItem getDeny();

    public void openConfirmMenu(Player player) {

        MenuCore menu = new MenuCore("Weet je het zeker?", 3);

        menu.addMenuClick(new ItemBuilder(Material.LEGACY_STAINED_GLASS_PANE, 1, (byte) 5).setName("&a&lJa!").setLore(getConfirm().getLore()).toItemStack(), getConfirm().getOnClick(), 11);
        menu.addMenuClick(getMiddleItem().itemBuilder.setLore(getMiddleItem().getLore()).toItemStack(), getMiddleItem().getOnClick(), 13);
        menu.addMenuClick(new ItemBuilder(Material.LEGACY_STAINED_GLASS_PANE, 1, (byte) 14).setName("&c&lNee!").setLore(getDeny().getLore()).toItemStack(), getDeny().getOnClick(), 15);

        menu.openMenu(player);
    }

    public ConfirmBuilder apply() {
        return this;
    }

    @RequiredArgsConstructor
    public static class ConfirmItem {

        @Getter
        protected final String[] lore;
        @Getter
        protected final MenuClick onClick;
    }

    @RequiredArgsConstructor
    public static class ConfirmMiddleItem {

        @Getter
        protected final ItemBuilder itemBuilder;
        @Getter
        protected final String[] lore;
        @Getter
        protected final MenuClick onClick;
    }

}