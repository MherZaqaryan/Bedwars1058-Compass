package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.menus.communicationMenus.ResourceSelector;
import club.mher.compass.menu.menus.communicationMenus.TeamSelector;
import club.mher.compass.support.Misc;
import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;

import club.mher.compass.menu.Menu;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CommunicationsMenu extends Menu {

    private final Object bedWars;
    YamlConfiguration yml;
    Object arena;

    public CommunicationsMenu(Object bedWars, Player player, Object arena) {
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
        return Misc.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        NBTItem nbti = new NBTItem(bedWars, e.getCurrentItem());
        if (Compass.HOOK_NAME.equals("BW1058")) {
            if (((IArena) arena).isSpectator(player)) return;
            ITeam team = ((IArena) arena).getTeam(player);
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
        } else if (Compass.HOOK_NAME.equals("BW2023")){
            if (((com.tomkeuper.bedwars.api.arena.IArena) arena).isSpectator(player)) return;
            com.tomkeuper.bedwars.api.arena.team.ITeam team = ((com.tomkeuper.bedwars.api.arena.IArena) arena).getTeam(player);
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


    }

    @Override
    public void setMenuItems() {
        for (String s : Misc.getMainConfig().getYml().getConfigurationSection(MainConfig.COMMUNICATIONS_MENU_ITEMS).getKeys(false)) {
            NBTItem nbtItem = new NBTItem(bedWars, Misc.getMainConfig().getCommunicationItem(player, MainConfig.COMMUNICATIONS_MENU_ITEMS+"."+s));
            inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
        }
        NBTItem backItem = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_BACK, true, "back-item"));
        inventory.setItem(backItem.getInteger("slot"), backItem.getItem());
    }

}
