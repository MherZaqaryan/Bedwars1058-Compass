package me.mherzaqaryan.compass.menu.menus.communicationMenus;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import de.tr7zw.nbtapi.NBTItem;
import me.mherzaqaryan.compass.CompassPlugin;
import me.mherzaqaryan.compass.data.ConfigData;
import me.mherzaqaryan.compass.data.MessagesData;
import me.mherzaqaryan.compass.menu.Menu;
import me.mherzaqaryan.compass.menu.menus.CommunicationsMenu;
import me.mherzaqaryan.compass.util.MessagingUtil;
import me.mherzaqaryan.compass.util.TextUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamSelector extends Menu {

    Language lang;

    YamlConfiguration yml;

    List<Integer> slots;

    IArena arena;

    ITeam team;

    String path;

    HashMap<Integer, ITeam> teamMap = new HashMap<>();

    public TeamSelector(Player player, ITeam team, String path) {
        super(player);
        this.lang = MessagesData.getLang(player.getPlayer());
        this.yml = lang.getYml();
        this.slots = new ArrayList<>();
        this.team = team;
        this.arena = team.getArena();
        this.path = path;
        for (String s : CompassPlugin.getConfigData().getYml().getString(ConfigData.COMMUNICATIONS_MENU_TEAMS+".slots").split(",")) {
            int i;
            try {
                i = Integer.parseInt(s);
            }catch (NumberFormatException e) {
                continue;
            }
            slots.add(i);
        }
    }

    @Override
    public String getMenuName() {
        return yml.getString(MessagesData.COMMUNICATIONS_MENU_TEAMS_TITLE);
    }

    @Override
    public int getSlots() {
        return CompassPlugin.getConfigData().getInt(ConfigData.COMMUNICATIONS_MENU_TEAMS+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbti = new NBTItem(e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (!CompassPlugin.getBedWars().getArenaUtil().isPlaying(player)) return;
        IArena a = CompassPlugin.getBedWars().getArenaUtil().getArenaByPlayer(player);
        if (a.isSpectator(player)) return;
        if (team.getMembers().size() <= 1) return;
        switch (nbti.getString("data")) {
            case "back-item":
                new CommunicationsMenu(player, arena).open();
                break;
            case "team-item":
                MessagingUtil.teamMessage(player, team, nbti.getString("path"), teamMap.get(e.getSlot()));
                break;
        }
    }

    @Override
    public void setMenuItems() {
        int index = 0;
        for (ITeam t : arena.getTeams()) {
            if (t.getMembers().isEmpty()) continue;
            if (t.equals(team)) continue;
            if (slots.size() <= index) continue;
            inventory.setItem(slots.get(index), getTeamItem(CompassPlugin.getConfigData().getItem(player, ConfigData.TRACKER_MENU_TEAM_ITEM, false, null), t));
            teamMap.put(slots.get(index), t);
            index++;
        }
        NBTItem nbtBack = new NBTItem(CompassPlugin.getConfigData().getItem(player, ConfigData.COMMUNICATIONS_MENU_TEAMS+".back-item", true, "back-item"));
        inventory.setItem(nbtBack.getInteger("slot"), nbtBack.getItem());
    }

    private ItemStack getTeamItem(ItemStack itemStack, ITeam team) {
        String displayName = yml.getString(path).replace("{team}", team.getColor().chat() + "§l" + team.getDisplayName(lang));
        itemStack = CompassPlugin.getBedWars().getVersionSupport().colourItem(itemStack, team);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextUtil.colorize(displayName));
        List<String> newLore = new ArrayList<>();
        yml.getStringList(MessagesData.COMMUNICATIONS_MENU_LORE).forEach(s -> newLore.add(TextUtil.colorize(s.replace("{message}", displayName))));
        itemMeta.setLore(newLore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("path", path);
        nbtItem.setString("data", "team-item");
        return nbtItem.getItem();
    }

}
