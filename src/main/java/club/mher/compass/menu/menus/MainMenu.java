package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.support.Misc;
import club.mher.compass.util.NBTItem;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenu extends Menu {

    private final Object bedWars;
    YamlConfiguration yml;

    public MainMenu(Object bedWars, Player player) {
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
        return Misc.getMainConfig().getInt(MainConfig.MAIN_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbtItem = new NBTItem(bedWars, e.getCurrentItem());
        if (Compass.HOOK_NAME.equals("BW1058")){
            if (!((BedWars) bedWars).getArenaUtil().isPlaying(player)) return;
            IArena arena = ((BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
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
        if (Compass.HOOK_NAME.equals("BW2023")){
            if (!((com.tomkeuper.bedwars.api.BedWars) bedWars).getArenaUtil().isPlaying(player)) return;
            com.tomkeuper.bedwars.api.arena.IArena arena = ((com.tomkeuper.bedwars.api.BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
            if (!(arena.getStatus().equals(com.tomkeuper.bedwars.api.arena.GameState.playing) && !arena.isSpectator(player))) return;
            switch (nbtItem.getString("data")) {
                case "tracker-menu":
                    new TrackerMenu(bedWars, player, arena).open();
                    break;
                case "communications-menu":
                    new CommunicationsMenu(bedWars, player, arena).open();
                    break;
            }
        }
    }

    @Override
    public void setMenuItems() {
        Object arena = null;
        if (Compass.HOOK_NAME.equals("BW1058")) {
            arena = ((BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
            Object team = ((IArena) arena).getTeam(player);
            if (((ITeam) team).getMembers().size() > 1 && Misc.getMainConfig().getBoolean(MainConfig.USE_COMMUNICATIONS)) {
                NBTItem nbtTracker = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.MAIN_MENU_TRACKER_TEAM, true, "tracker-menu"));
                NBTItem nbtComm = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.MAIN_MENU_COMMUNICATIONS, true, "communications-menu"));
                inventory.setItem(nbtTracker.getInteger("slot"), nbtTracker.getItem());
                inventory.setItem(nbtComm.getInteger("slot"), nbtComm.getItem());
            }
        }
        if (Compass.HOOK_NAME.equals("BW2023")) {
            arena = ((com.tomkeuper.bedwars.api.BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
            com.tomkeuper.bedwars.api.arena.team.ITeam team = ((com.tomkeuper.bedwars.api.arena.IArena) arena).getTeam(player);
            if (team.getMembers().size() > 1 && Misc.getMainConfig().getBoolean(MainConfig.USE_COMMUNICATIONS)) {
                NBTItem nbtTracker = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.MAIN_MENU_TRACKER_TEAM, true, "tracker-menu"));
                NBTItem nbtComm = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.MAIN_MENU_COMMUNICATIONS, true, "communications-menu"));
                inventory.setItem(nbtTracker.getInteger("slot"), nbtTracker.getItem());
                inventory.setItem(nbtComm.getInteger("slot"), nbtComm.getItem());
            }
        }

        else {
            NBTItem nbtItem = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.MAIN_MENU_TRACKER, true, "tracker-menu"));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
    }

}
