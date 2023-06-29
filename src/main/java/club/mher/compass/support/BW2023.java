package club.mher.compass.support;

import club.mher.compass.Compass;
import com.tomkeuper.bedwars.api.addon.Addon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BW2023 extends Addon {

    public static BW2023 instance;

    public BW2023() {
        instance = this;
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

}
