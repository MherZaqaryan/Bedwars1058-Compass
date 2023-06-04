package club.mher.compass.util.bw1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.inventory.ItemStack;

public class NBTItem {
    private static BedWars bedWars = null;
    private static final VersionSupport nms = bedWars.getVersionSupport();

    private ItemStack is;

    public NBTItem(BedWars bedWars, ItemStack is) {
        this.bedWars = bedWars;
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