package club.mher.compass.util;

import club.mher.compass.CompassPlugin;
import club.mher.compass.data.MainConfig;
import club.mher.compass.data.MessagesData;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MessagingUtil {

    public static void simpleMessage(Player player, ITeam team, String path) {
        player.closeInventory();
        team.getMembers().forEach(p -> {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path));
            if (CompassPlugin.isUsingVaultChat()) formatted = CompassPlugin.getVault().setPlaceholders(formatted, player);
            if (CompassPlugin.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, CompassPlugin.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
        });
    }

    public static void resourceMessage(Player player, ITeam team, String path, String resourcePath) {
        player.closeInventory();
        team.getMembers().forEach(p -> {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{resource}", yml.getString(MessagesData.PATH + MainConfig.COMMUNICATIONS_MENU_RESOURCES + "." + resourcePath + ".resource-name")));
            if (CompassPlugin.isUsingVaultChat()) formatted = CompassPlugin.getVault().setPlaceholders(formatted, player);
            if (CompassPlugin.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, CompassPlugin.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
        });
    }

    public static void teamMessage(Player player, ITeam team, String path, ITeam specifiedTeam) {
        player.closeInventory();
        team.getMembers().forEach(p -> {
            Language lang = MessagesData.getLang(p);
            YamlConfiguration yml = lang.getYml();
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{team}", specifiedTeam.getColor().chat() + "§l" + specifiedTeam.getDisplayName(lang)));
            if (CompassPlugin.isUsingVaultChat()) formatted = CompassPlugin.getVault().setPlaceholders(formatted, player);
            if (CompassPlugin.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, CompassPlugin.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
        });
    }

}
