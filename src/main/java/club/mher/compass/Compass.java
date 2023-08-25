package club.mher.compass;

import club.mher.compass.data.BW2023MainConfig;
import club.mher.compass.data.MainConfig;
import club.mher.compass.listener.GameListenerBW2023;
import club.mher.compass.listener.QuickBuyListener;
import club.mher.compass.support.BW1058;
import club.mher.compass.support.BW2023;
import club.mher.compass.support.IBedWars;
import club.mher.compass.support.bstats.Metrics;
import lombok.Getter;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.listener.GameListenerBW1058;
import club.mher.compass.listener.MenuListener;
import club.mher.compass.support.vault.VaultSupport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class Compass extends JavaPlugin {

    @Getter private static Compass instance;
    @Getter private static IBedWars bedWars;
    @Getter private static MainConfig mainConfig;
    @Getter private static boolean isUsingVaultChat = false, isUsingPapi = false;
    @Getter private static VaultSupport vault;

    public static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
    public static String PLUGIN_VERSION;

    @Override
    public void onEnable() {
        instance = this;
        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars1058") && !Bukkit.getPluginManager().isPluginEnabled("BedWars2023") ) {
            getLogger().severe("BedWars1058 or BedWars2023  was not found. Disabling...");
            setEnabled(false);
            return;
        }
        long start = System.currentTimeMillis();
        new Metrics(this, 11186);
        PLUGIN_VERSION = getDescription().getVersion();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) isUsingPapi = true;
        vault = new VaultSupport();
        isUsingVaultChat = vault.setupChat();
        if (Bukkit.getPluginManager().isPluginEnabled("BedWars1058")){
            bedWars = new BW1058();
            mainConfig = new BW1058MainConfig(this, "config", bedWars.getAddonsPath().getPath()+File.separator+"Compass");
            Bukkit.getPluginManager().registerEvents(new GameListenerBW1058(), this);
        } else if (Bukkit.getPluginManager().isPluginEnabled("BedWars2023")){
            bedWars = new BW2023();
            mainConfig = new BW2023MainConfig(this, "config", bedWars.getAddonsPath().getPath()+File.separator+"Compass");
            Bukkit.getPluginManager().registerEvents(new GameListenerBW2023(), this);
        }
        bedWars.registerCommand();
        mainConfig.reload();
        new MessagesData();
        Arrays.asList(new MenuListener(), new QuickBuyListener()).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
        getLogger().log(Level.INFO, "Successfully loaded in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        bedWars.ClearTrackingArenaMap();
    }

    public static void setTrackingTeam(Object arena, UUID uuid, Object team) {
        bedWars.setTrackingTeam(arena, uuid, team);
    }

    public static boolean isTracking(Object arena, UUID uuid) {
        return bedWars.isTracking(arena,uuid);
    }

    public static HashMap<UUID, Object> getTrackingTeamMap(Object arena) {
        return bedWars.getTrackingTeamMap(arena);
    }

    public static Object getTrackingTeam(Object arena, UUID uuid) {
        return getTrackingTeamMap(arena).get(uuid);
    }

    public static void removeTrackingTeam(Object arena, UUID uuid) {
        getTrackingTeamMap(arena).remove(uuid);
    }

    public static void removeTrackingArena(Object arena) {
        bedWars.removeFromTrackingArenaMap(arena);
    }

    public static void removeTrackingArenaMapIfEquals(Object victimTeam){
        bedWars.removeFromTrackingArenaMapIfEquals(victimTeam);
    }

}
