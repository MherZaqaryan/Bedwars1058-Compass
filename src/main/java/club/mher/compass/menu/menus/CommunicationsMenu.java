package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.MainConfig;
import club.mher.compass.data.MessagesData;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;

import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.communicationMenus.ResourceSelector;
import club.mher.compass.menu.menus.communicationMenus.TeamSelector;
import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CommunicationsMenu extends Menu {

    YamlConfiguration yml;
    IArena arena;

    public CommunicationsMenu(Player player, IArena arena) {
        super(player);
        this.yml = MessagesData.getYml(player);
        this.arena = arena;
    }

    @Override
    public String getMenuName() {
        return yml.getString(MessagesData.COMMUNICATIONS_MENU_TITLE);
    }

    @Override
    public int getSlots() {
        return Compass.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbti = new NBTItem(e.getCurrentItem());
        if (arena.isSpectator(player)) return;
        ITeam team = arena.getTeam(player);
        if (team.getMembers().size() <= 1) return;
        if (nbti.getString("data").equals("back-item")) new MainMenu(player).open();
        else if (nbti.getString("data").equals("communication-item")) {
            switch (nbti.getString("menuType")) {
                case "NONE":
                    MessagingUtil.simpleMessage(player, team, nbti.getString("path"));
                    break;
                case "TEAM":
                    new TeamSelector(player, team, nbti.getString("path")).open();
                    break;
                case "RESOURCE":
                    new ResourceSelector(player, team, nbti.getString("path")).open();
                    break;
            }
        }

    }

    @Override
    public void setMenuItems() {
        for (String s : Compass.getMainConfig().getYml().getConfigurationSection(MainConfig.COMMUNICATIONS_MENU_ITEMS).getKeys(false)) {
            NBTItem nbtItem = new NBTItem(Compass.getMainConfig().getCommunicationItem(player, MainConfig.COMMUNICATIONS_MENU_ITEMS+"."+s));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
        NBTItem backItem = new NBTItem(Compass.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_BACK, true, "back-item"));
        inventory.setItem(backItem.getInteger("slot"), backItem.getItem());
    }

}
