package me.mherzaqaryan.compass.util;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import me.mherzaqaryan.compass.CompassPlugin;
import me.mherzaqaryan.compass.data.MainConfig;
import me.mherzaqaryan.compass.data.MessagesData;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MessagingUtil {

    public static void simpleMessage(Player player, ITeam team, String path) {
        player.closeInventory();
        for (Player p : team.getMembers()) {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path));
            VersionUtil.playSound(p, CompassPlugin.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            if (CompassPlugin.isUsingPapi()) {
                p.sendMessage(TextUtil.colorize(PlaceholderAPI.setPlaceholders(player, formatted)));
                continue;
            }
            p.sendMessage(TextUtil.colorize(formatted));
        }
    }

    public static void resourceMessage(Player player, ITeam team, String path, String resourcePath) {
        player.closeInventory();
        for (Player p : team.getMembers()) {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{resource}", yml.getString(MessagesData.PATH + MainConfig.COMMUNICATIONS_MENU_RESOURCES + "." + resourcePath + ".resource-name")));
            VersionUtil.playSound(p, CompassPlugin.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            if (CompassPlugin.isUsingPapi()) {
                p.sendMessage(TextUtil.colorize(PlaceholderAPI.setPlaceholders(player, formatted)));
                continue;
            }
            p.sendMessage(TextUtil.colorize(formatted));
        }
    }

    public static void teamMessage(Player player, ITeam team, String path, ITeam specifiedTeam) {
        player.closeInventory();
        for (Player p : team.getMembers()) {
            Language lang = MessagesData.getLang(p);
            YamlConfiguration yml = lang.getYml();
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{team}", specifiedTeam.getColor().chat() + "§l" + specifiedTeam.getDisplayName(lang)));
            VersionUtil.playSound(p, CompassPlugin.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            if (CompassPlugin.isUsingPapi()) {
                p.sendMessage(TextUtil.colorize(PlaceholderAPI.setPlaceholders(player, formatted)));
                continue;
            }
            p.sendMessage(TextUtil.colorize(formatted));
        }
    }

}
