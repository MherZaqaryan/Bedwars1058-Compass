package club.mher.compass.util;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.inventory.ItemStack;

public class NBTItem {

    private ItemStack is;

    public NBTItem(ItemStack is) {
        this.is = is;
    }

    public void setString(String key, String value) {
        this.is = BedWars.nms.setTag(is, key, value);
    }

    public String getString(String key) {
        String value = BedWars.nms.getTag(is, key);
        return value == null ? "" : value;
    }

    public void setInteger(String key, Integer value) {
        this.is = BedWars.nms.setTag(is, key, String.valueOf(value));
    }

    public Integer getInteger(String key) {
        return Integer.valueOf(BedWars.nms.getTag(is, key));
    }

    public ItemStack getItem() {
        return is;
    }

}