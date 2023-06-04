package club.mher.compass.listener.bw1058;

import club.mher.compass.menu.Menu;
import club.mher.compass.util.bw1058.NBTItem;
import com.andrei1058.bedwars.api.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {
    private final BedWars bedWars;

    public MenuListener(BedWars bedWars) {
        this.bedWars = bedWars;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }
    }

    @EventHandler
    public void onCompass(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
        NBTItem nbtItem = new NBTItem(bedWars, e.getCurrentItem());
        if (nbtItem.getString("data") == null) return;
        if (!nbtItem.getString("data").equals("compass-item")) return;
        if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
            e.setCancelled(true);
            return;
        }
        if (e.getView().getTopInventory().getType() == InventoryType.CRAFTING && e.getView().getBottomInventory().getType() == InventoryType.PLAYER) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = bedWars.getVersionSupport().getItemInHand(player);
        if (item == null) return;
        if (item.getType() == Material.AIR) return;
        String data = new NBTItem(bedWars, item).getString("data");
        if (data == null) return;
        if (!data.equals("compass-item")) return;
        Bukkit.dispatchCommand(player, "bw compass menu");
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getCursor() == null) return;
        if (e.getCursor().getType() == Material.AIR) return;
        String data = new NBTItem(bedWars, e.getCursor()).getString("data");
        if (data == null) return;
        if (data.equals("compass-item")) e.setCancelled(true);
    }

}
