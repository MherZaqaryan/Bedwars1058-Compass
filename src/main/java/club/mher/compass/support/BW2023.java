package club.mher.compass.support;

import club.mher.compass.Compass;
import club.mher.compass.command.BW2023SubCommand;
import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.addon.Addon;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;

public class BW2023 extends Addon implements IBedWars{

    @Getter
    private final HashMap<IArena, HashMap<UUID, Object>> trackingArenaMap = new HashMap<>();

    final BedWars instance;
    public BW2023() {
        this.instance = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        instance.getAddonsUtil().registerAddon(this);
    }

    @Override
    public String getAuthor() {
        return Compass.getInstance().getDescription().getAuthors().get(0);
    }

    @Override
    public Plugin getPlugin() {
        return Compass.getInstance();
    }

    @Override
    public String getVersion() {
        return Compass.getInstance().getDescription().getVersion();
    }

    @Override
    public String getDescription() {
        return Compass.getInstance().getDescription().getDescription();
    }

    @Override
    public String getName() {
        return Compass.getInstance().getDescription().getName();
    }

    @Override
    public void load() {
        Bukkit.getPluginManager().enablePlugin(Compass.getInstance());
    }

    @Override
    public void unload() {
        Bukkit.getPluginManager().disablePlugin(Compass.getInstance());
    }

    @Override
    public void registerCommand() {
        new BW2023SubCommand(instance.getBedWarsCommand(), "compass");
    }

    @Override
    public Object getArenaUtil() {
        return instance.getArenaUtil();
    }

    @Override
    public File getAddonsPath(){
        return instance.getAddonsPath();
    }

    @Override
    public void ClearTrackingArenaMap(){
        trackingArenaMap.clear();
    }

    @Override
    public void removeFromTrackingArenaMapIfEquals(Object victimTeam){
        trackingArenaMap.values().removeIf(victimTeam::equals);
    }

        @Override
    public void removeFromTrackingArenaMap(Object arena){
        trackingArenaMap.remove((IArena) arena);
    }

    @Override
    public boolean containsKeyTrackingArenaMap(Object arena) {
        return trackingArenaMap.containsKey((IArena) arena);
    }

    @Override
    public List<Object> getLanguages() {
        return new ArrayList<>(Language.getLanguages());
    }

    @Override
    public YamlConfiguration getYml(Object language) {
        return ((Language) language).getYml();
    }

    @Override
    public String getIso(Object language) {
        return ((Language) language).getIso();
    }

    @Override
    public void save(Object language) {
        ((Language) language).save();
    }

    @Override
    public Object getPlayerLanguage(Player player) {
        return Language.getPlayerLanguage(player);
    }

    @Override
    public String getMsg(Player player, String path) {
        return Language.getMsg(player, path);
    }

    @Override
    public String getShopIndexNamePath() {
        return Messages.SHOP_INDEX_NAME;
    }

    @Override
    public Object getArenaByPlayer(Player player) {
        return instance.getArenaUtil().getArenaByPlayer(player);
    }

    @Override
    public Object getArenaByTeam(Object team) {
        return ((ITeam) team).getArena();
    }

    @Override
    public boolean isArenaPlaying(Object arena) {
        return ((IArena)arena).getStatus().equals(GameState.playing);
    }

    @Override
    public List<Object> getTeamsByArena(Object arena) {
        return new ArrayList<>(((IArena) arena).getTeams());
    }

    @Override
    public Object getTeamByArena(Object arena, Player player) {
        return ((IArena) arena).getTeam(player);
    }

    @Override
    public List<Player> getPlayers(Object arena) {
        return ((IArena) arena).getPlayers();
    }

    @Override
    public boolean isPlaying(Player player) {
        return instance.getArenaUtil().isPlaying(player);
    }

    @Override
    public boolean isSpectator(Player player) {
        instance.getArenaUtil().isSpectating(player);
        return instance.getArenaUtil().getArenaByPlayer(player).isSpectator(player);
    }

    @Override
    public boolean isTracking(Object arena, UUID uuid) {
        if (trackingArenaMap.containsKey((IArena) arena)) {
            return trackingArenaMap.get((IArena) arena).containsKey(uuid);
        }
        return false;
    }

    @Override
    public void setTrackingTeam(Object arena, UUID uuid, Object team) {
        if (trackingArenaMap.get((IArena) arena) != null) {
            trackingArenaMap.get((IArena) arena).put(uuid, team);
            return;
        }
        HashMap<UUID, Object> map = new HashMap<>();
        map.put(uuid, team);
        trackingArenaMap.put((IArena) arena, map);
    }

    @Override
    public HashMap<UUID, Object> getTrackingTeamMap(Object arena){
        return trackingArenaMap.get((IArena) arena);
    }

    @Override
    public void takeMoney(Player player, Material material, int amount) {
        instance.getShopUtil().takeMoney(player, material , amount);
    }

    @Override
    public Object getTeam(Player player) {
        return instance.getArenaUtil().getArenaByPlayer(player).getTeam(player);
    }

    @Override
    public List<Player> getPlayersForTeam(Object team) {
        if (team == null) return null;
        return ((ITeam) team).getMembers();
    }

    @Override
    public ChatColor getColorForTeam(Object team) {
        return ((ITeam) team).getColor().chat();
    }

    @Override
    public String getTeamDisplayName(Object team, Player player) {
        return ((ITeam) team).getDisplayName(instance.getPlayerLanguage(player));
    }

    @Override
    public boolean isAllBedsDestroyedForTeam(Object arena, Player player) {
        boolean bool = true;
        ITeam team = ((IArena) arena).getTeam(player);
        for (ITeam t : ((IArena)arena).getTeams()) {
            if (t.equals(team)) continue;
            if (t.isBedDestroyed()) continue;
            bool = false;
            break;
        }
        return bool;
    }

    @Override
    public ItemStack colourItem(ItemStack item, Object team) {
        return instance.getVersionSupport().colourItem(item, (ITeam) team);
    }

    @Override
    public void playAction(Player player, String s) {
        instance.getVersionSupport().playAction(player, s);
    }

    @Override
    public void openShop(Player player) {
        instance.getShopUtil().getShopManager().getShop().open(player, instance.getShopUtil().getPlayerQuickBuyCache().getQuickBuyCache(player.getUniqueId()), false);
    }

    @Override
    public ItemStack getItemInHand(Player player) {
        return instance.getVersionSupport().getItemInHand(player);
    }

    @Override
    public String getForCurrentVersion(String s, String s1, String s2){
        return instance.getForCurrentVersion(s, s1, s2);
    }

    @Override
    public String getNmsTag(ItemStack item, String key) {
        return instance.getVersionSupport().getTag(item, key);
    }

    @Override
    public ItemStack setNmsTag(ItemStack item, String key, String value){
        return instance.getVersionSupport().setTag(item,key,value);
    }
}
