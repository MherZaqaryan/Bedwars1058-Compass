package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.support.Misc;
import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;

import club.mher.compass.util.TextUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamSelector extends Menu {

    private final Object bedWars;
    Object lang;

    YamlConfiguration yml;

    List<Integer> slots;

    Object team;

    String path;

    HashMap<Integer, Object> teamMap = new HashMap<>();

    public TeamSelector(Object bedWars, Player player, Object team, String path) {
        super(player);
        this.bedWars = bedWars;
        this.lang = MessagesData.getLang(player.getPlayer());
        if (Compass.HOOK_NAME.equals("BW1058")) this.yml = ((Language) lang).getYml();
        else if (Compass.HOOK_NAME.equals("BW2023")) this.yml = ((com.tomkeuper.bedwars.api.language.Language) lang).getYml();
        this.slots = new ArrayList<>();
        this.team = team;
        this.path = path;
        for (String s : Misc.getMainConfig().getYml().getString(MainConfig.COMMUNICATIONS_MENU_TEAMS+".slots").split(",")) {
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
        return Misc.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_TEAMS+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbti = new NBTItem(bedWars, e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (Compass.HOOK_NAME.equals("BW1058")) {
            if (!((BedWars) bedWars).getArenaUtil().isPlaying(player)) return;
            IArena a = ((BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
            if (a.isSpectator(player)) return;
            if (((ITeam) team).getMembers().size() <= 1) return;
            switch (nbti.getString("data")) {
                case "back-item":
                    new CommunicationsMenu(bedWars, player, ((ITeam) team).getArena()).open();
                    break;
                case "team-item":
                    MessagingUtil.teamMessage(player, team, nbti.getString("path"), teamMap.get(e.getSlot()));
                    break;
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")){
            if (!((com.tomkeuper.bedwars.api.BedWars) bedWars).getArenaUtil().isPlaying(player)) return;
            com.tomkeuper.bedwars.api.arena.IArena a = ((com.tomkeuper.bedwars.api.BedWars) bedWars).getArenaUtil().getArenaByPlayer(player);
            if (a.isSpectator(player)) return;
            if (((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getMembers().size() <= 1) return;
            switch (nbti.getString("data")) {
                case "back-item":
                    new CommunicationsMenu(bedWars, player, ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getArena()).open();
                    break;
                case "team-item":
                    MessagingUtil.teamMessage(player, team, nbti.getString("path"), teamMap.get(e.getSlot()));
                    break;
            }
        }

    }

    @Override
    public void setMenuItems() {
        int index = 0;
        if (Compass.HOOK_NAME.equals("BW1058")){
            for (ITeam t : ((ITeam) team).getArena().getTeams()) {
                if (t.getMembers().isEmpty()) continue;
                if (t.equals(team)) continue;
                if (slots.size() <= index) continue;
                inventory.setItem(slots.get(index), getTeamItem(Misc.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_TEAM_ITEM, false, null), t));
                teamMap.put(slots.get(index), t);
                index++;
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")){
            for (com.tomkeuper.bedwars.api.arena.team.ITeam t : ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getArena().getTeams()) {
                if (t.getMembers().isEmpty()) continue;
                if (t.equals(team)) continue;
                if (slots.size() <= index) continue;
                inventory.setItem(slots.get(index), getTeamItem(Misc.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_TEAM_ITEM, false, null), t));
                teamMap.put(slots.get(index), t);
                index++;
            }
        }

        NBTItem nbtBack = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_TEAMS+".back-item", true, "back-item"));
        inventory.setItem(nbtBack.getInteger("slot"), nbtBack.getItem());
    }

    private ItemStack getTeamItem(ItemStack itemStack, Object team) {
        String displayName;
        if (Compass.HOOK_NAME.equals("BW1058")){
            displayName = yml.getString(path).replace("{team}", ((ITeam) team).getColor().chat() + "§l" + ((ITeam) team).getDisplayName((Language) lang));
            itemStack = ((BedWars)bedWars).getVersionSupport().colourItem(itemStack, (ITeam) team);
        } else if (Compass.HOOK_NAME.equals("BW2023")){
            displayName = yml.getString(path).replace("{team}", ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getColor().chat() + "§l" + ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getDisplayName((com.tomkeuper.bedwars.api.language.Language) lang));
            itemStack = ((com.tomkeuper.bedwars.api.BedWars)bedWars).getVersionSupport().colourItem(itemStack, (com.tomkeuper.bedwars.api.arena.team.ITeam) team);
        } else {
            displayName = null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextUtil.colorize(displayName));
        List<String> newLore = new ArrayList<>();
        yml.getStringList(MessagesData.COMMUNICATIONS_MENU_LORE).forEach(s -> newLore.add(TextUtil.colorize(s.replace("{message}", displayName))));
        itemMeta.setLore(newLore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(bedWars, itemStack);
        nbtItem.setString("path", path);
        nbtItem.setString("data", "team-item");
        return nbtItem.getItem();
    }

}
