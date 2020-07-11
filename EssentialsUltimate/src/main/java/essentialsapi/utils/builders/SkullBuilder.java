package essentialsapi.utils.builders;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import essentialsapi.utils.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class SkullBuilder extends ItemBuilder {

    public SkullBuilder() {
        super(Material.LEGACY_SKULL_ITEM, 1, (byte) 3);
    }

    public SkullBuilder(int amount) {
        super(Material.LEGACY_SKULL_ITEM, amount, (byte) 3);
    }

    @SuppressWarnings("deprecation")
    public SkullBuilder setOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta) this.getItemMeta();
        skullMeta.setOwner(owner);
        this.setItemMeta(skullMeta);
        return this;
    }

    /*public SkullBuilder setOwner(OfflinePlayer owner){
        SkullMeta skullMeta = (SkullMeta) this.getItemMeta();
        skullMeta.setOwningPlayer(owner);
        this.setItemMeta(skullMeta);
        return this;
    }*/

    public SkullBuilder setSkullSkin(String skinUrl) {
        SkullMeta skullMeta = (SkullMeta) this.getItemMeta();
        GameProfile localGameProfile = new GameProfile(UUID.randomUUID(), null);
        byte[] arrayOfByte = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[]{skinUrl}).getBytes());
        localGameProfile.getProperties().put("textures", new Property("textures", new String(arrayOfByte)));
        Field localField;
        try {
            localField = skullMeta.getClass().getDeclaredField("profile");
            localField.setAccessible(true);
            localField.set(skullMeta, localGameProfile);
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        this.setItemMeta(skullMeta);
        return this;
    }

}