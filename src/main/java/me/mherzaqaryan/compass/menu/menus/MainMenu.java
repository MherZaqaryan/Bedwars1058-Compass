package me.mherzaqaryan.compass.menu.menus;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import de.tr7zw.nbtapi.NBTItem;
import me.mherzaqaryan.compass.CompassPlugin;
import me.mherzaqaryan.compass.data.ConfigData;
import me.mherzaqaryan.compass.data.MessagesData;
import me.mherzaqaryan.compass.menu.Menu;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenu extends Menu {

    YamlConfiguration yml;

    public MainMenu(Player player) {
        super(player);
        this.yml = MessagesData.getYml(player);
    }

    @Override
    public String getMenuName() {
        return yml.getString(MessagesData.MAIN_MENU_TITLE);
    }

    @Override
    public int getSlots() {
        return CompassPlugin.getConfigData().getInt(ConfigData.MAIN_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbtItem = new NBTItem(e.getCurrentItem());
        if (!CompassPlugin.getBedWars().getArenaUtil().isPlaying(player)) return;
        IArena arena = CompassPlugin.getBedWars().getArenaUtil().getArenaByPlayer(player);
        if (!(arena.getStatus().equals(GameState.playing) && !arena.isSpectator(player))) return;
        switch (nbtItem.getString("data")) {
            case "tracker-menu":
                new TrackerMenu(player, arena).open();
                break;
            case "communications-menu":
                new CommunicationsMenu(player, arena).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        IArena arena = CompassPlugin.getBedWars().getArenaUtil().getArenaByPlayer(player);
        ITeam team = arena.getTeam(player);
        if (team.getMembers().size() > 1 && CompassPlugin.getConfigData().getBoolean(ConfigData.USE_COMMUNICATIONS)) {
            NBTItem nbtTracker = new NBTItem(CompassPlugin.getConfigData().getItem(player, ConfigData.MAIN_MENU_TRACKER_TEAM, true, "tracker-menu"));
            NBTItem nbtComm = new NBTItem(CompassPlugin.getConfigData().getItem(player, ConfigData.MAIN_MENU_COMMUNICATIONS, true, "communications-menu"));
            inventory.setItem(nbtTracker.getInteger("slot"), nbtTracker.getItem());
            inventory.setItem(nbtComm.getInteger("slot"), nbtComm.getItem());
        }
        else {
            NBTItem nbtItem = new NBTItem(CompassPlugin.getConfigData().getItem(player, ConfigData.MAIN_MENU_TRACKER, true, "tracker-menu"));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
    }

}
