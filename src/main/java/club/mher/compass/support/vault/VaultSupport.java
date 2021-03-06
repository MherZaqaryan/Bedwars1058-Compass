package club.mher.compass.support.vault;

import club.mher.compass.Compass;
import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultSupport {

    @Getter private Chat chat = null;

    public boolean setupChat() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return false;
        Compass.getInstance().getLogger().info("Hook into Vault chat support");
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) return false;
        chat = rsp.getProvider();
        return chat != null;
    }

    public String setPlaceholders(String s, Player p) {
        if (chat == null) return s;
        return s.replace("{vPrefix}", chat.getPlayerPrefix(p)).replace("{vSuffix}", chat.getPlayerSuffix(p));
    }

}
