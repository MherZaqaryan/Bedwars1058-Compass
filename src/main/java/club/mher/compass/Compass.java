package club.mher.compass;

import club.mher.compass.listener.QuickBuyListener;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import lombok.Getter;
import club.mher.compass.command.CompassMenuCommand;
import club.mher.compass.data.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.listener.GameListener;
import club.mher.compass.listener.MenuListener;
import club.mher.compass.support.VaultSupport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class Compass extends JavaPlugin {

    @Getter private static Compass instance;
    @Getter private static BedWars bedWars;
    @Getter private static MainConfig mainConfig;
    @Getter private static final HashMap<IArena, HashMap<UUID, ITeam>> trackingArenaMap = new HashMap<>();
    @Getter private static boolean isUsingVaultChat = false, isUsingPapi = false;
    @Getter private static VaultSupport vault;

    public static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
    public static String PLUGIN_VERSION;

    @Override
    public void onEnable() {
        instance = this;
        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars1058")) {
            getLogger().severe("BedWars1058 was not found. Disabling...");
            setEnabled(false);
            return;
        }
        long start = System.currentTimeMillis();
        new Metrics(this, 11186);
        PLUGIN_VERSION = getDescription().getVersion();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) isUsingPapi = true;
        vault = new VaultSupport();
        isUsingVaultChat = vault.setupChat();
        bedWars = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        new CompassMenuCommand(bedWars.getBedWarsCommand(), "compass");
        mainConfig = new MainConfig(this, "config", bedWars.getAddonsPath().getPath()+File.separator+"Compass");
        mainConfig.reload();
        new MessagesData();
        Arrays.asList(new MenuListener(), new GameListener(), new QuickBuyListener()).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
        getLogger().log(Level.INFO, "Successfully loaded in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        trackingArenaMap.clear();
    }

    public static void setTrackingTeam(IArena arena, UUID uuid, ITeam team) {
        if (trackingArenaMap.get(arena) != null) {
            getTrackingTeamMap(arena).put(uuid, team);
            return;
        }
        HashMap<UUID, ITeam> map = new HashMap<>();
        map.put(uuid, team);
        trackingArenaMap.put(arena, map);
    }

    public static boolean isTracking(IArena arena, UUID uuid) {
        if (trackingArenaMap.containsKey(arena)) {
            return trackingArenaMap.get(arena).containsKey(uuid);
        }
        return false;
    }

    public static HashMap<UUID, ITeam> getTrackingTeamMap(IArena arena) {
        return trackingArenaMap.get(arena);
    }

    public static ITeam getTrackingTeam(IArena arena, UUID uuid) {
        return trackingArenaMap.get(arena).get(uuid);
    }

    public static void removeTrackingTeam(IArena arena, UUID uuid) {
        trackingArenaMap.get(arena).remove(uuid);
    }

    public static void removeTrackingArena(IArena arena) {
        trackingArenaMap.remove(arena);
    }

}
