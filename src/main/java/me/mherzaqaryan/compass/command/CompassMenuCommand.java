package me.mherzaqaryan.compass.command;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import me.mherzaqaryan.compass.CompassPlugin;
import me.mherzaqaryan.compass.menu.menus.MainMenu;
import me.mherzaqaryan.compass.util.TextUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CompassMenuCommand extends SubCommand {

    public CompassMenuCommand(ParentCommand parent, String name) {
        super(parent, name);
        showInList(true);
        setDisplayInfo(TextUtil.msgHoverClick("&6▪ &7/bw compass &8- &eTo see commands list", "Click to see all list of commands", "/bw compass", ClickEvent.Action.RUN_COMMAND));
        setPriority(14);
        setArenaSetupCommand(false);
    }

    @Override
    public boolean execute(String[] args, CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (!player.hasPermission("bw.compass.reload")) return true;
                player.spigot().sendMessage(TextUtil.msgHoverClick("&6BedWars1058 Compass Addon " + CompassPlugin.PLUGIN_VERSION + " &7- &cAdmin Commands", "&7Author: &cMrIronMan", "https://www.spigotmc.org/resources/91537/", ClickEvent.Action.OPEN_URL));
                player.sendMessage("");
                player.spigot().sendMessage(TextUtil.msgHoverClick(" &6▪ &7/bw compass menu &8- &eTo open compass menu", "&7Click to open the compass menu.", "/bw compass menu", ClickEvent.Action.RUN_COMMAND));
                player.spigot().sendMessage(TextUtil.msgHoverClick(" &6▪ &7/bw compass reload &8- &eTo reload compass configuration", "&7Click to reload configuration file\n&8Path: plugins/BedWars1058/Compass/config.yml", "/bw compass reload", ClickEvent.Action.RUN_COMMAND));
            }else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("menu")) {
                    if (!CompassPlugin.getBedWars().getArenaUtil().isPlaying(player)) return true;
                    IArena arena = CompassPlugin.getBedWars().getArenaUtil().getArenaByPlayer(player);
                    if (!arena.getStatus().equals(GameState.playing)) return true;
                    if (arena.isSpectator(player)) return true;
                    new MainMenu(player).open();
                }
                else if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("bw.compass.reload")) {
                        CompassPlugin.getConfigData().reload();
                        player.sendMessage(TextUtil.colorize("&aConfiguration file has been reloaded."));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return Arrays.asList("menu", "reload", CompassPlugin.PLUGIN_VERSION);
    }

}
