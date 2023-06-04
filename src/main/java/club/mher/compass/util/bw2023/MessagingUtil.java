package club.mher.compass.util.bw2023;

import club.mher.compass.Compass;
import club.mher.compass.data.bw2023.MainConfig;
import club.mher.compass.data.bw2023.MessagesData;
import club.mher.compass.support.BW2023;
import club.mher.compass.util.TextUtil;
import club.mher.compass.util.VersionUtil;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MessagingUtil {

    public static void simpleMessage(Player player, ITeam team, String path) {
        player.closeInventory();
        team.getMembers().forEach(p -> {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path));
            if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
            if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, BW2023.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
        });
    }

    public static void resourceMessage(Player player, ITeam team, String path, String resourcePath) {
        player.closeInventory();
        team.getMembers().forEach(p -> {
            YamlConfiguration yml = MessagesData.getYml(p);
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{resource}", yml.getString(MessagesData.PATH + MainConfig.COMMUNICATIONS_MENU_RESOURCES + "." + resourcePath + ".resource-name")));
            if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
            if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, BW2023.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
        });
    }

    public static void teamMessage(Player player, ITeam team, String path, ITeam specifiedTeam) {
        player.closeInventory();
        team.getMembers().forEach(p -> {
            Language lang = MessagesData.getLang(p);
            YamlConfiguration yml = lang.getYml();
            String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{team}", specifiedTeam.getColor().chat() + "§l" + specifiedTeam.getDisplayName(lang)));
            if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
            if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
            p.sendMessage(TextUtil.colorize(formatted));
            VersionUtil.playSound(p, BW2023.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
        });
    }

}
