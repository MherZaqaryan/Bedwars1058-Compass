package club.mher.compass.listener.bw1058;

import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.support.BW1058;
import club.mher.compass.util.bw1058.NBTItem;
import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GameListener implements Listener {

    private final BedWars bedWars;
    public GameListener(BedWars bedWars) {
        this.bedWars = bedWars;
    }

    @EventHandler
    public void onServerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!bedWars.getArenaUtil().isPlaying(player)) return;
        IArena arena = bedWars.getArenaUtil().getArenaByPlayer(player);
        if (!BW1058.isTracking(arena, uuid)) return;
        BW1058.removeTrackingTeam(arena, uuid);
    }

    @EventHandler
    public void onLeave(PlayerLeaveArenaEvent e) {
        IArena arena = e.getArena();
        UUID uuid = e.getPlayer().getUniqueId();
        if (BW1058.isTracking(arena, uuid)) BW1058.removeTrackingTeam(arena, uuid);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (!bedWars.getArenaUtil().isPlaying(player)) return;
        NBTItem nbti = new NBTItem(bedWars, BW1058.getMainConfig().getItem(player, MainConfig.COMPASS_ITEM, true, "compass-item"));
        e.getDrops().remove(nbti.getItem());
    }

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        IArena arena = e.getArena();
        Player victim = e.getVictim();
        UUID victimUniqueId = victim.getUniqueId();
        ITeam victimTeam = arena.getTeam(victim);
        if (BW1058.isTracking(arena, victimUniqueId)) BW1058.removeTrackingTeam(arena, victimUniqueId);
        if (victimTeam.getMembers().size() == 0) BW1058.getTrackingArenaMap().values().removeIf(victimTeam::equals);
    }

    @EventHandler
    public void onRespawn(PlayerReSpawnEvent e) {
        addToInventory(e.getPlayer());
    }

    @EventHandler
    public void onStateChange(GameStateChangeEvent e) {
        IArena arena = e.getArena();
        if (e.getNewState().equals(GameState.playing)) {
            arena.getPlayers().forEach(this::addToInventory);
        }
        else if (e.getNewState().equals(GameState.restarting)) {
            BW1058.removeTrackingArena(arena);
        }
    }

    @EventHandler
    public void onCompassDrop(ItemSpawnEvent e) {
        ItemStack is = e.getEntity().getItemStack();
        if (is == null) return;
        String data = new NBTItem(bedWars, is).getString("data");
        if (data == null) return;
        if (data.equals("compass-item")) e.setCancelled(true);
    }

    public void addToInventory(Player p) {
        NBTItem nbti = new NBTItem(bedWars, BW1058.getMainConfig().getItem(p, MainConfig.COMPASS_ITEM, true, "compass-item"));
        p.getInventory().setItem(nbti.getInteger("slot"), nbti.getItem());
    }

}