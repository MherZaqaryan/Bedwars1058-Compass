package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.data.MessagesData;

import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.communicationMenus.ResourceSelector;
import club.mher.compass.menu.menus.communicationMenus.TeamSelector;
import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CommunicationsMenu extends Menu {

    final YamlConfiguration yml;
    final Object arena;

    public CommunicationsMenu(Player player, Object arena) {
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
        return Compass.getMainConfig().getInt(BW1058MainConfig.COMMUNICATIONS_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbti = new NBTItem(e.getCurrentItem());
        if (Compass.getBedWars().isSpectator(player)) return;
        Object team = Compass.getBedWars().getTeam(player);
        if (Compass.getBedWars().getPlayersForTeam(team).size() <= 1) return;
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
        for (String s : Compass.getMainConfig().getYml().getConfigurationSection(BW1058MainConfig.COMMUNICATIONS_MENU_ITEMS).getKeys(false)) {
            NBTItem nbtItem = new NBTItem(Compass.getMainConfig().getCommunicationItem(player, BW1058MainConfig.COMMUNICATIONS_MENU_ITEMS+"."+s));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
        NBTItem backItem = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.COMMUNICATIONS_MENU_BACK, true, "back-item"));
        inventory.setItem(backItem.getInteger("slot"), backItem.getItem());
    }

}
