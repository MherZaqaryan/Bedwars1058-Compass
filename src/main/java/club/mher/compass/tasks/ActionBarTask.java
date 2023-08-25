package club.mher.compass.tasks;

import club.mher.compass.Compass;
import club.mher.compass.data.MessagesData;
import club.mher.compass.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ActionBarTask extends BukkitRunnable {

    private final Object arena;

    public ActionBarTask(Object arena) {
        this.arena = arena;
    }

    @Override
    public void run() {
        if (Compass.getBedWars().getPlayers(arena).size() <= 1 || !Compass.getBedWars().containsKeyTrackingArenaMap(arena) || Compass.getTrackingTeamMap(arena) == null) {
            this.cancel();
            return;
        }
        for (Map.Entry<UUID, Object> teamMap : Compass.getTrackingTeamMap(arena).entrySet()) {
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
            Compass.getBedWars().playAction(player, TextUtil.colorize(MessagesData.getYml(player).getString(MessagesData.ACTION_BAR_TRACKING).replace("{target}", getPlayer(player, teamMap.getValue()).getDisplayName()).replace("{distance}", String.valueOf(getMeters(player, teamMap.getValue())))).replace("{teamColor}", "§"+ Compass.getBedWars().getColorForTeam(teamMap.getValue()).getChar()));
        }
    }

    public int getMeters(Player player, Object team) {
        List<Map.Entry<Player, Integer>> sorted = getSorted(player, team);
        return sorted.isEmpty() ? 0 : sorted.get(0).getValue();
    }

    public Player getPlayer(Player player, Object team) {
        List<Map.Entry<Player, Integer>> sorted = getSorted(player, team);
        return sorted.isEmpty() ? null : sorted.get(0).getKey();
    }

    public List<Map.Entry<Player, Integer>> getSorted(Player player, Object team) {
        HashMap<Player, Integer> playerDistanceMap = new HashMap<>();
        for (Player p : Compass.getBedWars().getPlayersForTeam(team)) {
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