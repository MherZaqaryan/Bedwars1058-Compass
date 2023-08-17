package club.mher.compass.listener;

import club.mher.compass.Compass;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.util.NBTItem;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerKillEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.tomkeuper.bedwars.api.events.player.PlayerReSpawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GameListenerBW2023 implements Listener {

    @EventHandler
    public void onServerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!Compass.getBedWars().isPlaying(player)) return;
        Object arena = Compass.getBedWars().getArenaByPlayer(player);
        if (!Compass.isTracking(arena, uuid)) return;
        Compass.removeTrackingTeam(arena, uuid);
    }

    @EventHandler
    public void onLeave(PlayerLeaveArenaEvent e) {
        IArena arena = e.getArena();
        UUID uuid = e.getPlayer().getUniqueId();
        if (Compass.isTracking(arena, uuid)) Compass.removeTrackingTeam(arena, uuid);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (!Compass.getBedWars().isPlaying(player)) return;
        NBTItem nbti = new NBTItem(Compass.getMainConfig().getItem(player, BW1058MainConfig.COMPASS_ITEM, true, "compass-item"));
        e.getDrops().remove(nbti.getItem());
    }

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        IArena arena = e.getArena();
        Player victim = e.getVictim();
        UUID victimUniqueId = victim.getUniqueId();
        ITeam victimTeam = arena.getTeam(victim);
        if (Compass.isTracking(arena, victimUniqueId)) Compass.removeTrackingTeam(arena, victimUniqueId);
        if (victimTeam.getMembers().size() == 0) Compass.removeTrackingArenaMapIfEquals(victimTeam);
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
            Compass.removeTrackingArena(arena);
        }
    }

    @EventHandler
    public void onCompassDrop(ItemSpawnEvent e) {
        ItemStack is = e.getEntity().getItemStack();
        if (is == null) return;
        String data = new NBTItem(is).getString("data");
        if (data == null) return;
        if (data.equals("compass-item")) e.setCancelled(true);
    }

    public void addToInventory(Player p) {
        NBTItem nbti = new NBTItem(Compass.getMainConfig().getItem(p, BW1058MainConfig.COMPASS_ITEM, true, "compass-item"));
        p.getInventory().setItem(nbti.getInteger("slot"), nbti.getItem());
    }

}