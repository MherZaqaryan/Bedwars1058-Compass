package club.mher.compass;

import club.mher.compass.support.bstats.Metrics;
import club.mher.compass.support.vault.VaultSupport;
import lombok.Getter;
import club.mher.compass.data.bw1058.MessagesData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;

public class Compass extends JavaPlugin {

    @Getter private static Compass instance;
    @Getter private static boolean isUsingVaultChat = false, isUsingPapi = false;
    @Getter private static VaultSupport vault;

    public static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
    public static String PLUGIN_VERSION;

    @Override
    public void onEnable() {
        instance = this;
        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars1058") && !Bukkit.getPluginManager().isPluginEnabled("BedWars2023") ) {
            getLogger().severe("BedWars1058 or BedWars2023 was not found. Disabling...");
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
            com.andrei1058.bedwars.api.BedWars bedWars;
            bedWars = Bukkit.getServicesManager().getRegistration(com.andrei1058.bedwars.api.BedWars.class).getProvider();
            new club.mher.compass.command.bw1058.CompassMenuCommand(bedWars,bedWars.getBedWarsCommand(), "compass");
            club.mher.compass.data.bw1058.MainConfig mainConfig = new club.mher.compass.data.bw1058.MainConfig(this, "config", bedWars.getAddonsPath().getPath()+File.separator+"Compass", bedWars);
            mainConfig.reload();
            Arrays.asList(new club.mher.compass.listener.bw1058.MenuListener(bedWars), new club.mher.compass.listener.bw1058.GameListener(bedWars), new club.mher.compass.listener.bw1058.QuickBuyListener(bedWars)).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
        }
        if (Bukkit.getPluginManager().isPluginEnabled("BedWars2023")){
            //todo switch classes
            com.tomkeuper.bedwars.api.BedWars bedWars;
            bedWars = Bukkit.getServicesManager().getRegistration(com.tomkeuper.bedwars.api.BedWars.class).getProvider();
            new club.mher.compass.command.bw2023.CompassMenuCommand(bedWars,bedWars.getBedWarsCommand(), "compass", bedWars);
            club.mher.compass.data.bw2023.MainConfig mainConfig = new club.mher.compass.data.bw2023.MainConfig(this, "config", bedWars.getAddonsPath().getPath()+File.separator+"Compass", bedWars);
            mainConfig.reload();
            Arrays.asList(new club.mher.compass.listener.bw2023.MenuListener(bedWars), new club.mher.compass.listener.bw2023.GameListener(bedWars), new club.mher.compass.listener.bw2023.QuickBuyListener(bedWars)).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
        }

        new MessagesData();
//        Arrays.asList(new MenuListener(), new GameListener(), new QuickBuyListener()).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
        getLogger().log(Level.INFO, "Successfully loaded in " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
//        trackingArenaMap.clear();
    }
}
