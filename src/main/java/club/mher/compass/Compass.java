package club.mher.compass;

import club.mher.compass.command.bw1058.CompassMenuCommand;
import club.mher.compass.data.IMainConfig;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.listener.bw1058.GameListener;
import club.mher.compass.listener.MenuListener;
import club.mher.compass.listener.QuickBuyListener;
import club.mher.compass.support.BW2023;
import club.mher.compass.support.Misc;
import club.mher.compass.support.bstats.Metrics;
import club.mher.compass.support.vault.VaultSupport;
import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.addon.Addon;
import lombok.Getter;
import club.mher.compass.data.MessagesData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
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
    public static String HOOK_NAME;
    public static IMainConfig config;

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
            HOOK_NAME = "BW1058";
            com.andrei1058.bedwars.api.BedWars bedWars;
            bedWars = Bukkit.getServicesManager().getRegistration(com.andrei1058.bedwars.api.BedWars.class).getProvider();
            new CompassMenuCommand(bedWars,bedWars.getBedWarsCommand(), "compass");
            config = new MainConfig(this, "config", bedWars.getAddonsPath().getPath()+File.separator+"Compass", bedWars);
            config.reload();
            Misc.setMainConfig(config);
            Arrays.asList(new MenuListener(bedWars), new GameListener(bedWars), new QuickBuyListener(bedWars)).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
        }
        if (Bukkit.getPluginManager().isPluginEnabled("BedWars2023")){
            HOOK_NAME = "BW2023";
            com.tomkeuper.bedwars.api.BedWars bedWars;
            bedWars = Bukkit.getServicesManager().getRegistration(com.tomkeuper.bedwars.api.BedWars.class).getProvider();
            new club.mher.compass.command.bw2023.CompassMenuCommand(bedWars,bedWars.getBedWarsCommand(), "compass");
            config = new club.mher.compass.data.bw2023.MainConfig(this, "config", bedWars.getAddonsPath().getPath()+File.separator+"Compass", bedWars);
            config.reload();
            Misc.setMainConfig(config);
            Arrays.asList(new MenuListener(bedWars), new club.mher.compass.listener.bw2023.GameListener(bedWars), new QuickBuyListener(bedWars)).forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));

            if (BW2023.instance == null) bedWars.getAddonsUtil().registerAddon(new BW2023());

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
