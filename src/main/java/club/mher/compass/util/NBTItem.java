package club.mher.compass.util;

import club.mher.compass.Compass;
import org.bukkit.inventory.ItemStack;

public class NBTItem {

    private ItemStack is;

    public NBTItem(ItemStack is) {
        this.is = is;
    }

    public void setString(String key, String value) {
        this.is = Compass.getBedWars().setNmsTag(is, key, value);
    }

    public String getString(String key) {
        String value = Compass.getBedWars().getNmsTag(is, key);
        return value == null ? "" : value;
    }

    public void setInteger(String key, Integer value) {
        this.is = Compass.getBedWars().setNmsTag(is, key, String.valueOf(value));
    }

    public Integer getInteger(String key) {
        return Integer.valueOf(Compass.getBedWars().getNmsTag(is, key));
    }

    public ItemStack getItem() {
        return is;
    }

}