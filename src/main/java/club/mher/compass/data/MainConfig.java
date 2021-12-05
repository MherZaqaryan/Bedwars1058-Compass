package club.mher.compass.data;

import club.mher.compass.Compass;
import club.mher.compass.menu.MenuType;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import club.mher.compass.util.ItemBuilder;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.stream.Collectors;

public class MainConfig extends ConfigManager {

    public MainConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration config = getYml();
        BedWars bw = Compass.getBedWars();

        config.options().header("BedWars1058 Compass Addon By Mher Zaqaryan, Version: " + Compass.getInstance().getDescription().getVersion());

        config.addDefault(USE_COMMUNICATIONS, true);
        config.addDefault(TRACKER_UPDATE_RATE, 1);
        config.addDefault(MESSAGE_SEND_SOUND, bw.getForCurrentVersion("SUCCESSFUL_HIT", "ENTITY_ARROW_HIT_PLAYER", "ENTITY_ARROW_HIT_PLAYER") + ",1,1");
        config.addDefault(PLAYER_TRACK_COST, 2);
        config.addDefault(PLAYER_TRACK_RESOURCE, "EMERALD");
        config.addDefault(MAIN_MENU_SIZE, 27);

        saveItem(COMPASS_ITEM, "COMPASS", false, 8);
//        config.addDefault(COMPASS_ITEM+".drop-slot", 17);
        saveItem(MAIN_MENU_TRACKER, "COMPASS", false, 13);
        saveItem(MAIN_MENU_TRACKER_TEAM, "COMPASS", false, 15);
        saveItem(MAIN_MENU_COMMUNICATIONS, "EMERALD", false, 11);

        config.addDefault(TRACKER_MENU_SIZE, 36);
        config.addDefault(TRACKER_MENU_SLOTS, "10,11,12,13,14,15,16,19,20,21,22,23,24,25");
        saveItem(TRACKER_MENU_TEAM_ITEM, bw.getForCurrentVersion("WOOL", "WOOL", "WHITE_WOOL"), false);
        saveItem(TRACKER_MENU_BACK_ITEM, "ARROW", false, 31);
        config.addDefault(COMMUNICATIONS_MENU_SIZE, 45);
        saveItem(COMMUNICATIONS_MENU_BACK, "ARROW", false, 40);
        config.addDefault(COMMUNICATIONS_MENU_ITEMS, new String[]{});

        if (isFirstTime()) {
            saveCommunicationItem("1", "BOOK", false, 10, MenuType.NONE);
            saveCommunicationItem("2", "BOOK", false, 11, MenuType.NONE);
            saveCommunicationItem("3", bw.getForCurrentVersion("IRON_FENCE", "IRON_FENCE", "IRON_BARS"), false, 12, MenuType.NONE);
            saveCommunicationItem("4", "IRON_SWORD", false, 13, MenuType.TEAM);
            saveCommunicationItem("5", "DIAMOND", false, 14, MenuType.RESOURCE);
            saveCommunicationItem("6", "CHEST", false, 15, MenuType.RESOURCE);
            saveCommunicationItem("7", "BOOK", false, 20, MenuType.NONE);
            saveCommunicationItem("8", "BOOK", false, 21, MenuType.NONE);
            saveCommunicationItem("9", bw.getForCurrentVersion("IRON_FENCE", "IRON_FENCE", "IRON_BARS"), false, 22, MenuType.NONE);
            saveCommunicationItem("10", "IRON_SWORD", false, 23, MenuType.TEAM);
            saveCommunicationItem("11", "DIAMOND", false, 24, MenuType.RESOURCE);
            saveCommunicationItem("12", "FEATHER", false, 25, MenuType.NONE);
        }

