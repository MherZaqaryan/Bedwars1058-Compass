package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;

import club.mher.compass.util.NBTItem;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenu extends Menu {

    final YamlConfiguration yml;

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
        return Compass.getMainConfig().getInt(BW1058MainConfig.MAIN_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbtItem = new NBTItem(e.getCurrentItem());
        if (!Compass.getBedWars().isPlaying(player)) return;
        Object arena = Compass.getBedWars().getArenaByPlayer(player);
        if (!(Compass.getBedWars().isArenaPlaying(arena) && !Compass.getBedWars().isSpectator(player))) return;
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
        Object team = Compass.getBedWars().getTeam(player);
        if (Compass.getBedWars().getPlayersForTeam(team).size() > 1 && Compass.getMainConfig().getBoolean(BW1058MainConfig.USE_COMMUNICATIONS)) {
            NBTItem nbtTracker = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.MAIN_MENU_TRACKER_TEAM, true, "tracker-menu"));
            NBTItem nbtComm = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.MAIN_MENU_COMMUNICATIONS, true, "communications-menu"));
            inventory.setItem(nbtTracker.getInteger("slot"), nbtTracker.getItem());
            inventory.setItem(nbtComm.getInteger("slot"), nbtComm.getItem());
        }
        else {
            NBTItem nbtItem = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.MAIN_MENU_TRACKER, true, "tracker-menu"));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
    }

}
