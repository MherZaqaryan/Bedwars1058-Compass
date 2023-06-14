package club.mher.compass.tasks;

import club.mher.compass.Compass;
import club.mher.compass.data.MessagesData;
import club.mher.compass.support.Misc;
import club.mher.compass.util.TextUtil;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ActionBarTask extends BukkitRunnable {

    private final Object bedWars;
    private final Object arena;

    public ActionBarTask(Object bedWars, Object arena) {
        this.bedWars = bedWars;
        this.arena = arena;
    }

    @Override
    public void run() {
        if (Compass.HOOK_NAME.equals("BW1058")){
            if (((IArena) arena).getPlayers().size() <= 1 || !Misc.getTrackingArenaMap().containsKey(arena) || Misc.getTrackingTeamMap(arena) == null) {
                this.cancel();
                return;
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")){
            if (((com.tomkeuper.bedwars.api.arena.IArena) arena).getPlayers().size() <= 1 || !Misc.getTrackingArenaMap().containsKey(arena) || Misc.getTrackingTeamMap(arena) == null) {
                this.cancel();
                return;
            }
        }
        for (Map.Entry<UUID, Object> teamMap : Misc.getTrackingTeamMap(arena).entrySet()) {
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
            if (Compass.HOOK_NAME.equals("BW1058")) ((BedWars) bedWars).getVersionSupport().playAction(player, TextUtil.colorize(MessagesData.getYml(player).getString(MessagesData.ACTION_BAR_TRACKING).replace("{target}", getPlayer(player, teamMap.getValue()).getDisplayName()).replace("{distance}", String.valueOf(getMeters(player, teamMap.getValue())))).replace("{teamColor}", "§"+((ITeam)teamMap.getValue()).getColor().chat().getChar()));
            if (Compass.HOOK_NAME.equals("BW2023")) ((com.tomkeuper.bedwars.api.BedWars) bedWars).getVersionSupport().playAction(player, TextUtil.colorize(MessagesData.getYml(player).getString(MessagesData.ACTION_BAR_TRACKING).replace("{target}", getPlayer(player, teamMap.getValue()).getDisplayName()).replace("{distance}", String.valueOf(getMeters(player, teamMap.getValue())))).replace("{teamColor}", "§"+((com.tomkeuper.bedwars.api.arena.team.ITeam)teamMap.getValue()).getColor().chat().getChar()));
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
        if (Compass.HOOK_NAME.equals("BW1058")){
            for (Player p : ((ITeam) team).getMembers()) {
                if (!player.getWorld().equals(p.getWorld())) {
                    continue;
                }
                playerDistanceMap.put(p, (int) player.getLocation().distance(p.getLocation()));
            }
        } else if (Compass.HOOK_NAME.equals("BW2023")){
            for (Player p : ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getMembers()) {
                if (!player.getWorld().equals(p.getWorld())) {
                    continue;
                }
                playerDistanceMap.put(p, (int) player.getLocation().distance(p.getLocation()));
            }
        }
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(playerDistanceMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }

}