package club.mher.compass.menu.menus.communicationMenus;

import club.mher.compass.CompassPlugin;
import club.mher.compass.data.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.CommunicationsMenu;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class ResourceSelector extends Menu {

    final YamlConfiguration yml;
    final String path;
    final ITeam team;
    final String[] resources = {"iron", "gold", "diamond", "emerald"};
    final HashMap<Integer, String> resourceMap = new HashMap<>();

    public ResourceSelector(Player player, ITeam team, String path) {
        super(player);
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
        return CompassPlugin.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_RESOURCES+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbtItem = new NBTItem(e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (!CompassPlugin.getBedWars().getArenaUtil().isPlaying(player)) return;
        IArena arena = CompassPlugin.getBedWars().getArenaUtil().getArenaByPlayer(player);
        if (arena.isSpectator(player)) return;
        switch (nbtItem.getString("data")) {
            case "back-item":
                new CommunicationsMenu(player, team.getArena()).open();
                break;
            case "resource-item":
                MessagingUtil.resourceMessage(player, team, nbtItem.getString("path"), resourceMap.get(e.getSlot()));
                break;
        }
    }

    @Override
    public void setMenuItems() {
        for (String s : resources) {
            NBTItem nbtItem = new NBTItem(CompassPlugin.getMainConfig().getResourceItem(player, s, path));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
            resourceMap.put(nbtItem.getInteger("slot"), s);
        }
        NBTItem nbtItem = new NBTItem(CompassPlugin.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_RESOURCES+".back-item", true, "back-item"));
        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

}
