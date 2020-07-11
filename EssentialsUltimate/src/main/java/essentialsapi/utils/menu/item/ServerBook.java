package essentialsapi.utils.menu.item;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public abstract class ServerBook {

    private Player player;
    private ItemStack book;
    private List<IChatBaseComponent> pages;

    public abstract BaseComponent getBookText();

    public void openBook() {

        int slot = player.getInventory().getHeldItemSlot();
        ItemStack old = player.getInventory().getItem(slot);
        player.getInventory().setItem(slot, getBookContent());

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, (byte) 0);
        buf.writerIndex(1);

        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        player.getInventory().setItem(slot, old);
    }

    @SuppressWarnings("unchecked")
    private ItemStack getBookContent() {

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        List<IChatBaseComponent> pages;

        try {
            pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bookMeta);
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
            return null;
        }
        IChatBaseComponent page = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(getBookText()));
        pages.add(page);

        bookMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.book.getItemMeta().getDisplayName()));
        bookMeta.setLore(this.book.getItemMeta().getLore());

        bookMeta.setTitle(ChatColor.stripColor(this.book.getItemMeta().getDisplayName()));
        bookMeta.setAuthor("EssentialsUltimate");

        book.setItemMeta(bookMeta);
        return book;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getBook() {
        return book;
    }

    public List<IChatBaseComponent> getPages() {
        return pages;
    }

    public void setPages(List<IChatBaseComponent> pages) {
        this.pages = pages;
    }
}