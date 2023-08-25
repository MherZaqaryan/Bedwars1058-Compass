package club.mher.compass.data;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MainConfig {

    ItemStack getResourceItem(Player player, String name, String path);
    ItemStack getCommunicationItem(Player player, String path);
    ItemStack getItem(Player player, String path, boolean hasSlot, String customData);

    void reload();
    YamlConfiguration getYml();
    String getString(String path);
    int getInt(String path);
    boolean getBoolean(String path);


    String
            USE_COMMUNICATIONS = "use-quick-communications",
            COMPASS_ITEM = "compass-item",
            TRACKER_SHOP = "tracker-shop",
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
