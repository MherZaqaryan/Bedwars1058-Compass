package club.mher.compass.menu.menus;

import club.mher.compass.Compass;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.support.Misc;
import club.mher.compass.tasks.ActionBarTask;
import club.mher.compass.util.NBTItem;

import club.mher.compass.menu.Menu;
import club.mher.compass.util.TextUtil;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.shop.ShopManager;
import com.andrei1058.bedwars.shop.quickbuy.PlayerQuickBuyCache;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TrackerMenu extends Menu {
    private final Object bedWars;
    private final YamlConfiguration yml;
    private final Object arena;
    private final List<Integer> slots = new ArrayList<>();
    private final HashMap<Integer, Object> teamSlotMap;
    private boolean backToShop = false;

    public TrackerMenu(Object bedWars, Player player, Object arena) {
        super(player);
        this.bedWars = bedWars;
        this.arena = arena;
        this.teamSlotMap = new HashMap<>();
        this.yml = MessagesData.getYml(player);
        for (String s : Misc.getMainConfig().getYml().getString(MainConfig.TRACKER_MENU_SLOTS).split(",")) {
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
        return Misc.getMainConfig().getInt(MainConfig.TRACKER_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID uuid = player.getUniqueId();
        NBTItem nbtItem = new NBTItem(bedWars, e.getCurrentItem());

        if (nbtItem.getString("data").equals("main-menu")) {
            if (backToShop) {
                if (Compass.HOOK_NAME.equals("BW1058")){
                    ShopManager.shop.open(player, PlayerQuickBuyCache.getQuickBuyCache(player.getUniqueId()), false);
                } else if (Compass.HOOK_NAME.equals("BW2023")) {
                    ((com.tomkeuper.bedwars.api.BedWars) bedWars).getShopUtil().getShopManager().getShop().open(player, ((com.tomkeuper.bedwars.api.BedWars) bedWars).getShopUtil().getPlayerQuickBuyCache().getQuickBuyCache(player.getUniqueId()), false);
                }
            }else {
                new MainMenu(bedWars, player).open();
            }
        }

        if (!slots.contains(e.getSlot())) return;
        if (Compass.HOOK_NAME.equals("BW1058")){
            if (!isAllBedsDestroyed(((IArena) arena).getTeam(player))) {
                player.closeInventory();
                player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.NOT_ALL_BEDS_DESTROYED)));
                return;
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")) {
            if (!isAllBedsDestroyed(((com.tomkeuper.bedwars.api.arena.IArena) arena).getTeam(player))) {
                player.closeInventory();
                player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.NOT_ALL_BEDS_DESTROYED)));
                return;
            }
        }
        if (!player.getInventory().contains(Material.valueOf(Misc.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), Misc.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST))) {
            player.closeInventory();
            player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.NOT_ENOUGH_RESOURCE)));
            return;
        }

        player.closeInventory();

        if (Misc.isTracking(arena, uuid)) {
            if (Misc.getTrackingTeam(arena, uuid).equals(teamSlotMap.get(e.getSlot()))) {
                player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.ALREADY_TRACKING)));
                return;
            }
        }

        if (!Misc.getTrackingArenaMap().containsKey(arena)) {
            new ActionBarTask(bedWars, arena).runTaskTimer(Compass.getInstance(), 0, Misc.getMainConfig().getInt(MainConfig.TRACKER_UPDATE_RATE));
        }

        Misc.setTrackingTeam(arena, uuid, teamSlotMap.get(e.getSlot()));
        if (Compass.HOOK_NAME.equals("BW1058")) ((BedWars) bedWars).getShopUtil().takeMoney(player, Material.valueOf(Misc.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), Misc.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST));
        if (Compass.HOOK_NAME.equals("BW2023")) ((com.tomkeuper.bedwars.api.BedWars) bedWars).getShopUtil().takeMoney(player, Material.valueOf(Misc.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), Misc.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST));
        player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.PURCHASED)));
    }

    @Override
    public void setMenuItems() {
        NBTItem nbtItem = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_BACK_ITEM, true, "main-menu"));
        int index = 0;
        if (Compass.HOOK_NAME.equals("BW1058")){
            for (ITeam team : ((IArena) arena).getTeams()) {
                if (team.getMembers().isEmpty()) continue;
                if (((IArena) arena).getTeam(player).equals(team)) continue;
                if (slots.size() <= index) continue;
                NBTItem teamItem = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_TEAM_ITEM, false, null));
                teamSlotMap.put(slots.get(index), team);
                inventory.setItem(slots.get(index), getTeamItem(teamItem.getItem(), team, player));
                index++;
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")) {
            for (com.tomkeuper.bedwars.api.arena.team.ITeam team : ((com.tomkeuper.bedwars.api.arena.IArena) arena).getTeams()) {
                if (team.getMembers().isEmpty()) continue;
                if (((com.tomkeuper.bedwars.api.arena.IArena) arena).getTeam(player).equals(team)) continue;
                if (slots.size() <= index) continue;
                NBTItem teamItem = new NBTItem(bedWars, Misc.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_TEAM_ITEM, false, null));
                teamSlotMap.put(slots.get(index), team);
                inventory.setItem(slots.get(index), getTeamItem(teamItem.getItem(), team, player));
                index++;
            }
        }

        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

    public ItemStack getTeamItem(ItemStack itemStack, Object team, Player player) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (Compass.HOOK_NAME.equals("BW1058")){
            itemStack = ((BedWars) bedWars).getVersionSupport().colourItem(itemStack, (ITeam) team);
            itemMeta.setDisplayName(TextUtil.colorize(itemMeta.getDisplayName().replace("{team}", ((ITeam) team).getDisplayName(((BedWars) bedWars).getPlayerLanguage(player)))));
        }
        if (Compass.HOOK_NAME.equals("BW2023")){
            itemStack = ((com.tomkeuper.bedwars.api.BedWars) bedWars).getVersionSupport().colourItem(itemStack, (com.tomkeuper.bedwars.api.arena.team.ITeam) team);
            itemMeta.setDisplayName(TextUtil.colorize(itemMeta.getDisplayName().replace("{team}", ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getDisplayName(((com.tomkeuper.bedwars.api.BedWars) bedWars).getPlayerLanguage(player)))));
        }
        if (itemMeta.hasLore()) {
            List<String> newLore = new ArrayList<>();
            itemMeta.getLore().forEach(s -> newLore.add(TextUtil.colorize(s.replace("{status}", getStatus(player, arena)))));
            itemMeta.setLore(newLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getStatus(Player player, Object arena) {
        if (Compass.HOOK_NAME.equals("BW1058")){
            if (!isAllBedsDestroyed(((IArena) arena).getTeam(player))) {
                return yml.getString(MessagesData.STATUS_LOCKED);
            }else if (!player.getInventory().contains(Material.valueOf(Misc.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), Misc.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST))) {
                return yml.getString(MessagesData.STATUS_NOT_ENOUGH);
            }else {
                return yml.getString(MessagesData.STATUS_UNLOCKED);
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")) {
            if (!isAllBedsDestroyed(((com.tomkeuper.bedwars.api.arena.IArena) arena).getTeam(player))) {
                return yml.getString(MessagesData.STATUS_LOCKED);
            }else if (!player.getInventory().contains(Material.valueOf(Misc.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), Misc.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST))) {
                return yml.getString(MessagesData.STATUS_NOT_ENOUGH);
            }else {
                return yml.getString(MessagesData.STATUS_UNLOCKED);
            }
        }
        return null;
    }

    public boolean isAllBedsDestroyed(Object t) {
        boolean bool = true;
        if (Compass.HOOK_NAME.equals("BW1058")){
            for (ITeam team : ((IArena) arena).getTeams()) {
                if (team.equals(t)) continue;
                if (team.isBedDestroyed()) continue;
                bool = false;
                break;
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")) {
            for (com.tomkeuper.bedwars.api.arena.team.ITeam team : ((com.tomkeuper.bedwars.api.arena.IArena) arena).getTeams()) {
                if (team.equals(t)) continue;
                if (team.isBedDestroyed()) continue;
                bool = false;
                break;
            }
        }
        return bool;
    }

    public void setBackToShop(boolean b) {
        this.backToShop = b;
    }

}