        config.addDefault(COMMUNICATIONS_MENU_TEAMS + ".size", 36);
        config.addDefault(COMMUNICATIONS_MENU_TEAMS + ".slots", "10,11,12,13,14,15,16,19,20,21,22,23,24,25");
        saveItem(COMMUNICATIONS_MENU_TEAMS + ".back-item", "ARROW", false, 31);
        config.addDefault(COMMUNICATIONS_MENU_RESOURCES + ".size", 36);
        saveItem(COMMUNICATIONS_MENU_RESOURCES + ".back-item", "ARROW", false, 31);
        saveResource(COMMUNICATIONS_MENU_RESOURCES + ".iron", "IRON_INGOT", false, 12);
        saveResource(COMMUNICATIONS_MENU_RESOURCES + ".gold", "GOLD_INGOT", false, 14);
        saveResource(COMMUNICATIONS_MENU_RESOURCES +".diamond", "DIAMOND", false, 10);
        saveResource(COMMUNICATIONS_MENU_RESOURCES + ".emerald", "EMERALD", false, 16);
        config.options().copyDefaults(true);
        save();
    }

    private void saveResource(String path, String material, boolean enchanted, int slot) {
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", enchanted);
        getYml().addDefault(path + ".slot", slot);
    }

    private void saveItem(String path, String material, boolean enchanted, int slot) {
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", enchanted);
        getYml().addDefault(path + ".slot", slot);
    }

    private void saveItem(String path, String material, boolean enchanted) {
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", enchanted);
    }

    private void saveCommunicationItem(String path, String material, boolean enchanted, int slot, MenuType menuType) {
        path = COMMUNICATIONS_MENU_ITEMS + "." + path;
        getYml().addDefault(path + ".material", material);
        getYml().addDefault(path + ".enchanted", enchanted);
        getYml().addDefault(path + ".slot", slot);
        getYml().addDefault(path + ".menu", menuType.toString());
    }

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

    public ItemStack getCommunicationItem(Player player, String path) {
        return new ItemBuilder(getItem(player, path, true, "communication-item"))
            .addTag("path", MessagesData.PATH + path + ".message")
            .addTag("menuType", getString(path + ".menu"))
            .build();
    }

    public ItemStack getItem(Player player, String path, boolean hasSlot, String customData) {
        YamlConfiguration msg = MessagesData.getYml(player);
        ItemBuilder ib = new ItemBuilder(getString(path + ".material"))
            .setEnchanted(getBoolean(path + ".enchanted"))
            .setDisplayName(msg.getString(MessagesData.PATH +path+ ".display-name"))
            .setLore(msg.getStringList(MessagesData.PATH + path + ".lore"));
        if (customData != null) ib.addTag("data", customData);
        if (hasSlot) ib.addTag("slot", getInt(path + ".slot"));
        return ib.build();
    }

    public static final String
            USE_COMMUNICATIONS = "use-quick-communications",
            COMPASS_ITEM = "compass-item",
            MAIN_MENU_SIZE = "menus.main-menu.size",
            MAIN_MENU_TRACKER = "menus.main-menu.tracker",
            MAIN_MENU_TRACKER_TEAM = "menus.main-menu.tracker-team",
            MAIN_MENU_COMMUNICATIONS = "menus.main-menu.communications",
            TRACKER_MENU = "menus.tracker-menu",
            TRACKER_MENU_SIZE = "menus.tracker-menu.size",
            TRACKER_MENU_SLOTS = "menus.tracker-menu.slots",
            TRACKER_MENU_TEAM_ITEM = "menus.tracker-menu.team-item",
            TRACKER_MENU_BACK_ITEM = "menus.tracker-menu.back-item",
            COMMUNICATIONS_MENU_SIZE = "menus.communications.size",
            COMMUNICATIONS_MENU_BACK = "menus.communications.back-item",
            COMMUNICATIONS_MENU_ITEMS = "menus.communications.items",
            COMMUNICATIONS_MENU_TEAMS = "communication-menus.teams",
            COMMUNICATIONS_MENU_RESOURCES = "communication-menus.resources",
            MESSAGE_SEND_SOUND = "team-message-sound",
            PLAYER_TRACK_COST = "player-track.cost",
            PLAYER_TRACK_RESOURCE = "player-track.resource",
            TRACKER_UPDATE_RATE = "tracker-update-rate";

}
