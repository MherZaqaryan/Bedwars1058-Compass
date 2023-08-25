package club.mher.compass.command;

import club.mher.compass.Compass;
import club.mher.compass.menu.menus.MainMenu;
import club.mher.compass.util.TextUtil;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class BW1058SubCommand extends SubCommand {

    public BW1058SubCommand(ParentCommand parent, String name) {
        super(parent, name);
        showInList(true);
        setDisplayInfo(TextUtil.msgHoverClick(" &6▪ &7/bw compass &8- &eTo see commands list", "Click to see all list of commands", "/bw compass", ClickEvent.Action.RUN_COMMAND));
        setPriority(14);
        setArenaSetupCommand(false);
    }

    @Override
    public boolean execute(String[] args, CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (!player.hasPermission("bw.compass.reload")) return true;
                Arrays.asList(
                        TextUtil.msgHoverClick("&8&lþ &6BedWars1058 Compass v" + Compass.PLUGIN_VERSION + " &7- &cAdmin Commands", "&7Author: &cMher Zaqaryan", "https://www.spigotmc.org/resources/91537/", ClickEvent.Action.OPEN_URL),
                        TextUtil.blank(),
                        TextUtil.msgHoverClick(" &6▪ &7/bw compass menu &8- &eTo open compass menu", "&7Click to open the compass menu.", "/bw compass menu", ClickEvent.Action.RUN_COMMAND),
                        TextUtil.msgHoverClick(" &6▪ &7/bw compass reload &8- &eTo reload compass configuration", "&7Click to reload configuration file\n&8Path: plugins/BedWars1058/Compass/config.yml", "/bw compass reload", ClickEvent.Action.RUN_COMMAND)
                ).forEach(player.spigot()::sendMessage);
            }else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("menu")) {
                    if (!((BedWars.ArenaUtil) Compass.getBedWars().getArenaUtil()).isPlaying(player)) return true;
                    IArena arena = ((BedWars.ArenaUtil) Compass.getBedWars().getArenaUtil()).getArenaByPlayer(player);
                    if (!arena.getStatus().equals(GameState.playing)) return true;
                    if (arena.isSpectator(player)) return true;
                    new MainMenu(player).open();
                }
                else if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("bw.compass.reload")) {
                        Compass.getMainConfig().reload();
                        player.sendMessage(TextUtil.colorize("&aConfiguration file has been reloaded."));
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
