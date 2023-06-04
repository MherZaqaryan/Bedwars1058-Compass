package club.mher.compass.listener.bw2023;

import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.menu.menus.bw2023.TrackerMenu;
import club.mher.compass.support.BW2023;
import club.mher.compass.util.bw2023.NBTItem;
import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class QuickBuyListener implements Listener {

    private final BedWars bedWars;

    public QuickBuyListener(BedWars bedWars) {
        this.bedWars = bedWars;
    }

    @EventHandler
    public void onShop(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        if (!isShop(player, e.getView().getTitle())) {
            return;
        }
        NBTItem item = new NBTItem(bedWars, BW2023.getMainConfig().getItem(player, MainConfig.TRACKER_SHOP, true, "tracker-shop"));
        e.getInventory().setItem(item.getInteger("slot"), item.getItem());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!bedWars.getArenaUtil().isPlaying(player)) {
            return;
        }
        if (!isShop(player, e.getView().getTitle())) {
            return;
        }
        ItemStack is = e.getCurrentItem();
        if (is == null || is.getType() == Material.AIR) return;
        String data = new NBTItem(bedWars, is).getString("data");
        if (data == null || !data.equals("tracker-shop")) {
            return;
        }
        IArena a = bedWars.getArenaUtil().getArenaByPlayer(player);
        TrackerMenu tm = new TrackerMenu(bedWars, player, a);
        tm.setBackToShop(true);
        tm.open();
    }

    private boolean isShop(Player player, String title) {
        return title.equalsIgnoreCase(Language.getMsg(player, Messages.SHOP_INDEX_NAME));
    }

}