package me.mherzaqaryan.compass.menu.menus.communicationMenus;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import de.tr7zw.nbtapi.NBTItem;
import me.mherzaqaryan.compass.CompassPlugin;
import me.mherzaqaryan.compass.data.ConfigData;
import me.mherzaqaryan.compass.data.MessagesData;
import me.mherzaqaryan.compass.menu.Menu;
import me.mherzaqaryan.compass.menu.menus.CommunicationsMenu;
import me.mherzaqaryan.compass.util.MessagingUtil;
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
        return CompassPlugin.getConfigData().getInt(ConfigData.COMMUNICATIONS_MENU_RESOURCES+".size");
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
            NBTItem nbtItem = new NBTItem(CompassPlugin.getConfigData().getResourceItem(player, s, path));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
            resourceMap.put(nbtItem.getInteger("slot"), s);
        }
        NBTItem nbtItem = new NBTItem(CompassPlugin.getConfigData().getItem(player, ConfigData.COMMUNICATIONS_MENU_RESOURCES+".back-item", true, "back-item"));
        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

}
