package club.mher.compass.menu.menus.bw2023;

import club.mher.compass.data.bw2023.MainConfig;
import club.mher.compass.data.bw1058.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.bw2023.CommunicationsMenu;
import club.mher.compass.menu.menus.bw2023.TrackerMenu;
import club.mher.compass.support.BW1058;
import club.mher.compass.support.BW2023;
import club.mher.compass.util.bw2023.NBTItem;
import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenu extends Menu {

    private final BedWars bedWars;
    YamlConfiguration yml;

    public MainMenu(BedWars bedWars, Player player) {
        super(player);
        this.bedWars = bedWars;
        this.yml = MessagesData.getYml(player);
    }

    @Override
    public String getMenuName() {
        return yml.getString(MessagesData.MAIN_MENU_TITLE);
    }

    @Override
    public int getSlots() {
        return BW1058.getMainConfig().getInt(MainConfig.MAIN_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbtItem = new NBTItem(bedWars, e.getCurrentItem());
        if (!bedWars.getArenaUtil().isPlaying(player)) return;
        IArena arena = bedWars.getArenaUtil().getArenaByPlayer(player);
        if (!(arena.getStatus().equals(GameState.playing) && !arena.isSpectator(player))) return;
        switch (nbtItem.getString("data")) {
            case "tracker-menu":
                new TrackerMenu(bedWars, player, arena).open();
                break;
            case "communications-menu":
                new CommunicationsMenu(bedWars, player, arena).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        IArena arena = bedWars.getArenaUtil().getArenaByPlayer(player);
        ITeam team = arena.getTeam(player);
        if (team.getMembers().size() > 1 && BW1058.getMainConfig().getBoolean(MainConfig.USE_COMMUNICATIONS)) {
            NBTItem nbtTracker = new NBTItem(bedWars, BW1058.getMainConfig().getItem(player, MainConfig.MAIN_MENU_TRACKER_TEAM, true, "tracker-menu"));
            NBTItem nbtComm = new NBTItem(bedWars, BW1058.getMainConfig().getItem(player, MainConfig.MAIN_MENU_COMMUNICATIONS, true, "communications-menu"));
            inventory.setItem(nbtTracker.getInteger("slot"), nbtTracker.getItem());
            inventory.setItem(nbtComm.getInteger("slot"), nbtComm.getItem());
        }
        else {
            NBTItem nbtItem = new NBTItem(bedWars, BW1058.getMainConfig().getItem(player, MainConfig.MAIN_MENU_TRACKER, true, "tracker-menu"));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
    }

}
