package club.mher.compass.menu.menus.bw2023.communicationMenus;

import club.mher.compass.data.bw2023.MainConfig;
import club.mher.compass.data.bw2023.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.bw2023.CommunicationsMenu;
import club.mher.compass.support.BW2023;
import club.mher.compass.util.bw2023.MessagingUtil;
import club.mher.compass.util.bw2023.NBTItem;
import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class ResourceSelector extends Menu {

    private final BedWars bedWars;
    final YamlConfiguration yml;
    final String path;
    final ITeam team;
    final String[] resources = {"iron", "gold", "diamond", "emerald"};
    final HashMap<Integer, String> resourceMap = new HashMap<>();

    public ResourceSelector(BedWars bedWars, Player player, ITeam team, String path) {
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
        return BW2023.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_RESOURCES+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbtItem = new NBTItem(bedWars, e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (!bedWars.getArenaUtil().isPlaying(player)) return;
        IArena arena = bedWars.getArenaUtil().getArenaByPlayer(player);
        if (arena.isSpectator(player)) return;
        switch (nbtItem.getString("data")) {
            case "back-item":
                new CommunicationsMenu(bedWars, player, team.getArena()).open();
                break;
            case "resource-item":
                MessagingUtil.resourceMessage(player, team, nbtItem.getString("path"), resourceMap.get(e.getSlot()));
                break;
        }
    }

    @Override
    public void setMenuItems() {
        for (String s : resources) {
            NBTItem nbtItem = new NBTItem(bedWars, BW2023.getMainConfig().getResourceItem(player, s, path));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
            resourceMap.put(nbtItem.getInteger("slot"), s);
        }
        NBTItem nbtItem = new NBTItem(bedWars, BW2023.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_RESOURCES+".back-item", true, "back-item"));
        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

}
