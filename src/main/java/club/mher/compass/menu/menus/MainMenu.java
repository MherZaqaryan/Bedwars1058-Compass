package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;

import club.mher.compass.util.NBTItem;
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
        return Compass.getMainConfig().getInt(MainConfig.MAIN_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbtItem = new NBTItem(e.getCurrentItem());
        if (!Compass.getBedWars().getArenaUtil().isPlaying(player)) return;
        IArena arena = Compass.getBedWars().getArenaUtil().getArenaByPlayer(player);
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
        IArena arena = Compass.getBedWars().getArenaUtil().getArenaByPlayer(player);
        ITeam team = arena.getTeam(player);
        if (team.getMembers().size() > 1 && Compass.getMainConfig().getBoolean(MainConfig.USE_COMMUNICATIONS)) {
            NBTItem nbtTracker = new NBTItem(Compass.getMainConfig().getItem(player, MainConfig.MAIN_MENU_TRACKER_TEAM, true, "tracker-menu"));
            NBTItem nbtComm = new NBTItem(Compass.getMainConfig().getItem(player, MainConfig.MAIN_MENU_COMMUNICATIONS, true, "communications-menu"));
            inventory.setItem(nbtTracker.getInteger("slot"), nbtTracker.getItem());
            inventory.setItem(nbtComm.getInteger("slot"), nbtComm.getItem());
        }
        else {
            NBTItem nbtItem = new NBTItem(Compass.getMainConfig().getItem(player, MainConfig.MAIN_MENU_TRACKER, true, "tracker-menu"));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
    }

}
