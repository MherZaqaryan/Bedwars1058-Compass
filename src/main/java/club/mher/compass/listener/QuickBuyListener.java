package club.mher.compass.listener;

import club.mher.compass.Compass;
import club.mher.compass.data.MainConfig;
import club.mher.compass.menu.menus.TrackerMenu;
import club.mher.compass.util.NBTItem;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class QuickBuyListener implements Listener {

    @EventHandler
    public void onShop(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        if (!isShop(player, e.getView().getTitle())) {
            return;
        }
        NBTItem item = new NBTItem(Compass.getMainConfig().getItem(player, MainConfig.TRACKER_SHOP, true, "tracker-shop"));
        e.getInventory().setItem(item.getInteger("slot"), item.getItem());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!Compass.getBedWars().getArenaUtil().isPlaying(player)) {
            return;
        }
        if (!isShop(player, e.getView().getTitle())) {
            return;
        }
        ItemStack is = e.getCurrentItem();
        if (is == null || is.getType() == Material.AIR) return;
        String data = new NBTItem(is).getString("data");
        if (data == null || !data.equals("tracker-shop")) {
            return;
        }
        IArena a = Compass.getBedWars().getArenaUtil().getArenaByPlayer(player);
        TrackerMenu tm = new TrackerMenu(player, a);
        tm.setBackToShop(true);
        tm.open();
    }

    private boolean isShop(Player player, String title) {
        return title.equalsIgnoreCase(Language.getMsg(player, Messages.SHOP_INDEX_NAME));
    }

}