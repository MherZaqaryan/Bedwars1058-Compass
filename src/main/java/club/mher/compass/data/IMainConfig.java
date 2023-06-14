package club.mher.compass.data;

import club.mher.compass.menu.MenuType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IMainConfig {

    ItemStack getItem(Player player, String path, boolean hasSlot, String customData);
    ItemStack getCommunicationItem(Player player, String path);
    ItemStack getResourceItem(Player player, String name, String path);
    YamlConfiguration getYml();
    int getInt(String path);
    String getString(String path);
    void reload();
    boolean getBoolean(String path);

}
