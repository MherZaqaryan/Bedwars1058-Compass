package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.data.MessagesData;

import club.mher.compass.menu.Menu;
import club.mher.compass.tasks.ActionBarTask;
import club.mher.compass.util.NBTItem;
import club.mher.compass.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TrackerMenu extends Menu {

    private final YamlConfiguration yml;
    private final Object arena;
    private final List<Integer> slots = new ArrayList<>();
    private final HashMap<Integer, Object> teamSlotMap;
    private boolean backToShop = false;

    public TrackerMenu(Player player, Object arena) {
        super(player);
        this.arena = arena;
        this.teamSlotMap = new HashMap<>();
        this.yml = MessagesData.getYml(player);
        for (String s : Compass.getMainConfig().getYml().getString(BW1058MainConfig.TRACKER_MENU_SLOTS).split(",")) {
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
        return yml.getString(MessagesData.TRACKER_MENU_TITLE);
    }

    @Override
    public int getSlots() {
        return Compass.getMainConfig().getInt(BW1058MainConfig.TRACKER_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID uuid = player.getUniqueId();
        NBTItem nbtItem = new NBTItem(e.getCurrentItem());

        if (nbtItem.getString("data").equals("main-menu")) {
            if (backToShop) {
                Compass.getBedWars().openShop(player);
            }else {
                new MainMenu(player).open();
            }
        }

        if (!slots.contains(e.getSlot())) return;

        if (!Compass.getBedWars().isAllBedsDestroyedForTeam(arena, player)) {
            player.closeInventory();
            player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.NOT_ALL_BEDS_DESTROYED)));
            return;
        }

        if (!player.getInventory().contains(Material.valueOf(Compass.getMainConfig().getString(BW1058MainConfig.PLAYER_TRACK_RESOURCE)), Compass.getMainConfig().getInt(BW1058MainConfig.PLAYER_TRACK_COST))) {
            player.closeInventory();
            player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.NOT_ENOUGH_RESOURCE)));
            return;
        }

        player.closeInventory();

        if (Compass.isTracking(arena, uuid)) {
            if (Compass.getTrackingTeam(arena, uuid).equals(teamSlotMap.get(e.getSlot()))) {
                player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.ALREADY_TRACKING)));
                return;
            }
        }

        if (!Compass.getBedWars().containsKeyTrackingArenaMap(arena)) {
            new ActionBarTask(arena).runTaskTimer(Compass.getInstance(), 0, Compass.getMainConfig().getInt(BW1058MainConfig.TRACKER_UPDATE_RATE));
        }

        Compass.setTrackingTeam(arena, uuid, teamSlotMap.get(e.getSlot()));
        Compass.getBedWars().takeMoney(player, Material.valueOf(Compass.getMainConfig().getString(BW1058MainConfig.PLAYER_TRACK_RESOURCE)), Compass.getMainConfig().getInt(BW1058MainConfig.PLAYER_TRACK_COST));
        player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.PURCHASED)));
    }

    @Override
    public void setMenuItems() {
        NBTItem nbtItem = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.TRACKER_MENU_BACK_ITEM, true, "main-menu"));
        int index = 0;
        for (Object team : Compass.getBedWars().getTeamsByArena(arena)) {
            if (Compass.getBedWars().getPlayersForTeam(team).isEmpty()) continue;
            if (Compass.getBedWars().getTeamByArena(arena, player).equals(team)) continue;
            if (slots.size() <= index) continue;
            NBTItem teamItem = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.TRACKER_MENU_TEAM_ITEM, false, null));
            teamSlotMap.put(slots.get(index), team);
            inventory.setItem(slots.get(index), getTeamItem(teamItem.getItem(), team, player));
            index++;
        }
        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

    public ItemStack getTeamItem(ItemStack itemStack, Object team, Player player) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemStack = Compass.getBedWars().colourItem(itemStack, team);
        itemMeta.setDisplayName(TextUtil.colorize(itemMeta.getDisplayName().replace("{team}", Compass.getBedWars().getTeamDisplayName(team, player))));
        if (itemMeta.hasLore()) {
            List<String> newLore = new ArrayList<>();
            itemMeta.getLore().forEach(s -> newLore.add(TextUtil.colorize(s.replace("{status}", getStatus(player, arena)))));
            itemMeta.setLore(newLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getStatus(Player player, Object arena) {
        if (!Compass.getBedWars().isAllBedsDestroyedForTeam(arena, player)) {
            return yml.getString(MessagesData.STATUS_LOCKED);
        }else if (!player.getInventory().contains(Material.valueOf(Compass.getMainConfig().getString(BW1058MainConfig.PLAYER_TRACK_RESOURCE)), Compass.getMainConfig().getInt(BW1058MainConfig.PLAYER_TRACK_COST))) {
            return yml.getString(MessagesData.STATUS_NOT_ENOUGH);
        }else {
            return yml.getString(MessagesData.STATUS_UNLOCKED);
        }
    }

    public void setBackToShop(boolean b) {
        this.backToShop = b;
    }

}

