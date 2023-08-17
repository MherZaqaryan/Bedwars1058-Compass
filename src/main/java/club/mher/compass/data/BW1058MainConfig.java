package club.mher.compass.data;

import club.mher.compass.Compass;
import club.mher.compass.menu.MenuType;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import club.mher.compass.util.ItemBuilder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.stream.Collectors;

public class BW1058MainConfig extends ConfigManager implements MainConfig {

    public BW1058MainConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration yml = getYml();
        yml.options().header("BedWars1058 Compass Addon By Mher Zaqaryan, Version: " + Compass.getInstance().getDescription().getVersion());
        yml.addDefault(MainConfig.USE_COMMUNICATIONS, true);
        yml.addDefault(TRACKER_UPDATE_RATE, 1);
        yml.addDefault(MESSAGE_SEND_SOUND, Compass.getBedWars().getForCurrentVersion("SUCCESSFUL_HIT", "ENTITY_ARROW_HIT_PLAYER", "ENTITY_ARROW_HIT_PLAYER") + ",1,1");
        yml.addDefault(PLAYER_TRACK_COST, 2);
        yml.addDefault(PLAYER_TRACK_RESOURCE, "EMERALD");
        yml.addDefault(MAIN_MENU_SIZE, 27);
        saveItem(COMPASS_ITEM, "COMPASS", 8);
        saveItem(TRACKER_SHOP, "COMPASS", 45);
        saveItem(MAIN_MENU_TRACKER, "COMPASS", 13);
        saveItem(MAIN_MENU_TRACKER_TEAM, "COMPASS", 15);
        saveItem(MAIN_MENU_COMMUNICATIONS, "EMERALD", 11);
        yml.addDefault(TRACKER_MENU_SIZE, 36);
        yml.addDefault(TRACKER_MENU_SLOTS, "10,11,12,13,14,15,16,19,20,21,22,23,24,25");
        saveItem(TRACKER_MENU_TEAM_ITEM, Compass.getBedWars().getForCurrentVersion("WOOL", "WOOL", "WHITE_WOOL"));
        saveItem(TRACKER_MENU_BACK_ITEM, "ARROW", 31);
        yml.addDefault(COMMUNICATIONS_MENU_SIZE, 45);
        saveItem(COMMUNICATIONS_MENU_BACK, "ARROW", 40);
        yml.addDefault(COMMUNICATIONS_MENU_ITEMS, Collections.emptyList());
        if (isFirstTime()) {
            saveCommunicationItem("1", "BOOK", 10, MenuType.NONE);
            saveCommunicationItem("2", "BOOK", 11, MenuType.NONE);
            saveCommunicationItem("3", Compass.getBedWars().getForCurrentVersion("IRON_FENCE", "IRON_FENCE", "IRON_BARS"), 12, MenuType.NONE);
            saveCommunicationItem("4", "IRON_SWORD", 13, MenuType.TEAM);
            saveCommunicationItem("5", "DIAMOND", 14, MenuType.RESOURCE);
            saveCommunicationItem("6", "CHEST", 15, MenuType.RESOURCE);
            saveCommunicationItem("7", "BOOK", 20, MenuType.NONE);
            saveCommunicationItem("8", "BOOK", 21, MenuType.NONE);
            saveCommunicationItem("9", Compass.getBedWars().getForCurrentVersion("IRON_FENCE", "IRON_FENCE", "IRON_BARS"), 22, MenuType.NONE);
            saveCommunicationItem("10", "IRON_SWORD", 23, MenuType.TEAM);
            saveCommunicationItem("11", "DIAMOND", 24, MenuType.RESOURCE);
            saveCommunicationItem("12", "FEATHER", 25, MenuType.NONE);
        }
        yml.addDefault(COMMUNICATIONS_MENU_TEAMS + ".size", 36);
        yml.addDefault(COMMUNICATIONS_MENU_TEAMS + ".slots", "10,11,12,13,14,15,16,19,20,21,22,23,24,25");
        saveItem(COMMUNICATIONS_MENU_TEAMS + ".back-item", "ARROW", 31);
        yml.addDefault(COMMUNICATIONS_MENU_RESOURCES + ".size", 36);
        saveItem(COMMUNICATIONS_MENU_RESOURCES + ".back-item", "ARROW", 31);
        saveResource(COMMUNICATIONS_MENU_RESOURCES + ".iron", "IRON_INGOT", 12);
        saveResource(COMMUNICATIONS_MENU_RESOURCES + ".gold", "GOLD_INGOT", 14);
        saveResource(COMMUNICATIONS_MENU_RESOURCES +".diamond", "DIAMOND", 10);
        saveResource(COMMUNICATIONS_MENU_RESOURCES + ".emerald", "EMERALD", 16);
        yml.options().copyDefaults(true);
        save();
    }

    private void saveResource(String path, String material, int slot) {
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", false);
        getYml().addDefault(path + ".slot", slot);
    }

    private void saveItem(String path, String material, int slot) {
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", false);
        getYml().addDefault(path + ".slot", slot);
    }

    private void saveItem(String path, String material) {
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", false);
    }

    private void saveCommunicationItem(String path, String material, int slot, MenuType menuType) {
        path = COMMUNICATIONS_MENU_ITEMS + "." + path;
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", false);
        getYml().addDefault(path + ".slot", slot);
        getYml().addDefault(path + ".menu", menuType.toString());
    }

    @Override
    public ItemStack getResourceItem(Player player, String name, String path) {
        name = COMMUNICATIONS_MENU_RESOURCES + "." + name;
        YamlConfiguration msg = MessagesData.getYml(player);
        String displayName = msg.getString(path).replace("{resource}", msg.getString(MessagesData.PATH + name + ".resource-name"));
        return new ItemBuilder(getString(name + ".material"))
            .setDisplayName(displayName)
            .setLore(msg.getStringList(MessagesData.COMMUNICATIONS_MENU_LORE).stream().map(s -> s.replace("{message}", displayName)).collect(Collectors.toList()))
            .setEnchanted(getBoolean(name + ".enchanted"))
            .addTag("path", path)
            .addTag("data", "resource-item")
            .addTag("slot", getInt(name + ".slot"))
            .build();
    }


    @Override
    public ItemStack getCommunicationItem(Player player, String path) {
        return new ItemBuilder(getItem(player, path, true, "communication-item"))
            .addTag("path", MessagesData.PATH + path + ".message")
            .addTag("menuType", getString(path + ".menu"))
            .build();
    }


    @Override
    public ItemStack getItem(Player player, String path, boolean hasSlot, String customData) {
        YamlConfiguration msg = MessagesData.getYml(player);
        ItemBuilder ib = new ItemBuilder(getString(path + ".material"))
            .setEnchanted(getBoolean(path + ".enchanted"))
            .setDisplayName(msg.getString(MessagesData.PATH +path+ ".display-name"))
            .setLore(msg.getStringList(MessagesData.PATH + path + ".lore"));
        if (customData != null) {
            ib.addTag("data", customData);
        }
        if (hasSlot) {
            ib.addTag("slot", getInt(path + ".slot"));
        }
        return ib.build();
    }

}