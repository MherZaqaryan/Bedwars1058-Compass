package club.mher.compass.menu.menus.communicationMenus;

import club.mher.compass.Compass;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.CommunicationsMenu;
import club.mher.compass.support.Misc;
import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class ResourceSelector extends Menu {

    private final Object bedWars;
    final YamlConfiguration yml;
    final String path;
    final Object team;
    final String[] resources = {"iron", "gold", "diamond", "emerald"};
    final HashMap<Integer, String> resourceMap = new HashMap<>();

    public ResourceSelector(Object bedWars, Player player, Object team, String path) {
        super(player);
        this.bedWars = bedWars;
        this.team = team;
        this.path = path;
        this.yml = MessagesData.getYml(player);
    }

    @Override
    public String getMenuName() {
        return yml.getString(MessagesData.COMMUNICATIONS_MENU_RESOURCES_TITLE);
    }

    @Override
    public int getSlots() {
        return Misc.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_RESOURCES+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbtItem = new NBTItem(bedWars, e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (Compass.HOOK_NAME.equals("BW1058")){
            if (!((BedWars) bedWars).getArenaUtil().isPlaying(player)) return;
            IArena arena = ((BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
            if (arena.isSpectator(player)) return;
            switch (nbtItem.getString("data")) {
                case "back-item":
                    new CommunicationsMenu(bedWars, player, ((ITeam)team).getArena()).open();
                    break;
                case "resource-item":
                    MessagingUtil.resourceMessage(player, ((ITeam)team), nbtItem.getString("path"), resourceMap.get(e.getSlot()));
                    break;
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")) {
            if (!((com.tomkeuper.bedwars.api.BedWars) bedWars).getArenaUtil().isPlaying(player)) return;
            com.tomkeuper.bedwars.api.arena.IArena arena = ((com.tomkeuper.bedwars.api.BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
            if (arena.isSpectator(player)) return;
            switch (nbtItem.getString("data")) {
                case "back-item":
                    new CommunicationsMenu(bedWars, player, ((com.tomkeuper.bedwars.api.arena.team.ITeam)team).getArena()).open();
                    break;
                case "resource-item":
                    MessagingUtil.resourceMessage(player, ((com.tomkeuper.bedwars.api.arena.team.ITeam)team), nbtItem.getString("path"), resourceMap.get(e.getSlot()));
                    break;
            }
        }

    }

    @Override
    public void setMenuItems() {
        for (String s : resources) {
            NBTItem nbtItem = new NBTItem(bedWars, Misc.getMainConfig().getResourceItem(player, s, path));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
            resourceMap.put(nbtItem.getInteger("slot"), s);
        }
        NBTItem nbtItem = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_RESOURCES+".back-item", true, "back-item"));
        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

}
