package club.mher.compass.menu.menus.communicationMenus;

import club.mher.compass.Compass;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.CommunicationsMenu;
import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class ResourceSelector extends Menu {

    final YamlConfiguration yml;
    final String path;
    final Object team;
    final String[] resources = {"iron", "gold", "diamond", "emerald"};
    final HashMap<Integer, String> resourceMap = new HashMap<>();

    public ResourceSelector(Player player, Object team, String path) {
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
        return Compass.getMainConfig().getInt(BW1058MainConfig.COMMUNICATIONS_MENU_RESOURCES+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbtItem = new NBTItem(e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (!Compass.getBedWars().isPlaying(player)) return;
        Object arena = Compass.getBedWars().getArenaByPlayer(player);
        if (Compass.getBedWars().isSpectator(player)) return;
        switch (nbtItem.getString("data")) {
            case "back-item":
                new CommunicationsMenu(player, Compass.getBedWars().getArenaByTeam(team)).open();
                break;
            case "resource-item":
                MessagingUtil.resourceMessage(player, team, nbtItem.getString("path"), resourceMap.get(e.getSlot()));
                break;
        }
    }

    @Override
    public void setMenuItems() {
        for (String s : resources) {
            NBTItem nbtItem = new NBTItem(Compass.getMainConfig().getResourceItem(player, s, path));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
            resourceMap.put(nbtItem.getInteger("slot"), s);
        }
        NBTItem nbtItem = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.COMMUNICATIONS_MENU_RESOURCES+".back-item", true, "back-item"));
        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

}
