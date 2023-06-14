package club.mher.compass.util;

import club.mher.compass.Compass;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.inventory.ItemStack;

public class NBTItem {
    private static Object bedWars;

    private static Object nms;

    private ItemStack is;

    public NBTItem(Object bedWars, ItemStack is) {
        this.bedWars = bedWars;
        this.is = is;
        if (Compass.HOOK_NAME.equals("BW1058")) nms = ((BedWars) bedWars).getVersionSupport();
        if (Compass.HOOK_NAME.equals("BW2023")) nms = ((com.tomkeuper.bedwars.api.BedWars) bedWars).getVersionSupport();
    }

    public void setString(String key, String value) {
        if (Compass.HOOK_NAME.equals("BW1058")) this.is = ((VersionSupport) nms).setTag(is, key, value);
        if (Compass.HOOK_NAME.equals("BW2023")) this.is = ((com.tomkeuper.bedwars.api.server.VersionSupport) nms).setTag(is, key, value);
    }

    public String getString(String key) {
        if (Compass.HOOK_NAME.equals("BW1058")) return ((VersionSupport) nms).getTag(is, key) == null ? "" : ((VersionSupport) nms).getTag(is, key);
        if (Compass.HOOK_NAME.equals("BW2023")) return ((com.tomkeuper.bedwars.api.server.VersionSupport) nms).getTag(is, key) == null ? "" : ((com.tomkeuper.bedwars.api.server.VersionSupport) nms).getTag(is, key);
        throw new RuntimeException("Unsupported NMS type");
    }

    public void setInteger(String key, Integer value) {
        if (Compass.HOOK_NAME.equals("BW1058")) this.is = ((VersionSupport) nms).setTag(is, key, String.valueOf(value));
        if (Compass.HOOK_NAME.equals("BW2023")) this.is = ((com.tomkeuper.bedwars.api.server.VersionSupport) nms).setTag(is, key, String.valueOf(value));
    }

    public Integer getInteger(String key) {
        if (Compass.HOOK_NAME.equals("BW1058")) return ((VersionSupport) nms).getTag(is, key) == null ? null : Integer.valueOf(((VersionSupport) nms).getTag(is, key));
        if (Compass.HOOK_NAME.equals("BW2023")) return ((com.tomkeuper.bedwars.api.server.VersionSupport) nms).getTag(is, key) == null ? null : Integer.valueOf(((com.tomkeuper.bedwars.api.server.VersionSupport) nms).getTag(is, key));
        throw new RuntimeException("Unsupported NMS type");
    }

    public ItemStack getItem() {
        return is;
    }

}