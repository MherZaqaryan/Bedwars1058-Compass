package club.mher.compass.menu.menus.communicationMenus;

import club.mher.compass.Compass;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.menu.Menu;
import club.mher.compass.menu.menus.CommunicationsMenu;

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

    Object lang;

    YamlConfiguration yml;

    List<Integer> slots;

    Object arena;

    Object team;

    String path;

    HashMap<Integer, Object> teamMap = new HashMap<>();

    public TeamSelector(Player player, Object team, String path) {
        super(player);
        this.lang = MessagesData.getLang(player.getPlayer());
        this.yml = Compass.getBedWars().getYml(lang);
        this.slots = new ArrayList<>();
        this.team = team;
        this.arena = Compass.getBedWars().getArenaByTeam(team);
        this.path = path;
        for (String s : Compass.getMainConfig().getYml().getString(BW1058MainConfig.COMMUNICATIONS_MENU_TEAMS+".slots").split(",")) {
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
        return Compass.getMainConfig().getInt(BW1058MainConfig.COMMUNICATIONS_MENU_TEAMS+".size");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        NBTItem nbti = new NBTItem(e.getCurrentItem());
        Player player = (Player) e.getWhoClicked();
        if (!Compass.getBedWars().isPlaying(player)) return;
        if (Compass.getBedWars().isSpectator(player)) return;
        if (Compass.getBedWars().getPlayersForTeam(team).size() <= 1) return;
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
        for (Object team : Compass.getBedWars().getTeamsByArena(arena)) {
            if (Compass.getBedWars().getPlayersForTeam(team).isEmpty()) continue;
            if (Compass.getBedWars().getTeamByArena(arena, player).equals(team)) continue;
            if (slots.size() <= index) continue;
            inventory.setItem(slots.get(index), getTeamItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.TRACKER_MENU_TEAM_ITEM, false, null), team));
            teamMap.put(slots.get(index), team);
            index++;
        }
        NBTItem nbtBack = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.COMMUNICATIONS_MENU_TEAMS+".back-item", true, "back-item"));
        inventory.setItem(nbtBack.getInteger("slot"), nbtBack.getItem());
    }

    private ItemStack getTeamItem(ItemStack itemStack, Object team) {
        String displayName = yml.getString(path).replace("{team}", Compass.getBedWars().getColorForTeam(team) + "§l" + Compass.getBedWars().getTeamDisplayName(team, player));
        itemStack = Compass.getBedWars().colourItem(itemStack, team);
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
