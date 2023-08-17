package club.mher.compass.support;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface IBedWars {
    void registerCommand();

    Object getArenaUtil();

    File getAddonsPath();

    void ClearTrackingArenaMap();

    void removeFromTrackingArenaMapIfEquals(Object victimTeam);

    void removeFromTrackingArenaMap(Object arena);

    boolean containsKeyTrackingArenaMap(Object arena);

    List<Object> getLanguages();
    YamlConfiguration getYml(Object language);
    String getIso(Object language);
    void save(Object language);
    Object getPlayerLanguage(Player player);
    String getMsg(Player player, String path);
    String getShopIndexNamePath();

    Object getArenaByPlayer(Player player);
    Object getArenaByTeam(Object team);
    boolean isArenaPlaying(Object arena);
    List<Object> getTeamsByArena(Object arena);
    Object getTeamByArena(Object arena, Player player);

    List<Player> getPlayers(Object arena);

    boolean isPlaying(Player player);
    boolean isSpectator(Player player);
    boolean isTracking(Object arena, UUID uuid);

    void setTrackingTeam(Object arena, UUID uuid, Object team);

    HashMap<UUID, Object> getTrackingTeamMap(Object arena);

    void takeMoney(Player player, Material material, int amount);

    Object getTeam(Player player);
    List<Player> getPlayersForTeam(Object team);
    ChatColor getColorForTeam(Object team);

    String getTeamDisplayName(Object team, Player player);

    boolean isAllBedsDestroyedForTeam(Object arena, Player player);
    ItemStack colourItem(ItemStack item, Object team);

    void playAction(Player player, String s);
    void openShop(Player player);
    ItemStack getItemInHand(Player player);

    String getForCurrentVersion(String s, String s1, String s2);

    String getNmsTag(ItemStack item, String key);

    ItemStack setNmsTag(ItemStack item, String key, String value);
}
