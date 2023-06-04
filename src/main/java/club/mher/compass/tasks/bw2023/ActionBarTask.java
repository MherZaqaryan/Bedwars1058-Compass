package club.mher.compass.tasks.bw2023;

import club.mher.compass.data.bw1058.MessagesData;
import club.mher.compass.support.BW2023;
import club.mher.compass.util.TextUtil;
import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ActionBarTask extends BukkitRunnable {

    private final BedWars bedWars;
    private final IArena arena;

    public ActionBarTask(BedWars bedWars, IArena arena) {
        this.bedWars = bedWars;
        this.arena = arena;
    }

    @Override
    public void run() {
        if (arena.getPlayers().size() <= 1 || !BW2023.getTrackingArenaMap().containsKey(arena) || BW2023.getTrackingTeamMap(arena) == null) {
            this.cancel();
            return;
        }
        for (Map.Entry<UUID, ITeam> teamMap : BW2023.getTrackingTeamMap(arena).entrySet()) {
            if (teamMap.getValue() == null) {
                continue;
            }
            Player player = Bukkit.getPlayer(teamMap.getKey());
            if (player == null) {
                continue;
            }
            if (getPlayer(player, teamMap.getValue()) == null) {
                continue;
            }
            player.setCompassTarget(getPlayer(player, teamMap.getValue()).getLocation());
            bedWars.getVersionSupport().playAction(player, TextUtil.colorize(MessagesData.getYml(player).getString(MessagesData.ACTION_BAR_TRACKING).replace("{target}", getPlayer(player, teamMap.getValue()).getDisplayName()).replace("{distance}", String.valueOf(getMeters(player, teamMap.getValue())))).replace("{teamColor}", "§"+teamMap.getValue().getColor().chat().getChar()));
        }
    }

    public int getMeters(Player player, ITeam team) {
        List<Map.Entry<Player, Integer>> sorted = getSorted(player, team);
        return sorted.isEmpty() ? 0 : sorted.get(0).getValue();
    }

    public Player getPlayer(Player player, ITeam team) {
        List<Map.Entry<Player, Integer>> sorted = getSorted(player, team);
        return sorted.isEmpty() ? null : sorted.get(0).getKey();
    }

    public List<Map.Entry<Player, Integer>> getSorted(Player player, ITeam team) {
        HashMap<Player, Integer> playerDistanceMap = new HashMap<>();
        for (Player p : team.getMembers()) {
            if (!player.getWorld().equals(p.getWorld())) {
                continue;
            }
            playerDistanceMap.put(p, (int) player.getLocation().distance(p.getLocation()));
        }
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(playerDistanceMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }

}