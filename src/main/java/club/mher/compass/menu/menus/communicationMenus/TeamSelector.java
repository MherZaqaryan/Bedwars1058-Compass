package club.mher.compass.menu.menus.communicationMenus;

import club.mher.compass.Compass;
import club.mher.compass.data.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.CommunicationsMenu;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;

import club.mher.compass.util.MessagingUtil;
import club.mher.compass.util.NBTItem;
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
        for (String s : Compass.getMainConfig().getYml().getString(MainConfig.COMMUNICATIONS_MENU_TEAMS+".slots").split(",")) {
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
        return Compass.getMainConfig().getInt(MainConfig.COMMUNICATIONS_MENU_TEAMS+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbti = new NBTItem(e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (!Compass.getBedWars().getArenaUtil().isPlaying(player)) return;
        IArena a = Compass.getBedWars().getArenaUtil().getArenaByPlayer(player);
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
            inventory.setItem(slots.get(index), getTeamItem(Compass.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_TEAM_ITEM, false, null), t));
            teamMap.put(slots.get(index), t);
            index++;
        }
        NBTItem nbtBack = new NBTItem(Compass.getMainConfig().getItem(player, MainConfig.COMMUNICATIONS_MENU_TEAMS+".back-item", true, "back-item"));
        inventory.setItem(nbtBack.getInteger("slot"), nbtBack.getItem());
    }

    private ItemStack getTeamItem(ItemStack itemStack, ITeam team) {
        String displayName = yml.getString(path).replace("{team}", team.getColor().chat() + "§l" + team.getDisplayName(lang));
        itemStack = Compass.getBedWars().getVersionSupport().colourItem(itemStack, team);
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
