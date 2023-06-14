package club.mher.compass.util;

import club.mher.compass.Compass;
import club.mher.compass.data.bw1058.MainConfig;
import club.mher.compass.data.MessagesData;
import club.mher.compass.support.Misc;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MessagingUtil {

    public static void simpleMessage(Player player, Object team, String path) {
        player.closeInventory();
        if (Compass.HOOK_NAME.equals("BW1058")){
            ((ITeam) team).getMembers().forEach(p -> {
                YamlConfiguration yml = MessagesData.getYml(p);
                String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path));
                if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
                if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
                p.sendMessage(TextUtil.colorize(formatted));
                VersionUtil.playSound(p, Misc.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            });
        } else if (Compass.HOOK_NAME.equals("BW2023")) {
            ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getMembers().forEach(p -> {
                YamlConfiguration yml = MessagesData.getYml(p);
                String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path));
                if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
                if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
                p.sendMessage(TextUtil.colorize(formatted));
                VersionUtil.playSound(p, Misc.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            });
        }
    }

    public static void resourceMessage(Player player, Object team, String path, String resourcePath) {
        player.closeInventory();
        if (Compass.HOOK_NAME.equals("BW1058")){
            ((ITeam) team).getMembers().forEach(p -> {
                YamlConfiguration yml = MessagesData.getYml(p);
                String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{resource}", yml.getString(MessagesData.PATH + MainConfig.COMMUNICATIONS_MENU_RESOURCES + "." + resourcePath + ".resource-name")));
                if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
                if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
                p.sendMessage(TextUtil.colorize(formatted));
                VersionUtil.playSound(p, Misc.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            });
        } else if (Compass.HOOK_NAME.equals("BW2023")){
            ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getMembers().forEach(p -> {
                YamlConfiguration yml = MessagesData.getYml(p);
                String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{resource}", yml.getString(MessagesData.PATH + MainConfig.COMMUNICATIONS_MENU_RESOURCES + "." + resourcePath + ".resource-name")));
                if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
                if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
                p.sendMessage(TextUtil.colorize(formatted));
                VersionUtil.playSound(p, Misc.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            });
        }

    }

    public static void teamMessage(Player player, Object team, String path, Object specifiedTeam) {
        player.closeInventory();
        if (Compass.HOOK_NAME.equals("BW1058")) {
            ((ITeam) team).getMembers().forEach(p -> {
                Language lang = (Language) MessagesData.getLang(p);
                YamlConfiguration yml = lang.getYml();
                String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{team}", ((ITeam)specifiedTeam).getColor().chat() + "§l" + ((ITeam)specifiedTeam).getDisplayName(lang)));
                if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
                if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
                p.sendMessage(TextUtil.colorize(formatted));
                VersionUtil.playSound(p, Misc.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            });
        } else if (Compass.HOOK_NAME.equals("BW2023")) {
            ((com.tomkeuper.bedwars.api.arena.team.ITeam) team).getMembers().forEach(p -> {
                com.tomkeuper.bedwars.api.language.Language lang = (com.tomkeuper.bedwars.api.language.Language) MessagesData.getLang(p);
                YamlConfiguration yml = lang.getYml();
                String formatted = yml.getString(MessagesData.TEAM_MESSAGE_FORMAT).replace("{player}", player.getDisplayName()).replace("{message}", yml.getString(path).replace("{team}", ((com.tomkeuper.bedwars.api.arena.team.ITeam)specifiedTeam).getColor().chat() + "§l" + ((com.tomkeuper.bedwars.api.arena.team.ITeam)specifiedTeam).getDisplayName(lang)));
                if (Compass.isUsingVaultChat()) formatted = Compass.getVault().setPlaceholders(formatted, player);
                if (Compass.isUsingPapi()) formatted = PlaceholderAPI.setPlaceholders(player, formatted);
                p.sendMessage(TextUtil.colorize(formatted));
                VersionUtil.playSound(p, Misc.getMainConfig().getString(MainConfig.MESSAGE_SEND_SOUND));
            });
        }
    }
}
