package essentialsapi.utils.builders;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static essentialsapi.utils.Utils.replaceColor;

@SuppressWarnings("deprecation")
public class ItemBuilder {

    private ItemStack is;
    private String skullOwner;
    private ItemMeta im;

    public ItemBuilder(ItemBuilder builder) {
        this(builder.toItemStack().clone());
    }

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is.clone();
        this.im = is.getItemMeta();
    }

    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
        im = is.getItemMeta();
    }

    public ItemBuilder(Material m, int amount, byte durability) {
        is = new ItemStack(m, amount, durability);
        im = is.getItemMeta();
    }

    public ItemMeta getItemMeta() {
        return this.im;
    }

    public void setItemMeta(ItemMeta im) {
        this.im = im;
    }

    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setType(Material m) {
        is.setType(m);
        return this;
    }

    public String getName() {
        ItemMeta im = is.getItemMeta();
        return im.getDisplayName();
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = getItemMeta();
        im.setDisplayName(replaceColor(name));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public List<String> getLore() {
        return getItemMeta().getLore();
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = getItemMeta();
        List<String> color = new ArrayList<String>();
        for (String l : lore) {
            color.add(replaceColor(l));
        }
        im.setLore(color);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = getItemMeta();
        List<String> color = new ArrayList<String>();
        for (String l : lore) {
            color.add(replaceColor(l));
        }
        im.setLore(color);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLore() {
        ItemMeta im = getItemMeta();
        List<String> lore = new ArrayList<String>(im.getLore());
        lore.clear();
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setPotionEffect(PotionEffect effect) {
        try {
            PotionMeta meta = ((PotionMeta) this.getItemMeta());

            meta.setMainEffect(effect.getType());
            meta.addCustomEffect(effect, false);

            this.is.setItemMeta(meta);

            return this;
        } catch (ClassCastException im) {
            //
        }

        return this;
    }

    public ItemBuilder hideAttributes() {
        ItemMeta im = getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        im.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder createPotion(boolean splash) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        PotionEffect main = null;
        for (PotionEffect current : ((PotionMeta) this.is.getItemMeta()).getCustomEffects()) {
            if (main == null) main = current;
            meta.addCustomEffect(current, true);
        }

        if (main == null) {
            // Invalid potion. Just return itemstack.
            return this;
        }

        potion.setItemMeta(meta);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setMainEffect(main.getType());
        Potion po = new Potion(PotionType.getByEffect(main.getType()));
        po.setSplash(splash);
        po.apply(potion);
        return new ItemBuilder(potion);
    }

    public ItemBuilder setSplash(boolean splash) {
        try {
            Potion potion = Potion.fromItemStack(this.is);

            potion.setSplash(splash);

            this.is = potion.toItemStack(this.is.getAmount());

            return this;
        } catch (ClassCastException im) {
            //
        }

        return this;
    }

//    public ItemBuilder setSkullOwnerNMS(String url) {
//
//        try {
//            byte[] decodedBytes = Base64.getDecoder().decode(url);
//
//            String decoded = new String(decodedBytes).replace("{\"textures\":{\"SKIN\":{\"url\":\"", "").replace("\"}}}", "");
//
//            SkullMeta headMeta = (SkullMeta) is.getItemMeta();
//            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
//            byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", decoded).getBytes());
//            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
//            Field profileField;
//            try {
//                profileField = headMeta.getClass().getDeclaredField("profile");
//                profileField.setAccessible(true);
//                profileField.set(headMeta, profile);
//                this.skullOwner = decoded;
//            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            is.setItemMeta(headMeta);
//        } catch (ClassCastException expected) {
//        }
//        return this;
//    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantGlow(Enchantment ench, int level) {
        ItemMeta im = getItemMeta();
        im.addEnchant(ench, level, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        is.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder addLoreLines(List<String> line) {
        ItemMeta im = getItemMeta();
        List<String> lore = new ArrayList<String>();
        if (im.hasLore()) {
            lore = new ArrayList<String>(im.getLore());
        }
        for (String s : line) {
            if (s == null) {
                continue;
            }
            lore.add(replaceColor(s));
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLines(String... line) {
        ItemMeta im = getItemMeta();
        List<String> lore = new ArrayList<String>();
        if (im.hasLore()) {
            lore = new ArrayList<String>(im.getLore());
        }
        for (String s : line) {
            lore.add(replaceColor(s));
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = getItemMeta();
        List<String> lore = new ArrayList<String>(im.getLore());
        if (!lore.contains(line)) {
            return this;
        }
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = getItemMeta();
        List<String> lore = new ArrayList<String>(im.getLore());
        if (index < 0 || index > lore.size()) {
            return this;
        }
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = getItemMeta();
        List<String> lore = new ArrayList<String>();
        if (im.hasLore()) {
            lore = new ArrayList<String>(im.getLore());
        }
        lore.add(replaceColor(line));
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = getItemMeta();
        List<String> lore = new ArrayList<String>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setDyeColor(DyeColor color) {
        this.is.setDurability(color.getDyeData());
        return this;
    }

    @Deprecated
    public ItemBuilder setWoolColor(DyeColor color) {
        if (!is.getType().equals(Material.LEGACY_WOOL)) {
            return this;
        }
        this.is.setDurability(color.getDyeData());
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemStack toItemStack() {
        return is;
    }

    public String getSkullOwner() {
        return skullOwner;
    }

    public ItemBuilder setSkullOwner(Player owner) {
        setSkullOwner(owner.getName());
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

//    public ItemBuilder setSkullOwnerNMS(SkullData data) {
//
//        try {
//            String url = data.getTexture();
//            if (data.getType() == SkullDataType.NAME) {
//                return this.setSkullOwner(url);
//            }
//
//            SkullMeta headMeta = (SkullMeta) this.is.getItemMeta();
//            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
//            if (data.getType() == SkullDataType.URL) {
//                byte[] decodedBytes = Base64.getDecoder().decode(url);
//                String decoded = (new String(decodedBytes)).replace("{\"textures\":{\"SKIN\":{\"url\":\"", "").replace("\"}}}", "");
//                byte[] encodedData = Base64.getEncoder().encode(String.replaceColor("{textures:{SKIN:{\"url\":\"%s\"}}}", decoded).getBytes());
//                profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
//
//                try {
//                    Field profileField = headMeta.getClass().getDeclaredField("profile");
//                    profileField.setAccessible(true);
//                    profileField.set(headMeta, profile);
//                    this.skullOwner = decoded;
//                } catch (IllegalAccessException | NoSuchFieldException | IllegalArgumentException var10) {
//                    var10.printStackTrace();
//                }
//            } else if (data.getType() == SkullDataType.TEXTURE) {
//                profile.getProperties().put("textures", new Property("textures", data.getTexture()));
//
//                try {
//                    Field profileField = headMeta.getClass().getDeclaredField("profile");
//                    profileField.setAccessible(true);
//                    profileField.set(headMeta, profile);
//                    this.skullOwner = ((JsonObject) (new JsonParser()).parse(new String(Base64.getDecoder().decode(url)))).get("profileName").getAsString();
//                } catch (IllegalAccessException | NoSuchFieldException | IllegalArgumentException var9) {
//                    var9.printStackTrace();
//                }
//            }
//
//            this.is.setItemMeta(headMeta);
//        } catch (ClassCastException var11) {
//        }
//        return this;
//    }

//    public ItemBuilder setSkullOwnerNMS(Player player) {
//
//        String texture = "";
//        for (Map.Entry<String, Property> entry : NMSMethods.getCraftPlayer().getHandle().getProfile().getProperties().entries()) {
//            if (entry.getKey().equalsIgnoreCase("textures")) {
//                String decoded = new String(Base64.getDecoder().decode(entry.getValue().getValue()));
//                decoded = '{' + decoded.split(",")[decoded.split(",").length - 1];
//                texture = new String(Base64.getEncoder().encode(decoded.getBytes()));
//            }
//        }

//        if(!StringUtils.isNullOrEmpty(texture))
//
//    {
//        setSkullOwnerNMS(texture);
//    }
//
//        return this;
//}

    public ItemBuilder setOwner(String playerName) {
        ItemStack skull = (new SkullBuilder(1)).setOwner(skullOwner).toItemStack();
        ItemMeta metaSkull = skull.getItemMeta();
        metaSkull.setDisplayName(getName());
        metaSkull.setLore(getLore());
        skull.setItemMeta(metaSkull);
        return this;
    }

    public enum SkullDataType {
        NAME,
        URL,
        TEXTURE;

        SkullDataType() {

        }
    }

    public static class SkullData {
        private final String texture;
        private final SkullDataType type;

        public SkullData(String texture, SkullDataType type) {

            this.texture = texture;
            this.type = type;
        }

        public String getTexture() {

            return this.texture;
        }

        public SkullDataType getType() {

            return this.type;
        }
    }
}