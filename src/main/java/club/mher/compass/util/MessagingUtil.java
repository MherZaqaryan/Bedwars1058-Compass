package club.mher.compass.util;

import club.mher.compass.Compass;
import club.mher.compass.data.BW1058MainConfig;
import club.mher.compass.data.MessagesData;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MessagingUtil {

    public static void simpleMessage(Player player, Object team, String path) {
        player.closeInventory();
        Compass.getBedWars().getPlayersForTeam(team).forEach(p -> {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path));
            if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
            if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, Compass.getMainConfig().getString(BW1058MainConfig.MESSAGE_SEND_SOUND));
        });
    }

    public static void resourceMessage(Player player, Object team, String path, String resourcePath) {
        player.closeInventory();
        Compass.getBedWars().getPlayersForTeam(team).forEach(p -> {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{resource}", yml.getString(MessagesData.PATH + BW1058MainConfig.COMMUNICATIONS_MENU_RESOURCES + "." + resourcePath + ".resource-name")));
            if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
            if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, Compass.getMainConfig().getString(BW1058MainConfig.MESSAGE_SEND_SOUND));
        });
    }

    public static void teamMessage(Player player, Object team, String path, Object specifiedTeam) {
        player.closeInventory();
        Compass.getBedWars().getPlayersForTeam(team).forEach(p -> {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{team}", Compass.getBedWars().getColorForTeam(specifiedTeam) + "§l" + Compass.getBedWars().getTeamDisplayName(specifiedTeam, p)));
            if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
            if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, Compass.getMainConfig().getString(BW1058MainConfig.MESSAGE_SEND_SOUND));
        });
    }

}
