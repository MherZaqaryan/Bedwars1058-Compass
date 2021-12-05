package club.mher.compass.tasks;

import club.mher.compass.Compass;
import club.mher.compass.data.MessagesData;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import club.mher.compass.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ActionBarTask extends BukkitRunnable {

    private final IArena arena;

    public ActionBarTask(IArena arena) {
        this.arena = arena;
    }

    @Override
    public void run() {
        if (arena.getPlayers().size() <= 1 || !Compass.getTrackingArenaMap().containsKey(arena) || Compass.getTrackingTeamMap(arena) == null) {
            cancel();
            return;
        }
        for (Map.Entry<UUID, ITeam> teamMap : Compass.getTrackingTeamMap(arena).entrySet()) {
            if (teamMap.getValue() == null) continue;
            Player player = Bukkit.getPlayer(teamMap.getKey());
            if (player == null) continue;
            if (getPlayer(player, teamMap.getValue()) == null) continue;
            player.setCompassTarget(getPlayer(player, teamMap.getValue()).getLocation());
            Compass.getBedWars().getVersionSupport().playAction(player, TextUtil.colorize(MessagesData.getYml(player).getString(MessagesData.ACTION_BAR_TRACKING).replace("{target}", getPlayer(player, teamMap.getValue()).getDisplayName()).replace("{distance}", String.valueOf(getMeters(player, teamMap.getValue())))).replace("{teamColor}", "§"+teamMap.getValue().getColor().chat().getChar()));
        }
    }

    public int getMeters(Player player, ITeam team) {
        if (getSorted(player, team).isEmpty()) return 0;
        return getSorted(player, team).get(0).getValue();
    }

    public Player getPlayer(Player player, ITeam team) {
        if (getSorted(player, team).isEmpty()) return null;
        return getSorted(player, team).get(0).getKey();
    }

    public List<Map.Entry<Player, Integer>> getSorted(Player player, ITeam team) {
        HashMap<Player, Integer> playerDistanceMap = new HashMap<>();
        team.getMembers().forEach(p -> playerDistanceMap.put(p, (int) player.getLocation().distance(p.getLocation())));
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(playerDistanceMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }

}
