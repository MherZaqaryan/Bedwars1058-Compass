package me.mherzaqaryan.compass.listener;

import de.tr7zw.nbtapi.NBTItem;
import me.mherzaqaryan.compass.CompassPlugin;
import me.mherzaqaryan.compass.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().toString().contains("AIR")) return;
        NBTItem nbtItem = new NBTItem(e.getCurrentItem());
        if (nbtItem.getString("data").equals("compass-item"))  {
            if (e.getView().getTopInventory().getType().equals(InventoryType.CRAFTING) && e.getView().getBottomInventory().getType().equals(InventoryType.PLAYER)) return;
            e.setCancelled(true);
        }
        if (!(holder instanceof Menu)) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
        Menu menu = (Menu) holder;
        menu.handleMenu(e);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (CompassPlugin.getBedWars().getVersionSupport().getItemInHand(player) == null) return;
        if (CompassPlugin.getBedWars().getVersionSupport().getItemInHand(player).getType().toString().contains("AIR")) return;
        NBTItem nbtItem = new NBTItem(CompassPlugin.getBedWars().getVersionSupport().getItemInHand(player));
        if (!nbtItem.getString("data").equals("compass-item")) return;
        Bukkit.dispatchCommand(player, "bw compass menu");
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getCursor() == null) return;
        if (e.getCursor().getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(e.getCursor());
        if (nbtItem.getString("data").equals("compass-item")) e.setCancelled(true);
    }

}
