package club.mher.compass.support;

import club.mher.compass.data.bw1058.MainConfig;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class BW1058 {
    @Getter private static MainConfig mainConfig;
    @Getter
    private static final HashMap<IArena, HashMap<UUID, ITeam>> trackingArenaMap = new HashMap<>();

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
