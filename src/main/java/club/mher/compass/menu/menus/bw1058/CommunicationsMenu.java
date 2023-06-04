package club.mher.compass.menu.menus.bw1058;

import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.bw1058.MessagesData;
import club.mher.compass.menu.menus.bw1058.communicationMenus.ResourceSelector;
import club.mher.compass.menu.menus.bw1058.communicationMenus.TeamSelector;
import club.mher.compass.support.BW1058;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;

import club.mher.compass.menu.Menu;
import club.mher.compass.util.bw1058.MessagingUtil;
import club.mher.compass.util.bw1058.NBTItem;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CommunicationsMenu extends Menu {

    private final BedWars bedWars;
    YamlConfiguration yml;
    IArena arena;

    public CommunicationsMenu(BedWars bedWars, Player player, IArena arena) {
        super(player);
        this.bedWars = bedWars;
        this.yml = MessagesData.getYml(player);
        this.arena = arena;
    }

    @Override
    public String getMenuName() {
        return yml.getString(MessagesData.COMMUNICATIONS_MENU_TITLE);
    }

    @Override
    public int getSlots() {
        return BW1058.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbti = new NBTItem(bedWars, e.getCurrentItem());
        if (arena.isSpectator(player)) return;
        ITeam team = arena.getTeam(player);
        if (team.getMembers().size() <= 1) return;
        if (nbti.getString("data").equals("back-item")) new MainMenu(bedWars, player).open();
        else if (nbti.getString("data").equals("communication-item")) {
            switch (nbti.getString("menuType")) {
                case "NONE":
                    MessagingUtil.simpleMessage(player, team, nbti.getString("path"));
                    break;
                case "TEAM":
                    new TeamSelector(bedWars, player, team, nbti.getString("path")).open();
                    break;
                case "RESOURCE":
                    new ResourceSelector(bedWars, player, team, nbti.getString("path")).open();
                    break;
            }
        }

    }

    @Override
    public void setMenuItems() {
        for (String s : BW1058.getMainConfig().getYml().getConfigurationSection(MainConfig.COMMUNICATIONS_MENU_ITEMS).getKeys(false)) {
            NBTItem nbtItem = new NBTItem(bedWars, BW1058.getMainConfig().getCommunicationItem(player, MainConfig.COMMUNICATIONS_MENU_ITEMS+"."+s));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
        NBTItem backItem = new NBTItem(bedWars, BW1058.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_BACK, true, "back-item"));
        inventory.setItem(backItem.getInteger("slot"), backItem.getItem());
    }

}
