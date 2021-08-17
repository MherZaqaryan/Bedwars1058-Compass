package me.mherzaqaryan.compass.listener;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import de.tr7zw.nbtapi.NBTItem;
import me.mherzaqaryan.compass.CompassPlugin;
import me.mherzaqaryan.compass.data.ConfigData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class GameListener implements Listener {

    @EventHandler
    public void onServerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!CompassPlugin.getBedWars().getArenaUtil().isPlaying(player)) return;
        IArena arena = CompassPlugin.getBedWars().getArenaUtil().getArenaByPlayer(player);
        if (!CompassPlugin.isTracking(arena, uuid)) return;
        CompassPlugin.removeTrackingTeam(arena, uuid);
    }

    @EventHandler
    public void onLeave(PlayerLeaveArenaEvent e) {
        IArena arena = e.getArena();
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (CompassPlugin.isTracking(arena, uuid)) CompassPlugin.removeTrackingTeam(arena, uuid);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (!CompassPlugin.getBedWars().getArenaUtil().isPlaying(player)) return;
        NBTItem nbti = new NBTItem(CompassPlugin.getConfigData().getItem(player, ConfigData.COMPASS_ITEM, true, "compass-item"));
        e.getDrops().remove(nbti.getItem());
    }

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        IArena arena = e.getArena();
        Player victim = e.getVictim();
        UUID victimUniqueId = victim.getUniqueId();
        ITeam victimTeam = arena.getTeam(victim);
        if (CompassPlugin.isTracking(arena, victimUniqueId)) CompassPlugin.removeTrackingTeam(arena, victimUniqueId);
        if (victimTeam.getMembers().size() == 0) CompassPlugin.getTrackingArenaMap().values().removeIf(victimTeam::equals);
    }

    @EventHandler
    public void onRespawn(PlayerReSpawnEvent e) {
        Player player = e.getPlayer();
        addToInventory(player);
    }

    @EventHandler
    public void onStateChange(GameStateChangeEvent e) {
        IArena arena = e.getArena();
        if (e.getNewState().equals(GameState.playing)) {
            arena.getPlayers().forEach(this::addToInventory);
        }
        else if (e.getNewState().equals(GameState.restarting)) {
            if (!CompassPlugin.getTrackingArenaMap().containsKey(arena)) return;
            CompassPlugin.removeTrackingArena(arena);
        }
    }

    @EventHandler
    public void onCompassDrop(ItemSpawnEvent e) {
        ItemStack itemStack = e.getEntity().getItemStack();
        if (itemStack == null) return;
        NBTItem nbtItem = new NBTItem(itemStack);
        if (nbtItem.getString("data").equals("compass-item")) e.setCancelled(true);
    }

    public void addToInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        NBTItem nbti = new NBTItem(CompassPlugin.getConfigData().getItem(player, ConfigData.COMPASS_ITEM, true, "compass-item"));
        inventory.setItem(nbti.getInteger("slot"), nbti.getItem());
    }

}
