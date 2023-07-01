package club.mher.compass.support;

import club.mher.compass.data.IMainConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

public class Misc {
    @Getter
    @Setter
    private static IMainConfig mainConfig;
    @Getter
    private static final HashMap<Object, HashMap<UUID, Object>> trackingArenaMap = new HashMap<>();

    public static void setTrackingTeam(Object arena, UUID uuid, Object team) {
        if (trackingArenaMap.get(arena) != null) {
            getTrackingTeamMap(arena).put(uuid, team);
            return;
        }
        HashMap<UUID, Object> map = new HashMap<>();
        map.put(uuid, team);
        trackingArenaMap.put(arena, map);
    }

    public static boolean isTracking(Object arena, UUID uuid) {
        if (trackingArenaMap.containsKey(arena)) {
            return trackingArenaMap.get(arena).containsKey(uuid);
        }
        return false;
    }

    public static HashMap<UUID, Object> getTrackingTeamMap(Object arena) {
        return trackingArenaMap.get(arena);
    }

    public static Object getTrackingTeam(Object arena, UUID uuid) {
        return trackingArenaMap.get(arena).get(uuid);
    }

    public static void removeTrackingTeam(Object arena, UUID uuid) {
        trackingArenaMap.get(arena).remove(uuid);
    }

    public static void removeTrackingArena(Object arena) {
        trackingArenaMap.remove(arena);
    }


}
