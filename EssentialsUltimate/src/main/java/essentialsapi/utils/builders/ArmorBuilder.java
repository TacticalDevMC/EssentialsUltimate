package essentialsapi.utils.builders;
// Plugin made by TacticalDev
// File created on 25/05/2020 (23:12)
// Class name: ArmorBuilder


import essentialsapi.utils.builders.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorBuilder extends essentialsapi.utils.builders.ItemBuilder {

    public ArmorBuilder(Material material) {
        super(material);
    }

    public ArmorBuilder(Material material, int amount) {
        super(material, amount);
    }

    public ArmorBuilder(ItemBuilder builder) {
        super(builder);
    }

    private Color toRGBfromHex(String hexColor) {
        return Color.fromBGR(Integer.valueOf(hexColor.substring(1, 3), 16),
                Integer.valueOf(hexColor.substring(3, 5), 16),
                Integer.valueOf(hexColor.substring(5, 7), 16));
    }

    public ItemBuilder setArmorColor(int red, int green, int blue) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.getItemMeta();
        leatherArmorMeta.setColor(Color.fromBGR(red, green, blue));
        this.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemBuilder setArmorColor(String hexColor) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.getItemMeta();
        leatherArmorMeta.setColor(toRGBfromHex(hexColor));
        this.setItemMeta(leatherArmorMeta);
        return this;
    }

}
