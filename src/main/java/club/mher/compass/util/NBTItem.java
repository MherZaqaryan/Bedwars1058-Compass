package club.mher.compass.util;

import club.mher.compass.Compass;
import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.inventory.ItemStack;

public class NBTItem {

    private static final VersionSupport nms = Compass.getBedWars().getVersionSupport();

    private ItemStack is;

    public NBTItem(ItemStack is) {
        this.is = is;
    }

    public void setString(String key, String value) {
        this.is = nms.setTag(is, key, value);
    }

    public String getString(String key) {
        String value = nms.getTag(is, key);
        return value == null ? "" : value;
    }

    public void setInteger(String key, Integer value) {
        this.is = nms.setTag(is, key, String.valueOf(value));
    }

    public Integer getInteger(String key) {
        return Integer.valueOf(nms.getTag(is, key));
    }

    public ItemStack getItem() {
        return is;
    }

}