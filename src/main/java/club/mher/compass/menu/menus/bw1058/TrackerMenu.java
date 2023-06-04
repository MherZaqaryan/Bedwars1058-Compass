package club.mher.compass.menu.menus.bw1058;

import club.mher.compass.Compass;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.bw1058.MessagesData;
import club.mher.compass.support.BW1058;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;

import club.mher.compass.menu.Menu;
import club.mher.compass.tasks.bw1058.ActionBarTask;
import club.mher.compass.util.bw1058.NBTItem;
import club.mher.compass.util.TextUtil;
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
    private final BedWars bedWars;
    private final YamlConfiguration yml;
    private final IArena arena;
    private final List<Integer> slots = new ArrayList<>();
    private final HashMap<Integer, ITeam> teamSlotMap;
    private boolean backToShop = false;

    public TrackerMenu(BedWars bedWars, Player player, IArena arena) {
        super(player);
        this.bedWars = bedWars;
        this.arena = arena;
        this.teamSlotMap = new HashMap<>();
        this.yml = MessagesData.getYml(player);
        for (String s : BW1058.getMainConfig().getYml().getString(MainConfig.TRACKER_MENU_SLOTS).split(",")) {
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
        return BW1058.getMainConfig().getInt(MainConfig.TRACKER_MENU_SIZE);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID uuid = player.getUniqueId();
        NBTItem nbtItem = new NBTItem(bedWars, e.getCurrentItem());

        if (nbtItem.getString("data").equals("main-menu")) {
            if (backToShop) {
                ShopManager.shop.open(player, PlayerQuickBuyCache.getQuickBuyCache(player.getUniqueId()), false);
            }else {
                new MainMenu(bedWars, player).open();
            }
        }

        if (!slots.contains(e.getSlot())) return;

        if (!isAllBedsDestroyed(arena.getTeam(player))) {
            player.closeInventory();
            player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.NOT_ALL_BEDS_DESTROYED)));
            return;
        }

        if (!player.getInventory().contains(Material.valueOf(BW1058.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), BW1058.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST))) {
            player.closeInventory();
            player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.NOT_ENOUGH_RESOURCE)));
            return;
        }

        player.closeInventory();

        if (BW1058.isTracking(arena, uuid)) {
            if (BW1058.getTrackingTeam(arena, uuid).equals(teamSlotMap.get(e.getSlot()))) {
                player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.ALREADY_TRACKING)));
                return;
            }
        }

        if (!BW1058.getTrackingArenaMap().containsKey(arena)) {
            new ActionBarTask(bedWars, arena).runTaskTimer(Compass.getInstance(), 0, BW1058.getMainConfig().getInt(MainConfig.TRACKER_UPDATE_RATE));
        }

        BW1058.setTrackingTeam(arena, uuid, teamSlotMap.get(e.getSlot()));
        bedWars.getShopUtil().takeMoney(player, Material.valueOf(BW1058.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), BW1058.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST));
        player.sendMessage(TextUtil.colorize(yml.getString(MessagesData.PURCHASED)));
    }

    @Override
    public void setMenuItems() {
        NBTItem nbtItem = new NBTItem(bedWars, BW1058.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_BACK_ITEM, true, "main-menu"));
        int index = 0;
        for (ITeam team : arena.getTeams()) {
            if (team.getMembers().isEmpty()) continue;
            if (arena.getTeam(player).equals(team)) continue;
            if (slots.size() <= index) continue;
            NBTItem teamItem = new NBTItem(bedWars, BW1058.getMainConfig().getItem(player, MainConfig.TRACKER_MENU_TEAM_ITEM, false, null));
            teamSlotMap.put(slots.get(index), team);
            inventory.setItem(slots.get(index), getTeamItem(teamItem.getItem(), team, player));
            index++;
        }
        inventory.setItem(nbtItem.getInteger("slot"), nbtItem.getItem());
    }

    public ItemStack getTeamItem(ItemStack itemStack, ITeam team, Player player) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemStack = bedWars.getVersionSupport().colourItem(itemStack, team);
        itemMeta.setDisplayName(TextUtil.colorize(itemMeta.getDisplayName().replace("{team}", team.getDisplayName(bedWars.getPlayerLanguage(player)))));
        if (itemMeta.hasLore()) {
            List<String> newLore = new ArrayList<>();
            itemMeta.getLore().forEach(s -> newLore.add(TextUtil.colorize(s.replace("{status}", getStatus(player, arena)))));
            itemMeta.setLore(newLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getStatus(Player player, IArena arena) {
        if (!isAllBedsDestroyed(arena.getTeam(player))) {
            return yml.getString(MessagesData.STATUS_LOCKED);
        }else if (!player.getInventory().contains(Material.valueOf(BW1058.getMainConfig().getString(MainConfig.PLAYER_TRACK_RESOURCE)), BW1058.getMainConfig().getInt(MainConfig.PLAYER_TRACK_COST))) {
            return yml.getString(MessagesData.STATUS_NOT_ENOUGH);
        }else {
            return yml.getString(MessagesData.STATUS_UNLOCKED);
        }
    }

    public boolean isAllBedsDestroyed(ITeam t) {
        boolean bool = true;
        for (ITeam team : arena.getTeams()) {
            if (team.equals(t)) continue;
            if (team.isBedDestroyed()) continue;
            bool = false;
            break;
        }
        return bool;
    }

    public void setBackToShop(boolean b) {
        this.backToShop = b;
    }

}

